package org.malai.ex.draw.view;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import org.malai.ex.draw.model.MyRect;
import org.malai.ex.draw.model.MyShape;

public final class ViewFactory {

	public static final ViewFactory INSTANCE = new ViewFactory();
	
	private ViewFactory() {
		super();
	}

	
	public Shape createViewShape(final MyShape sh) {
		if(sh instanceof MyRect) {
			final Rectangle view = new Rectangle();
			final MyRect model = (MyRect) sh;

			view.setUserData(sh);
			view.widthProperty().bind(model.widthProperty());
			view.heightProperty().bind(model.heightProperty());
			view.xProperty().bind(model.xProperty());
			view.yProperty().bind(model.yProperty());
			view.strokeProperty().bind(model.lineColorProperty());
			view.setFill(Color.LIGHTGRAY);
			view.setStrokeWidth(5d);
			return view;
		}
		return null;
	}
}
