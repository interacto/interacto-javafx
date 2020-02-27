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

import io.github.interacto.command.CommandImpl;
import io.github.interacto.jfx.interaction.library.DnD;
import io.github.interacto.jfx.test.BindingsContext;
import io.github.interacto.jfx.test.WidgetBindingExtension;
import io.github.interacto.undo.Undoable;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.api.FxRobot;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;
import org.testfx.util.WaitForAsyncUtils;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(ApplicationExtension.class)
@ExtendWith(WidgetBindingExtension.class)
public class TestNodeBinderCancelDnD extends BaseNodeBinderTest<Pane> {
	Rectangle rec;

	@Override
	@Start
	public void start(final Stage stage) {
		widget1 = new Pane();
		widget2 = new Pane();
		widget1.setPrefWidth(400d);
		widget1.setPrefHeight(400d);
		widget2.setPrefWidth(400d);
		widget2.setPrefHeight(400d);
		rec = new Rectangle(100d, 100d, 70d, 50d);
		widget1.getChildren().addAll(rec);
		super.start(stage);
	}

	@Test
	public void testCanCancelDnD(final FxRobot robot, final BindingsContext ctx) {
		binding = Bindings.nodeBinder()
			.usingInteraction(() -> new DnD(true, true))
			.toProduce(() -> new MoveShape(rec))
			.on(rec)
			.first((i, c) -> rec.requestFocus())
			.then((i, c) -> c.setCoord(rec.getX() + (i.getTgtScenePoint().getX() - i.getSrcScenePoint().getX()),
				rec.getY() + (i.getTgtScenePoint().getY() - i.getSrcScenePoint().getY())))
			.continuousExecution()
			.bind();
		robot.drag(rec).moveBy(100, 100).type(KeyCode.ESCAPE).sleep(50L);
		ctx.noCmdProduced();
		assertEquals(1, binding.getTimesCancelled());
	}

	@Test
	public void testCanCancelDnDWithObsList(final FxRobot robot, final BindingsContext ctx) {
		binding = Bindings.nodeBinder()
			.usingInteraction(() -> new DnD(true, true))
			.toProduce(i -> new MoveShape((Rectangle) i.getSrcObject().orElseThrow()))
			.on(widget1.getChildren())
			.first((i, c) -> Platform.runLater(() -> i.getSrcObject().orElseThrow().requestFocus()))
			.then((i, c) -> c.setCoord(((Rectangle) i.getSrcObject().orElseThrow()).getX() + (i.getTgtScenePoint().getX() - i.getSrcScenePoint().getX()),
				((Rectangle) i.getSrcObject().get()).getY() + (i.getTgtScenePoint().getY() - i.getSrcScenePoint().getY())))
			.continuousExecution()
			.bind();

		final Rectangle rec2 = new Rectangle(200d, 200d, 70d, 50d);
		Platform.runLater(() -> widget1.getChildren().addAll(rec2));
		WaitForAsyncUtils.waitForFxEvents();
		robot.drag(rec2).moveBy(100, 100).type(KeyCode.ESCAPE).sleep(50L);
		ctx.noCmdProduced();
		assertEquals(1, binding.getTimesCancelled());
	}
}

class MoveShape extends CommandImpl implements Undoable {
	private double mementoX;
	private double mementoY;
	private final DoubleProperty newX;
	private final DoubleProperty newY;
	private final Rectangle rec;

	MoveShape(final Rectangle shape) {
		super();
		rec = shape;
		newX = new SimpleDoubleProperty();
		newY = new SimpleDoubleProperty();
	}

	@Override
	protected void doCmdBody() {
		redo();
	}

	@Override
	protected void createMemento() {
		mementoX = rec.getX();
		mementoY = rec.getY();
	}

	@Override
	public void undo() {
		rec.setX(mementoX);
		rec.setY(mementoY);
	}

	@Override
	public void redo() {
		rec.setX(newX.doubleValue());
		rec.setY(newY.doubleValue());
	}

	public void setCoord(final double newX, final double newY) {
		this.newX.set(newX);
		this.newY.set(newY);
	}

	@Override
	public String getUndoName(final ResourceBundle bundle) {
		return "Shape moved";
	}

	@Override
	public boolean canDo() {
		return rec != null;
	}
}
