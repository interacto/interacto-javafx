package org.malai.javafx.interaction.library;

import javafx.scene.control.TextField;
import javafx.scene.control.TextInputControl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.malai.javafx.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class TestTextChanged extends BaseJfXInteractionTest<TextChanged> {
	TextInputControl input;

	@Override
	@BeforeEach
	public void setUp() {
		super.setUp();
		input = new TextField();
	}

	@Override
	protected TextChanged createInteraction() {
		return new TextChanged();
	}
}
