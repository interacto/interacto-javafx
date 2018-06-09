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

import java.util.List;
import java.util.Objects;
import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.function.Predicate;
import javafx.collections.ObservableList;
import javafx.scene.control.MenuItem;
import org.malai.command.CommandImpl;
import org.malai.javafx.instrument.JfxInstrument;
import org.malai.javafx.interaction.library.MenuItemInteraction;
import org.malai.javafx.interaction.library.WidgetData;

/**
 * This anonymous widget binding for menus takes a function as a parameter that will be executed to initialise the command.
 * The goal is to avoid the creation of a specific class when the widget binding is quite simple.
 * @author Arnaud Blouin
 */
public class JFxAnonMenuBinding<C extends CommandImpl, I extends MenuItemInteraction<WidgetData<MenuItem>, ?, MenuItem>, N extends JfxInstrument>
			extends JfxMenuItemBinding<C, I, N> {
	private final BiConsumer<WidgetData<MenuItem>, C> execInitCmd;
	private final Predicate<WidgetData<MenuItem>> checkInteraction;
	private final BiConsumer<WidgetData<MenuItem>, C> onEnd;
	/** Used rather than 'command' to catch the command during its creation.
	 * Sometimes (eg onInteractionStops) can create the command, execute it, and forget it. */
	protected C currentCmd;

	/**
	 * Creates a menu item binding. This constructor must initialise the interaction. The binding is (de-)activated if the given
	 * instrument is (de-)activated.
	 * @param ins The instrument that contains the binding.
	 * @param exec Specifies if the command must be execute or update on each evolution of the interaction.
	 * @param cmdCreation The function for producing commands.
	 * @param interaction The user interaction to use.
	 * @param menus The menus used by the binding. Cannot be null.
	 * @param initCmdFct The function that initialises the command to execute. Cannot be null.
	 * @throws IllegalArgumentException If the given interaction or instrument is null.
	 */
	public JFxAnonMenuBinding(final N ins, final boolean exec, final I interaction, final Function<WidgetData<MenuItem>, C> cmdCreation,
							  final BiConsumer<WidgetData<MenuItem>, C> initCmdFct, final Predicate<WidgetData<MenuItem>> check,
							  final BiConsumer<WidgetData<MenuItem>, C> onEndFct,
							  final List<MenuItem> menus, final List<ObservableList<? extends MenuItem>> additionalMenus) {
		super(ins, exec, interaction, cmdCreation, menus);
		execInitCmd = initCmdFct == null ? (c, i) -> {} : initCmdFct;
		checkInteraction = check == null ? i -> true : check;
		onEnd = onEndFct;
		currentCmd = null;

		if(additionalMenus != null) {
			additionalMenus.stream().filter(Objects::nonNull).forEach(elt -> interaction.registerToObservableMenuList(elt));
		}
	}

	@Override
	public void first() {
		execInitCmd.accept(getInteraction(), getCommand());
	}

	@Override
	public boolean when() {
		return checkInteraction == null || checkInteraction.test(getInteraction());
	}

	@Override
	protected C map() {
		if(cmdProducer == null) {
			currentCmd = super.map();
		}else {
			currentCmd = cmdProducer.apply(getInteraction());
		}
		return currentCmd;
	}


	@Override
	public void fsmStops() {
		super.fsmStops();
		if(onEnd != null) {
			onEnd.accept(getInteraction(), currentCmd);
		}
		currentCmd = null;
	}

	@Override
	public void fsmCancels() {
		super.fsmCancels();
		currentCmd = null;
	}

	@Override
	public String toString() {
		return "JFxAnonMenuBinding in " + instrument + '{' + interaction + " -> " + cmdProducer + '}';
	}
}
