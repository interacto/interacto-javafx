package org.malai.kevoree.core.instrument;

import org.malai.action.Action;
import org.malai.action.ActionHandler;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: ablouin
 * Date: 18/07/12
 * Time: 13:28
 * To change this template use File | Settings | File Templates.
 */
public interface InstrumentService {
    boolean isActivated();

    List<LinkComponent<?>> getLinks();

    void onActionAborted(Action action);

    void interimFeedback();

    void onActionExecuted(Action action);

    void onActionDone(Action action);

    void onActionAdded(Action action);

    void onActionCancelled(Action action);

    InstrumentComponent getInstrument();
}
