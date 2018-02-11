package org.malai.javafx.interaction2.library;

import java.util.Collections;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.Pane;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.malai.fsm.CancelFSMException;
import org.malai.fsm.FSMHandler;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TestMultiClick extends BaseJfXInteractionTest<MultiClick> {
	@Override
	MultiClick createInteraction() {
		return new MultiClick();
	}

	@Test
	void testClickStarts() throws CancelFSMException {
		interaction.processEvent(createMouseClickEvent(11, 23, MouseButton.PRIMARY));
		Mockito.verify(handler, Mockito.times(1)).fsmStarts();
	}

	@Test
	void testClickStartsData() {
		interaction.getFsm().addHandler(new InteractionHandlerStub() {
			@Override
			public void fsmStops() {
				assertEquals(1, interaction.getPoints().size());
				assertEquals(11, interaction.getPoints().get(0).getX(), 0.0001d);
				assertEquals(23, interaction.getPoints().get(0).getY(), 0.0001d);
				assertEquals(0, interaction.getPoints().get(0).getZ(), 0.0001d);
			}
		});
		interaction.processEvent(createMouseClickEvent(11, 23, MouseButton.PRIMARY));
	}

	@Test
	void testClickThenLeftClickDoNotEnds() throws CancelFSMException {
		interaction.processEvent(createMouseClickEvent(11, 23, MouseButton.PRIMARY));
		interaction.processEvent(createMouseClickEvent(11, 23, MouseButton.SECONDARY));
		Mockito.verify(handler, Mockito.times(1)).fsmStarts();
		Mockito.verify(handler, Mockito.never()).fsmStops();
	}

	@Test
	void testClickThenLeftClickData() {
		interaction.getFsm().addHandler(new InteractionHandlerStub() {
			@Override
			public void fsmStops() {
				assertEquals(1, interaction.getPoints().size());
				assertEquals(11, interaction.getPoints().get(0).getX(), 0.0001d);
				assertEquals(23, interaction.getPoints().get(0).getY(), 0.0001d);
				assertEquals(0, interaction.getPoints().get(0).getZ(), 0.0001d);
			}
		});
		interaction.processEvent(createMouseClickEvent(11, 23, MouseButton.PRIMARY));
		interaction.processEvent(createMouseClickEvent(121, 213, MouseButton.SECONDARY));
	}

	@Test
	void testTwoClicksThenLeftClickEnds() throws CancelFSMException {
		interaction.processEvent(createMouseClickEvent(11, 23, MouseButton.PRIMARY));
		interaction.processEvent(createMouseClickEvent(11, 23, MouseButton.PRIMARY));
		interaction.processEvent(createMouseClickEvent(11, 23, MouseButton.SECONDARY));
		Mockito.verify(handler, Mockito.times(1)).fsmStarts();
		Mockito.verify(handler, Mockito.times(1)).fsmStops();
	}

	@Test
	void testTwoClicksThenLeftClickData() {
		interaction.getFsm().addHandler(new InteractionHandlerStub() {
			@Override
			public void fsmStops() {
				assertEquals(3, interaction.getPoints().size());
				assertEquals(11, interaction.getPoints().get(0).getX(), 0.0001d);
				assertEquals(23, interaction.getPoints().get(0).getY(), 0.0001d);
				assertEquals(111, interaction.getPoints().get(1).getX(), 0.0001d);
				assertEquals(231, interaction.getPoints().get(1).getY(), 0.0001d);
				assertEquals(112, interaction.getPoints().get(2).getX(), 0.0001d);
				assertEquals(233, interaction.getPoints().get(2).getY(), 0.0001d);
			}
		});
		interaction.processEvent(createMouseClickEvent(11, 23, MouseButton.PRIMARY));
		interaction.processEvent(createMouseClickEvent(111, 231, MouseButton.PRIMARY));
		interaction.processEvent(createMouseClickEvent(112, 233, MouseButton.SECONDARY));
	}

	@Test
	void testReinitData() {
		interaction.processEvent(createMouseClickEvent(11, 23, MouseButton.PRIMARY));
		interaction.processEvent(createMouseClickEvent(111, 231, MouseButton.PRIMARY));
		interaction.processEvent(createMouseClickEvent(112, 233, MouseButton.SECONDARY));
		assertTrue(interaction.getPoints().isEmpty());
		assertNull(interaction.getCurrentPosition());
	}

	@Test
	void testThreeClicksThenLeftClickEnds() throws CancelFSMException {
		interaction.processEvent(createMouseClickEvent(11, 23, MouseButton.PRIMARY));
		interaction.processEvent(createMouseClickEvent(11, 23, MouseButton.PRIMARY));
		interaction.processEvent(createMouseClickEvent(11, 23, MouseButton.PRIMARY));
		interaction.processEvent(createMouseClickEvent(11, 23, MouseButton.SECONDARY));
		Mockito.verify(handler, Mockito.times(1)).fsmStarts();
		Mockito.verify(handler, Mockito.times(1)).fsmStops();
	}

	@Test
	void testClicksThenMoves() {
		interaction.processEvent(createMouseClickEvent(11, 23, MouseButton.PRIMARY));
		interaction.processEvent(createMouseClickEvent(111, 213, MouseButton.PRIMARY));
		interaction.getFsm().addHandler(new InteractionHandlerStub() {
			@Override
			public void fsmUpdates() {
				assertEquals(2, interaction.getPoints().size());
				assertEquals(112, interaction.getCurrentPosition().getX(), 0.0001d);
				assertEquals(233, interaction.getCurrentPosition().getY(), 0.0001d);
			}
		});
		interaction.processEvent(createMouseMoveEvent(112, 233, MouseButton.PRIMARY));
	}

	@Test
	void testClicksThenMovesThenClickThenMove() {
		interaction.processEvent(createMouseClickEvent(11, 23, MouseButton.PRIMARY));
		interaction.processEvent(createMouseClickEvent(111, 213, MouseButton.PRIMARY));
		interaction.processEvent(createMouseMoveEvent(112, 233, MouseButton.PRIMARY));
		interaction.processEvent(createMouseClickEvent(11, 23, MouseButton.PRIMARY));
		interaction.getFsm().addHandler(new InteractionHandlerStub() {
			@Override
			public void fsmUpdates() {
				assertEquals(3, interaction.getPoints().size());
				assertEquals(1112, interaction.getCurrentPosition().getX(), 0.0001d);
				assertEquals(2343, interaction.getCurrentPosition().getY(), 0.0001d);
			}
		});
		interaction.processEvent(createMouseMoveEvent(1112, 2343, MouseButton.PRIMARY));
	}

	@Test
	void testClickESCCancels() throws CancelFSMException {
		interaction.processEvent(createMouseClickEvent(11, 23, MouseButton.PRIMARY));
		interaction.processEvent(createKeyPressEvent("ESC", KeyCode.ESCAPE));
		Mockito.verify(handler, Mockito.times(1)).fsmStarts();
		Mockito.verify(handler, Mockito.times(1)).fsmCancels();
		Mockito.verify(handler, Mockito.never()).fsmStops();
	}

	@Test
	void testTwoClicksESCCancels() throws CancelFSMException {
		interaction.processEvent(createMouseClickEvent(11, 23, MouseButton.PRIMARY));
		interaction.processEvent(createMouseClickEvent(21, 33, MouseButton.PRIMARY));
		interaction.processEvent(createKeyPressEvent("ESC", KeyCode.ESCAPE));
		Mockito.verify(handler, Mockito.times(1)).fsmStarts();
		Mockito.verify(handler, Mockito.times(1)).fsmCancels();
		Mockito.verify(handler, Mockito.never()).fsmStops();
	}

	@Test
	void testNbMinPtsKO() {
		final MultiClick multiClick = new MultiClick(0);
		assertEquals(2, multiClick.getFsm().getMinPoints());
	}

	@Test
	void testMultiClickOnRegWidget() throws CancelFSMException {
		Pane pane = new Pane();
		interaction.registerToNodes(Collections.singletonList(pane));
		pane.fireEvent(createMouseClickEvent(11, 23, MouseButton.PRIMARY));
		Mockito.verify(handler, Mockito.times(1)).fsmStarts();
	}

	@Test
	void testMultiClickOnRegUnregWidgetKO() throws CancelFSMException {
		Pane pane = new Pane();
		interaction.registerToNodes(Collections.singletonList(pane));
		interaction.unregisterFromNodes(Collections.singletonList(pane));
		pane.fireEvent(createMouseClickEvent(11, 23, MouseButton.PRIMARY));
		Mockito.verify(handler, Mockito.never()).fsmStarts();
	}

	@Nested
	class CustomMinPtsTest {
		@BeforeEach
		void setUp() {
			interaction = new MultiClick(3);
			handler = Mockito.mock(FSMHandler.class);
			interaction.getFsm().addHandler(handler);
		}

		@Test
		void testTwoClicksThenLeftClickDoesNotEnd() throws CancelFSMException {
			interaction.processEvent(createMouseClickEvent(11, 23, MouseButton.PRIMARY));
			interaction.processEvent(createMouseClickEvent(11, 23, MouseButton.SECONDARY));
			Mockito.verify(handler, Mockito.times(1)).fsmStarts();
			Mockito.verify(handler, Mockito.never()).fsmStops();
			Mockito.verify(handler, Mockito.never()).fsmCancels();
		}

		@Test
		void testThreeClicksThenLeftClickDoesNotEnd() throws CancelFSMException {
			interaction.processEvent(createMouseClickEvent(111, 2223, MouseButton.PRIMARY));
			interaction.processEvent(createMouseClickEvent(121, 243, MouseButton.PRIMARY));
			interaction.processEvent(createMouseClickEvent(11, 23, MouseButton.SECONDARY));
			Mockito.verify(handler, Mockito.times(1)).fsmStarts();
			Mockito.verify(handler, Mockito.times(1)).fsmStops();
			Mockito.verify(handler, Mockito.never()).fsmCancels();
		}
	}
}
