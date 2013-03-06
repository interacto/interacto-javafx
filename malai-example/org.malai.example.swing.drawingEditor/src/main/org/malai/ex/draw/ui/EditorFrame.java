package org.malai.ex.draw.ui;

import org.malai.ex.draw.instrument.Pencil;
import org.malai.ex.draw.mapping.Drawing2ViewDrawingMapping;
import org.malai.ex.draw.mapping.ShapeList2ViewShapeListMapping;
import org.malai.ex.draw.model.MyDrawing;
import org.malai.ex.draw.view.MyViewDrawing;
import org.malai.instrument.Instrument;
import org.malai.mapping.MappingRegistry;
import org.malai.presentation.Presentation;
import org.malai.swing.ui.UI;

/*
 * The main GUI of the application.
 * Each GUI of the application should inherit
 * of the Malai class UI that represents a GUI.
 * A Malai UI is a JFrame. 
 */
public class EditorFrame extends UI {
	private static final long serialVersionUID = 1L;
	
	protected Pencil pencil;
	
	public EditorFrame() {
		super();
		/* Setting the name of the application. */
		setName("A simple drawing editor");
		/* 
		 * In opposite to Swing, the 'set default close operation'
		 * stop the application by default.
		 */

		MyViewDrawing viewDrawing = getViewDrawing();
		
		/*
		 * Malai provides one mechanism to bind/map objects. For instance, the concrete
		 * presentation has to be updated on modifications of its abstract presentation.
		 * So, a mapping has been developed for that (Drawing2ViewDrawingMapping).
		 * The following line adds this mapping to the mapping registry that manages all
		 * the mappings.
		 */
		MappingRegistry.REGISTRY.addMapping(new Drawing2ViewDrawingMapping(getDrawing(), viewDrawing));
		
		MappingRegistry.REGISTRY.addMapping(
				new ShapeList2ViewShapeListMapping(getDrawing().getShapes(), viewDrawing.getViews()));
		
		pencil = new Pencil(viewDrawing);
		
		getContentPane().add(viewDrawing);
		
		pencil.setActivated(true);
		pencil.addEventable(viewDrawing);
	}
	
	

	@Override
	public Instrument[] getInstruments() {
		return new Instrument[]{pencil};
	}

	@Override
	public void initialisePresentations() {
		/*
		 * A UI is composed of presentations.
		 * Each presentation is a couple abstract presentation
		 * and concrete presentation.
		 * In this example, the abstract presentation is the drawing
		 * and its concrete presentation the Swing canvas representing it.
		 */
		MyDrawing drawing = new MyDrawing(); // Creating the model.
		MyViewDrawing canvas = new MyViewDrawing(); // Creating the view.
		/*
		 * Creating a presentation composed of these two elements.
		 * Adding this presentation to the set of presentations of the UI.
		 */
		presentations.add(new Presentation<>(drawing, canvas));
	}
	
	
	public MyDrawing getDrawing() {
		/*
		 * It is possible to obtain an abstract or concrete presentation using this process:
		 */
		return getPresentation(MyDrawing.class, MyViewDrawing.class).getAbstractPresentation();
		/*
		 * Of course, storing this value into an attribute would improve the performances when
		 * many access to it would be done. 
		 */
	}
	
	
	public MyViewDrawing getViewDrawing() {
		/*
		 * It is possible to obtain an abstract or concrete presentation using this process:
		 */
		return getPresentation(MyDrawing.class, MyViewDrawing.class).getConcretePresentation();
		/*
		 * Of course, storing this value into an attribute would improve the performances when
		 * many access to it would be done. 
		 */
	}
}
