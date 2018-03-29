package org.malai.ex.draw.command;

import org.malai.command.CommandImpl;
import org.malai.ex.draw.model.MyShape;

abstract class ShapeCmd extends CommandImpl {
	MyShape shape;

	public ShapeCmd(final MyShape shape) {
		super();
		this.shape = shape;
	}

	public MyShape getShape() {
		return shape;
	}

	public void setShape(final MyShape shape) {
		this.shape = shape;
	}

	@Override
	public boolean canDo() {
		return shape != null;
	}
}
