package org.malai.ex.draw.command;

import org.malai.javafx.command.ProgressableCmdImpl;

/**
 * A fake saving command to show how a command that longs can be handled.
 */
public class Save extends ProgressableCmdImpl {
	private boolean ok;

	@Override
	protected void doCmdBody() {
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
