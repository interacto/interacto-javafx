package org.malai.javafx.interaction2.library;

import java.util.Collections;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.Pane;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.malai.fsm.CancelFSMException;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.assertEquals;
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
		interaction.processEvent(createMouseClickEvent(11, 23, MouseButton.MIDDLE));
		interaction.processEvent(createMouseClickEvent(11, 23, MouseButton.MIDDLE));
		Mockito.verify(handler, Mockito.times(1)).fsmStarts();
		Mockito.verify(handler, Mockito.times(1)).fsmStops();
	}

	@Test
	void testDbleClickData() {
		interaction.getFsm().setHandler(new InteractionHandlerStub() {
			@Override
			public void fsmStops() {
				assertEquals(11, interaction.getSrcLocalPoint().getX(), 0.0001d);
				assertEquals(23, interaction.getSrcLocalPoint().getY(), 0.0001d);
				assertEquals(MouseButton.MIDDLE, interaction.getButton());
			}
		});
		interaction.processEvent(createMouseClickEvent(11, 23, MouseButton.MIDDLE));
		interaction.processEvent(createMouseClickEvent(11, 23, MouseButton.MIDDLE));
	}

	@Test
	void testDbleClickOnWidget() {
		Pane pane = new Pane();
		interaction.registerToNodes(Collections.singletonList(pane));
		interaction.getFsm().setHandler(new InteractionHandlerStub() {
			@Override
			public void fsmStops() {
				assertEquals(11, interaction.getSrcLocalPoint().getX(), 0.0001d);
				assertEquals(23, interaction.getSrcLocalPoint().getY(), 0.0001d);
				assertEquals(MouseButton.MIDDLE, interaction.getButton());
			}
		});
		pane.fireEvent(createMouseClickEvent(11, 23, MouseButton.MIDDLE));
	}

	@Test
	void testDbleClickOnUnregWidget() throws CancelFSMException {
		Pane pane = new Pane();
		interaction.registerToNodes(Collections.singletonList(pane));
		interaction.unregisterFromNodes(Collections.singletonList(pane));
		pane.fireEvent(createMouseClickEvent(11, 23, MouseButton.MIDDLE));
		pane.fireEvent(createMouseClickEvent(11, 23, MouseButton.MIDDLE));
		Mockito.verify(handler, Mockito.never()).fsmStarts();
	}

	@Test
	void testDbleClickReinitData() {
		interaction.processEvent(createMouseClickEvent(11, 23, MouseButton.MIDDLE));
		interaction.processEvent(createMouseClickEvent(11, 23, MouseButton.MIDDLE));
		assertNull(interaction.getSrcLocalPoint());
		assertNull(interaction.getButton());
	}

	@Test
	void testDbleClickNotSameButtonExecution() throws CancelFSMException {
		interaction.processEvent(createMouseClickEvent(11, 23, MouseButton.MIDDLE));
		interaction.processEvent(createMouseClickEvent(11, 23, MouseButton.PRIMARY));
		Mockito.verify(handler, Mockito.times(1)).fsmStarts();
		Mockito.verify(handler, Mockito.never()).fsmStops();
		Mockito.verify(handler, Mockito.never()).fsmCancels();
	}

	@Test
	void testDbleClickKOWithDelay() throws CancelFSMException {
		interaction.processEvent(createMouseClickEvent(11, 23, MouseButton.MIDDLE));
		sleep(500L);
		interaction.processEvent(createMouseClickEvent(11, 23, MouseButton.MIDDLE));
		Mockito.verify(handler, Mockito.never()).fsmStops();
		Mockito.verify(handler, Mockito.times(2)).fsmStarts();
		Mockito.verify(handler, Mockito.times(1)).fsmCancels();
	}

	@Test
	void testDbleClickKOWithCustomDelay() throws CancelFSMException {
		DoubleClickFSM.setTimeGap(50L);
		interaction.processEvent(createMouseClickEvent(11, 23, MouseButton.MIDDLE));
		sleep(250L);
		interaction.processEvent(createMouseClickEvent(11, 23, MouseButton.MIDDLE));
		Mockito.verify(handler, Mockito.never()).fsmStops();
		Mockito.verify(handler, Mockito.times(2)).fsmStarts();
		Mockito.verify(handler, Mockito.times(1)).fsmCancels();
	}

	@Test
	void testDbleClickKOWithMove() throws CancelFSMException {
		interaction.processEvent(createMouseClickEvent(11, 23, MouseButton.MIDDLE));
		interaction.processEvent(createMouseMoveEvent(20, 40, MouseButton.MIDDLE));
		interaction.processEvent(createMouseClickEvent(20, 40, MouseButton.MIDDLE));
		Mockito.verify(handler, Mockito.never()).fsmStops();
		Mockito.verify(handler, Mockito.times(2)).fsmStarts();
		Mockito.verify(handler, Mockito.times(1)).fsmCancels();
	}

	@Test
	void testDbleClickOKWithBadMove() throws CancelFSMException {
		interaction.processEvent(createMouseClickEvent(11, 23, MouseButton.MIDDLE));
		interaction.processEvent(createMouseMoveEvent(20, 40, MouseButton.PRIMARY));
		interaction.processEvent(createMouseClickEvent(11, 23, MouseButton.MIDDLE));
		Mockito.verify(handler, Mockito.never()).fsmCancels();
		Mockito.verify(handler, Mockito.times(1)).fsmStarts();
		Mockito.verify(handler, Mockito.times(1)).fsmStops();
	}
}
