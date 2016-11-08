/*
 * This file is part of Malai
 * Copyright (c) 2005-2017 Arnaud BLOUIN
 *  LaTeXDraw is free software; you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation; either version 2 of the License, or
 *  (at your option) any later version.
 *  LaTeXDraw is distributed without any warranty; without even the
 *  implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR
 *  PURPOSE. See the GNU General Public License for more details.
 */
package org.malai.javafx.interaction.library;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import javafx.geometry.Point3D;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;
import org.malai.interaction.AbortingState;
import org.malai.interaction.IntermediaryState;
import org.malai.interaction.TerminalState;
import org.malai.javafx.interaction.KeyPressureTransition;
import org.malai.javafx.interaction.KeyReleaseTransition;
import org.malai.javafx.interaction.MoveTransition;
import org.malai.javafx.interaction.PressureTransition;
import org.malai.javafx.interaction.ReleaseTransition;
import org.malai.stateMachine.SourceableState;
import org.malai.stateMachine.TargetableState;

/**
 * A DnD interaction is a Drag-And-Drop: press-drag-release that can have key pressures (eg modifiers).
 * @author Arnaud BLOUIN
 */
public class DnDWithKeys extends PointInteraction {
	/** The ending point of the dnd. */
	protected Optional<Point3D> endPt;

	/** The object picked at the end of the dnd. */
	protected Optional<Object> startObject;

	/** The code of the key. */
	protected List<KeyCode> keyCodes;


	/**
	 * Creates the interaction.
	 */
	public DnDWithKeys() {
		super();
		keyCodes = new ArrayList<>();
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

		new PressureTransition(initState, pressed) {
			@Override
			public void action() {
				super.action();
				DnDWithKeys.this.endPt	 	 = Optional.of(new Point3D(this.event.getX(), this.event.getY(), this.event.getZ()));
				DnDWithKeys.this.button  	 = this.event.getButton();
				DnDWithKeys.this.startObject = Optional.ofNullable(this.event.getSource());
			}

		};

		new PressureTransition(keyPressed, pressed) {
			@Override
			public void action() {
				super.action();
				DnDWithKeys.this.endPt	 	 = Optional.of(new Point3D(this.event.getX(), this.event.getY(), this.event.getZ()));
				DnDWithKeys.this.button  	 = this.event.getButton();
				DnDWithKeys.this.startObject = Optional.ofNullable(this.event.getSource());
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
				return DnDWithKeys.this.keyCodes.contains(this.event.getCode()) && DnDWithKeys.this.keyCodes.size()>1;
			}
		};
		new DnDWithKeysKeyRelease(keyPressed, abort) {
			@Override
			public boolean isGuardRespected() {
				return DnDWithKeys.this.keyCodes.contains(this.event.getCode()) && DnDWithKeys.this.keyCodes.size()<=1;
			}
		};
	}


	private class DnDWithKeysKeyRelease extends KeyReleaseTransition {
		/**
		 * Creates the transition.
		 * @param inputState The input state of the transition.
		 * @param outputState The output state of the transition.
		 */
		DnDWithKeysKeyRelease(final SourceableState inputState, final TargetableState outputState) {
			super(inputState, outputState);
		}

		@Override
		public void action() {
			DnDWithKeys.this.keyCodes.remove(this.event.getCode());
		}
	}

	private class DnDWithKeysKeyPress extends KeyPressureTransition {
		/**
		 * Creates the transition.
		 * @param inputState The input state of the transition.
		 * @param outputState The output state of the transition.
		 */
		DnDWithKeysKeyPress(final SourceableState inputState, final TargetableState outputState) {
			super(inputState, outputState);
		}

		@Override
		public void action() {
			DnDWithKeys.this.keyCodes.add(this.event.getCode());
		}
	}


	@Override
	public void reinit() {
		super.reinit();
		endPt 		= null;
		startObject = null;

		if(keyCodes!=null)
			keyCodes.clear();
	}


	/**
	 * @return The ending point of the dnd.
	 * @since 0.1
	 */
	public Optional<Point3D> getEndPt() {
		return endPt;
	}


	/**
	 * @return The button of the device used to performed the dnd (-1 if no button).
	 * @since 0.1
	 */
	@Override
	public MouseButton getButton() {
		return button;
	}


	/**
	 * @return the keys pressed.
	 */
	public List<KeyCode> getKeys() {
		return keyCodes;
	}


	/**
 	 * @return The object picked at the beginning of the dnd.
	 * @since 0.1
	 */
	public Optional<Object> getStartObject() {
		return startObject;
	}


	/**
	 * A transition dedicated for the DnD interaction. Corresponds to the release event.
	 */
	private class Release4DnD extends ReleaseTransition {
		Release4DnD(final SourceableState inputState, final TargetableState outputState) {
			super(inputState, outputState);
		}
		@Override
		public boolean isGuardRespected() {
			return DnDWithKeys.this.button==this.event.getButton() && DnDWithKeys.this.getLastHIDUsed()==this.hid;
		}
	}


	/**
	 * A transition dedicated for the DnD interaction. Corresponds to the move events.
	 */
	private class Move4DnD extends MoveTransition {
		Move4DnD(final SourceableState inputState, final TargetableState outputState) {
			super(inputState, outputState);
		}
		@Override
		public void action() {
			super.action();
			DnDWithKeys.this.endPt = Optional.of(new Point3D(this.event.getX(), this.event.getY(), this.event.getZ()));
			DnDWithKeys.this.target = this.event.getTarget();
		}
		@Override
		public boolean isGuardRespected() {
			return DnDWithKeys.this.getLastHIDUsed()==this.hid;
		}
	}
}
