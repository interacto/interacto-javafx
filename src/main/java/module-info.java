module interacto.javafx {
	requires javafx.base;
	requires javafx.controls;
	requires java.logging;
	requires java.xml;
	requires interacto.java.api;

	exports io.github.interacto.jfx.binding;
	exports io.github.interacto.jfx.command;
	exports io.github.interacto.jfx.instrument;
	exports io.github.interacto.jfx.interaction;
	exports io.github.interacto.jfx.interaction.help;
	exports io.github.interacto.jfx.interaction.library;
	exports io.github.interacto.jfx.ui;
	exports io.github.interacto.jfx.undo;
}