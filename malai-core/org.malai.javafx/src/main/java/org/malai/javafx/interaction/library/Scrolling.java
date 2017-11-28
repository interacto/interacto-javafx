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
package org.malai.javafx.interaction.library;

import java.util.Collection;
import java.util.Objects;

import javafx.scene.Node;
import javafx.scene.input.ScrollEvent;
import javafx.stage.Window;
import org.malai.interaction.TerminalState;
import org.malai.javafx.interaction.JfxInteractionImpl;
import org.malai.javafx.interaction.ScrollTransition;
import org.malai.stateMachine.SourceableState;
import org.malai.stateMachine.TargetableState;

/**
 * A JFX scrolling interaction
 * @author Arnaud Blouin
 */
public class Scrolling extends JfxInteractionImpl {
	/** The scrolled node. */
	protected Object scrolledNode;

	/** The X-coordinate of the scroll position. */
	protected double px;

	/** The Y-coordinate of the scroll position. */
	protected double py;

	/** The total increment of the scrolling. */
	protected double increment;


	/**
	 * Creates the interaction.
	 */
	public Scrolling() {
		super();
		initStateMachine();
	}


	@Override
	public void reinit() {
		super.reinit();

		scrolledNode = null;
		px = 0d;
		py = 0d;
		increment = 0d;
	}


	@SuppressWarnings("unused")
	@Override
	protected void initStateMachine() {
		final TerminalState wheeled = new TerminalState("scrolled"); //$NON-NLS-1$
		addState(wheeled);
		new ScrollingScrollTransition(initState, wheeled);
	}


	/**
	 * @return The object on which the scroll is performed.
	 */
	public Object getScrolledNode() {
		return scrolledNode;
	}

	/**
	 * @param node The object on which the scroll is performed.
	 */
	protected void setScrolledNode(final Object node) {
		scrolledNode = node;
	}

	/**
	 * @return The X-coordinate of the scroll position.
	 */
	public double getPx() {
		return px;
	}

	/**
	 * @param x The X-coordinate of the scroll position.
	 */
	protected void setPx(final double x) {
		px = x;
	}

	/**
	 * @return The Y-coordinate of the scroll position.
	 */
	public double getPy() {
		return py;
	}

	/**
	 * @param y The Y-coordinate of the scroll position.
	 */
	protected void setPy(final double y) {
		py = y;
	}

	/**
	 * @return The total increment of the scrolling.
	 */
	public double getIncrement() {
		return increment;
	}

	/**
	 * @param incr The total incr of the scrolling.
	 */
	protected void setIncrement(final double incr) {
		increment = incr;
	}


	@Override
	public void registerToNodes(Collection<Node> widgets) {
		super.registerToNodes(widgets);
		if(widgets != null) {
			widgets.stream().filter(Objects::nonNull).forEach(w -> w.addEventHandler(ScrollEvent.SCROLL, evt -> onScroll(evt, 0)));
		}
	}

	@Override
	public void registerToWindows(Collection<Window> windows) {
		super.registerToWindows(windows);
		if(windows != null) {
			windows.stream().filter(Objects::nonNull).forEach(w -> w.addEventHandler(ScrollEvent.SCROLL, evt -> onScroll(evt, 0)));
		}
	}


	/**
	 * This scroll transition modifies the scrolling interaction.
	 */
	public class ScrollingScrollTransition extends ScrollTransition {
		/**
		 * Creates the transition.
		 * @param inputState The input state of the transition.
		 * @param outputState The output state of the transition.
		 */
		public ScrollingScrollTransition(final SourceableState inputState, final TargetableState outputState) {
			super(inputState, outputState);
		}

		@Override
		public void action() {
			Scrolling.this.setLastHIDUsed(hid);
			Scrolling.this.increment += event.getDeltaY();
			Scrolling.this.px = event.getX();
			Scrolling.this.py = event.getY();
			Scrolling.this.scrolledNode = event.getSource();
		}
	}
}
