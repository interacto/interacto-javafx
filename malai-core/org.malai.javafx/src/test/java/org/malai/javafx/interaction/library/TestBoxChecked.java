package org.malai.javafx.interaction.library;

import java.util.Collections;
import javafx.scene.control.CheckBox;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.malai.interaction.InitState;
import org.malai.javafx.MockitoExtension;
import org.malai.stateMachine.MustCancelStateMachineException;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(MockitoExtension.class)
public class TestBoxChecked extends BaseJfXInteractionTest<BoxChecked> {
	CheckBox checkbox;

	@Override
	@BeforeEach
	void setUp() {
		super.setUp();
		checkbox = new CheckBox();
	}

	@Override
	protected BoxChecked createInteraction() {
		return new BoxChecked();
	}

	@Test
	void testCBClickGoodState() throws MustCancelStateMachineException {
		interaction.onJfxBoxChecked(checkbox);
		Mockito.verify(handler, Mockito.times(1)).interactionStops();
		Mockito.verify(handler, Mockito.times(1)).interactionStarts();
	}

	@Test
	void testCBClickGoodData() {
		interaction.addHandler(new InteractionHandlerStub() {
			@Override
			public void interactionStops() throws MustCancelStateMachineException {
				super.interactionStops();
				assertEquals(checkbox, interaction.widget);
			}
		});
		interaction.onJfxBoxChecked(checkbox);
	}

	@Test
	void testCBClickReinit() {
		interaction.onJfxBoxChecked(checkbox);
		assertNull(interaction.getWidget());
		assertEquals(-1, interaction.getLastHIDUsed());
		assertTrue(interaction.getCurrentState() instanceof InitState);
	}

	@Test
	void testRegisterCB() throws MustCancelStateMachineException {
		interaction.registerToNodes(Collections.singletonList(checkbox));
		checkbox.fire();
		Mockito.verify(handler, Mockito.times(1)).interactionStops();
		Mockito.verify(handler, Mockito.times(1)).interactionStarts();
	}

	@Test
	void testNoActionWhenNotRegistered() throws MustCancelStateMachineException {
		checkbox.fire();
		Mockito.verify(handler, Mockito.never()).interactionStops();
		Mockito.verify(handler, Mockito.never()).interactionStarts();
	}

	@Test
	void testNoActionWhenNotCBRegistered() throws MustCancelStateMachineException {
		interaction.registerToNodes(Collections.singletonList(new CheckBox()));
		checkbox.fire();
		Mockito.verify(handler, Mockito.never()).interactionStops();
		Mockito.verify(handler, Mockito.never()).interactionStarts();
	}

	@Test
	void testNoActionWhenNullCBRegistered() throws MustCancelStateMachineException {
		interaction.registerToNodes(null);
		checkbox.fire();
		Mockito.verify(handler, Mockito.never()).interactionStops();
		Mockito.verify(handler, Mockito.never()).interactionStarts();
	}

	@Test
	void testNoActionWhenContainsNullRegistered() throws MustCancelStateMachineException {
		interaction.registerToNodes(Collections.singletonList(null));
		checkbox.fire();
		Mockito.verify(handler, Mockito.never()).interactionStops();
		Mockito.verify(handler, Mockito.never()).interactionStarts();
	}
}