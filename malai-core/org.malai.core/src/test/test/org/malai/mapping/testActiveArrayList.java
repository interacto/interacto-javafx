package test.org.malai.mapping;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.malai.mapping.ActiveArrayList;
import org.malai.mapping.IActiveList;
import org.malai.mapping.MappingRegistry;
import org.malai.mapping.SymmetricList2ListMapping;
import static org.junit.Assert.*;

public class testActiveArrayList {
	protected IActiveList<Integer> list;

	protected List<Integer> list2;

	@Before
	public void setUp() {
		list  = new ActiveArrayList<Integer>();
		list2 = new ArrayList<Integer>();
		MappingRegistry.REGISTRY.addMapping(new List2List(list, list2));
	}



	@Test
	public void testMove() {
		try {
			list.move(0, 1);
			fail();
		}catch(final IndexOutOfBoundsException e) { /* */ }
		assertEquals(0, list2.size());

		list.add(new Integer(1));
		list.move(0, 0);
		assertEquals(1, list2.size());
		assertEquals(1, list2.get(0).intValue());

		list.add(new Integer(2));
		list.move(1, 0);
		assertEquals(2, list2.size());
		assertEquals(2, list2.get(0).intValue());
		assertEquals(1, list2.get(1).intValue());
		list.move(0, 1);
		assertEquals(2, list2.size());
		assertEquals(1, list2.get(0).intValue());
		assertEquals(2, list2.get(1).intValue());
		try {
			list.move(0, 10);
			fail();
		}catch(final IndexOutOfBoundsException e) { /* */ }
		assertEquals(2, list2.size());
		assertEquals(1, list2.get(0).intValue());
		assertEquals(2, list2.get(1).intValue());
		try {
			list.move(0, -1);
			fail();
		}catch(final IndexOutOfBoundsException e) { /* */ }
		assertEquals(2, list2.size());
		assertEquals(1, list2.get(0).intValue());
		assertEquals(2, list2.get(1).intValue());

		list.add(new Integer(3));
		list.move(1, 2);
		assertEquals(3, list2.size());
		assertEquals(1, list2.get(0).intValue());
		assertEquals(3, list2.get(1).intValue());
		assertEquals(2, list2.get(2).intValue());
		list.move(0, 2);
		assertEquals(3, list2.size());
		assertEquals(3, list2.get(0).intValue());
		assertEquals(2, list2.get(1).intValue());
		assertEquals(1, list2.get(2).intValue());
		list.move(2, 0);
		assertEquals(3, list2.size());
		assertEquals(1, list2.get(0).intValue());
		assertEquals(3, list2.get(1).intValue());
		assertEquals(2, list2.get(2).intValue());

		list.add(new Integer(4));
		list.move(1, 2);
		assertEquals(4, list2.size());
		assertEquals(1, list2.get(0).intValue());
		assertEquals(2, list2.get(1).intValue());
		assertEquals(3, list2.get(2).intValue());
		assertEquals(4, list2.get(3).intValue());
		list.move(2, 1);
		assertEquals(4, list2.size());
		assertEquals(1, list2.get(0).intValue());
		assertEquals(3, list2.get(1).intValue());
		assertEquals(2, list2.get(2).intValue());
		assertEquals(4, list2.get(3).intValue());
		list.move(0, 2);
		assertEquals(4, list2.size());
		assertEquals(3, list2.get(0).intValue());
		assertEquals(2, list2.get(1).intValue());
		assertEquals(1, list2.get(2).intValue());
		assertEquals(4, list2.get(3).intValue());
		list.move(2, 0);
		assertEquals(4, list2.size());
		assertEquals(1, list2.get(0).intValue());
		assertEquals(3, list2.get(1).intValue());
		assertEquals(2, list2.get(2).intValue());
		assertEquals(4, list2.get(3).intValue());
	}



	@Test
	public void testAdd1() {
		list.add(new Integer(1));
		assertEquals(1, list2.size());
		assertEquals(1, list2.get(0).intValue());
		assertNotSame(list.get(0), list2.get(0));

		list.add(new Integer(2));
		assertEquals(2, list2.size());
		assertEquals(1, list2.get(0).intValue());
		assertEquals(2, list2.get(1).intValue());
		assertNotSame(list.get(0), list2.get(0));
		assertNotSame(list.get(1), list2.get(1));
	}



	@Test
	public void testAdd2() {
		list.add(0, new Integer(1));
		assertEquals(1, list2.size());
		assertEquals(1, list2.get(0).intValue());
		assertNotSame(list.get(0), list2.get(0));

		list.add(1, new Integer(2));
		assertEquals(2, list2.size());
		assertEquals(1, list2.get(0).intValue());
		assertEquals(2, list2.get(1).intValue());
		assertNotSame(list.get(0), list2.get(0));
		assertNotSame(list.get(1), list2.get(1));

		list.add(0, new Integer(3));
		assertEquals(3, list2.size());
		assertEquals(3, list2.get(0).intValue());
		assertEquals(1, list2.get(1).intValue());
		assertEquals(2, list2.get(2).intValue());
		assertNotSame(list.get(0), list2.get(0));
		assertNotSame(list.get(1), list2.get(1));
		assertNotSame(list.get(2), list2.get(2));

		list.add(1, new Integer(4));
		assertEquals(4, list2.size());
		assertEquals(3, list2.get(0).intValue());
		assertEquals(4, list2.get(1).intValue());
		assertEquals(1, list2.get(2).intValue());
		assertEquals(2, list2.get(3).intValue());
		assertNotSame(list.get(0), list2.get(0));
		assertNotSame(list.get(1), list2.get(1));
		assertNotSame(list.get(2), list2.get(2));
		assertNotSame(list.get(3), list2.get(3));

		try {
			list.add(-1, new Integer(4));
			fail();
		}catch(final IndexOutOfBoundsException e) { /* */ }

		try {
			list.add(10, new Integer(4));
			fail();
		}catch(final IndexOutOfBoundsException e) { /* */ }
	}



	@Test
	public void testAddAll1() {
		final ArrayList<Integer> list3 = new ArrayList<Integer>();

		list3.add(new Integer(0));
		list3.add(new Integer(1));
		list3.add(new Integer(2));
		list3.add(new Integer(3));

		list.addAll(list3);
		assertEquals(4, list2.size());
		assertEquals(0, list2.get(0).intValue());
		assertEquals(1, list2.get(1).intValue());
		assertEquals(2, list2.get(2).intValue());
		assertEquals(3, list2.get(3).intValue());
		assertNotSame(list.get(0), list2.get(0));
		assertNotSame(list.get(1), list2.get(1));
		assertNotSame(list.get(2), list2.get(2));
		assertNotSame(list.get(3), list2.get(3));
	}



	@Test
	public void testAddAll2() {
		final ArrayList<Integer> list3 = new ArrayList<Integer>();

		list3.add(new Integer(0));
		list3.add(new Integer(1));
		list3.add(new Integer(2));
		list3.add(new Integer(3));

		list.add(new Integer(10));
		list.add(new Integer(11));
		list.add(new Integer(12));
		list.add(new Integer(13));

		list.addAll(2, list3);
		assertEquals(8, list2.size());
		assertEquals(10, list2.get(0).intValue());
		assertEquals(11, list2.get(1).intValue());
		assertEquals(0, list2.get(2).intValue());
		assertEquals(1, list2.get(3).intValue());
		assertEquals(2, list2.get(4).intValue());
		assertEquals(3, list2.get(5).intValue());
		assertEquals(12, list2.get(6).intValue());
		assertEquals(13, list2.get(7).intValue());
		assertNotSame(list.get(0), list2.get(0));
		assertNotSame(list.get(1), list2.get(1));
		assertNotSame(list.get(2), list2.get(2));
		assertNotSame(list.get(3), list2.get(3));
		assertNotSame(list.get(4), list2.get(4));
		assertNotSame(list.get(5), list2.get(5));
		assertNotSame(list.get(6), list2.get(6));
		assertNotSame(list.get(7), list2.get(7));
	}



	@Test
	public void testClear() {
		list.add(new Integer(10));
		list.add(new Integer(11));
		list.add(new Integer(12));
		list.add(new Integer(13));

		list.clear();

		assertEquals(0, list2.size());
	}



	@Test
	public void testRemove1() {
		assertFalse(list.remove(null));
		assertEquals(0, list2.size());
		assertFalse(list.remove(new Integer(0)));
		assertEquals(0, list2.size());

		list.add(new Integer(1));
		assertFalse(list.remove(new Integer(-1)));
		assertEquals(1, list2.size());
		assertEquals(1, list2.get(0).intValue());
		assertFalse(list.remove(new Integer(2)));
		assertEquals(1, list2.size());
		assertEquals(1, list2.get(0).intValue());

		assertTrue(list.remove(new Integer(1)));
		assertEquals(0, list2.size());

		list.add(new Integer(1));
		list.add(new Integer(2));
		list.add(new Integer(3));
		assertTrue(list.remove(new Integer(2)));
		assertEquals(2, list2.size());
		assertEquals(1, list2.get(0).intValue());
		assertEquals(3, list2.get(1).intValue());

		list.add(new Integer(3));
		assertTrue(list.remove(new Integer(3)));
		assertEquals(2, list2.size());
		assertEquals(1, list2.get(0).intValue());
		assertEquals(3, list2.get(1).intValue());

		assertTrue(list.remove(new Integer(3)));
		assertEquals(1, list2.size());
		assertEquals(1, list2.get(0).intValue());
	}



	@Test
	public void testRemove2() {
		try {
			list.remove(-1);
			fail();
		}catch(final IndexOutOfBoundsException e) { /* */ }
		assertEquals(0, list2.size());
		try {
			list.remove(0);
		}catch(final IndexOutOfBoundsException e) { /* */ }
		assertEquals(0, list2.size());

		list.add(new Integer(1));
		list.remove(0);
		assertEquals(0, list2.size());

		list.add(new Integer(1));
		list.add(new Integer(2));
		list.add(new Integer(3));
		list.remove(1);
		assertEquals(2, list2.size());
		assertEquals(1, list2.get(0).intValue());
		assertEquals(3, list2.get(1).intValue());
		list.remove(1);
		assertEquals(1, list2.size());
		assertEquals(1, list2.get(0).intValue());
		list.remove(0);
		assertEquals(0, list2.size());
	}



	@Test
	public void testSet() {
		try {
			list.set(0, new Integer(1));
			fail();
		}catch(final IndexOutOfBoundsException e){ /* */ }

		list.add(new Integer(1));

		try {
			list.set(-1, new Integer(1));
			fail();
		}catch(final IndexOutOfBoundsException e){ /* */ }

		try {
			list.set(1, new Integer(1));
			fail();
		}catch(final IndexOutOfBoundsException e){ /* */ }

		list.set(0, new Integer(0));
		assertEquals(1, list2.size());
		assertEquals(0, list2.get(0).intValue());

		list.add(new Integer(1));
		list.add(new Integer(2));
		list.set(2, new Integer(3));
		assertEquals(3, list2.size());
		assertEquals(3, list2.get(2).intValue());
		list.set(1, new Integer(4));
		assertEquals(3, list2.size());
		assertEquals(4, list2.get(1).intValue());
		list.set(0, new Integer(5));
		assertEquals(3, list2.size());
		assertEquals(5, list2.get(0).intValue());
	}
}


class List2List extends SymmetricList2ListMapping<Integer, Integer> {

	public List2List(final List<Integer> source, final List<Integer> target) {
		super(source, target);
	}

	@Override
	protected Integer createTargetObject(final Object sourceObject) {
		return new Integer((Integer) sourceObject);
	}

}


