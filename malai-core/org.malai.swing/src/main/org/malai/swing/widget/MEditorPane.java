package org.malai.swing.widget;

import javax.swing.JEditorPane;
import javax.swing.JScrollBar;

import org.malai.interaction.Eventable;
import org.malai.swing.interaction.SwingEventManager;

/**
 * This widgets is based on a JEditorPane. It allows to be used in the Malai framework for picking.<br>
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
 * 06/05/2010<br>
 * @author Arnaud BLOUIN
 * @version 0.2
 * @since 0.2
 */
public class MEditorPane extends JEditorPane implements ScrollableWidget, Eventable {
	private static final long serialVersionUID = 1L;

	/** The possible scrollpane that contains the panel. */
	protected MScrollPane scrollpane;

	/** The event manager that listens events produced by the panel. May be null. */
	protected SwingEventManager eventManager;


	/**
	 * Creates an EditorPane.
	 * @param withScrollPane True: the panel will contain a scroll pane.
	 * @param withEvtManager True: the panel will have an event manager that gathers
	 * events produced using the editor pane (for picking).
	 * @since 0.2
	 */
	public MEditorPane(final boolean withScrollPane, final boolean withEvtManager) {
		this(withScrollPane, withEvtManager, false);
	}


	/**
	 * Creates an EditorPane.
	 * @param withScrollPane True: the panel will contain a scroll pane.
	 * @param withEvtManager True: the panel will have an event manager that gathers
	 * events produced using the editor pane (for picking).
	 * @param eventOnEachModification If true: each modification
	 * of the underlying document will launch an event (DocumentEvent)
	 * that can be used by a link based on the interaction TextChanged.
	 * If false, the user has to type on the back space key to create
	 * an event.
	 * @since 0.2
	 */
	public MEditorPane(final boolean withScrollPane, final boolean withEvtManager, final boolean eventOnEachModification) {
		super();

		if(withScrollPane) {
			scrollpane = new MScrollPane();
			scrollpane.getViewport().add(this);
		}

		if(withEvtManager) {
			eventManager = new SwingEventManager();
			eventManager.attachTo(this);
		}

		if(eventOnEachModification)
			getDocument().putProperty(SwingEventManager.OWNING_PROPERTY, this);
	}
	
	
	@Override
	public boolean isHorizontalScrollbarVisible() {
		return getScrollbar(true).isVisible();
	}


	@Override
	public boolean isVerticalScrollbarVisible() {
		return getScrollbar(true).isVisible();
	}
	
	
	@Override
	public void scrollHorizontally(final int increment) {
		scroll(increment, false);
	}


	@Override
	public void scrollVertically(final int increment) {
		scroll(increment, true);
	}


	/**
	 * Scroll the vertical or horizontal scroll bar, if possible, using the given increment.
	 * @param increment The increment to apply on the vertical scroll bar.
	 * @param vertical True: the vertical scroll bar is
	 * @since 0.1
	 */
	protected void scroll(final int increment, final boolean vertical) {
		final JScrollBar scrollbar = getScrollbar(vertical);

		if(scrollbar!=null && scrollbar.isVisible())
			scrollbar.setValue(scrollbar.getValue() - increment);
	}


	/**
	 * @param vertical True: the vertical scrollbar is returned. Otherwise, the horizontal scroll bar.
	 * @return The required scroll bar or null if the panel has no scrollpane.
	 * @since 0.1
	 */
	protected JScrollBar getScrollbar(final boolean vertical) {
		if(hasScrollPane())
			return vertical ? getScrollpane().getVerticalScrollBar() : getScrollpane().getHorizontalScrollBar();

		return null;
	}


	@Override
	public boolean hasScrollPane() {
		return scrollpane!=null;
	}



	@Override
	public MScrollPane getScrollpane() {
		return scrollpane;
	}


	@Override
	public SwingEventManager getEventManager() {
		return eventManager;
	}


	@Override
	public boolean hasEventManager() {
		return eventManager!=null;
	}
}
