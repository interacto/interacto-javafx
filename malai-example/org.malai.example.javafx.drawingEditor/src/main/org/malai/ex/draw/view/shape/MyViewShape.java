package org.malai.ex.draw.view.shape;

import javafx.scene.canvas.GraphicsContext;

import org.malai.ex.draw.model.MyShape;

public abstract class MyViewShape<T extends MyShape> {
	protected T model;
	
	public MyViewShape(T model) {
		super();
		this.model = model;
	}

	
	public void paint(final GraphicsContext gc) {
		gc.setLineWidth(model.getThickness());
		gc.setStroke(model.getLineColor());
		drawShape(false, gc);
		
		if(model.isFilled())
			drawShape(true, gc);
	}
	
	
	protected abstract void drawShape(final boolean fill, final GraphicsContext gc);
	
	
//	public Rectangle2D getBorder() {
//		return new Rectangle2D(0, 0, 10, 10); //stroke.createStrokedShape(path).getBounds2D();
//	}
	
	
//	public boolean contains(double x, double y) {
//		return false;
////		if(model.isFilled())
////			return view.contains(x, y) || stroke.createStrokedShape(path).contains(x, y);
////		return stroke.createStrokedShape(path).contains(x, y);
//	}
}
