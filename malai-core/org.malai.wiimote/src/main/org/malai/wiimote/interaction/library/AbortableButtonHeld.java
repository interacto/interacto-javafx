package org.malai.wiimote.interaction.library;

import java.util.List;

import org.malai.interaction.AbortingState;
import org.malai.interaction.ReleaseTransition;
import org.malai.stateMachine.ITransition;


/**
 * Allows to track a button held, and disable it if a ReleaseTransition 
 * is launched
 * 
 * @author Maxime Lorant
 *
 */
public class AbortableButtonHeld extends ButtonHeld {

	/**
	 * Creates the interaction.
	 */
	public AbortableButtonHeld() {
		super();
		initStateMachine();
	}

	/** 
	 * Build the interaction, with a new state, to
	 * abort the current interaction
	 */
	@Override
	protected void initStateMachine() {
		
		super.initStateMachine();

		AbortingState aborted = new AbortingState("aborted"); //$NON-NLS-1$
		addState(aborted);

		new EscapeButtonPressureTransition(pressed, aborted);

		List<ITransition> ts = pressed.getTransitions();
		boolean ok = false;
		int i=0, size = ts.size();
		ITransition t;

		while(!ok && i<size) {
			t = ts.get(i);

			if(t instanceof ReleaseTransition && t.getOutputState()==released) {
				ok = true;
				ts.remove(t);
			}
			else i++;
		}
	}
}