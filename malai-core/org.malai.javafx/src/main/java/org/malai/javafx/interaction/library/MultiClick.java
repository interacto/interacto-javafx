/*
 * This file is part of Malai.
 * Copyright (c) 2009-2018 Arnaud BLOUIN
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
import javafx.event.Event;
import javafx.geometry.Point3D;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import org.malai.javafx.interaction.JfxInteraction;

public class MultiClick extends JfxInteraction<MultiClickFSM, Event> {
	private final MultiClickFSM.MultiClickFSMHandler handler;
	/** The list of pressed position. */
	protected final List<Point3D> points;
	/** The current position of the pointing device. */
	protected Point3D currentPosition;
	protected MouseButton currentButton;

	public MultiClick(final int minPts) {
		super(new MultiClickFSM(minPts));
		points = new ArrayList<>();

		handler = new MultiClickFSM.MultiClickFSMHandler() {
			@Override
			public void onClick(final MouseEvent event) {
				MultiClick.this.points.add(new Point3D(event.getX(), event.getY(), event.getZ()));
				currentPosition = new Point3D(event.getX(), event.getY(), event.getZ());
				currentButton = event.getButton();
			}

			@Override
			public void onMove(final MouseEvent event) {
				currentPosition = new Point3D(event.getX(), event.getY(), event.getZ());
			}

			@Override
			public void reinitData() {
				MultiClick.this.reinitData();
			}
		};

		fsm.buildFSM(handler);
	}

	public MultiClick() {
		this(2);
	}

	@Override
	public MultiClickFSM getFsm() {
		return super.getFsm();
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

	public MouseButton getCurrentButton() {
		return currentButton;
	}

	@Override
	public void reinitData() {
		super.reinitData();
		points.clear();
		currentPosition = null;
	}
}
