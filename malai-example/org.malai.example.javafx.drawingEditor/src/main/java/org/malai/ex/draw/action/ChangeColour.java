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

	@Override
	public String getUndoName() {
		return "Color changed";
	}
}
