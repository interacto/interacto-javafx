package org.malai.javafx.interaction.library;

import java.util.List;
import java.util.Optional;

import javafx.scene.Node;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

import org.malai.javafx.interaction.JfxInteractionImpl;
import org.malai.javafx.interaction.KeyPressureTransition;
import org.malai.stateMachine.SourceableState;
import org.malai.stateMachine.TargetableState;

/**
 * This abstract interaction should be used to define JavaFX interactions based on keyboards.<br>
 * <br>
 * This file is part of Malai.<br>
 * Copyright (c) 2005-2014 Arnaud BLOUIN<br>
 * <br>
 * Malai is free software; you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation; either version 2 of the License, or (at your option) any later version.
 * <br>
 * Malai is distributed without any warranty; without even the implied
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * General Public License for more details.<br>
 * <br>
 * 2014-09-22<br>
 * @author Arnaud BLOUIN
 */
public abstract class KeyInteraction extends JfxInteractionImpl {
	/** The key pressed. */
	protected Optional<String> key;

	/** The object that produced the interaction. */
	protected Optional<Object> object;
	
	/** The code of the key.*/
	protected Optional<KeyCode> keyCode;


	/**
	 * Creates the interaction.
	 */
	public KeyInteraction() {
		super();
	}


	@Override
	public void reinit() {
		super.reinit();
		key = Optional.empty();
		object = Optional.empty();
		keyCode = Optional.empty();
	}


	/**
	 * @return The object that produced the interaction.
	 */
	public Optional<Object> getObject() {
		return object;
	}

	/**
	 * @return The key code used by the interaction.
	 */
	public Optional<KeyCode> getKeyCode() {
		return keyCode;
	}

	/**
	 * @return The key used by the interaction.
	 */
	public Optional<String> getKey() {
		return key;
	}


	/**
	 * @param key The key pressed.
	 */
	protected void setKey(final String key) {
		this.key = Optional.ofNullable(key);
	}
	
	/**
	 * @param key The key pressed.
	 */
	protected void setKeyCode(final KeyCode keycode) {
		this.keyCode = Optional.ofNullable(keycode);
	}


	/**
	 * @param object The object that produced the interaction.
	 */
	protected void setObject(final Object object) {
		this.object = Optional.ofNullable(object);
	}


	/**
	 * Defines a transition modifying the key attribute of the interaction.
	 */
	public class KeyInteractionKeyPressedTransition extends KeyPressureTransition {
		/**
		 * Creates the transition.
		 * @param inputState The source state of the transition.
		 * @param outputState The target state of the transition.
		 */
		public KeyInteractionKeyPressedTransition(final SourceableState inputState, final TargetableState outputState) {
			super(inputState, outputState);
		}

		@Override
		public void action() {
			KeyInteraction.this.setObject(event.getSource());
			KeyInteraction.this.setKey(event.getCharacter());
			KeyInteraction.this.setKeyCode(event.getCode());
			KeyInteraction.this.setLastHIDUsed(this.hid);
		}
	}
	
	@Override
	public void registerToWidgets(List<Node> widgets) {
		widgets.stream().forEach(w -> {
			w.addEventHandler(KeyEvent.KEY_PRESSED, evt -> onKeyPressure(evt, 0));
			w.addEventHandler(KeyEvent.KEY_RELEASED, evt -> onKeyRelease(evt, 0));
		});
	}
}
