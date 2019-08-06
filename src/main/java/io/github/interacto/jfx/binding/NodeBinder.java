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

import io.github.interacto.command.CommandImpl;
import io.github.interacto.interaction.InteractionData;
import io.github.interacto.jfx.instrument.JfxInstrument;
import io.github.interacto.jfx.interaction.JfxInteraction;
import java.util.function.Function;
import java.util.function.Supplier;
import javafx.scene.Node;

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
