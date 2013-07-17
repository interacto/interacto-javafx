package org.malai.jinput.interaction;

import org.malai.interaction.Transition;
import org.malai.stateMachine.SourceableState;
import org.malai.stateMachine.TargetableState;

import net.java.games.input.*;

public class ButtonPressedTransition extends Transition {
	/** The pressed button. */
	protected Component.Identifier.Button button ;

	/**
	 * {@link Transition#Transition(SourceableState, TargetableState)}
	 */
	public ButtonPressedTransition(final SourceableState inputState, final TargetableState outputState) {
		super(inputState, outputState);
	}


	/**
	 * @return The pressed button.
	 * @since 0.2
	 */
	public Component.Identifier.Button getButton() {
		return button ;
	}


	/**
	 * Sets the pressed button.
	 * @param button The pressed button. Must not be null.
	 * @since 0.2
	 */
	public void setButton(final Component.Identifier.Button button) {
		if(button!=null)
			this.button = button;
	}
}
