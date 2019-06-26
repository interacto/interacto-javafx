package io.interacto.jfx.interaction.library;

import javafx.scene.control.Hyperlink;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.framework.junit5.ApplicationExtension;

@ExtendWith(ApplicationExtension.class)
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
