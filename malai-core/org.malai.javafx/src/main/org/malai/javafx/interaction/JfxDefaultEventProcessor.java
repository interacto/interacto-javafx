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
package org.malai.javafx.interaction;

import javafx.stage.WindowEvent;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Spinner;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextInputControl;
import javafx.scene.control.ToggleButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import org.malai.interaction.Interaction;

/**
 * A trait implementing services of the interface JfxEventProcessor.
 * @author Arnaud Blouin
 */
public interface JfxDefaultEventProcessor extends JfxEventProcessor, Interaction {
	@Override
	default void onScroll(final ScrollEvent evt, final int idHID) {
		if(!isActivated()) return;
		getCurrentState().getTransitions().stream().filter(tr -> tr instanceof ScrollTransition).filter(tr -> {
			final ScrollTransition pt = (ScrollTransition) tr;
			pt.setEvent(evt);
			pt.setHid(idHID);
			return checkTransition(tr);
		}).findFirst();
	}

	@Override
	default void onMove(final MouseEvent evt, final int idHID) {
		if(!isActivated()) return;
		getCurrentState().getTransitions().stream().filter(tr -> tr instanceof MoveTransition).filter(tr -> {
			final MoveTransition pt = (MoveTransition) tr;
			pt.setEvent(evt);
			pt.setHid(idHID);
			return checkTransition(tr);
		}).findFirst();
	}

	@Override
	default void onDrag(final MouseEvent evt, final int idHID) {
		if(!isActivated()) return;
		getCurrentState().getTransitions().stream().filter(tr -> tr instanceof DragTransition).filter(tr -> {
			final DragTransition pt = (DragTransition) tr;
			pt.setEvent(evt);
			pt.setHid(idHID);
			return checkTransition(tr);
		}).findFirst();
	}

	@Override
	default void onJfxButtonPressed(final Button button) {
		if(!isActivated()) return;
		getCurrentState().getTransitions().stream().filter(tr -> tr instanceof JfxButtonPressedTransition).filter(tr -> {
			((JfxButtonPressedTransition) tr).setWidget(button);
			return checkTransition(tr);
		}).findFirst();
	}

	@Override
	default void onJfxBoxChecked(final CheckBox cb) {
		if(!isActivated()) return;
		getCurrentState().getTransitions().stream().filter(tr -> tr instanceof JfxBoxCheckedTransition).filter(tr -> {
			((JfxBoxCheckedTransition) tr).setWidget(cb);
			return checkTransition(tr);
		}).findFirst();
	}

	@Override
	default void onJfxHyperlinkClicked(final Hyperlink link) {
		if(!isActivated()) return;
		getCurrentState().getTransitions().stream().filter(tr -> tr instanceof JfxHyperlinkClickedTransition).filter(tr -> {
			((JfxHyperlinkClickedTransition) tr).setWidget(link);
			return checkTransition(tr);
		}).findFirst();
	}

	@Override
	default void onJfxMenuButtonPressed(final MenuButton button) {
		if(!isActivated()) return;
		getCurrentState().getTransitions().stream().filter(tr -> tr instanceof JfxMenuButtonPressedTransition).filter(tr -> {
			((JfxMenuButtonPressedTransition) tr).setWidget(button);
			return checkTransition(tr);
		}).findFirst();
	}

	@Override
	default void onJfxMenuItemPressed(final MenuItem item) {
		if(!isActivated()) return;
		getCurrentState().getTransitions().stream().filter(tr -> tr instanceof JfxMenuItemPressedTransition).filter(tr -> {
			((JfxMenuItemPressedTransition) tr).setWidget(item);
			return checkTransition(tr);
		}).findFirst();
	}

	@Override
	default void onJfxToggleButtonPressed(final ToggleButton button) {
		if(!isActivated()) return;
		getCurrentState().getTransitions().stream().filter(tr -> tr instanceof JfxToggleButtonPressedTransition).filter(tr -> {
			((JfxToggleButtonPressedTransition) tr).setWidget(button);
			return checkTransition(tr);
		}).findFirst();
	}

	@Override
	default void onJfxComboBoxSelected(final ComboBox<?> cc) {
		if(!isActivated()) return;
		getCurrentState().getTransitions().stream().filter(tr -> tr instanceof JfxComboBoxUsedTransition).filter(tr -> {
			((JfxComboBoxUsedTransition) tr).setWidget(cc);
			return checkTransition(tr);
		}).findFirst();
	}

	@Override
	default void onJfxDatePicked(final DatePicker dp) {
		if(!isActivated()) return;
		getCurrentState().getTransitions().stream().filter(tr -> tr instanceof JfxDatePickedTransition).filter(tr -> {
			((JfxDatePickedTransition) tr).setWidget(dp);
			return checkTransition(tr);
		}).findFirst();
	}

	@Override
	default void onJfxColorPicked(final ColorPicker cc) {
		if(!isActivated()) return;
		getCurrentState().getTransitions().stream().filter(tr -> tr instanceof JfxColorPickedTransition).filter(tr -> {
			((JfxColorPickedTransition) tr).setWidget(cc);
			return checkTransition(tr);
		}).findFirst();
	}

	@Override
	default void onTextChanged(final TextInputControl tf) {
		if(!isActivated()) return;
		getCurrentState().getTransitions().stream().filter(tr -> tr instanceof JfxTextChangedTransition).filter(tr -> {
			((JfxTextChangedTransition) tr).setWidget(tf);
			return checkTransition(tr);
		}).findFirst();
	}

	@Override
	default void onJfxSpinnerValueChanged(final Spinner<?> spinner) {
		if(!isActivated()) return;
		getCurrentState().getTransitions().stream().filter(tr -> tr instanceof JfxSpinnerValueChangedTransition).filter(tr -> {
			((JfxSpinnerValueChangedTransition) tr).setWidget(spinner);
			return checkTransition(tr);
		}).findFirst();
	}

	@Override
	default void onJfXTabSelected(final TabPane tabPane) {
		if(!isActivated()) return;
		getCurrentState().getTransitions().stream().filter(tr -> tr instanceof JfxTabSelectedTransition).filter(tr -> {
			((JfxTabSelectedTransition) tr).setWidget(tabPane);
			return checkTransition(tr);
		}).findFirst();
	}


	@Override
	default void onWindowClosed(final WindowEvent event) {
		if(!isActivated()) return;
		getCurrentState().getTransitions().stream().filter(tr -> tr instanceof JfxWindowClosedTransition).filter(tr -> {
			((JfxWindowClosedTransition) tr).setEvent(event);
			return checkTransition(tr);
		}).findFirst();
	}
}
