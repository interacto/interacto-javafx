/*
 * This file is part of Malai.
 * Copyright (c) 2005-2017 Arnaud BLOUIN
 * Malai is free software; you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation; either version 2 of the License, or (at your option) any later version.
 * Malai is distributed without any warranty; without even the implied
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * General Public License for more details.
 */
package org.malai.javafx.action;

import java.io.File;
import java.util.concurrent.ExecutionException;
import javafx.scene.control.ProgressBar;
import org.malai.error.ErrorCatcher;
import org.malai.javafx.ui.JfxUI;
import org.malai.javafx.ui.OpenSaver;

/**
 * A load action.
 * @author Arnaud Blouin
 */
public class Load<B> extends IOAction<B> {
	public Load(final File file, final OpenSaver<B> openSaveManager, final ProgressBar progressBar, final B statusWidget, final JfxUI ui) {
		super(file, openSaveManager, progressBar, statusWidget, ui);
	}

	/**
	 * Creates a save action.
	 */
	public Load() {
		super();
	}

	@Override
	protected void doActionBody() {
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
