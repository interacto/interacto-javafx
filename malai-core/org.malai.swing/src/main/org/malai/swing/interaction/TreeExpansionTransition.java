package org.malai.swing.interaction;

import javax.swing.tree.TreePath;

import org.malai.interaction.Transition;
import org.malai.stateMachine.SourceableState;
import org.malai.stateMachine.TargetableState;

/**
 * This transition corresponds to an expansion of a collapsing of a jtree node.<br>
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
 * 2013-03-28<br>
 * @author Arnaud BLOUIN
 * @since 0.2
 */
public class TreeExpansionTransition extends Transition {
	/** The source object that produced the event (normally a JTree). */
	protected Object src;
	
	/** The tree path of the expanded/collapsed node. */
	protected TreePath expandedPath;
	
	/** Defines whether the node has been expanded or collapsed. */
	protected boolean isExpanded;

	/**
	 * Defines a transition.
	 * @param inputState The source state of the transition.
	 * @param outputState The target state of the transition.
	 * @throws IllegalArgumentException If one of the given parameters is null or not valid.
	 */
	public TreeExpansionTransition(final SourceableState inputState, final TargetableState outputState) {
		super(inputState, outputState);
	}

	/**
	 * @return The source object that produced the event (normally a JTree).
	 */
	public Object getSrc() {
		return src;
	}

	/**
	 * @param src The source object that produced the event.
	 */
	public void setSrc(final Object src) {
		this.src = src;
	}

	/**
	 * @return The tree path of the expanded/collapsed node.
	 */
	public TreePath getExpandedPath() {
		return expandedPath;
	}

	/**
	 * @param expandedPath The tree path of the expanded/collapsed node.
	 */
	public void setExpandedPath(final TreePath expandedPath) {
		this.expandedPath = expandedPath;
	}

	/**
	 * @return Defines whether the node has been expanded or collapsed.
	 */
	public boolean isExpanded() {
		return isExpanded;
	}

	/**
	 * @param isExpanded Defines whether the node has been expanded or collapsed.
	 */
	public void setExpanded(final boolean isExpanded) {
		this.isExpanded = isExpanded;
	}
}
