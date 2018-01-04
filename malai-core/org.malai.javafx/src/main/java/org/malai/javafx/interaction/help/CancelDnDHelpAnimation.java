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
package org.malai.javafx.interaction.help;

import javafx.scene.layout.Pane;

public class CancelDnDHelpAnimation extends DnDHelpAnimation {
	public CancelDnDHelpAnimation(final Pane learningPane, final double x1, final double x2, final double y1, final double y2, final String textPress,
								  final String textDrag, final String textRelease) {
		super(learningPane, x1, x2, y1, y2, textPress, textDrag, textRelease);
	}

	public CancelDnDHelpAnimation(final Pane learningPane) {
		this(learningPane, 150, 500, 150, 350, "Press", "Drag or press 'ESC' to cancel", "Release");
	}
}
