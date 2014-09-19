package test.org.malai.interaction;

import java.lang.reflect.Field;
import java.util.List;

import javax.swing.JButton;

import org.malai.interaction.InteractionImpl;
import org.malai.interaction.library.KeyPressure;

import junit.framework.TestCase;

public class TestInteractionEventRecyling extends TestCase {

	public void testKeyPressureInteraction() throws SecurityException, NoSuchFieldException, IllegalArgumentException, IllegalAccessException {
		final KeyPressure kp = new KeyPressure();
		final int key = 10;
		final Field stillProcField = InteractionImpl.class.getDeclaredField("stillProcessingEvents");
		stillProcField.setAccessible(true);

		kp.onKeyPressure(key, 'a', 0, new JButton());
		assertEquals(-1, kp.getKey());

		final List<?> stillProcessingEvents = (List<?>) stillProcField.get(kp);
		assertEquals(1, stillProcessingEvents.size());

		kp.onKeyRelease(10, 'a', 0, new JButton());
		assertEquals(0, stillProcessingEvents.size());
	}
}
