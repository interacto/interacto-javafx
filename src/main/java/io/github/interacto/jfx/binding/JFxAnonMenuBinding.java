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

import io.github.interacto.command.CommandImpl;
import io.github.interacto.jfx.interaction.library.MenuItemInteraction;
import io.github.interacto.jfx.interaction.library.WidgetData;
import java.util.List;
import java.util.Objects;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import javafx.collections.ObservableList;
import javafx.scene.control.MenuItem;

/**
 * This anonymous widget binding for menus takes a function as a parameter that will be executed to initialise the command.
 * The goal is to avoid the creation of a specific class when the widget binding is quite simple.
 * @author Arnaud Blouin
 */
public class JFxAnonMenuBinding<C extends CommandImpl, I extends MenuItemInteraction<WidgetData<MenuItem>, ?, MenuItem>>
			extends JfxMenuItemBinding<C, I> {
	private final BiConsumer<WidgetData<MenuItem>, C> execInitCmd;
	private final Predicate<WidgetData<MenuItem>> checkInteraction;
	private final Consumer<WidgetData<MenuItem>> onEnd;

	/**
	 * Creates a menu item binding. This constructor must initialise the interaction. The binding is (de-)activated if the given
	 * instrument is (de-)activated.
	 * @param continuousExec Specifies whether the command must be executed on each evolution of the interaction.
	 * @param cmdCreation The function for producing commands.
	 * @param interaction The user interaction to use.
	 * @param menus The menus used by the binding. Cannot be null.
	 * @param initCmdFct The function that initialises the command to execute. Cannot be null.
	 * @throws IllegalArgumentException If the given interaction or instrument is null.
	 */
	public JFxAnonMenuBinding(final boolean continuousExec, final I interaction, final Function<WidgetData<MenuItem>, C> cmdCreation,
							final BiConsumer<WidgetData<MenuItem>, C> initCmdFct, final Predicate<WidgetData<MenuItem>> check,
							final Consumer<WidgetData<MenuItem>> onEndFct,
							final List<MenuItem> menus, final List<ObservableList<? extends MenuItem>> additionalMenus) {
		super(continuousExec, interaction, cmdCreation, menus);
		execInitCmd = initCmdFct == null ? (c, i) -> { } : initCmdFct;
		checkInteraction = check == null ? i -> true : check;
		onEnd = onEndFct;

		if(additionalMenus != null) {
			additionalMenus.stream().filter(Objects::nonNull).forEach(elt -> interaction.registerToObservableMenuList(elt));
		}
	}

	@Override
	public void first() {
		execInitCmd.accept(getInteraction(), getCommand());
	}

	@Override
	public void end() {
		if(onEnd != null) {
			onEnd.accept(getInteraction().getData());
		}
	}

	@Override
	public boolean when() {
		return checkInteraction == null || checkInteraction.test(getInteraction());
	}

	@Override
	protected C createCommand() {
		final C currentCmd;

		if(cmdProducer == null) {
			currentCmd = super.createCommand();
		}else {
			currentCmd = cmdProducer.apply(getInteraction());
		}

		return currentCmd;
	}

	@Override
	public String toString() {
		return "JFxAnonMenuBinding {" + interaction + " -> " + cmdProducer + '}';
	}
}
