package org.malai.wiimote.interaction.library;

import org.malai.interaction.IntermediaryState;
import org.malai.interaction.TerminalState;
import org.malai.wiimote.interaction.ButtonPressedTransition;
import org.malai.wiimote.interaction.MotionSensingTransition;
import org.malai.wiimote.interaction.WiimoteEventManager;
import org.malai.wiimote.interaction.WiimoteInteraction;

import wiiusej.wiiusejevents.physicalevents.MotionSensingEvent;
import wiiusej.wiiusejevents.physicalevents.WiimoteButtonsEvent;


/**
 * Activate the motion sensing tracking after pushing the B-button. 
 * Once the B-button is release, the tracking stops.
 * 
 * @author Maxime Lorant
 */
public class WiimoteMovement extends WiimoteInteraction {
	
	/** Motion sensing data */
	protected MotionSensingEvent motion;

	/** Creates the interaction. */
	public WiimoteMovement() {
		super();
		initStateMachine();
	}


	@Override
	public void reinit() {
		super.reinit();
		motion = null;
	}


	@Override
	protected void initStateMachine() {
		final IntermediaryState start = new IntermediaryState("start");
		final TerminalState stop = new TerminalState("stop");

		addState(start);
		addState(stop);
		
		
		// Begin interaction, by pressing B-button
		new ButtonPressedTransition(initState, start) {
			public void action() {
				super.action();	
				WiimoteEventManager.getInstance().enableMotionEvent();
			}
		
			public boolean isGuardRespected() {
				// TODO: Parameterize button (should be defined by the user)
				if(this.button instanceof WiimoteButtonsEvent) {
					WiimoteButtonsEvent b = (WiimoteButtonsEvent) this.button;
					return b.isButtonBJustPressed();
				}
				return false;
			}
		};
		
		
		// Send every data
		new MotionSensingTransition(start, start) {
			public void action() {
				super.action();	
				WiimoteMovement.this.motion = this.motion;
			}
		};
		
		
		// Stop interaction, when B button is released
		new ButtonPressedTransition(start, stop) {
			public void action() {
				super.action();	
				WiimoteEventManager.getInstance().disableMotionEvent();
			}
			
			public boolean isGuardRespected() {
				// TODO: Parameterize button (should be defined by the user)
				if(this.button instanceof WiimoteButtonsEvent) {
					WiimoteButtonsEvent b = (WiimoteButtonsEvent) this.button;
					return b.isButtonBJustReleased();
				}
				return false;
			}
		};
	}


	/**
	 * @return Motion sensing data.
	 * @since 0.2
	 */
	public MotionSensingEvent getMotion() {
		return motion;
	}
}
