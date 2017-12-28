package org.malai.javafx.interaction.library;

import java.util.Collections;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.malai.javafx.MockitoExtension;
import org.malai.stateMachine.MustCancelStateMachineException;
import org.mockito.Mockito;

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
	void TestTabSelectedValid() throws MustCancelStateMachineException {
		interaction.onJfXTabSelected(tabPane);
		Mockito.verify(handler, Mockito.times(1)).interactionStops();
		Mockito.verify(handler, Mockito.times(1)).interactionStarts();
	}

	@Test
	void TestTabSelectedGoodData() {
		interaction.addHandler(new InteractionHandlerStub() {
			@Override
			public void interactionStops() throws MustCancelStateMachineException {
				super.interactionStops();
				assertEquals(tabPane, interaction.widget);
			}
		});
		interaction.onJfXTabSelected(tabPane);
	}

	@Test
	void TestTabSelectedRegisterToNode() throws MustCancelStateMachineException {
		interaction.registerToNodes(Collections.singletonList(tabPane));
		tabPane.getSelectionModel().select(1);
		Mockito.verify(handler, Mockito.times(1)).interactionStops();
		Mockito.verify(handler, Mockito.times(1)).interactionStarts();
	}

	@Test
	void testNoActionWhenNotRegistered() throws MustCancelStateMachineException {
		tabPane.getSelectionModel().select(1);
		Mockito.verify(handler, Mockito.never()).interactionStops();
		Mockito.verify(handler, Mockito.never()).interactionStarts();
	}
}
