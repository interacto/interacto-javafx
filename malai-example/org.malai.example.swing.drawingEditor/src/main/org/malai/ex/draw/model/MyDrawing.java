package org.malai.ex.draw.model;

import java.util.List;

import org.malai.mapping.ActiveArrayList;
import org.malai.mapping.MappingRegistry;
import org.malai.presentation.AbstractPresentation;

/*
 * Defines the model of the application: a drawing containing
 * shapes. In Malai such model is called an abstract presentation.
 * So, this class implements the Malai interface AbstractPresentation.
 */
public class MyDrawing implements AbstractPresentation {
	protected List<MyShape> shapes;
	
	protected boolean modified;
	
	
	public MyDrawing() {
		super();
		/*
		 * This list is an active list. It means that it can have
		 * observers listening for any change in the list.
		 * It is useful to notify the view that, for instance, a
		 * shapes ha been added to the list.
		 */
		shapes = new ActiveArrayList<>();
		modified = false;
	}

	
	public List<MyShape> getShapes() {
		return shapes;
	}
	
	
	public boolean addShape(MyShape sh) {
		return shapes.add(sh);
	}
	
	public boolean removeShape(MyShape sh) {
		return shapes.remove(sh);
	}


	@Override
	public boolean isModified() {
		boolean mod = modified;
		
		for(int i=0, size=shapes.size(); !modified && i<size; i++)
			mod = shapes.get(i).isModified();
		
		return mod;
	}


	/*
	 * Defines whether the drawing has been modified.
	 */
	@Override
	public void setModified(boolean mod) {
		modified = mod;
		
		/*
		 * If the drawing has been modified (or one of its shape),
		 * the mapping registry is notified to update its possible
		 * concrete presentations.
		 * This implies that a mapping between this abstract presentation
		 * and a concrete one exists. Otherwise, this step is not
		 * necessary.
		 * See the class Drawing2DrawingViewMapping for such a mapping.
		 */
		if(modified)
			MappingRegistry.REGISTRY.onObjectModified(this);
	}


	@Override
	public void reinit() {
		// Nothing to do for the moment.
	}
}
