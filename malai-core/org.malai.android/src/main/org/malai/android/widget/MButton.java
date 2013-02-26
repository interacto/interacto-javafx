package org.malai.android.widget;

import org.malai.picking.Pickable;
import org.malai.picking.Picker;

import android.content.Context;
import android.util.AttributeSet;
import android.view.ViewParent;
import android.widget.Button;

/**
 * This widgets is based on an Android Button. It allows to be used in the Malai framework for picking.<br>
 * <br>
 * This file is part of Malai.<br>
 * Copyright (c) 2009-2013 Arnaud BLOUIN<br>
 * <br>
 * Malai is free software; you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation; either version 2 of the License, or (at your option) any later version.
 * <br>
 * Malai is distributed without any warranty; without even the implied
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * General Public License for more details.<br>
 * <br>
 * 2012-02-26<br>
 * @author Arnaud BLOUIN
 * @version 0.2
 * @since 0.2
 */
public class MButton extends Button implements Pickable {

	public MButton(Context ctx) {
		super(ctx);
	}


	public MButton(Context ctx, AttributeSet attrs) {
		super(ctx, attrs);
	}
	

	public MButton(Context ctx, AttributeSet attrs, int defStyle) {
		super(ctx, attrs, defStyle);
	}


	@Override
	public Picker getPicker() {
		ViewParent parent = getParent();
		Picker picker 		= null;

		while(picker==null && parent!=null)
			if(parent instanceof Picker)
				picker = (Picker) parent;
			else
				parent = parent.getParent();

		return picker;
	}


	@Override
	public boolean contains(double x, double y) {
		final float bx = getX();
		final float by = getY();
		return x>=bx && y>=by && x<=bx+getWidth() && y<=by+getHeight();
	}
}
