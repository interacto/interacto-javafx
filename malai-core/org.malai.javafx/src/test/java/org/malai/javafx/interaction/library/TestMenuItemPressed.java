package org.malai.javafx.interaction.library;

import java.util.Collections;
import javafx.scene.control.CheckBox;
import javafx.scene.control.MenuItem;
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
	public void testMenuItemClickGoodState() throws MustCancelStateMachineException {
		interaction.onJfxMenuItemPressed(menu);
		Mockito.verify(handler, Mockito.times(1)).interactionStops();
		Mockito.verify(handler, Mockito.times(1)).interactionStarts();
	}

	@Test
	void testButtonClickGoodData() {
		interaction.addHandler(new InteractionHandlerStub() {
			@Override
			public void interactionStops() throws MustCancelStateMachineException {
				super.interactionStops();
				assertEquals(menu, interaction.widget);
			}
		});
		interaction.onJfxMenuItemPressed(menu);
	}

	@Test
	void testMenuItemClickReinit() {
		interaction.onJfxMenuItemPressed(menu);
		assertNull(interaction.getWidget());
		assertEquals(-1, interaction.getLastHIDUsed());
		assertTrue(interaction.getCurrentState() instanceof InitState);
	}

	@Test
	void testRegisterMenuItem() throws MustCancelStateMachineException {
		interaction.registerToMenuItems(Collections.singletonList(menu));
		menu.fire();
		Mockito.verify(handler, Mockito.times(1)).interactionStops();
		Mockito.verify(handler, Mockito.times(1)).interactionStarts();
	}

	@Test
	void testNoActionWhenNotRegistered() throws MustCancelStateMachineException {
		menu.fire();
		Mockito.verify(handler, Mockito.never()).interactionStops();
		Mockito.verify(handler, Mockito.never()).interactionStarts();
	}

	@Test
	void testNoActionWhenNotMenuItemRegistered() throws MustCancelStateMachineException {
		interaction.registerToNodes(Collections.singletonList(new CheckBox()));
		menu.fire();
		Mockito.verify(handler, Mockito.never()).interactionStops();
		Mockito.verify(handler, Mockito.never()).interactionStarts();
	}

	@Test
	void testNoActionWhenNullRegistered() throws MustCancelStateMachineException {
		interaction.registerToNodes(null);
		menu.fire();
		Mockito.verify(handler, Mockito.never()).interactionStops();
		Mockito.verify(handler, Mockito.never()).interactionStarts();
	}

	@Test
	void testNoActionWhenContainsNullRegistered() throws MustCancelStateMachineException {
		interaction.registerToNodes(Collections.singletonList(null));
		menu.fire();
		Mockito.verify(handler, Mockito.never()).interactionStops();
		Mockito.verify(handler, Mockito.never()).interactionStarts();
	}
}
