package org.malai.ex.draw.model;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;

public class MyRect extends MyShape {
	protected final DoubleProperty x = new SimpleDoubleProperty();
	protected final DoubleProperty y  = new SimpleDoubleProperty();
	protected final DoubleProperty width = new SimpleDoubleProperty();
	protected final DoubleProperty height = new SimpleDoubleProperty();
	
	
	public MyRect() {
		super();
		x.set(0.);
		y.set(0.);
		width.set(1.);
		height.set(1.);
	}


	public double getX() {return x.get();}
	public void setX(double x) {
		this.x.set(x);
	}

	public double getY() {return y.get();}
	public void setY(double y) {
		this.y.set(y);
	}

	public double getWidth() {return width.get();}
	public void setWidth(double width) {
		this.width.set(width);
	}


	public double getHeight() {return height.get();}
	public void setHeight(double height) {
		this.height.set(height);
	}
}
