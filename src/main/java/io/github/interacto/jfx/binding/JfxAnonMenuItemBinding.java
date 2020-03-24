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
package io.github.interacto.jfx.binding;

import io.github.interacto.command.Command;
import io.github.interacto.jfx.binding.api.LogLevel;
import io.github.interacto.jfx.interaction.help.HelpAnimation;
import io.github.interacto.jfx.interaction.library.MenuItemInteraction;
import io.github.interacto.jfx.interaction.library.WidgetData;
import java.util.List;
import java.util.Objects;
import java.util.Set;
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
class JfxAnonMenuItemBinding<C extends Command, I extends MenuItemInteraction<WidgetData<MenuItem>, ?>> extends JfxAnonBinding<C, I, WidgetData<MenuItem>> {
	/**
	 * Creates a menu item binding. This constructor must initialise the interaction. The binding is (de-)activated if the given
	 * instrument is (de-)activated.
	 */
	JfxAnonMenuItemBinding(final boolean continuousExec, final I interaction, final BiConsumer<WidgetData<MenuItem>, C> initCmdFct, final BiConsumer<WidgetData<MenuItem>, C> updateCmdFct,
			final Predicate<WidgetData<MenuItem>> check, final BiConsumer<WidgetData<MenuItem>, C> onEndFct, final Function<WidgetData<MenuItem>, C> cmdFunction, final Consumer<WidgetData<MenuItem>> cancel,
			final Consumer<WidgetData<MenuItem>> endOrCancel, final List<MenuItem> widgets, final List<ObservableList<? extends MenuItem>> additionalWidgets,
			final boolean asyncExec, final boolean strict, final long timeoutThrottle, final Set<LogLevel> loggers,
			final boolean help, final HelpAnimation animation, final BiConsumer<WidgetData<MenuItem>, C> hadNoEffectFct, final BiConsumer<WidgetData<MenuItem>, C> hadEffectsFct,
			final BiConsumer<WidgetData<MenuItem>, C> cannotExecFct, final boolean consumeEvents) {
		super(continuousExec, interaction, initCmdFct, updateCmdFct, check, onEndFct, cmdFunction, cancel, endOrCancel, asyncExec, strict, timeoutThrottle,
			loggers, help, animation, hadNoEffectFct, hadEffectsFct, cannotExecFct, consumeEvents);

		interaction.registerToMenuItems(widgets);

		if(additionalWidgets != null) {
			additionalWidgets.stream().filter(Objects::nonNull).forEach(elt -> interaction.registerToObservableMenuList(elt));
		}
	}
}

