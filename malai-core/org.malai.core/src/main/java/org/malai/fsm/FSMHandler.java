package org.malai.fsm;

public interface FSMHandler {
	/**
	 * When the FSM starts.
	 * @throws CancelFSMException If the FSM must be cancelled.
	 */
	void fsmStarts() throws CancelFSMException;

	/**
	 * When the FSM runs to new state.
	 * @throws CancelFSMException If the FSM must be cancelled.
	 */
	void fsmUpdates() throws CancelFSMException;

	/**
	 * When the FSM enters a terminal state.
	 * @throws CancelFSMException If the FSM must be cancelled.
	 */
	void fsmStops() throws CancelFSMException;

	/**
	 * When the interaction enters a cancelling state.
	 */
	void fsmCancels();
}
