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
import io.github.interacto.jfx.interaction.library.MenuItemPressed;
import java.util.Collections;
import java.util.function.Supplier;
import javafx.scene.control.MenuItem;
import javafx.stage.Stage;

/**
 * A widget binding that opens a URL using a menu item.
 * @author Arnaud BLOUIN
 */
public class MenuItem2ShowLazyStage extends JfxMenuItemBinding<ShowStage, MenuItemPressed> {
	protected Supplier<Stage> stageToShowLazy;

	protected boolean show;

	/**
	 * Creates the binding.
	 * @param menuItem he menu item that will be uses to create the command.
	 * @param stageLazy The stage to show or hide (the creation of the stage can be postponed at the execution of the command).
	 * @throws IllegalArgumentException If one of the given parameters is null.
	 */
	public MenuItem2ShowLazyStage(final MenuItem menuItem, final Supplier<Stage> stageLazy, final boolean toshow) {
		super(false, new MenuItemPressed(), i -> new ShowStage(), Collections.singletonList(menuItem));

		if(stageLazy == null) {
			throw new IllegalArgumentException();
		}

		stageToShowLazy = stageLazy;
		show = toshow;
	}

	@Override
	public void first() {
		cmd.setWidget(stageToShowLazy.get());
		cmd.setVisible(show);
	}
}
