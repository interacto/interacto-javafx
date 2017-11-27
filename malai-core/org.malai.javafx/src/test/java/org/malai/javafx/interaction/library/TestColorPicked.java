package org.malai.javafx.interaction.library;

import javafx.scene.control.ColorPicker;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.malai.javafx.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class TestColorPicked extends BaseJfXInteractionTest<ColorPicked> {
	ColorPicker picker;

	@Override
	@BeforeEach
	public void setUp() {
		picker = new ColorPicker();
	}

	@Override
	protected ColorPicked createInteraction() {
		return new ColorPicked();
	}

}