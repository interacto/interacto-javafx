package org.malai.javafx.binding;

import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.function.Predicate;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.stage.Window;
import org.malai.action.AnonAction;
import org.malai.javafx.instrument.JfxInstrument;
import org.malai.javafx.interaction.JfxInteraction;
import org.malai.javafx.interaction.help.HelpAnimation;
import org.malai.logging.LogLevel;

public class AnonJfxWidgetBinding<I extends JfxInteraction, N extends JfxInstrument> extends JFxAnonNodeBinding<AnonAction, I, N> {
	private final Runnable anonAction;

	public AnonJfxWidgetBinding(final N ins, final boolean exec, final Runnable action, final I interaction,
								final BiConsumer<AnonAction, I> initActionFct, final BiConsumer<AnonAction, I> updateActionFct,
								final Predicate<I> check, final BiConsumer<AnonAction, I> onEndFct, final Function<I, AnonAction> actionFunction,
								final BiConsumer<AnonAction, I> cancel, final BiConsumer<AnonAction, I> endOrCancel, final Runnable feedback,
								final List<Node> widgets, final List<ObservableList<? extends Node>> additionalWidgets, final boolean async,
								final boolean strict, final Set<LogLevel> loggers, final boolean help, final HelpAnimation animation)
								throws InstantiationException, IllegalAccessException {
		super(ins, exec, AnonAction.class, interaction, initActionFct, updateActionFct, check, onEndFct, actionFunction, cancel, endOrCancel, feedback,
			widgets, additionalWidgets, async, strict, loggers, help, animation);
		anonAction = Objects.requireNonNull(action);
	}

	public AnonJfxWidgetBinding(final N ins, final boolean exec, final Runnable action, final I interaction, final List<Window> widgets,
								final BiConsumer<AnonAction, I> initActionFct, final BiConsumer<AnonAction, I> updateActionFct, final Predicate<I> check,
								final BiConsumer<AnonAction, I> onEndFct, final Function<I, AnonAction> actionFunction, final BiConsumer<AnonAction, I> cancel,
								final BiConsumer<AnonAction, I> endOrCancel, final Runnable feedback, final boolean async,
								final boolean strict, final Set<LogLevel> loggers, final boolean help, final HelpAnimation animation)
								throws InstantiationException, IllegalAccessException {
		super(ins, exec, AnonAction.class, interaction, widgets, initActionFct, updateActionFct, check, onEndFct, actionFunction, cancel, endOrCancel,
			feedback, async, strict, loggers, help, animation);
		anonAction = Objects.requireNonNull(action);
	}

	@Override
	protected AnonAction map() {
		return new AnonAction(anonAction);
	}
}
