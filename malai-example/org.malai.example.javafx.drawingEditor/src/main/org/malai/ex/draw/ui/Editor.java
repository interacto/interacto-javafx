package org.malai.ex.draw.ui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import org.malai.action.ActionsRegistry;
import org.malai.ex.draw.view.shape.MyViewDrawing;
import org.malai.undo.UndoCollector;

/*
 * The main GUI of the application.
 * Each GUI of the application should inherit
 * of the Malai class UI that represents a GUI.
 * A Malai UI is a JFrame. 
 */
public class Editor extends Application{// extends JavafxUI {
	static {
		/*
		 * One of the first thing to do is to define the 
		 * number of undoable actions that can be stored.
		 * When the threshold is reached, the oldest stored
		 * action is flushed.
		 */
		UndoCollector.INSTANCE.setSizeMax(30);
		
		/*
		 * In the same way, the number of actions that can
		 * be kept in memory should be defined.
		 * This step is different from the undo process.
		 * An action may need another action to run. So,
		 * this registry stores the recent executed actions.
		 * When the threshold is reached, the oldest stored
		 * action is flushed. 
		 */
		ActionsRegistry.INSTANCE.setSizeMax(30);
	}
	
	protected MyViewDrawing viewDrawing;
	
	public Editor() {
		super();

//		viewDrawing = getViewDrawing();
		
		/*
		 * Malai provides one mechanism to bind/map objects. For instance, the concrete
		 * presentation has to be updated on modifications of its abstract presentation.
		 * So, a mapping has been developed for that (Drawing2ViewDrawingMapping).
		 * The following line adds this mapping to the mapping registry that manages all
		 * the mappings.
		 */
//		MappingRegistry.REGISTRY.addMapping(new Drawing2ViewDrawingMapping(getDrawing(), viewDrawing));
		
//		MappingRegistry.REGISTRY.addMapping(
//				new ShapeList2ViewShapeListMapping(getDrawing().getShapes(), viewDrawing.getViews()));
		
//		pencil = new Pencil(viewDrawing);
		
//		pencil.setActivated(true);
//		pencil.addEventable(viewDrawing);
	}
	
	
	@Override
	public void start(Stage primaryStage) {
		try {
			BorderPane root = (BorderPane)FXMLLoader.load(getClass().getResource("../view/ui/UI.fxml"));
			Scene scene = new Scene(root);
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}




//	@Override
//	public Instrument[] getInstruments() {
//		return new Instrument[]{pencil};
//	}
//
//	@Override
//	public void initialisePresentations() {
//		/*
//		 * A UI is composed of presentations.
//		 * Each presentation is a couple abstract presentation
//		 * and concrete presentation.
//		 * In this example, the abstract presentation is the drawing
//		 * and its concrete presentation the Swing canvas representing it.
//		 */
//		MyDrawing drawing = new MyDrawing(); // Creating the model.
//		MyViewDrawing canvas = new MyViewDrawing(); // Creating the view.
//		/*
//		 * Creating a presentation composed of these two elements.
//		 * Adding this presentation to the set of presentations of the UI.
//		 */
//		presentations.add(new Presentation<>(drawing, canvas));
//	}
//	
//	
//	public MyDrawing getDrawing() {
//		/*
//		 * It is possible to obtain an abstract or concrete presentation using this process:
//		 */
//		return getPresentation(MyDrawing.class, MyViewDrawing.class).getAbstractPresentation();
//		/*
//		 * Of course, storing this value into an attribute would improve the performances when
//		 * many access to it would be done. 
//		 */
//	}
//	
//	
//	public MyViewDrawing getViewDrawing() {
//		/*
//		 * It is possible to obtain an abstract or concrete presentation using this process:
//		 */
//		return getPresentation(MyDrawing.class, MyViewDrawing.class).getConcretePresentation();
//		/*
//		 * Of course, storing this value into an attribute would improve the performances when
//		 * many access to it would be done. 
//		 */
//	}
	
	
	public static void main(String[] args) {
		launch(args);
	}
}
