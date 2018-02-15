package org.malai.javafx.interaction.library;

import java.util.Collections;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.Pane;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.malai.fsm.CancelFSMException;
import org.malai.javafx.interaction.JfxFSM;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

public class TestDoubleClick extends BaseJfXInteractionTest<DoubleClick> {
	@Override
	DoubleClick createInteraction() {
		return new DoubleClick();
	}

	@BeforeAll
	static void beforeAll() {
		DoubleClickFSM.setTimeGap(300L);
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
				assertEquals(11, interaction.getClickData().getSrcLocalPoint().getX(), 0.0001d);
				assertEquals(23, interaction.getClickData().getSrcLocalPoint().getY(), 0.0001d);
				assertEquals(MouseButton.MIDDLE, interaction.getClickData().getButton());
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
		assertNotNull(((JfxFSM<?>)((Click) interaction.getClickData()).getFsm()).getDataHandler());
	}

	@Test
	void testDbleClickOnWidgetOKOnStarts() {
		Pane pane = new Pane();
		interaction.registerToNodes(Collections.singletonList(pane));
		interaction.getFsm().addHandler(new InteractionHandlerStub() {
			@Override
			public void fsmStarts() {
				assertEquals(11, interaction.getClickData().getSrcLocalPoint().getX(), 0.0001d);
				assertEquals(23, interaction.getClickData().getSrcLocalPoint().getY(), 0.0001d);
				assertEquals(MouseButton.MIDDLE, interaction.getClickData().getButton());
				assertEquals(pane, interaction.getClickData().getSrcObject().get());
			}
		});
		pane.fireEvent(createMouseClickEvent(11, 23, MouseButton.MIDDLE, pane));
	}

	@Test
	void testDbleClickOnWidgetOkOnStops() {
		Pane pane = new Pane();
		interaction.registerToNodes(Collections.singletonList(pane));
		interaction.getFsm().addHandler(new InteractionHandlerStub() {
			@Override
			public void fsmStops() {
				assertEquals(11, interaction.getClickData().getSrcLocalPoint().getX(), 0.0001d);
				assertEquals(23, interaction.getClickData().getSrcLocalPoint().getY(), 0.0001d);
				assertEquals(MouseButton.MIDDLE, interaction.getClickData().getButton());
				assertEquals(pane, interaction.getClickData().getSrcObject().get());
			}
		});
		pane.fireEvent(createMouseClickEvent(11, 23, MouseButton.MIDDLE, pane));
	}

	@Test
	void testDbleClickOnUnregWidget() throws CancelFSMException {
		Pane pane = new Pane();
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
		assertNull(interaction.getClickData().getSrcLocalPoint());
		assertNull(interaction.getClickData().getButton());
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
		sleep(500L);
		interaction.processEvent(createMouseClickEvent(11, 23, MouseButton.MIDDLE, null));
		Mockito.verify(handler, Mockito.never()).fsmStarts();
		Mockito.verify(handler, Mockito.never()).fsmStops();
		Mockito.verify(handler, Mockito.never()).fsmCancels();
	}

	@Test
	void testDbleClickKOWithCustomDelay() throws CancelFSMException {
		DoubleClickFSM.setTimeGap(50L);
		interaction.processEvent(createMouseClickEvent(11, 23, MouseButton.MIDDLE, null));
		sleep(250L);
		interaction.processEvent(createMouseClickEvent(11, 23, MouseButton.MIDDLE, null));
		Mockito.verify(handler, Mockito.never()).fsmStarts();
		Mockito.verify(handler, Mockito.never()).fsmStops();
		Mockito.verify(handler, Mockito.never()).fsmCancels();
	}

	@Test
	void testDbleClickKOWithCustomDelayButRecycled() throws CancelFSMException {
		DoubleClickFSM.setTimeGap(50L);
		interaction.processEvent(createMouseClickEvent(11, 23, MouseButton.MIDDLE, null));
		sleep(250L);
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
