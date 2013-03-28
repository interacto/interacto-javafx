package org.malai.swing.interaction.library;

import javax.swing.tree.TreePath;

import org.malai.interaction.TerminalState;
import org.malai.swing.interaction.SwingInteraction;
import org.malai.swing.interaction.TreeSelectionTransition;
import org.malai.swing.widget.MTree;

/**
 * An interaction to use to select a node of a tree widget.<br>
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
public class TreeNodeSelected extends SwingInteraction {
	/** The tree widget to interact with. */
	protected MTree tree;
	
	/** The (un-)select tree paths. */
	protected TreePath[] changedPaths;
	
	/** Defines whether it is a selection or an de-selection. */
	protected boolean isSelectionAdded;
	
	/**
	 * Creates the interaction.
	 */
	public TreeNodeSelected() {
		super();
		initStateMachine();
	}

	
	@SuppressWarnings("unused")
	@Override
	protected void initStateMachine() {
		TerminalState end = new TerminalState("end");
		new TreeSelectionTransition(initState, end) {
			@Override
			public void action() {
				super.action();
				if(src instanceof MTree)
					TreeNodeSelected.this.tree = (MTree) src;
				TreeNodeSelected.this.changedPaths = changedPaths;
				TreeNodeSelected.this.isSelectionAdded = isSelectionAdded;
			}
		};
		
		addState(end);
	}


	/**
	 * @return The tree widget to interact with.
	 */
	public MTree getTree() {
		return tree;
	}


	/**
	 * @return The (un-)select tree paths.
	 */
	public TreePath[] getChangedPaths() {
		return changedPaths;
	}


	/**
	 * @return Defines whether it is a selection or an de-selection.
	 */
	public boolean isSelectionAdded() {
		return isSelectionAdded;
	}
}
