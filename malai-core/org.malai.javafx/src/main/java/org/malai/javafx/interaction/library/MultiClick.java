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

import java.util.ArrayList;
import java.util.List;
import javafx.event.EventHandler;
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
 * occurs with another button, the interaction ends. THe interaction is aborted is key 'escape' is pressed.
 * @author Arnaud Blouin
 */
public class MultiClick extends JfxInteractionImpl {
	private final EventHandler<MouseEvent> pressure;
	private final EventHandler<MouseEvent> release;
	private final EventHandler<MouseEvent> move;
	private final EventHandler<KeyEvent> keyPress;
	private final EventHandler<KeyEvent> keyRelease;

	/** The list of pressed position. */
	protected final List<Point3D> points;

	/** The current position of the pointing device. */
	protected Point3D currentPosition;

	/** The minimum number of points that the interaction must gather. */
	protected int minPoints;

	public MultiClick(final int minPts) {
		super();
		this.minPoints = minPts;
		points = new ArrayList<>();
		minPoints = minPts;
		initStateMachine();
		pressure = evt -> onPressure(evt, 0);
		release = evt -> onRelease(evt, 0);
		move = evt -> onMove(evt, 0);
		keyPress = evt -> onKeyPressure(evt, 0);
		keyRelease = evt -> onKeyRelease(evt, 0);
	}

	/**
	 * Creates the interaction.
	 */
	public MultiClick() {
		this(2);
	}

	@Override
	public void reinit() {
		super.reinit();
		points.clear();
		currentPosition = null;
	}

	/**
	 * @return True if the last srcPoint gathered by the interaction is a srcPoint created by the right
	 * click that ends the interaction. This method is useful to make the difference between
	 * points created using left clicks and the last one created using a right click.
	 */
	public boolean isLastPointFinalPoint() {
		return currentState instanceof TerminalState;
	}

	@Override
	protected void initStateMachine() {
		final IntermediaryState pressed = new IntermediaryState("pressed");
		final IntermediaryState released = new IntermediaryState("released");
		final TerminalState ended = new TerminalState("ended");
		final AbortingState aborted = new AbortingState("aborted");

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
	protected void onNodeUnregistered(final Node node) {
		node.removeEventHandler(MouseEvent.MOUSE_PRESSED, pressure);
		node.removeEventHandler(MouseEvent.MOUSE_RELEASED, release);
		node.removeEventHandler(MouseEvent.MOUSE_MOVED, move);
		node.removeEventHandler(KeyEvent.KEY_PRESSED, keyPress);
		node.removeEventHandler(KeyEvent.KEY_RELEASED, keyRelease);
	}

	@Override
	protected void onWindowUnregistered(final Window window) {
		window.removeEventHandler(MouseEvent.MOUSE_PRESSED, pressure);
		window.removeEventHandler(MouseEvent.MOUSE_RELEASED, release);
		window.removeEventHandler(MouseEvent.MOUSE_MOVED, move);
		window.removeEventHandler(KeyEvent.KEY_PRESSED, keyPress);
		window.removeEventHandler(KeyEvent.KEY_RELEASED, keyRelease);

	}

	@Override
	protected void onNewNodeRegistered(final Node node) {
		node.addEventHandler(MouseEvent.MOUSE_PRESSED, pressure);
		node.addEventHandler(MouseEvent.MOUSE_RELEASED, release);
		node.addEventHandler(MouseEvent.MOUSE_MOVED, move);
		node.addEventHandler(KeyEvent.KEY_PRESSED, keyPress);
		node.addEventHandler(KeyEvent.KEY_RELEASED, keyRelease);
	}

	@Override
	protected void onNewWindowRegistered(final Window window) {
		window.addEventHandler(MouseEvent.MOUSE_PRESSED, pressure);
		window.addEventHandler(MouseEvent.MOUSE_RELEASED, release);
		window.addEventHandler(MouseEvent.MOUSE_MOVED, move);
		window.addEventHandler(KeyEvent.KEY_PRESSED, keyPress);
		window.addEventHandler(KeyEvent.KEY_RELEASED, keyRelease);
	}


	/**
	 * @return The list of pressed position.
	 */
	public List<Point3D> getPoints() {
		return points;
	}

	/**
	 * @return The current position of the pointing device.
	 */
	public Point3D getCurrentPosition() {
		return currentPosition;
	}

	/**
	 * @return The minimum number of points that the interaction must gather.
	 */
	public int getMinPoints() {
		return minPoints;
	}

	/**
	 * @param minPoints The minimum number of points that the interaction must gather. Must be greater than 0.
	 */
	public void setMinPoints(final int minPoints) {
		if(minPoints > 0) {
			this.minPoints = minPoints;
		}
	}
}
