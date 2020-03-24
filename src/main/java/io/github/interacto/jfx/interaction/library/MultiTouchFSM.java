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

import io.github.interacto.jfx.interaction.JfxConcurrFSM;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import javafx.event.Event;
import javafx.scene.input.TouchEvent;

/**
 * The FSM that defines a multi-touch interaction (that works like a DnD)
 */
public class MultiTouchFSM extends JfxConcurrFSM<TouchDnDFSM.TouchDnDFSMHandler, TouchDnDFSM> {
	/**
	 * Creates the FSM.
	 */
	public MultiTouchFSM(final int nbTouch) {
		super(IntStream.range(0, nbTouch).mapToObj(i -> new TouchDnDFSM()).collect(Collectors.toSet()));
	}

	@Override
	protected void buildFSM(final TouchDnDFSM.TouchDnDFSMHandler dataHandler) {
		super.buildFSM(dataHandler);
		getConccurFSMs().forEach(fsm -> fsm.buildFSM(dataHandler));
	}

	@Override
	public boolean process(final Event event) {
		if(!(event instanceof TouchEvent)) {
			return false;
		}

		final Optional<TouchDnDFSM> touch = getConccurFSMs().stream()
			.filter(fsm -> fsm.touchID == ((TouchEvent) event).getTouchPoint().getId())
			.findAny();

		if(touch.isPresent()) {
			return touch.get().process(event);
		}

		for(final TouchDnDFSM conccurFSM : getConccurFSMs()) {
			if(conccurFSM.process(event)) {
				return true;
			}
		}

		return false;
	}
}
