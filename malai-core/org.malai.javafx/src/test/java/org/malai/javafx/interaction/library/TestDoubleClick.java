package org.malai.javafx.interaction.library;

import java.util.Collections;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.Pane;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.malai.fsm.CancelFSMException;
import org.malai.javafx.JfxtestHelper;
import org.malai.javafx.interaction.JfxFSM;
import org.mockito.Mockito;
import org.testfx.framework.junit5.ApplicationExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

@ExtendWith(ApplicationExtension.class)
public class TestDoubleClick extends BaseJfXInteractionTest<DoubleClick> {
	@Override
	DoubleClick createInteraction() {
		return new DoubleClick();
	}

	@Test
	void testDbleClickExecution() throws CancelFSMException {
		interaction.processEvent(createMouseClickEvent(11, 23, MouseButton.MIDDLE, null));
		interaction.processEvent(createMouseClickEvent(11, 23, MouseButton.MIDDLE, null));
		Mockito.verify(handler, Mockito.times(1)).fsmStarts();
		Mockito.verify(handler, Mockito.times(1)).fsmStops();
	}

	@Test
	void testDbleClickData() {
		interaction.getFsm().addHandler(new InteractionHandlerStub() {
			@Override
			public void fsmStops() {
				assertEquals(11, interaction.getData().getSrcLocalPoint().getX(), 0.0001d);
				assertEquals(23, interaction.getData().getSrcLocalPoint().getY(), 0.0001d);
				assertEquals(MouseButton.MIDDLE, interaction.getData().getButton());
			}
		});
		interaction.processEvent(createMouseClickEvent(11, 23, MouseButton.MIDDLE, null));
		interaction.processEvent(createMouseClickEvent(11, 23, MouseButton.MIDDLE, null));
	}

	@Test
	void testDataHandlerNotNull() {
		assertNotNull(((JfxFSM<?>) interaction.getFsm()).getDataHandler());
	}

	@Test
	void testSubDataHandlerNotNull() {
		assertNotNull(((JfxFSM<?>) ((Click) interaction.getData()).getFsm()).getDataHandler());
	}

	@Test
	void testDbleClickOnWidgetOKOnStarts() {
		final Pane pane = new Pane();
		interaction.registerToNodes(Collections.singletonList(pane));
		interaction.getFsm().addHandler(new InteractionHandlerStub() {
			@Override
			public void fsmStarts() {
				assertEquals(11, interaction.getData().getSrcLocalPoint().getX(), 0.0001d);
				assertEquals(23, interaction.getData().getSrcLocalPoint().getY(), 0.0001d);
				assertEquals(MouseButton.MIDDLE, interaction.getData().getButton());
				assertEquals(pane, interaction.getData().getSrcObject().get());
			}
		});
		pane.fireEvent(createMouseClickEvent(11, 23, MouseButton.MIDDLE, pane));
	}

	@Test
	void testDbleClickOnWidgetOkOnStops() {
		final Pane pane = new Pane();
		interaction.registerToNodes(Collections.singletonList(pane));
		interaction.getFsm().addHandler(new InteractionHandlerStub() {
			@Override
			public void fsmStops() {
				assertEquals(11, interaction.getData().getSrcLocalPoint().getX(), 0.0001d);
				assertEquals(23, interaction.getData().getSrcLocalPoint().getY(), 0.0001d);
				assertEquals(MouseButton.MIDDLE, interaction.getData().getButton());
				assertEquals(pane, interaction.getData().getSrcObject().get());
			}
		});
		pane.fireEvent(createMouseClickEvent(11, 23, MouseButton.MIDDLE, pane));
	}

	@Test
	void testDbleClickOnUnregWidget() throws CancelFSMException {
		final Pane pane = new Pane();
		interaction.registerToNodes(Collections.singletonList(pane));
		interaction.unregisterFromNodes(Collections.singletonList(pane));
		pane.fireEvent(createMouseClickEvent(11, 23, MouseButton.MIDDLE, pane));
		pane.fireEvent(createMouseClickEvent(11, 23, MouseButton.MIDDLE, pane));
		Mockito.verify(handler, Mockito.never()).fsmStarts();
	}

	@Test
	void testDbleClickReinitData() {
		interaction.processEvent(createMouseClickEvent(11, 23, MouseButton.MIDDLE, null));
		interaction.processEvent(createMouseClickEvent(11, 23, MouseButton.MIDDLE, null));
		assertNull(interaction.getData().getSrcLocalPoint());
		assertNull(interaction.getData().getButton());
	}

	@Test
	void testDbleClickNotSameButtonExecution() throws CancelFSMException {
		interaction.processEvent(createMouseClickEvent(11, 23, MouseButton.MIDDLE, null));
		interaction.processEvent(createMouseClickEvent(11, 23, MouseButton.PRIMARY, null));
		Mockito.verify(handler, Mockito.never()).fsmStarts();
		Mockito.verify(handler, Mockito.never()).fsmStops();
		Mockito.verify(handler, Mockito.never()).fsmCancels();
	}

	@Test
	void testDbleClickKOWithDelay() throws CancelFSMException {
		interaction.processEvent(createMouseClickEvent(11, 23, MouseButton.MIDDLE, null));
		JfxtestHelper.waitForTimeoutTransitions();
		interaction.processEvent(createMouseClickEvent(11, 23, MouseButton.MIDDLE, null));
		Mockito.verify(handler, Mockito.never()).fsmStarts();
		Mockito.verify(handler, Mockito.never()).fsmStops();
		Mockito.verify(handler, Mockito.never()).fsmCancels();
	}

	@Test
	void testDbleClickKOWithCustomDelay() throws CancelFSMException {
		DoubleClickFSM.setTimeGap(50L);
		interaction.processEvent(createMouseClickEvent(11, 23, MouseButton.MIDDLE, null));
		JfxtestHelper.waitForTimeoutTransitions();
		interaction.processEvent(createMouseClickEvent(11, 23, MouseButton.MIDDLE, null));
		Mockito.verify(handler, Mockito.never()).fsmStarts();
		Mockito.verify(handler, Mockito.never()).fsmStops();
		Mockito.verify(handler, Mockito.never()).fsmCancels();
	}

	@Test
	void testDbleClickKOWithCustomDelayButRecycled() throws CancelFSMException {
		DoubleClickFSM.setTimeGap(50L);
		interaction.processEvent(createMouseClickEvent(11, 23, MouseButton.MIDDLE, null));
		JfxtestHelper.waitForTimeoutTransitions();
		interaction.processEvent(createMouseClickEvent(11, 23, MouseButton.MIDDLE, null));
		interaction.processEvent(createMouseClickEvent(12, 24, MouseButton.MIDDLE, null));
		Mockito.verify(handler, Mockito.never()).fsmCancels();
		Mockito.verify(handler, Mockito.times(1)).fsmStarts();
		Mockito.verify(handler, Mockito.times(1)).fsmStops();
	}

	@Test
	void testDbleClickKOWithMove() throws CancelFSMException {
		interaction.processEvent(createMouseClickEvent(11, 23, MouseButton.MIDDLE, null));
		interaction.processEvent(createMouseMoveEvent(20, 40, MouseButton.MIDDLE));
		interaction.processEvent(createMouseClickEvent(20, 40, MouseButton.MIDDLE, null));
		Mockito.verify(handler, Mockito.never()).fsmStarts();
		Mockito.verify(handler, Mockito.never()).fsmStops();
		Mockito.verify(handler, Mockito.never()).fsmCancels();
	}

	@Test
	void testDbleClickKOWithMoveButRecycled() throws CancelFSMException {
		interaction.processEvent(createMouseClickEvent(11, 23, MouseButton.MIDDLE, null));
		interaction.processEvent(createMouseMoveEvent(20, 40, MouseButton.MIDDLE));
		interaction.processEvent(createMouseClickEvent(20, 40, MouseButton.MIDDLE, null));
		interaction.processEvent(createMouseClickEvent(22, 42, MouseButton.MIDDLE, null));
		Mockito.verify(handler, Mockito.times(1)).fsmStarts();
		Mockito.verify(handler, Mockito.times(1)).fsmStops();
	}

	@Test
	void testDbleClickOKWithBadMove() throws CancelFSMException {
		interaction.processEvent(createMouseClickEvent(11, 23, MouseButton.MIDDLE, null));
		interaction.processEvent(createMouseMoveEvent(20, 40, MouseButton.PRIMARY));
		interaction.processEvent(createMouseClickEvent(11, 23, MouseButton.MIDDLE, null));
		Mockito.verify(handler, Mockito.never()).fsmCancels();
		Mockito.verify(handler, Mockito.times(1)).fsmStarts();
		Mockito.verify(handler, Mockito.times(1)).fsmStops();
	}
}
