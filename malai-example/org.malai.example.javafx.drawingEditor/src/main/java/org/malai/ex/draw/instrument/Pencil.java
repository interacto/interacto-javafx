package org.malai.ex.draw.instrument;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
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
	private final MyDrawing drawing;

	public Pencil() {
		super();
		drawing = new MyDrawing();
	}

	@Override
	protected void configureBindings() throws InstantiationException, IllegalAccessException {
		// A DnD interaction will produce an AddShape action
		// when interacting on the canvas
		// A temporary view of the created shape is created and displayed by the canvas. This view is removed
		// at the end of the interaction.
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


	@Override
	public void initialize(final URL url, final ResourceBundle res) {
		canvas.bindModel(drawing);
		setActivated(true);
	}
}
