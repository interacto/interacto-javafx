package test.org.malai.mapping;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.malai.mapping.Object2ObjectMapping;

public class TestObject2ObjectMapping {
	protected MockObject2ObjectMapping map;
	protected Object src;
	protected Object tgt;


	@Before
	public void setUp() {
		src = new Object();
		tgt = new Object();
		map = new MockObject2ObjectMapping(src, tgt);
	}

	@SuppressWarnings("unused")
	@Test(expected=IllegalArgumentException.class)
	public void testContructorNullSrc() {
		new MockObject2ObjectMapping(null, tgt);
	}

	@SuppressWarnings("unused")
	@Test(expected=IllegalArgumentException.class)
	public void testContructorNullTgt() {
		new MockObject2ObjectMapping(src, null);
	}

	@SuppressWarnings("unused")
	@Test(expected=IllegalArgumentException.class)
	public void testContructorNullSrcTgt() {
		new MockObject2ObjectMapping(src, null);
	}

	@SuppressWarnings("unused")
	@Test(expected=IllegalArgumentException.class)
	public void testContructorSameSrcTgt() {
		new MockObject2ObjectMapping(src, src);
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
	public void testInit() {
		map.init();
		assertTrue(map.modified);
	}


	@Test
	public void testInitWhenCleared() {
		map.clear();
		map.init();
		assertFalse(map.modified);
	}


	public static class MockObject2ObjectMapping extends Object2ObjectMapping<Object, Object> {
		public boolean modified = false;

		public MockObject2ObjectMapping(final Object source, final Object target) {
			super(source, target);
		}

		@Override
		public void onObjectModified(final Object object) {
			modified = true;
		}
	}
}
