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
package io.github.interacto.jfx.interaction;

import io.github.interacto.fsm.FSM;
import io.github.interacto.fsm.OutputState;
import io.github.interacto.interaction.InteractionData;
import io.reactivex.disposables.Disposable;
import java.util.List;
import java.util.stream.Collectors;
import javafx.event.Event;
import javafx.event.EventType;
import javafx.scene.Node;
import javafx.stage.Window;

/**
 * The base implementation of a user interaction that uses concurrent FSMs.
 * @param <D> The type of the interaction data.
 * @param <F> The type of the FSM.
 */
public abstract class ConcurrentInteraction<D extends InteractionData,
		F extends JfxConcurrFSM<? extends FSMDataHandler, ? extends FSM<Event>>> extends JfxInteraction<D, F> {
	protected final List<Disposable> disposables;

	/**
	 * Creates the concurrent interaction.
	 * @param fsm The concurrent FSM that defines the behavior of the user interaction.
	 */
	protected ConcurrentInteraction(final F fsm) {
		super(fsm);
		disposables = this
			.fsm
			.getConccurFSMs()
			.stream()
			.map(conc -> conc.currentState()
				.subscribe(current -> this.updateEventsRegistered(current.getValue(), current.getKey())))
			.collect(Collectors.toList());
	}

	@Override
	public boolean isRunning() {
		return isActivated() && fsm.isStarted();
	}

	@Override
	public void onNodeUnregistered(final Node node) {
		getCurrentAcceptedEvents(null).forEach(type -> unregisterEventToNode(type, node));
	}

	@Override
	public void onNewNodeRegistered(final Node node) {
		getCurrentAcceptedEvents(null).forEach(type -> registerEventToNode(type, node));
	}

	@Override
	protected void onWindowUnregistered(final Window window) {
		getCurrentAcceptedEvents(null).forEach(type -> unregisterEventToWindow(type, window));
	}

	@Override
	protected void onNewWindowRegistered(final Window window) {
		getCurrentAcceptedEvents(null).forEach(type -> registerEventToWindow(type, window));
	}

	@Override
	public List<EventType<?>> getCurrentAcceptedEvents(final OutputState<Event> state) {
		return fsm.getConccurFSMs()
			.stream()
			.map(concFSM -> getEventTypesOf(concFSM.getCurrentState()))
			.flatMap(s -> s.stream())
			.distinct()
			.collect(Collectors.toList());
	}

	@Override
	public void uninstall() {
		super.uninstall();
		this.disposables.forEach(disposable -> disposable.dispose());
	}
}
