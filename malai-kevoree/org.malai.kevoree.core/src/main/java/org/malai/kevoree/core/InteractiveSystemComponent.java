package org.malai.kevoree.core;

import org.kevoree.annotation.*;
import org.kevoree.framework.AbstractComponentType;
import org.malai.kevoree.core.instrument.InstrumentService;

/**
 * Created with IntelliJ IDEA.
 * User: ablouin
 * Date: 18/07/12
 * Time: 17:49
 * To change this template use File | Settings | File Templates.
 */
@Library(name = "Malai")
@Provides({
        @ProvidedPort(name = "instruments", type = PortType.MESSAGE)
})
@ComponentFragment
public abstract class InteractiveSystemComponent extends AbstractComponentType {

    public InteractiveSystemComponent() {
        super();
    }


    protected abstract void createInstruments();


    @Start
    public void start() throws Exception {
        createInstruments();
    }

    @Stop
    public void stop() {
    }
}
