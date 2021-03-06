/*
 * Interacto
 * Copyright (C) 2020 Arnaud Blouin
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

import javafx.animation.Transition;
import javafx.scene.layout.Pane;

/**
 * Defines help animations.
 * Help animations aim at helping users in learning how to interact with the user interface.
 */
public interface HelpAnimation {
	/**
	 * @return The help animation.
	 */
	Transition getAnimation();

	/**
	 * @return True: the animation is installed in the zone pane.
	 */
	boolean isInstalled();

	/**
	 * @return The pane targeted by the animation.
	 */
	Pane getZonePane();

	/**
	 * @return The pane that will contain the animation.
	 */
	Pane getHelpPane();
}
