package org.malai.javafx.interaction.library;

import java.util.Collections;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.malai.fsm.CancelFSMException;
import org.mockito.Mockito;
import org.testfx.framework.junit5.ApplicationExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(ApplicationExtension.class)
public class TestKeysScroll extends BaseJfXInteractionTest<KeysScroll> {
	@Override
	KeysScroll createInteraction() {
		return new KeysScroll();
	}

	@Test
	public void testKeyPressExecution() throws CancelFSMException {
		interaction.processEvent(createKeyPressEvent("A", KeyCode.A));
		Mockito.verify(handler, Mockito.never()).fsmStops();
		Mockito.verify(handler, Mockito.times(1)).fsmStarts();
	}

	@Test
	void testKeyPressData() {
		interaction.getFsm().addHandler(new InteractionHandlerStub() {
			@Override
			public void fsmUpdates() {
				assertEquals(1, interaction.getKeys().size());
				assertEquals("A", interaction.getKeys().get(0));
			}
		});
		interaction.processEvent(createKeyPressEvent("A", KeyCode.A));
	}

	@Test
	public void testTwoKeyPressExecution() throws CancelFSMException {
		interaction.processEvent(createKeyPressEvent("A", KeyCode.A));
		interaction.processEvent(createKeyPressEvent("B", KeyCode.B));
		Mockito.verify(handler, Mockito.never()).fsmStops();
		Mockito.verify(handler, Mockito.times(1)).fsmStarts();
		Mockito.verify(handler, Mockito.times(2)).fsmUpdates();
	}

	@Test
	void testTwoKeyPressData() {
		interaction.processEvent(createKeyPressEvent("A", KeyCode.A));
		interaction.getFsm().addHandler(new InteractionHandlerStub() {
			@Override
			public void fsmUpdates() {
				assertEquals(2, interaction.getKeys().size());
				assertEquals("A", interaction.getKeys().get(0));
				assertEquals("B", interaction.getKeys().get(1));
			}
		});
		interaction.processEvent(createKeyPressEvent("B", KeyCode.B));
	}

	@Test
	public void testKeyPressReleaseExecution() throws CancelFSMException {
		interaction.processEvent(createKeyPressEvent("B", KeyCode.B));
		interaction.processEvent(createKeyReleaseEvent("B", KeyCode.B));
		Mockito.verify(handler, Mockito.times(1)).fsmCancels();
		Mockito.verify(handler, Mockito.never()).fsmStops();
	}

	@Test
	public void testTwoKeyPressReleaseDataCleaned() {
		interaction.processEvent(createKeyPressEvent("B", KeyCode.B));
		interaction.processEvent(createKeyReleaseEvent("B", KeyCode.B));
		assertTrue(interaction.getKeyCodes().isEmpty());
		assertTrue(interaction.getKeys().isEmpty());
	}

	@Test
	public void testTwoKeyPressReleaseExecution() throws CancelFSMException {
		interaction.processEvent(createKeyPressEvent("B", KeyCode.B));
		interaction.processEvent(createKeyPressEvent("A", KeyCode.A));
		interaction.processEvent(createKeyReleaseEvent("B", KeyCode.B));
		Mockito.verify(handler, Mockito.times(1)).fsmStarts();
		Mockito.verify(handler, Mockito.times(3)).fsmUpdates();
		Mockito.verify(handler, Mockito.never()).fsmStops();
		Mockito.verify(handler, Mockito.never()).fsmCancels();
	}

	@Test
	public void testScroll() throws CancelFSMException {
		interaction.processEvent(createScrollEvent(10, 20, 1, 0));
		Mockito.verify(handler, Mockito.times(1)).fsmStarts();
		Mockito.verify(handler, Mockito.times(1)).fsmStops();
		Mockito.verify(handler, Mockito.never()).fsmCancels();
	}

	@Test
	public void testKeyPressScroll() throws CancelFSMException {
		interaction.processEvent(createKeyPressEvent("B", KeyCode.B));
		interaction.processEvent(createScrollEvent(10, 20, 1, 0));
		Mockito.verify(handler, Mockito.times(1)).fsmStops();
		Mockito.verify(handler, Mockito.never()).fsmCancels();
	}

	@Test
	public void testKeyPressScrollData() {
		interaction.getFsm().addHandler(new InteractionHandlerStub() {
			@Override
			public void fsmStops() {
				assertEquals(1, interaction.getIncrement(), 0.0001);
				assertEquals(10, interaction.getPx(), 0.0001);
				assertEquals(20, interaction.getPy(), 0.0001);
			}
		});
		interaction.processEvent(createKeyPressEvent("B", KeyCode.B));
		interaction.processEvent(createScrollEvent(10, 20, 1, 1));
	}

	@Test
	public void testKeyPressScrollRecycle() throws CancelFSMException {
		interaction.processEvent(createKeyPressEvent("B", KeyCode.B));
		interaction.processEvent(createScrollEvent(10, 20, 1, 0));
		Mockito.verify(handler, Mockito.times(2)).fsmStarts();
		assertEquals(1, interaction.getKeyCodes().size());
	}

	@Test
	public void testKeyPressPressReleaseScroll() throws CancelFSMException {
		interaction.processEvent(createKeyPressEvent("B", KeyCode.B));
		interaction.processEvent(createKeyPressEvent("C", KeyCode.C));
		interaction.processEvent(createKeyReleaseEvent("B", KeyCode.B));
		interaction.processEvent(createScrollEvent(10, 20, 1, 0));
		Mockito.verify(handler, Mockito.times(1)).fsmStops();
		Mockito.verify(handler, Mockito.never()).fsmCancels();
	}

	@Test
	void testKeyPressOnRegWidget() throws CancelFSMException {
		final Pane pane = new Pane();
		interaction.registerToNodes(Collections.singletonList(pane));
		pane.fireEvent(createKeyPressEvent("A", KeyCode.A));
		Mockito.verify(handler, Mockito.times(1)).fsmStarts();
	}

	@Test
	void testKeyTypedOnRegWidgetData() {
		final Pane pane = new Pane();
		interaction.registerToNodes(Collections.singletonList(pane));
		interaction.getFsm().addHandler(new InteractionHandlerStub() {
			@Override
			public void fsmStops() {
				assertEquals("A", interaction.getKeys().get(0));
			}
		});
		pane.fireEvent(createKeyPressEvent("A", KeyCode.A));
	}
}
