package io.github.interacto.jfx.interaction.library;

import javafx.event.ActionEvent;
import javafx.scene.control.DatePicker;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.framework.junit5.ApplicationExtension;

@ExtendWith(ApplicationExtension.class)
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
