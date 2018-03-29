package org.malai.ex.draw.command;

import javafx.scene.paint.Color;
import org.malai.ex.draw.model.MyShape;
import org.malai.undo.Undoable;

public class ChangeColour extends ShapeCmd implements Undoable {
	private Color newCol;
	private Color mementoCol;

	public ChangeColour(final Color col, final MyShape sh) {
		super(sh);
		newCol = col;
	}

	@Override
	protected void doCmdBody() {
		redo();
	}

	/*
	 * This command needs a memento to save the previous state of the modified object:
	 * its colour. This operation is automatically called a single time before the first execution of the
	 * command to produce the memento (here the former colour of the shape to modify).
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
