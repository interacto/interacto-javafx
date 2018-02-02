package org.malai.javafx.interaction2.library;

import javafx.event.ActionEvent;
import javafx.scene.control.Spinner;

public class TestSpinnerChanged extends BaseWIMPWidgetTest<Spinner<?>, SpinnerChanged> {
	@Override
	Spinner<?> createWidget() {
		return new Spinner<>();
	}

	@Override
	void triggerWidget() {
		wimpWidget.fireEvent(new ActionEvent(wimpWidget, null));
	}

	@Override
	protected SpinnerChanged createInteraction() {
		return new SpinnerChanged();
	}
}
