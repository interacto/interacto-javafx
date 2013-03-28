package org.malai.swing.widget;

import javax.swing.JScrollPane;

/**
 * This interface is for widgets that can have a scroll pane.<br>
 * <br>
 * This file is part of Malai.<br>
 * Copyright (c) 2009-2013 Arnaud BLOUIN<br>
 * <br>
 * Malai is free software; you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation; either version 2 of the License, or (at your option) any later version.
 * <br>
 * Malai is distributed without any warranty; without even the implied
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * General Public License for more details.<br>
 * <br>
 * 05/15/2010<br>
 * @author Arnaud BLOUIN
 * @version 0.2
 * @since 0.2
 */
public interface ScrollableWidget {
	/**
	 * @return True if the panel is in a scroll pane.
	 * @since 0.2
	 */
	boolean hasScrollPane();

	/**
	 * @return The scroll pane of the panel (may be null).
	 * @since 0.2
	 */
	JScrollPane getScrollpane();
	
	/**
	 * @return True if the horizontal scroll bar is visible.
	 * @since 0.2
	 */
	boolean isHorizontalScrollbarVisible();
	
	/**
	 * @return True if the vertical scroll bar is visible.
	 * @since 0.2
	 */
	boolean isVerticalScrollbarVisible();
	
	/**
	 * Scroll the vertical scroll bar, if possible, using the given increment.
	 * @param increment The increment to apply on the vertical scroll bar.
	 * @since 0.2
	 */
	void scrollHorizontally(final int increment);
	
	/**
	 * Scroll the vertical scroll bar, if possible, using the given increment.
	 * @param increment The increment to apply on the vertical scroll bar.
	 * @since 0.2
	 */
	void scrollVertically(final int increment);
}
