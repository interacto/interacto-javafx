/*
 * Interacto
 * Copyright (C) 2020 Arnaud Blouin
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * You should have received a copy of the GNU General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 */
package io.github.interacto.jfx.interaction.library;

import io.github.interacto.fsm.CancelFSMException;
import io.github.interacto.jfx.TimeoutWaiter;
import java.util.Collections;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputControl;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;

@ExtendWith(ApplicationExtension.class)
public class TestTextInputChangedUI extends BaseJfXInteractionTest<TextInputChanged> implements TimeoutWaiter {
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
		waitForTimeoutTransitions();
		Mockito.verify(handler, Mockito.times(1)).fsmStops();
	}

	@Test
	void testTextTypedTextChanged() {
		interaction.registerToNodes(Collections.singletonList(input));
		clickOn(input).type(KeyCode.A, KeyCode.B, KeyCode.C);
		Assertions.assertEquals("abc", interaction.getData().getWidget().getText());
	}
}
