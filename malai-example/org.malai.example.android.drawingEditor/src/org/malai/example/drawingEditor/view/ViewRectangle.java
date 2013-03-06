package org.malai.example.drawingEditor.view;

import org.malai.picking.Picker;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.util.AttributeSet;

public class ViewRectangle extends ViewShape {
	protected float x;
	protected float y;
	protected float w;
	protected float h;
	protected int color;
	protected float stroke;
	
	
	public ViewRectangle(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init();
	}

	public ViewRectangle(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	public ViewRectangle(Context context) {
		super(context);
		init();
	}
	
	
	public void setX(float x) {
		this.x = x;
	}

	public void setY(float y) {
		this.y = y;
	}

	public void setW(float w) {
		this.w = w;
	}

	public void setH(float h) {
		this.h = h;
	}

	public void setColor(int color) {
		this.color = color;
	}

	public void setStroke(float stroke) {
		this.stroke = stroke;
	}

	protected void init() {
		x = y = 10f;
		w = h = 30f;
		color = Color.RED;
		stroke = 3f;
	}
	
	
	public void onDraw(Canvas canvas) {
		paint.setColor(Color.BLACK);
		paint.setStrokeWidth(3);
		canvas.drawRect(x, y, w, h, paint);
	}
	
	

	@Override
	public boolean contains(double arg0, double arg1) {
		return false;
	}

	@Override
	public Picker getPicker() {
		return null;
	}
}
