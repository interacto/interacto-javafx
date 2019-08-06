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
package io.github.interacto.jfx.binding;

import io.github.interacto.jfx.command.ShowStage;
import io.github.interacto.jfx.instrument.JfxInstrument;
import io.github.interacto.jfx.interaction.library.MenuItemPressed;
import java.util.Collections;
import javafx.scene.control.MenuItem;
import javafx.stage.Stage;

/**
 * An widget binding that opens a URL using a menu item.
 * @author Arnaud BLOUIN
 */
public class MenuItem2ShowStage extends JfxMenuItemBinding<ShowStage, MenuItemPressed, JfxInstrument> {
	protected Stage stageToShow;

	protected boolean show;

	/**
	 * Creates the widget binding.
	 * @param ins The instrument that will contain the widget binding.
	 * @param menuItem he menu item that will be uses to create the command.
	 * @param stage The stage to show or hide
	 * @throws IllegalArgumentException If one of the given parameters is null.
	 * @since 2.0
	 */
	public MenuItem2ShowStage(final JfxInstrument ins, final MenuItem menuItem, final Stage stage, final boolean toshow) {
		super(ins, false, new MenuItemPressed(), i -> new ShowStage(), Collections.singletonList(menuItem));

		if(stage == null) {
			throw new IllegalArgumentException();
		}

		stageToShow = stage;
		show = toshow;
	}

	@Override
	public void first() {
		cmd.setWidget(stageToShow);
		cmd.setVisible(show);
	}
}
