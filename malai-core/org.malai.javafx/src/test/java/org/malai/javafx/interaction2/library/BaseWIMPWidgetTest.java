package org.malai.javafx.interaction2.library;

import java.util.Collections;
import javafx.event.ActionEvent;
import javafx.scene.Node;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.malai.fsm.CancelFSMException;
import org.malai.fsm.InitState;
import org.malai.javafx.interaction2.JfxInteraction;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

public abstract class BaseWIMPWidgetTest<W extends Node, I extends JfxInteraction<?, W>> extends BaseJfXInteractionTest<I> {
	W wimpWidget;

	abstract void triggerWidget();

	abstract W createWidget();

	@Override
	@BeforeEach
	void setUp() {
		super.setUp();
		wimpWidget = createWidget();
	}

	@Test
	void testProcessEventGoodState() throws CancelFSMException {
		interaction.processEvent(new ActionEvent(wimpWidget, null));
		Mockito.verify(handler, Mockito.times(1)).fsmStops();
		Mockito.verify(handler, Mockito.times(1)).fsmStarts();
	}

	@Test
	void testProcessEventGoodStateNULL() throws CancelFSMException {
		interaction.processEvent(null);
		Mockito.verify(handler, Mockito.never()).fsmStops();
		Mockito.verify(handler, Mockito.never()).fsmStarts();
	}

	@Test
	void testProcessEventGoodData() {
		interaction.getFsm().setHandler(new InteractionHandlerStub() {
			@Override
			public void fsmStops() {
				assertEquals(wimpWidget, interaction.getWidget());
			}
		});
		interaction.processEvent(new ActionEvent(wimpWidget, null));
	}

	@Test
	void testProcessEventReinit() {
		interaction.processEvent(new ActionEvent(wimpWidget, null));
		assertNull(interaction.getWidget());
		assertTrue(interaction.getFsm().getCurrentState() instanceof InitState);
	}

	@Test
	void testRegister() throws CancelFSMException {
		interaction.registerToNodes(Collections.singletonList(wimpWidget));
		triggerWidget();
		Mockito.verify(handler, Mockito.times(1)).fsmStops();
		Mockito.verify(handler, Mockito.times(1)).fsmStarts();
	}

	@Test
	void testNoActionWhenNotRegistered() throws CancelFSMException {
		triggerWidget();
		Mockito.verify(handler, Mockito.never()).fsmStops();
		Mockito.verify(handler, Mockito.never()).fsmStarts();
	}

	@Test
	void testNoActionWhenWrongRegistered() throws CancelFSMException {
		interaction.registerToNodes(Collections.singletonList(createWidget()));
		triggerWidget();
		Mockito.verify(handler, Mockito.never()).fsmStops();
		Mockito.verify(handler, Mockito.never()).fsmStarts();
	}

	@Test
	void testNoActionWhenNullRegistered() throws CancelFSMException {
		interaction.registerToNodes(null);
		triggerWidget();
		Mockito.verify(handler, Mockito.never()).fsmStops();
		Mockito.verify(handler, Mockito.never()).fsmStarts();
	}

	@Test
	void testNoActionWhenContainsNullRegistered() throws CancelFSMException {
		interaction.registerToNodes(Collections.singletonList(null));
		triggerWidget();
		Mockito.verify(handler, Mockito.never()).fsmStops();
		Mockito.verify(handler, Mockito.never()).fsmStarts();
	}

	@Test
	void testUnRegisterNode() throws CancelFSMException {
		interaction.registerToNodes(Collections.singletonList(wimpWidget));
		interaction.unregisterFromNodes(Collections.singletonList(wimpWidget));
		triggerWidget();
		Mockito.verify(handler, Mockito.never()).fsmStops();
		Mockito.verify(handler, Mockito.never()).fsmStarts();
	}
}