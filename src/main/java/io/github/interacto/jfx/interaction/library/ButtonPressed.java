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
import javafx.scene.control.Button;

/**
 * A user interaction for buttons.
 * @author Arnaud BLOUIN
 */
public class ButtonPressed extends JfxInteraction<WidgetData<Button>, ButtonPressedFSM> {
	private final ButtonPressedFSM.ButtonPressedFSMHandler handler;

	/**
	 * Creates the interaction.
	 */
	public ButtonPressed() {
		super(new ButtonPressedFSM());

		handler = new ButtonPressedFSM.ButtonPressedFSMHandler() {
			@Override
			public void initToPressedHandler(final ActionEvent event) {
				if(event.getSource() instanceof Button) {
					((WidgetDataImpl<Button>) data).setWidget((Button) event.getSource());
				}
			}

			@Override
			public void reinitData() {
				ButtonPressed.this.reinitData();
			}
		};

		fsm.buildFSM(handler);
	}

	@Override
	protected void onNewNodeRegistered(final Node node) {
		if(node instanceof Button) {
			registerActionHandler(node);
		}
	}

	@Override
	protected WidgetDataImpl<Button> createDataObject() {
		return new WidgetDataImpl<>();
	}


	@Override
	protected void onNodeUnregistered(final Node node) {
		if(node instanceof Button) {
			unregisterActionHandler(node);
		}
	}
}
