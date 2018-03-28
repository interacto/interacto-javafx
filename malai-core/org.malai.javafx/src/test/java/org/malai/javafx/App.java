package org.malai.javafx;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class App extends Application {
	Canvas canvas = new Canvas();

	public static void main(final String[] args) {
		launch(args);
	}

	@Override
	public void start(final Stage primaryStage) {
		canvas.setWidth(1200);
		canvas.setHeight(800);
		final VBox parent = new VBox();
		parent.getChildren().add(canvas);
		final Scene scene = new Scene(parent);
		primaryStage.setScene(scene);
		primaryStage.show();
		primaryStage.toFront();
	}
}
