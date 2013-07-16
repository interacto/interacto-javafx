package org.malai.wiimote.interaction.library;

import org.malai.interaction.IntermediaryState;
import org.malai.interaction.TerminalState;
import org.malai.wiimote.interaction.ButtonPressedTransition;
import org.malai.wiimote.interaction.IRTransition;
import org.malai.wiimote.interaction.WiimoteEventManager;
import org.malai.wiimote.interaction.WiimoteInteraction;

import wiiusej.wiiusejevents.physicalevents.IREvent;
import wiiusej.wiiusejevents.physicalevents.WiimoteButtonsEvent;


/**
 * Activate the IR tracking after pushing the B-button. Once the B-button
 * is release, the tracking stops.
 * 
 * @author Maxime Lorant
 *
 */
public class IRPointing extends WiimoteInteraction {
	
	/** IR tracking information */
	protected IREvent ir;

	/** Creates the interaction.*/
	public IRPointing() {
		super();
		initStateMachine();
	}


	@Override
	public void reinit() {
		super.reinit();
		ir = null;
	}


	@Override
	protected void initStateMachine() {
		final IntermediaryState start = new IntermediaryState("start");
		final TerminalState stop = new TerminalState("stop");

		addState(start);
		addState(stop);
		
		
		// Begin interaction by pushing B-button
		new ButtonPressedTransition(initState, start) {
			public void action() {
				super.action();	
				
				// Enable IR tracking
				WiimoteEventManager.getInstance().enableIREvent();
				
			}
		
			public boolean isGuardRespected() {
				if(this.button instanceof WiimoteButtonsEvent) {
					WiimoteButtonsEvent b = (WiimoteButtonsEvent) this.button;
					return b.isButtonBJustPressed();
				}
				return false;
			}
		};
		
		
		// Send every data
		new IRTransition(start, start) {
			public void action() {
				super.action();	
				IRPointing.this.ir = this.ir;
			}
		};
		
		
		// Stop interaction
		new ButtonPressedTransition(start, stop) {
			public void action() {
				super.action();	
				
				// Disable IR tracking
				WiimoteEventManager.getInstance().disableIREvent();
			}
			
			public boolean isGuardRespected() {
				if(this.button instanceof WiimoteButtonsEvent) {
					WiimoteButtonsEvent b = (WiimoteButtonsEvent) this.button;
					return b.isButtonBJustReleased();
				}
				return false;
			}
		};
	}


	/**
	 * @return IR tracking data
	 * @since 0.2
	 */
	public IREvent getIR() {
		return ir;
	}
}
