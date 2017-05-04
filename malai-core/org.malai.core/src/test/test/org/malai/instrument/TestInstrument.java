package test.org.malai.instrument;

import java.util.List;
import org.junit.Test;
import org.malai.instrument.Instrument;
import org.malai.binding.WidgetBinding;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;


public abstract class TestInstrument<T extends Instrument> {
	protected T instrument;

	@Test
	public void testNotNoLink() {
		assertTrue(instrument.getNbWidgetBindings() == 0);
		if(!instrument.getWidgetBindings().isEmpty()) {
			instrument.setActivated(true);
			assertTrue(instrument.getNbWidgetBindings() > 0);
		}
	}


	@Test
	public void testCreation() {
		assertFalse(instrument.isActivated());
		assertFalse(instrument.isModified());
		assertNotNull(instrument.getWidgetBindings());
		assertTrue(instrument.getWidgetBindings().isEmpty());
	}


	@Test
	public void testActivation() {
		instrument.setActivated(true);
		assertTrue(instrument.isActivated());
		instrument.setActivated(false);
		assertFalse(instrument.isActivated());
	}


	public WidgetBinding getLink(final String nameClassLink) {
		WidgetBinding interactor = null;
		final List<WidgetBinding> interactors = instrument.getWidgetBindings();

		for(int i = 0; i < interactors.size() && interactor == null; i++)
			if(interactors.get(i).getClass().getName().endsWith(nameClassLink)) interactor = interactors.get(i);

		return interactor;
	}
}
