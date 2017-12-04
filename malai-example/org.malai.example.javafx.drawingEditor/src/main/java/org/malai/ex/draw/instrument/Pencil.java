package org.malai.ex.draw.instrument;

import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;
import java.util.stream.Collectors;
import javafx.collections.ListChangeListener;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.Group;
import javafx.scene.control.ColorPicker;
import javafx.scene.shape.Shape;
import org.malai.ex.draw.action.AddShape;
import org.malai.ex.draw.action.ChangeColour;
import org.malai.ex.draw.model.MyDrawing;
import org.malai.ex.draw.model.MyRect;
import org.malai.ex.draw.model.MyShape;
import org.malai.ex.draw.view.shape.MyCanvas;
import org.malai.ex.draw.view.shape.ViewFactory;
import org.malai.ex.util.ColorCursor;
import org.malai.javafx.instrument.JfxInstrument;
import org.malai.javafx.interaction.library.DnD;
import org.malai.javafx.interaction.library.Press;

public class Pencil extends JfxInstrument implements Initializable {
	@FXML private MyCanvas canvas;
	@FXML private ColorPicker lineCol;

	/** The model of the app. */
	private final MyDrawing drawing;

	public Pencil() {
		super();
		drawing = new MyDrawing();
	}

	@Override
	protected void configureBindings() throws InstantiationException, IllegalAccessException {
		// A DnD interaction will produce an AddShape action while interacting on the canvas.
		// A temporary view of the created shape is created and displayed by the canvas.
		// This view is removed at the end of the interaction.
		nodeBinder(AddShape.class, new DnD()).on(canvas).
			map(i -> new AddShape(drawing, new MyRect(i.getSrcPoint().getX(), i.getSrcPoint().getY()))).
			first((a, i) -> canvas.setTmpShape(ViewFactory.INSTANCE.createViewShape(a.getShape()))).
			then((a, i) -> {
				final MyRect sh = (MyRect) a.getShape();
				sh.setWidth(i.getEndPt().getX() - sh.getX());
				sh.setHeight(i.getEndPt().getY() - sh.getY());
			}).
			end((a, i) -> canvas.setTmpShape(null)).
			bind();

		/*
		 * A DnD on the colour picker produces ChangeCol actions when the target of the DnD is a shape
		 * (the shape we want to change the colour). The interim feedback changes the cursor during the DnD to show the dragged colour.
		 * Note that the feedback callback is not optimised here as the colour does not change during the DnD. The cursor
		 * should be changed in 'first'
		 */
		nodeBinder(ChangeColour.class, new DnD()).on(lineCol).
			map(i -> new ChangeColour(lineCol.getValue(), null)).
			then((a, i) -> i.getEndObjet().map(view -> (MyShape) view.getUserData()).ifPresent(sh -> a.setShape(sh))).
			when(i -> i.getEndObjet().orElse(null) instanceof Shape).
			feedback(() -> lineCol.getScene().setCursor(new ColorCursor(lineCol.getValue()))).
			end((a, i) -> lineCol.getScene().setCursor(Cursor.DEFAULT)).
			bind();

		/*
		 * A mouse pressure creates an anonymous action that simply shows a message in the console.
		 */
		anonActionBinder(() -> System.out.println("An example of the anonymous action."), new Press()).on(canvas).bind();
	}


	/**
	 * Binds the model to the view. MVP layout: this presenter binds the model to the view. These two last do not know each others.
	 */
	public void bindModel() {
		final Group shapesPane = canvas.getShapesPane();

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

	@Override
	public void initialize(final URL url, final ResourceBundle res) {
		bindModel();
		setActivated(true);
	}
}
