/*
 * This file is part of Malai.
 * Copyright (c) 2009-2018 Arnaud BLOUIN
 * Malai is free software; you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation; either version 2 of the License, or (at your option) any later version.
 * Malai is distributed without any warranty; without even the implied
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * General Public License for more details.
 */
package org.malai.binding;

import java.util.Arrays;
import java.util.function.Function;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.property.Property;
import org.malai.command.AutoUnbind;
import org.malai.command.Command;
import org.malai.command.CommandImpl;
import org.malai.command.CommandsRegistry;
import org.malai.fsm.CancelFSMException;
import org.malai.instrument.Instrument;
import org.malai.interaction.InteractionData;
import org.malai.interaction.InteractionImpl;
import org.malai.undo.Undoable;

/**
 * The base class to do widget bindings, i.e. bindings between user interactions and (undoable) commands.
 * @param <A> The type of the command that will produce this widget binding.
 * @param <I> The type of the interaction that will use this widget binding.
 * @param <N> The type of the instrument that will contain this widget binding.
 * @author Arnaud BLOUIN
 */
public abstract class WidgetBindingImpl<A extends CommandImpl, I extends InteractionImpl<D, ?, ?>, N extends Instrument<?>, D extends InteractionData> implements WidgetBinding {
	protected Logger loggerBinding;

	protected Logger loggerCmd;

	/** The source interaction. */
	protected final I interaction;

	/** The current command in progress. */
	protected A cmd;

	/** The instrument that contains the widget binding. */
	protected final N instrument;

	/** Specifies if the command must be execute or update * on each evolution of the interaction. */
	protected final boolean execute;

	/** Defines whether the command must be executed in a specific thread. */
	protected boolean async;

	/** A function that produces commands. */
	protected final Function<D, A> cmdProducer;


	/**
	 * Creates a widget binding. This constructor must initialise the interaction. The widget binding is (de-)activated if the given
	 * instrument is (de-)activated.
	 * @param ins The instrument that contains the widget binding.
	 * @param exec Specifies if the command must be execute or update on each evolution of the interaction.
	 * @param cmdCreation The type of the command that will be created. Used to instantiate the cmd by reflexivity.
	 * The class must be public and must have a constructor with no parameter.
	 * @param interaction The user interaction of the binding.
	 * @throws IllegalArgumentException If the given interaction or instrument is null.
	 */
	public WidgetBindingImpl(final N ins, final boolean exec, final Function<D, A> cmdCreation, final I interaction) {
		super();

		if(ins == null || cmdCreation == null || interaction == null) {
			throw new IllegalArgumentException();
		}

		cmdProducer = cmdCreation;
		this.interaction = interaction;
		cmd = null;
		instrument = ins;
		execute = exec;
		this.interaction.getFsm().addHandler(this);
		setActivated(ins.isActivated());
		async = false;
	}

	public void logBinding(final boolean log) {
		if(log) {
			if(loggerBinding == null) {
				loggerBinding = Logger.getLogger(getClass().getName());
			}
		}else {
			loggerBinding = null;
		}
	}

	public void logCmd(final boolean log) {
		if(log) {
			if(loggerCmd == null) {
				loggerCmd = Logger.getLogger(getClass().getName());
			}
		}else {
			loggerCmd = null;
		}
	}

	public void logInteraction(final boolean log) {
		interaction.log(log);
	}

	/**
	 * Whether the command must be executed in a specific thread.
	 * @return True: the command will be executed asynchronously.
	 */
	public boolean isAsync() {
		return async;
	}

	/**
	 * Sets wether the command must be executed in a specific thread.
	 * @param asyncCmd True: the command will be executed asynchronously.
	 */
	public void setAsync(final boolean asyncCmd) {
		async = asyncCmd;
	}

	@Override
	public void clearEvents() {
		interaction.fullReinit();
	}


	/**
	 * creates the command of the widget binding. If the attribute 'cmd' is not null, nothing will be done.
	 * @return The created command or null if problems occured.
	 */
	protected A map() {
		return cmdProducer.apply(interaction.getData());
	}


	@Override
	public abstract void first();


	@Override
	public void then() {
		// to override.
	}


	@Override
	public abstract boolean when();


	@Override
	public I getInteraction() {
		return interaction;
	}


	@Override
	public A getCommand() {
		return cmd;
	}


	@Override
	public boolean isActivated() {
		return instrument.isActivated();
	}

	@Override
	public boolean isRunning() {
		return interaction.isRunning();
	}


	@Override
	public boolean isStrictStart() {
		return false;
	}

	protected void unbindCmdAttributes() {
		if(cmd != null) {
			unbindCmdAttributesClass(cmd.getClass());
			if(loggerCmd != null) {
				loggerCmd.log(Level.INFO, "Command unbound: " + cmd);
			}
		}
	}

	private void unbindCmdAttributesClass(final Class<? extends CommandImpl> clazz) {
		Arrays.stream(clazz.getDeclaredFields()).filter(field -> field.isAnnotationPresent(AutoUnbind.class) &&
			Property.class.isAssignableFrom(field.getType())).forEach(field -> {
			try {
				final boolean access = field.isAccessible();
				field.setAccessible(true);
				final Object o = field.get(cmd);
				if(o instanceof Property<?>) {
					((Property<?>) o).unbind();
				}
				field.setAccessible(access);
			}catch(final IllegalAccessException ex) {
				ex.printStackTrace();
			}
		});

		final Class<?> superClass = clazz.getSuperclass();
		if(superClass != null && superClass != CommandImpl.class && CommandImpl.class.isAssignableFrom(superClass)) {
			unbindCmdAttributesClass((Class<? extends CommandImpl>) superClass);
		}
	}

	@Override
	public void fsmCancels() {
		if(cmd != null) {
			if(loggerBinding != null) {
				loggerBinding.log(Level.INFO, "Binding cancelled");
			}

			cmd.cancel();
			if(loggerCmd != null) {
				loggerCmd.log(Level.INFO, "Command cancelled");
			}
			unbindCmdAttributes();

			// The instrument is notified about the cancel of the command.
			instrument.onCmdCancelled(cmd);

			if(isExecute() && cmd.hadEffect()) {
				if(cmd instanceof Undoable) {
					((Undoable) cmd).undo();
					if(loggerCmd != null) {
						loggerCmd.log(Level.INFO, "Command undone");
					}
				}else {
					throw new MustBeUndoableCmdException(cmd.getClass());
				}
			}

			cmd = null;
			instrument.interimFeedback();
		}
	}


	@Override
	public void fsmStarts() throws CancelFSMException {
		final boolean ok = cmd == null && isActivated() && when();

		if(loggerBinding != null) {
			loggerBinding.log(Level.INFO, "Starting binding: " + ok);
		}

		if(ok) {
			cmd = map();
			first();
			if(loggerCmd != null) {
				loggerCmd.log(Level.INFO, "Command created and init: " + cmd);
			}
			feedback();
		}else {
			if(isStrictStart()) {
				if(loggerBinding != null) {
					loggerBinding.log(Level.INFO, "Cancelling starting interaction: " + interaction);
				}
				throw new CancelFSMException();
			}
		}
	}


	@Override
	public void fsmStops() {
		final boolean ok = when();
		if(loggerBinding != null) {
			loggerBinding.log(Level.INFO, "Binding stops with condition: " + ok);
		}
		if(ok) {
			if(cmd == null) {
				cmd = map();
				first();
				if(loggerCmd != null) {
					loggerCmd.log(Level.INFO, "Command created and init: " + cmd);
				}
			}

			if(!execute) {
				then();
				if(loggerCmd != null) {
					loggerCmd.log(Level.INFO, "Command updated: " + cmd);
				}
			}

			executeCmd(cmd, async);
			unbindCmdAttributes();
			cmd = null;
			instrument.interimFeedback();
		}else {
			if(cmd != null) {
				if(loggerCmd != null) {
					loggerCmd.log(Level.INFO, "Cancelling the command: " + cmd);
				}
				cmd.cancel();
				unbindCmdAttributes();
				instrument.onCmdCancelled(cmd);
				cmd = null;
				instrument.interimFeedback();
			}
		}
	}


	private void executeCmd(final Command cmd, final boolean async) {
		if(async) {
			executeCmdAsync(cmd);
		}else {
			afterCmdExecuted(cmd, cmd.doIt());
		}
	}

	protected abstract void executeCmdAsync(final Command cmd);

	protected void afterCmdExecuted(final Command cmd, final boolean ok) {
		if(loggerCmd != null) {
			loggerCmd.log(Level.INFO, "Command execution did it: " + ok);
		}

		if(ok) {
			instrument.onCmdExecuted(cmd);
			cmd.done();
			instrument.onCmdDone(cmd);
		}

		final boolean hadEffect = cmd.hadEffect();

		if(loggerCmd != null) {
			loggerCmd.log(Level.INFO, "Command execution had effect: " + hadEffect);
		}
		if(hadEffect) {
			if(cmd.getRegistrationPolicy() != Command.RegistrationPolicy.NONE) {
				CommandsRegistry.INSTANCE.addCommand(cmd, instrument);
				instrument.onCmdAdded(cmd);
			}else {
				CommandsRegistry.INSTANCE.unregisterCommand(cmd);
			}
			cmd.followingCmds().forEach(cmdFollow -> executeCmd(cmdFollow, false));
		}
	}


	@Override
	public void fsmUpdates() {
		final boolean ok = when();

		if(loggerBinding != null) {
			loggerBinding.log(Level.INFO, "Binding updates with condition: " + ok);
		}

		if(ok) {
			if(cmd == null) {
				if(loggerCmd != null) {
					loggerCmd.log(Level.INFO, "Command creation");
				}
				cmd = map();
				first();
			}

			if(loggerCmd != null) {
				loggerCmd.log(Level.INFO, "Command update");
			}

			then();

			if(execute && cmd.canDo()) {
				if(loggerCmd != null) {
					loggerCmd.log(Level.INFO, "Command execution");
				}
				cmd.doIt();
				instrument.onCmdExecuted(cmd);
			}

			feedback();
		}
	}

	@Override
	public void uninstallBinding() {
		setActivated(false);
		loggerCmd = null;
		loggerBinding = null;
	}

	@Override
	public boolean isExecute() {
		return execute;
	}


	@Override
	public void feedback() {
		//
	}


	@Override
	public void setActivated(final boolean activ) {
		if(loggerBinding != null) {
			loggerBinding.log(Level.INFO, "Binding Activated: " + activ);
		}

		interaction.setActivated(activ);
		if(!activ && cmd != null) {
			cmd.flush();
			cmd = null;
		}
	}


	@Override
	public N getInstrument() {
		return instrument;
	}
}
