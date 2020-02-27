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
import io.github.interacto.jfx.interaction.JfxInteraction;
import io.github.interacto.jfx.interaction.help.HelpAnimation;
import io.github.interacto.logging.LogLevel;
import java.util.List;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import javafx.stage.Window;

/**
 * @author Arnaud Blouin
 */
class JfxAnonWindowBinding<C extends Command, I extends JfxInteraction<D, ?, ?>, D extends InteractionData> extends JfxAnonBinding<C, I, D> {
	JfxAnonWindowBinding(final boolean continuousExec, final I interaction, final BiConsumer<D, C> initCmdFct, final BiConsumer<D, C> updateCmdFct,
						final Predicate<D> check, final BiConsumer<D, C> onEndFct, final Function<D, C> cmdFunction, final Consumer<D> cancel,
						final Consumer<D> endOrCancel, final List<Window> widgets,
						final boolean asyncExec, final boolean strict, final long timeoutThrottle, final Set<LogLevel> loggers,
						final boolean help, final HelpAnimation animation, final BiConsumer<D, C> hadNoEffectFct, final BiConsumer<D, C> hadEffectsFct,
						final BiConsumer<D, C> cannotExecFct, final boolean consumeEvents) {
		super(continuousExec, interaction, initCmdFct, updateCmdFct, check, onEndFct, cmdFunction, cancel,
			endOrCancel, asyncExec, strict, timeoutThrottle, loggers, help, animation, hadNoEffectFct, hadEffectsFct, cannotExecFct, consumeEvents);

		interaction.registerToWindows(widgets);
	}
}
