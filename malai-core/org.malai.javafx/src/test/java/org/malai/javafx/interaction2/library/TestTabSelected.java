package org.malai.javafx.interaction2.library;

import javafx.event.ActionEvent;
import javafx.scene.control.TabPane;

public class TestTabSelected extends BaseWIMPWidgetTest<TabPane, TabSelected> {
	@Override
	TabPane createWidget() {
		return new TabPane();
	}

	@Override
	void triggerWidget() {
		wimpWidget.fireEvent(new ActionEvent(wimpWidget, null));
	}

	@Override
	protected TabSelected createInteraction() {
		return new TabSelected();
	}
}
