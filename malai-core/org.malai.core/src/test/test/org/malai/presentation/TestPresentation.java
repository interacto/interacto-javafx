package test.org.malai.presentation;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import org.malai.presentation.AbstractPresentation;
import org.malai.presentation.ConcretePresentation;
import org.malai.presentation.Presentation;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class TestPresentation {
	protected Presentation<MockPresAbs, MockPresConc> pres;
	protected MockPresAbs abs;
	protected MockPresConc conc;

	@Before
	public void setUp() throws Exception {
		abs = new MockPresAbs();
		conc = new MockPresConc();
		pres = new Presentation<>(abs, conc);
	}


	@Test
	public void testGetAbsPres() {
		assertEquals(abs, pres.getAbstractPresentation());
	}


	@Test
	public void testGetConcPres() {
		assertEquals(conc, pres.getConcretePresentation());
	}

	@Test
	public void testSetModified() {
		pres.setModified(true);
		assertTrue(conc.isModified());
		assertTrue(abs.isModified());
		pres.setModified(false);
		assertFalse(conc.isModified());
		assertFalse(abs.isModified());
	}


	@Test
	public void testIsModified() {
		pres.setModified(false);
		assertFalse(pres.isModified());
		conc.isModif = true;
		assertTrue(pres.isModified());
		conc.isModif = false;
		abs.isModif = true;
		assertTrue(pres.isModified());
		conc.isModif = true;
		assertTrue(pres.isModified());
	}


	@Test(expected=IllegalArgumentException.class)
	public void testConstructorAbsNull() {
		pres = new Presentation<>(null, new MockPresConc());
	}

	@Test(expected=IllegalArgumentException.class)
	public void testConstructorConcNull() {
		pres = new Presentation<>(new MockPresAbs(), null);
	}


	@Test(expected=IllegalArgumentException.class)
	public void testConstructorAbsConcNull() {
		pres = new Presentation<>(null, null);
	}


	@Test
	public void testUpdate() {
		pres.update();
		assertTrue(conc.updated);
	}


	@Test
	public void testReinit() {
		pres.reinit();
		assertTrue(conc.reinit);
		assertTrue(abs.reinit);
	}


	public class MockPresAbs implements AbstractPresentation {
		public boolean isModif = false;
		public boolean reinit = false;

		@Override
		public void setModified(final boolean modified) {
			isModif = modified;
		}

		@Override
		public boolean isModified() {
			return isModif;
		}

		@Override
		public void reinit() {
			reinit = true;
		}
	}


	public class MockPresConc implements ConcretePresentation {
		public boolean isModif = false;
		public boolean updated = false;
		public boolean reinit = false;

		@Override
		public void save(final boolean generalPreferences, final String nsURI, final Document document, final Element root) {
			//
		}

		@Override
		public void load(final boolean generalPreferences, final String nsURI, final Element meta) {
			//
		}

		@Override
		public void setModified(final boolean modified) {
			isModif = modified;
		}

		@Override
		public boolean isModified() {
			return isModif;
		}

		@Override
		public void reinit() {
			reinit = true;
		}

		@Override
		public void update() {
			updated = true;
		}
	}
}
