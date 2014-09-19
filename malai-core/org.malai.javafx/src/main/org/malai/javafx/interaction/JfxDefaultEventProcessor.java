package org.malai.javafx.interaction;

import javafx.scene.control.ButtonBase;
import javafx.scene.control.ColorPicker;

import org.malai.interaction.Interaction;
import org.malai.stateMachine.State;
import org.malai.stateMachine.Transition;

/**
 * A trait implementing services of the interface JfxEventProcessor.<br>
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
public interface JfxDefaultEventProcessor extends JfxEventProcessor, Interaction {
	@Override
	default void onJfxButtonPressed(final ButtonBase button) {
		if(!isActivated()) return ;

		boolean again = true;
		Transition t;
		final State state = getCurrentState();

		for(int i=0, size=state.getTransitions().size(); i<size && again; i++) {
			t = state.getTransition(i);

			if(t instanceof JfxButtonPressedTransition) {
				((JfxButtonPressedTransition)t).setWidget(button);
				again = !checkTransition(t);
			}
		}	
	}
	
	@Override
	default void onJfxColorPicked(final ColorPicker picker) {
		if(!isActivated()) return ;

		boolean again = true;
		Transition t;
		final State state = getCurrentState();

		for(int i=0, size=state.getTransitions().size(); i<size && again; i++) {
			t = state.getTransition(i);

			if(t instanceof JfxColorPickedTransition) {
				((JfxColorPickedTransition)t).setWidget(picker);
				again = !checkTransition(t);
			}
		}	
	}
}
