package io.interacto.jfx.interaction.library;

import io.interacto.fsm.CancelFSMException;
import java.util.Collections;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.testfx.framework.junit5.ApplicationExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(ApplicationExtension.class)
public class TestKeyTyped extends BaseJfXInteractionTest<KeyTyped> {
	@Override
	KeyTyped createInteraction() {
		return new KeyTyped();
	}

	@Test
	public void testKeyPressExecution() throws CancelFSMException {
		interaction.processEvent(createKeyTypedEvent("A", KeyCode.A));
		Mockito.verify(handler, Mockito.times(1)).fsmStops();
		Mockito.verify(handler, Mockito.times(1)).fsmStarts();
	}

	@Test
	void testKeyPressData() {
		interaction.getFsm().addHandler(new InteractionHandlerStub() {
			@Override
			public void fsmStops() {
				assertEquals("A", interaction.getKey());
			}
		});
		interaction.processEvent(createKeyTypedEvent("A", KeyCode.A));
	}

	@Test
	void testKeyPressOnRegWidget() throws CancelFSMException {
		final Pane pane = new Pane();
		interaction.registerToNodes(Collections.singletonList(pane));
		pane.fireEvent(createKeyTypedEvent("A", KeyCode.A));
		Mockito.verify(handler, Mockito.times(1)).fsmStarts();
		Mockito.verify(handler, Mockito.times(1)).fsmStops();
	}

	@Test
	void testKeyPressOnUnregWidget() throws CancelFSMException {
		final Pane pane = new Pane();
		interaction.registerToNodes(Collections.singletonList(pane));
		interaction.unregisterFromNodes(Collections.singletonList(pane));
		pane.fireEvent(createKeyTypedEvent("A", KeyCode.A));
		Mockito.verify(handler, Mockito.never()).fsmStarts();
	}

	@Test
	public void testTwoKeyPressEnds() throws CancelFSMException {
		interaction.processEvent(createKeyTypedEvent("A", KeyCode.A));
		interaction.processEvent(createKeyTypedEvent("B", KeyCode.B));
		Mockito.verify(handler, Mockito.times(2)).fsmStops();
		Mockito.verify(handler, Mockito.times(2)).fsmStarts();
	}
}
