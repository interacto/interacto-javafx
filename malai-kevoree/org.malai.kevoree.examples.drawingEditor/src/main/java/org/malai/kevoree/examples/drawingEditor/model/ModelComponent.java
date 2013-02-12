package org.malai.kevoree.examples.drawingEditor.model;

import org.kevoree.annotation.Start;
import org.kevoree.annotation.Stop;
import org.malai.kevoree.core.presentation.AbstractPresentationComponent;
import org.malai.kevoree.examples.drawingEditor.model.interfaces.DrawingTK;
import org.malai.kevoree.examples.drawingEditor.model.interfaces.IDrawing;

/**
 * User: Arnaud Blouin
 * Date: 19/07/12
 * Time: 18:29
 */
public class ModelComponent extends AbstractPresentationComponent {
    protected IDrawing drawing;

    public ModelComponent() {
        super();
        drawing = DrawingTK.getFactory().createDrawing();
    }


    @Override
    public void setModified(boolean b) {
        drawing.setModified(b);
    }

    @Override
    public boolean isModified() {
        return drawing.isModified();
    }

    @Override
    public void reinit() {
        drawing.reinit();
    }


    @Start
    public void start() throws Exception {

    }

    @Stop
    public void stop() {
    }
}
