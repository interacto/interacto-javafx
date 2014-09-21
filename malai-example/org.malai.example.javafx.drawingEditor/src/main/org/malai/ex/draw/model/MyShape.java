package org.malai.ex.draw.model;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.Property;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.paint.Color;

import org.malai.mapping.MappingRegistry;
import org.malai.properties.Modifiable;

public abstract class MyShape implements Modifiable {
	protected final Property<Color> lineColor = new SimpleObjectProperty<>();
	protected final DoubleProperty thickness = new SimpleDoubleProperty();
	protected final BooleanProperty filled = new SimpleBooleanProperty();
	
	protected boolean modified;


	public MyShape() {
		super();
		modified = false;
		lineColor.setValue(Color.BLACK);
		filled.set(true);
		thickness.setValue(1.);
	}
	
	
	@Override
	public boolean isModified() {
		return modified;
	}

	@Override
	public void setModified(boolean mod) {
		/*
		 * If the shape has been modified, the mapping registry is 
		 * notified to update its possible view.
		 * This implies that a mapping between this shape, or a list of shapes,
		 * and its view, or a list of views. Otherwise, this step is not
		 * necessary.
		 * See the class ListShape2ListViewShapeMapping for such a mapping.
		 */
		if(mod)
			MappingRegistry.REGISTRY.onObjectModified(this);

		modified = mod;
	}

	public Color getLineColor() { return lineColor.getValue();}
	public Property<Color> getLineColorProp() { return lineColor;}

	public void setLineColor(Color color) {
		if(color!=null) lineColor.setValue(color);
	}

	public double getThickness() {return thickness.get();}
	public DoubleProperty getThicknessProp() {return thickness;}

	public void setThickness(double thick) {
		if(thick>0) thickness.setValue(thick);
	}

	public boolean isFilled() { return filled.get();}
	public BooleanProperty getIsFilledProp() { return filled;}

	public void setFilled(boolean filled) {
		this.filled.set(filled);
	}
}
