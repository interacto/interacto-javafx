/*
 * This file is part of Malai.
 * Copyright (c) 2009-2018 Arnaud BLOUIN
 * Malai is free software; you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation; either version 2 of the License, or (at your option) any later version.
 * Malai is distributed without any warranty; without even the implied
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * General Public License for more details.
 */
package org.malai.javafx.interaction.help;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.ParallelTransition;
import javafx.animation.PauseTransition;
import javafx.animation.SequentialTransition;
import javafx.animation.Timeline;
import javafx.animation.Transition;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Ellipse;
import javafx.scene.text.Text;
import javafx.util.Duration;

public class DnDHelpAnimation extends HelpAnimationImpl {
	protected final Duration duration = Duration.millis(2500);
	protected final double size = 15;
	protected Ellipse ell;
	protected Text text;
	protected final double x1;
	protected final double x2;
	protected final double y1;
	protected final double y2;
	protected final String textPress;
	protected final String textDrag;
	protected final String textRelease;

	public DnDHelpAnimation(final Pane learningPane, final Pane widget) {
		this(learningPane, widget, 150, 500, 150, 350, "Press", "Drag", "Release");
	}

	public DnDHelpAnimation(final Pane learningPane, final Pane widget, final double x1, final double x2, final double y1, final double y2,
							final String textPress, final String textDrag, final String textRelease) {
		super(learningPane, widget);
		this.x1 = x1;
		this.x2 = x2;
		this.y1 = y1;
		this.y2 = y2;
		this.textPress = textPress;
		this.textDrag = textDrag;
		this.textRelease = textRelease;
	}

	@Override
	public Transition createTransition() {
		ell = new Ellipse(x1, y1, size, size);
		text = new Text(textPress);

		text.xProperty().bind(ell.centerXProperty().add(ell.getRadiusX()));
		text.yProperty().bind(ell.centerYProperty().subtract(ell.getRadiusY()));
		ell.setFill(Color.LIGHTGRAY);
		ell.setEffect(new DropShadow(20d, Color.BLACK));

		helpPane.getChildren().add(ell);
		helpPane.getChildren().add(text);
		ell.setVisible(false);
		text.setVisible(false);
		ell.setFocusTraversable(false);
		ell.setMouseTransparent(true);
		text.setFocusTraversable(false);
		text.setMouseTransparent(true);

		final SequentialTransition mainTrans = new SequentialTransition();
		final ParallelTransition parallelTransition = new ParallelTransition(
			new Timeline(new KeyFrame(duration, new KeyValue(ell.centerXProperty(), x2))),
			new Timeline(new KeyFrame(duration, new KeyValue(ell.centerYProperty(), y2)))
		);

		mainTrans.getChildren().add(new Timeline(new KeyFrame(Duration.millis(10), new KeyValue(text.visibleProperty(), Boolean.TRUE))));
		mainTrans.getChildren().add(new Timeline(new KeyFrame(Duration.millis(10), new KeyValue(ell.visibleProperty(), Boolean.TRUE))));
		mainTrans.getChildren().add(new PauseTransition(Duration.seconds(1.5)));
		mainTrans.getChildren().add(new ParallelTransition(
			new Timeline(new KeyFrame(Duration.millis(400d), new KeyValue(ell.radiusXProperty(), size / 2d))),
			new Timeline(new KeyFrame(Duration.millis(400d), new KeyValue(ell.radiusYProperty(), size / 2d)))
		));
		mainTrans.getChildren().add(new Timeline(new KeyFrame(Duration.millis(100d), new KeyValue(text.textProperty(), textDrag))));
		mainTrans.getChildren().add(parallelTransition);
		mainTrans.getChildren().add(new Timeline(new KeyFrame(Duration.millis(100d), new KeyValue(text.textProperty(), textRelease))));
		mainTrans.getChildren().add(new ParallelTransition(
			new Timeline(new KeyFrame(Duration.millis(400d), new KeyValue(ell.radiusXProperty(), size))),
			new Timeline(new KeyFrame(Duration.millis(400d), new KeyValue(ell.radiusYProperty(), size)))
		));
		mainTrans.getChildren().add(new PauseTransition(Duration.seconds(1.5)));
		mainTrans.getChildren().add(new Timeline(new KeyFrame(Duration.millis(10), new KeyValue(text.visibleProperty(), Boolean.FALSE))));
		mainTrans.getChildren().add(new Timeline(new KeyFrame(Duration.millis(10), new KeyValue(ell.visibleProperty(), Boolean.FALSE))));
		transition = mainTrans;
		return transition;
	}
}
