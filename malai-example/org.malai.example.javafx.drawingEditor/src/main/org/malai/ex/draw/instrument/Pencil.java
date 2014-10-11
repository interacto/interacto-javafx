package org.malai.ex.draw.instrument;

import java.net.URL;
import java.util.Collections;
import java.util.Optional;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import org.malai.ex.draw.action.AddShape;
import org.malai.ex.draw.model.MyDrawing;
import org.malai.ex.draw.model.MyRect;
import org.malai.ex.draw.view.shape.MyViewDrawing;
import org.malai.javafx.instrument.JfxInstrument;
import org.malai.javafx.instrument.JfxInteractor;
import org.malai.javafx.interaction.library.DnD;

public class Pencil extends JfxInstrument implements Initializable {

	@FXML MyViewDrawing canvas;
	MyDrawing drawing;
	
	public Pencil() {
		super();
	}

	@Override
	protected void initialiseInteractors() {
		try {
			addInteractor(new DnD2AddShape(this));
		} catch (InstantiationException | IllegalAccessException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void initialize(final URL url, final ResourceBundle res) {
		drawing = new MyDrawing();
		canvas.bindModel(drawing);
		setActivated(true);
		
		MyRect rect = new MyRect();
		rect.setX(100);
		rect.setY(200);
		rect.setWidth(200);
		rect.setHeight(100);
		drawing.addShape(rect);
	}
}

class DnD2AddShape extends JfxInteractor<AddShape, DnD, Pencil> {
	private Optional<MyRect> shape;
	
	DnD2AddShape(Pencil ins) throws InstantiationException, IllegalAccessException {
		super(ins, false, AddShape.class, DnD.class, Collections.singletonList(ins.canvas));
		shape = Optional.empty();
	}

	@Override
	public void initAction() {
		final MyRect rect = new MyRect();
		getInteraction().getStartPt().ifPresent(pt -> {
			rect.setX(pt.getX());
			rect.setY(pt.getY());
		});
		
		rect.setWidth(1);
		rect.setHeight(1);
		action.setDrawing(instrument.drawing);
		action.setShape(rect);
		shape = Optional.of(rect);
	}
	
	@Override
	public void updateAction() {
		shape.ifPresent(sh -> {
			getInteraction().getEndPt().ifPresent(pt -> {
				sh.setWidth(pt.getX()-sh.getX());
				sh.setHeight(pt.getY()-sh.getY());
			});
		});
	}

	
	@Override
	public boolean isConditionRespected() {
		return true;
	}
}
