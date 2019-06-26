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
package io.interacto.jfx.command;

import io.interacto.command.CommandImpl;
import io.interacto.jfx.instrument.JfxInstrument;
import java.util.ArrayList;
import java.util.List;

/**
 * This instrument activates and inactivates instruments.
 * @author Arnaud Blouin
 */
public class ActivateInactivateInstruments extends CommandImpl {
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
		super();
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
	protected void doCmdBody() {
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
