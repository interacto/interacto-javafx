package io.interacto.jfx.interaction.library;

import io.interacto.fsm.CancelFSMException;
import io.interacto.fsm.InitState;
import java.util.Collections;
import javafx.event.ActionEvent;
import javafx.scene.control.MenuItem;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.testfx.framework.junit5.ApplicationExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(ApplicationExtension.class)
public class TestMenuItemPressed extends BaseJfXInteractionTest<MenuItemPressed> {
	MenuItem menuitem;

	@Override
	@BeforeEach
	void setUp() {
		super.setUp();
		menuitem = new MenuItem();
	}

	@Override
	protected MenuItemPressed createInteraction() {
		return new MenuItemPressed();
	}

	@Test
	void testProcessEventGoodState() throws CancelFSMException {
		interaction.processEvent(new ActionEvent(menuitem, null));
		Mockito.verify(handler, Mockito.times(1)).fsmStops();
		Mockito.verify(handler, Mockito.times(1)).fsmStarts();
	}

	@Test
	void testProcessEventGoodData() {
		interaction.getFsm().addHandler(new InteractionHandlerStub() {
			@Override
			public void fsmStops() {
				assertEquals(menuitem, interaction.getWidget());
			}
		});
		interaction.processEvent(new ActionEvent(menuitem, null));
	}

	@Test
	void testProcessEventReinit() {
		interaction.processEvent(new ActionEvent(menuitem, null));
		assertNull(interaction.getWidget());
		assertTrue(interaction.getFsm().getCurrentState() instanceof InitState);
	}

	@Test
	void testRegister() throws CancelFSMException {
		interaction.registerToMenuItems(Collections.singletonList(menuitem));
		menuitem.fire();
		Mockito.verify(handler, Mockito.times(1)).fsmStops();
		Mockito.verify(handler, Mockito.times(1)).fsmStarts();
	}

	@Test
	void testNoActionWhenNotRegistered() throws CancelFSMException {
		menuitem.fire();
		Mockito.verify(handler, Mockito.never()).fsmStops();
		Mockito.verify(handler, Mockito.never()).fsmStarts();
	}

	@Test
	void testNoActionWhenWrongRegistered() throws CancelFSMException {
		interaction.registerToMenuItems(Collections.singletonList(new MenuItem()));
		menuitem.fire();
		Mockito.verify(handler, Mockito.never()).fsmStops();
		Mockito.verify(handler, Mockito.never()).fsmStarts();
	}

	@Test
	void testNoActionWhenNullRegistered() throws CancelFSMException {
		interaction.registerToMenuItems(null);
		menuitem.fire();
		Mockito.verify(handler, Mockito.never()).fsmStops();
		Mockito.verify(handler, Mockito.never()).fsmStarts();
	}

	@Test
	void testNoActionWhenContainsNullRegistered() throws CancelFSMException {
		interaction.registerToMenuItems(Collections.singletonList(null));
		menuitem.fire();
		Mockito.verify(handler, Mockito.never()).fsmStops();
		Mockito.verify(handler, Mockito.never()).fsmStarts();
	}

	@Test
	void testUnRegisterNode() throws CancelFSMException {
		interaction.registerToMenuItems(Collections.singletonList(menuitem));
		interaction.unregisterFromMenuItems(Collections.singletonList(menuitem));
		menuitem.fire();
		Mockito.verify(handler, Mockito.never()).fsmStops();
		Mockito.verify(handler, Mockito.never()).fsmStarts();
	}
}
