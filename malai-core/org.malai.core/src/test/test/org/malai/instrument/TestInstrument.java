package test.org.malai.instrument;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;
import org.malai.instrument.Instrument;
import org.malai.instrument.Link;


public abstract class TestInstrument<T extends Instrument> {
	protected T instrument;

	@Test
	public void testNotNoLink() {
		assertTrue(instrument.getSizeLinks()==0);
		instrument.setActivated(true);
		assertTrue(instrument.getSizeLinks()>0);
	}


	@Test
	public void testActivation() {
		instrument.setActivated(true);
		assertTrue(instrument.isActivated());
		instrument.setActivated(false);
		assertFalse(instrument.isActivated());
	}


	public Link<?, ?, ?> getLink(final String nameClassLink) {
		Link<?, ?, ?> link = null;
		final List<Link<?, ?, ?>> links = instrument.getLinks();

		for(int i=0; i<links.size() && link==null; i++)
			if(links.get(i).getClass().getName().endsWith(nameClassLink))
				link = links.get(i);

		return link;
	}
}
