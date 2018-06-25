package org.malai.javafx.instrument;

import java.awt.Point;
import java.awt.geom.Point2D;
import javafx.application.Platform;
import javafx.geometry.VerticalDirection;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.malai.properties.Zoomable;
import org.testfx.framework.junit5.ApplicationTest;
import org.testfx.util.WaitForAsyncUtils;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;

public class TestBasicZoomer extends ApplicationTest {
	BasicZoomer<StubZommable> zoomer;
	StubZommable canvas;

	@BeforeEach
	void setUp() {
	}

	@Override
	public void start(final Stage stage) throws Exception {
		super.start(stage);
		zoomer = new BasicZoomer<>();
		canvas = new StubZommable();
		canvas.setWidth(100);
		canvas.setHeight(100);
		zoomer.setWithKeys(true);
		zoomer.setZoomable(canvas);
		final VBox parent = new VBox();
		parent.getChildren().add(canvas);
		final Scene scene = new Scene(parent);
		stage.setScene(scene);
		stage.show();
		stage.toFront();
		stage.centerOnScreen();
		stage.requestFocus();
		zoomer.setActivated(true);
		Platform.runLater(() -> canvas.requestFocus());
		WaitForAsyncUtils.waitForFxEvents();
	}

	@Test
	void testGetZoomable() {
		assertSame(canvas, zoomer.getZoomable());
	}

	@Test
	void testCtrlScrollUp() {
		clickOn(canvas);
		press(KeyCode.CONTROL).scroll(VerticalDirection.UP).scroll(VerticalDirection.UP).release(KeyCode.CONTROL);
		WaitForAsyncUtils.waitForFxEvents();
		assertEquals(4d, canvas.getZoom(), 0.00001);
	}

	@Test
	void testCtrlScrollDown() {
		clickOn(canvas);
		press(KeyCode.CONTROL).scroll(VerticalDirection.DOWN).release(KeyCode.CONTROL);
		WaitForAsyncUtils.waitForFxEvents();
		assertEquals(1d, canvas.getZoom(), 0.00001);
	}

	@Test
	void testCtrlBadKey() {
		clickOn(canvas);
		press(KeyCode.CONTROL, KeyCode.S).release(KeyCode.S, KeyCode.CONTROL);
		WaitForAsyncUtils.waitForFxEvents();
		assertEquals(2d, canvas.getZoom(), 0.00001);
	}

	@Test
	void testKeyAdd() {
		clickOn(canvas);
		type(KeyCode.ADD);
		WaitForAsyncUtils.waitForFxEvents();
		assertEquals(3d, canvas.getZoom(), 0.00001);
	}

	@Test
	void testKeyMinus() {
		clickOn(canvas);
		type(KeyCode.MINUS);
		WaitForAsyncUtils.waitForFxEvents();
		assertEquals(1d, canvas.getZoom(), 0.00001);
	}

	public static class StubZommable extends Canvas implements Zoomable {
		double zoom = 2;

		@Override
		public double getZoomIncrement() {
			return 1;
		}

		@Override
		public double getMaxZoom() {
			return 5;
		}

		@Override
		public double getMinZoom() {
			return 0;
		}

		@Override
		public double getZoom() {
			return zoom;
		}

		@Override
		public void setZoom(final double x, final double y, final double zoomLvl) {
			zoom = zoomLvl;
		}

		@Override
		public Point2D getZoomedPoint(final double v, final double v1) {
			return null;
		}

		@Override
		public Point2D getZoomedPoint(final Point point) {
			return null;
		}
	}
}
