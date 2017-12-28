package org.malai.javafx.interaction.library;

import java.util.Collections;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ColorPicker;
import javafx.stage.Stage;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.malai.javafx.MockitoExtension;
import org.malai.stateMachine.MustCancelStateMachineException;
import org.mockito.Mockito;
import org.testfx.util.WaitForAsyncUtils;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

@ExtendWith(MockitoExtension.class)
public class TestScrolling extends BaseJfXInteractionTest<Scrolling> {
	@Override
	protected Scrolling createInteraction() {
		return new Scrolling();
	}

	@Test
	void testScrollingGoodState() throws MustCancelStateMachineException {
		interaction.onScroll(createScrollEvent(1, 2, 5, 0), 1);
		Mockito.verify(handler, Mockito.times(1)).interactionStops();
		Mockito.verify(handler, Mockito.times(1)).interactionStarts();
	}

	@Test
	void testScrollingClickGoodData() {
		interaction.addHandler(new InteractionHandlerStub() {
			@Override
			public void interactionStops() throws MustCancelStateMachineException {
				super.interactionStops();
				assertEquals(5d, interaction.getIncrement(), 0.00001);
				assertEquals(1d, interaction.px, 0.00001);
				assertEquals(2d, interaction.py, 0.00001);
			}
		});
		interaction.onScroll(createScrollEvent(1, 2, 0, 5), 1);
	}

	@Test
	void testScrollingReinit() {
		interaction.onScroll(createScrollEvent(1, 2, 5, 0), 1);
		assertNull(interaction.getScrolledNode());
		assertEquals(0d, interaction.getIncrement(), 0.00001);
		assertEquals(0d, interaction.px, 0.00001);
		assertEquals(0d, interaction.py, 0.00001);
	}

	@Test
	void testRegisterScrolling() throws MustCancelStateMachineException {
		ColorPicker dummyWidget = new ColorPicker();
		interaction.registerToNodes(Collections.singletonList(dummyWidget));
		dummyWidget.fireEvent(createScrollEvent(1, 2, 5, 0));
		Mockito.verify(handler, Mockito.times(1)).interactionStops();
		Mockito.verify(handler, Mockito.times(1)).interactionStarts();
	}

	@Test
	void testRegisterScrollingNoActionWhenNotRegistered() throws MustCancelStateMachineException {
		ColorPicker dummyWidget = new ColorPicker();
		dummyWidget.fireEvent(createScrollEvent(1, 2, 5, 0));
		Mockito.verify(handler, Mockito.never()).interactionStops();
		Mockito.verify(handler, Mockito.never()).interactionStarts();
	}

	@Test
	void testNoActionWhenNotScrollingRegistered() throws MustCancelStateMachineException {
		ColorPicker dummyWidgetNotRegistered = new ColorPicker();
		interaction.registerToNodes(Collections.singletonList(new CheckBox()));
		dummyWidgetNotRegistered.fireEvent(createScrollEvent(1, 2, 5, 0));
		Mockito.verify(handler, Mockito.never()).interactionStops();
		Mockito.verify(handler, Mockito.never()).interactionStarts();
	}

	@Test
	void testScrollingNoActionWhenNullRegistered() throws MustCancelStateMachineException {
		ColorPicker dummyWidget = new ColorPicker();
		interaction.registerToNodes(null);
		dummyWidget.fireEvent(createScrollEvent(1, 2, 5, 0));
		Mockito.verify(handler, Mockito.never()).interactionStops();
		Mockito.verify(handler, Mockito.never()).interactionStarts();
	}

	@Test
	void testScrollingNoActionWhenContainsNullRegistered() throws MustCancelStateMachineException {
		ColorPicker dummyWidget = new ColorPicker();
		interaction.registerToNodes(Collections.singletonList(null));
		dummyWidget.fireEvent(createScrollEvent(1, 2, 5, 0));
		Mockito.verify(handler, Mockito.never()).interactionStops();
		Mockito.verify(handler, Mockito.never()).interactionStarts();

	}

	@Test
	void testRegisterWindowScrolling() throws MustCancelStateMachineException {
		Scene dummyScene = new Scene(new Button());
		Platform.runLater(() -> {
			Stage stage = new Stage();
			stage.setScene(dummyScene);
		});
		WaitForAsyncUtils.waitForFxEvents();

		interaction.registerToWindows(Collections.singletonList(dummyScene.getWindow()));
		dummyScene.getWindow().fireEvent(createScrollEvent(1, 2, 5, 0));
		Mockito.verify(handler, Mockito.times(1)).interactionStops();
		Mockito.verify(handler, Mockito.times(1)).interactionStarts();
	}

	@Test
	void testRegisterWindowScrollingNoActionWhenNotRegistered() throws MustCancelStateMachineException {
		Scene dummyScene = new Scene(new Button());
		Platform.runLater(() -> {
			Stage stage = new Stage();
			stage.setScene(dummyScene);
		});
		WaitForAsyncUtils.waitForFxEvents();
		dummyScene.getWindow().fireEvent(createScrollEvent(1, 2, 5, 0));
		Mockito.verify(handler, Mockito.never()).interactionStops();
		Mockito.verify(handler, Mockito.never()).interactionStarts();
	}

	@Test
	void testNoActionWhenNotScrollingRegisteredWindow() throws MustCancelStateMachineException {
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
		Mockito.verify(handler, Mockito.never()).interactionStops();
		Mockito.verify(handler, Mockito.never()).interactionStarts();
	}

	@Test
	void testScrollingNoActionWhenNullRegisteredWindow() throws MustCancelStateMachineException {
		Scene dummyScene = new Scene(new Button());
		Platform.runLater(() -> {
			Stage stage = new Stage();
			stage.setScene(dummyScene);
		});
		WaitForAsyncUtils.waitForFxEvents();

		interaction.registerToWindows(null);
		dummyScene.getWindow().fireEvent(createScrollEvent(1, 2, 5, 0));
		Mockito.verify(handler, Mockito.never()).interactionStops();
		Mockito.verify(handler, Mockito.never()).interactionStarts();
	}

	@Test
	void testScrollingNoActionWhenContainsNullRegisteredWindow() throws MustCancelStateMachineException {
		Scene dummyScene = new Scene(new Button());
		Platform.runLater(() -> {
			Stage stage = new Stage();
			stage.setScene(dummyScene);
		});
		WaitForAsyncUtils.waitForFxEvents();

		interaction.registerToWindows(Collections.singletonList(null));
		dummyScene.getWindow().fireEvent(createScrollEvent(1, 2, 5, 0));
		Mockito.verify(handler, Mockito.never()).interactionStops();
		Mockito.verify(handler, Mockito.never()).interactionStarts();
	}
}
