package org.malai.javafx.interaction.library;

import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.MenuItem;
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
public class TestMenuItemPressed extends BaseJfXInteractionTest<MenuItemPressed> {
	MenuItem menu;

	@Override
	@BeforeEach
	public void setUp() {
		super.setUp();
		menu = new MenuItem();
	}

	@Override
	protected MenuItemPressed createInteraction() {
		return new MenuItemPressed();
	}

	@Test
	public void testMenuItemClickGoodState() throws MustAbortStateMachineException {
		interaction.onJfxMenuItemPressed(menu);
		Mockito.verify(handler, Mockito.times(1)).interactionStops(interaction);
		Mockito.verify(handler, Mockito.times(1)).interactionStarts(interaction);
	}

	@Test
	void testButtonClickGoodData() throws MustAbortStateMachineException {
		interaction.addHandler(new InteractionHandlerStub() {
			@Override
			public void interactionStops(final Interaction interaction) throws MustAbortStateMachineException {
				super.interactionStops(interaction);
				assertEquals(menu, ((MenuItemPressed) interaction).widget);
			}
		});
		interaction.onJfxMenuItemPressed(menu);
	}

	@Test
	void testMenuItemClickReinit() throws MustAbortStateMachineException {
		interaction.onJfxMenuItemPressed(menu);
		assertNull(interaction.getWidget());
		assertEquals(-1, interaction.getLastHIDUsed());
		assertTrue(interaction.getCurrentState() instanceof InitState);
	}

	@Test
	void testRegisterMenuItem() throws MustAbortStateMachineException {
		interaction.registerToMenuItems(Collections.singletonList(menu));
		menu.fire();
		Mockito.verify(handler, Mockito.times(1)).interactionStops(interaction);
		Mockito.verify(handler, Mockito.times(1)).interactionStarts(interaction);
	}

	@Test
	void testNoActionWhenNotRegistered() throws MustAbortStateMachineException {
		menu.fire();
		Mockito.verify(handler, Mockito.never()).interactionStops(interaction);
		Mockito.verify(handler, Mockito.never()).interactionStarts(interaction);
	}

	@Test
	void testNoActionWhenNotMenuItemRegistered() throws MustAbortStateMachineException {
		interaction.registerToNodes(Collections.singletonList(new CheckBox()));
		menu.fire();
		Mockito.verify(handler, Mockito.never()).interactionStops(interaction);
		Mockito.verify(handler, Mockito.never()).interactionStarts(interaction);
	}

	@Test
	void testNoActionWhenNullRegistered() throws MustAbortStateMachineException {
		interaction.registerToNodes(null);
		menu.fire();
		Mockito.verify(handler, Mockito.never()).interactionStops(interaction);
		Mockito.verify(handler, Mockito.never()).interactionStarts(interaction);
	}

	@Test
	void testNoActionWhenContainsNullRegistered() throws MustAbortStateMachineException {
		interaction.registerToNodes(Collections.singletonList(null));
		menu.fire();
		Mockito.verify(handler, Mockito.never()).interactionStops(interaction);
		Mockito.verify(handler, Mockito.never()).interactionStarts(interaction);
	}
}
