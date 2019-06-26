/*
 * Interacto
 * Copyright (C) 2019 Arnaud Blouin
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
package io.interacto.jfx.interaction.library;

import io.interacto.jfx.interaction.JfxInteraction;
import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.control.ToggleButton;

/**
 * A user interaction for toggle buttons.
 * @author Arnaud BLOUIN
 */
public class ToggleButtonPressed extends JfxInteraction<WidgetData<ToggleButton>, ToggleButtonPressedFSM, ToggleButton> {
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
					widget = (ToggleButton) event.getSource();
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
		return this;
	}

	@Override
	protected void onNodeUnregistered(final Node node) {
		if(node instanceof ToggleButton) {
			unregisterActionHandler(node);
		}
	}
}
