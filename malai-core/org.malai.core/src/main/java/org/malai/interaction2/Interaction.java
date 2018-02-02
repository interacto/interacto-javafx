package org.malai.interaction2;

import java.util.logging.Level;
import java.util.logging.Logger;
import org.malai.fsm.FSM;

public abstract class Interaction<E, F extends FSM<E>> {
	protected Logger logger;

	protected final F fsm;
	/**
	 * Defines if the interaction is activated or not. If not, the interaction will not
	 * change on events.
	 */
	protected boolean activated;

	protected Interaction(final F fsm) {
		super();

		if(fsm == null) {
			throw new IllegalArgumentException("null fsm");
		}

		this.fsm = fsm;
		activated = true;
	}

	public void processEvent(final E event) {
		if(isActivated()) {
			fsm.process(event);
		}
	}

	public void log(final boolean log) {
		if(log) {
			if(logger == null) {
				logger = Logger.getLogger(getClass().getName());
			}
		}else {
			logger = null;
		}

		fsm.log(log);
	}

	public boolean isActivated() {
		return activated;
	}

	public void setActivated(final boolean activated) {
		if(logger != null) {
			logger.log(Level.INFO, "Interaction activation: " + activated);
		}

		this.activated = activated;

		if(!activated) {
			fsm.fullReinit();
		}
	}

	public FSM<E> getFsm() {
		return fsm;
	}

	protected void reinit() {
		fsm.reinit();
		reinitData();
	}

	protected abstract void reinitData();

	public void processCheckBoxData(final Object checkbox) {
	}

	public void processButtonData(final Object button) {
	}

	public void processToggleButtonData(final Object button) {
	}

	public void processColorPickerData(final Object picker) {
	}

	public void processComboBoxData(final Object box) {
	}

	public void processChoiceBoxData(final Object box) {
	}

	public void processDatePickerData(final Object picker) {
	}

	public void processHyperlinkData(final Object hlink) {
	}

	public void processMenuButtonData(final Object menu) {
	}

	public void processMenuItemData(final Object menu) {
	}

	public void processSpinnerData(final Object spinner) {
	}

	public void processTabData(final Object tab) {
	}

	public void processTextInputData(final Object textInputWidget) {
	}

	public void processWindowData(final Object window) {
	}
}
