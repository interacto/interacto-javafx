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
package org.malai.javafx.ui;

import javafx.concurrent.Task;
import javafx.scene.control.ProgressBar;

/**
 * An interface for objects that must open and save abstract presentations and instruments data into a file.
 * @param <B> The type of the widget that will be used to display information.
 * @author Arnaud BLOUIN
 */
public interface OpenSaver<B> {
	/**
	 * Saves the abstract presentations and the instruments parameters of the given UI to the given file.
	 * @param path The destination path.
	 * @param progressBar The progress bar used to show the progress of the saving. Can be null.
	 * @param statusWidget The widget that displays the status of the saving operation. Can be null.
	 * @return True: the operation is successful.
	 */
	Task<Boolean> save(final String path, final ProgressBar progressBar, final B statusWidget);

	/**
	 * Opens the given file and sets the abstract presentations and the instruments parameters of the given UI to the given file.
	 * @param path The source path that contains information for presentations and instruments.
	 * @param progressBar The progress bar used to show the progress of the saving. Can be null.
	 * @param statusWidget The widget that displays the status of the loading operation. Can be null.
	 * @return True: the operation is successful.
	 */
	Task<Boolean> open(final String path, final ProgressBar progressBar, final B statusWidget);
}
