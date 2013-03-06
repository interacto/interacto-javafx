package org.malai.ex.draw.mapping;

import org.malai.ex.draw.model.MyShape;
import org.malai.ex.draw.view.MyViewShape;
import org.malai.mapping.Object2ObjectMapping;

public class Shape2ViewMapping extends Object2ObjectMapping<MyShape,MyViewShape<?>> {

	public Shape2ViewMapping(MyShape source, MyViewShape<?> target) {
		super(source, target);
	}

	@Override
	public void onObjectModified(Object object) {
		/*
		 * When the source object has been modified and this mapping
		 * notified, the target object, the view is updated.
		 */
		targetObject.update();
	}
}
