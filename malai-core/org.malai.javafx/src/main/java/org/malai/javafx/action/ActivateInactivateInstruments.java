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
package org.malai.javafx.action;

import java.util.ArrayList;
import java.util.List;
import org.malai.action.ActionImpl;
import org.malai.javafx.instrument.JfxInstrument;

/**
 * This instrument activates and inactivates instruments.
 * @author Arnaud Blouin
 */
public class ActivateInactivateInstruments extends ActionImpl {
	/** The instruments to activate. */
	protected List<JfxInstrument> insActivate;

	/** The instrument to desactivate. */
	protected List<JfxInstrument> insInactivate;

	/** Defines the activations must be performed before the inactivations. */
	protected boolean activateFirst;

	/** Defines if the widgets of WidgetInstrument instances must be hidden during their deactivation. */
	protected boolean hideWidgets;

	public ActivateInactivateInstruments(final List<JfxInstrument> insActivate, final List<JfxInstrument> insInactivate,
										 final boolean activateFirst, final boolean hideWidgets) {
		this.insActivate = insActivate;
		this.insInactivate = insInactivate;
		this.activateFirst = activateFirst;
		this.hideWidgets = hideWidgets;
	}

	/**
	 * Creates and initialises the instrument.
	 * By default instruments are not hidden and activation is performed map.
	 */
	public ActivateInactivateInstruments() {
		this(null, null, true, false);
	}


	@Override
	public void flush() {
		super.flush();
		if(insActivate != null) {
			insActivate.clear();
		}
		if(insInactivate != null) {
			insInactivate.clear();
		}
	}


	@Override
	protected void doActionBody() {
		if(activateFirst) {
			activate();
			deactivate();
		}else {
			deactivate();
			activate();
		}
	}


	protected void deactivate() {
		if(insInactivate != null) {
			insInactivate.forEach(ins -> ins.setActivated(false, hideWidgets));
		}
	}


	protected void activate() {
		if(insActivate != null) {
			insActivate.forEach(ins -> ins.setActivated(true));
		}
	}


	/**
	 * Adds an instrument to activate.
	 * @param ins The instrument to activate.
	 */
	public void addInstrumentToActivate(final JfxInstrument ins) {
		if(ins != null) {
			if(insActivate == null) {
				insActivate = new ArrayList<>();
			}
			insActivate.add(ins);
		}
	}


	/**
	 * Adds an instrument to inactivate.
	 * @param ins The instrument to inactivate.
	 */
	public void addInstrumentToInactivate(final JfxInstrument ins) {
		if(ins != null) {
			if(insInactivate == null) {
				insInactivate = new ArrayList<>();
			}
			insInactivate.add(ins);
		}
	}


	@Override
	public boolean canDo() {
		return insActivate != null || insInactivate != null;
	}


	/**
	 * @param activateFirst True: the activations will be performed before the inactivations.
	 */
	public void setActivateFirst(final boolean activateFirst) {
		this.activateFirst = activateFirst;
	}

	/**
	 * @param hideWidgets Defines whether the widgets of WidgetInstrument instances must be hidden during their deactivation.
	 */
	public void setHideWidgets(final boolean hideWidgets) {
		this.hideWidgets = hideWidgets;
	}
}
