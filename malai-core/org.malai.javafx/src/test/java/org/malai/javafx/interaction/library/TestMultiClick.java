package org.malai.javafx.interaction.library;

import org.junit.jupiter.api.extension.ExtendWith;
import org.malai.javafx.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class TestMultiClick extends BaseJfXInteractionTest<MultiClick> {
	@Override
	protected MultiClick createInteraction() {
		return new MultiClick();
	}
}
