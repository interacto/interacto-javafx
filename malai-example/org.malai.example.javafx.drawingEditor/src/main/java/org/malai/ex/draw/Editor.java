package org.malai.ex.draw;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import org.malai.command.CommandsRegistry;
import org.malai.undo.UndoCollector;

/*
 * The main GUI of the application.
 */
public class Editor extends Application {

	static {
		/*
		 * One of the first thing to do is to define the
		 * number of undoable commands that can be stored.
		 * When the threshold is reached, the oldest stored
		 * command is flushed.
		 */
		UndoCollector.INSTANCE.setSizeMax(30);

		/*
		 * In the same way, the number of commands that can
		 * be kept in memory should be defined.
		 * This step is different from the undo process.
		 * A command may need another command to run. So,
		 * this registry stores the recent executed commands.
		 * When the threshold is reached, the oldest stored
		 * command is flushed.
		 */
		CommandsRegistry.INSTANCE.setSizeMax(30);
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
