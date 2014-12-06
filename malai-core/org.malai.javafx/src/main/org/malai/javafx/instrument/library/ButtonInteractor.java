package org.malai.javafx.instrument.library;

import java.util.List;

import javafx.scene.Node;

import org.malai.action.Action;
import org.malai.javafx.instrument.JfxInstrument;
import org.malai.javafx.instrument.JfxInteractor;
import org.malai.javafx.interaction.library.ButtonPressed;

public abstract class ButtonInteractor<A extends Action, I extends JfxInstrument> extends JfxInteractor<A, ButtonPressed, I> {

	public ButtonInteractor(I ins, boolean exec, Class<A> clazzAction, List<Node> widgets) throws InstantiationException, IllegalAccessException {
		super(ins, exec, clazzAction, ButtonPressed.class, widgets);
	}
}
