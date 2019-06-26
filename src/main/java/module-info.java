module interacto.javafx {
	requires javafx.base;
	requires javafx.controls;
	requires java.logging;
	requires java.xml;
	requires interacto.java.api;

	exports io.interacto.jfx.binding;
	exports io.interacto.jfx.command;
	exports io.interacto.jfx.instrument;
	exports io.interacto.jfx.interaction;
	exports io.interacto.jfx.interaction.help;
	exports io.interacto.jfx.interaction.library;
	exports io.interacto.jfx.ui;
	exports io.interacto.jfx.undo;
}