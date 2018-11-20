package org.malai.javafx.interaction.library;

import java.util.Collections;
import javafx.event.ActionEvent;
import javafx.scene.Node;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.malai.fsm.CancelFSMException;
import org.malai.fsm.InitState;
import org.malai.javafx.JfxtestHelper;
import org.malai.javafx.interaction.JfxInteraction;
import org.mockito.Mockito;
import org.testfx.api.FxRobot;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

public abstract class BaseWIMPWidgetTest<W extends Node, I extends JfxInteraction<?, ?, W>> extends BaseJfXInteractionTest<I> {
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
	void testProcessEventGoodState(final FxRobot robot) throws CancelFSMException {
		interaction.processEvent(new ActionEvent(wimpWidget, null));
		JfxtestHelper.waitForTimeoutTransitions();
		Mockito.verify(handler, Mockito.times(1)).fsmStops();
		Mockito.verify(handler, Mockito.times(1)).fsmStarts();
	}

	@Test
	void testProcessEventGoodStateNULL(final FxRobot robot) throws CancelFSMException {
		interaction.processEvent(null);
		JfxtestHelper.waitForTimeoutTransitions();
		Mockito.verify(handler, Mockito.never()).fsmStops();
		Mockito.verify(handler, Mockito.never()).fsmStarts();
	}

	@Test
	void testProcessEventGoodDataOnStart(final FxRobot robot) {
		interaction.getFsm().addHandler(new InteractionHandlerStub() {
			@Override
			public void fsmStarts() {
				assertEquals(wimpWidget, interaction.getWidget());
			}
		});
		interaction.processEvent(new ActionEvent(wimpWidget, null));
		JfxtestHelper.waitForTimeoutTransitions();
	}

	@Test
	void testProcessEventGoodDataOnStop(final FxRobot robot) {
		interaction.getFsm().addHandler(new InteractionHandlerStub() {
			@Override
			public void fsmStops() {
				assertEquals(wimpWidget, interaction.getWidget());
			}
		});
		interaction.processEvent(new ActionEvent(wimpWidget, null));
		JfxtestHelper.waitForTimeoutTransitions();
	}

	@Test
	void testProcessEventReinit(final FxRobot robot) {
		interaction.processEvent(new ActionEvent(wimpWidget, null));
		JfxtestHelper.waitForTimeoutTransitions();
		assertNull(interaction.getWidget());
		assertTrue(interaction.getFsm().getCurrentState() instanceof InitState);
	}

	@Test
	void testRegister(final FxRobot robot) throws CancelFSMException {
		interaction.registerToNodes(Collections.singletonList(wimpWidget));
		triggerWidget();
		JfxtestHelper.waitForTimeoutTransitions();
		Mockito.verify(handler, Mockito.times(1)).fsmStops();
		Mockito.verify(handler, Mockito.times(1)).fsmStarts();
	}

	@Test
	void testNoActionWhenNotRegistered(final FxRobot robot) throws CancelFSMException {
		triggerWidget();
		JfxtestHelper.waitForTimeoutTransitions();
		Mockito.verify(handler, Mockito.never()).fsmStops();
		Mockito.verify(handler, Mockito.never()).fsmStarts();
	}

	@Test
	void testNoActionWhenWrongRegistered(final FxRobot robot) throws CancelFSMException {
		interaction.registerToNodes(Collections.singletonList(createWidget()));
		triggerWidget();
		JfxtestHelper.waitForTimeoutTransitions();
		Mockito.verify(handler, Mockito.never()).fsmStops();
		Mockito.verify(handler, Mockito.never()).fsmStarts();
	}

	@Test
	void testNoActionWhenNullRegistered(final FxRobot robot) throws CancelFSMException {
		interaction.registerToNodes(null);
		triggerWidget();
		JfxtestHelper.waitForTimeoutTransitions();
		Mockito.verify(handler, Mockito.never()).fsmStops();
		Mockito.verify(handler, Mockito.never()).fsmStarts();
	}

	@Test
	void testNoActionWhenContainsNullRegistered(final FxRobot robot) throws CancelFSMException {
		interaction.registerToNodes(Collections.singletonList(null));
		triggerWidget();
		JfxtestHelper.waitForTimeoutTransitions();
		Mockito.verify(handler, Mockito.never()).fsmStops();
		Mockito.verify(handler, Mockito.never()).fsmStarts();
	}

	@Test
	void testUnRegisterNode(final FxRobot robot) throws CancelFSMException {
		interaction.registerToNodes(Collections.singletonList(wimpWidget));
		interaction.unregisterFromNodes(Collections.singletonList(wimpWidget));
		triggerWidget();
		JfxtestHelper.waitForTimeoutTransitions();
		Mockito.verify(handler, Mockito.never()).fsmStops();
		Mockito.verify(handler, Mockito.never()).fsmStarts();
	}
}