package org.malai.ex.draw.action;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import org.malai.ex.draw.model.MyShape;
import org.malai.undo.Undoable;

public class MoveShape extends ShapeAction implements Undoable {
	private double mementoX;
	private double mementoY;
	private final DoubleProperty newX;
	private final DoubleProperty newY;

	public MoveShape(final MyShape shape) {
		super(shape);
		newX = new SimpleDoubleProperty();
		newY = new SimpleDoubleProperty();
	}

	@Override
	protected void doActionBody() {
		redo();
	}

	@Override
	protected void createMemento() {
		mementoX = shape.getX();
		mementoY = shape.getY();
	}

	@Override
	public void undo() {
		shape.setX(mementoX);
		shape.setY(mementoY);
	}

	@Override
	public void redo() {
		shape.setX(newX.doubleValue());
		shape.setY(newY.doubleValue());
	}

	public void setCoord(final double newX, final double newY) {
		this.newX.set(newX);
		this.newY.set(newY);
	}

	@Override
	public String getUndoName() {
		return "Shape moved";
	}
}
