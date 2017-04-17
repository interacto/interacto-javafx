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
package org.malai.javafx.instrument.library;

import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Function;
import javafx.scene.Node;
import javafx.scene.input.KeyCode;
import org.malai.action.ActionImpl;
import org.malai.javafx.instrument.JFxAnonInteractor;
import org.malai.javafx.instrument.JfxInstrument;
import org.malai.javafx.interaction.library.KeysPressure;

/**
 *	An interactor using a key shortcut.
 * @param <A> The action to produce.
 * @param <I> The instrument.
 */
public class KeysShortcutInteractor <A extends ActionImpl, I extends JfxInstrument> extends JFxAnonInteractor<A, KeysPressure, I> {
	private final Function<KeysPressure, Boolean> canDo;
	private final List<KeyCode> keyCodes;

	/**
	 * Creates an interactor. This constructor must initialise the interaction. The interactor is (de-)activated if the given
	 * instrument is (de-)activated.
	 * @param ins The instrument that contains the interactor.
	 * @param clazzAction The type of the action that will be created. Used to instantiate the action by reflexivity.
	 * The class must be public and must have a constructor with no parameter.
	 * The class must be public and must have a constructor with no parameter.
	 * @param widgets The widgets used by the interactor. Cannot be null.
	 * @param codes The key shortcut. Cannot be null.
	 * @param execute The command to execute when the shortcut is strictly respected.
	 * @throws IllegalAccessException If no free-parameter constructor is available.
	 * @throws InstantiationException If an error occurs during instantiation of the interaction/action.
	 * @throws IllegalArgumentException If the given interaction or instrument is null.
	 * @throws NullPointerException If the given lambda or the key codes list are null.
	 */
	public KeysShortcutInteractor(I ins, Class<A> clazzAction, final Consumer<A> execute, List<KeyCode> codes, Node... widgets) throws InstantiationException, IllegalAccessException {
		this(ins, clazzAction, execute, null, codes, widgets);
	}

	/**
	 * Creates an interactor. This constructor must initialise the interaction. The interactor is (de-)activated if the given
	 * instrument is (de-)activated.
	 * @param ins The instrument that contains the interactor.
	 * @param clazzAction The type of the action that will be created. Used to instantiate the action by reflexivity.
	 * The class must be public and must have a constructor with no parameter.
	 * The class must be public and must have a constructor with no parameter.
	 * @param widgets The widgets used by the interactor. Cannot be null.
	 * @param codes The key shortcut. Cannot be null.
	 * @param execute The command to execute when the shortcut is strictly respected.
	 * @param executable The command that checks whether the action can be executed in complement of the key shortcut. Can be null.
	 * @throws IllegalAccessException If no free-parameter constructor is available.
	 * @throws InstantiationException If an error occurs during instantiation of the interaction/action.
	 * @throws IllegalArgumentException If the given interaction or instrument is null.
	 * @throws NullPointerException If the given lambda or the key codes list are null.
	 */
	public KeysShortcutInteractor(I ins, Class<A> clazzAction, final Consumer<A> execute, Function<KeysPressure, Boolean> executable,
								  List<KeyCode> codes, Node... widgets) throws InstantiationException, IllegalAccessException {
		super(ins, false, clazzAction, KeysPressure.class, execute, widgets);
		keyCodes = Objects.requireNonNull(codes);
		canDo = executable;
	}

	@Override
	public boolean isConditionRespected() {
		final List<KeyCode> keys = getInteraction().getKeyCodes();
		return keyCodes.size()==keys.size() && keyCodes.stream().allMatch(code -> keys.contains(code)) && (canDo==null || canDo.apply(getInteraction()));
	}
}