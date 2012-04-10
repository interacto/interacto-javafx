package test.org.malai.interaction;

import java.lang.reflect.Field;
import java.util.List;

import javax.swing.JButton;

import org.malai.interaction.Interaction;
import org.malai.interaction.library.KeyPressure;

import junit.framework.TestCase;

public class TestInteractionEventRecyling extends TestCase {

	public void testKeyPressureInteraction() throws SecurityException, NoSuchFieldException, IllegalArgumentException, IllegalAccessException {
		KeyPressure kp = new KeyPressure();
		int key = 10;
		Field stillProcField = Interaction.class.getDeclaredField("stillProcessingEvents");
		stillProcField.setAccessible(true);

		kp.onKeyPressure(key, 0, new JButton());
		assertEquals(-1, kp.getKey());

		List<?> stillProcessingEvents = (List<?>) stillProcField.get(kp);
		assertEquals(1, stillProcessingEvents.size());

		kp.onKeyRelease(10, 0, new JButton());
		assertEquals(0, stillProcessingEvents.size());
	}
}
