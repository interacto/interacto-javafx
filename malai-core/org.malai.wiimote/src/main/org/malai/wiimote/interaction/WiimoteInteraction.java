package org.malai.wiimote.interaction;

import org.malai.interaction.InitState;
import org.malai.interaction.Interaction;
import org.malai.stateMachine.ITransition;

import wiiusej.wiiusejevents.physicalevents.ButtonsEvent;

public class WiimoteInteraction extends Interaction implements WiimoteEventHandler {

	public WiimoteInteraction() {
		super();
	}
	
	public WiimoteInteraction(final InitState initState) {
		super(initState);
	}
	
	public void onButtonPressed(ButtonsEvent button) {
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
