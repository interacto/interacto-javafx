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
import io.github.interacto.jfx.instrument.JfxInstrument;
import io.github.interacto.jfx.interaction.help.HelpAnimation;
import io.github.interacto.jfx.interaction.library.MenuItemPressed;
import io.github.interacto.jfx.interaction.library.WidgetData;
import java.util.Collections;
import java.util.EnumSet;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.ObservableList;
import javafx.scene.control.Button;
import javafx.scene.control.MenuItem;

/**
 * The binding builder to create bindings between a menu item interaction and a given command.
 * @param <C> The type of the command to produce.
 * @author Arnaud Blouin
 */
class MenuItemBinder<C extends Command> extends Binder<MenuItem, C, MenuItemPressed, WidgetData<MenuItem>> {
	MenuItemBinder(final JfxInstrument instrument, final BindingsObserver observer) {
		super(instrument, observer);
		interactionSupplier = MenuItemPressed::new;
	}

	MenuItemBinder(final BiConsumer<WidgetData<MenuItem>, C> initCmd, final Predicate<WidgetData<MenuItem>> checkConditions,
		final Function<WidgetData<MenuItem>, C> cmdProducer, final List<MenuItem> widgets, final Supplier<MenuItemPressed> interactionSupplier,
		final JfxInstrument instrument, final boolean async, final BiConsumer<WidgetData<MenuItem>, C> onEnd,
		final List<ObservableList<? extends MenuItem>> additionalWidgets, final EnumSet<LogLevel> logLevels, final HelpAnimation helpAnimation,
		final boolean withHelp, final DoubleProperty progressProp, final StringProperty msgProp, final Button cancel,
		final BiConsumer<WidgetData<MenuItem>, C> hadNoEffectFct, final BiConsumer<WidgetData<MenuItem>, C> hadEffectsFct,
		final BiConsumer<WidgetData<MenuItem>, C> cannotExecFct, final BindingsObserver observer, final boolean consumeEvents) {
		super(initCmd, checkConditions, cmdProducer, widgets, interactionSupplier, instrument, async, onEnd, additionalWidgets, logLevels, helpAnimation,
			withHelp, progressProp, msgProp, cancel, hadNoEffectFct, hadEffectsFct, cannotExecFct, observer, consumeEvents);
	}

	@Override
	protected MenuItemBinder<C> duplicate() {
		return new MenuItemBinder<>(initCmd, checkConditions, cmdProducer, widgets, interactionSupplier, instrument, async,
			onEnd, additionalWidgets, logLevels, helpAnimation, withHelp, progressProp, msgProp, cancel, hadNoEffectFct, hadEffectsFct,
			cannotExecFct, observer, consumeEvents);
	}

	@Override
	public JfxWidgetBinding<C, MenuItemPressed, WidgetData<MenuItem>> bind() {
		final JfxAnonMenuItemBinding<C, MenuItemPressed> binding = new JfxAnonMenuItemBinding<>(false, interactionSupplier.get(),
			initCmd, null, checkConditions, onEnd, cmdProducer, null, null, widgets, additionalWidgets,
			false, false, 0L, Collections.emptySet(), false, null, hadNoEffectFct, hadEffectsFct,
			cannotExecFct, consumeEvents);
		binding.setProgressBarProp(progressProp);
		binding.setProgressMsgProp(msgProp);
		binding.setCancelCmdButton(cancel);
		if(instrument != null) {
			instrument.addBinding(binding);
		}
		if(observer != null) {
			observer.observeBinding(binding);
		}
		return binding;
	}
}
