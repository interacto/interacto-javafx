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
import java.util.concurrent.atomic.AtomicInteger;
import javafx.application.Platform;
import javafx.scene.Node;
import org.testfx.util.WaitForAsyncUtils;

public abstract class TestBinder<W> {
	W widget1;
	W widget2;
	JfxWidgetBinding<? extends Command, ?, ?> binding;

	public static class StubCmd extends CommandImpl {
		final AtomicInteger exec = new AtomicInteger(0);

		@Override
		protected void doCmdBody() {
			synchronized(exec) {
				exec.incrementAndGet();
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
