/*
 * Interacto
 * Copyright (C) 2019 Arnaud Blouin
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
package io.github.interacto.jfx.interaction;

import io.github.interacto.jfx.binding.Bindings;
import io.github.interacto.jfx.binding.TestBinder;
import io.github.interacto.jfx.test.WidgetBindingExtension;
import java.util.concurrent.atomic.AtomicInteger;
import javafx.application.Platform;
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

@ExtendWith(ApplicationExtension.class)
@ExtendWith(WidgetBindingExtension.class)
public class TestKeyLost {
	Canvas canvas;
	Stage stage;
	AtomicInteger cpt;

	@Start
	public void start(final Stage stageToConfigure) {
		canvas = new Canvas();
		final VBox parent = new VBox();
		parent.getChildren().add(canvas);
		final Scene scene = new Scene(parent);
		stageToConfigure.setScene(scene);
		stageToConfigure.show();
		stageToConfigure.toFront();
		stageToConfigure.centerOnScreen();
		stageToConfigure.requestFocus();
		stage = stageToConfigure;
	}

	@BeforeEach
	void setUp() {
		cpt = new AtomicInteger();
		WaitForAsyncUtils.waitForFxEvents();
		Platform.runLater(() -> canvas.requestFocus());
		WaitForAsyncUtils.waitForFxEvents();

		Bindings.shortcutBinder()
			.toProduce(TestBinder.StubCmd::new)
			.with(KeyCode.CONTROL, KeyCode.C)
			.on(canvas)
			.end(i -> cpt.incrementAndGet())
			.bind();
	}

	@Disabled
	@Test
	void testLostWindowFocus(final FxRobot robot) {
		robot.press(KeyCode.ALT);
		Platform.runLater(() -> stage.setIconified(true));
		WaitForAsyncUtils.waitForFxEvents(40);
		Platform.runLater(() -> stage.setIconified(false));
		WaitForAsyncUtils.waitForFxEvents(40);
		Platform.runLater(() -> canvas.requestFocus());
		WaitForAsyncUtils.waitForFxEvents();
		robot.press(KeyCode.CONTROL).type(KeyCode.C);

		assertEquals(1, cpt.get());
	}
}
