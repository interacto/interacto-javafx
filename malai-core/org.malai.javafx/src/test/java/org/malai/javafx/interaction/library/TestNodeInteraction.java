package org.malai.javafx.interaction.library;

import java.util.stream.Stream;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import static org.junit.jupiter.api.Assertions.assertNull;

public class TestNodeInteraction {
	static Stream<NodeInteraction<?>> interactionProvider() {
		return Stream.of(new ButtonPressed(), new BoxChecked(), new ColorPicked(), new ComboBoxSelected(),
			new SpinnerValueChanged(), new TabSelected(), new TextChanged(), new ToggleButtonPressed());
	}

	@ParameterizedTest
	@MethodSource("interactionProvider")
	void testInitInteraction(final NodeInteraction<?> interaction) {
		assertNull(interaction.getWidget());
	}
}
