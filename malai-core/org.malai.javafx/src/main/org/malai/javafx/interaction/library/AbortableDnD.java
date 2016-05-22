package org.malai.javafx.interaction.library;

import java.util.List;

import javafx.scene.Node;

import org.malai.interaction.AbortingState;
import org.malai.javafx.interaction.EscapeKeyPressureTransition;
import org.malai.javafx.interaction.ReleaseTransition;

public class AbortableDnD extends DnD {
	/**
	 * Creates the interaction.
	 */
	public AbortableDnD() {
		super();
	}
	
	@SuppressWarnings("unused")
	@Override
	protected void initStateMachine() {
		super.initStateMachine();

		AbortingState aborted = new AbortingState("aborted"); //$NON-NLS-1$
		addState(aborted);

		new EscapeKeyPressureTransition(pressed, aborted);
		new EscapeKeyPressureTransition(dragged, aborted);
		pressed.getTransitions().stream().filter(t -> t instanceof ReleaseTransition).findFirst().ifPresent(t -> pressed.getTransitions().remove(t));
		new Release4DnD(pressed, aborted);
	}

	
	@Override
	public void registerToNodes(List<Node> widgets) {
		super.registerToNodes(widgets);
		widgets.forEach(widget -> widget.setOnKeyReleased(evt -> onKeyRelease(evt, 0)));
	}
}
