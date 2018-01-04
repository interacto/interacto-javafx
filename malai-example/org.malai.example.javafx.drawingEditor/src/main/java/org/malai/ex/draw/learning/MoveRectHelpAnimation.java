package org.malai.ex.draw.learning;

import javafx.animation.Transition;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import org.malai.javafx.interaction.help.CancelDnDHelpAnimation;

public class MoveRectHelpAnimation extends CancelDnDHelpAnimation {
	public MoveRectHelpAnimation(final Pane learningPane) {
		super(learningPane, 150, 500, 150, 350, "Press (right button) on a rectangle to move it",
			"Drag or press 'ESC' to cancel", "Release to stop moving the rectangle");
	}

	@Override
	public Transition install() {
		final Transition transition = super.install();
		final Rectangle rec = new Rectangle(110d, 70d);
		pane.getChildren().add(rec);
		rec.toBack();
		rec.setFocusTraversable(false);
		rec.setMouseTransparent(true);
		rec.setFill(Color.LIGHTGRAY);
		rec.setStroke(Color.GRAY);
		rec.xProperty().bind(ell.centerXProperty().subtract(rec.getWidth() / 2d));
		rec.yProperty().bind(ell.centerYProperty().subtract(rec.getHeight() / 2d));
		rec.visibleProperty().bind(ell.visibleProperty());

		return transition;
	}
}
