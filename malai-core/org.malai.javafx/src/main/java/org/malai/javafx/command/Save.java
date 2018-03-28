/*
 * This file is part of Malai.
 * Copyright (c) 2009-2018 Arnaud BLOUIN
 * Malai is free software; you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation; either version 2 of the License, or (at your option) any later version.
 * Malai is distributed without any warranty; without even the implied
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * General Public License for more details.
 */
package org.malai.javafx.command;

import java.io.File;
import java.util.concurrent.ExecutionException;
import javafx.scene.control.ProgressBar;
import org.malai.error.ErrorCatcher;
import org.malai.javafx.ui.JfxUI;
import org.malai.javafx.ui.OpenSaver;

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
