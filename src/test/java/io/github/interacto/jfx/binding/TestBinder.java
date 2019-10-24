/*
 * Interacto
 * Copyright (C) 2019 Arnaud Blouin
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
package io.github.interacto.jfx.binding;

import io.github.interacto.command.Command;
import io.github.interacto.command.CommandImpl;
import io.reactivex.disposables.Disposable;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import javafx.application.Platform;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.scene.Node;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.testfx.api.FxRobot;
import org.testfx.util.WaitForAsyncUtils;

public abstract class TestBinder<W> {
	W widget1;
	W widget2;
	StubCmd cmd;
	AtomicInteger cpt;
	JfXWidgetBinding<? extends Command, ?, ?> binding;
	List<Command> producedCmds;
	Disposable disposable;

	@BeforeEach
	void setUp() {
		producedCmds = new ArrayList<>();
		cmd = new StubCmd();
		cpt = new AtomicInteger();
	}

	@AfterEach
	void afterEach(final FxRobot robot) {
		robot.release(new KeyCode[] {});
		robot.release(new MouseButton[] {});
		WaitForAsyncUtils.waitForFxEvents();

		if(binding != null) {
			binding.uninstallBinding();
		}

		if(disposable != null && !disposable.isDisposed()) {
			disposable.dispose();
		}
	}

	public static class StubCmd extends CommandImpl {
		final IntegerProperty exec = new SimpleIntegerProperty(0);

		@Override
		protected void doCmdBody() {
			synchronized(exec) {
				exec.setValue(exec.getValue() + 1);
			}
		}

		@Override
		public boolean canDo() {
			return true;
		}

		@Override
		public RegistrationPolicy getRegistrationPolicy() {
			return RegistrationPolicy.NONE;
		}
	}

	void grabFocus(final Node node) {
		Platform.runLater(() -> node.requestFocus());
		WaitForAsyncUtils.waitForFxEvents(20);
	}
}
