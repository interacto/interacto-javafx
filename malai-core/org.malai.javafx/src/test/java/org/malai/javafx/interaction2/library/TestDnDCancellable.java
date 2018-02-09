package org.malai.javafx.interaction2.library;

import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;
import org.junit.jupiter.api.Test;
import org.malai.fsm.CancelFSMException;
import org.mockito.Mockito;

public class TestDnDCancellable extends BaseJfXInteractionTest<DnD> {
	@Override
	DnD createInteraction() {
		return new DnD(false, true);
	}

	@Test
	void testPressExecution() throws CancelFSMException {
		interaction.processEvent(createMousePressEvent(11, 23, MouseButton.PRIMARY));
		Mockito.verify(handler, Mockito.times(1)).fsmStarts();
		Mockito.verify(handler, Mockito.never()).fsmStops();
		Mockito.verify(handler, Mockito.never()).fsmCancels();
	}

	@Test
	void testPressESCExecution() throws CancelFSMException {
		interaction.processEvent(createMousePressEvent(11, 23, MouseButton.PRIMARY));
		interaction.processEvent(createKeyPressEvent("ESC", KeyCode.ESCAPE));
		Mockito.verify(handler, Mockito.times(1)).fsmStarts();
		Mockito.verify(handler, Mockito.times(1)).fsmCancels();
		Mockito.verify(handler, Mockito.never()).fsmStops();
	}

	@Test
	void testPressMoveESCExecution() throws CancelFSMException {
		interaction.processEvent(createMousePressEvent(11, 23, MouseButton.PRIMARY));
		interaction.processEvent(createMouseMoveEvent(11, 23, MouseButton.PRIMARY));
		interaction.processEvent(createKeyPressEvent("ESC", KeyCode.ESCAPE));
		Mockito.verify(handler, Mockito.times(1)).fsmStarts();
		Mockito.verify(handler, Mockito.times(1)).fsmCancels();
		Mockito.verify(handler, Mockito.never()).fsmStops();
	}

	@Test
	void testPressMoveMoveESCExecution() throws CancelFSMException {
		interaction.processEvent(createMousePressEvent(11, 23, MouseButton.PRIMARY));
		interaction.processEvent(createMouseMoveEvent(11, 23, MouseButton.PRIMARY));
		interaction.processEvent(createMouseMoveEvent(11, 23, MouseButton.PRIMARY));
		interaction.processEvent(createKeyPressEvent("ESC", KeyCode.ESCAPE));
		Mockito.verify(handler, Mockito.times(1)).fsmStarts();
		Mockito.verify(handler, Mockito.times(1)).fsmCancels();
		Mockito.verify(handler, Mockito.never()).fsmStops();
	}
}
