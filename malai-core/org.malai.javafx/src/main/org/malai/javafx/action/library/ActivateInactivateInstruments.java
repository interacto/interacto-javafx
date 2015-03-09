/*
 * This file is part of Malai.<br>
 * Copyright (c) 2005-2015 Arnaud BLOUIN<br>
 * <br>
 * Malai is free software; you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation; either version 2 of the License, or (at your option) any later version.
 * <br>
 * Malai is distributed without any warranty; without even the implied
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * General Public License for more details.<br> 
 */
package org.malai.javafx.action.library;

import java.util.ArrayList;
import java.util.List;

import org.malai.action.Action;
import org.malai.javafx.instrument.JfxInstrument;

/**
 * This instrument activates and inactivates instruments.<br>
 * <br>
 * @author Arnaud Blouin
 * @since 2.0
 */
public class ActivateInactivateInstruments extends Action {
	/** The instruments to activate. */
	protected List<JfxInstrument> insActivate;

	/** The instrument to desactivate. */
	protected List<JfxInstrument> insInactivate;

	/** Defines the activations must be performed before the inactivations. */
	protected boolean activateFirst;



	/**
	 * Creates and initialises the instrument.
	 * By default instruments are not hidden and activation is performed first.
	 * @since 0.1
	 */
	public ActivateInactivateInstruments() {
		super();
		activateFirst = true;
	}


	@Override
	public void flush() {
		super.flush();
		if(insActivate!=null)
			insActivate.clear();
		if(insInactivate!=null)
			insInactivate.clear();
	}


	@Override
	protected void doActionBody() {
		if(activateFirst) {
			activate();
			deactivate();
		} else {
			deactivate();
			activate();
		}
	}


	protected void deactivate() {
		if(insInactivate!=null)
			insInactivate.forEach(ins -> ins.setActivated(false));
	}


	protected void activate() {
		if(insActivate!=null)
			insActivate.forEach(ins -> ins.setActivated(true));
	}


	/**
	 * Adds an instrument to activate.
	 * @param ins The instrument to activate.
	 * @since 0.1
	 */
	public void addInstrumentToActivate(final JfxInstrument ins) {
		if(ins!=null) {
			if(insActivate==null)
				insActivate = new ArrayList<>();

			insActivate.add(ins);
		}
	}


	/**
	 * Adds an instrument to inactivate.
	 * @param ins The instrument to inactivate.
	 * @since 0.1
	 */
	public void addInstrumentToInactivate(final JfxInstrument ins) {
		if(ins!=null) {
			if(insInactivate==null)
				insInactivate = new ArrayList<>();

			insInactivate.add(ins);
		}
	}


	@Override
	public boolean canDo() {
		return insActivate!=null || insInactivate!=null;
	}


	/**
	 * @return False. Action ActivateInactivateInstruments cannot be registered.
	 */
	@Override
	public boolean isRegisterable() {
		return false;
	}


	/**
	 * @param activateFirst True: the activations will be performed before the inactivations.
	 * @since 0.2
	 */
	public void setActivateFirst(final boolean activateFirst) {
		this.activateFirst = activateFirst;
	}
}
