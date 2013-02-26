package org.malai.android.interaction;

import org.malai.interaction.InitState;
import org.malai.interaction.Interaction;
import org.malai.stateMachine.ITransition;

import android.widget.Button;

public class AndroidInteraction extends Interaction implements AndroidEventHandler {
	public AndroidInteraction() {
		super();
	}

	
	public AndroidInteraction(final InitState initState) {
		super(initState);
	}


	@Override
	public void onButtonPressed(final Button button) {
		if(!activated) return ;

		boolean again = true;
		ITransition t;

		for(int i=0, j=currentState.getTransitions().size(); i<j && again; i++) {
			t = currentState.getTransition(i);

			if(t instanceof ButtonPressedTransition) {
				((ButtonPressedTransition)t).setButton(button);
				again = !checkTransition(t);
			}
		}
	}


	@Override
	protected void initStateMachine() {
		// TODO Auto-generated method stub
		
	}
}
