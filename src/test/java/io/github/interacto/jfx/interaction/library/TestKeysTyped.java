package io.github.interacto.jfx.interaction.library;

import io.github.interacto.jfx.JfxtestHelper;
import io.github.interacto.fsm.CancelFSMException;
import java.util.Collections;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.testfx.framework.junit5.ApplicationExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(ApplicationExtension.class)
public class TestKeysTyped extends BaseJfXInteractionTest<KeysTyped> {
	@Override
	KeysTyped createInteraction() {
		return new KeysTyped();
	}

	@Test
	public void testKeyTypedSleepExecutionOK() throws CancelFSMException {
		interaction.processEvent(createKeyTypedEvent("A", KeyCode.A));
		JfxtestHelper.waitForTimeoutTransitions();
		Mockito.verify(handler, Mockito.times(1)).fsmStops();
		Mockito.verify(handler, Mockito.times(1)).fsmStarts();
	}

	@Test
	public void testKeyTypedSleepDataOK() {
		interaction.getFsm().addHandler(new InteractionHandlerStub() {
			@Override
			public void fsmStops() {
				assertEquals("A", interaction.getKeys().get(0));
			}
		});
		interaction.processEvent(createKeyTypedEvent("A", KeyCode.A));
		JfxtestHelper.waitForTimeoutTransitions();
	}

	@Test
	public void testKeyTypedSleepCleanOK() {
		interaction.processEvent(createKeyTypedEvent("A", KeyCode.A));
		JfxtestHelper.waitForTimeoutTransitions();
		assertTrue(interaction.getKeyCodes().isEmpty());
		assertTrue(interaction.getKeys().isEmpty());
	}

	@Test
	public void testKeyTypedExecutionOK() throws CancelFSMException {
		interaction.processEvent(createKeyTypedEvent("A", KeyCode.A));
		Mockito.verify(handler, Mockito.times(1)).fsmStarts();
		Mockito.verify(handler, Mockito.times(1)).fsmUpdates();
		Mockito.verify(handler, Mockito.never()).fsmStops();
	}

	@Test
	public void testKeyTypedDataOK() {
		interaction.getFsm().addHandler(new InteractionHandlerStub() {
			@Override
			public void fsmUpdates() {
				assertEquals("A", interaction.getKeys().get(0));
			}
		});
		interaction.processEvent(createKeyTypedEvent("A", KeyCode.A));
	}

	@Test
	public void testTwoKeysTypedExecutionOK() throws CancelFSMException {
		interaction.processEvent(createKeyTypedEvent("A", KeyCode.A));
		interaction.processEvent(createKeyTypedEvent("B", KeyCode.B));
		Mockito.verify(handler, Mockito.times(1)).fsmStarts();
		Mockito.verify(handler, Mockito.times(2)).fsmUpdates();
		Mockito.verify(handler, Mockito.never()).fsmStops();
	}

	@Test
	public void testTwoKeysTypedDataOK() {
		interaction.processEvent(createKeyTypedEvent("A", KeyCode.A));
		interaction.getFsm().addHandler(new InteractionHandlerStub() {
			@Override
			public void fsmUpdates() {
				assertEquals("A", interaction.getKeys().get(0));
				assertEquals("B", interaction.getKeys().get(1));
			}
		});
		interaction.processEvent(createKeyTypedEvent("B", KeyCode.B));
	}

	@Test
	public void testTwoKeysTypedSleepExecutionOK() throws CancelFSMException {
		interaction.processEvent(createKeyTypedEvent("A", KeyCode.A));
		interaction.processEvent(createKeyTypedEvent("B", KeyCode.B));
		JfxtestHelper.waitForTimeoutTransitions();
		Mockito.verify(handler, Mockito.times(1)).fsmStops();
		Mockito.verify(handler, Mockito.times(1)).fsmStarts();
		Mockito.verify(handler, Mockito.times(2)).fsmUpdates();
	}

	@Test
	public void testTwoKeysTypedSleepDataOK() {
		interaction.getFsm().addHandler(new InteractionHandlerStub() {
			@Override
			public void fsmStops() {
				assertEquals("A", interaction.getKeys().get(0));
				assertEquals("B", interaction.getKeys().get(1));
			}
		});
		interaction.processEvent(createKeyTypedEvent("A", KeyCode.A));
		interaction.processEvent(createKeyTypedEvent("B", KeyCode.B));
		JfxtestHelper.waitForTimeoutTransitions();
	}

	@Test
	void testKeyTypedOnRegWidget() throws CancelFSMException {
		final Pane pane = new Pane();
		interaction.registerToNodes(Collections.singletonList(pane));
		pane.fireEvent(createKeyTypedEvent("A", KeyCode.A));
		Mockito.verify(handler, Mockito.times(1)).fsmStarts();
	}

	@Test
	void testKeyTypedOnRegUnregWidget() throws CancelFSMException {
		final Pane pane = new Pane();
		interaction.registerToNodes(Collections.singletonList(pane));
		interaction.unregisterFromNodes(Collections.singletonList(pane));
		pane.fireEvent(createKeyTypedEvent("A", KeyCode.A));
		Mockito.verify(handler, Mockito.never()).fsmStarts();
	}
}
