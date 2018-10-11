module malai.javafx {
	requires javafx.base;
	requires javafx.controls;
	requires java.logging;
	requires java.xml;
	requires malai.core;

	exports org.malai.javafx.binding;
	exports org.malai.javafx.command;
	exports org.malai.javafx.instrument;
	exports org.malai.javafx.interaction;
	exports org.malai.javafx.interaction.help;
	exports org.malai.javafx.interaction.library;
	exports org.malai.javafx.ui;
	exports org.malai.javafx.undo;
}