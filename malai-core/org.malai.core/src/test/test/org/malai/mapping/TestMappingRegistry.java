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
		final List<Double> list2 = new ActiveArrayList<Double>();
		final JButton b2 = new JButton("b2");
		final IMapping mapping = new MappingMock(list2, b2) {
			@Override
			public void onObjectAdded(final Object list, final Object object, final int index) {
				fail();
			}
		};

		MappingRegistry.REGISTRY.addMapping(mapping);
		MappingRegistry.REGISTRY.removeMappingsUsingSource(list2);
		assertNull(MappingRegistry.REGISTRY.getSourceFromTarget(b2, List.class));
		list2.add(3.);
	}


	public void testMappingAddedThenRemovedFromSourceObjectClass() {
		final List<Double> list2 = new ActiveArrayList<Double>();
		final JButton b2 = new JButton("b2");
		final IMapping mapping = new MappingMock(list2, b2) {
			@Override
			public void onObjectAdded(final Object list, final Object object, final int index) {
				fail();
			}
		};

		MappingRegistry.REGISTRY.addMapping(mapping);
		MappingRegistry.REGISTRY.removeMappingsUsingSource(list2, mapping.getClass());
		assertNull(MappingRegistry.REGISTRY.getSourceFromTarget(b2, List.class));
		list2.add(3.);
	}


	public void testMappingAddedThenRemovedFromTargetObjectClass() {
		final List<Double> list2 = new ActiveArrayList<Double>();
		final JButton b2 = new JButton("b2");
		final IMapping mapping = new MappingMock(list2, b2) {
			@Override
			public void onObjectAdded(final Object list, final Object object, final int index) {
				fail();
			}
		};

		MappingRegistry.REGISTRY.addMapping(mapping);
		MappingRegistry.REGISTRY.removeMappingsUsingTarget(b2, mapping.getClass());
		assertNull(MappingRegistry.REGISTRY.getSourceFromTarget(b2, List.class));
		list2.add(3.);
	}


	public void testMappingAddedThenRemovedFromTargetObject() {
		final List<Double> list2 = new ActiveArrayList<Double>();
		final JButton b2 = new JButton("b2");
		final IMapping mapping = new MappingMock(list2, b2) {
			@Override
			public void onObjectAdded(final Object list, final Object object, final int index) {
				fail();
			}
		};

		MappingRegistry.REGISTRY.addMapping(mapping);
		MappingRegistry.REGISTRY.removeMappingsUsingTarget(b2);
		assertNull(MappingRegistry.REGISTRY.getSourceFromTarget(b2, List.class));
		list2.add(3.);
	}



	class MappingMock extends List2ObjectMapping<Double, JButton> {
		MappingMock(final List<Double> source, final JButton target) {
			super(source, target);
		}
		@Override
		public void onObjectAdded(final Object list, final Object object, final int index) {
			//
		}
		@Override
		public void onObjectRemoved(final Object list, final Object object, final int index) {
			//
		}
		@Override
		public void onListCleaned(final Object list) {
			//
		}
		@Override
		public void onObjectMoved(final Object list, final Object object, final int srcIndex, final int targetIndex) {
			//
		}
		@Override
		public void onObjectReplaced(final IUnary<?> object, final Object replacedObject) {
			//
		}
		@Override
		public void onObjectModified(final Object object) {
			//
		}
		@Override
		public void init() {
			//
		}
	}
}
