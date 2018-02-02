package org.malai.javafx.interaction2;

import javafx.event.Event;
import org.malai.fsm.FSM;

public abstract class JfxFSM<W> extends FSM<Event> {
	protected JfxInteraction<?, W> interaction;

	protected void buildFSM(final JfxInteraction<?, W> interaction) {
		this.interaction = interaction;
	}

	@Override
	public void reinit() {
		super.reinit();
		if(interaction != null) {
			interaction.reinitData();
		}
	}
}
