package org.malai.javafx.interaction.library;

import javafx.scene.control.Button;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.input.MouseButton;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.malai.interaction.Interaction;
import org.malai.javafx.MockitoExtension;
import org.malai.stateMachine.MustAbortStateMachineException;
import org.mockito.Mockito;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class TestTabSelected extends BaseJfXInteractionTest<TabSelected> {
	TabPane tabPane;
	Tab tab1;
	Tab tab2;

	@Override
	@BeforeEach
	public void setUp() {
		super.setUp();
		tabPane = new TabPane();
		tab1 = new Tab();
		tab2 = new Tab();
		tabPane.getTabs().addAll(tab1);
		tabPane.getTabs().addAll(tab2);
		tabPane.getSelectionModel().select(0);
	}

	@Override
	protected TabSelected createInteraction() {
		return new TabSelected();
	}

	@Test
	void TestTabSelectedValid() throws MustAbortStateMachineException {
		interaction.onJfXTabSelected(tabPane);
		Mockito.verify(handler, Mockito.times(1)).interactionStops(interaction);
		Mockito.verify(handler, Mockito.times(1)).interactionStarts(interaction);
	}

	@Test
	void TestTabSelectedGoodData() throws MustAbortStateMachineException {
		interaction.addHandler(new InteractionHandlerStub() {
			@Override
			public void interactionStops(final Interaction interaction) throws MustAbortStateMachineException {
				super.interactionStops(interaction);
				assertEquals(tabPane, ((TabSelected) interaction).widget );
			}
		});
		interaction.onJfXTabSelected(tabPane);
	}

	@Test
	void TestTabSelectedRegisterToNode() throws MustAbortStateMachineException {
		interaction.registerToNodes(Collections.singletonList(tabPane));
		tabPane.getSelectionModel().select(1);
		Mockito.verify(handler, Mockito.times(1)).interactionStops(interaction);
		Mockito.verify(handler, Mockito.times(1)).interactionStarts(interaction);
	}

	@Test
	void testNoActionWhenNotRegistered() throws MustAbortStateMachineException {
		tabPane.getSelectionModel().select(1);
		Mockito.verify(handler, Mockito.never()).interactionStops(interaction);
		Mockito.verify(handler, Mockito.never()).interactionStarts(interaction);
	}

}
