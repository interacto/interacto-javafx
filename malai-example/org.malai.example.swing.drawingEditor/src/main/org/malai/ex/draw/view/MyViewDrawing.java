package org.malai.ex.draw.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.List;

import org.malai.presentation.ConcretePresentation;
import org.malai.swing.widget.MPanel;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

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
public class MyViewDrawing extends MPanel implements ConcretePresentation {
	private static final long serialVersionUID = 1L;
	
	protected List<MyViewShape<?>> views;
	
	protected MyViewShape<?> tmpShape;
	
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
		super(true, true);
		
		views = new ArrayList<>();
		modified = false;
		setPreferredSize(new Dimension(800, 600));
	}

	
	public List<MyViewShape<?>> getViews() {
		return views;
	}


	@Override
	public void paint(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;

		g2.setRenderingHint(RenderingHints.KEY_ALPHA_INTERPOLATION, RenderingHints.VALUE_ALPHA_INTERPOLATION_QUALITY);
		g2.setRenderingHint(RenderingHints.KEY_COLOR_RENDERING, RenderingHints.VALUE_COLOR_RENDER_QUALITY);
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g2.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
		g2.setColor(Color.WHITE);
		g2.fillRect(0, 0, getWidth(), getHeight());

		for(MyViewShape<?> view : views)
			view.paint(g2);
		
		if(tmpShape!=null)
			tmpShape.paint(g2);
	}


	@Override
	public void load(boolean arg0, String arg1, Element arg2) {
		/*
		 * Useful when loading an XML document to parameterize the view.
		 */
	}



	@Override
	public void save(boolean arg0, String arg1, Document arg2, Element arg3) {
		/*
		 * Useful when saving into an XML document the parameters of the view.
		 */		
	}


	@Override
	public boolean isModified() {
		return modified;
	}


	@Override
	public void setModified(boolean mod) {
		modified = mod;
	}


	@Override
	public void reinit() {
		/*
		 * Actions to do when this widget is reinitialized.
		 */
		views.clear();
		update();
	}

	
	public void updateSize() {
		double maxWidth = Double.MIN_VALUE;
		double maxHeight = Double.MIN_VALUE;
		Rectangle2D rec;
		
		for(MyViewShape<?> view : views) {
			rec = view.getBorder();
			if(rec.getMaxX()>maxWidth)  maxWidth  = rec.getMaxX();
			if(rec.getMaxY()>maxHeight) maxHeight = rec.getMaxY();
		}
		
		setPreferredSize(new Dimension((int)Math.max(0, maxWidth), (int)Math.max(0, maxHeight)));
	}
	

	@Override
	public void update() {
		/*
		 * Actions to do when this widget must be updated. 
		 */
		updateSize(); // Updating the size of the widget using the size of its shape views.
		repaint(); // repainting the shapes.
		revalidate(); // Updating the scroll bars.
	}


	public void setTempShape(MyViewShape<?> tempShape) {
		tmpShape = tempShape;
	}
}
