package test.org.malai.mapping;

import java.util.List;

import javax.swing.JButton;

import junit.framework.TestCase;

import org.malai.mapping.ActiveArrayList;
import org.malai.mapping.IMapping;
import org.malai.mapping.IUnary;
import org.malai.mapping.List2ObjectMapping;
import org.malai.mapping.MappingRegistry;

public class TestMappingRegistry extends TestCase {
	public void testMappingAddedThenRemovedFromSourceObject() {
		List<Double> list2 = new ActiveArrayList<Double>();
		JButton b2 = new JButton("b2");
		IMapping mapping = new MappingMock(list2, b2) {
			@Override
			public void onObjectAdded(Object list, Object object, int index) {
				fail();
			}
		};

		MappingRegistry.REGISTRY.addMapping(mapping);
		MappingRegistry.REGISTRY.removeMappingsUsingSource(list2);
		assertNull(MappingRegistry.REGISTRY.getSourceFromTarget(b2, List.class));
		list2.add(3.);
	}


	public void testMappingAddedThenRemovedFromSourceObjectClass() {
		List<Double> list2 = new ActiveArrayList<Double>();
		JButton b2 = new JButton("b2");
		IMapping mapping = new MappingMock(list2, b2) {
			@Override
			public void onObjectAdded(Object list, Object object, int index) {
				fail();
			}
		};

		MappingRegistry.REGISTRY.addMapping(mapping);
		MappingRegistry.REGISTRY.removeMappingsUsingSource(list2, mapping.getClass());
		assertNull(MappingRegistry.REGISTRY.getSourceFromTarget(b2, List.class));
		list2.add(3.);
	}


	public void testMappingAddedThenRemovedFromTargetObjectClass() {
		List<Double> list2 = new ActiveArrayList<Double>();
		JButton b2 = new JButton("b2");
		IMapping mapping = new MappingMock(list2, b2) {
			@Override
			public void onObjectAdded(Object list, Object object, int index) {
				fail();
			}
		};

		MappingRegistry.REGISTRY.addMapping(mapping);
		MappingRegistry.REGISTRY.removeMappingsUsingTarget(b2, mapping.getClass());
		assertNull(MappingRegistry.REGISTRY.getSourceFromTarget(b2, List.class));
		list2.add(3.);
	}


	public void testMappingAddedThenRemovedFromTargetObject() {
		List<Double> list2 = new ActiveArrayList<Double>();
		JButton b2 = new JButton("b2");
		IMapping mapping = new MappingMock(list2, b2) {
			@Override
			public void onObjectAdded(Object list, Object object, int index) {
				fail();
			}
		};

		MappingRegistry.REGISTRY.addMapping(mapping);
		MappingRegistry.REGISTRY.removeMappingsUsingTarget(b2);
		assertNull(MappingRegistry.REGISTRY.getSourceFromTarget(b2, List.class));
		list2.add(3.);
	}



	class MappingMock extends List2ObjectMapping<Double, JButton> {
		MappingMock(List<Double> source, JButton target) {
			super(source, target);
		}
		@Override
		public void onObjectAdded(Object list, Object object, int index) {
			//
		}
		@Override
		public void onObjectRemoved(Object list, Object object, int index) {
			//
		}
		@Override
		public void onListCleaned(Object list) {
			//
		}
		@Override
		public void onObjectMoved(Object list, Object object, int srcIndex, int targetIndex) {
			//
		}
		@Override
		public void onObjectReplaced(IUnary<?> object, Object replacedObject) {
			//
		}
		@Override
		public void onObjectModified(Object object) {
			//
		}
		@Override
		public void init() {
			//
		}
	}
}
