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
package org.malai.instrument;

import java.util.ArrayList;
import java.util.List;
import org.malai.command.Command;
import org.malai.binding.WidgetBinding;
import org.malai.error.ErrorCatcher;
import org.malai.undo.Undoable;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 * The base class of an instrument.
 * @author Arnaud BLOUIN
 */
public abstract class InstrumentImpl<T extends WidgetBinding> implements Instrument<T> {
	/** Defines whether the instrument is activated. */
	protected boolean activated;

	/** The widget bindings of the instrument. */
	protected final List<T> bindings;

	/** Defined whether the instrument has been modified. */
	protected boolean modified;

	/**
	 * Creates and initialises the instrument.
	 */
	public InstrumentImpl() {
		activated = false;
		modified = false;
		bindings = new ArrayList<>();
	}


	@Override
	public int getNbWidgetBindings() {
		return bindings.size();
	}


	@Override
	public boolean hasWidgetBindings() {
		return getNbWidgetBindings() > 0;
	}


	@Override
	public List<T> getWidgetBindings() {
		return bindings;
	}


	/**
	 * Initialises the bindings of the instrument.
	 * @throws InstantiationException When a widget binding cannot instantiate its interaction.
	 * @throws IllegalAccessException When a widget binding cannot instantiate its interaction.
	 */
	protected abstract void configureBindings() throws InstantiationException, IllegalAccessException;


	/**
	 * Adds the given widget binding to the list of bindings of the instrument.
	 * @param binding The widget binding to add. If null, nothing is done.
	 */
	public void addBinding(final T binding) {
		if(binding != null) {
			bindings.add(binding);
			binding.setActivated(isActivated());
		}
	}

	/**
	 * Removes the given widget binding from the list of bindings of the instrument.
	 * @param binding The widget binding to remove.
	 * @return True: the given widget binding has been removed. False otherwise.
	 */
	public boolean removeBinding(final T binding) {
		return binding != null && bindings.remove(binding);
	}


	@Override
	public void clearEvents() {
		bindings.forEach(binding -> binding.clearEvents());
	}


	@Override
	public boolean isActivated() {
		return activated;
	}


	@Override
	public void setActivated(final boolean toBeActivated) {
		activated = toBeActivated;

		if(toBeActivated && !hasWidgetBindings()) {
			try {
				configureBindings();
			}catch(InstantiationException | IllegalAccessException ex) {
				ErrorCatcher.INSTANCE.reportError(ex);
			}
		}else {
			bindings.forEach(binding -> binding.setActivated(toBeActivated));
		}

		interimFeedback();
	}


	@Override
	public void interimFeedback() {
		// Nothing to do
	}


	@Override
	public void save(final boolean generalPreferences, final String nsURI, final Document document, final Element root) {
		// Should be overridden.
	}


	@Override
	public void load(final boolean generalPreferences, final String nsURI, final Element meta) {
		// Should be overridden.
	}

	@Override
	public boolean isModified() {
		return modified;
	}

	@Override
	public void setModified(final boolean isModified) {
		modified = isModified;
	}

	@Override
	public void reinit() {
		// Should be overridden.
	}

	@Override
	public void uninstallBindings() {
		bindings.forEach(binding -> binding.uninstallBinding());
		bindings.clear();
	}

	@Override
	public void onUndoableCleared() {
		// Should be overridden.
	}

	@Override
	public void onUndoableAdded(final Undoable undoable) {
		// Should be overridden.
	}

	@Override
	public void onUndoableUndo(final Undoable undoable) {
		// Should be overridden.
	}

	@Override
	public void onUndoableRedo(final Undoable undoable) {
		// Should be overridden.
	}

	@Override
	public void onCmdAdded(final Command cmd) {
		// Should be overridden.
	}

	@Override
	public void onCmdCancelled(final Command cmd) {
		// Should be overridden.
	}

	@Override
	public void onCmdExecuted(final Command cmd) {
		// Should be overridden.
	}

	@Override
	public void onCmdDone(final Command cmd) {
		// Should be overridden.
	}
}
