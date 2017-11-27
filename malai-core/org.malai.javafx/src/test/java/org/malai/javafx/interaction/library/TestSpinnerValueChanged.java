package org.malai.javafx.interaction.library;

import javafx.scene.control.Spinner;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.malai.javafx.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class TestSpinnerValueChanged extends BaseJfXInteractionTest<SpinnerValueChanged> {
	Spinner<?> spinner;

	@Override
	@BeforeEach
	public void setUp() {
		super.setUp();
		spinner = new Spinner<>();
	}

	@Override
	protected SpinnerValueChanged createInteraction() {
		return new SpinnerValueChanged();
	}
}
