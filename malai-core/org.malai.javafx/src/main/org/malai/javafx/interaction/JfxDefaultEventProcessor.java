package org.malai.javafx.interaction;

import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;

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
	default void onJfxButtonPressed(final Button button) {
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
	default void onJfxCheckBoxUsed(final CheckBox button) {
		if(!isActivated()) return ;

		boolean again = true;
		Transition t;
		final State state = getCurrentState();

		for(int i=0, size=state.getTransitions().size(); i<size && again; i++) {
			t = state.getTransition(i);

			if(t instanceof JfxCheckBoxUsedTransition) {
				((JfxCheckBoxUsedTransition)t).setWidget(button);
				again = !checkTransition(t);
			}
		}	
	}
	
	@Override
	default void onJfxHyperlinkClicked(final Hyperlink button) {
		if(!isActivated()) return ;

		boolean again = true;
		Transition t;
		final State state = getCurrentState();

		for(int i=0, size=state.getTransitions().size(); i<size && again; i++) {
			t = state.getTransition(i);

			if(t instanceof JfxHyperlinkClickedTransition) {
				((JfxHyperlinkClickedTransition)t).setWidget(button);
				again = !checkTransition(t);
			}
		}	
	}
	
	@Override
	default void onJfxMenuButtonPressed(final MenuButton button) {
		if(!isActivated()) return ;

		boolean again = true;
		Transition t;
		final State state = getCurrentState();

		for(int i=0, size=state.getTransitions().size(); i<size && again; i++) {
			t = state.getTransition(i);

			if(t instanceof JfxMenuButtonPressedTransition) {
				((JfxMenuButtonPressedTransition)t).setWidget(button);
				again = !checkTransition(t);
			}
		}	
	}
	
	@Override
	default void onJfxMenuItemPressed(final MenuItem item) {
		if(!isActivated()) return ;

		boolean again = true;
		Transition t;
		final State state = getCurrentState();

		for(int i=0, size=state.getTransitions().size(); i<size && again; i++) {
			t = state.getTransition(i);

			if(t instanceof JfxMenuItemPressedTransition) {
				((JfxMenuItemPressedTransition)t).setWidget(item);
				again = !checkTransition(t);
			}
		}	
	}
	
	@Override
	default void onJfxToggleButtonsPressed(final ToggleButton button) {
		if(!isActivated()) return ;

		boolean again = true;
		Transition t;
		final State state = getCurrentState();

		for(int i=0, size=state.getTransitions().size(); i<size && again; i++) {
			t = state.getTransition(i);

			if(t instanceof JfxToggleButtonPressedTransition) {
				((JfxToggleButtonPressedTransition)t).setWidget(button);
				again = !checkTransition(t);
			}
		}	
	}
	
	@Override
	default void onJfxComboBoxSelected(final ComboBox<?> cc) {
		if(!isActivated()) return ;

		boolean again = true;
		Transition t;
		final State state = getCurrentState();

		for(int i=0, size=state.getTransitions().size(); i<size && again; i++) {
			t = state.getTransition(i);

			if(t instanceof JfxComboBoxUsedTransition) {
				((JfxComboBoxUsedTransition)t).setWidget(cc);
				again = !checkTransition(t);
			}
		}	
	}
	
	
	@Override
	default void onJfxDatePicked(final DatePicker cc) {
		if(!isActivated()) return ;

		boolean again = true;
		Transition t;
		final State state = getCurrentState();

		for(int i=0, size=state.getTransitions().size(); i<size && again; i++) {
			t = state.getTransition(i);

			if(t instanceof JfxDatePickedTransition) {
				((JfxDatePickedTransition)t).setWidget(cc);
				again = !checkTransition(t);
			}
		}	
	}

	
	@Override
	default void onJfxColorPicked(final ColorPicker cc) {
		if(!isActivated()) return ;

		boolean again = true;
		Transition t;
		final State state = getCurrentState();

		for(int i=0, size=state.getTransitions().size(); i<size && again; i++) {
			t = state.getTransition(i);

			if(t instanceof JfxColorPickedTransition) {
				((JfxColorPickedTransition)t).setWidget(cc);
				again = !checkTransition(t);
			}
		}	
	}

	
	@Override
	default void onTextChanged(final TextField cc) {
		if(!isActivated()) return ;

		boolean again = true;
		Transition t;
		final State state = getCurrentState();

		for(int i=0, size=state.getTransitions().size(); i<size && again; i++) {
			t = state.getTransition(i);

			if(t instanceof JfxTextChangedTransition) {
				((JfxTextChangedTransition)t).setWidget(cc);
				again = !checkTransition(t);
			}
		}	
	}
}
