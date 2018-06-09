/*
 * This file is part of Malai.
 * Copyright (c) 2009-2018 Arnaud BLOUIN
 * Malai is free software; you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation; either version 2 of the License, or (at your option) any later version.
 * Malai is distributed without any warranty; without even the implied
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * General Public License for more details.
 */
package org.malai.javafx.binding;

import java.util.Collections;
import java.util.function.Supplier;
import javafx.scene.control.MenuItem;
import javafx.stage.Stage;
import org.malai.javafx.command.ShowStage;
import org.malai.javafx.instrument.JfxInstrument;
import org.malai.javafx.interaction.library.MenuItemPressed;

/**
 * A widget binding that opens a URL using a menu item.
 * @author Arnaud BLOUIN
 */
public class MenuItem2ShowLazyStage extends JfxMenuItemBinding<ShowStage, MenuItemPressed, JfxInstrument> {
	protected Supplier<Stage> stageToShowLazy;

	protected boolean show;

	/**
	 * Creates the binding.
	 * @param ins The instrument that will contain the binding.
	 * @param menuItem he menu item that will be uses to create the command.
	 * @param stageLazy The stage to show or hide (the creation of the stage can be postponed at the execution of the command).
	 * @throws IllegalArgumentException If one of the given parameters is null.
	 * @since 2.0
	 */
	public MenuItem2ShowLazyStage(final JfxInstrument ins, final MenuItem menuItem, final Supplier<Stage> stageLazy, final boolean toshow) {
		super(ins, false, new MenuItemPressed(), i -> new ShowStage(), Collections.singletonList(menuItem));

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
