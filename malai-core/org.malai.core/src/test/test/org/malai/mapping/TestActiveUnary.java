package test.org.malai.mapping;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.malai.mapping.ActiveUnary;
import org.malai.mapping.IUnary;
import org.malai.mapping.MappingRegistry;
import org.malai.mapping.Unary2UnaryMapping;

import static org.junit.Assert.assertEquals;

public class TestActiveUnary {
	protected IUnary<Integer> s2;

	protected IUnary<Integer> s1;

	@Before
	public void setUp() {
		s1 = new ActiveUnary<>();
		s2 = new ActiveUnary<>();
		MappingRegistry.REGISTRY.addMapping(new S2S(s1, s2));
	}


	@Test
	public void testConstructorValue() {
		s1 = new ActiveUnary<>(12);
		assertEquals(12, (int) s1.getValue());
	}


	@Test
	public void testGetValue() {
		Integer integer = new Integer(1);
		s1.setValue(integer);
		assertEquals(integer, s1.getValue());

		integer = new Integer(2);
		s1.setValue(integer);
		assertEquals(integer, s1.getValue());
	}


	@Test
	public void testSetValue() {
		s1.setValue(new Integer(1));
		assertEquals(s2.getValue(), s1.getValue());
		assertEquals(1, s1.getValue().intValue());
		s1.setValue(new Integer(2));
		assertEquals(s2.getValue(), s1.getValue());
		assertEquals(2, s1.getValue().intValue());
	}

}


@Ignore
class S2S extends Unary2UnaryMapping<Integer, Integer> {

	public S2S(final IUnary<Integer> source, final IUnary<Integer> target) {
		super(source, target);
	}

	@Override
	public void onObjectModified(final Object object) {
		targetObject.setValue(new Integer(sourceObject.getValue()));
	}

	@Override
	public void onObjectReplaced(final IUnary<?> object, final Object replacedObject) {
		targetObject.setValue(new Integer(sourceObject.getValue()));
	}

}

