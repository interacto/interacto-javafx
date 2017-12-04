package org.malai.ex.draw.instrument;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import org.malai.action.library.Redo;
import org.malai.action.library.Undo;
import org.malai.javafx.instrument.JfxInstrument;
import org.malai.javafx.undo.FXUndoCollector;
import org.malai.undo.UndoCollector;

/**
 * Manages undos and redos.
 */
public class UndoRedoer extends JfxInstrument implements Initializable {
	/** The button used to undo actions. */
	@FXML private Button undoB;
	/** The button used to redo actions. */
	@FXML private Button redoB;

	@Override
	public void initialize(final URL location, final ResourceBundle resources) {
		setActivated(true);
		UndoCollector.INSTANCE.addHandler(this);

		undoB.disableProperty().bind(FXUndoCollector.INSTANCE.lastUndoProperty().isNull().or(activatedProp.not()));
		redoB.disableProperty().bind(FXUndoCollector.INSTANCE.lastRedoProperty().isNull().or(activatedProp.not()));
	}

	@Override
	protected void configureBindings() throws InstantiationException, IllegalAccessException {
		// Undo and Redo are actions provided by Malai.
		buttonBinder(Undo.class).on(undoB).when(i -> UndoCollector.INSTANCE.getLastUndo() != null).bind();
		buttonBinder(Redo.class).on(redoB).when(i -> UndoCollector.INSTANCE.getLastRedo() != null).bind();
	}
}
