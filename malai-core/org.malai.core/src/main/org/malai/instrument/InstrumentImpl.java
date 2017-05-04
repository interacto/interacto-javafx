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
package org.malai.instrument;

import java.util.ArrayList;
import java.util.List;
import org.malai.action.Action;
import org.malai.binding.WidgetBinding;
import org.malai.error.ErrorCatcher;
import org.malai.interaction.Eventable;
import org.malai.undo.Undoable;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 * The base class of an instrument.
 * @author Arnaud BLOUIN
 */
public abstract class InstrumentImpl<T extends WidgetBinding> implements Instrument {
	/** Defines if the instrument is activated or not. */
	protected boolean activated;

	/** The widget bindings of the instrument. */
	protected final List<T> bindings;

	/** Defined if the instrument has been modified. */
	protected boolean modified;

	/** The eventable objects that the instrument uses. */
	protected List<Eventable> eventables;


	/**
	 * Creates and initialises the instrument.
	 * @since 0.1
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


	@SuppressWarnings("unchecked")
	@Override
	public List<WidgetBinding> getWidgetBindings() {
		return (List<WidgetBinding>) bindings;
	}


	/**
	 * Initialises the bindings of the instrument.
	 * @throws InstantiationException When a widget binding cannot instantiate its interaction.
	 * @throws IllegalAccessException When a widget binding cannot instantiate its interaction.
	 * @since 0.2
	 */
	protected abstract void configureBindings() throws InstantiationException, IllegalAccessException;


	/**
	 * Adds the given widget binding to the list of bindings of the instrument.
	 * Eventables object previously added to the instrument are added
	 * to the added widget binding.
	 * @param binding The widget binding to add. If null, nothing is done.
	 * @since 0.2
	 */
	protected void addBinding(final T binding) {
		if(binding != null) {
			bindings.add(binding);
			binding.setActivated(isActivated());

			if(eventables != null) {
				eventables.forEach(eventable -> binding.addEventable(eventable));
			}
		}
	}


	/**
	 * Removes the given widget binding from the list of bindings of the instrument.
	 * @param binding The widget binding to remove.
	 * @return True: the given widget binding has been removed. False otherwise.
	 * @since 0.2
	 */
	protected boolean removeBinding(final T binding) {
		return binding != null && bindings.remove(binding);
	}


	@Override
	public void addEventable(final Eventable eventable) {
		if(eventable != null) {
			if(eventables == null) {
				eventables = new ArrayList<>();
			}

			eventables.add(eventable);
			bindings.forEach(binding -> binding.addEventable(eventable));
		}
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
	public void setModified(final boolean isModified) {
		modified = isModified;
	}


	@Override
	public boolean isModified() {
		return modified;
	}


	@Override
	public void reinit() {
		// Should be overridden.
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
	public void onActionCancelled(final Action action) {
		// Should be overridden.
	}

	@Override
	public void onActionAdded(final Action action) {
		// Should be overridden.
	}

	@Override
	public void onActionAborted(final Action action) {
		// Should be overridden.
	}

	@Override
	public void onActionExecuted(final Action action) {
		// Should be overridden.
	}

	@Override
	public void onActionDone(final Action action) {
		// Should be overridden.
	}
}
