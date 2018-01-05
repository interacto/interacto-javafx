/*
 * This file is part of Malai.
 * Copyright (c) 2005-2017 Arnaud BLOUIN
 * Malai is free software; you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation; either version 2 of the License, or (at your option) any later version.
 * Malai is distributed without any warranty; without even the implied
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * General Public License for more details.
 */
package org.malai.javafx.interaction.help;

import javafx.animation.Animation;
import javafx.animation.Transition;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;

public abstract class HelpAnimationImpl implements HelpAnimation {
	protected Transition transition;
	protected boolean installed;
	protected final Pane helpPane;
	protected final Pane zonePane;
	private EventHandler<MouseEvent> handlerStop;


	public HelpAnimationImpl(final Pane learningPane, final Pane zonePane) {
		super();
		installed = false;
		helpPane = learningPane;
		this.zonePane = zonePane;
	}

	protected void install() {
		if(!installed) {
			transition = createTransition();
			installed = true;

			if(handlerStop == null) {
				handlerStop = evt -> HelpAnimationPlayer.INSTANCE.stop(this);
			}

			transition.statusProperty().addListener((observable, oldValue, newValue) -> {
				if(newValue == Animation.Status.STOPPED) {
					zonePane.removeEventHandler(MouseEvent.MOUSE_MOVED, handlerStop);
				}
				if(newValue == Animation.Status.RUNNING) {
					zonePane.addEventHandler(MouseEvent.MOUSE_MOVED, handlerStop);
				}
			});
		}
	}

	@Override
	public Transition getAnimation() {
		if(!installed) {
			install();
		}
		return transition;
	}

	@Override
	public boolean isInstalled() {
		return installed;
	}

	protected abstract Transition createTransition();

	@Override
	public Pane getZonePane() {
		return zonePane;
	}

	@Override
	public Pane getHelpPane() {
		return helpPane;
	}
}
