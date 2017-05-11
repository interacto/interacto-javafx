/*
 * This file is part of Malai.
 * Copyright (c) 2005-2017 Arnaud BLOUIN
 * Malai is free software; you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation; either version 2 of the License, or (at your option) any later version.
 * Malai is distributed without any warranty; without even the implied
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * General Public License for more details.
 */
package org.malai.javafx.binding;

import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Function;
import javafx.scene.Node;
import javafx.scene.input.KeyCode;
import javafx.stage.Window;
import org.malai.action.ActionImpl;
import org.malai.javafx.instrument.JfxInstrument;
import org.malai.javafx.interaction.library.KeysPressure;

/**
 * A widget binding for key shortcuts.
 * @param <A> The action to produce.
 * @param <I> The instrument.
 * @author Arnaud Blouin
 */
public class KeysShortcutBinding<A extends ActionImpl, I extends JfxInstrument> extends JFxAnonBinding<A, KeysPressure, I> {
	private final Function<KeysPressure, Boolean> canDo;
	private final Collection<KeyCode> keyCodes;

	/**
	 * Creates a key shortcut binding. This constructor must initialise the interaction. The binding is (de-)activated if the given
	 * instrument is (de-)activated.
	 * @param ins The instrument that contains the binding.
	 * @param clazzAction The type of the action that will be created. Used to instantiate the action by reflexivity.
	 * The class must be public and must have a constructor with no parameter.
	 * The class must be public and must have a constructor with no parameter.
	 * @param widgets The widgets used by the binding. Cannot be null.
	 * @param codes The key shortcut. Cannot be null.
	 * @param execute The command to execute when the shortcut is strictly respected.
	 * @throws IllegalAccessException If no free-parameter constructor is available.
	 * @throws InstantiationException If an error occurs during instantiation of the interaction/action.
	 * @throws IllegalArgumentException If the given interaction or instrument is null.
	 * @throws NullPointerException If the given lambda or the key codes list are null.
	 */
	public KeysShortcutBinding(final I ins, final Class<A> clazzAction, final Consumer<A> execute, final Collection<KeyCode> codes,
							   final Node... widgets) throws InstantiationException, IllegalAccessException {
		this(ins, clazzAction, execute, null, codes, widgets);
	}

	/**
	 * Creates a key shortcut binding. This constructor must initialise the interaction. The binding is (de-)activated if the given
	 * instrument is (de-)activated.
	 * @param ins The instrument that contains the binding.
	 * @param clazzAction The type of the action that will be created. Used to instantiate the action by reflexivity.
	 * The class must be public and must have a constructor with no parameter.
	 * The class must be public and must have a constructor with no parameter.
	 * @param widgets The widgets used by the binding. Cannot be null.
	 * @param codes The key shortcut. Cannot be null.
	 * @param execute The command to execute when the shortcut is strictly respected.
	 * @param executable The command that checks whether the action can be executed in complement of the key shortcut. Can be null.
	 * @throws IllegalAccessException If no free-parameter constructor is available.
	 * @throws InstantiationException If an error occurs during instantiation of the interaction/action.
	 * @throws IllegalArgumentException If the given interaction or instrument is null.
	 * @throws NullPointerException If the given lambda or the key codes list are null.
	 */
	public KeysShortcutBinding(final I ins, final Class<A> clazzAction, final Consumer<A> execute,
							   final Function<KeysPressure, Boolean> executable, final Collection<KeyCode> codes, final Node... widgets)
				throws InstantiationException, IllegalAccessException {
		super(ins, false, clazzAction, KeysPressure.class, execute, null, widgets);
		keyCodes = Objects.requireNonNull(codes);
		canDo = executable;
	}

	/**
	 * Creates a widget binding. This constructor must initialise the interaction. The binding is (de-)activated if the given
	 * instrument is (de-)activated.
	 * @param ins The instrument that contains the binding.
	 * @param clazzAction The type of the action that will be created. Used to instantiate the action by reflexivity.
	 * The class must be public and must have a constructor with no parameter.
	 * The class must be public and must have a constructor with no parameter.
	 * @param windows The windows used by the binding. Cannot be null.
	 * @param codes The key shortcut. Cannot be null.
	 * @param execute The command to execute when the shortcut is strictly respected.
	 * @throws IllegalAccessException If no free-parameter constructor is available.
	 * @throws InstantiationException If an error occurs during instantiation of the interaction/action.
	 * @throws IllegalArgumentException If the given interaction or instrument is null.
	 * @throws NullPointerException If the given lambda or the key codes list are null.
	 */
	public KeysShortcutBinding(final I ins, final Class<A> clazzAction, final Consumer<A> execute, final Collection<KeyCode> codes,
							   final Window... windows) throws InstantiationException, IllegalAccessException {
		this(ins, clazzAction, execute, null, codes, windows);
	}

	/**
	 * Creates a widget binding. This constructor must initialise the interaction. The binding is (de-)activated if the given
	 * instrument is (de-)activated.
	 * @param ins The instrument that contains the binding.
	 * @param clazzAction The type of the action that will be created. Used to instantiate the action by reflexivity.
	 * The class must be public and must have a constructor with no parameter.
	 * The class must be public and must have a constructor with no parameter.
	 * @param windows The windows used by the binding. Cannot be null.
	 * @param codes The key shortcut. Cannot be null.
	 * @param execute The command to execute when the shortcut is strictly respected.
	 * @param executable The command that checks whether the action can be executed in complement of the key shortcut. Can be null.
	 * @throws IllegalAccessException If no free-parameter constructor is available.
	 * @throws InstantiationException If an error occurs during instantiation of the interaction/action.
	 * @throws IllegalArgumentException If the given interaction or instrument is null.
	 * @throws NullPointerException If the given lambda or the key codes list are null.
	 */
	public KeysShortcutBinding(final I ins, final Class<A> clazzAction, final Consumer<A> execute, final Function<KeysPressure,
		Boolean> executable, final Collection<KeyCode> codes, final Window... windows) throws InstantiationException,
		IllegalAccessException {
		super(ins, false, clazzAction, KeysPressure.class, execute, windows);
		keyCodes = Objects.requireNonNull(codes);
		canDo = executable;
	}

	@Override
	public boolean isConditionRespected() {
		final List<KeyCode> keys = getInteraction().getKeyCodes();
		return keyCodes.size() == keys.size() && keyCodes.stream().allMatch(code -> keys.contains(code)) && (canDo == null || canDo.apply
			(getInteraction()));
	}
}
