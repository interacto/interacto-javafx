package org.malai.javafx.interaction.library;

import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.malai.javafx.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class TestTabSelected extends BaseJfXInteractionTest<TabSelected> {
	TabPane tabPane;
	Tab tab;

	@Override
	@BeforeEach
	public void setUp() {
		super.setUp();
		tabPane = new TabPane();
		tab = new Tab();
		tabPane.getTabs().addAll(tab);
	}

	@Override
	protected TabSelected createInteraction() {
		return new TabSelected();
	}
}
