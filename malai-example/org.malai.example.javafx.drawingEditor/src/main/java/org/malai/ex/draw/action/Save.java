package org.malai.ex.draw.action;

import org.malai.javafx.action.ProgressableActionImpl;

/**
 * A fake saving action to show how an action that longs can be handled.
 */
public class Save extends ProgressableActionImpl {
	private boolean ok;

	@Override
	protected void doActionBody() {
		textProgress.set("Saving...");
		try {
			for(int i = 0; i < 10; i++) {
				Thread.sleep(1000L);
				progress.set(progress.getValue() + 10d);
				textProgress.set("Saving step #" + i);
			}
			ok = true;
		}catch(InterruptedException e) {
			ok = false;
		}
	}

	@Override
	public boolean hadEffect() {
		return ok;
	}

	@Override
	public boolean canDo() {
		return true;
	}
}
