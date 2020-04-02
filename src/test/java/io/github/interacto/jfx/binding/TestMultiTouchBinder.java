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
package io.github.interacto.jfx.binding;

import io.github.interacto.jfx.interaction.library.BaseJfXInteractionTest;
import io.github.interacto.jfx.test.BindingsContext;
import io.github.interacto.jfx.test.WidgetBindingExtension;
import javafx.scene.canvas.Canvas;
import javafx.stage.Stage;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(ApplicationExtension.class)
@ExtendWith(WidgetBindingExtension.class)
public class TestMultiTouchBinder extends BaseNodeBinderTest<Canvas> {
	@Override
	@Start
	public void start(final Stage stage) {
		widget1 = new Canvas();
		widget2 = new Canvas();
		super.start(stage);
	}

	@Test
	public void testProduceACommand(final BindingsContext ctx) {
		binding = Bindings.multiTouchBinder(2)
			.toProduce(i -> new StubCmd())
			.on(widget1)
			.bind();

		widget1.fireEvent(BaseJfXInteractionTest.createTouchPressEvent(11, 23, 1, null));
		widget1.fireEvent(BaseJfXInteractionTest.createTouchPressEvent(21, 13, 2, null));
		widget1.fireEvent(BaseJfXInteractionTest.createTouchMoveEvent(210, 130, 2, null));
		widget1.fireEvent(BaseJfXInteractionTest.createTouchReleaseEvent(210, 130, 2, null));
		ctx.oneCmdProduced(StubCmd.class);
		assertNotNull(binding);
	}
}
