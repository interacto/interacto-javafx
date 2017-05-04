package org.malai.swing.instrument;

import org.malai.binding.WidgetBinding;
import org.malai.instrument.InstrumentImpl;

/**
 * A swing instrument should be used when developing a Swing app only.<br>
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
 * @date 2014-09-19
 * @since 2.0
 */
public abstract class SwingInstrument extends InstrumentImpl<WidgetBinding> {
	public SwingInstrument() {
		super();
	}
}
