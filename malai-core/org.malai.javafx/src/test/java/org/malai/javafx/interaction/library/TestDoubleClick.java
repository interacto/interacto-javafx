package org.malai.javafx.interaction.library;

import org.junit.jupiter.api.extension.ExtendWith;
import org.malai.javafx.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class TestDoubleClick extends BaseJfXInteractionTest<DoubleClick> {
	@Override
	protected DoubleClick createInteraction() {
		return new DoubleClick();
	}
}
