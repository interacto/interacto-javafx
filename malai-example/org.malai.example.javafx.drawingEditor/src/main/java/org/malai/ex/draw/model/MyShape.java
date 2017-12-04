package org.malai.ex.draw.model;

import javafx.beans.property.Property;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.paint.Color;

public abstract class MyShape {
	protected final Property<Color> lineColor;


	public MyShape() {
		super();
		lineColor = new SimpleObjectProperty<>(Color.BLACK);
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
}
