module malai.core {
	requires java.logging;
	requires java.desktop;

	exports org.malai.binding;
	exports org.malai.command;
	exports org.malai.command.library;
	exports org.malai.error;
	exports org.malai.fsm;
	exports org.malai.instrument;
	exports org.malai.interaction;
	exports org.malai.logging;
	exports org.malai.properties;
	exports org.malai.undo;
	exports org.malai.utils;
}