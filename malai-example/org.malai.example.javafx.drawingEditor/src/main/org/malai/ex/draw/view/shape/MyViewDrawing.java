package org.malai.ex.draw.view.shape;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javafx.collections.ListChangeListener;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import org.malai.ex.draw.model.MyDrawing;
import org.malai.ex.draw.model.MyShape;


/*
 * Defines the graphical representation of a drawing.
 * It extends MPanel which is a JPanel adapted for Malai.
 * Each Swing widget that have to be used with Malai (i.e.
 * to be used with instruments and interactions) must extends
 * the Malai widget class corresponding to the expected 
 * Swing widget. Here, we want MyViewDrawing to be a JPanel.
 * So, its corresponding Malai class, MPanel is used.
 * 
 * Moreover, this view is a concrete presentation, i.e. the
 * representation of the model of the application (MyDrawing).
 */
public class MyViewDrawing extends Canvas {
	protected final List<MyViewShape<?>> views;
	
	protected Optional<MyViewShape<?>> tmpShape;
	
	protected boolean modified;


	public MyViewDrawing() {
		/*
		 * The constructors of the Malai widgets have special parameters.
		 * withScrollPane is specific to panels and defines if the panel
		 * must have a scrollbar. The goal is to ease the definition of 
		 * scroll bars which is not so simple in Swing.
		 * withEvtManager (the second parameter) defines if the widget 
		 * must receive and dispatch events it receives to its listening 
		 * interactions. In our case, this panel must receive events
		 * from DnDs and other interactions used to create and handle
		 * shapes.
		 */
		super();
		
		views = new ArrayList<>();
		tmpShape = Optional.empty();
		modified = false;
		setWidth(800);
		setHeight(600);
	}
	
	
	public void bindModel(final MyDrawing drawing) {
		drawing.getShapes().addListener((ListChangeListener.Change<? extends MyShape> ch) -> {
			while(ch.next()) {
				if(ch.wasAdded()) {
					ch.getAddedSubList().forEach(sh -> views.add(ViewFactory.INSTANCE.createViewShape(sh)));
				}
				else if(ch.wasRemoved()) {
					ch.getRemoved().forEach(sh -> {
						Optional<MyViewShape<?>> view = views.stream().filter(v -> sh==v.model).findFirst();
						if(view.isPresent()) {
							views.remove(view.get());
						}
					});
				}
			}
			repaint();
		});
	}

	
	public List<MyViewShape<?>> getViews() {
		return views;
	}


	public void repaint() {
		final GraphicsContext gc = getGraphicsContext2D();

		gc.setFill(Color.WHITE);
		gc.fillRect(0, 0, getWidth(), getHeight());

		views.forEach(view -> view.paint(gc));
		
		if(tmpShape.isPresent())
			tmpShape.get().paint(gc);
	}


//	@Override
//	public void load(boolean arg0, String arg1, Element arg2) {
//		/*
//		 * Useful when loading an XML document to parameterize the view.
//		 */
//	}
//
//
//
//	@Override
//	public void save(boolean arg0, String arg1, Document arg2, Element arg3) {
//		/*
//		 * Useful when saving into an XML document the parameters of the view.
//		 */		
//	}
//
//
//	@Override
//	public boolean isModified() {
//		return modified;
//	}
//
//
//	@Override
//	public void setModified(boolean mod) {
//		modified = mod;
//	}
//
//
//	@Override
//	public void reinit() {
//		/*
//		 * Actions to do when this widget is reinitialized.
//		 */
//		views.clear();
//		update();
//	}

	
	public void updateSize() {
//		double maxWidth = Double.MIN_VALUE;
//		double maxHeight = Double.MIN_VALUE;
//		Rectangle2D rec;
//		
//		for(MyViewShape<?> view : views) {
//			rec = view.getBorder();
//			if(rec.getMaxX()>maxWidth)  maxWidth  = rec.getMaxX();
//			if(rec.getMaxY()>maxHeight) maxHeight = rec.getMaxY();
//		}
		
//		setPreferredSize(new Dimension((int)Math.max(0, maxWidth), (int)Math.max(0, maxHeight)));
	}
	

//	@Override
//	public void update() {
//		/*
//		 * Actions to do when this widget must be updated. 
//		 */
//		updateSize(); // Updating the size of the widget using the size of its shape views.
//		repaint(); // repainting the shapes.
////		revalidate(); // Updating the scroll bars.
//	}


	public void setTempShape(Optional<MyViewShape<?>> tempShape) {
		tmpShape = tempShape;
	}
}
