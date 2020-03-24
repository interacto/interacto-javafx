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
import javafx.beans.value.ChangeListener;
import javafx.scene.Node;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;

/**
 * A user interaction for tabs.
 * @author Arnaud BLOUIN
 */
public class TabSelected extends JfxInteraction<WidgetData<TabPane>, TabSelectedFSM> {
	private final ChangeListener<Tab> event;
	private final TabSelectedFSM.TabSelectedFSMHandler handler;

	/**
	 * Creates the interaction.
	 */
	public TabSelected() {
		super(new TabSelectedFSM());

		handler = new TabSelectedFSM.TabSelectedFSMHandler() {
			@Override
			public void initToSelectedHandler(final TabEvent event) {
				if(event.getSource() instanceof TabPane) {
					((WidgetDataImpl<TabPane>) data).setWidget((TabPane) event.getSource());
				}
			}

			@Override
			public void reinitData() {
				TabSelected.this.reinitData();
			}
		};

		fsm.buildFSM(handler);
		event = (observable, oldValue, newValue) -> processEvent(new TabEvent(newValue.getTabPane(), null));
	}

	@Override
	protected void onNewNodeRegistered(final Node node) {
		if(node instanceof TabPane) {
			((TabPane) node).getSelectionModel().selectedItemProperty().addListener(event);
		}
	}

	@Override
	protected WidgetDataImpl<TabPane> createDataObject() {
		return new WidgetDataImpl<>();
	}

	@Override
	protected void onNodeUnregistered(final Node node) {
		if(node instanceof TabPane) {
			((TabPane) node).getSelectionModel().selectedItemProperty().removeListener(event);
		}
	}
}
