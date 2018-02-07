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
package org.malai.action.library;

import org.malai.action.ActionImpl;
import org.malai.instrument.Instrument;

/**
 * This action manipulates an instrument.
 * @author Arnaud Blouin
 */
public abstract class InstrumentAction extends ActionImpl {
	/** The manipulated instrument. */
	protected Instrument<?> instrument;

	/**
	 * Creates the action.
	 */
	public InstrumentAction() {
		this(null);
	}

	public InstrumentAction(final Instrument<?> instrument) {
		this.instrument = instrument;
	}

	@Override
	public void flush() {
		super.flush();
		instrument = null;
	}


	@Override
	public boolean canDo() {
		return instrument != null;
	}


	/**
	 * @return The manipulated instrument.
	 */
	public Instrument<?> getInstrument() {
		return instrument;
	}


	/**
	 * Sets the manipulated instrument.
	 * @param newInstrument The manipulated instrument.
	 */
	public void setInstrument(final Instrument<?> newInstrument) {
		instrument = newInstrument;
	}
}
