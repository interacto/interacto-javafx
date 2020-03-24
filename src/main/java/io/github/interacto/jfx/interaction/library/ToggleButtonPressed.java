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
import javafx.scene.control.ToggleButton;

/**
 * A user interaction for toggle buttons.
 * @author Arnaud BLOUIN
 */
public class ToggleButtonPressed extends JfxInteraction<WidgetData<ToggleButton>, ToggleButtonPressedFSM> {
	private final ToggleButtonPressedFSM.ToggleButtonPressedFSMHandler handler;

	/**
	 * Creates the interaction.
	 */
	public ToggleButtonPressed() {
		super(new ToggleButtonPressedFSM());

		handler = new ToggleButtonPressedFSM.ToggleButtonPressedFSMHandler() {
			@Override
			public void initToPressedHandler(final ActionEvent event) {
				if(event.getSource() instanceof ToggleButton) {
					((WidgetDataImpl<ToggleButton>) data).setWidget((ToggleButton) event.getSource());
				}
			}

			@Override
			public void reinitData() {
				ToggleButtonPressed.this.reinitData();
			}
		};

		fsm.buildFSM(handler);
	}

	@Override
	protected void onNewNodeRegistered(final Node node) {
		if(node instanceof ToggleButton) {
			registerActionHandler(node);
		}
	}

	@Override
	public WidgetData<ToggleButton> getData() {
		return super.getData();
	}

	@Override
	protected WidgetDataImpl<ToggleButton> createDataObject() {
		return new WidgetDataImpl<>();
	}

	@Override
	protected void onNodeUnregistered(final Node node) {
		if(node instanceof ToggleButton) {
			unregisterActionHandler(node);
		}
	}
}
