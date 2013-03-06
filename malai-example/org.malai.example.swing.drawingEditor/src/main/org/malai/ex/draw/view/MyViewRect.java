package org.malai.ex.draw.view;

import org.malai.ex.draw.model.MyRect;

public class MyViewRect extends MyViewShape<MyRect> {

	public MyViewRect(MyRect model) {
		super(model);
		update();
	}

	
	@Override
	public void update() {
		double x = model.getX();
		double y = model.getY();
		double width = model.getWidth();
		double height = model.getHeight();

		super.update();
		path.reset();
		path.moveTo(x, y);
		path.lineTo(x+width, y);
		path.lineTo(x+width, y+height);
		path.lineTo(x, y+height);
		path.closePath();
	}
}
