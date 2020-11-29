/*
 * Interacto
 * Copyright (C) 2020 Arnaud Blouin
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * You should have received a copy of the GNU General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 */
package io.github.interacto.jfx.instrument;

import io.github.interacto.command.library.Zoom;
import io.github.interacto.jfx.test.BindingsContext;
import io.github.interacto.jfx.test.WidgetBindingExtension;
import io.github.interacto.properties.Zoomable;
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
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.api.FxRobot;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;
import org.testfx.util.WaitForAsyncUtils;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;

@ExtendWith(ApplicationExtension.class)
@ExtendWith(WidgetBindingExtension.class)
public class TestBasicZoomer {
	BasicZoomer<StubZoomable> zoomer;
	StubZoomable canvas;

	@Start
	public void start(final Stage stage) {
		canvas = new StubZoomable();
		canvas.setWidth(100);
		canvas.setHeight(100);
		final VBox parent = new VBox();
		parent.getChildren().add(canvas);
		final Scene scene = new Scene(parent);
		stage.setScene(scene);
		stage.show();
		stage.toFront();
		stage.centerOnScreen();
		stage.requestFocus();
	}

	@BeforeEach
	void setUp() {
		zoomer = new BasicZoomer<>();
		zoomer.setWithKeys(true);
		zoomer.setZoomable(canvas);
		zoomer.setActivated(true);
		Platform.runLater(() -> canvas.requestFocus());
		WaitForAsyncUtils.waitForFxEvents();
	}

	@Test
	void testGetZoomable() {
		assertSame(canvas, zoomer.getZoomable());
	}

	@Disabled("headless server does not support key modifiers yet")
	@Test
	void testCtrlScrollUp(final FxRobot robot, final BindingsContext ctx) {
		robot.clickOn(canvas);
		robot.press(KeyCode.CONTROL).scroll(VerticalDirection.UP).scroll(VerticalDirection.UP).release(KeyCode.CONTROL);
		WaitForAsyncUtils.waitForFxEvents();

		ctx.cmdsProduced(2);
		assertEquals(4d, canvas.getZoom(), 0.00001);
	}

	@Disabled("headless server does not support key modifiers yet")
	@Test
	void testCtrlScrollDown(final FxRobot robot, final BindingsContext ctx) {
		robot.clickOn(canvas);
		robot.press(KeyCode.CONTROL).scroll(VerticalDirection.DOWN).release(KeyCode.CONTROL);
		WaitForAsyncUtils.waitForFxEvents();

		ctx.oneCmdProduced(Zoom.class);
		assertEquals(1d, canvas.getZoom(), 0.00001);
	}

//	@Disabled("headless server does not support key modifiers yet")
	@Test
	void testCtrlBadKey(final FxRobot robot, final BindingsContext ctx) {
		robot.clickOn(canvas);
		robot.press(KeyCode.CONTROL, KeyCode.S).release(KeyCode.S, KeyCode.CONTROL);
		WaitForAsyncUtils.waitForFxEvents();

		ctx.noCmdProduced();
		assertEquals(2d, canvas.getZoom(), 0.00001);
	}

//	@Disabled("headless server does not support key modifiers yet")
	@Test
	void testKeyAdd(final FxRobot robot, final BindingsContext ctx) {
		robot.clickOn(canvas).type(KeyCode.ADD);
		WaitForAsyncUtils.waitForFxEvents();

		ctx.oneCmdProduced(Zoom.class);
		assertEquals(3d, canvas.getZoom(), 0.00001);
	}

	@Test
	void testKeyPlus(final FxRobot robot, final BindingsContext ctx) {
		robot.clickOn(canvas).type(KeyCode.PLUS);
		WaitForAsyncUtils.waitForFxEvents();

		ctx.oneCmdProduced(Zoom.class);
		assertEquals(3d, canvas.getZoom(), 0.00001);
	}

	@Test
	void testKeyShiftEquals(final FxRobot robot, final BindingsContext ctx) {
		robot.clickOn(canvas).press(KeyCode.SHIFT).type(KeyCode.EQUALS);
		WaitForAsyncUtils.waitForFxEvents();

		ctx.oneCmdProduced(Zoom.class);
		assertEquals(3d, canvas.getZoom(), 0.00001);
	}

	@Test
	void testKeyMinus(final FxRobot robot, final BindingsContext ctx) {
		robot.clickOn(canvas).type(KeyCode.MINUS);
		WaitForAsyncUtils.waitForFxEvents();

		ctx.oneCmdProduced(Zoom.class);
		assertEquals(1d, canvas.getZoom(), 0.00001);
	}

	@Test
	void testKeySub(final FxRobot robot, final BindingsContext ctx) {
		robot.clickOn(canvas).type(KeyCode.SUBTRACT);
		WaitForAsyncUtils.waitForFxEvents();

		ctx.oneCmdProduced(Zoom.class);
		assertEquals(1d, canvas.getZoom(), 0.00001);
	}

	public static class StubZoomable extends Canvas implements Zoomable {
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
