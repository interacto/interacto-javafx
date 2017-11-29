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
	public AnonActionBinder<W, I> init(final Consumer<AnonAction> initActionFct) {
		super.init(initActionFct);
		return this;
	}

	@Override
	public AnonActionBinder<W, I> init(final BiConsumer<AnonAction, I> initActionFct) {
		super.init(initActionFct);
		return this;
	}

	@Override
	public AnonActionBinder<W, I> when(final Predicate<I> checkAction) {
		super.when(checkAction);
		return this;
	}

	@Override
	public AnonActionBinder<W, I> onEnd(final BiConsumer<AnonAction, I> onEndFct) {
		super.onEnd(onEndFct);
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
