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
package org.malai.javafx.action.library;

import java.io.File;
import javafx.scene.control.ProgressBar;
import org.malai.action.ActionImpl;
import org.malai.javafx.ui.JfxUI;
import org.malai.javafx.ui.OpenSaver;

/**
 * An model for loading and saving actions.
 * @param <B> The type of the widget that will be used to display information.
 * @author Arnaud Blouin
 */
public abstract class IOAction<B extends Object> extends ActionImpl {
	/** The current file loaded or saved. */
	protected File file;

	/** Define if the drawing has been successfully loaded or saved. */
	protected boolean ok;

	/** The object that does save and load operations. */
	protected OpenSaver<B> openSaveManager;

	/** The progress bar used to show the progress of the operation. */
	protected ProgressBar progressBar;

	/** The widget that displays the status of the I/O operation. */
	protected B statusWidget;

	protected JfxUI ui;


	/**
	 * Creates a save action.
	 */
	public IOAction() {
		super();
		ok = false;
	}


	@Override
	public void flush() {
		super.flush();
		file = null;
		openSaveManager = null;
		progressBar = null;
		statusWidget = null;
		ui = null;
	}


	@Override
	public boolean isRegisterable() {
		return false;
	}


	@Override
	public boolean canDo() {
		return file != null && openSaveManager != null && ui != null;
	}


	@Override
	public boolean hadEffect() {
		return super.hadEffect() && ok;
	}

	/**
	 * @return The targeted file.
	 */
	public File getFile() {
		return file;
	}

	/**
	 * @param file the file to set.
	 */
	public void setFile(final File file) {
		this.file = file;
	}

	/**
	 * @param bar The progress bar used to show the progress of the saving.
	 */
	public void setProgressBar(final ProgressBar bar) {
		progressBar = bar;
	}

	/**
	 * @param saver the saver to set.
	 */
	public void setOpenSaveManager(final OpenSaver<B> saver) {
		openSaveManager = saver;
	}

	/**
	 * @param status the widget that will be used to display I/O messages.
	 */
	public void setStatusWidget(final B status) {
		statusWidget = status;
	}

	public void setUi(final JfxUI jfxUI) {
		this.ui = jfxUI;
	}
}
