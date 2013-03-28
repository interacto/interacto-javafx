package org.malai.swing.interaction;

import java.awt.ItemSelectable;

import javax.swing.AbstractButton;
import javax.swing.JCheckBox;
import javax.swing.JMenuItem;
import javax.swing.JSpinner;
import javax.swing.JTabbedPane;
import javax.swing.text.JTextComponent;
import javax.swing.tree.TreePath;

import org.malai.interaction.InitState;
import org.malai.interaction.Interaction;
import org.malai.stateMachine.ITransition;
import org.malai.swing.widget.MFrame;

/**
 * The core class for defining interactions using the Swing library.<br>
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
 * 2012-02-24<br>
 * @author Arnaud BLOUIN
 * @since 0.2
 */
public abstract class SwingInteraction extends Interaction implements SwingEventHandler {
	/**
	 * Creates a Swing interaction.
	 */
	public SwingInteraction() {
		super();
	}

	
	/**
	 * Creates the Swing interaction.
	 * @param initState The initial state of the interaction.
	 * @throws IllegalArgumentException If the given state is null.
	 * @since 0.2
	 */
	public SwingInteraction(final InitState initState) {
		super(initState);
	}
	
	
	@Override
	public void onTextChanged(final JTextComponent textComp) {
		if(!activated) return ;

		boolean again = true;
		ITransition t;

		for(int i=0, j=currentState.getTransitions().size(); i<j && again; i++) {
			t = currentState.getTransition(i);

			if(t instanceof TextChangedTransition) {
				final TextChangedTransition tct = (TextChangedTransition) t;

				tct.setTextComp(textComp);
				tct.setText(textComp.getText());
				again = !checkTransition(t);
			}
		}
	}
	
	

	@Override
	public void onButtonPressed(final AbstractButton button) {
		if(!activated) return ;

		boolean again = true;
		ITransition t;

		for(int i=0, j=currentState.getTransitions().size(); i<j && again; i++) {
			t = currentState.getTransition(i);

			if(t instanceof ButtonPressedTransition) {
				((ButtonPressedTransition)t).setButton(button);
				again = !checkTransition(t);
			}
		}
	}



	@Override
	public void onItemSelected(final ItemSelectable itemSelectable) {
		if(!activated) return ;

		boolean again = true;
		ITransition t;

		for(int i=0, j=currentState.getTransitions().size(); i<j && again; i++) {
			t = currentState.getTransition(i);

			if(t instanceof ListTransition) {
				((ListTransition)t).setList(itemSelectable);
				again = !checkTransition(t);
			}
		}
	}



	@Override
	public void onSpinnerChanged(final JSpinner spinner) {
		if(!activated) return ;

		boolean again = true;
		ITransition t;

		for(int i=0, j=currentState.getTransitions().size(); i<j && again; i++) {
			t = currentState.getTransition(i);

			if(t instanceof SpinnerTransition) {
				((SpinnerTransition)t).setSpinner(spinner);
				again = !checkTransition(t);
			}
		}
	}


	@Override
	public void onTreeSelectionChanged(final Object src, final TreePath[] changedPaths, final boolean isSelectionAdded) {
		if(!activated) return ;

		boolean again = true;
		ITransition t;

		for(int i=0, j=currentState.getTransitions().size(); i<j && again; i++) {
			t = currentState.getTransition(i);

			if(t instanceof TreeSelectionTransition) {
				TreeSelectionTransition treeTrans = (TreeSelectionTransition) t;
				treeTrans.setSrc(src);
				treeTrans.setChangedPaths(changedPaths);
				treeTrans.setSelectionAdded(isSelectionAdded);
				again = !checkTransition(t);
			}
		}
	}


	@Override
	public void onTreeExpanded(final Object src, final TreePath expandedPath, final boolean isExpanded) {
		if(!activated) return ;

		boolean again = true;
		ITransition t;

		for(int i=0, j=currentState.getTransitions().size(); i<j && again; i++) {
			t = currentState.getTransition(i);

			if(t instanceof TreeExpansionTransition) {
				TreeExpansionTransition treeTrans = (TreeExpansionTransition) t;
				treeTrans.setSrc(src);
				treeTrans.setExpanded(isExpanded);
				treeTrans.setExpandedPath(expandedPath);
				again = !checkTransition(t);
			}
		}
	}


	@Override
	public void onCheckBoxModified(final JCheckBox checkbox) {
		if(!activated) return ;

		boolean again = true;
		ITransition t;

		for(int i=0, j=currentState.getTransitions().size(); i<j && again; i++) {
			t = currentState.getTransition(i);

			if(t instanceof CheckBoxTransition) {
				((CheckBoxTransition)t).setCheckBox(checkbox);
				again = !checkTransition(t);
			}
		}
	}


	@Override
	public void onMenuItemPressed(final JMenuItem menuItem) {
		if(!activated) return ;

		boolean again = true;
		ITransition t;

		for(int i=0, j=currentState.getTransitions().size(); i<j && again; i++) {
			t = currentState.getTransition(i);

			if(t instanceof MenuItemTransition) {
				((MenuItemTransition)t).setMenuItem(menuItem);
				again = !checkTransition(t);
			}
		}
	}
	
	
	@Override
	public void onWindowClosed(final MFrame frame) {
		if(!activated) return ;

		ITransition transition;
		boolean again = true;

		for(int i=0, j=currentState.getTransitions().size(); again && i<j; i++) {
			transition = currentState.getTransition(i);

			if(transition instanceof WindowClosedTransition) {
				((WindowClosedTransition)transition).setFrame(frame);

				if(transition.isGuardRespected())
					again = !checkTransition(transition);
			}
		}
	}


	@Override
	public void onTabChanged(final JTabbedPane tabbedPanel) {
		if(!activated) return ;

		ITransition transition;
		boolean again = true;

		for(int i=0, j=currentState.getTransitions().size(); again && i<j; i++) {
			transition = currentState.getTransition(i);

			if(transition instanceof TabSelectedTransition) {
				((TabSelectedTransition)transition).setTabbedPane(tabbedPanel);

				if(transition.isGuardRespected())
					again = !checkTransition(transition);
			}
		}
	}
}
