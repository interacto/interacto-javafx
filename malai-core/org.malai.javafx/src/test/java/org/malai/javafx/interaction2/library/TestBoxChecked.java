package org.malai.javafx.interaction2.library;

import javafx.scene.control.CheckBox;

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
