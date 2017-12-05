package org.malai.ex.draw.model;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.Property;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.paint.Color;

public abstract class MyShape {
	protected final Property<Color> lineColor;
	protected final DoubleProperty x;
	protected final DoubleProperty y;


	public MyShape(final double x, final double y) {
		super();
		lineColor = new SimpleObjectProperty<>(Color.BLACK);
		this.x = new SimpleDoubleProperty(x);
		this.y = new SimpleDoubleProperty(y);
	}

	public void setLineColor(final Color color) {
		if(color != null) {
			lineColor.setValue(color);
		}
	}

	public Property<Color> lineColorProperty() {
		return lineColor;
	}

	public Color getLineColor() {
		return lineColor.getValue();
	}

	public double getX() {
		return x.get();
	}

	public double getY() {
		return y.get();
	}

	public void setX(final double x) {
		this.x.set(x);
	}

	public void setY(final double y) {
		this.y.set(y);
	}

	public DoubleProperty xProperty() {
		return x;
	}

	public DoubleProperty yProperty() {
		return y;
	}
}
