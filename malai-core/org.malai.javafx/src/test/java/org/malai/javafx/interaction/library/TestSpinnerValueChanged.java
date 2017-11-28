package org.malai.javafx.interaction.library;

import javafx.scene.control.Spinner;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.malai.javafx.MockitoExtension;
import org.malai.stateMachine.MustAbortStateMachineException;
import org.mockito.Mockito;

@ExtendWith(MockitoExtension.class)
public class TestSpinnerValueChanged extends BaseJfXInteractionTest<SpinnerValueChanged> {
	Spinner<?> spinner;

	@Override
	@BeforeEach
	public void setUp() {
		super.setUp();
		spinner = new Spinner<>();
	}

	@Override
	protected SpinnerValueChanged createInteraction() {
		return new SpinnerValueChanged();
	}

	@Test
	public void testSpinnerChangedGoodState() throws MustAbortStateMachineException {
		interaction.onJfxSpinnerValueChanged(spinner);
		sleep(500);
		Mockito.verify(handler, Mockito.times(1)).interactionStops(interaction);
		Mockito.verify(handler, Mockito.times(1)).interactionStarts(interaction);
		Mockito.verify(handler, Mockito.times(1)).interactionUpdates(interaction);
	}

	@Test
	public void testSpinnerChange2TimesGoodState() throws MustAbortStateMachineException {
		interaction.onJfxSpinnerValueChanged(spinner);
		interaction.onJfxSpinnerValueChanged(spinner);
		sleep(500);
		Mockito.verify(handler, Mockito.times(1)).interactionStops(interaction);
		Mockito.verify(handler, Mockito.times(1)).interactionStarts(interaction);
		Mockito.verify(handler, Mockito.times(2)).interactionUpdates(interaction);
	}

	@Test
	public void testSpinnerChangeTwoTimesWith500GoodState() throws MustAbortStateMachineException {
		interaction.onJfxSpinnerValueChanged(spinner);
		sleep(500);
		interaction.onJfxSpinnerValueChanged(spinner);
		sleep(500);
		Mockito.verify(handler, Mockito.times(2)).interactionStops(interaction);
		Mockito.verify(handler, Mockito.times(2)).interactionStarts(interaction);
		Mockito.verify(handler, Mockito.times(2)).interactionUpdates(interaction);
	}
}
