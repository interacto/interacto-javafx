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
import javafx.scene.control.CheckBox;

/**
 * A user interaction for checkboxes.
 * @author Arnaud BLOUIN
 */
public class BoxChecked extends JfxInteraction<WidgetData<CheckBox>, BoxCheckedFSM> {
	private final BoxCheckedFSM.BoxCheckedFSMHandler handler;

	/**
	 * Creates the interaction.
	 */
	public BoxChecked() {
		super(new BoxCheckedFSM());

		handler = new BoxCheckedFSM.BoxCheckedFSMHandler() {
			@Override
			public void initToCheckedHandler(final ActionEvent event) {
				if(event.getSource() instanceof CheckBox) {
					((WidgetDataImpl<CheckBox>) data).setWidget((CheckBox) event.getSource());
				}
			}

			@Override
			public void reinitData() {
				BoxChecked.this.reinitData();
			}
		};

		fsm.buildFSM(handler);
	}

	@Override
	protected void onNewNodeRegistered(final Node node) {
		if(node instanceof CheckBox) {
			registerActionHandler(node);
		}
	}

	@Override
	protected WidgetData<CheckBox> createDataObject() {
		return new WidgetDataImpl<>();
	}

	@Override
	protected void onNodeUnregistered(final Node node) {
		if(node instanceof CheckBox) {
			unregisterActionHandler(node);
		}
	}
}
