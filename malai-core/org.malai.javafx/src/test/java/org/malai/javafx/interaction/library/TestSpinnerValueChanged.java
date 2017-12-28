package org.malai.javafx.interaction.library;

import javafx.scene.control.Spinner;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.malai.javafx.MockitoExtension;
import org.malai.stateMachine.MustCancelStateMachineException;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
class TestSpinnerValueChanged extends BaseJfXInteractionTest<SpinnerValueChanged> {
	Spinner<?> spinner;

	@Override
	@BeforeEach
	void setUp() {
		super.setUp();
		spinner = new Spinner<>();
		DoubleClick.setTimeGap(300L);
	}

	@Test
	void testSetTimeGap() {
		DoubleClick.setTimeGap(200L);
		assertEquals(200L, DoubleClick.getTimeGap());
	}

	@Override
	SpinnerValueChanged createInteraction() {
		return new SpinnerValueChanged();
	}

	@Test
	void testSpinnerChangedGoodState() throws MustCancelStateMachineException {
		interaction.onJfxSpinnerValueChanged(spinner);
		sleep(500);
		Mockito.verify(handler, Mockito.times(1)).interactionStops();
		Mockito.verify(handler, Mockito.times(1)).interactionStarts();
		Mockito.verify(handler, Mockito.times(1)).interactionUpdates();
	}

	@Test
	void testSpinnerChange2TimesGoodState() throws MustCancelStateMachineException {
		interaction.onJfxSpinnerValueChanged(spinner);
		interaction.onJfxSpinnerValueChanged(spinner);
		sleep(500);
		Mockito.verify(handler, Mockito.times(1)).interactionStops();
		Mockito.verify(handler, Mockito.times(1)).interactionStarts();
		Mockito.verify(handler, Mockito.times(2)).interactionUpdates();
	}

	@Test
	void testSpinnerChangedGoodStateithTimeGap() throws MustCancelStateMachineException {
		SpinnerValueChanged.setTimeGap(50L);
		interaction.onJfxSpinnerValueChanged(spinner);
		sleep(100L);
		Mockito.verify(handler, Mockito.times(1)).interactionStops();
		Mockito.verify(handler, Mockito.times(1)).interactionStarts();
		Mockito.verify(handler, Mockito.times(1)).interactionUpdates();
	}

	@Test
	void testSpinnerChangeTwoTimesWith500GoodState() throws MustCancelStateMachineException {
		interaction.onJfxSpinnerValueChanged(spinner);
		sleep(500);
		interaction.onJfxSpinnerValueChanged(spinner);
		sleep(500);
		Mockito.verify(handler, Mockito.times(2)).interactionStops();
		Mockito.verify(handler, Mockito.times(2)).interactionStarts();
		Mockito.verify(handler, Mockito.times(2)).interactionUpdates();
	}
}
