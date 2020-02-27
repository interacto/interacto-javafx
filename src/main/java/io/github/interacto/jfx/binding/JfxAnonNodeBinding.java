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
import io.github.interacto.interaction.InteractionData;
import io.github.interacto.jfx.binding.api.LogLevel;
import io.github.interacto.jfx.interaction.JfxInteraction;
import io.github.interacto.jfx.interaction.help.HelpAnimation;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import javafx.collections.ObservableList;
import javafx.scene.Node;

/**
 * This anonymous widget binding takes a function as a parameter that will be executed to initialise the command.
 * The goal is to avoid the creation of a specific class when the binding is quite simple.
 * @author Arnaud Blouin
 */
class JfxAnonNodeBinding<C extends Command, I extends JfxInteraction<D, ?, ?>, D extends InteractionData> extends JfxAnonBinding<C, I, D> {
	/**
	 * Creates a widget binding. This constructor must initialise the interaction. The binding is (de-)activated if the given
	 * instrument is (de-)activated.
	 * @param continuousExec Specifies whether the command must be executed on each evolution of the interaction.
	 * @param interaction The user interaction of the binding.
	 * @param initCmdFct The function that initialises the command to execute. Cannot be null.
	 * @param updateCmdFct The function that updates the command. Can be null.
	 * @param cmdFunction The function that produces the command.
	 * @param widgets The widgets used by the binding. Cannot be null.
	 * @throws IllegalArgumentException If the given interaction or instrument is null.
	 */
	JfxAnonNodeBinding(final boolean continuousExec, final I interaction, final BiConsumer<D, C> initCmdFct, final BiConsumer<D, C> updateCmdFct,
						final Predicate<D> check, final BiConsumer<D, C> onEndFct, final Function<D, C> cmdFunction, final Consumer<D> cancel,
						final Consumer<D> endOrCancel, final List<Node> widgets, final List<ObservableList<? extends Node>> additionalWidgets,
						final boolean asyncExec, final boolean strict, final long timeoutThrottle, final Set<LogLevel> loggers,
						final boolean help, final HelpAnimation animation, final BiConsumer<D, C> hadNoEffectFct, final BiConsumer<D, C> hadEffectsFct,
						final BiConsumer<D, C> cannotExecFct, final boolean consumeEvents) {
		super(continuousExec, interaction, initCmdFct, updateCmdFct, check, onEndFct, cmdFunction, cancel,
			endOrCancel, asyncExec, strict, timeoutThrottle, loggers, help, animation, hadNoEffectFct, hadEffectsFct, cannotExecFct, consumeEvents);

		interaction.registerToNodes(widgets);

		if(additionalWidgets != null) {
			additionalWidgets.stream().filter(Objects::nonNull).forEach(elt -> interaction.registerToObservableNodeList(elt));
		}
	}
}
