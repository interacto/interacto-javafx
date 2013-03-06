package org.malai.ex.draw.mapping;

import org.malai.ex.draw.model.MyDrawing;
import org.malai.ex.draw.view.MyViewDrawing;
import org.malai.mapping.Object2ObjectMapping;

/*
 * This mapping maps the drawing to its view.
 * Each time the drawing is modified (see the operation MyDrawing.setModified),
 * this mapping is (indirectly) notified through the operation onObjectModified.
 * 
 * This mapping is an object-to-object mapping, i.e. its mapping one object to one another.
 * Malai proposes several types of mapping (see ShapeList2ViewShapeList for another one).
 */
public class Drawing2ViewDrawingMapping extends Object2ObjectMapping<MyDrawing,MyViewDrawing> {
	
	public Drawing2ViewDrawingMapping(final MyDrawing source, final MyViewDrawing target) {
		super(source, target);
	}


	/*
	 * When this mapping is notified of a change from the source object, the target object
	 * (the drawing view) is updated.
	 */
	@Override
	public void onObjectModified(final Object object) {
		targetObject.update();
	}
}
