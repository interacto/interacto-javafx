package org.malai.javafx.interaction.library;

import java.util.List;

import org.malai.interaction.AbortingState;
import org.malai.interaction.ReleaseTransition;
import org.malai.stateMachine.Transition;

public class AbortableDnD extends DnD {

	public AbortableDnD() {
		super();
	}
	
	@SuppressWarnings("unused")
	@Override
	protected void initStateMachine() {
		super.initStateMachine();

		AbortingState aborted = new AbortingState("aborted"); //$NON-NLS-1$
		addState(aborted);

		new EscapeKeyPressureTransition(pressed, aborted);
		new EscapeKeyPressureTransition(dragged, aborted);

		List<Transition> ts = pressed.getTransitions();
		boolean ok = false;
		int i=0, size = ts.size();
		Transition t;

		while(!ok && i<size) {
			t = ts.get(i);

			if(t instanceof ReleaseTransition && t.getOutputState()==released) {
				ok = true;
				ts.remove(t);
			}
			else i++;
		}

		new Release4DnD(pressed, aborted);
	}
}
