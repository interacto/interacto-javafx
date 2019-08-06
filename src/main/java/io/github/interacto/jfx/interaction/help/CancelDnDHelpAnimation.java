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
package io.github.interacto.jfx.interaction.help;

import javafx.scene.layout.Pane;

public class CancelDnDHelpAnimation extends DnDHelpAnimation {
	public CancelDnDHelpAnimation(final Pane learningPane, final Pane widget, final double x1, final double x2, final double y1, final double y2,
								final String textPress, final String textDrag, final String textRelease) {
		super(learningPane, widget, x1, x2, y1, y2, textPress, textDrag, textRelease);
	}

	public CancelDnDHelpAnimation(final Pane learningPane, final Pane widget) {
		this(learningPane, widget, 150, 500, 150, 350, "Press", "Drag or press 'ESC' to cancel", "Release");
	}
}
