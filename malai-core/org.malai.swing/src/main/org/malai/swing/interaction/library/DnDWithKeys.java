package org.malai.swing.interaction.library;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

import org.malai.interaction.AbortingState;
import org.malai.interaction.InteractionImpl;
import org.malai.interaction.IntermediaryState;
import org.malai.interaction.MoveTransition;
import org.malai.interaction.ReleaseTransition;
import org.malai.interaction.TerminalState;
import org.malai.picking.Pickable;
import org.malai.stateMachine.SourceableState;
import org.malai.stateMachine.TargetableState;
import org.malai.swing.interaction.KeyPressureTransition;
import org.malai.swing.interaction.KeyReleaseTransition;

/**
 * A DnD interaction is a Drag-And-Drop: press-drag-release that can have key pressures (eg modifiers).<br>
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
 * 2013/07/20<br>
 * @author Arnaud BLOUIN
 * @since 1.1
 */
public class DnDWithKeys extends PointInteraction {
	/** The ending point of the dnd. */
	protected Point endPt;

	/** The object picked at the end of the dnd. */
	protected Pickable startObject;

	/** The list of the keys pressed. */
	protected List<Integer> keys;


	/**
	 * Creates the interaction.
	 */
	public DnDWithKeys() {
		super();
		keys = new ArrayList<>();
		initStateMachine();
	}


	@SuppressWarnings("unused")
	@Override
	protected void initStateMachine() {
		final IntermediaryState pressed = new IntermediaryState("pressed"); //$NON-NLS-1$
		final IntermediaryState keyPressed = new IntermediaryState("keypressed"); //$NON-NLS-1$
		final AbortingState abort 		= new AbortingState("abort"); //$NON-NLS-1$
		final IntermediaryState dragged = new IntermediaryState("dragged"); //$NON-NLS-1$
		final TerminalState released	= new TerminalState("released"); //$NON-NLS-1$

		addState(pressed);
		addState(dragged);
		addState(released);
		addState(keyPressed);
		addState(abort);

		new PointPressureTransition(initState, pressed) {
			@Override
			public void action() {
				super.action();
				DnDWithKeys.this.endPt	 	 = new Point(this.x, this.y);
				DnDWithKeys.this.button  	 = this.button;
				DnDWithKeys.this.startObject = DnDWithKeys.this.target;
			}

		};

		new PointPressureTransition(keyPressed, pressed) {
			@Override
			public void action() {
				super.action();
				DnDWithKeys.this.endPt	 	 = new Point(this.x, this.y);
				DnDWithKeys.this.button  	 = this.button;
				DnDWithKeys.this.startObject = DnDWithKeys.this.target;
			}
		};

		new Move4DnD(pressed, dragged);
		new Move4DnD(dragged, dragged);
		new Release4DnD(dragged, released);
		new Release4DnD(pressed, released);
		new DnDWithKeysKeyRelease(pressed, pressed);
		new DnDWithKeysKeyPress(pressed, pressed);
		new DnDWithKeysKeyRelease(dragged, dragged);
		new DnDWithKeysKeyPress(dragged, dragged);
		new DnDWithKeysKeyPress(initState, keyPressed);
		new DnDWithKeysKeyPress(keyPressed, keyPressed);
		new DnDWithKeysKeyRelease(keyPressed, keyPressed) {
			@Override
			public boolean isGuardRespected() {
				return DnDWithKeys.this.keys.contains(key) && DnDWithKeys.this.keys.size()>1;
			}
		};
		new DnDWithKeysKeyRelease(keyPressed, abort) {
			@Override
			public boolean isGuardRespected() {
				return DnDWithKeys.this.keys.contains(key) && DnDWithKeys.this.keys.size()<=1;
			}
		};
	}


	private class DnDWithKeysKeyRelease extends KeyReleaseTransition {
		/**
		 * Creates the transition.
		 * @param inputState The input state of the transition.
		 * @param outputState The output state of the transition.
		 */
		public DnDWithKeysKeyRelease(final SourceableState inputState, final TargetableState outputState) {
			super(inputState, outputState);
		}

		@Override
		public void action() {
			DnDWithKeys.this.keys.remove((Integer)this.key);
		}
	}

	private class DnDWithKeysKeyPress extends KeyPressureTransition {
		/**
		 * Creates the transition.
		 * @param inputState The input state of the transition.
		 * @param outputState The output state of the transition.
		 */
		public DnDWithKeysKeyPress(final SourceableState inputState, final TargetableState outputState) {
			super(inputState, outputState);
		}

		@Override
		public void action() {
			DnDWithKeys.this.keys.add(this.key);
		}
	}


	@Override
	public void reinit() {
		super.reinit();
		endPt 		= null;
		startObject = null;

		if(keys!=null)
			keys.clear();
	}


	/**
	 * @return The ending point of the dnd.
	 * @since 0.1
	 */
	public Point getEndPt() {
		return endPt;
	}


	/**
	 * @return The button of the device used to performed the dnd (-1 if no button).
	 * @since 0.1
	 */
	@Override
	public int getButton() {
		return button;
	}


	/**
	 * @return the keys pressed.
	 */
	public List<Integer> getKeys() {
		return keys;
	}


	/**
 	 * @return The object picked at the beginning of the dnd.
	 * @since 0.1
	 */
	public Pickable getStartObject() {
		return startObject;
	}


	/**
	 * A transition dedicated for the DnD interaction. Corresponds to the release event.
	 */
	public class Release4DnD extends ReleaseTransition {
		/**
		 * Creates the transition.
		 * @param inputState The input state of the transition.
		 * @param outputState The output state of the transition.
		 */
		public Release4DnD(final SourceableState inputState, final TargetableState outputState) {
			super(inputState, outputState);
		}
		@Override
		public boolean isGuardRespected() {
			return DnDWithKeys.this.button==this.button && DnDWithKeys.this.getLastHIDUsed()==this.hid;
		}
	}


	/**
	 * A transition dedicated for the DnD interaction. Corresponds to the move events.
	 */
	public class Move4DnD extends MoveTransition {
		/**
		 * Creates the transition.
		 * @param inputState The input state of the transition.
		 * @param outputState The output state of the transition.
		 */
		public Move4DnD(final SourceableState inputState, final TargetableState outputState) {
			super(inputState, outputState);
		}
		@Override
		public void action() {
			super.action();
			DnDWithKeys.this.endPt.setLocation(x, y);
			DnDWithKeys.this.target = InteractionImpl.getPickableAt(this.x, this.y, this.source);
		}
		@Override
		public boolean isGuardRespected() {
			return DnDWithKeys.this.getLastHIDUsed()==this.hid;
		}
	}
}
