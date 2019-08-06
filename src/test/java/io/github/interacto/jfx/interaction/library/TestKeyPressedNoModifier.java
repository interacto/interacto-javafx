package io.github.interacto.jfx.interaction.library;

import io.github.interacto.fsm.CancelFSMException;
import javafx.scene.input.KeyCode;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.testfx.framework.junit5.ApplicationExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(ApplicationExtension.class)
public class TestKeyPressedNoModifier extends BaseJfXInteractionTest<KeyPressed> {
	@Override
	KeyPressed createInteraction() {
		return new KeyPressed(false);
	}

	@Test
	public void testKeyPressExecution() throws CancelFSMException {
		interaction.processEvent(createKeyPressEvent("A", KeyCode.A));
		Mockito.verify(handler, Mockito.times(1)).fsmStops();
		Mockito.verify(handler, Mockito.times(1)).fsmStarts();
	}

	@Test
	void testKeyPressData() {
		interaction.getFsm().addHandler(new InteractionHandlerStub() {
			@Override
			public void fsmStops() {
				assertEquals("A", interaction.getKey());
				assertEquals(KeyCode.A, interaction.getKeyCode());
			}
		});
		interaction.processEvent(createKeyPressEvent("A", KeyCode.A));
	}

	@Test
	public void testKeyPressExecutionKOCtrl() throws CancelFSMException {
		interaction.processEvent(createKeyPressEvent("CONTROL", KeyCode.CONTROL));
		Mockito.verify(handler, Mockito.never()).fsmStarts();
	}

	@Test
	public void testKeyPressExecutionKOAlt() throws CancelFSMException {
		interaction.processEvent(createKeyPressEvent("ALT", KeyCode.ALT));
		Mockito.verify(handler, Mockito.never()).fsmStarts();
	}

	@Test
	public void testKeyPressExecutionKOMeta() throws CancelFSMException {
		interaction.processEvent(createKeyPressEvent("META", KeyCode.META));
		Mockito.verify(handler, Mockito.never()).fsmStarts();
	}

	@Test
	public void testKeyPressExecutionKOCmd() throws CancelFSMException {
		interaction.processEvent(createKeyPressEvent("COMMAND", KeyCode.COMMAND));
		Mockito.verify(handler, Mockito.never()).fsmStarts();
	}
}
