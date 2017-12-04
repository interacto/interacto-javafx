package org.malai.ex.draw.model;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;

public class MyRect extends MyShape {
	private final DoubleProperty x;
	private final DoubleProperty y;
	private final DoubleProperty width;
	private final DoubleProperty height;

	public MyRect(final double x, final double y) {
		super();
		this.x = new SimpleDoubleProperty(x);
		this.y = new SimpleDoubleProperty(y);
		width = new SimpleDoubleProperty(1d);
		height = new SimpleDoubleProperty(1d);
	}


	public double getX() {
		return x.get();
	}

	public double getY() {
		return y.get();
	}

	public void setWidth(final double width) {
		if(width > 0d) {
			this.width.set(width);
		}
	}


	public void setHeight(final double height) {
		if(height > 0d) {
			this.height.set(height);
		}
	}

	public DoubleProperty xProperty() {
		return x;
	}

	public DoubleProperty yProperty() {
		return y;
	}

	public DoubleProperty widthProperty() {
		return width;
	}

	public DoubleProperty heightProperty() {
		return height;
	}
}
