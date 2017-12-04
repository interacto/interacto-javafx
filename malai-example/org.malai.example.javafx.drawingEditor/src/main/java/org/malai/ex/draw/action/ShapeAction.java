package org.malai.ex.draw.action;

import org.malai.action.ActionImpl;
import org.malai.ex.draw.model.MyShape;

abstract class ShapeAction extends ActionImpl {
	MyShape shape;

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
