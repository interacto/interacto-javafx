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
import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.control.Spinner;

/**
 * A user interaction for spinners.
 * @author Arnaud BLOUIN
 */
public class SpinnerChanged extends JfxInteraction<WidgetData<Spinner<?>>, SpinnerChangedFSM> {
	final SpinnerChangedFSM.SpinnerChangedFSMHandler handler;

	/**
	 * Creates the interaction.
	 */
	public SpinnerChanged() {
		super(new SpinnerChangedFSM());

		handler = new SpinnerChangedFSM.SpinnerChangedFSMHandler() {
			@Override
			public void initToChangedHandler(final ActionEvent event) {
				if(event.getSource() instanceof Spinner<?>) {
					((WidgetDataImpl<Spinner<?>>) data).setWidget((Spinner<?>) event.getSource());
				}
			}

			@Override
			public void reinitData() {
				SpinnerChanged.this.reinitData();
			}
		};

		fsm.buildFSM(handler);
	}

	@Override
	protected void onNewNodeRegistered(final Node node) {
		if(node instanceof Spinner<?>) {
			registerActionHandler(node);
		}
	}

	@Override
	protected WidgetDataImpl<Spinner<?>> createDataObject() {
		return new WidgetDataImpl<>();
	}

	@Override
	protected void onNodeUnregistered(final Node node) {
		if(node instanceof Spinner<?>) {
			unregisterActionHandler(node);
		}
	}
}
