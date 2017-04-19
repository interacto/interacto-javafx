package test.org.malai.mapping;

import java.util.ArrayList;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import org.malai.mapping.IUnary;
import org.malai.mapping.List2ObjectMapping;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class TestList2ObjectMapping {
	protected MockList2ObjectMapping map;
	protected List<Object> src;
	protected Object tgt;


	@Before
	public void setUp() {
		src = new ArrayList<>();
		tgt = new Object();
		map = new MockList2ObjectMapping(src, tgt);
	}

	@SuppressWarnings("unused")
	@Test(expected = IllegalArgumentException.class)
	public void testContructorNullSrc() {
		new MockList2ObjectMapping(null, tgt);
	}

	@SuppressWarnings("unused")
	@Test(expected = IllegalArgumentException.class)
	public void testContructorNullTgt() {
		new MockList2ObjectMapping(src, null);
	}

	@SuppressWarnings("unused")
	@Test(expected = IllegalArgumentException.class)
	public void testContructorNullSrcTgt() {
		new MockList2ObjectMapping(src, null);
	}

	@SuppressWarnings("unused")
	@Test(expected = IllegalArgumentException.class)
	public void testContructorSameSrcTgt() {
		new MockList2ObjectMapping(src, src);
	}


	@Test
	public void testGetSource() {
		assertEquals(src, map.getSource());
	}

	@Test
	public void testGetTarget() {
		assertEquals(tgt, map.getTarget());
	}

	@Test
	public void testClear() {
		map.clear();
		assertNull(map.getSource());
		assertNull(map.getTarget());
	}


	public static class MockList2ObjectMapping extends List2ObjectMapping<Object, Object> {

		public MockList2ObjectMapping(final List<Object> source, final Object target) {
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
