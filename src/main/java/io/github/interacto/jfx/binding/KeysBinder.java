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

import io.github.interacto.command.Command;
import io.github.interacto.jfx.binding.api.KeyInteractionBinder;
import io.github.interacto.jfx.binding.api.KeyInteractionCmdBinder;
import io.github.interacto.jfx.instrument.JfxInstrument;
import io.github.interacto.jfx.interaction.help.HelpAnimation;
import io.github.interacto.jfx.interaction.library.KeysData;
import io.github.interacto.jfx.interaction.library.KeysPressed;
import io.github.interacto.logging.LogLevel;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.BooleanSupplier;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.ObservableList;
import javafx.scene.control.Button;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;

/**
 * The base binding builder to create bindings between a keys pressure interaction and a given command.
 * @param <C> The type of the command to produce.
 * @param <W> The type of the widget to bind.
 * @author Arnaud Blouin
 */
abstract class KeysBinder<W, C extends Command> extends Binder<W, C, KeysPressed, KeysData> implements
				KeyInteractionBinder<W, KeysPressed, KeysData>, KeyInteractionCmdBinder<W, C, KeysPressed, KeysData> {
	final Collection<KeyCode> codes;
	final Predicate<KeysData> checkCode;

	KeysBinder(final JfxInstrument instrument) {
		super(instrument);
		interactionSupplier = KeysPressed::new;
		codes = new ArrayList<>();

		checkCode = i -> {
			final List<KeyCode> keys = i.getKeyCodes();
			return (codes.isEmpty() || codes.size() == keys.size() && keys.containsAll(codes)) && (checkConditions == null || checkConditions.test(i));
		};
	}

	@Override
	public KeysBinder<W, C> with(final KeyCode... keyCodes) {
		this.codes.addAll(Arrays.asList(keyCodes));
		return this;
	}

	@Override
	public KeysBinder<W, C> on(final W... widget) {
		return (KeysBinder<W, C>) super.on(widget);
	}

	@Override
	public KeysBinder<W, C> on(final ObservableList<? extends W> widgets) {
		return (KeysBinder<W, C>) super.on(widgets);
	}

	@Override
	public KeysBinder<W, C> first(final Consumer<C> initCmdFct) {
		return (KeysBinder<W, C>) super.first(initCmdFct);
	}

	@Override
	public KeysBinder<W, C> first(final BiConsumer<KeysData, C> initCmdFct) {
		return (KeysBinder<W, C>) super.first(initCmdFct);
	}

	@Override
	public KeysBinder<W, C> when(final Predicate<KeysData> checkCmd) {
		return (KeysBinder<W, C>) super.when(checkCmd);
	}

	@Override
	public KeysBinder<W, C> when(final BooleanSupplier checkCmd) {
		return (KeysBinder<W, C>) super.when(checkCmd);
	}

	@Override
	public KeysBinder<W, C> async(final Button cancel, final DoubleProperty progressProp, final StringProperty msgProp) {
		return (KeysBinder<W, C>) super.async(cancel, progressProp, msgProp);
	}

	@Override
	public KeysBinder<W, C> end(final Consumer<KeysData> onEndFct) {
		return (KeysBinder<W, C>) super.end(onEndFct);
	}

	@Override
	public KeysBinder<W, C> log(final LogLevel level) {
		return (KeysBinder<W, C>) super.log(level);
	}

	@Override
	public KeysBinder<W, C> help(final HelpAnimation animation) {
		return (KeysBinder<W, C>) super.help(animation);
	}

	@Override
	public KeysBinder<W, C> help(final Pane helpPane) {
		return (KeysBinder<W, C>) super.help(helpPane);
	}

	@Override
	public <C2 extends Command> KeysBinder<W, C2> toProduce(final Supplier<C2> cmdCreation) {
		return (KeysBinder<W, C2>) super.toProduce(cmdCreation);
	}

	@Override
	public <C2 extends Command> KeysBinder<W, C2> toProduce(final Function<KeysData, C2> cmdCreation) {
		return (KeysBinder<W, C2>) super.toProduce(cmdCreation);
	}
}