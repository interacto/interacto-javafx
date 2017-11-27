package org.malai.javafx.interaction.library;

import javafx.scene.control.CheckBox;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.malai.javafx.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class TestBoxChecked extends BaseJfXInteractionTest<BoxChecked> {
	CheckBox checkbox;

	@Override
	@BeforeEach
	public void setUp() {
		checkbox = new CheckBox();
	}

	@Override
	protected BoxChecked createInteraction() {
		return new BoxChecked();
	}

}