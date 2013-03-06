package org.malai.ex.draw.view;

import java.awt.BasicStroke;
import java.awt.Graphics2D;
import java.awt.Stroke;
import java.awt.geom.Path2D;
import java.awt.geom.Rectangle2D;

import org.malai.ex.draw.model.MyShape;

public abstract class MyViewShape<T extends MyShape> {
	protected Path2D path;
	
	protected T model;
	
	protected Stroke stroke;
	
	
	public MyViewShape(T model) {
		super();
		this.model = model;
		path = new Path2D.Double();
	}

	
	public void paint(Graphics2D g) {
		g.setColor(model.getLineColor());
		g.setStroke(stroke);
		g.draw(path);
		
		if(model.isFilled())
			g.fill(path);
	}
	
	
	public Rectangle2D getBorder() {
		return stroke.createStrokedShape(path).getBounds2D();
	}
	
	
	public boolean contains(double x, double y) {
		if(model.isFilled())
			return path.contains(x, y) || stroke.createStrokedShape(path).contains(x, y);
		return stroke.createStrokedShape(path).contains(x, y);
	}
	
	
	public void update() {
		stroke = new BasicStroke((float) model.getThickness());
	}
}
