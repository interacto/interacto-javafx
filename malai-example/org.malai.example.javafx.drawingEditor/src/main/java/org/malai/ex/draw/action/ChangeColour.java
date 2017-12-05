package org.malai.ex.draw.action;

import javafx.scene.paint.Color;
import org.malai.ex.draw.model.MyShape;
import org.malai.undo.Undoable;

public class ChangeColour extends ShapeAction implements Undoable {
	private Color newCol;
	private Color mementoCol;

	public ChangeColour(final Color col, final MyShape sh) {
		super(sh);
		newCol = col;
	}

	@Override
	protected void doActionBody() {
		redo();
	}

	/*
	 * This action needs a memento to save the previous state of the modified object:
	 * its colour. This operation is automatically called a single time before the first execution of the
	 * action to produce the memento (here the former colour of the shape to modify).
	 */
	@Override
	protected void createMemento() {
		mementoCol = shape.getLineColor();
	}

	@Override
	public void undo() {
		shape.setLineColor(mementoCol);
	}

	@Override
	public void redo() {
		shape.setLineColor(newCol);
	}

	@Override
	public boolean canDo() {
		return super.canDo() && newCol != null;
	}

	@Override
	public String getUndoName() {
		return "Color changed";
	}
}
