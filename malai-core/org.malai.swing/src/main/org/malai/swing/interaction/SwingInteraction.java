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
import org.malai.interaction.InteractionImpl;
import org.malai.stateMachine.Transition;
import org.malai.swing.widget.MFrame;

/**
 * The core class for defining interactions using the Swing library.<br>
 * <br>
 * This file is part of Malai.<br>
 * Copyright (c) 2005-2014 Arnaud BLOUIN<br>
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
public abstract class SwingInteraction extends InteractionImpl implements SwingEventProcessor {
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
	public void onKeyRelease(final int key, final char keyChar, final int idHID, final Object object) {
		boolean again = true;

		if(activated) {
			Transition t;

			for(int i=0, j=currentState.getTransitions().size(); i<j && again; i++) {
				t = currentState.getTransition(i);

				if(t instanceof KeyReleaseTransition) {
					final KeyReleaseTransition krt = (KeyReleaseTransition)t;
					krt.setKey(key);
					krt.setKeyChar(keyChar);
					krt.setHid(idHID);
					krt.setSource(object);

					if(t.isGuardRespected()) {
						// Removing from the 'still in process' list
						removeKeyEvent(idHID, key);
						again = !checkTransition(t);
					}
				}
			}
		}

		// Removing from the 'still in process' list
		if(again)
			removeKeyEvent(idHID, key);
	}
	
	
	@Override
	public void onKeyPressure(final int key, final char keyChar, final int idHID, final Object object) {
		if(!activated) return ;

		boolean again = true;
		Transition t;

		for(int i=0, j=currentState.getTransitions().size(); i<j && again; i++) {
			t = currentState.getTransition(i);

			if(t instanceof KeyPressureTransition) {
				final KeyPressureTransition kpt = (KeyPressureTransition)t;
				kpt.setKey(key);
				kpt.setKeyChar(keyChar);
				kpt.setSource(object);
				kpt.setHid(idHID);

				again = !checkTransition(t);

				if(!again && !stillInProgressContainsKey(idHID, key))
					// Adding an event 'still in process'
					addEvent(new KeyPressEvent(idHID, key, keyChar, object));
			}
		}
	}


	/** Checks that the list stillProcessingEvents does not contains a keyEvent corresponding to the given one. */
	private boolean stillInProgressContainsKey(final int idHID, final int key) {
		if(stillProcessingEvents==null) return false;
		for(final Event evt : stillProcessingEvents)
			if(idHID==evt.getIdHID() && evt instanceof KeyPressEvent && ((KeyPressEvent)evt).keyCode==key)
				return true;
		return false;
	}
	
	@Override
	public void onTextChanged(final JTextComponent textComp) {
		if(!activated) return ;

		boolean again = true;
		Transition t;

		for(int i=0, j=currentState.getTransitions().size(); i<j && again; i++) {
			t = currentState.getTransition(i);

			if(t instanceof TextChangedTransition) {
				final TextChangedTransition tct = (TextChangedTransition) t;

				tct.setWidget(textComp);
				tct.setText(textComp.getText());
				again = !checkTransition(t);
			}
		}
	}
	
	

	@Override
	public void onButtonPressed(final AbstractButton button) {
		if(!activated) return ;

		boolean again = true;
		Transition t;

		for(int i=0, j=currentState.getTransitions().size(); i<j && again; i++) {
			t = currentState.getTransition(i);

			if(t instanceof SwingButtonPressedTransition) {
				((SwingButtonPressedTransition)t).setWidget(button);
				again = !checkTransition(t);
			}
		}
	}



	@Override
	public void onItemSelected(final ItemSelectable itemSelectable) {
		if(!activated) return ;

		boolean again = true;
		Transition t;

		for(int i=0, j=currentState.getTransitions().size(); i<j && again; i++) {
			t = currentState.getTransition(i);

			if(t instanceof ListTransition) {
				((ListTransition)t).setWidget(itemSelectable);
				again = !checkTransition(t);
			}
		}
	}



	@Override
	public void onSpinnerChanged(final JSpinner spinner) {
		if(!activated) return ;

		boolean again = true;
		Transition t;

		for(int i=0, j=currentState.getTransitions().size(); i<j && again; i++) {
			t = currentState.getTransition(i);

			if(t instanceof SpinnerTransition) {
				((SpinnerTransition)t).setWidget(spinner);
				again = !checkTransition(t);
			}
		}
	}


	@Override
	public void onTreeSelectionChanged(final Object src, final TreePath[] changedPaths, final boolean isSelectionAdded) {
		if(!activated) return ;

		boolean again = true;
		Transition t;

		for(int i=0, j=currentState.getTransitions().size(); i<j && again; i++) {
			t = currentState.getTransition(i);

			if(t instanceof TreeSelectionTransition) {
				TreeSelectionTransition treeTrans = (TreeSelectionTransition) t;
				treeTrans.setWidget(src);
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
		Transition t;

		for(int i=0, j=currentState.getTransitions().size(); i<j && again; i++) {
			t = currentState.getTransition(i);

			if(t instanceof TreeExpansionTransition) {
				TreeExpansionTransition treeTrans = (TreeExpansionTransition) t;
				treeTrans.setWidget(src);
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
		Transition t;

		for(int i=0, j=currentState.getTransitions().size(); i<j && again; i++) {
			t = currentState.getTransition(i);

			if(t instanceof CheckBoxTransition) {
				((CheckBoxTransition)t).setWidget(checkbox);
				again = !checkTransition(t);
			}
		}
	}


	@Override
	public void onMenuItemPressed(final JMenuItem menuItem) {
		if(!activated) return ;

		boolean again = true;
		Transition t;

		for(int i=0, j=currentState.getTransitions().size(); i<j && again; i++) {
			t = currentState.getTransition(i);

			if(t instanceof MenuItemTransition) {
				((MenuItemTransition)t).setWidget(menuItem);
				again = !checkTransition(t);
			}
		}
	}
	
	
	@Override
	public void onWindowClosed(final MFrame frame) {
		if(!activated) return ;

		Transition transition;
		boolean again = true;

		for(int i=0, j=currentState.getTransitions().size(); again && i<j; i++) {
			transition = currentState.getTransition(i);

			if(transition instanceof WindowClosedTransition) {
				((WindowClosedTransition)transition).setWidget(frame);

				if(transition.isGuardRespected())
					again = !checkTransition(transition);
			}
		}
	}


	@Override
	public void onTabChanged(final JTabbedPane tabbedPanel) {
		if(!activated) return ;

		Transition transition;
		boolean again = true;

		for(int i=0, j=currentState.getTransitions().size(); again && i<j; i++) {
			transition = currentState.getTransition(i);

			if(transition instanceof TabSelectedTransition) {
				((TabSelectedTransition)transition).setWidget(tabbedPanel);

				if(transition.isGuardRespected())
					again = !checkTransition(transition);
			}
		}
	}
	
	
	/**
	 * Removes the given KeyPress event from the events 'still in process' list.
	 * @param idHID The identifier of the HID which produced the event.
	 * @param key The key code of the event to remove.
	 * @since 0.2
	 */
	protected void removeKeyEvent(final int idHID, final int key) {
		if(stillProcessingEvents==null) return ;

		boolean removed = false;
		Event event;

		for(int i=0, size=stillProcessingEvents.size(); i<size && !removed; i++) {
			event = stillProcessingEvents.get(i);

			if(event instanceof KeyPressEvent && event.getIdHID()==idHID && ((KeyPressEvent)event).keyCode==key) {
				removed = true;
				stillProcessingEvents.remove(i);
			}
		}
	}
	
	
	@Override
	protected void processEvent(final Event event) {
		super.processEvent(event);
		if(event instanceof KeyPressEvent) {
			final KeyPressEvent key = (KeyPressEvent)event;
			onKeyPressure(key.keyCode, key.keyChar, key.getIdHID(), key.source);
		}
	}
	
	
	
	/**
	 * This class defines an event corresponding to the pressure of a key.
	 */
	protected static class KeyPressEvent extends InteractionImpl.Event {
		/** The code of the key pressed. */
		protected int keyCode;

		/** The char of the key. */
		protected char keyChar;

		/** The object that produced the key event. */
		protected Object source;


		/**
		 * Creates the event.
		 * @param idHID The identifier of the HID.
		 * @param keyCode The key code.
		 * @param keyChar The char of the key.
		 * @param source The object that produced the event.
		 * @since 0.2
		 */
		public KeyPressEvent(final int idHID, final int keyCode, final char keyChar, final Object source) {
			super(idHID);
			this.keyCode = keyCode;
			this.keyChar = keyChar;
			this.source  = source;
		}
	}
}
