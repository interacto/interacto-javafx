package io.interacto.jfx.interaction.library;

import io.interacto.fsm.CancelFSMException;
import io.interacto.fsm.FSMHandler;
import java.util.Collections;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.Pane;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.testfx.framework.junit5.ApplicationExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(ApplicationExtension.class)
public class TestMultiClick extends BaseJfXInteractionTest<MultiClick> {
	@Override
	MultiClick createInteraction() {
		return new MultiClick();
	}

	@Test
	void testClickStarts() throws CancelFSMException {
		interaction.processEvent(createMouseClickEvent(11, 23, MouseButton.PRIMARY, null));
		Mockito.verify(handler, Mockito.times(1)).fsmStarts();
	}

	@Test
	void testClickStartsData() {
		interaction.getFsm().addHandler(new InteractionHandlerStub() {
			@Override
			public void fsmStops() {
				assertEquals(1, interaction.getPointsData().size());
				assertEquals(11, interaction.getPointsData().get(0).getSrcLocalPoint().getX(), 0.0001d);
				assertEquals(23, interaction.getPointsData().get(0).getSrcLocalPoint().getY(), 0.0001d);
				assertEquals(0, interaction.getPointsData().get(0).getSrcLocalPoint().getZ(), 0.0001d);
			}
		});
		interaction.processEvent(createMouseClickEvent(11, 23, MouseButton.PRIMARY, null));
	}

	@Test
	void testClickThenLeftClickDoNotEnds() throws CancelFSMException {
		interaction.processEvent(createMouseClickEvent(11, 23, MouseButton.PRIMARY, null));
		interaction.processEvent(createMouseClickEvent(11, 23, MouseButton.SECONDARY, null));
		Mockito.verify(handler, Mockito.times(1)).fsmStarts();
		Mockito.verify(handler, Mockito.times(1)).fsmStops();
	}

	@Test
	void testClickThenLeftClickData() {
		interaction.getFsm().addHandler(new InteractionHandlerStub() {
			@Override
			public void fsmStops() {
				assertEquals(2, interaction.getPointsData().size());
				assertEquals(11, interaction.getPointsData().get(0).getSrcLocalPoint().getX(), 0.0001d);
				assertEquals(23, interaction.getPointsData().get(0).getSrcLocalPoint().getY(), 0.0001d);
				assertEquals(0, interaction.getPointsData().get(0).getSrcLocalPoint().getZ(), 0.0001d);
				assertEquals(121, interaction.getPointsData().get(1).getSrcLocalPoint().getX(), 0.0001d);
				assertEquals(213, interaction.getPointsData().get(1).getSrcLocalPoint().getY(), 0.0001d);
				assertEquals(0, interaction.getPointsData().get(1).getSrcLocalPoint().getZ(), 0.0001d);
			}
		});
		interaction.processEvent(createMouseClickEvent(11, 23, MouseButton.PRIMARY, null));
		interaction.processEvent(createMouseClickEvent(121, 213, MouseButton.SECONDARY, null));
	}

	@Test
	void testTwoClicksThenLeftClickEnds() throws CancelFSMException {
		interaction.processEvent(createMouseClickEvent(11, 23, MouseButton.PRIMARY, null));
		interaction.processEvent(createMouseClickEvent(11, 23, MouseButton.PRIMARY, null));
		interaction.processEvent(createMouseClickEvent(11, 23, MouseButton.SECONDARY, null));
		Mockito.verify(handler, Mockito.times(1)).fsmStarts();
		Mockito.verify(handler, Mockito.times(1)).fsmStops();
	}

	@Test
	void testTwoClicksThenLeftClickData() {
		interaction.getFsm().addHandler(new InteractionHandlerStub() {
			@Override
			public void fsmStops() {
				assertEquals(3, interaction.getPointsData().size());
				assertEquals(11, interaction.getPointsData().get(0).getSrcLocalPoint().getX(), 0.0001d);
				assertEquals(23, interaction.getPointsData().get(0).getSrcLocalPoint().getY(), 0.0001d);
				assertEquals(111, interaction.getPointsData().get(1).getSrcLocalPoint().getX(), 0.0001d);
				assertEquals(231, interaction.getPointsData().get(1).getSrcLocalPoint().getY(), 0.0001d);
				assertEquals(112, interaction.getPointsData().get(2).getSrcLocalPoint().getX(), 0.0001d);
				assertEquals(233, interaction.getPointsData().get(2).getSrcLocalPoint().getY(), 0.0001d);
			}
		});
		interaction.processEvent(createMouseClickEvent(11, 23, MouseButton.PRIMARY, null));
		interaction.processEvent(createMouseClickEvent(111, 231, MouseButton.PRIMARY, null));
		interaction.processEvent(createMouseClickEvent(112, 233, MouseButton.SECONDARY, null));
	}

	@Test
	void testReinitData() {
		interaction.processEvent(createMouseClickEvent(11, 23, MouseButton.PRIMARY, null));
		interaction.processEvent(createMouseClickEvent(111, 231, MouseButton.PRIMARY, null));
		interaction.processEvent(createMouseClickEvent(112, 233, MouseButton.SECONDARY, null));
		assertTrue(interaction.getPointsData().isEmpty());
		assertNull(interaction.getCurrentPosition());
	}

	@Test
	void testThreeClicksThenLeftClickEnds() throws CancelFSMException {
		interaction.processEvent(createMouseClickEvent(11, 23, MouseButton.PRIMARY, null));
		interaction.processEvent(createMouseClickEvent(11, 23, MouseButton.PRIMARY, null));
		interaction.processEvent(createMouseClickEvent(11, 23, MouseButton.PRIMARY, null));
		interaction.processEvent(createMouseClickEvent(11, 23, MouseButton.SECONDARY, null));
		Mockito.verify(handler, Mockito.times(1)).fsmStarts();
		Mockito.verify(handler, Mockito.times(1)).fsmStops();
	}

	@Test
	void testClicksThenMoves() {
		interaction.processEvent(createMouseClickEvent(11, 23, MouseButton.PRIMARY, null));
		interaction.processEvent(createMouseClickEvent(111, 213, MouseButton.PRIMARY, null));
		interaction.getFsm().addHandler(new InteractionHandlerStub() {
			@Override
			public void fsmUpdates() {
				assertEquals(2, interaction.getPointsData().size());
				assertEquals(112, interaction.getCurrentPosition().getX(), 0.0001d);
				assertEquals(233, interaction.getCurrentPosition().getY(), 0.0001d);
			}
		});
		interaction.processEvent(createMouseMoveEvent(112, 233, MouseButton.PRIMARY));
	}

	@Test
	void testClicksThenMovesThenClickThenMove() {
		interaction.processEvent(createMouseClickEvent(11, 23, MouseButton.PRIMARY, null));
		interaction.processEvent(createMouseClickEvent(111, 213, MouseButton.PRIMARY, null));
		interaction.processEvent(createMouseMoveEvent(112, 233, MouseButton.PRIMARY));
		interaction.processEvent(createMouseClickEvent(11, 23, MouseButton.PRIMARY, null));
		interaction.getFsm().addHandler(new InteractionHandlerStub() {
			@Override
			public void fsmUpdates() {
				assertEquals(3, interaction.getPointsData().size());
				assertEquals(1112, interaction.getCurrentPosition().getX(), 0.0001d);
				assertEquals(2343, interaction.getCurrentPosition().getY(), 0.0001d);
			}
		});
		interaction.processEvent(createMouseMoveEvent(1112, 2343, MouseButton.PRIMARY));
	}

	@Test
	void testClickESCCancels() throws CancelFSMException {
		interaction.processEvent(createMouseClickEvent(11, 23, MouseButton.PRIMARY, null));
		interaction.processEvent(createKeyPressEvent("ESC", KeyCode.ESCAPE));
		Mockito.verify(handler, Mockito.times(1)).fsmStarts();
		Mockito.verify(handler, Mockito.times(1)).fsmCancels();
		Mockito.verify(handler, Mockito.never()).fsmStops();
	}

	@Test
	void testTwoClicksESCCancels() throws CancelFSMException {
		interaction.processEvent(createMouseClickEvent(11, 23, MouseButton.PRIMARY, null));
		interaction.processEvent(createMouseClickEvent(21, 33, MouseButton.PRIMARY, null));
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
		final Pane pane = new Pane();
		interaction.registerToNodes(Collections.singletonList(pane));
		pane.fireEvent(createMouseClickEvent(11, 23, MouseButton.PRIMARY, null));
		Mockito.verify(handler, Mockito.times(1)).fsmStarts();
	}

	@Test
	void testMultiClickOnRegUnregWidgetKO() throws CancelFSMException {
		final Pane pane = new Pane();
		interaction.registerToNodes(Collections.singletonList(pane));
		interaction.unregisterFromNodes(Collections.singletonList(pane));
		pane.fireEvent(createMouseClickEvent(11, 23, MouseButton.PRIMARY, null));
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
			interaction.processEvent(createMouseClickEvent(11, 23, MouseButton.PRIMARY, null));
			interaction.processEvent(createMouseClickEvent(11, 23, MouseButton.SECONDARY, null));
			Mockito.verify(handler, Mockito.times(1)).fsmStarts();
			Mockito.verify(handler, Mockito.never()).fsmStops();
			Mockito.verify(handler, Mockito.never()).fsmCancels();
		}

		@Test
		void testThreeClicksThenLeftClickDoesNotEnd() throws CancelFSMException {
			interaction.processEvent(createMouseClickEvent(111, 2223, MouseButton.PRIMARY, null));
			interaction.processEvent(createMouseClickEvent(121, 243, MouseButton.PRIMARY, null));
			interaction.processEvent(createMouseClickEvent(11, 23, MouseButton.SECONDARY, null));
			Mockito.verify(handler, Mockito.times(1)).fsmStarts();
			Mockito.verify(handler, Mockito.times(1)).fsmStops();
			Mockito.verify(handler, Mockito.never()).fsmCancels();
		}
	}
}
