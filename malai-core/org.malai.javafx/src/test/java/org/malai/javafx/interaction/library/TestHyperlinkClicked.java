package org.malai.javafx.interaction.library;

import javafx.scene.control.Hyperlink;

public class TestHyperlinkClicked extends BaseWIMPWidgetTest<Hyperlink, HyperlinkClicked> {
	@Override
	Hyperlink createWidget() {
		return new Hyperlink();
	}

	@Override
	void triggerWidget() {
		wimpWidget.fire();
	}

	@Override
	protected HyperlinkClicked createInteraction() {
		return new HyperlinkClicked();
	}
}
