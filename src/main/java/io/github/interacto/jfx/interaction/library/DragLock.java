/*
 * Interacto
 * Copyright (C) 2020 Arnaud Blouin
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * You should have received a copy of the GNU General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 */
package io.github.interacto.jfx.interaction.library;

import io.github.interacto.jfx.interaction.JfxInteraction;
import javafx.scene.input.MouseEvent;

/**
 * The drag-lock user interaction
 */
public class DragLock extends JfxInteraction<SrcTgtPointsData, DragLockFSM> {
	private final DragLockFSM.DragLockFSMHandler handler;
	protected final DoubleClick firstClick;
	protected final DoubleClick sndClick;

	/**
	 * Creates a drag lock.
	 */
	public DragLock() {
		super(new DragLockFSM());

		handler = new DragLockFSM.DragLockFSMHandler() {
			@Override
			public void onLock() {
				final PointData pdata = firstClick.getData();
				((SrcTgtPointsDataImpl) DragLock.this.data).setPointData(pdata.getSrcLocalPoint().getX(),
					pdata.getSrcLocalPoint().getY(),
					pdata.getSrcLocalPoint().getZ(),
					pdata.getSrcScenePoint().getX(),
					pdata.getSrcScenePoint().getY(),
					pdata.getButton(),
					pdata.getSrcObject().orElse(null));
				setTgtData(pdata);
			}

			@Override
			public void onMove(final MouseEvent evt) {
				((PointDataImpl) sndClick.firstClick.getData()).setPointData(evt.getX(), evt.getY(), evt.getZ(), evt.getSceneX(),
					evt.getSceneY(), evt.getButton(), evt.getPickResult().getIntersectedNode());
				((PointDataImpl) sndClick.firstClick.getData()).setModifiersData(evt);
				setTgtData(sndClick.getData());
			}

			@Override
			public void onUnlock() {
				setTgtData(sndClick.getData());
			}

			@Override
			public void reinitData() {
				DragLock.this.reinitData();
			}

			private void setTgtData(final PointData pdata) {
				((SrcTgtPointsDataImpl) data).setTgtData(pdata.getSrcLocalPoint().getX(),
					pdata.getSrcLocalPoint().getY(),
					pdata.getSrcLocalPoint().getZ(),
					pdata.getSrcScenePoint().getX(),
					pdata.getSrcScenePoint().getY(),
					pdata.getSrcObject().orElse(null));

				((SrcTgtPointsDataImpl) data).altPressed = pdata.isAltPressed();
				((SrcTgtPointsDataImpl) data).ctrlPressed = pdata.isCtrlPressed();
				((SrcTgtPointsDataImpl) data).metaPressed = pdata.isMetaPressed();
				((SrcTgtPointsDataImpl) data).shiftPressed = pdata.isShiftPressed();
			}
		};

		firstClick = new DoubleClick(fsm.firstDbleClick);
		sndClick = new DoubleClick(fsm.sndDbleClick);
		fsm.buildFSM(handler);
	}

	@Override
	protected SrcTgtPointsData createDataObject() {
		return new SrcTgtPointsDataImpl();
	}

	@Override
	public void reinitData() {
		super.reinitData();
		firstClick.reinitData();
		sndClick.reinitData();
	}
}
