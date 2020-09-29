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
import io.github.interacto.jfx.binding.api.KeyInteractionBinder;
import io.github.interacto.jfx.binding.api.KeyInteractionCmdBinder;
import io.github.interacto.jfx.binding.api.LogLevel;
import io.github.interacto.jfx.instrument.JfxInstrument;
import io.github.interacto.jfx.interaction.help.HelpAnimation;
import io.github.interacto.jfx.interaction.library.KeysData;
import io.github.interacto.jfx.interaction.library.KeysPressed;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.EnumSet;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.BooleanSupplier;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Collectors;
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
	Collection<KeyCode> codes;
	final Predicate<KeysData> checkCode;
	boolean shift;
	boolean ctrl;
	boolean alt;
	boolean shortcut;
	boolean meta;


	KeysBinder(final JfxInstrument instrument, final BindingsObserver observer) {
		this(null, null, null, Collections.emptyList(),
			instrument, false, null, Collections.emptyList(), EnumSet.noneOf(LogLevel.class),
			null, false, null, null, null, Collections.emptyList(), null, null, null, observer, false);
	}

	KeysBinder(final BiConsumer<KeysData, C> initCmd, final Predicate<KeysData> whenPredicate, final Function<KeysData, C> cmdProducer,
		final List<W> widgets, final JfxInstrument instrument, final boolean async,
		final BiConsumer<KeysData, C> onEnd, final List<ObservableList<? extends W>> additionalWidgets, final EnumSet<LogLevel> logLevels,
		final HelpAnimation helpAnimation, final boolean withHelp, final DoubleProperty progressProp, final StringProperty msgProp, final Button cancel,
		final Collection<KeyCode> keyCodes, final BiConsumer<KeysData, C> hadNoEffectFct, final BiConsumer<KeysData, C> hadEffectsFct,
		final BiConsumer<KeysData, C> cannotExecFct, final BindingsObserver observer, final boolean consumeEvents) {
		super(initCmd, whenPredicate, cmdProducer, widgets, KeysPressed::new, instrument, async, onEnd, additionalWidgets, logLevels, helpAnimation,
			withHelp, progressProp, msgProp, cancel, hadNoEffectFct, hadEffectsFct, cannotExecFct, observer, consumeEvents);
		setUpKeys(keyCodes);
		checkCode = i -> isCodeChecked(i) && (checkConditions == null || checkConditions.test(i));
	}

	private boolean isCodeChecked(final KeysData data) {
		final List<KeyCode> keys = data.getKeyCodes();
		return (codes.isEmpty() || codes.size() == keys.size() && keys.containsAll(codes)) && isModifiersChecks(data);
	}

	private boolean isModifiersChecks(final KeysData data) {
		return meta == data.isMetaDown()
			&& alt == data.isAltDown()
			&& (!shortcut || data.isShortcutDown())
			&& shift == data.isShiftDown()
			&& ctrl == data.isCtrlDown();
	}

	private void setUpKeys(final Collection<KeyCode> keys) {
		final var isMac = System.getProperty("os.name").startsWith("Mac");

		codes = keys
			.stream()
			.filter(key -> !key.isModifierKey() && key != KeyCode.SHORTCUT)
			.collect(Collectors.toUnmodifiableSet());
		shortcut = keys.contains(KeyCode.SHORTCUT);
		meta = keys.contains(KeyCode.META) || keys.contains(KeyCode.COMMAND) || (shortcut && isMac);
		shift = keys.contains(KeyCode.SHIFT);
		ctrl = keys.contains(KeyCode.CONTROL) || (shortcut && !isMac);
		alt = keys.contains(KeyCode.ALT);
	}

	@Override
	protected abstract KeysBinder<W, C> duplicate();

	@Override
	public KeysBinder<W, C> with(final KeyCode... keyCodes) {
		final KeysBinder<W, C> dup = duplicate();
		dup.setUpKeys(Arrays.asList(keyCodes));
		return dup;
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
		return when(i -> checkCmd.getAsBoolean());
	}

	@Override
	public KeysBinder<W, C> consume() {
		return (KeysBinder<W, C>) super.consume();
	}

	@Override
	public KeysBinder<W, C> async(final Button cancel, final DoubleProperty progressProp, final StringProperty msgProp) {
		return (KeysBinder<W, C>) super.async(cancel, progressProp, msgProp);
	}

	@Override
	public KeysBinder<W, C> ifHadEffects(final BiConsumer<KeysData, C> hadEffectsFct) {
		return (KeysBinder<W, C>) super.ifHadEffects(hadEffectsFct);
	}

	@Override
	public KeysBinder<W, C> ifHadNoEffect(final BiConsumer<KeysData, C> noEffectFct) {
		return (KeysBinder<W, C>) super.ifHadNoEffect(noEffectFct);
	}

	@Override
	public KeysBinder<W, C> end(final Consumer<C> onEnd) {
		return (KeysBinder<W, C>) super.end(onEnd);
	}

	@Override
	public KeysBinder<W, C> end(final Runnable endFct) {
		return (KeysBinder<W, C>) super.end(endFct);
	}

	@Override
	public KeysBinder<W, C> end(final BiConsumer<KeysData, C> onEndFct) {
		return (KeysBinder<W, C>) super.end(onEndFct);
	}

	@Override
	public KeysBinder<W, C> log(final LogLevel... level) {
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
