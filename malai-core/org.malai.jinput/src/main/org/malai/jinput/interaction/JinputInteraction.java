package org.malai.jinput.interaction;

import org.malai.interaction.InitState;
import org.malai.interaction.Interaction;
import org.malai.stateMachine.ITransition;

import net.java.games.input.Component;

public class JinputInteraction extends Interaction implements JinputEventHandler {
	public JinputInteraction() {
		super();
	}

	
	public JinputInteraction(final InitState initState) {
		super(initState);
	}


	@Override
	public void onButtonPressed(final Component.Identifier.Button button) {
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
