package org.malai.javafx.interaction.library;

import org.junit.jupiter.api.extension.ExtendWith;
import org.malai.javafx.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class TestScrolling extends BaseJfXInteractionTest<Scrolling> {
	@Override
	protected Scrolling createInteraction() {
		return new Scrolling();
	}
}
