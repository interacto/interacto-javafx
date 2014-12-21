/*
 * This file is part of Malai.<br>
 * Copyright (c) 2005-2014 Arnaud BLOUIN<br>
 * <br>
 * Malai is free software; you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation; either version 2 of the License, or (at your option) any later version.
 * <br>
 * Malai is distributed without any warranty; without even the implied
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * General Public License for more details.<br> 
 */
package org.malai.javafx.instrument.library;

import java.util.List;

import javafx.scene.Node;

import org.malai.action.Action;
import org.malai.javafx.instrument.JfxInstrument;
import org.malai.javafx.instrument.JfxInteractor;
import org.malai.javafx.interaction.library.BoxChecked;

/**
 *	An interactor using a JFX checkbox interaction.
 * @param <A> The action to produce.
 * @param <I> The instrument.
 */
public abstract class CheckboxInteractor<A extends Action, I extends JfxInstrument> extends JfxInteractor<A, BoxChecked, I> {
	/**
	 * Creates an interactor.
	 * @param ins The instrument that contains the interactor.
	 * @param clazzAction The type of the action that will be created. Used to instantiate the action by reflexivity.
	 * The class must be public and must have a constructor with no parameter.
	 * @param widgets The widgets used by the interactor. Cannot be null.
	 * @throws IllegalAccessException If no free-parameter constructor is available.
	 * @throws InstantiationException If an error occurs during instantiation of the interaction/action.
	 * @throws IllegalArgumentException If the given interaction or instrument is null.
	 */
	public CheckboxInteractor(I ins, Class<A> clazzAction, List<Node> widgets) throws InstantiationException, IllegalAccessException {
		super(ins, false, clazzAction, BoxChecked.class, widgets);
	}
}
