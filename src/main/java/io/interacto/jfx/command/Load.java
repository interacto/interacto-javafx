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
package io.interacto.jfx.command;

import io.interacto.error.ErrorCatcher;
import io.interacto.jfx.ui.JfxUI;
import io.interacto.jfx.ui.OpenSaver;
import java.io.File;
import java.util.concurrent.ExecutionException;
import javafx.scene.control.ProgressBar;

/**
 * A load command.
 * @author Arnaud Blouin
 */
public class Load<B> extends IOCommand<B> {
	public Load(final File file, final OpenSaver<B> openSaveManager, final ProgressBar progressBar, final B statusWidget, final JfxUI ui) {
		super(file, openSaveManager, progressBar, statusWidget, ui);
	}

	/**
	 * Creates a save command.
	 */
	public Load() {
		super();
	}

	@Override
	protected void doCmdBody() {
		ui.reinit();
		try {
			ok = openSaveManager.open(file.getPath(), progressBar, statusWidget).get();
		}catch(InterruptedException | ExecutionException ex) {
			ok = false;
			ErrorCatcher.INSTANCE.reportError(ex);
		}
		ui.setModified(false);
	}
}
