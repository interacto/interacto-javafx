package org.malai.javafx.instrument;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ButtonBase;
import javafx.scene.control.ColorPicker;

import org.malai.instrument.InstrumentImpl;
import org.malai.javafx.interaction.JfxEventHandler;
import org.malai.javafx.interaction.JfxInteraction;

/**
 * Base of an instrument for JavaFX applications.<br>
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
 * @version 2.0
 */
public abstract class JfxInstrument extends InstrumentImpl<JfxInteractor<?, ? extends JfxInteraction, ? extends JfxInstrument>> implements JfxEventHandler {

	public JfxInstrument() {
		super();
	}

	
    @FXML
    @Override
	public void onAction(final ActionEvent e) {
		if(e==null || !isActivated()) return;

		final Object src = e.getSource();

//		if(src instanceof JMenuItem) {
//			final JMenuItem mi = (JMenuItem) e.getSource();
//
//    		for(final SwingEventHandler handler : swingHandlers)
//				handler.onMenuItemPressed(mi);
//		}
//		else if(src instanceof JCheckBox) {
//			final JCheckBox cb = (JCheckBox) e.getSource();
//
//    		for(final SwingEventHandler handler : swingHandlers)
//				handler.onCheckBoxModified(cb);
//		}
//		else 
		if(src instanceof ButtonBase) {
			final ButtonBase widg = (ButtonBase)e.getSource();
			interactors.forEach(inter -> inter.getInteraction().onJfxButtonPressed(widg));
		}
		else if(src instanceof ColorPicker) {
			final ColorPicker widg = (ColorPicker)e.getSource();
			interactors.forEach(inter -> inter.getInteraction().onJfxColorPicked(widg));
		}
//		else if(src instanceof JTextComponent) {
//			final JTextComponent tc = (JTextComponent)e.getSource();
//
//    		for(final SwingEventHandler handler : swingHandlers)
//				handler.onTextChanged(tc);
//		}
    }
}
