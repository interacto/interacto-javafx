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
package io.github.interacto.jfx.ui;

import javafx.beans.NamedArg;
import javafx.event.ActionEvent;
import javafx.scene.control.Spinner;

/**
 * A spinner that works...
 * Should be used instead of the default JFX Spinner
 * @author Arnaud BLOUIN
 */
public class SpinnerFixed<T> extends Spinner<T> {
	public SpinnerFixed() {
		super();
		configureSpinner();
	}

	public SpinnerFixed(@NamedArg("min") final int min, @NamedArg("max") final int max, @NamedArg("initialValue") final int initialValue) {
		super(min, max, initialValue);
		configureSpinner();
	}

	public SpinnerFixed(@NamedArg("min") final int min, @NamedArg("max") final int max, @NamedArg("initialValue") final int initialValue, @NamedArg
		("amountToStepBy") final int amountToStepBy) {
		super(min, max, initialValue, amountToStepBy);
		configureSpinner();
	}

	public SpinnerFixed(@NamedArg("min") final double min, @NamedArg("max") final double max, @NamedArg("initialValue") final double initialValue) {
		super(min, max, initialValue);
		configureSpinner();
	}

	public SpinnerFixed(@NamedArg("min") final double min, @NamedArg("max") final double max, @NamedArg("initialValue") final double initialValue,
					@NamedArg("amountToStepBy") final double amountToStepBy) {
		super(min, max, initialValue, amountToStepBy);
		configureSpinner();
	}

	private final void configureSpinner() {
		setOnScroll(event -> {
			if(event.getDeltaY() < 0d) {
				decrement();
			}else {
				if(event.getDeltaY() > 0d) {
					increment();
				}
			}
		});
	}

	@Override
	public void increment(final int steps) {
		final T value = getValue();
		super.increment(steps);
		if(value != null && !value.equals(getValue())) {
			sendAction();
		}
	}

	@Override
	public void decrement(final int steps) {
		final T value = getValue();
		super.decrement(steps);
		if(value != null && !value.equals(getValue())) {
			sendAction();
		}
	}

	private void sendAction() {
		fireEvent(new ActionEvent(this, null));
	}
}
