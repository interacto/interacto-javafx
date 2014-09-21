package org.malai.ex.draw.view.shape;

import org.malai.ex.draw.model.MyRect;
import org.malai.ex.draw.model.MyShape;

public final class ViewFactory {

	public static final ViewFactory INSTANCE = new ViewFactory();
	
	private ViewFactory() {
		super();
	}

	
	public MyViewShape<?> createViewShape(final MyShape sh) {
		if(sh instanceof MyRect)
			return new MyViewRect((MyRect)sh);
		return null;
	}
}
