package org.malai.javafx.binding;

import java.util.Objects;
import java.util.stream.Collectors;
import javafx.scene.Node;
import org.malai.action.AnonAction;
import org.malai.javafx.instrument.JfxInstrument;
import org.malai.javafx.interaction.JfxInteraction;

public class AnonActionBinder<W, I extends JfxInteraction> extends Binder<W, AnonAction, I, AnonActionBinder<W, I>> {
	private final Runnable action;

	public AnonActionBinder(final Runnable anonAction, final I interaction, final JfxInstrument ins) {
		super(AnonAction.class, interaction, ins);
		action = Objects.requireNonNull(anonAction);
	}

	@Override
	public JfXWidgetBinding<AnonAction, I, ?> bind() throws IllegalAccessException, InstantiationException {
		final AnonJfxWidgetBinding<I, JfxInstrument> binding = new AnonJfxWidgetBinding<>(instrument, false, action, interaction,
			null, null, checkConditions, onEnd, actionProducer, null, null, null,
			widgets.stream().map(w -> (Node) w).collect(Collectors.toList()), additionalWidgets, async);
		instrument.addBinding(binding);
		return binding;
	}
}
