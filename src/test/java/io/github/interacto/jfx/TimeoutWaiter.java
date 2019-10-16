package io.github.interacto.jfx;

import io.github.interacto.fsm.TimeoutTransition;
import org.testfx.util.WaitForAsyncUtils;

public interface TimeoutWaiter {
	default void waitForTimeoutTransitions() {
		Thread.getAllStackTraces()
			.keySet()
			.stream()
			.filter(thread -> thread.getName().startsWith(TimeoutTransition.TIMEOUT_THREAD_NAME_BASE))
			.forEach(thread -> {
				try {
					thread.join();
				}catch(final InterruptedException ex) {
					Thread.currentThread().interrupt();
				}
			});
		WaitForAsyncUtils.waitForFxEvents();
	}
}
