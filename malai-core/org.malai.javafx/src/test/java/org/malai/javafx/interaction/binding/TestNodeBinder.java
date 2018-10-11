package org.malai.javafx.interaction.binding;

import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

abstract class TestNodeBinder<W extends Node> extends TestBinder<W> {
	public void start(final Stage stage) {
		instrument = new StubInstrument();
		instrument.setActivated(true);
		final VBox parent = new VBox();
		parent.getChildren().add(widget1);
		parent.getChildren().add(widget2);
		final Scene scene = new Scene(parent);
		stage.setScene(scene);
		stage.show();
		stage.toFront();
		stage.centerOnScreen();
		stage.requestFocus();
	}
}
