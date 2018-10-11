module malai.example.drawingEditor {
	requires malai.core;
	requires malai.javafx;
	requires javafx.base;
	requires javafx.fxml;
	requires javafx.controls;

	exports org.malai.ex.draw to javafx.graphics;
	exports org.malai.ex.draw.instrument to javafx.fxml;
	exports org.malai.ex.draw.view to javafx.fxml;

	opens org.malai.ex.draw.instrument to javafx.fxml;
	opens org.malai.ex.draw.command to malai.javafx;
}