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
import javafx.scene.control.ComboBox;

/**
 * A user interaction for combo boxes.
 * @author Arnaud BLOUIN
 */
public class ComboBoxSelected extends JfxInteraction<WidgetData<ComboBox<?>>, ComboBoxSelectedFSM> {
	private final ComboBoxSelectedFSM.ComboBoxSelectedFSMHandler handler;

	/**
	 * Creates the interaction.
	 */
	public ComboBoxSelected() {
		super(new ComboBoxSelectedFSM());

		handler = new ComboBoxSelectedFSM.ComboBoxSelectedFSMHandler() {
			@Override
			public void initToSelectedHandler(final ActionEvent event) {
				if(event.getSource() instanceof ComboBox<?>) {
					((WidgetDataImpl<ComboBox<?>>) data).setWidget((ComboBox<?>) event.getSource());
				}
			}

			@Override
			public void reinitData() {
				ComboBoxSelected.this.reinitData();
			}
		};

		fsm.buildFSM(handler);
	}

	@Override
	protected void onNewNodeRegistered(final Node node) {
		if(node instanceof ComboBox<?>) {
			registerActionHandler(node);
		}
	}

	@Override
	protected WidgetDataImpl<ComboBox<?>> createDataObject() {
		return new WidgetDataImpl<>();
	}

	@Override
	protected void onNodeUnregistered(final Node node) {
		if(node instanceof ComboBox<?>) {
			unregisterActionHandler(node);
		}
	}
}
