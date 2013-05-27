package org.malai.wiimote.interaction.library;

import org.malai.interaction.IntermediaryState;
import org.malai.interaction.TerminalState;
import org.malai.wiimote.interaction.ButtonPressedTransition;
import org.malai.wiimote.interaction.WiimoteInteraction;

import wiiusej.wiiusejevents.physicalevents.ButtonsEvent;


/**
 * Occurs when a button is pressed, and finish when this one is released 
 * 
 * @author Maxime Lorant
 */
public class ButtonHeld extends WiimoteInteraction {
	
	/** The pressed button. */
	protected ButtonsEvent button;
	
	/** The justPressed button id, to check at every released button if it's the same */
	protected int buttonPressed;
	
	/** Pressed state */
	protected IntermediaryState pressed;
	
	/** Released state */
	protected TerminalState released;
	

	public ButtonHeld() {
		super();
		initStateMachine();
	}

	@Override
	protected void initStateMachine() {
		pressed  = new IntermediaryState("pressed"); //$NON-NLS-1$
		released = new TerminalState("released"); //$NON-NLS-1$

		addState(pressed);
		addState(released);

		new ButtonPressedTransition(initState, pressed) {
			@Override
			public void action() {
				super.action();
				ButtonHeld.this.buttonPressed = this.button.getButtonsJustPressed();
				ButtonHeld.this.button = this.button;
			}
			
			@Override
			public boolean isGuardRespected() {
				return super.isGuardRespected() && this.button.getButtonsJustPressed() != 0;
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
	 * @return Button held.
	 * @since 0.2
	 */
	public ButtonsEvent getButton() {
		return button;
	}
}
