package org.malai.ex.draw.view.shape;

import javafx.scene.Group;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Shape;


/*
 * Defines the graphical representation of a drawing.
 * It extends MPanel which is a JPanel adapted for Malai.
 * Each Swing widget that have to be used with Malai (i.e.
 * to be used with instruments and interactions) must extends
 * the Malai widget class corresponding to the expected 
 * Swing widget. Here, we want MyViewDrawing to be a JPanel.
 * So, its corresponding Malai class, MPanel is used.
 * 
 * Moreover, this view is a concrete presentation, i.e. the
 * representation of the model of the application (MyDrawing).
 */
public class MyCanvas extends Pane {
	/** The views of the shape. */
	private final Group shapesPane;
	private final Group tmpShape;


	public MyCanvas() {
		super();
		shapesPane = new Group();
		tmpShape = new Group();
		getChildren().add(shapesPane);
		getChildren().add(tmpShape);
	}
	

	public void setTmpShape(final Shape shape) {
		tmpShape.getChildren().clear();
		if(shape != null) {
			tmpShape.getChildren().addAll(shape);
		}
	}

	public Group getShapesPane() {
		return shapesPane;
	}
}
