package test.org.malai.mapping;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.malai.mapping.ActiveUnary;
import org.malai.mapping.IUnary;
import org.malai.mapping.Unary2UnaryMapping;

public class TestUnary2UnaryMapping {
	protected MockUnary2UnaryMapping map;
	protected ActiveUnary<Object> src;
	protected ActiveUnary<Object> tgt;


	@Before
	public void setUp() {
		src = new ActiveUnary<Object>();
		tgt = new ActiveUnary<Object>();
		map = new MockUnary2UnaryMapping(src, tgt);
	}

	@SuppressWarnings("unused")
	@Test(expected=IllegalArgumentException.class)
	public void testContructorNullSrc() {
		new MockUnary2UnaryMapping(null, tgt);
	}

	@SuppressWarnings("unused")
	@Test(expected=IllegalArgumentException.class)
	public void testContructorNullTgt() {
		new MockUnary2UnaryMapping(src, null);
	}

	@SuppressWarnings("unused")
	@Test(expected=IllegalArgumentException.class)
	public void testContructorNullSrcTgt() {
		new MockUnary2UnaryMapping(src, null);
	}

	@SuppressWarnings("unused")
	@Test(expected=IllegalArgumentException.class)
	public void testContructorSameSrcTgt() {
		new MockUnary2UnaryMapping(src, src);
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

	@Test
	public void testInitWhenCleared() {
		map.clear();
		map.init();
	}

	@Test
	public void testInit() {
		map.init();
		assertTrue(map.replaced);
	}


	public static class MockUnary2UnaryMapping extends Unary2UnaryMapping<Object, Object> {
		public boolean replaced = false;

		public MockUnary2UnaryMapping(final IUnary<Object> source, final IUnary<Object> target) {
			super(source, target);
		}

		@Override
		public void onObjectReplaced(final IUnary<?> object, final Object replacedObject) {
			replaced = true;
			assertEquals(object.getValue(), replacedObject);
		}

		@Override
		public void onObjectModified(final Object object) {
			//
		}
	}
}
