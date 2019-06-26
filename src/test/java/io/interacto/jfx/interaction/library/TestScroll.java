package io.interacto.jfx.interaction.library;

import io.interacto.fsm.CancelFSMException;
import java.util.Collections;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ColorPicker;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.util.WaitForAsyncUtils;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

@ExtendWith(ApplicationExtension.class)
public class TestScroll extends BaseJfXInteractionTest<Scroll> {
	@Override
	Scroll createInteraction() {
		return new Scroll();
	}

	@Test
	void testScrollingGoodState() throws CancelFSMException {
		interaction.processEvent(createScrollEvent(1, 2, 5, 0));
		Mockito.verify(handler, Mockito.times(1)).fsmStops();
		Mockito.verify(handler, Mockito.times(1)).fsmStarts();
	}

	@Test
	void testScrollOnRegWidget() throws CancelFSMException {
		final Pane pane = new Pane();
		interaction.registerToNodes(Collections.singletonList(pane));
		pane.fireEvent(createScrollEvent(1, 2, 5, 0));
		Mockito.verify(handler, Mockito.times(1)).fsmStarts();
		Mockito.verify(handler, Mockito.times(1)).fsmStops();
	}

	@Test
	void testScrollingClickGoodData() {
		interaction.getFsm().addHandler(new InteractionHandlerStub() {
			@Override
			public void fsmStops() {
				assertEquals(5d, interaction.getIncrement(), 0.00001);
				assertEquals(1d, interaction.px, 0.00001);
				assertEquals(2d, interaction.py, 0.00001);
			}
		});
		interaction.processEvent(createScrollEvent(1, 2, 0, 5));
	}

	@Test
	void testScrollingReinit() {
		interaction.processEvent(createScrollEvent(1, 2, 5, 0));
		assertNull(interaction.getScrolledNode());
		assertEquals(0d, interaction.getIncrement(), 0.00001);
		assertEquals(0d, interaction.px, 0.00001);
		assertEquals(0d, interaction.py, 0.00001);
	}

	@Test
	void testRegisterScrolling() throws CancelFSMException {
		final ColorPicker dummyWidget = new ColorPicker();
		interaction.registerToNodes(Collections.singletonList(dummyWidget));
		dummyWidget.fireEvent(createScrollEvent(1, 2, 5, 0));
		Mockito.verify(handler, Mockito.times(1)).fsmStops();
		Mockito.verify(handler, Mockito.times(1)).fsmStarts();
	}

	@Test
	void testRegisterScrollingNoActionWhenNotRegistered() throws CancelFSMException {
		final ColorPicker dummyWidget = new ColorPicker();
		dummyWidget.fireEvent(createScrollEvent(1, 2, 5, 0));
		Mockito.verify(handler, Mockito.never()).fsmStops();
		Mockito.verify(handler, Mockito.never()).fsmStarts();
	}

	@Test
	void testNoActionWhenNotScrollingRegistered() throws CancelFSMException {
		final ColorPicker dummyWidgetNotRegistered = new ColorPicker();
		interaction.registerToNodes(Collections.singletonList(new CheckBox()));
		dummyWidgetNotRegistered.fireEvent(createScrollEvent(1, 2, 5, 0));
		Mockito.verify(handler, Mockito.never()).fsmStops();
		Mockito.verify(handler, Mockito.never()).fsmStarts();
	}

	@Test
	void testScrollingNoActionWhenNullRegistered() throws CancelFSMException {
		final ColorPicker dummyWidget = new ColorPicker();
		interaction.registerToNodes(null);
		dummyWidget.fireEvent(createScrollEvent(1, 2, 5, 0));
		Mockito.verify(handler, Mockito.never()).fsmStops();
		Mockito.verify(handler, Mockito.never()).fsmStarts();
	}

	@Test
	void testScrollingNoActionWhenContainsNullRegistered() throws CancelFSMException {
		final ColorPicker dummyWidget = new ColorPicker();
		interaction.registerToNodes(Collections.singletonList(null));
		dummyWidget.fireEvent(createScrollEvent(1, 2, 5, 0));
		Mockito.verify(handler, Mockito.never()).fsmStops();
		Mockito.verify(handler, Mockito.never()).fsmStarts();

	}

	@Test
	void testRegisterWindowScrolling() throws CancelFSMException {
		final Scene dummyScene = new Scene(new Button());
		Platform.runLater(() -> {
			final Stage stage = new Stage();
			stage.setScene(dummyScene);
		});
		WaitForAsyncUtils.waitForFxEvents();

		interaction.registerToWindows(Collections.singletonList(dummyScene.getWindow()));
		dummyScene.getWindow().fireEvent(createScrollEvent(1, 2, 5, 0));
		Mockito.verify(handler, Mockito.times(1)).fsmStops();
		Mockito.verify(handler, Mockito.times(1)).fsmStarts();
	}

	@Test
	void testRegisterWindowScrollingNoActionWhenNotRegistered() throws CancelFSMException {
		final Scene dummyScene = new Scene(new Button());
		Platform.runLater(() -> {
			final Stage stage = new Stage();
			stage.setScene(dummyScene);
		});
		WaitForAsyncUtils.waitForFxEvents();
		dummyScene.getWindow().fireEvent(createScrollEvent(1, 2, 5, 0));
		Mockito.verify(handler, Mockito.never()).fsmStops();
		Mockito.verify(handler, Mockito.never()).fsmStarts();
	}

	@Test
	void testNoActionWhenNotScrollingRegisteredWindow() throws CancelFSMException {
		final Scene dummyScene = new Scene(new Button());
		final Scene dummySceneFire = new Scene(new Button());
		Platform.runLater(() -> {
			final Stage stage = new Stage();
			stage.setScene(dummyScene);
			final Stage stageFire = new Stage();
			stageFire.setScene(dummySceneFire);
		});
		WaitForAsyncUtils.waitForFxEvents();

		interaction.registerToWindows(Collections.singletonList(dummyScene.getWindow()));
		dummySceneFire.getWindow().fireEvent(createScrollEvent(1, 2, 5, 0));
		Mockito.verify(handler, Mockito.never()).fsmStops();
		Mockito.verify(handler, Mockito.never()).fsmStarts();
	}

	@Test
	void testScrollingNoActionWhenNullRegisteredWindow() throws CancelFSMException {
		final Scene dummyScene = new Scene(new Button());
		Platform.runLater(() -> {
			final Stage stage = new Stage();
			stage.setScene(dummyScene);
		});
		WaitForAsyncUtils.waitForFxEvents();

		interaction.registerToWindows(null);
		dummyScene.getWindow().fireEvent(createScrollEvent(1, 2, 5, 0));
		Mockito.verify(handler, Mockito.never()).fsmStops();
		Mockito.verify(handler, Mockito.never()).fsmStarts();
	}

	@Test
	void testScrollingNoActionWhenContainsNullRegisteredWindow() throws CancelFSMException {
		final Scene dummyScene = new Scene(new Button());
		Platform.runLater(() -> {
			final Stage stage = new Stage();
			stage.setScene(dummyScene);
		});
		WaitForAsyncUtils.waitForFxEvents();

		interaction.registerToWindows(Collections.singletonList(null));
		dummyScene.getWindow().fireEvent(createScrollEvent(1, 2, 5, 0));
		Mockito.verify(handler, Mockito.never()).fsmStops();
		Mockito.verify(handler, Mockito.never()).fsmStarts();
	}
}
