package org.malai.javafx.widget;

import javafx.scene.Node;
import javafx.scene.control.Button;

import org.malai.picking.Pickable;
import org.malai.picking.Picker;

public class MButton extends Button implements Pickable {

	public MButton() {
		super();
	}

	public MButton(String arg0, Node arg1) {
		super(arg0, arg1);
	}

	public MButton(String arg0) {
		super(arg0);
	}

	@Override
	public Picker getPicker() {
		return JavafxWidgetUtilities.INSTANCE.getPicker(this);
	}
	
	@Override
	public boolean contains(final double x, final double y) {
		return JavafxWidgetUtilities.INSTANCE.contains(this, x, y);
	}
}
