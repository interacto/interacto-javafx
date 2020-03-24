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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javafx.scene.input.TouchPoint;

public class MultiTouchDataImpl implements MultiTouchData {
	private final Map<Integer, TouchDataImpl> touchData;

	public MultiTouchDataImpl() {
		super();
		touchData = new HashMap<>();
	}

	@Override
	public List<TouchData> touchData() {
		return new ArrayList<>(touchData.values());
	}

	public void addTouchData(final TouchDataImpl data) {
		touchData.put(data.getTouchId(), data);
	}

	@Override
	public void flush() {
		touchData.clear();
	}

	public void setTouch(final TouchPoint tp) {
		final TouchDataImpl data = this.touchData.get(tp.getId());
		if(data != null) {
			data.setTgtData(tp.getX(), tp.getY(), tp.getZ(), tp.getSceneX(), tp.getSceneY(), tp.getPickResult().getIntersectedNode());
		}
	}
}
