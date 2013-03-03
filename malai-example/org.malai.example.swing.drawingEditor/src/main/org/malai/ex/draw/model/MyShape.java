package org.malai.ex.draw.model;

import java.awt.Color;

import org.malai.mapping.MappingRegistry;
import org.malai.properties.Modifiable;

public abstract class MyShape implements Modifiable {
	protected Color lineColor;
	protected double thickness;
	protected boolean filled;
	protected boolean modified;


	public MyShape() {
		super();
		modified = false;
		lineColor = Color.BLACK;
		filled = true;
		thickness = 1.;
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

	public Color getLineColor() {
		return lineColor;
	}

	public void setLineColor(Color lineColor) {
		this.lineColor = lineColor;
	}

	public double getThickness() {
		return thickness;
	}

	public void setThickness(double thickness) {
		this.thickness = thickness;
	}

	public boolean isFilled() {
		return filled;
	}

	public void setFilled(boolean filled) {
		this.filled = filled;
	}
}
