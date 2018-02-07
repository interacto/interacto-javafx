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
package org.malai.javafx.ui;

import java.util.Set;
import java.util.stream.Stream;
import javafx.application.Application;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ChangeListener;
import javafx.scene.Scene;
import org.malai.javafx.instrument.JfxInstrument;
import org.malai.properties.Preferenciable;
import org.malai.properties.Modifiable;
import org.malai.properties.Reinitialisable;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 * A JFX user interface.
 * @author Arnaud BLOUIN
 */
public abstract class JfxUI extends Application implements Modifiable, Reinitialisable, Preferenciable {
	/** Defined if the UI has been modified. */
	protected final BooleanProperty modified;
	/** The lambda that windows should register to reset interactions on focus loses. */
	private final ChangeListener<Boolean> focusReset;

	/**
	 * Creates a user interface.
	 */
	public JfxUI() {
		super();
		modified = new SimpleBooleanProperty(false);
		focusReset = (observable, oldValue, newValue) -> getInstruments().stream().map(ins -> ins.getWidgetBindings()).flatMap(s -> s.stream()).
			forEach(binding -> binding.clearEvents());
	}

	/**
	 * @return The instruments of the app.
	 */
	public abstract Set<JfxInstrument> getInstruments();

	/**
	 * @param <T> The components must be Modifiable and Reinitialisable
	 * @return The set of objects that must be set as modified or be reinitialised (in complement to the instruments).
	 */
	protected abstract <T extends Modifiable & Reinitialisable> Set<T> getAdditionalComponents();

	@Override
	public void setModified(final boolean isModified) {
		modified.set(isModified);
		getAdditionalComponents().forEach(comp -> comp.setModified(isModified));
		getInstruments().forEach(ins -> ins.setModified(isModified));
	}


	@Override
	public boolean isModified() {
		return modified.get() || Stream.concat(getInstruments().stream(), getAdditionalComponents().stream()).anyMatch(ins -> ins.isModified());
	}

	@Override
	public void save(final boolean generalPreferences, final String nsURI, final Document document, final Element root) {
		// To override.
	}


	@Override
	public void load(final boolean generalPreferences, final String nsURI, final Element meta) {
		// To override.
	}

	public void registerScene(final Scene scene) {
		if(scene != null) {
			scene.getWindow().focusedProperty().addListener(focusReset);
		}
	}

	public void unregisterScene(final Scene scene) {
		if(scene != null) {
			scene.getWindow().focusedProperty().removeListener(focusReset);
		}
	}

	@Override
	public void reinit() {
		getAdditionalComponents().forEach(comp -> comp.reinit());
		getInstruments().forEach(ins -> ins.reinit());
		setModified(false);
	}
}
