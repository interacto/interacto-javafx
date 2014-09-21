package org.malai.ex.draw.instrument;

import java.net.URL;
import java.util.Collections;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.ComboBox;

import org.malai.ex.draw.action.ChangeCol;
import org.malai.javafx.instrument.JfxInstrument;
import org.malai.javafx.instrument.JfxInteractor;
import org.malai.javafx.interaction.library.ColorPicked;

public class LineCustomiser extends JfxInstrument implements Initializable {

    @FXML ComboBox<?> lineStyle;

    @FXML ColorPicker lineCol;
	
	
	public LineCustomiser() {
		super();
	}

	@Override
	protected void initialiseInteractors() {
		try {
			addInteractor(new ColButton2ChangeCol(this));
		} catch (InstantiationException | IllegalAccessException e) {
			e.printStackTrace();
		}
	}
	
	
	@Override
	public void initialize(URL url, ResourceBundle res) {
		setActivated(true);
	}
}


class ColButton2ChangeCol extends JfxInteractor<ChangeCol, ColorPicked, LineCustomiser> {
	ColButton2ChangeCol(LineCustomiser ins) throws InstantiationException, IllegalAccessException {
		super(ins, false, ChangeCol.class, ColorPicked.class, Collections.singletonList(ins.lineCol));
	}

	@Override
	public void initAction() {
		action.setNewCol(instrument.lineCol.getValue());
	}

	@Override
	public boolean isConditionRespected() {
		return interaction.getWidget()==instrument.lineCol;
	}
}