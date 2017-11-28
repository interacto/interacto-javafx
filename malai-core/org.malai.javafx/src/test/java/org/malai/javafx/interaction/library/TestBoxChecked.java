package org.malai.javafx.interaction.library;

import javafx.scene.control.CheckBox;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.malai.interaction.InitState;
import org.malai.interaction.Interaction;
import org.malai.javafx.MockitoExtension;
import org.malai.stateMachine.MustAbortStateMachineException;
import org.mockito.Mockito;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(MockitoExtension.class)
public class TestBoxChecked extends BaseJfXInteractionTest<BoxChecked> {
	CheckBox checkbox;

	@Override
	@BeforeEach
	public void setUp() {
		super.setUp();
		checkbox = new CheckBox();
	}

	@Override
	protected BoxChecked createInteraction() {
		return new BoxChecked();
	}

	@Test
	void testCBClickGoodState() throws MustAbortStateMachineException {
		interaction.onJfxBoxChecked(checkbox);
		Mockito.verify(handler, Mockito.times(1)).interactionStops(interaction);
		Mockito.verify(handler, Mockito.times(1)).interactionStarts(interaction);
	}

	@Test
	void testCBClickGoodData() throws MustAbortStateMachineException {
		interaction.addHandler(new InteractionHandlerStub() {
			@Override
			public void interactionStops(final Interaction interaction) throws MustAbortStateMachineException {
				super.interactionStops(interaction);
				assertEquals(checkbox, ((BoxChecked) interaction).widget);
			}
		});
		interaction.onJfxBoxChecked(checkbox);
	}

	@Test
	void testCBClickReinit() throws MustAbortStateMachineException {
		interaction.onJfxBoxChecked(checkbox);
		assertNull(interaction.getWidget());
		assertEquals(-1, interaction.getLastHIDUsed());
		assertTrue(interaction.getCurrentState() instanceof InitState);
	}

	@Test
	void testRegisterCB() throws MustAbortStateMachineException {
		interaction.registerToNodes(Collections.singletonList(checkbox));
		checkbox.fire();
		Mockito.verify(handler, Mockito.times(1)).interactionStops(interaction);
		Mockito.verify(handler, Mockito.times(1)).interactionStarts(interaction);
	}

	@Test
	void testNoActionWhenNotRegistered() throws MustAbortStateMachineException {
		checkbox.fire();
		Mockito.verify(handler, Mockito.never()).interactionStops(interaction);
		Mockito.verify(handler, Mockito.never()).interactionStarts(interaction);
	}

	@Test
	void testNoActionWhenNotCBRegistered() throws MustAbortStateMachineException {
		interaction.registerToNodes(Collections.singletonList(new CheckBox()));
		checkbox.fire();
		Mockito.verify(handler, Mockito.never()).interactionStops(interaction);
		Mockito.verify(handler, Mockito.never()).interactionStarts(interaction);
	}

	@Test
	void testNoActionWhenNullCBRegistered() throws MustAbortStateMachineException {
		interaction.registerToNodes(null);
		checkbox.fire();
		Mockito.verify(handler, Mockito.never()).interactionStops(interaction);
		Mockito.verify(handler, Mockito.never()).interactionStarts(interaction);
	}

	@Test
	void testNoActionWhenContainsNullRegistered() throws MustAbortStateMachineException {
		interaction.registerToNodes(Collections.singletonList(null));
		checkbox.fire();
		Mockito.verify(handler, Mockito.never()).interactionStops(interaction);
		Mockito.verify(handler, Mockito.never()).interactionStarts(interaction);
	}
}