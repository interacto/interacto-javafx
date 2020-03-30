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
import javafx.geometry.Point3D;
import javafx.scene.input.MouseEvent;

public class DnD extends JfxInteraction<SrcTgtPointsData, DnDFSM> {
	private final DnDFSM.DnDFSMHandler handler;

	public DnD(final boolean updateSrcOnUpdate, final boolean cancellable) {
		super(new DnDFSM(cancellable));

		handler = new DnDFSM.DnDFSMHandler() {
			@Override
			public void onPress(final MouseEvent evt) {
				((SrcTgtPointsDataImpl) data).setPointData(evt.getX(), evt.getY(), evt.getZ(), evt.getSceneX(), evt.getSceneY(),
					evt.getButton(), evt.getPickResult().getIntersectedNode());
				setTgt(evt);
			}

			@Override
			public void onDrag(final MouseEvent evt) {
				if(updateSrcOnUpdate) {
					final SrcTgtPointsDataImpl d = (SrcTgtPointsDataImpl) data;
					final Point3D tgtlocal = data.getTgtLocalPoint();
					final Point3D tgtScene = data.getTgtScenePoint();
					d.setPointData(tgtlocal.getX(), tgtlocal.getY(), tgtlocal.getZ(),
						tgtScene.getX(), tgtScene.getY(), data.getButton(), data.getTgtObject().orElse(null));
				}

				setTgt(evt);
			}

			@Override
			public void onRelease(final MouseEvent evt) {
				setTgt(evt);
			}

			@Override
			public void reinitData() {
				DnD.this.reinitData();
			}

			private void setTgt(final MouseEvent evt) {
				((SrcTgtPointsDataImpl) data).setTgtData(evt.getX(), evt.getY(), evt.getZ(), evt.getSceneX(), evt.getSceneY(),
					evt.getPickResult().getIntersectedNode());
				((SrcTgtPointsDataImpl) data).setModifiersData(evt);
			}
		};

		fsm.buildFSM(handler);
	}

	public DnD() {
		this(false, false);
	}

	@Override
	protected SrcTgtPointsDataImpl createDataObject() {
		return new SrcTgtPointsDataImpl();
	}
}
