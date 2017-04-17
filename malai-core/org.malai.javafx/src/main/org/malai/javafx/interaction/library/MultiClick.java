/*
  * This file is part of Malai.
  * Copyright (c) 2005-2017 Arnaud BLOUIN
  * LaTeXDraw is free software; you can redistribute it and/or modify it under
  * the terms of the GNU General Public License as published by the Free Software
  * Foundation; either version 2 of the License, or (at your option) any later version.
  * LaTeXDraw is distributed without any warranty; without even the implied
  * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
  * General Public License for more details.
 */
package org.malai.javafx.interaction.library;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javafx.geometry.Point3D;
import javafx.scene.Node;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.stage.Window;
import org.malai.interaction.AbortingState;
import org.malai.interaction.IntermediaryState;
import org.malai.interaction.TerminalState;
import org.malai.javafx.interaction.EscapeKeyPressureTransition;
import org.malai.javafx.interaction.JfxInteractionImpl;
import org.malai.javafx.interaction.MoveTransition;
import org.malai.javafx.interaction.PressureTransition;
import org.malai.javafx.interaction.ReleaseTransition;

/**
 * This interaction allows to performed several clicks using button 1. If a click
 * occurs with another button, the interaction ends. THe interaction is aborted is key 'escape'
 * is pressed.
 */
public class MultiClick extends JfxInteractionImpl {
	/** The list of pressed position. */
	protected List<Point3D> points;

	/** The current position of the pointing device. */
	protected Point3D currentPosition;

	/** The minimum number of points that the interaction must gather. */
	protected int minPoints;


	/**
	 * Creates the interaction.
	 */
	public MultiClick() {
		super();
		minPoints = 2;
		initStateMachine();
	}

	@Override
	public void reinit() {
		super.reinit();

		if(points == null) {
			points = new ArrayList<>();
		}else {
			points.clear();
		}

		currentPosition = null;
	}

	/**
	 * @return True if the last srcPoint gathered by the interaction is a srcPoint created by the right
	 * click that ends the interaction. This method is useful to make the difference between
	 * points created using left clicks and the last one created using a right click.
	 * @since 0.2
	 */
	public boolean isLastPointFinalPoint() {
		return currentState instanceof TerminalState;
	}

	@SuppressWarnings("unused")
	@Override
	protected void initStateMachine() {
		final IntermediaryState pressed = new IntermediaryState("pressed"); //$NON-NLS-1$
		final IntermediaryState released = new IntermediaryState("released"); //$NON-NLS-1$
		final TerminalState ended = new TerminalState("ended"); //$NON-NLS-1$
		final AbortingState aborted = new AbortingState("aborted"); //$NON-NLS-1$

		addState(pressed);
		addState(released);
		addState(ended);
		addState(aborted);

		new PressureTransition(initState, pressed) {
			@Override
			public void action() {
				MultiClick.this.setLastHIDUsed(this.hid);
				MultiClick.this.points.add(new Point3D(this.event.getX(), this.event.getY(), this.event.getZ()));
				currentPosition = new Point3D(this.event.getX(), this.event.getY(), this.event.getZ());
			}

			@Override
			public boolean isGuardRespected() {
				return this.event.getButton() == MouseButton.PRIMARY;
			}
		};

		new ReleaseTransition(pressed, released) {
			@Override
			public boolean isGuardRespected() {
				return this.event.getButton() == MouseButton.PRIMARY && this.hid == MultiClick.this.getLastHIDUsed();
			}
		};

		new PressureTransition(released, pressed) {
			@Override
			public void action() {
				MultiClick.this.points.add(new Point3D(this.event.getX(), this.event.getY(), this.event.getZ()));
				currentPosition = new Point3D(this.event.getX(), this.event.getY(), this.event.getZ());
			}

			@Override
			public boolean isGuardRespected() {
				return this.event.getButton() == MouseButton.PRIMARY && this.hid == MultiClick.this.getLastHIDUsed();
			}
		};

		new EscapeKeyPressureTransition(pressed, aborted);

		new MoveTransition(released, released) {
			@Override
			public void action() {
				currentPosition = new Point3D(this.event.getX(), this.event.getY(), this.event.getZ());
			}

			@Override
			public boolean isGuardRespected() {
				return this.hid == MultiClick.this.getLastHIDUsed();
			}
		};

		new EscapeKeyPressureTransition(released, aborted);

		new PressureTransition(released, ended) {
			@Override
			public void action() {
				MultiClick.this.points.add(new Point3D(this.event.getX(), this.event.getY(), this.event.getZ()));
			}

			@Override
			public boolean isGuardRespected() {
				return this.event.getButton() != MouseButton.PRIMARY & this.hid == MultiClick.this.getLastHIDUsed() &&
					MultiClick.this.points.size() + 1 >= MultiClick.this.minPoints;
			}
		};

		new PressureTransition(released, aborted) {
			@Override
			public boolean isGuardRespected() {
				return this.event.getButton() != MouseButton.PRIMARY && this.hid == MultiClick.this.getLastHIDUsed() &&
					MultiClick.this.points.size() < MultiClick.this.minPoints;
			}
		};
	}

	@Override
	public void registerToNodes(final Collection<Node> widgets) {
		widgets.forEach(widget -> {
			widget.addEventHandler(MouseEvent.MOUSE_PRESSED, evt -> onPressure(evt, 0));
			widget.addEventHandler(MouseEvent.MOUSE_RELEASED, evt -> onRelease(evt, 0));
			widget.addEventHandler(MouseEvent.MOUSE_MOVED, evt -> onMove(evt, 0));
			widget.addEventHandler(KeyEvent.KEY_PRESSED, evt -> onKeyPressure(evt, 0));
			widget.addEventHandler(KeyEvent.KEY_RELEASED, evt -> onKeyRelease(evt, 0));
		});
	}

	@Override
	public void registerToWindows(final Collection<Window> windows) {
		windows.forEach(window -> {
			window.addEventHandler(MouseEvent.MOUSE_PRESSED, evt -> onPressure(evt, 0));
			window.addEventHandler(MouseEvent.MOUSE_RELEASED, evt -> onRelease(evt, 0));
			window.addEventHandler(MouseEvent.MOUSE_MOVED, evt -> onMove(evt, 0));
			window.addEventHandler(KeyEvent.KEY_PRESSED, evt -> onKeyPressure(evt, 0));
			window.addEventHandler(KeyEvent.KEY_RELEASED, evt -> onKeyRelease(evt, 0));
		});
	}

	/**
	 * @return The list of pressed position.
	 * @since 0.2
	 */
	public List<Point3D> getPoints() {
		return points;
	}

	/**
	 * @return The current position of the pointing device.
	 * @since 0.2
	 */
	public Point3D getCurrentPosition() {
		return currentPosition;
	}

	/**
	 * @return The minimum number of points that the interaction must gather.
	 * @since 0.2
	 */
	public int getMinPoints() {
		return minPoints;
	}

	/**
	 * @param minPoints The minimum number of points that the interaction must gather. Must be greater than 0.
	 * @since 0.2
	 */
	public void setMinPoints(final int minPoints) {
		if(minPoints > 0) {
			this.minPoints = minPoints;
		}
	}
}
