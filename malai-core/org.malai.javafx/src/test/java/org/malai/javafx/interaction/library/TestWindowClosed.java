package org.malai.javafx.interaction.library;

import javafx.stage.Stage;
import javafx.stage.Window;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.malai.javafx.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class TestWindowClosed extends BaseJfXInteractionTest<WindowClosed> {
	Window window;

	@Override
	@BeforeEach
	public void setUp() {
		super.setUp();
		Stage stage = new Stage();
		window = stage.getScene().getWindow();
	}

	@Override
	protected WindowClosed createInteraction() {
		return new WindowClosed();
	}
}
