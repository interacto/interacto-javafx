package io.interacto.jfx.interaction.library;

import io.interacto.fsm.CancelFSMException;
import io.interacto.fsm.InitState;
import io.interacto.jfx.JfxtestHelper;
import java.util.Collections;
import javafx.event.ActionEvent;
import javafx.scene.control.Spinner;
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
public class TestSpinnerChanged extends BaseJfXInteractionTest<SpinnerChanged> {
	Spinner<?> widget;

	@Override
	@BeforeEach
	void setUp() {
		super.setUp();
		widget = new Spinner<>();
	}

	@Override
	protected SpinnerChanged createInteraction() {
		return new SpinnerChanged();
	}

	@Nested
	class TestNominal {
		@BeforeEach
		void setUp() {
			interaction.registerToNodes(Collections.singletonList(widget));
		}

		@Test
		void testSpinnerChangedGoodState() throws CancelFSMException {
			widget.fireEvent(new ActionEvent(widget, null));
			JfxtestHelper.waitForTimeoutTransitions();
			Mockito.verify(handler, Mockito.times(1)).fsmStops();
			Mockito.verify(handler, Mockito.times(1)).fsmUpdates();
			Mockito.verify(handler, Mockito.times(1)).fsmStarts();
		}

		@Test
		void testSpinnerChange2TimesGoodState() throws CancelFSMException {
			widget.fireEvent(new ActionEvent(widget, null));
			widget.fireEvent(new ActionEvent(widget, null));
			JfxtestHelper.waitForTimeoutTransitions();
			Mockito.verify(handler, Mockito.times(1)).fsmStops();
			Mockito.verify(handler, Mockito.times(1)).fsmStarts();
			Mockito.verify(handler, Mockito.times(2)).fsmUpdates();
		}

		@Test
		void testSpinnerChangedGoodStateithTimeGap() throws CancelFSMException {
			SpinnerChangedFSM.setTimeGap(50L);
			widget.fireEvent(new ActionEvent(widget, null));
			JfxtestHelper.waitForTimeoutTransitions();
			Mockito.verify(handler, Mockito.times(1)).fsmStops();
			Mockito.verify(handler, Mockito.times(1)).fsmStarts();
			Mockito.verify(handler, Mockito.times(1)).fsmUpdates();
		}

		@Test
		void testSpinnerChangeTwoTimesWith500GoodState() throws CancelFSMException {
			widget.fireEvent(new ActionEvent(widget, null));
			JfxtestHelper.waitForTimeoutTransitions();
			widget.fireEvent(new ActionEvent(widget, null));
			JfxtestHelper.waitForTimeoutTransitions();
			Mockito.verify(handler, Mockito.times(2)).fsmStops();
			Mockito.verify(handler, Mockito.times(2)).fsmStarts();
			Mockito.verify(handler, Mockito.times(2)).fsmUpdates();
		}
	}

	@Test
	void testProcessEventGoodState() throws CancelFSMException {
		interaction.processEvent(new ActionEvent(widget, null));
		JfxtestHelper.waitForTimeoutTransitions();
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
		interaction.getFsm().addHandler(new InteractionHandlerStub() {
			@Override
			public void fsmStops() {
				assertEquals(widget, interaction.getWidget());
			}
		});
		interaction.processEvent(new ActionEvent(widget, null));
	}

	@Test
	void testProcessEventReinit() {
		interaction.processEvent(new ActionEvent(widget, null));
		JfxtestHelper.waitForTimeoutTransitions();
		assertNull(interaction.getWidget());
		assertTrue(interaction.getFsm().getCurrentState() instanceof InitState);
	}

	@Test
	void testNoActionWhenNotRegistered() throws CancelFSMException {
		widget.fireEvent(new ActionEvent(widget, null));
		Mockito.verify(handler, Mockito.never()).fsmStops();
		Mockito.verify(handler, Mockito.never()).fsmStarts();
	}

	@Test
	void testNoActionWhenWrongRegistered() throws CancelFSMException {
		interaction.registerToNodes(Collections.singletonList(new Spinner<>()));
		widget.fireEvent(new ActionEvent(widget, null));
		Mockito.verify(handler, Mockito.never()).fsmStops();
		Mockito.verify(handler, Mockito.never()).fsmStarts();
	}

	@Test
	void testNoActionWhenNullRegistered() throws CancelFSMException {
		interaction.registerToNodes(null);
		widget.fireEvent(new ActionEvent(widget, null));
		Mockito.verify(handler, Mockito.never()).fsmStops();
		Mockito.verify(handler, Mockito.never()).fsmStarts();
	}

	@Test
	void testNoActionWhenContainsNullRegistered() throws CancelFSMException {
		interaction.registerToNodes(Collections.singletonList(null));
		widget.fireEvent(new ActionEvent(widget, null));
		Mockito.verify(handler, Mockito.never()).fsmStops();
		Mockito.verify(handler, Mockito.never()).fsmStarts();
	}

	@Test
	void testUnRegisterNode() throws CancelFSMException {
		interaction.registerToNodes(Collections.singletonList(widget));
		interaction.unregisterFromNodes(Collections.singletonList(widget));
		widget.fireEvent(new ActionEvent(widget, null));
		Mockito.verify(handler, Mockito.never()).fsmStops();
		Mockito.verify(handler, Mockito.never()).fsmStarts();
	}
}
