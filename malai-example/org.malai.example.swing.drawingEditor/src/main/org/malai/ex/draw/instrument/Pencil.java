package org.malai.ex.draw.instrument;

import java.awt.Point;
import java.awt.event.MouseEvent;

import org.malai.ex.draw.action.AddShape;
import org.malai.ex.draw.model.MyDrawing;
import org.malai.ex.draw.model.MyRect;
import org.malai.ex.draw.view.MyViewDrawing;
import org.malai.ex.draw.view.MyViewShape;
import org.malai.ex.draw.view.ViewFactory;
import org.malai.instrument.Instrument;
import org.malai.instrument.Link;
import org.malai.mapping.MappingRegistry;
import org.malai.swing.interaction.library.AbortableDnD;

/*
 * The pencil is an instrument that permits the creation
 * of shapes by interacting with the drawing view.
 */
public class Pencil extends Instrument {
	protected MyViewDrawing viewDrawing;

	/*
	 * The pencil interacts with the ViewDrawing.
	 */
	public Pencil(MyViewDrawing viewDrawing) {
		super();
		this.viewDrawing = viewDrawing;
	}

	
	@Override
	protected void initialiseLinks() {
		/*
		 * This operation contains the creation of the links (action-to-interaction links).
		 * To order of the creation may have an effect. The interaction contained into other ones
		 * should be initialized first. For instance, if a press2addText link must but added here,
		 * it should be put before DnD2AddShape since the press interaction is contained by the
		 * dnd.
		 * This operation should not be called explicitly. Lazy instantiation permits to create
		 * the links the first time the instrument is activated.
		 */
		try {
			addLink(new DnD2AddShape(this));
		}catch (IllegalAccessException | InstantiationException e) {
			e.printStackTrace();
		}
	}
	
	
	/*
	 * The interim feedback of the instrument permits to display/hide some information
	 * to the users and different major events such as at the creation and the execution of an action
	 * produced by one of its links.
	 */
	@Override
	public void interimFeedback() {
		viewDrawing.setTempShape(null);
		viewDrawing.repaint();
	}
	
	
	/*
	 * Defines a link mapping a DnD interaction to an action that adds a shape into the drawing.
	 * A Malai link has three generics: the type of the Mala action to create; the type of the
	 * interaction to use; the instrument containing this link.
	 * Developers never have to explicitly instantiate the action or the interaction. This is
	 * performed automatically by the Malai library.
	 */
	class DnD2AddShape extends Link<AddShape, AbortableDnD, Pencil> {
		/*
		 * Some attributes of the link.
		 */
		protected MyRect rect;
		
		protected MyViewShape<?> tmpView;
		
		/*
		 * The constructor of a link must define:
		 * - its parent instrument object
		 * - if the action must be executed on each update of the interaction
		 * - the class of the action (to instantiate reflexively the action)
		 * - the class of the interaction (to instantiate reflexively the interaction)
		 */
		public DnD2AddShape(Pencil ins) throws InstantiationException, IllegalAccessException {
			super(ins, false, AddShape.class, AbortableDnD.class);
		}
		
		
		@Override
		public void updateAction() {
			/*
			 * operations to do for updating the action when the interaction runs.
			 * Called each time the interaction's state changes.
			 */
			super.updateAction();
			Point p1 = interaction.getStartPt();
			Point p2 = interaction.getEndPt();
			rect.setX(Math.min(p1.getX(), p2.getX()));
			rect.setY(Math.min(p1.getY(), p2.getY()));
			rect.setWidth(Math.abs(p1.getX()-p2.getX()));
			rect.setHeight(Math.abs(p1.getY()-p2.getY()));
		}
		

		/*
		 * The interim feedback of the link.
		 * This operation is called each time the interaction's state changes.
		 */
		@Override
		public void interimFeedback() {
			super.interimFeedback();
			/*
			 * The temporary views is updated and the canvas repainted.
			 */
			tmpView.update();
			viewDrawing.repaint();
		}

		@Override
		public void initAction() {
			/*
			 * Initializing the action.
			 */
			rect = new MyRect();
			action.setDrawing(MappingRegistry.REGISTRY.getSourceFromTarget(instrument.viewDrawing, MyDrawing.class));
			action.setShape(rect);
			/*
			 * This temporary view is used to see in real time the creation of the shape, while this last has not been added
			 * to the drawing yet (since the action is not executed yet).
			 */
			tmpView = ViewFactory.INSTANCE.createViewShape(rect);
			viewDrawing.setTempShape(tmpView);
		}

		@Override
		public boolean isConditionRespected() {
			/*
			 * Defines if the action can be created and executed.
			 * Here, we just check that the button 1 is used and the starting and the ending points are not equal.
			 */
			return interaction.getButton()==MouseEvent.BUTTON1 && !interaction.getEndPt().equals(interaction.getStartPt());
		}
	}
}
