package org.malai.javafx;

import org.malai.fsm.TimeoutTransition;
import org.testfx.util.WaitForAsyncUtils;

public final class JfxtestHelper {
	private JfxtestHelper() {
		super();
	}

	public static void waitForTimeoutTransitions() {
		Thread.getAllStackTraces().keySet()
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
