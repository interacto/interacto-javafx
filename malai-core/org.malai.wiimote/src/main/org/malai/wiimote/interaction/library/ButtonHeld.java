package org.malai.wiimote.interaction.library;

import org.malai.interaction.IntermediaryState;
import org.malai.interaction.TerminalState;
import org.malai.wiimote.interaction.ButtonPressedTransition;
import org.malai.wiimote.interaction.WiimoteInteraction;


/**
 * Occurs when one or several buttons are pressed on the wiimote
 * 
 * @author Maxime Lorant
 *
 */
public class ButtonHeld extends WiimoteInteraction {
	
	/** The pressed button. */
	protected int buttonPressed;

	/**
	 * Creates the interaction.
	 */
	public ButtonHeld() {
		super();
		initStateMachine();
	}

	@Override
	protected void initStateMachine() {
		final IntermediaryState pressed = new IntermediaryState("pressed"); //$NON-NLS-1$
		final TerminalState released = new TerminalState("released"); //$NON-NLS-1$

		addState(pressed);
		addState(released);

		new ButtonPressedTransition(initState, pressed) {
			@Override
			public void action() {
				super.action();
				ButtonHeld.this.buttonPressed = this.button.getButtonsJustPressed();
			}
		};
		
		new ButtonPressedTransition(pressed, released) {
			
			@Override
			public boolean isGuardRespected() {
				int released = this.button.getButtonsJustReleased();
				return super.isGuardRespected() && ButtonHeld.this.buttonPressed==released;
			}
		};
	}


	/**
	 * @return The pressed button.
	 * @since 0.2
	 */
	public int getButtonPressed() {
		return buttonPressed;
	}
}
