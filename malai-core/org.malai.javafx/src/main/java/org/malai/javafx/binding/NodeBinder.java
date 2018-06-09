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

import java.util.function.Function;
import java.util.function.Supplier;
import javafx.scene.Node;
import org.malai.command.CommandImpl;
import org.malai.interaction.InteractionData;
import org.malai.javafx.instrument.JfxInstrument;
import org.malai.javafx.interaction.JfxInteraction;

/**
 * The binding builder to create bindings between a given user interaction on a node and a given command.
 * @param <C> The type of the command to produce.
 * @param <I> The type of the user interaction to bind.
 * @author Arnaud Blouin
 */
public class NodeBinder<C extends CommandImpl, I extends JfxInteraction<D, ?, ?>, D extends InteractionData> extends UpdateBinder<Node, C, I, D, NodeBinder<C, I, D>> {
	public NodeBinder(final I interaction, final Supplier<C> cmdCreation, final JfxInstrument instrument) {
		this(interaction, i -> cmdCreation.get(), instrument);
	}

	public NodeBinder(final I interaction, final Function<D, C> cmdCreation, final JfxInstrument instrument) {
		super(interaction, cmdCreation, instrument);
	}
}
