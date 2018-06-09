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
package org.malai.javafx.binding;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CancellationException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Function;
import java.util.logging.Level;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.stage.Window;
import org.malai.binding.WidgetBindingImpl;
import org.malai.command.Command;
import org.malai.command.CommandImpl;
import org.malai.error.ErrorCatcher;
import org.malai.interaction.InteractionData;
import org.malai.javafx.instrument.JfxInstrument;
import org.malai.javafx.interaction.JfxInteraction;
import org.malai.javafx.interaction.help.HelpAnimation;
import org.malai.javafx.interaction.help.HelpAnimationPlayer;

/**
 * Base of a widget binding for JavaFX applications.
 * @author Arnaud BLOUIN
 */
public abstract class JfXWidgetBinding<C extends CommandImpl, I extends JfxInteraction<D, ?, ?>, N extends JfxInstrument, D extends InteractionData>
			extends WidgetBindingImpl<C, I, N, D> {
	/** The executor service used to execute command async. Do not access directly (lazy instantiation). Use its private getter instead. */
	private static ExecutorService executorService = null;

	private static ExecutorService getCmdExecutor() {
		if(executorService == null) {
			executorService = Executors.newSingleThreadExecutor();
		}
		return executorService;
	}

	protected final BooleanProperty activation;
	protected boolean withHelp;
	protected HelpAnimation customAnimation;
	/** The property used to displayed a message while executing a command async. */
	protected StringProperty progressMsgProp;
	/** The property used to make a progress bar progressing while executing a command async. */
	protected DoubleProperty progressBarProp;
	/** The button used to stop the command executed async. May be null. */
	protected Button cancelCmdButton;

	private final ChangeListener<Boolean> activationHandler = (observable, oldValue, newValue) -> {
		if(oldValue != newValue) {
			interaction.setActivated(newValue);
		}
	};

	/**
	 * Creates a widget binding. This constructor must initialise the interaction. The binding is (de-)activated if the given
	 * instrument is (de-)activated.
	 * @param ins The instrument that contains the widget binding.
	 * @param exec Specifies whether the command must be execute or update on each evolution of the interaction.
	 * @param interaction The user interaction of the binding.
	 * @param cmdCreation The function to call to create a command.
	 * @param widgets The widgets concerned by the binding. Cannot be null.
	 * @throws IllegalArgumentException If the given interaction or instrument is null.
	 */
	public JfXWidgetBinding(final N ins, final boolean exec, final I interaction, final Function<D, C> cmdCreation, final List<Node> widgets, final boolean help,
							final HelpAnimation animation) {
		super(ins, exec, cmdCreation, interaction);
		activation = new SimpleBooleanProperty(isActivated());
		activation.addListener(activationHandler);
		withHelp = help;
		customAnimation = animation;
		interaction.registerToNodes(widgets);
	}

	/**
	 * Creates a widget binding. This constructor must initialise the interaction. The binding is (de-)activated if the given
	 * instrument is (de-)activated.
	 * @param ins The instrument that contains the widget binding.
	 * @param exec Specifies whether the command must be execute or update on each evolution of the interaction.
	 * @param interaction The user interaction of the binding.
	 * @param cmdCreation The function to call to create a command.
	 * @param widgets The widgets concerned by the binding. Cannot be null.
	 * @throws IllegalArgumentException If the given interaction or instrument is null.
	 */
	public JfXWidgetBinding(final N ins, final boolean exec, final I interaction, final Function<D, C> cmdCreation, final boolean help,
							final HelpAnimation animation, final Node... widgets) {
		this(ins, exec, interaction, cmdCreation, Arrays.asList(widgets), help, animation);
	}

	/**
	 * Creates a widget binding for windows. This constructor must initialise the interaction. The binding is (de-)activated if the given
	 * instrument is (de-)activated.
	 * @param ins The instrument that contains the widget binding.
	 * @param exec Specifies if the command must be execute or update on each evolution of the interaction.
	 * @param windows The windows concerned by the binding. Cannot be null.
	 * @param interaction The user interaction of the binding.
	 * @param cmdCreation The function that creates commands.
	 * @throws IllegalArgumentException If the given interaction or instrument is null.
	 */
	public JfXWidgetBinding(final N ins, final boolean exec, final List<Window> windows, final I interaction, final Function<D, C> cmdCreation,
							final HelpAnimation animation, final boolean help) {
		super(ins, exec, cmdCreation, interaction);
		activation = new SimpleBooleanProperty(isActivated());
		withHelp = help;
		customAnimation = animation;
		activation.addListener(activationHandler);
		interaction.registerToWindows(windows);
	}

	/**
	 * The property used to displayed a message while executing a command async.
	 * @param progressMsgProp The string property to be bound. Nothing done if null or already bound.
	 */
	public void setProgressMsgProp(final StringProperty progressMsgProp) {
		this.progressMsgProp = progressMsgProp;
	}

	/**
	 * The property used to make a progress bar progressing while executing a command async.
	 * @param progressBarProp The double property to be bound. Nothing done if null or already bound.
	 */
	public void setProgressBarProp(final DoubleProperty progressBarProp) {
		this.progressBarProp = progressBarProp;
	}

	/**
	 * The button used to stop the command executed async.
	 * @param cancelCmdButton The cancel button. May be null.
	 */
	public void setCancelCmdButton(final Button cancelCmdButton) {
		this.cancelCmdButton = cancelCmdButton;
	}

	@Override
	public void setActivated(final boolean activ) {
		if(activation != null && !activation.isBound()) {
			activation.set(activ);
		}
		if(activation != null && withHelp) {
			final HelpAnimation anim = customAnimation == null ? interaction.getHelpAnimation().orElse(null) : customAnimation;
			if(anim != null) {
				if(activation.get()) {//TODO heuristics
					HelpAnimationPlayer.INSTANCE.add(anim);
				}else {
					HelpAnimationPlayer.INSTANCE.stop(anim);
				}
			}
		}
	}


	@Override
	protected void executeCmdAsync(final Command cmd) {
		final BindingTask task = new BindingTask(cmd);
		final boolean progressBound = progressBarProp != null && !progressBarProp.isBound();
		final boolean msgBound = progressMsgProp != null && !progressMsgProp.isBound();
		final EventHandler<ActionEvent> cancelEvent = evt -> task.cancel();

		if(progressBound) {
			progressBarProp.bind(task.progressProperty());
		}

		if(msgBound) {
			progressMsgProp.bind(task.messageProperty());
		}

		if(cancelCmdButton != null) {
			cancelCmdButton.addEventHandler(ActionEvent.ACTION, cancelEvent);
			cancelCmdButton.setVisible(true);
		}

		getCmdExecutor().submit(task);

		new Thread(() -> {
			boolean ok;

			try {
				ok = task.get();
			}catch(final CancellationException | InterruptedException ex1) {
				if(loggerCmd != null) {
					loggerCmd.log(Level.INFO, "Command execution cancelled: " + cmd);
				}
				ok = false;
			}catch(final ExecutionException ex2) {
				ErrorCatcher.INSTANCE.reportError(ex2);
				ok = false;
			}

			if(cancelCmdButton != null) {
				cancelCmdButton.removeEventHandler(ActionEvent.ACTION, cancelEvent);
				cancelCmdButton.setVisible(false);
			}

			if(progressBound) {
				progressBarProp.unbind();
			}

			if(msgBound) {
				progressMsgProp.unbind();
			}

			afterCmdExecuted(cmd, ok);
		}).start();
	}

	@Override
	public boolean when() {
		return true;
	}

	public BooleanProperty activationProperty() {
		return activation;
	}

	@Override
	public void uninstallBinding() {
		activation.unbind();
		activation.removeListener(activationHandler);
		if(progressBarProp != null) {
			progressBarProp.unbind();
			progressBarProp = null;
		}
		if(progressMsgProp != null) {
			progressMsgProp.unbind();
			progressMsgProp = null;
		}
		customAnimation = null;
		cancelCmdButton = null;
		//TODO cancelCmdButton action handler
		super.uninstallBinding();
	}
}
