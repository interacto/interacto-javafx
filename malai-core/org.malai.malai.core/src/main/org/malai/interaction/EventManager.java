package org.malai.interaction;

public interface EventManager {
	/**
	 * Adds a handler to the event manager.
	 * @param h The handler to add. Must not be null.
	 * @since 0.1
	 */
	void addHandlers(final EventHandler h);

	/**
	 * Removes a handler from the event manager.
	 * @param h The handler to remove. Must not be null.
	 * @since 0.1
	 */
	void removeHandler(final EventHandler h);
}
