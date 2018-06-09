package org.malai.javafx.interaction.binding;

import javafx.application.Platform;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import org.junit.jupiter.api.Test;
import org.malai.command.CommandImpl;
import org.malai.javafx.binding.NodeBinder;
import org.malai.javafx.interaction.library.DnD;
import org.malai.undo.Undoable;
import org.testfx.util.WaitForAsyncUtils;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class TestNodeBinderCancelDnD extends TestNodeBinder<Pane> {
	Rectangle rec;

	@Override
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
	public void testCanCancelDnD() {
		new NodeBinder<>(new DnD(true, true), i -> new MoveShape(rec), instrument).
			on(rec).
			first((i, c) -> rec.requestFocus()).
			then((i, c) -> c.setCoord(rec.getX() + (i.getTgtScenePoint().getX() - i.getSrcScenePoint().getX()),
				rec.getY() + (i.getTgtScenePoint().getY() - i.getSrcScenePoint().getY()))).
			end((i, c) -> fail("")).
			exec().
			bind();
		drag(rec).moveBy(100, 100).type(KeyCode.ESCAPE).sleep(50L);
		assertEquals(0, instrument.exec.get());
	}

	@Test
	public void testCanCancelDnDWithObsList() {
		new NodeBinder<>(new DnD(true, true), i -> new MoveShape((Rectangle) i.getSrcObject().get()), instrument).
			on(widget1.getChildren()).
			first((i, c) -> Platform.runLater(() -> i.getSrcObject().get().requestFocus())).
			then((i, c) -> c.setCoord(((Rectangle) i.getSrcObject().get()).getX() + (i.getTgtScenePoint().getX() - i.getSrcScenePoint().getX()),
				((Rectangle) i.getSrcObject().get()).getY() + (i.getTgtScenePoint().getY() - i.getSrcScenePoint().getY()))).
			end((i, c) -> fail("")).
			exec().
			bind();

		final Rectangle rec2 = new Rectangle(200d, 200d, 70d, 50d);
		Platform.runLater(() -> widget1.getChildren().addAll(rec2));
		WaitForAsyncUtils.waitForFxEvents();
		drag(rec2).moveBy(100, 100).type(KeyCode.ESCAPE).sleep(50L);
		assertEquals(0, instrument.exec.get());
	}
}

class MoveShape extends CommandImpl implements Undoable {
	private double mementoX;
	private double mementoY;
	private final DoubleProperty newX;
	private final DoubleProperty newY;
	private final Rectangle rec;

	public MoveShape(final Rectangle shape) {
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
	public String getUndoName() {
		return "Shape moved";
	}

	@Override
	public boolean canDo() {
		return rec != null;
	}
}
