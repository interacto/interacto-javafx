package org.malai.javafx.interaction.library;

import java.util.stream.Stream;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.malai.interaction.InitState;
import org.malai.javafx.interaction.JfxInteraction;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TestJfxInteraction {
	static Stream<JfxInteraction> interactionProvider() {
		return Stream.of(new ButtonPressed(), new DnD(), new AbortableDnD(), new BoxChecked(), new ColorPicked(), new ComboBoxSelected(),
			new DoubleClick(), new KeyPressure(), new KeyPressureNoModifier(), new KeysPressure(), new KeysScrolling(), new KeysTyped(),
			new KeyTyped(), new MenuItemPressed(), new MultiClick(), new Press(), new Scrolling(), new SpinnerValueChanged(),
			new TabSelected(), new TextChanged(), new ToggleButtonPressed(), new WindowClosed());
	}

	@ParameterizedTest
	@MethodSource("interactionProvider")
	void testInitInteraction(final JfxInteraction interaction) {
//		assertNull(interaction.getWidget());
		assertEquals(-1, interaction.getLastHIDUsed());
		assertTrue(interaction.getRegisteredWidgets().isEmpty());
		assertTrue(interaction.getRegisteredWindows().isEmpty());
		assertTrue(interaction.getCurrentState() instanceof InitState);
	}
}
