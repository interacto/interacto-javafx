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
package org.malai.presentation;

import org.malai.preferences.Preferenciable;
import org.malai.properties.Modifiable;
import org.malai.properties.Reinitialisable;

/**
 * The concrete presentation is the representation of the abstract presentation.
 * @author Arnaud BLOUIN
 * @since 0.1
 */
public interface ConcretePresentation extends Preferenciable, Modifiable, Reinitialisable {
	/**
	 * Updates the concrete presentation.
	 * @since 0.2
	 */
	void update();
}
