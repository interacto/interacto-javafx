package io.interacto.jfx.interaction.library;

import javafx.scene.control.CheckBox;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.framework.junit5.ApplicationExtension;

@ExtendWith(ApplicationExtension.class)
public class TestBoxChecked extends BaseWIMPWidgetTest<CheckBox, BoxChecked> {
	@Override
	void triggerWidget() {
		wimpWidget.fire();
	}

	@Override
	CheckBox createWidget() {
		return new CheckBox();
	}

	@Override
	protected BoxChecked createInteraction() {
		return new BoxChecked();
	}
}
