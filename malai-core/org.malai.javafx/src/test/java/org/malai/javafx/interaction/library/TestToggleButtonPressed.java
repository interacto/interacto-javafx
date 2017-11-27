package org.malai.javafx.interaction.library;

import javafx.scene.control.ToggleButton;
import org.junit.jupiter.api.BeforeEach;

public class TestToggleButtonPressed extends BaseJfXInteractionTest<ToggleButtonPressed> {
	ToggleButton button;

	@Override
	@BeforeEach
	public void setUp() {
		super.setUp();
		button = new ToggleButton();
	}

	@Override
	protected ToggleButtonPressed createInteraction() {
		return new ToggleButtonPressed();
	}
}
