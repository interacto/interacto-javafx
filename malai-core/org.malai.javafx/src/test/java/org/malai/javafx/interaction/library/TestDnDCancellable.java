package org.malai.javafx.interaction.library;

import java.util.Collections;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.Pane;
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
		Mockito.verify(handler, Mockito.never()).fsmStarts();
		Mockito.verify(handler, Mockito.never()).fsmStops();
		Mockito.verify(handler, Mockito.never()).fsmCancels();
	}

	@Test
	void testPressESCExecution() throws CancelFSMException {
		interaction.processEvent(createMousePressEvent(11, 23, MouseButton.PRIMARY));
		interaction.processEvent(createKeyPressEvent("ESC", KeyCode.ESCAPE));
		Mockito.verify(handler, Mockito.never()).fsmStarts();
		Mockito.verify(handler, Mockito.never()).fsmCancels();
		Mockito.verify(handler, Mockito.never()).fsmStops();
	}

	@Test
	void testPressDragESCExecution() throws CancelFSMException {
		interaction.processEvent(createMousePressEvent(11, 23, MouseButton.PRIMARY));
		interaction.processEvent(createMouseDragEvent(11, 23, MouseButton.PRIMARY, null));
		interaction.processEvent(createKeyPressEvent("ESC", KeyCode.ESCAPE));
		Mockito.verify(handler, Mockito.times(1)).fsmStarts();
		Mockito.verify(handler, Mockito.times(1)).fsmCancels();
		Mockito.verify(handler, Mockito.never()).fsmStops();
	}

	@Test
	void testPressDragDragESCExecution() throws CancelFSMException {
		interaction.processEvent(createMousePressEvent(11, 23, MouseButton.PRIMARY));
		interaction.processEvent(createMouseDragEvent(11, 23, MouseButton.PRIMARY, null));
		interaction.processEvent(createMouseDragEvent(11, 23, MouseButton.PRIMARY, null));
		interaction.processEvent(createKeyPressEvent("ESC", KeyCode.ESCAPE));
		Mockito.verify(handler, Mockito.times(1)).fsmStarts();
		Mockito.verify(handler, Mockito.times(1)).fsmCancels();
		Mockito.verify(handler, Mockito.never()).fsmStops();
	}

	@Test
	void testNewKindEventCancelRegistered() {
		Pane pane = new Pane();
		interaction.registerToNodes(Collections.singletonList(pane));
		pane.fireEvent(createMousePressEvent(11, 23, MouseButton.PRIMARY));
		pane.fireEvent(createMouseDragEvent(12, 25, MouseButton.PRIMARY, null));
		interaction.processEvent(createKeyPressEvent("ESC", KeyCode.ESCAPE));
		Mockito.verify(handler, Mockito.times(1)).fsmCancels();
	}

	@Test
	void testNewKindEventAfterCancelRegistered() throws CancelFSMException {
		Pane pane = new Pane();
		interaction.registerToNodes(Collections.singletonList(pane));
		pane.fireEvent(createMousePressEvent(11, 23, MouseButton.PRIMARY));
		pane.fireEvent(createMouseDragEvent(12, 25, MouseButton.PRIMARY, null));
		interaction.processEvent(createKeyPressEvent("ESC", KeyCode.ESCAPE));
		pane.fireEvent(createMousePressEvent(11, 23, MouseButton.PRIMARY));
		pane.fireEvent(createMouseDragEvent(12, 25, MouseButton.PRIMARY, null));
		Mockito.verify(handler, Mockito.times(2)).fsmStarts();
	}
}
