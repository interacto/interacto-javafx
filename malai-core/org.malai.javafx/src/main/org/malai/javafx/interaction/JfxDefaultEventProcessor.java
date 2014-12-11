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
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;

import org.malai.interaction.Interaction;

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
	default void onPressure(final MouseEvent evt, final int idHID) {
		if(!isActivated()) return ;
		getCurrentState().getTransitions().stream().filter(tr -> tr instanceof PressureTransition).filter(tr -> {
			final PressureTransition pt = (PressureTransition)tr;
			pt.setEvent(evt);
			pt.setHid(idHID);
			return checkTransition(tr);
		}).findFirst();
	}

	@Override
	default void onRelease(final MouseEvent evt, final int idHID) {
		if(!isActivated()) return ;
		getCurrentState().getTransitions().stream().filter(tr -> tr instanceof ReleaseTransition).filter(tr -> {
			final ReleaseTransition pt = (ReleaseTransition)tr;
			pt.setEvent(evt);
			pt.setHid(idHID);
			return checkTransition(tr);
		}).findFirst();
	}

	@Override
	default void onMove(final MouseEvent evt, final int idHID) {
		if(!isActivated()) return ;
		getCurrentState().getTransitions().stream().filter(tr -> tr instanceof MoveTransition).filter(tr -> {
			final MoveTransition pt = (MoveTransition)tr;
			pt.setEvent(evt);
			pt.setHid(idHID);
			return checkTransition(tr);
		}).findFirst();
	}
	
	@Override
	default void onDrag(final MouseEvent evt, final int idHID) {
		if(!isActivated()) return ;
		getCurrentState().getTransitions().stream().filter(tr -> tr instanceof DragTransition).filter(tr -> {
			final DragTransition pt = (DragTransition)tr;
			pt.setEvent(evt);
			pt.setHid(idHID);
			return checkTransition(tr);
		}).findFirst();
	}
	
	@Override
	default void onKeyPressure(final KeyEvent event, final int idHID) {
		if(!isActivated()) return ;
		getCurrentState().getTransitions().stream().filter(tr -> tr instanceof KeyPressureTransition).filter(tr -> {
			final KeyPressureTransition pt =  (KeyPressureTransition)tr;
			pt.setEvent(event);
			pt.setHid(idHID);
			return checkTransition(tr);
		}).findFirst();
	}

	@Override
	default void onKeyRelease(final KeyEvent event, final int idHID) {
		if(!isActivated()) return ;
		getCurrentState().getTransitions().stream().filter(tr -> tr instanceof KeyReleaseTransition).filter(tr -> {
			final KeyReleaseTransition pt =  (KeyReleaseTransition)tr;
			pt.setEvent(event);
			pt.setHid(idHID);
			return checkTransition(tr);
		}).findFirst();
	}
	
	@Override
	default void onJfxButtonPressed(final Button button) {
		if(!isActivated()) return ;
		getCurrentState().getTransitions().stream().filter(tr -> tr instanceof JfxButtonPressedTransition).filter(tr -> {
			((JfxButtonPressedTransition)tr).setWidget(button);
			return checkTransition(tr);
		}).findFirst();
	}
	
	@Override
	default void onJfxBoxChecked(final CheckBox button) {
		if(!isActivated()) return ;
		getCurrentState().getTransitions().stream().filter(tr -> tr instanceof JfxBoxCheckedTransition).filter(tr -> {
			((JfxBoxCheckedTransition)tr).setWidget(button);
			return checkTransition(tr);
		}).findFirst();
	}
	
	@Override
	default void onJfxHyperlinkClicked(final Hyperlink button) {
		if(!isActivated()) return ;
		getCurrentState().getTransitions().stream().filter(tr -> tr instanceof JfxHyperlinkClickedTransition).filter(tr -> {
			((JfxHyperlinkClickedTransition)tr).setWidget(button);
			return checkTransition(tr);
		}).findFirst();
	}
	
	@Override
	default void onJfxMenuButtonPressed(final MenuButton button) {
		if(!isActivated()) return ;
		getCurrentState().getTransitions().stream().filter(tr -> tr instanceof JfxMenuButtonPressedTransition).filter(tr -> {
			((JfxMenuButtonPressedTransition)tr).setWidget(button);
			return checkTransition(tr);
		}).findFirst();
	}
	
	@Override
	default void onJfxMenuItemPressed(final MenuItem item) {
		if(!isActivated()) return ;
		getCurrentState().getTransitions().stream().filter(tr -> tr instanceof JfxMenuItemPressedTransition).filter(tr -> {
			((JfxMenuItemPressedTransition)tr).setWidget(item);
			return checkTransition(tr);
		}).findFirst();
	}
	
	@Override
	default void onJfxToggleButtonPressed(final ToggleButton button) {
		if(!isActivated()) return ;
		getCurrentState().getTransitions().stream().filter(tr -> tr instanceof JfxToggleButtonPressedTransition).filter(tr -> {
			((JfxToggleButtonPressedTransition)tr).setWidget(button);
			return checkTransition(tr);
		}).findFirst();
	}
	
	@Override
	default void onJfxComboBoxSelected(final ComboBox<?> cc) {
		if(!isActivated()) return ;
		getCurrentState().getTransitions().stream().filter(tr -> tr instanceof JfxComboBoxUsedTransition).filter(tr -> {
			((JfxComboBoxUsedTransition)tr).setWidget(cc);
			return checkTransition(tr);
		}).findFirst();
	}
	
	@Override
	default void onJfxDatePicked(final DatePicker cc) {
		if(!isActivated()) return ;
		getCurrentState().getTransitions().stream().filter(tr -> tr instanceof JfxDatePickedTransition).filter(tr -> {
			((JfxDatePickedTransition)tr).setWidget(cc);
			return checkTransition(tr);
		}).findFirst();
	}
	
	@Override
	default void onJfxColorPicked(final ColorPicker cc) {
		if(!isActivated()) return ;
		getCurrentState().getTransitions().stream().filter(tr -> tr instanceof JfxColorPickedTransition).filter(tr -> {
			((JfxColorPickedTransition)tr).setWidget(cc);
			return checkTransition(tr);
		}).findFirst();
	}
	
	@Override
	default void onTextChanged(final TextField cc) {
		if(!isActivated()) return ;
		getCurrentState().getTransitions().stream().filter(tr -> tr instanceof JfxTextChangedTransition).filter(tr -> {
			((JfxTextChangedTransition)tr).setWidget(cc);
			return checkTransition(tr);
		}).findFirst();
	}
}
