package org.malai.example.drawingEditor.view;

import java.util.ArrayList;
import java.util.List;

import org.malai.picking.Pickable;
import org.malai.picking.Picker;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

public class ViewCanvas extends View implements Picker {
	protected List<ViewShape> views;
	Paint paint;
	
	public ViewCanvas(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init();
	}

	public ViewCanvas(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	public ViewCanvas(Context context) {
		super(context);
		init();
	}

	
	protected void init() {
		views = new ArrayList<ViewShape>();
		paint = new Paint();
	}
	
	
	public void onDraw(Canvas canvas) {
//		super.onDraw(canvas);
		 paint.setColor(Color.BLACK);
        paint.setStrokeWidth(3);
        canvas.drawRect(30, 30, 80, 80, paint);
        paint.setStrokeWidth(0);
        paint.setColor(Color.CYAN);
        canvas.drawRect(33, 60, 77, 77, paint );
        paint.setColor(Color.YELLOW);
        canvas.drawRect(33, 33, 77, 60, paint );
	}

	@Override
	public boolean contains(Object obj) {
		return views.contains(obj);
	}

	@Override
	public Pickable getPickableAt(double x, double y) {
		for(int i=views.size()-1; i>=0; i--)
			if(views.get(i).contains(x, y))
				return views.get(i);
		return null;
	}

	@Override
	public Picker getPickerAt(double x, double y) {
		return null;
	}
}
