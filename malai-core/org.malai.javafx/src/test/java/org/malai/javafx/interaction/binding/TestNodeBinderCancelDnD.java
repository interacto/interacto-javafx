package org.malai.javafx.interaction.binding;

import javafx.application.Platform;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import org.junit.jupiter.api.Test;
import org.malai.action.ActionImpl;
import org.malai.javafx.binding.NodeBinder;
import org.malai.javafx.interaction.library.CancellableDnD;
import org.malai.undo.Undoable;
import org.testfx.util.WaitForAsyncUtils;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class TestNodeBinderCancelDnD extends TestNodeBinder<Pane> {
	Rectangle rec;

	@Override
	public void start(Stage stage) {
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
	public void testCanCancelDnD() throws InstantiationException, IllegalAccessException {
		new NodeBinder<>(MoveShape.class, new CancellableDnD(true), instrument).
			map(i -> new MoveShape(rec)).
			on(rec).
			first((a, i) -> rec.requestFocus()).
			then((a, i) -> a.setCoord(rec.getX() + (i.getEndScenePt().getX() - i.getSrcScenePoint().getX()),
				rec.getY() + (i.getEndScenePt().getY() - i.getSrcScenePoint().getY()))).
			end((a, i) -> fail("")).
			exec().
			bind();
		drag(rec).moveBy(100, 100).type(KeyCode.ESCAPE).sleep(50L);
		assertEquals(0, instrument.exec.get());
	}

	@Test
	public void testCanCancelDnDWithObsList() throws InstantiationException, IllegalAccessException {
		new NodeBinder<>(MoveShape.class, new CancellableDnD(true), instrument).
			map(i -> new MoveShape((Rectangle) i.getSrcObject().get())).
			on(widget1.getChildren()).
			first((a, i) -> Platform.runLater(() -> i.getSrcObject().get().requestFocus())).
			then((a, i) -> a.setCoord(((Rectangle) i.getSrcObject().get()).getX() + (i.getEndScenePt().getX() - i.getSrcScenePoint().getX()),
				((Rectangle) i.getSrcObject().get()).getY() + (i.getEndScenePt().getY() - i.getSrcScenePoint().getY()))).
			end((a, i) -> fail("")).
			exec().
			bind();

		Rectangle rec2 = new Rectangle(200d, 200d, 70d, 50d);
		Platform.runLater(() -> widget1.getChildren().addAll(rec2));
		WaitForAsyncUtils.waitForFxEvents();
		drag(rec2).moveBy(100, 100).type(KeyCode.ESCAPE).sleep(50L);
		assertEquals(0, instrument.exec.get());
	}
}

class MoveShape extends ActionImpl implements Undoable {
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
	protected void doActionBody() {
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
