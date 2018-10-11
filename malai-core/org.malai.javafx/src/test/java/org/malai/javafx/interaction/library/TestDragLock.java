package org.malai.javafx.interaction.library;

import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.malai.fsm.CancelFSMException;
import org.malai.javafx.interaction.JfxFSM;
import org.mockito.Mockito;
import org.testfx.framework.junit5.ApplicationExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

@ExtendWith(ApplicationExtension.class)
public class TestDragLock extends BaseJfXInteractionTest<DragLock> {
	@Override
	DragLock createInteraction() {
		return new DragLock();
	}

	@Test
	void testDataHandlerNotNull() {
		assertNotNull(((JfxFSM<?>) interaction.getFsm()).getDataHandler());
	}

	@Test
	void testSubDataHandlerNotNull() {
		assertNotNull(((JfxFSM<?>) interaction.firstClick.firstClick.getFsm()).getDataHandler());
	}

	@Test
	void testNormalExecution() throws CancelFSMException {
		interaction.processEvent(createMouseClickEvent(11, 23, MouseButton.MIDDLE, null));
		interaction.processEvent(createMouseClickEvent(11, 23, MouseButton.MIDDLE, null));
		interaction.processEvent(createMouseMoveEvent(20, 30, MouseButton.MIDDLE));
		interaction.processEvent(createMouseClickEvent(20, 30, MouseButton.MIDDLE, null));
		interaction.processEvent(createMouseClickEvent(20, 30, MouseButton.MIDDLE, null));

		Mockito.verify(handler, Mockito.times(1)).fsmStarts();
		Mockito.verify(handler, Mockito.times(1)).fsmStops();
		Mockito.verify(handler, Mockito.never()).fsmCancels();
	}

	@Test
	void testNormalExecutionData() {
		interaction.getFsm().addHandler(new InteractionHandlerStub() {
			@Override
			public void fsmStops() {
				assertEquals(11d, interaction.getData().getSrcLocalPoint().getX(), 0.0001d);
				assertEquals(23d, interaction.getData().getSrcLocalPoint().getY(), 0.0001d);
				assertEquals(22d, interaction.getData().getTgtLocalPoint().getX(), 0.0001d);
				assertEquals(33d, interaction.getData().getTgtLocalPoint().getY(), 0.0001d);
				assertEquals(MouseButton.MIDDLE, interaction.getData().getButton());
			}
		});

		interaction.processEvent(createMouseClickEvent(11, 23, MouseButton.MIDDLE, null));
		interaction.processEvent(createMouseClickEvent(11, 23, MouseButton.MIDDLE, null));
		interaction.processEvent(createMouseMoveEvent(20, 30, MouseButton.MIDDLE));
		interaction.processEvent(createMouseClickEvent(22, 33, MouseButton.MIDDLE, null));
		interaction.processEvent(createMouseClickEvent(22, 33, MouseButton.MIDDLE, null));
	}

	@Test
	void testUpdateDuringMove() {
		interaction.processEvent(createMouseClickEvent(11, 23, MouseButton.MIDDLE, null));
		interaction.processEvent(createMouseClickEvent(11, 23, MouseButton.MIDDLE, null));

		interaction.getFsm().addHandler(new InteractionHandlerStub() {
			@Override
			public void fsmUpdates() {
				assertEquals(30d, interaction.getData().getTgtLocalPoint().getX(), 0.0001d);
				assertEquals(40d, interaction.getData().getTgtLocalPoint().getY(), 0.0001d);
				assertEquals(MouseButton.MIDDLE, interaction.getData().getButton());
			}
		});

		interaction.processEvent(createMouseMoveEvent(30, 40, MouseButton.MIDDLE));
	}

	@Test
	void testDataDuringMove() throws CancelFSMException {
		interaction.processEvent(createMouseClickEvent(11, 23, MouseButton.MIDDLE, null));
		interaction.processEvent(createMouseClickEvent(11, 23, MouseButton.MIDDLE, null));
		interaction.processEvent(createMouseMoveEvent(30, 40, MouseButton.MIDDLE));
		Mockito.verify(handler, Mockito.times(2)).fsmUpdates();
	}

	@Test
	void testDataReinit() {
		interaction.processEvent(createMouseClickEvent(11, 23, MouseButton.MIDDLE, null));
		interaction.processEvent(createMouseClickEvent(11, 23, MouseButton.MIDDLE, null));
		interaction.processEvent(createMouseMoveEvent(20, 30, MouseButton.MIDDLE));
		interaction.processEvent(createMouseClickEvent(22, 33, MouseButton.MIDDLE, null));
		interaction.processEvent(createMouseClickEvent(22, 33, MouseButton.MIDDLE, null));
		assertNull(interaction.getData().getSrcLocalPoint());
		assertNull(interaction.getData().getTgtLocalPoint());
	}

	@Test
	void testNoMoveCancels() throws CancelFSMException {
		interaction.processEvent(createMouseClickEvent(11, 23, MouseButton.MIDDLE, null));
		interaction.processEvent(createMouseClickEvent(11, 23, MouseButton.MIDDLE, null));
		interaction.processEvent(createMouseClickEvent(11, 23, MouseButton.MIDDLE, null));
		interaction.processEvent(createMouseClickEvent(11, 23, MouseButton.MIDDLE, null));

		Mockito.verify(handler, Mockito.times(1)).fsmStarts();
		Mockito.verify(handler, Mockito.times(1)).fsmCancels();
		Mockito.verify(handler, Mockito.never()).fsmStops();
	}

	@Test
	void testESCCancels() throws CancelFSMException {
		interaction.processEvent(createMouseClickEvent(11, 23, MouseButton.MIDDLE, null));
		interaction.processEvent(createMouseClickEvent(11, 23, MouseButton.MIDDLE, null));
		interaction.processEvent(createKeyPressEvent("ESC", KeyCode.ESCAPE));

		Mockito.verify(handler, Mockito.times(1)).fsmStarts();
		Mockito.verify(handler, Mockito.times(1)).fsmCancels();
		Mockito.verify(handler, Mockito.never()).fsmStops();
	}

	@Test
	void testESCAfterMoveCancels() throws CancelFSMException {
		interaction.processEvent(createMouseClickEvent(11, 23, MouseButton.MIDDLE, null));
		interaction.processEvent(createMouseClickEvent(11, 23, MouseButton.MIDDLE, null));
		interaction.processEvent(createMouseMoveEvent(11, 23, MouseButton.MIDDLE));
		interaction.processEvent(createKeyPressEvent("ESC", KeyCode.ESCAPE));

		Mockito.verify(handler, Mockito.times(1)).fsmStarts();
		Mockito.verify(handler, Mockito.times(1)).fsmCancels();
		Mockito.verify(handler, Mockito.never()).fsmStops();
	}

	@Test
	void testWrongButtonSecondDbleNoEnds() throws CancelFSMException {
		interaction.processEvent(createMouseClickEvent(11, 23, MouseButton.MIDDLE, null));
		interaction.processEvent(createMouseClickEvent(11, 23, MouseButton.MIDDLE, null));
		interaction.processEvent(createMouseMoveEvent(20, 30, MouseButton.MIDDLE));
		interaction.processEvent(createMouseClickEvent(20, 30, MouseButton.PRIMARY, null));
		interaction.processEvent(createMouseClickEvent(20, 30, MouseButton.PRIMARY, null));

		Mockito.verify(handler, Mockito.times(1)).fsmStarts();
		Mockito.verify(handler, Mockito.never()).fsmStops();
	}

	@Test
	void testWrongButtonNoEnds() throws CancelFSMException {
		interaction.processEvent(createMouseClickEvent(11, 23, MouseButton.MIDDLE, null));
		interaction.processEvent(createMouseClickEvent(11, 23, MouseButton.MIDDLE, null));
		interaction.processEvent(createMouseMoveEvent(20, 30, MouseButton.PRIMARY));
		interaction.processEvent(createMouseClickEvent(20, 30, MouseButton.MIDDLE, null));
		interaction.processEvent(createMouseClickEvent(20, 30, MouseButton.MIDDLE, null));

		Mockito.verify(handler, Mockito.times(1)).fsmStarts();
		Mockito.verify(handler, Mockito.times(1)).fsmCancels();
		Mockito.verify(handler, Mockito.never()).fsmStops();
	}
}
