package org.malai.javafx.interaction.library;

import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ColorPicker;
import javafx.stage.Stage;
import javafx.stage.Window;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.malai.interaction.Interaction;
import org.malai.javafx.MockitoExtension;
import org.malai.stateMachine.MustAbortStateMachineException;
import org.mockito.Mockito;
import org.testfx.util.WaitForAsyncUtils;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

@ExtendWith(MockitoExtension.class)
public class TestScrolling extends BaseJfXInteractionTest<Scrolling> {
	@Override
	protected Scrolling createInteraction() {
		return new Scrolling();
	}

	@Test
	void testScrollingGoodState() throws MustAbortStateMachineException {
		interaction.onScroll(createScrollEvent(1, 2, 5, 0), 1);
		Mockito.verify(handler, Mockito.times(1)).interactionStops(interaction);
		Mockito.verify(handler, Mockito.times(1)).interactionStarts(interaction);
	}

	@Test
	void testScrollingClickGoodData() throws MustAbortStateMachineException {
		interaction.addHandler(new InteractionHandlerStub() {
			@Override
			public void interactionStops(final Interaction interaction) throws MustAbortStateMachineException {
				super.interactionStops(interaction);
				assertEquals(5d, ((Scrolling) interaction).getIncrement(), 0.00001);
				assertEquals(1d, ((Scrolling) interaction).px, 0.00001);
				assertEquals(2d, ((Scrolling) interaction).py, 0.00001);
			}
		});
		interaction.onScroll(createScrollEvent(1, 2, 0, 5), 1);
	}

	@Test
	void testScrollingReinit() throws MustAbortStateMachineException {
		interaction.onScroll(createScrollEvent(1, 2, 5, 0), 1);
		assertNull(interaction.getScrolledNode());
		assertEquals(0d, ((Scrolling) interaction).getIncrement(), 0.00001);
		assertEquals(0d, ((Scrolling) interaction).px, 0.00001);
		assertEquals(0d, ((Scrolling) interaction).py, 0.00001);
	}

	@Test
	void testRegisterScrolling() throws MustAbortStateMachineException {
		ColorPicker dummyWidget = new ColorPicker();
		interaction.registerToNodes(Collections.singletonList(dummyWidget));
		dummyWidget.fireEvent(createScrollEvent(1, 2, 5, 0));
		Mockito.verify(handler, Mockito.times(1)).interactionStops(interaction);
		Mockito.verify(handler, Mockito.times(1)).interactionStarts(interaction);
	}

	@Test
	void testRegisterScrollingNoActionWhenNotRegistered() throws MustAbortStateMachineException {
		ColorPicker dummyWidget = new ColorPicker();
		dummyWidget.fireEvent(createScrollEvent(1, 2, 5, 0));
		Mockito.verify(handler, Mockito.never()).interactionStops(interaction);
		Mockito.verify(handler, Mockito.never()).interactionStarts(interaction);
	}

	@Test
	void testNoActionWhenNotScrollingRegistered() throws MustAbortStateMachineException {
		ColorPicker dummyWidgetNotRegistered = new ColorPicker();
		interaction.registerToNodes(Collections.singletonList(new CheckBox()));
		dummyWidgetNotRegistered.fireEvent(createScrollEvent(1, 2, 5, 0));
		Mockito.verify(handler, Mockito.never()).interactionStops(interaction);
		Mockito.verify(handler, Mockito.never()).interactionStarts(interaction);
	}

	@Test
	void testScrollingNoActionWhenNullRegistered() throws MustAbortStateMachineException {
		ColorPicker dummyWidget = new ColorPicker();
		interaction.registerToNodes(null);
		dummyWidget.fireEvent(createScrollEvent(1, 2, 5, 0));
		Mockito.verify(handler, Mockito.never()).interactionStops(interaction);
		Mockito.verify(handler, Mockito.never()).interactionStarts(interaction);
	}

	@Test
	void testScrollingNoActionWhenContainsNullRegistered() throws MustAbortStateMachineException {
		ColorPicker dummyWidget = new ColorPicker();
		interaction.registerToNodes(Collections.singletonList(null));
		dummyWidget.fireEvent(createScrollEvent(1, 2, 5, 0));
		Mockito.verify(handler, Mockito.never()).interactionStops(interaction);
		Mockito.verify(handler, Mockito.never()).interactionStarts(interaction);

	}

	@Test
	void testRegisterWindowScrolling() throws MustAbortStateMachineException {
		Scene dummyScene = new Scene(new Button());
		Platform.runLater(() -> {
			Stage stage = new Stage();
			stage.setScene(dummyScene);
		});
		WaitForAsyncUtils.waitForFxEvents();

		interaction.registerToWindows(Collections.singletonList(dummyScene.getWindow()));
		dummyScene.getWindow().fireEvent(createScrollEvent(1, 2, 5, 0));
		Mockito.verify(handler, Mockito.times(1)).interactionStops(interaction);
		Mockito.verify(handler, Mockito.times(1)).interactionStarts(interaction);
	}

	@Test
	void testRegisterWindowScrollingNoActionWhenNotRegistered() throws MustAbortStateMachineException {
		Scene dummyScene = new Scene(new Button());
		Platform.runLater(() -> {
			Stage stage = new Stage();
			stage.setScene(dummyScene);
		});
		WaitForAsyncUtils.waitForFxEvents();
		dummyScene.getWindow().fireEvent(createScrollEvent(1, 2, 5, 0));
		Mockito.verify(handler, Mockito.never()).interactionStops(interaction);
		Mockito.verify(handler, Mockito.never()).interactionStarts(interaction);
	}

	@Test
	void testNoActionWhenNotScrollingRegisteredWindow() throws MustAbortStateMachineException {
		Scene dummyScene = new Scene(new Button());
		Scene dummySceneFire = new Scene(new Button());
		Platform.runLater(() -> {
			Stage stage = new Stage();
			stage.setScene(dummyScene);
			Stage stageFire = new Stage();
			stageFire.setScene(dummySceneFire);
		});
		WaitForAsyncUtils.waitForFxEvents();

		interaction.registerToWindows(Collections.singletonList(dummyScene.getWindow()));
		dummySceneFire.getWindow().fireEvent(createScrollEvent(1, 2, 5, 0));
		Mockito.verify(handler, Mockito.never()).interactionStops(interaction);
		Mockito.verify(handler, Mockito.never()).interactionStarts(interaction);
	}

	@Test
	void testScrollingNoActionWhenNullRegisteredWindow() throws MustAbortStateMachineException {
		Scene dummyScene = new Scene(new Button());
		Platform.runLater(() -> {
			Stage stage = new Stage();
			stage.setScene(dummyScene);
		});
		WaitForAsyncUtils.waitForFxEvents();

		interaction.registerToWindows(null);
		dummyScene.getWindow().fireEvent(createScrollEvent(1, 2, 5, 0));
		Mockito.verify(handler, Mockito.never()).interactionStops(interaction);
		Mockito.verify(handler, Mockito.never()).interactionStarts(interaction);
	}

	@Test
	void testScrollingNoActionWhenContainsNullRegisteredWindow() throws MustAbortStateMachineException {
		Scene dummyScene = new Scene(new Button());
		Platform.runLater(() -> {
			Stage stage = new Stage();
			stage.setScene(dummyScene);
		});
		WaitForAsyncUtils.waitForFxEvents();

		interaction.registerToWindows(Collections.singletonList(null));
		dummyScene.getWindow().fireEvent(createScrollEvent(1, 2, 5, 0));
		Mockito.verify(handler, Mockito.never()).interactionStops(interaction);
		Mockito.verify(handler, Mockito.never()).interactionStarts(interaction);
	}
}
