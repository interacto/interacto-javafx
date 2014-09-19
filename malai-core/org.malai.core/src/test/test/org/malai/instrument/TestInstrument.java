package test.org.malai.instrument;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Test;
import org.malai.instrument.Instrument;
import org.malai.instrument.Interactor;


public abstract class TestInstrument<T extends Instrument> {
	protected T instrument;

	@Test
	public void testNotNoLink() {
		assertTrue(instrument.getNbInteractors()==0);
		if(!instrument.getInteractors().isEmpty()) {
			instrument.setActivated(true);
			assertTrue(instrument.getNbInteractors()>0);
		}
	}


	@Test
	public void testCreation() {
		assertFalse(instrument.isActivated());
		assertFalse(instrument.isModified());
		assertNotNull(instrument.getInteractors());
		assertTrue(instrument.getInteractors().isEmpty());
	}


	@Test
	public void testActivation() {
		instrument.setActivated(true);
		assertTrue(instrument.isActivated());
		instrument.setActivated(false);
		assertFalse(instrument.isActivated());
	}


	public Interactor getLink(final String nameClassLink) {
		Interactor interactor = null;
		final List<Interactor> interactors = instrument.getInteractors();

		for(int i=0; i<interactors.size() && interactor==null; i++)
			if(interactors.get(i).getClass().getName().endsWith(nameClassLink))
				interactor = interactors.get(i);

		return interactor;
	}
}
