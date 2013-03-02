package org.malai.example.drawingEditor.instrument;

import org.malai.example.drawingEditor.action.AddShape;
import org.malai.instrument.Instrument;
import org.malai.instrument.Link;
import org.malai.interaction.library.DnD;

public class Pencil extends Instrument {

	public Pencil() {
		super();
	}

	@Override
	protected void initialiseLinks() {
//		addLink(new)
	}
	
	
	public class DnD2AddShape extends Link<AddShape, DnD, Pencil> {
		public DnD2AddShape(Pencil ins) throws InstantiationException, IllegalAccessException {
			super(ins, true, AddShape.class, DnD.class);
		}

		
		@Override
		public void initAction() {
			// TODO Auto-generated method stub
		}

		
		@Override
		public boolean isConditionRespected() {
			// TODO Auto-generated method stub
			return false;
		}
	}
}
