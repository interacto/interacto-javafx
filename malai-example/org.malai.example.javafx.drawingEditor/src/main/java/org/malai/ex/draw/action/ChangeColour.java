package org.malai.ex.draw.action;

import javafx.scene.paint.Color;
import org.malai.undo.Undoable;

public class ChangeColour extends ShapeAction implements Undoable {
	private Color newCol;
	private Color mementoCol;

	public ChangeColour() {
		super();
	}

	@Override
	protected void doActionBody() {
		mementoCol = shape.getLineColor();
		redo();
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

	/**
	 * @param col the colour to use.
	 */
	public void setNewCol(final Color col) {
		this.newCol = col;
	}

	@Override
	public String getUndoName() {
		return "Color changed";
	}
}
