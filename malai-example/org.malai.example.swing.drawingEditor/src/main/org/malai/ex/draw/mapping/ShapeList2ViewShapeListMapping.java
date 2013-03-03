package org.malai.ex.draw.mapping;

import java.util.List;

import org.malai.ex.draw.model.MyShape;
import org.malai.ex.draw.view.MyViewShape;
import org.malai.ex.draw.view.ViewFactory;
import org.malai.mapping.MappingRegistry;
import org.malai.mapping.SynchroSymmetricList2ListMapping;

public class ShapeList2ViewShapeListMapping extends SynchroSymmetricList2ListMapping<MyShape,MyViewShape<?>> {

	public ShapeList2ViewShapeListMapping(List<MyShape> source, List<MyViewShape<?>> target) {
		super(source, target);
	}


	@Override
	protected MyViewShape<?> createTargetObject(final Object sourceObject) {
		/*
		 * When a source object has been added to the source list, a target object must be created.
		 * In this case a view of the source shape must be created. To do so a factory is used.
		 */
		return sourceObject instanceof MyShape ? ViewFactory.INSTANCE.createViewShape((MyShape)sourceObject) : null;
	}



	@Override
	public void onObjectAdded(final Object list, final Object object, final int index) {
		super.onObjectAdded(list, object, index);

		/*
		 * When a new shape is added to the list, a target view must be created and a mapping
		 * between them must be established.
		 */
		if(object instanceof MyShape) {
			/* Getting the view. */
			final MyViewShape<?> view = index==-1 ? target.get(target.size()-1) : target.get(index);
			/* 
			 * Creating the mapping and adding it to the registry.
			 * This step is necessary only if the mapped objects are mapped together as well.
			 */
			MappingRegistry.REGISTRY.addMapping(new Shape2ViewMapping((MyShape)object, view));
		}
	}



	@Override
	public void onObjectRemoved(final Object list, final Object object, final int index) {
		super.onObjectRemoved(list, object, index);
		/*
		 * When a new shape is removed from the list, the corresponding target view and the 
		 * mapping between them must be removed.
		 * This step is necessary only if the mapped objects are mapped together as well.
		 */
		MappingRegistry.REGISTRY.removeMappingsUsingSource(object, Shape2ViewMapping.class);
	}


	@Override
	public void onListCleaned(final Object list) {
		super.onListCleaned(list);
		/*
		 * When a new shape is removed from the list, the corresponding target view and the 
		 * mapping between them must be removed.
		 * This step is necessary only if the mapped objects are mapped together as well.
		 */
		for(MyShape shape : source)
			MappingRegistry.REGISTRY.removeMappingsUsingSource(shape, Shape2ViewMapping.class);
	}
}
