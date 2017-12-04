package org.malai.ex.draw.instrument;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Tooltip;
import org.malai.action.library.Redo;
import org.malai.action.library.Undo;
import org.malai.javafx.instrument.JfxInstrument;
import org.malai.undo.UndoCollector;
import org.malai.undo.Undoable;

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
		updateWidgets();
	}

	@Override
	protected void configureBindings() throws InstantiationException, IllegalAccessException {
		// Undo and Redo are actions provided by Malai.
		buttonBinder(Undo.class).on(undoB).when(i -> UndoCollector.INSTANCE.getLastUndo() != null).bind();
		buttonBinder(Redo.class).on(redoB).when(i -> UndoCollector.INSTANCE.getLastRedo() != null).bind();
	}

	public void updateWidgets() {
		if(isActivated()) {
			final Undoable undo = UndoCollector.INSTANCE.getLastUndo();
			final Undoable redo = UndoCollector.INSTANCE.getLastRedo();

			undoB.setDisable(undo == null);
			redoB.setDisable(redo == null);
			undoB.setTooltip(undo == null ? null : new Tooltip(undo.getUndoName()));
			redoB.setTooltip(redo == null ? null : new Tooltip(redo.getUndoName()));
		}
	}

	@Override
	public void onUndoableCleared() {
		updateWidgets();
	}


	@Override
	public void onUndoableAdded(final Undoable undoable) {
		updateWidgets();
	}


	@Override
	public void onUndoableUndo(final Undoable undoable) {
		updateWidgets();
	}


	@Override
	public void onUndoableRedo(final Undoable undoable) {
		updateWidgets();
	}
}
