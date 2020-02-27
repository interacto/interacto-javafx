/*
 * Interacto
 * Copyright (C) 2020 Arnaud Blouin
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * You should have received a copy of the GNU General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 */
package io.github.interacto.jfx.interaction.help;

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
