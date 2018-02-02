package org.malai.javafx.interaction2.library;

import javafx.event.ActionEvent;
import javafx.scene.control.DatePicker;

public class TestDatePicked extends BaseWIMPWidgetTest<DatePicker, DatePicked> {
	@Override
	DatePicker createWidget() {
		return new DatePicker();
	}

	@Override
	void triggerWidget() {
		wimpWidget.fireEvent(new ActionEvent(wimpWidget, null));
	}

	@Override
	protected DatePicked createInteraction() {
		return new DatePicked();
	}
}
