package org.malai.action.library;

import org.malai.action.ActionImpl;

/**
 * An anonymous class that permits to create an action without defining a specific ActionImpl class.
 * A runnable, corresponding to the body of the action has to be provided to the action.
 * Created by Arnaud Blouin on 18/07/16.
 */
public class AnonymousAction extends ActionImpl {
	/** The runnable executed when the action is executed. */
	protected Runnable actionBody;

	/** Create the action. */
	public AnonymousAction() {
		super();
	}

	@Override
	public boolean isRegisterable() {
		return false;
	}

	@Override
	protected void doActionBody() {
		actionBody.run();
	}

	@Override
	public boolean canDo() {
		return actionBody!=null;
	}

	/**
	 * Sets the runnable of the action.
	 * @param body The runnable executed when the action is executed.
	 */
	public void setActionBody(final Runnable body) {
		actionBody = body;
	}

	/**
	 * @return The runnable of the action.
	 */
	public Runnable getActionBody() {
		return actionBody;
	}
}
