package org.malai.javafx.ui;

import java.util.ArrayList;
import java.util.List;

import javafx.application.Application;

import org.malai.instrument.Instrument;
import org.malai.presentation.AbstractPresentation;
import org.malai.presentation.ConcretePresentation;
import org.malai.presentation.Presentation;
import org.malai.ui.UI;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 * Defines the concept of User Interface based on a JavaFX Application.<br>
 * <br>
 * This file is part of libMalai.<br>
 * Copyright (c) 2005-2014 Arnaud BLOUIN<br>
 * <br>
 * libMalan is free software; you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation; either version 2 of the License, or (at your option) any later version.<br>
 * <br>
 * libMalan is distributed without any warranty; without even the implied
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * General Public License for more details.<br>
 * @author Arnaud BLOUIN
 */
public abstract class JfxUI extends Application implements UI<JfxUIComposer<?>> {
	/** The presentations of the interactive system. */
	protected List<Presentation<?,?>> presentations;

	/** Defined if the UI has been modified. */
	protected boolean modified;

	/** The composer that composes the UI. */
	protected JfxUIComposer<?> composer;


	/**
	 * Creates a user interface.
	 * @since 0.1
	 */
	public JfxUI() {
		super();
		modified 		= false;
		presentations 	= new ArrayList<>();
		initialisePresentations();
	}


	@Override
	public void setModified(final boolean modified) {
		this.modified = modified;

		for(final Presentation<?, ?> pres : presentations)
			pres.setModified(modified);

		for(final Instrument ins : getInstruments())
			ins.setModified(modified);
	}


	@Override
	public boolean isModified() {
		boolean ok = modified;
		int i=0;
		final int size = presentations.size();

		// Looking for a modified presentation.
		while(!ok && i<size)
			if(presentations.get(i).isModified())
				ok = true;
			else i++;

		final Instrument[] instruments = getInstruments();
		i=0;

		while(i<instruments.length && !ok)
			if(instruments[i].isModified())
				 ok = true;
			else i++;

		return ok;
	}

	@Override
	public void updatePresentations() {
		for(final Presentation<?,?> presentation : presentations)
			presentation.update();
	}


	@Override
	@SuppressWarnings("unchecked")
	public <A extends AbstractPresentation, C extends ConcretePresentation>
	Presentation<A, C> getPresentation(final Class<A> absPresClass, final Class<C> concPresClass) {
		Presentation<A, C> pres = null;
		Presentation<?, ?> tmp;

		for(int i=0, size=presentations.size(); i<size && pres==null; i++) {
			tmp = presentations.get(i);
			if(absPresClass.isInstance(tmp.getAbstractPresentation()) && concPresClass.isInstance(tmp.getConcretePresentation()))
				pres = (Presentation<A, C>)tmp;
		}

		return pres;
	}


	@Override
	public void save(final boolean generalPreferences, final String nsURI, final Document document, final Element root) {
		// To override.
	}


	@Override
	public void load(final boolean generalPreferences, final String nsURI, final Element meta) {
		// To override.
	}


	@Override
	public List<Presentation<?,?>> getPresentations() {
		return presentations;
	}


	@Override
	public void reinit() {
		for(final Presentation<?,?> presentation : presentations)
			presentation.reinit();

		for(final Instrument ins : getInstruments())
			ins.reinit();

		setModified(false);
	}


	@Override
	public JfxUIComposer<?> getComposer() {
		return composer;
	}
}
