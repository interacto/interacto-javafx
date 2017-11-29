package org.malai.javafx.binding;

import java.util.Objects;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import javafx.scene.Node;
import org.malai.action.AnonAction;
import org.malai.javafx.instrument.JfxInstrument;
import org.malai.javafx.interaction.JfxInteraction;

public class AnonActionBinder<W, I extends JfxInteraction> extends Binder<W, AnonAction, I> {
	private final Runnable action;

	public AnonActionBinder(final Runnable anonAction, final I interaction, final JfxInstrument ins) {
		super(AnonAction.class, interaction, ins);
		action = Objects.requireNonNull(anonAction);
	}

	@Override
	public AnonActionBinder<W, I> on(final W... widget) {
		super.on(widget);
		return this;
	}

	@Override
	public AnonActionBinder<W, I> first(final Consumer<AnonAction> initActionFct) {
		super.first(initActionFct);
		return this;
	}

	@Override
	public AnonActionBinder<W, I> first(final BiConsumer<AnonAction, I> initActionFct) {
		super.first(initActionFct);
		return this;
	}

	@Override
	public AnonActionBinder<W, I> when(final Predicate<I> checkAction) {
		super.when(checkAction);
		return this;
	}

	@Override
	public AnonActionBinder<W, I> end(final BiConsumer<AnonAction, I> onEndFct) {
		super.end(onEndFct);
		return this;
	}

	@Override
	public AnonActionBinder<W, I> async() {
		super.async();
		return this;
	}

	@Override
	public void bind() throws IllegalAccessException, InstantiationException {
		instrument.addBinding(
			new AnonJfxWidgetBinding<>(instrument, false, action, interaction,
			null, null, checkConditions, onEnd, null, null,
				widgets.stream().map(w -> (Node) w).collect(Collectors.toList()), async));
	}
}
