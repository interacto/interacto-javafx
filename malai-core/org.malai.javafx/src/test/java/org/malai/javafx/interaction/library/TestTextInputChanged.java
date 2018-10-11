package org.malai.javafx.interaction.library;

import java.util.Collections;
import javafx.event.ActionEvent;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputControl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.malai.fsm.CancelFSMException;
import org.mockito.Mockito;
import org.testfx.framework.junit5.ApplicationExtension;

@ExtendWith(ApplicationExtension.class)
public class TestTextInputChanged extends BaseWIMPWidgetTest<TextInputControl, TextInputChanged> {
	@Override
	@BeforeEach
	void setUp() {
		super.setUp();
		sleep = 1300L;
	}

	@Override
	TextInputControl createWidget() {
		return new TextField();
	}

	@Override
	void triggerWidget() {
		wimpWidget.fireEvent(new ActionEvent(wimpWidget, null));
	}

	@Override
	protected TextInputChanged createInteraction() {
		return new TextInputChanged();
	}

	@Test
	void testTextChangedThreeTimes() throws CancelFSMException {
		interaction.registerToNodes(Collections.singletonList(wimpWidget));
		triggerWidget();
		sleep(500L);
		triggerWidget();
		sleep(200L);
		triggerWidget();
		sleep(1200L);
		Mockito.verify(handler, Mockito.times(1)).fsmStops();
		Mockito.verify(handler, Mockito.times(1)).fsmStarts();
		Mockito.verify(handler, Mockito.times(3)).fsmUpdates();
	}

	@Test
	void testTextChangedButNotFinished() throws CancelFSMException {
		interaction.registerToNodes(Collections.singletonList(wimpWidget));
		triggerWidget();
		sleep(1200L);
		triggerWidget();
		sleep(1200L);
		Mockito.verify(handler, Mockito.times(2)).fsmStops();
		Mockito.verify(handler, Mockito.times(2)).fsmStarts();
		Mockito.verify(handler, Mockito.times(2)).fsmUpdates();
	}
}
