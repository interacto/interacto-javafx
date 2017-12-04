package org.malai.ex.draw;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import org.malai.action.ActionsRegistry;
import org.malai.undo.UndoCollector;

/*
 * The main GUI of the application.
 * Each GUI of the application should inherit
 * of the Malai class UI that represents a GUI.
 * A Malai UI is a JFrame.
 */
public class Editor extends Application {

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

	public static void main(final String[] args) {
		launch(args);
	}


	public Editor() {
		super();
	}

	@Override
	public void start(final Stage primaryStage) {
		try {
			final BorderPane root = FXMLLoader.load(getClass().getResource("/fxml/UI.fxml"));
			final Scene scene = new Scene(root);
			primaryStage.setScene(scene);
			primaryStage.show();
		}catch(final Exception ex) {
			ex.printStackTrace();
		}
	}
}
