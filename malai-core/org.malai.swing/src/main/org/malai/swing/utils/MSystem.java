package org.malai.swing.utils;

import java.awt.event.KeyEvent;

/**
 * An helper class.<br>
 * <br>
 * This file is part of LaTeXDraw.<br>
 * Copyright (c) 2005-2013 Arnaud BLOUIN<br>
 * <br>
 * LaTeXDraw is free software; you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation; either version 2 of the License, or (at your option) any later version.
 * <br>
 * LaTeXDraw is distributed without any warranty; without even the implied
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * General Public License for more details.<br>
 * <br>
 * 2013-03-04<br>
 * @author Arnaud BLOUIN
 * @since 0.2
 */
public final class MSystem {
	public static final MSystem INSTANCE = new MSystem();
	
	private MSystem() {
		super();
	}
	
	
	/**
	 * @return The control modifier used by the currently used operating system.
	 * @since 0.2
	 */
	public int getControlKey() {
		if(System.getProperty("os.name").toLowerCase().contains("mac"))
			return KeyEvent.VK_META;
		return KeyEvent.VK_CONTROL;
	}
}
