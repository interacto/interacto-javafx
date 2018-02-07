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
package org.malai.javafx.interaction2.library;

import javafx.event.Event;
import javafx.scene.input.MouseEvent;

public class DragLock extends PointInteraction<DragLockFSM, Event> {
	private final DragLockFSM.DragLockFSMHandler handler;
	private final DoubleClick firstClick;
	private final DoubleClick sndClick;

	public DragLock() {
		super(new DragLockFSM());

		handler = new DragLockFSM.DragLockFSMHandler() {
			@Override
			public void onMove(final MouseEvent event) {
				setPointData(event);
			}

			@Override
			public void reinitData() {
				DragLock.this.reinitData();
			}
		};

		firstClick = new DoubleClick(fsm.firstDbleClick);
		sndClick = new DoubleClick(fsm.sndDbleClick);
		fsm.buildFSM(handler);
	}

	@Override
	public void reinitData() {
		super.reinitData();
		firstClick.reinitData();
		sndClick.reinitData();
	}

	public PointInteractionData getLockData() {
		return firstClick.getClickData();
	}

	public PointInteractionData getTgtData() {
		return sndClick.getClickData().getSrcLocalPoint() == null ? this : sndClick.getClickData();
	}
}
