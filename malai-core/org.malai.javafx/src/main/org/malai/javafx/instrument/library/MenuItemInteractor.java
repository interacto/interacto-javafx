package org.malai.javafx.instrument.library;

import javafx.scene.Node;
import org.malai.action.Action;
import org.malai.javafx.instrument.JfxInstrument;
import org.malai.javafx.instrument.JfxInteractor;
import org.malai.javafx.interaction.library.MenuItemPressed;

import java.util.List;

public abstract class MenuItemInteractor<A extends Action, I extends JfxInstrument> extends JfxInteractor<A, MenuItemPressed, I> {
	/**
	 * Creates an interactor.
	 * @param ins The instrument that contains the interactor.
	 * @param clazzAction The type of the action that will be created. Used to instantiate the action by reflexivity.
	 * The class must be public and must have a constructor with no parameter.
	 * @param widgets The widgets used by the interactor. Cannot be null.
	 * @throws IllegalAccessException If no free-parameter constructor is available.
	 * @throws InstantiationException If an error occurs during instantiation of the interaction/action.
	 * @throws IllegalArgumentException If the given interaction or instrument is null.
	 */
	public MenuItemInteractor(I ins, Class<A> clazzAction, List<Node> widgets) throws InstantiationException, IllegalAccessException {
		super(ins, false, clazzAction, MenuItemPressed.class, widgets);
	}

	/**
	 * Creates an interactor.
	 * @param ins The instrument that contains the interactor.
	 * @param clazzAction The type of the action that will be created. Used to instantiate the action by reflexivity.
	 * The class must be public and must have a constructor with no parameter.
	 * @param widgets The widgets used by the interactor. Cannot be null.
	 * @throws IllegalAccessException If no free-parameter constructor is available.
	 * @throws InstantiationException If an error occurs during instantiation of the interaction/action.
	 * @throws IllegalArgumentException If the given interaction or instrument is null.
	 */
	public MenuItemInteractor(I ins, Class<A> clazzAction, Node... widgets) throws InstantiationException, IllegalAccessException {
		super(ins, false, clazzAction, MenuItemPressed.class, widgets);
	}
}
