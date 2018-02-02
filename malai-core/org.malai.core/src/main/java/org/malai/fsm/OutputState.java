package org.malai.fsm;

import java.util.List;

public interface OutputState<E> extends State<E> {
	void exit() throws CancelFSMException;

	/**
	 * Asks to the state to process of the given event.
	 * @param event The event to process. Can be null.
	 */
	default void process(final E event) {
		for(final Transition<E> tr : getTransitions()) {
			try {
				if(tr.execute(event).isPresent()) {
					return;
				}
			}catch(final CancelFSMException ignored) {
				// Already processed
			}
		}
	}

	List<Transition<E>> getTransitions();

	void addTransition(final Transition<E> tr);
}
