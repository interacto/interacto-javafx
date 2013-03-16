package org.malai.wiimote.interaction.library;

import org.malai.interaction.TerminalState;
import org.malai.wiimote.interaction.ButtonPressedTransition;
import org.malai.wiimote.interaction.WiimoteInteraction;

import wiiusej.wiiusejevents.physicalevents.ButtonsEvent;


/**
 * Occurs when one or several buttons are pressed on the wiimote
 * 
 * @author Maxime Lorant
 *
 */
public class ButtonPressed extends WiimoteInteraction {
	
	/** The pressed button. */
	protected ButtonsEvent button;

	/**
	 * Creates the interaction.
	 */
	public ButtonPressed() {
		super();
		initStateMachine();
	}


	@Override
	public void reinit() {
		super.reinit();
		button = null;
	}


	@SuppressWarnings("unused")
	@Override
	protected void initStateMachine() {
		final TerminalState pressed = new TerminalState("pressed"); //$NON-NLS-1$

		addState(pressed);

		new ButtonPressedTransition(initState, pressed) {
			@Override
			public void action() {
				super.action();
				ButtonPressed.this.button = this.button;
			}
		};
	}


	/**
	 * @return The pressed button.
	 * @since 0.2
	 */
	public ButtonsEvent getButton() {
		return button;
	}
}
