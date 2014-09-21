package org.malai.ex.draw.view.shape;

import javafx.scene.canvas.GraphicsContext;

import org.malai.ex.draw.model.MyRect;

public class MyViewRect extends MyViewShape<MyRect> {

	public MyViewRect(MyRect model) {
		super(model);
//		update();
	}

	
	@Override
	protected void drawShape(final boolean fill, final GraphicsContext gc) {
		gc.strokeRect(model.getX(), model.getY(), model.getWidth(), model.getHeight());
	}
}
