package org.malai.example.drawingEditor.view;

import org.malai.picking.Pickable;

import android.content.Context;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

public abstract class ViewShape extends View implements Pickable {
	protected Paint paint = new Paint();
	
	public ViewShape(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	public ViewShape(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public ViewShape(Context context) {
		super(context);
	}
}
