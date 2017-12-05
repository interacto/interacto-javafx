package org.malai.ex.draw.model;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;

public class MyRect extends MyShape {
	private final DoubleProperty width;
	private final DoubleProperty height;

	public MyRect(final double x, final double y) {
		super(x, y);
		width = new SimpleDoubleProperty(1d);
		height = new SimpleDoubleProperty(1d);
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

	public DoubleProperty widthProperty() {
		return width;
	}

	public DoubleProperty heightProperty() {
		return height;
	}
}
