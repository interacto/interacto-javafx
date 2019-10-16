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
package io.github.interacto.jfx.command;

import io.github.interacto.error.ErrorCatcher;
import io.github.interacto.jfx.ui.JfxUI;
import io.github.interacto.jfx.ui.OpenSaver;
import java.io.File;
import java.util.concurrent.ExecutionException;
import javafx.scene.control.ProgressBar;

/**
 * A save command.
 * @author Arnaud Blouin
 */
public class Save<B> extends IOCommand<B> {
	public Save(final File file, final OpenSaver<B> openSaveManager, final ProgressBar progressBar, final B statusWidget, final JfxUI ui) {
		super(file, openSaveManager, progressBar, statusWidget, ui);
	}

	/**
	 * Creates a save command.
	 */
	public Save() {
		super();
	}

	@Override
	protected void doCmdBody() {
		try {
			ok = openSaveManager.save(file.getPath(), progressBar, statusWidget).get();
		}catch(InterruptedException | ExecutionException ex) {
			ok = false;
			ErrorCatcher.INSTANCE.reportError(ex);
		}
		ui.setModified(false);
	}
}