package org.malai.swing.interaction;

import java.awt.ItemSelectable;

import javax.swing.AbstractButton;
import javax.swing.JCheckBox;
import javax.swing.JMenuItem;
import javax.swing.JSpinner;
import javax.swing.JTabbedPane;
import javax.swing.text.JTextComponent;
import javax.swing.tree.TreePath;

import org.malai.swing.widget.MFrame;

/**
 * This interface can be used for object that want to gather Swing events (button pressed, etc.) produced by HIDs.<br>
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
 * 2013-02-21<br>
 * @author Arnaud BLOUIN
 * @since 0.2
 */
public interface SwingEventHandler {
	/**
	 * Defines actions to do when a button is activated.
	 * @param button The pressed button.
	 * @since 0.1
	 */
	void onButtonPressed(final AbstractButton button);

	/**
	 * Defines actions to do when the value of the given spinner change.
	 * @param spinner The spinner that changed.
	 * @since 0.2
	 */
	void onSpinnerChanged(final JSpinner spinner);

	/**
	 * Defines actions to do when the selected items of the given list changed.
	 * @param itemSelectable The list that changed.
	 * @since 0.2
	 */
	void onItemSelected(final ItemSelectable itemSelectable);

	/**
	 * Defines actions to do when the given check box is selected/unselected.
	 * @param checkbox The modified check box;
	 * @since 0.2
	 */
	void onCheckBoxModified(final JCheckBox checkbox);

	/**
	 * Defines actions to do when the given menu item is pressed.
	 * @param menuItem The pressed menu item.
	 * @since 0.2
	 */
	void onMenuItemPressed(final JMenuItem menuItem);

	/**
	 * Defines actions to do when the text of a text field or something equivalent is modified.
	 * @param textComp The text field.
	 * @since 0.1
	 */
	void onTextChanged(final JTextComponent textComp);
	
	/**
	 * Defines actions to do when the decorative close button of a frame is pressed.
	 * @param frame The frame closed.
	 * @since 0.2
	 */
	void onWindowClosed(final MFrame frame);

	/**
	 * Defines actions to do when the selected tab of a tabbed panel has changed.
	 * @param tabbedPanel The tabbed panel that produces the event.
	 * @since 0.2
	 */
	void onTabChanged(final JTabbedPane tabbedPanel);
	
	/**
	 * Defines actions to do when the selected rows of a jtree have changed.
	 * @param src The source tree.
	 * @param changedPaths The changed paths (removed or added).
	 * @param isSelectionAdded Defines whether the changed paths have been removed or added.
	 */
	void onTreeSelectionChanged(final Object src, final TreePath[] changedPaths, final boolean isSelectionAdded);
	
	/**
	 * Defines actions to do when expanding or collapsing a node.
	 * @param src The source tree.
	 * @param expandedPath The expanded or collapsed node.
	 * @param isExpanded Defines whether the node has been expanded or collasped.
	 */
	void onTreeExpanded(final Object src, final TreePath expandedPath, final boolean isExpanded);
}
