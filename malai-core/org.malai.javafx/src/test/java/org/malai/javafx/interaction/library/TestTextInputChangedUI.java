package org.malai.javafx.interaction.library;

import java.util.Collections;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputControl;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.malai.fsm.CancelFSMException;
import org.mockito.Mockito;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(ApplicationExtension.class)
public class TestTextInputChangedUI extends BaseJfXInteractionTest<TextInputChanged> {
	TextInputControl input;

	@Override
	TextInputChanged createInteraction() {
		return new TextInputChanged();
	}

	@Override
	@Start
	public void start(final Stage stage) throws Exception {
		super.start(stage);
		input = new TextField();
		final Scene scene = new Scene(input);
		stage.setScene(scene);
		stage.show();
		stage.toFront();
	}

	@Test
	void testTypeOnInputTextChanged() throws CancelFSMException {
		interaction.registerToNodes(Collections.singletonList(input));
		clickOn(input).type(KeyCode.A, KeyCode.B, KeyCode.C);
		Mockito.verify(handler, Mockito.times(3)).fsmUpdates();
		Mockito.verify(handler, Mockito.times(1)).fsmStarts();
	}

	@Test
	void testTypeOnInputTextChangedStopped() throws CancelFSMException {
		interaction.registerToNodes(Collections.singletonList(input));
		clickOn(input).type(KeyCode.A, KeyCode.B, KeyCode.C);
		sleep(1200L);
		Mockito.verify(handler, Mockito.times(1)).fsmStops();
	}

	@Test
	void testTextTypedTextChanged() {
		interaction.registerToNodes(Collections.singletonList(input));
		clickOn(input).type(KeyCode.A, KeyCode.B, KeyCode.C);
		assertEquals("abc", interaction.getWidget().getText());
	}
}
