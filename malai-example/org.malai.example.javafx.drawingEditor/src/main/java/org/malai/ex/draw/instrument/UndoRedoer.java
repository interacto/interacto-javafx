package org.malai.ex.draw.instrument;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.binding.Bindings;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Tooltip;
import org.malai.command.library.Redo;
import org.malai.command.library.Undo;
import org.malai.javafx.instrument.JfxInstrument;
import org.malai.javafx.undo.FXUndoCollector;
import org.malai.undo.UndoCollector;

/**
 * Manages undos and redos.
 */
public class UndoRedoer extends JfxInstrument implements Initializable {
	/** The button used to undo commands. */
	@FXML private Button undoB;
	/** The button used to redo commands. */
	@FXML private Button redoB;

	@Override
	public void initialize(final URL location, final ResourceBundle resources) {
		setActivated(true);
		UndoCollector.INSTANCE.addHandler(this);

		undoB.disableProperty().bind(FXUndoCollector.INSTANCE.lastUndoProperty().isNull().or(activatedProp.not()));
		redoB.disableProperty().bind(FXUndoCollector.INSTANCE.lastRedoProperty().isNull().or(activatedProp.not()));

		undoB.tooltipProperty().bind(Bindings.createObjectBinding(() ->
			UndoCollector.INSTANCE.getLastUndo().map(undo -> new Tooltip(undo.getUndoName())).orElse(null), FXUndoCollector.INSTANCE.lastUndoProperty()));
		redoB.tooltipProperty().bind(Bindings.createObjectBinding(() ->
			UndoCollector.INSTANCE.getLastRedo().map(redo -> new Tooltip(redo.getUndoName())).orElse(null), FXUndoCollector.INSTANCE.lastRedoProperty()));
	}

	@Override
	protected void configureBindings() {
		// Undo and Redo are commands provided by Malai.
		buttonBinder(Undo::new).on(undoB).bind();
		buttonBinder(Redo::new).on(redoB).bind();
	}
}
