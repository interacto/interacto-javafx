package org.malai.ex.draw;

import org.malai.action.ActionsRegistry;
import org.malai.ex.draw.ui.EditorFrame;
import org.malai.undo.UndoCollector;

/*
 * The main class of the application.
 */
public class Editor {

	static {
		/*
		 * One of the first thing to do is to define the 
		 * number of undoable actions that can be stored.
		 * When the threshold is reached, the oldest stored
		 * action is flushed.
		 */
		UndoCollector.INSTANCE.setSizeMax(30);
		
		/*
		 * In the same way, the number of actions that can
		 * be kept in memory should be defined.
		 * This step is different from the undo process.
		 * An action may need another action to run. So,
		 * this registry stores the recent executed actions.
		 * When the threshold is reached, the oldest stored
		 * action is flushed. 
		 */
		ActionsRegistry.INSTANCE.setSizeMax(30);
	}
	
	public static void main(String[] args) {
		/* Creation of the main GUI of the application. */
		EditorFrame frame = new EditorFrame();
		frame.setVisible(true);
		frame.setLocation(100, 100);
		frame.setSize(800, 600);
	}
}
