package org.malai.ex.util;

import javafx.scene.ImageCursor;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class ColorCursor extends ImageCursor {
	public ColorCursor(final Color color) {
		super(new Rectangle(20d, 20d, color).snapshot(null, null));
	}
}
