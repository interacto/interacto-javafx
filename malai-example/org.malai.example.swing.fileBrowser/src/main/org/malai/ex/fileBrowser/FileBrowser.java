package org.malai.ex.fileBrowser;

import java.io.IOException;

import org.malai.action.ActionsRegistry;
import org.malai.ex.fileBrowser.ui.FileBrowserFrame;
import org.malai.undo.UndoCollector;

public class FileBrowser {
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
	
	
	public static void main(String[] args) throws IOException {
		/* Creation of the main GUI of the application. */
		FileBrowserFrame frame = new FileBrowserFrame();
		
		// Each UI can have a GUI composer.
		// The role of a GUI composer is to compose the user interface
		// using the different graphical components provided by
		// the presentations and the instruments.
		frame.getComposer().compose(null);
		
		frame.setVisible(true);
		frame.setLocation(200, 150);
		frame.setSize(1000, 600);
	}
}
