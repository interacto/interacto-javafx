package org.malai.javafx.instrument.library;

import java.util.List;

import javafx.scene.Node;

import org.malai.action.Action;
import org.malai.javafx.instrument.JfxInstrument;
import org.malai.javafx.instrument.JfxInteractor;
import org.malai.javafx.interaction.library.ToggleButtonPressed;

public abstract class ToggleButtonInteractor<A extends Action, I extends JfxInstrument> extends JfxInteractor<A, ToggleButtonPressed, I> {

	public ToggleButtonInteractor(I ins, boolean exec, Class<A> clazzAction, List<Node> widgets) throws InstantiationException, IllegalAccessException {
		super(ins, exec, clazzAction, ToggleButtonPressed.class, widgets);
	}
}
