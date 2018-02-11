package org.malai.javafx.interaction2.library;

import java.util.Collections;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.Pane;
import org.junit.jupiter.api.Test;
import org.malai.fsm.CancelFSMException;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestDnD extends BaseJfXInteractionTest<DnD> {
	@Override
	DnD createInteraction() {
		return new DnD();
	}

	@Test
	void testPressExecution() throws CancelFSMException {
		interaction.processEvent(createMousePressEvent(11, 23, MouseButton.PRIMARY));
		Mockito.verify(handler, Mockito.times(1)).fsmStarts();
		Mockito.verify(handler, Mockito.never()).fsmStops();
		Mockito.verify(handler, Mockito.never()).fsmCancels();
	}

	@Test
	void testPressData() {
		interaction.getFsm().addHandler(new InteractionHandlerStub() {
			@Override
			public void fsmStarts() {
				assertEquals(11, interaction.getSrcLocalPoint().getX(), 0.0001d);
				assertEquals(23, interaction.getSrcLocalPoint().getY(), 0.0001d);
				assertEquals(11, interaction.getEndLocalPt().getX(), 0.0001d);
				assertEquals(23, interaction.getEndLocalPt().getY(), 0.0001d);
				assertEquals(MouseButton.PRIMARY, interaction.getButton());
			}
		});
		interaction.processEvent(createMousePressEvent(11, 23, MouseButton.PRIMARY));
	}

	@Test
	void testPressReleaseExecution() throws CancelFSMException {
		interaction.processEvent(createMousePressEvent(11, 23, MouseButton.PRIMARY));
		interaction.processEvent(createMouseReleaseEvent(11, 23, MouseButton.PRIMARY));
		Mockito.verify(handler, Mockito.times(1)).fsmStarts();
		Mockito.verify(handler, Mockito.times(1)).fsmCancels();
		Mockito.verify(handler, Mockito.never()).fsmStops();
	}

	@Test
	void testPressReleaseKOExecution() throws CancelFSMException {
		interaction.processEvent(createMousePressEvent(11, 23, MouseButton.PRIMARY));
		interaction.processEvent(createMouseReleaseEvent(11, 23, MouseButton.SECONDARY));
		Mockito.verify(handler, Mockito.times(1)).fsmStarts();
		Mockito.verify(handler, Mockito.never()).fsmStops();
		Mockito.verify(handler, Mockito.never()).fsmCancels();
	}

	@Test
	void testPressMoveExecution() throws CancelFSMException {
		interaction.processEvent(createMousePressEvent(11, 23, MouseButton.PRIMARY));
		interaction.processEvent(createMouseDragEvent(11, 23, MouseButton.PRIMARY));
		Mockito.verify(handler, Mockito.times(1)).fsmStarts();
		Mockito.verify(handler, Mockito.times(2)).fsmUpdates();
		Mockito.verify(handler, Mockito.never()).fsmStops();
		Mockito.verify(handler, Mockito.never()).fsmCancels();
	}

	@Test
	void testPressMoveData() {
		interaction.processEvent(createMousePressEvent(11, 23, MouseButton.SECONDARY));
		interaction.getFsm().addHandler(new InteractionHandlerStub() {
			@Override
			public void fsmUpdates() {
				assertEquals(11, interaction.getSrcLocalPoint().getX(), 0.0001d);
				assertEquals(23, interaction.getSrcLocalPoint().getY(), 0.0001d);
				assertEquals(12, interaction.getEndLocalPt().getX(), 0.0001d);
				assertEquals(24, interaction.getEndLocalPt().getY(), 0.0001d);
				assertEquals(MouseButton.SECONDARY, interaction.getButton());
			}
		});
		interaction.processEvent(createMouseDragEvent(12, 24, MouseButton.SECONDARY));
	}

	@Test
	void testPressMoveKOExecution() throws CancelFSMException {
		interaction.processEvent(createMousePressEvent(11, 23, MouseButton.PRIMARY));
		interaction.processEvent(createMouseDragEvent(12, 22, MouseButton.SECONDARY));
		Mockito.verify(handler, Mockito.times(1)).fsmStarts();
		Mockito.verify(handler, Mockito.times(1)).fsmUpdates();
		Mockito.verify(handler, Mockito.never()).fsmStops();
		Mockito.verify(handler, Mockito.never()).fsmCancels();
	}

	@Test
	void testPressMoveMoveKOExecution() throws CancelFSMException {
		interaction.processEvent(createMousePressEvent(11, 23, MouseButton.PRIMARY));
		interaction.processEvent(createMouseDragEvent(12, 22, MouseButton.PRIMARY));
		interaction.processEvent(createMouseDragEvent(14, 24, MouseButton.SECONDARY));
		Mockito.verify(handler, Mockito.times(1)).fsmStarts();
		Mockito.verify(handler, Mockito.times(2)).fsmUpdates();
		Mockito.verify(handler, Mockito.never()).fsmStops();
		Mockito.verify(handler, Mockito.never()).fsmCancels();
	}

	@Test
	void testPressMoveMoveExecution() throws CancelFSMException {
		interaction.processEvent(createMousePressEvent(11, 23, MouseButton.PRIMARY));
		interaction.processEvent(createMouseDragEvent(12, 22, MouseButton.PRIMARY));
		interaction.processEvent(createMouseDragEvent(14, 24, MouseButton.PRIMARY));
		Mockito.verify(handler, Mockito.times(1)).fsmStarts();
		Mockito.verify(handler, Mockito.times(3)).fsmUpdates();
		Mockito.verify(handler, Mockito.never()).fsmStops();
		Mockito.verify(handler, Mockito.never()).fsmCancels();
	}

	@Test
	void testPressMoveMoveData() {
		interaction.processEvent(createMousePressEvent(11, 23, MouseButton.MIDDLE));
		interaction.processEvent(createMouseDragEvent(12, 22, MouseButton.MIDDLE));
		interaction.getFsm().addHandler(new InteractionHandlerStub() {
			@Override
			public void fsmUpdates() {
				assertEquals(11, interaction.getSrcLocalPoint().getX(), 0.0001d);
				assertEquals(23, interaction.getSrcLocalPoint().getY(), 0.0001d);
				assertEquals(12, interaction.getEndLocalPt().getX(), 0.0001d);
				assertEquals(24, interaction.getEndLocalPt().getY(), 0.0001d);
				assertEquals(MouseButton.MIDDLE, interaction.getButton());
			}
		});
		interaction.processEvent(createMouseDragEvent(12, 24, MouseButton.MIDDLE));
	}

	@Test
	void testPressMoveReleaseExecution() throws CancelFSMException {
		interaction.processEvent(createMousePressEvent(11, 23, MouseButton.PRIMARY));
		interaction.processEvent(createMouseDragEvent(12, 22, MouseButton.PRIMARY));
		interaction.processEvent(createMouseReleaseEvent(12, 22, MouseButton.PRIMARY));
		Mockito.verify(handler, Mockito.times(1)).fsmStarts();
		Mockito.verify(handler, Mockito.times(2)).fsmUpdates();
		Mockito.verify(handler, Mockito.times(1)).fsmStops();
		Mockito.verify(handler, Mockito.never()).fsmCancels();
	}

	@Test
	void testPressMoveReleaseData() {
		interaction.getFsm().addHandler(new InteractionHandlerStub() {
			@Override
			public void fsmStops() {
				assertEquals(11, interaction.getSrcLocalPoint().getX(), 0.0001d);
				assertEquals(23, interaction.getSrcLocalPoint().getY(), 0.0001d);
				assertEquals(15, interaction.getEndLocalPt().getX(), 0.0001d);
				assertEquals(25, interaction.getEndLocalPt().getY(), 0.0001d);
				assertEquals(MouseButton.PRIMARY, interaction.getButton());
			}
		});
		interaction.processEvent(createMousePressEvent(11, 23, MouseButton.PRIMARY));
		interaction.processEvent(createMouseDragEvent(12, 22, MouseButton.PRIMARY));
		interaction.processEvent(createMouseReleaseEvent(15, 25, MouseButton.PRIMARY));
	}

	@Test
	void testPressMoveReleaseKOExecution() throws CancelFSMException {
		interaction.processEvent(createMousePressEvent(11, 23, MouseButton.PRIMARY));
		interaction.processEvent(createMouseDragEvent(12, 22, MouseButton.PRIMARY));
		interaction.processEvent(createMouseReleaseEvent(12, 22, MouseButton.SECONDARY));
		Mockito.verify(handler, Mockito.times(1)).fsmStarts();
		Mockito.verify(handler, Mockito.times(2)).fsmUpdates();
		Mockito.verify(handler, Mockito.never()).fsmCancels();
		Mockito.verify(handler, Mockito.never()).fsmStops();
	}

	@Test
	void testPressMoveMoveReleaseExecution() throws CancelFSMException {
		interaction.processEvent(createMousePressEvent(11, 23, MouseButton.PRIMARY));
		interaction.processEvent(createMouseDragEvent(14, 24, MouseButton.PRIMARY));
		interaction.processEvent(createMouseDragEvent(12, 22, MouseButton.PRIMARY));
		interaction.processEvent(createMouseReleaseEvent(12, 22, MouseButton.PRIMARY));
		Mockito.verify(handler, Mockito.times(1)).fsmStarts();
		Mockito.verify(handler, Mockito.times(3)).fsmUpdates();
		Mockito.verify(handler, Mockito.times(1)).fsmStops();
		Mockito.verify(handler, Mockito.never()).fsmCancels();
	}

	@Test
	void testPressMoveMoveReleaseData() {
		interaction.getFsm().addHandler(new InteractionHandlerStub() {
			@Override
			public void fsmStops() {
				assertEquals(11, interaction.getSrcLocalPoint().getX(), 0.0001d);
				assertEquals(23, interaction.getSrcLocalPoint().getY(), 0.0001d);
				assertEquals(15, interaction.getEndLocalPt().getX(), 0.0001d);
				assertEquals(25, interaction.getEndLocalPt().getY(), 0.0001d);
				assertEquals(MouseButton.PRIMARY, interaction.getButton());
			}
		});
		interaction.processEvent(createMousePressEvent(11, 23, MouseButton.PRIMARY));
		interaction.processEvent(createMouseDragEvent(12, 22, MouseButton.PRIMARY));
		interaction.processEvent(createMouseReleaseEvent(15, 25, MouseButton.PRIMARY));
	}

	@Test
	void testDnDOnRegWidget() throws CancelFSMException {
		Pane pane = new Pane();
		interaction.registerToNodes(Collections.singletonList(pane));
		pane.fireEvent(createMousePressEvent(11, 23, MouseButton.PRIMARY));
		Mockito.verify(handler, Mockito.times(1)).fsmStarts();
		Mockito.verify(handler, Mockito.times(1)).fsmUpdates();
	}

	@Test
	void testDnDOnUnRegWidgetKO() throws CancelFSMException {
		Pane pane = new Pane();
		interaction.registerToNodes(Collections.singletonList(pane));
		interaction.unregisterFromNodes(Collections.singletonList(pane));
		pane.fireEvent(createMousePressEvent(11, 23, MouseButton.PRIMARY));
		Mockito.verify(handler, Mockito.never()).fsmStarts();
	}

	@Test
	void testNewKindEventRegistered() throws CancelFSMException {
		Pane pane = new Pane();
		interaction.registerToNodes(Collections.singletonList(pane));
		pane.fireEvent(createMousePressEvent(11, 23, MouseButton.PRIMARY));
		pane.fireEvent(createMouseDragEvent(12, 25, MouseButton.PRIMARY));
		Mockito.verify(handler, Mockito.times(2)).fsmUpdates();
	}

	@Test
	void testNewKindEvent2Registered() throws CancelFSMException {
		Pane pane = new Pane();
		interaction.registerToNodes(Collections.singletonList(pane));
		pane.fireEvent(createMousePressEvent(11, 23, MouseButton.PRIMARY));
		pane.fireEvent(createMouseDragEvent(12, 25, MouseButton.PRIMARY));
		pane.fireEvent(createMouseReleaseEvent(12, 25, MouseButton.PRIMARY));
		Mockito.verify(handler, Mockito.times(1)).fsmStops();
	}

	@Test
	void testNewKindEventOnTerminalRegistered() throws CancelFSMException {
		Pane pane = new Pane();
		interaction.registerToNodes(Collections.singletonList(pane));
		pane.fireEvent(createMousePressEvent(11, 23, MouseButton.PRIMARY));
		pane.fireEvent(createMouseDragEvent(12, 25, MouseButton.PRIMARY));
		pane.fireEvent(createMouseReleaseEvent(12, 25, MouseButton.PRIMARY));
		pane.fireEvent(createMousePressEvent(11, 23, MouseButton.PRIMARY));
		Mockito.verify(handler, Mockito.times(2)).fsmStarts();
	}
}
