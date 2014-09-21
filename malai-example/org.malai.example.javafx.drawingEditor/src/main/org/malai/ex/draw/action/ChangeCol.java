package org.malai.ex.draw.action;

import javafx.scene.paint.Color;

import org.malai.action.Action;

public class ChangeCol extends Action {
	
//	private Color oldCol;
	protected Color newCol;
	

	public ChangeCol() {
	}

	@Override
	public boolean isRegisterable() {
		return true;
	}

	@Override
	protected void doActionBody() {
		System.out.println("new colour: " + newCol);
	}

	@Override
	public boolean canDo() {
		return newCol!=null;
	}

	/**
	 * @param col the colour to use.
	 */
	public void setNewCol(final Color col) {
		this.newCol = col;
	}
}
