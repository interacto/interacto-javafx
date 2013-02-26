package org.malai.action.library;

/**
 * This action inactivates an instrument.<br>
 * <br>
 * This file is part of Malai.<br>
 * Copyright (c) 2009-2013 Arnaud BLOUIN<br>
 * <br>
 * Malai is free software; you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation; either version 2 of the License, or (at your option) any later version.
 * <br>
 * Malai is distributed without any warranty; without even the implied
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * General Public License for more details.<br>
 * @author Arnaud Blouin
 * @since 0.2
 */
public class InactivateInstrument extends InstrumentAction {
	/**
	 * {@link InstrumentAction#InstrumentAction()}
	 */
	public InactivateInstrument() {
		super();
	}


	@Override
	protected void doActionBody() {
		instrument.setActivated(false);
	}


	@Override
	public boolean isRegisterable() {
		return false;
	}
}
