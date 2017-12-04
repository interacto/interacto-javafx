package org.malai.ex.draw.view.shape;

import java.util.Objects;
import java.util.stream.Collectors;
import javafx.collections.ListChangeListener;
import javafx.scene.Group;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Shape;
import org.malai.ex.draw.model.MyDrawing;
import org.malai.ex.draw.model.MyShape;


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
	
	
	public void bindModel(final MyDrawing drawing) {
		drawing.getShapes().addListener((ListChangeListener.Change<? extends MyShape> ch) -> {
			while(ch.next()) {
				if(ch.wasAdded()) {
					shapesPane.getChildren().addAll(
						ch.getAddedSubList().stream().map(sh -> ViewFactory.INSTANCE.createViewShape(sh)).filter(Objects::nonNull).collect(Collectors.toList()));
				}
				if(ch.wasRemoved()) {
					shapesPane.getChildren().removeAll(
						ch.getRemoved().stream().map(sh -> shapesPane.getChildren().stream().filter(v -> v.getUserData() == sh).findAny().orElse(null)).
						filter(Objects::nonNull).collect(Collectors.toList()));
				}
			}
		});
	}

	public void setTmpShape(final Shape shape) {
		tmpShape.getChildren().clear();
		if(shape != null) {
			tmpShape.getChildren().addAll(shape);
		}
	}
}
