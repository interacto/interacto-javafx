package org.malai.kevoree.core.presentation;

import org.kevoree.annotation.Start;
import org.kevoree.annotation.Stop;
import org.kevoree.framework.AbstractComponentType;
import org.malai.presentation.AbstractPresentation;

/**
 * Created with IntelliJ IDEA.
 * User: ablouin
 * Date: 19/07/12
 * Time: 17:05
 * To change this template use File | Settings | File Templates.
 */
public abstract class AbstractPresentationComponent extends AbstractComponentType implements AbstractPresentation {

    public AbstractPresentationComponent() {
        super();
    }

    @Start
    public void start() throws Exception {

    }

    @Stop
    public void stop() {
    }
}
