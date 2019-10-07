/*
 * Interacto
 * Copyright (C) 2019 Arnaud Blouin
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * You should have received a copy of the GNU General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 */
package io.github.interacto.jfx.binding;

import io.github.interacto.binding.WidgetBindingImpl;
import io.github.interacto.command.AutoUnbind;
import io.github.interacto.command.Command;
import io.github.interacto.command.CommandImpl;
import io.github.interacto.error.ErrorCatcher;
import io.github.interacto.interaction.InteractionData;
import io.github.interacto.jfx.interaction.JfxInteraction;
import io.github.interacto.jfx.interaction.help.HelpAnimation;
import io.github.interacto.jfx.interaction.help.HelpAnimationPlayer;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CancellationException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Function;
import java.util.logging.Level;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.Property;
import javafx.beans.property.StringProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.stage.Window;

/**
 * Base of a widget binding for JavaFX applications.
 * @author Arnaud BLOUIN
 */
public abstract class JfXWidgetBinding<C extends CommandImpl, I extends JfxInteraction<D, ?, ?>, D extends InteractionData>
			extends WidgetBindingImpl<C, I, D> {
	/** The executor service used to execute command async. Do not access directly (lazy instantiation). Use its private getter instead. */
	private static ExecutorService executorService = null;

	private static synchronized ExecutorService getCmdExecutor() {
		if(executorService == null) {
			executorService = Executors.newSingleThreadExecutor();
		}
		return executorService;
	}

	protected boolean withHelp;
	protected HelpAnimation customAnimation;
	/** The property used to displayed a message while executing a command async. */
	protected StringProperty progressMsgProp;
	/** The property used to make a progress bar progressing while executing a command async. */
	protected DoubleProperty progressBarProp;
	/** The button used to stop the command executed async. May be null. */
	protected Button cancelCmdButton;


	/**
	 * Creates a widget binding. This constructor must initialise the interaction. The binding is (de-)activated if the given
	 * instrument is (de-)activated.
	 * @param continuousExec Specifies whether the command must be executed on each evolution of the interaction.
	 * @param interaction The user interaction of the binding.
	 * @param cmdCreation The function to call to create a command.
	 * @param widgets The widgets concerned by the binding. Cannot be null.
	 * @throws IllegalArgumentException If the given interaction or instrument is null.
	 */
	public JfXWidgetBinding(final boolean continuousExec, final I interaction, final Function<D, C> cmdCreation, final List<Node> widgets, final boolean help,
							final HelpAnimation animation) {
		super(continuousExec, cmdCreation, interaction);
		withHelp = help;
		customAnimation = animation;
		interaction.registerToNodes(widgets);
	}

	/**
	 * Creates a widget binding. This constructor must initialise the interaction. The binding is (de-)activated if the given
	 * instrument is (de-)activated.
	 * @param continuousExec Specifies whether the command must be executed on each evolution of the interaction.
	 * @param interaction The user interaction of the binding.
	 * @param cmdCreation The function to call to create a command.
	 * @param widgets The widgets concerned by the binding. Cannot be null.
	 * @throws IllegalArgumentException If the given interaction or instrument is null.
	 */
	public JfXWidgetBinding(final boolean continuousExec, final I interaction, final Function<D, C> cmdCreation, final boolean help,
							final HelpAnimation animation, final Node... widgets) {
		this(continuousExec, interaction, cmdCreation, Arrays.asList(widgets), help, animation);
	}

	/**
	 * Creates a widget binding for windows. This constructor must initialise the interaction. The binding is (de-)activated if the given
	 * instrument is (de-)activated.
	 * @param continuousExec Specifies whether the command must be executed on each evolution of the interaction.
	 * @param windows The windows concerned by the binding. Cannot be null.
	 * @param interaction The user interaction of the binding.
	 * @param cmdCreation The function that creates commands.
	 * @throws IllegalArgumentException If the given interaction or instrument is null.
	 */
	public JfXWidgetBinding(final boolean continuousExec, final List<Window> windows, final I interaction, final Function<D, C> cmdCreation,
							final HelpAnimation animation, final boolean help) {
		super(continuousExec, cmdCreation, interaction);
		withHelp = help;
		customAnimation = animation;
		interaction.registerToWindows(windows);
	}

	@Override
	protected void unbindCmdAttributes() {
		if(cmd != null) {
			unbindCmdAttributesClass(cmd.getClass());
			if(loggerCmd != null) {
				loggerCmd.log(Level.INFO, "Command unbound: " + cmd);
			}
		}
	}

	private void unbindCmdAttributesClass(final Class<? extends CommandImpl> clazz) {
		Arrays
			.stream(clazz.getDeclaredFields())
			.filter(field -> field.isAnnotationPresent(AutoUnbind.class) && Property.class.isAssignableFrom(field.getType()))
			.forEach(field -> {
				try {
					final boolean access = field.canAccess(cmd);
					field.setAccessible(true);
					final Object o = field.get(cmd);
					if(o instanceof Property<?>) {
						((Property<?>) o).unbind();
					}
					field.setAccessible(access);
				}catch(final IllegalAccessException ex) {
					ErrorCatcher.INSTANCE.reportError(ex);
				}
			});

		final Class<?> superClass = clazz.getSuperclass();
		if(superClass != null && superClass != CommandImpl.class && CommandImpl.class.isAssignableFrom(superClass)) {
			unbindCmdAttributesClass((Class<? extends CommandImpl>) superClass);
		}
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
		super.setActivated(activ);
		if(withHelp) {
			if(customAnimation != null) {
				if(isActivated()) {
					HelpAnimationPlayer.INSTANCE.add(customAnimation);
				}else {
					HelpAnimationPlayer.INSTANCE.stop(customAnimation);
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
		launchThreadAsync(task, progressBound, msgBound, cancelEvent);
	}

	private void launchThreadAsync(final BindingTask task, final boolean progressBound, final boolean msgBound,
								final EventHandler<ActionEvent> cancelEvent) {
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


	@Override
	public void uninstallBinding() {
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
