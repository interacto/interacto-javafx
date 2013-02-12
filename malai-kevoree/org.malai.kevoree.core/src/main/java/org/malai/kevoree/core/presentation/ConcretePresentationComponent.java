package org.malai.kevoree.core.presentation;

import org.kevoree.annotation.Start;
import org.kevoree.annotation.Stop;
import org.kevoree.framework.AbstractComponentType;
import org.malai.presentation.ConcretePresentation;

/**
 * User: ablouin
 * Date: 19/07/12
 * Time: 17:11
 */
public abstract class ConcretePresentationComponent extends AbstractComponentType implements ConcretePresentation {

    public ConcretePresentationComponent() {
        super();
    }

    @Start
    public void start() throws Exception {

    }

    @Stop
    public void stop() {
    }
}
