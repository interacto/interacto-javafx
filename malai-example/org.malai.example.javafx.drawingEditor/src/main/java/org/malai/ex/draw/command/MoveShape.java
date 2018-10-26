package org.malai.ex.draw.command;

import java.util.ResourceBundle;
import javafx.beans.binding.DoubleBinding;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import org.malai.command.AutoUnbind;
import org.malai.ex.draw.model.MyShape;
import org.malai.undo.Undoable;

public class MoveShape extends ShapeCmd implements Undoable {
	private double mementoX;
	private double mementoY;
	@AutoUnbind private final DoubleProperty newX;
	@AutoUnbind private final DoubleProperty newY;

	public MoveShape(final MyShape shape, final DoubleBinding xBinding, final DoubleBinding yBinding) {
		super(shape);
		newX = new SimpleDoubleProperty();
		newY = new SimpleDoubleProperty();
		newX.bind(xBinding);
		newY.bind(yBinding);
	}

	@Override
	protected void doCmdBody() {
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

	@Override
	public String getUndoName(final ResourceBundle bundle) {
		return "Shape moved";
	}
}
