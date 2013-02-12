package org.malai.kevoree.core.instrument;

import org.kevoree.annotation.*;
import org.kevoree.framework.AbstractComponentType;
import org.kevoree.framework.MessagePort;
import org.malai.action.Action;
import org.malai.action.ActionsRegistry;
import org.malai.error.ErrorCatcher;
import org.malai.instrument.MustBeUndoableActionException;
import org.malai.interaction.Eventable;
import org.malai.kevoree.core.interaction.InteractionComponent;
import org.malai.kevoree.core.interaction.InteractionMsg;
import org.malai.kevoree.core.interaction.InteractionService;
import org.malai.stateMachine.MustAbortStateMachineException;
import org.malai.undo.Undoable;

/**
 * Created with IntelliJ IDEA.
 * User: ablouin
 * Date: 17/07/12
 * Time: 16:45
 * To change this template use File | Settings | File Templates.
 */
@Library(name = "Malai")
@Requires({
        @RequiredPort(name = "interactionMsg", type = PortType.MESSAGE),
        @RequiredPort(name = "interactionServ", type = PortType.SERVICE, className = InteractionService.class, optional = false),
        @RequiredPort(name = "instrumentServ", type = PortType.SERVICE, className = InstrumentService.class, optional = false)
})
@Provides({
        @ProvidedPort(name = "linkMsg", type = PortType.MESSAGE)
})
@ComponentFragment
public abstract class LinkComponent<A extends Action> extends AbstractComponentType {
    /** The target action. */
    protected A action;

    /** Specifies if the action must be execute or update
     * on each evolution of the interaction. */
    protected boolean execute;

    protected Class<A> clazzAction;

    /**
     * Creates a link. This constructor must initialise the interaction. The link is (de-)activated if the given
     * instrument is (de-)activated.
     * @param exec Specifies if the action must be execute or update on each evolution of the interaction.
     * @param clazzAction The type of the action that will be created. Used to instantiate the action by reflexivity.
     * @throws IllegalAccessException If no free-parameter constructor is available.
     * @throws InstantiationException If an error occurs during instantiation of the interaction/action.
     * @throws IllegalArgumentException If the given interaction or instrument is null.
     * @since 0.2
     */
    public LinkComponent(final boolean exec, final Class<A> clazzAction) throws InstantiationException, IllegalAccessException {
        super();

        if(clazzAction==null)
            throw new IllegalArgumentException();

        this.clazzAction = clazzAction;
        action		= null;
        execute		= exec;
//        setActivated(ins.isActivated()); FIXME   to do in the instrument
    }



    /**
     * Binds the interaction of the link to a Eventable object that produces
     * events used by the interaction.
     * @param eventable The eventable object that gathers event used by the interaction.
     * @since 0.2
     */
    public void addEventable(final Eventable eventable) {
//        if(eventable!=null && interaction!=null)
//            interaction.linkToEventable(eventable);
    }


    /**
     * Stops the interaction and clears all its events waiting for a process.
     * @since 0.2
     */
    public void clearEvents() {
        MessagePort port = getPortByName("linkMsg", MessagePort.class);
        port.process(LinkMsg.REINIT_INTERACTION);
        port.process(LinkMsg.CLEAR_EVENTS_INTERACTION);
    }


    /**
     * Initialises the action of the link. If the attribute 'action' is
     * not null, nothing will be done.
     * @since 0.2
     */
    protected void createAction() {
        try{
            action = clazzAction.newInstance();
        }catch(InstantiationException e){
            ErrorCatcher.INSTANCE.reportError(e);
        }catch(IllegalAccessException e){
            ErrorCatcher.INSTANCE.reportError(e);
        }
    }


    /**
     * After being created by method createAction, the action must be initialised
     * by this method.
     * @since 0.2
     */
    public abstract void initAction();



    /**
     * Updates the current action. To override.
     * @since 0.2
     */
    public void updateAction() {
        // to override.
    }


    /**
     * @return True if the condition of the link is respected.
     */
    public abstract boolean isConditionRespected();


    /**
     * @return The action in progress or null.
     */
    public A getAction() {
        return action;
    }


    /**
     * @return True if the link is activated.
     */
    public boolean isActivated() {
        return getPortByName("instrumentServ", InstrumentService.class).isActivated();
    }


    /**
     * Indicates if the link can be run. To be run, no link, of the instrument, that produces the
     * same type of action must be running.
     * @return True: The link can be run.
     */
    public boolean isRunnable() {
        InstrumentService service = getPortByName("instrumentServ", InstrumentService.class);

        for(LinkComponent<?> link : service.getLinks())
            if(link!=this && link.isRunning() && link.clazzAction==clazzAction)
                return false;

        return true;
    }



    /**
     * @return True: if the link is currently used.
     * since 0.2
     */
    public boolean isRunning() {
        return getPortByName("interactionServ", InteractionService.class).isRunning();
    }


    /**
     * Sometimes the interaction of two different links can overlap themselves. It provokes
     * that the first interaction can stops while the second is blocked in a intermediary state.
     * Two solutions are possible to avoid such a problem:<br>
     * - the use of this function that perform some tests. If the test fails, the starting interaction
     * is aborted and the resulting action is never created;<br>
     * - the modification of one of the interactions to avoid the overlapping.
     * @return True: if the starting interaction must be aborted so that the action is never created.
     * @since 0.2
     */
    public boolean isInteractionMustBeAborted() {
        return false;
    }


    @Port(name="interactionMsg")
    public void onInteractionChange(Object obj) {
        InteractionComponent inter = (InteractionComponent)((Object[])obj)[0];
        InteractionMsg type = (InteractionMsg)((Object[])obj)[1];

        if(InteractionMsg.START==type)
            interactionAborts(inter);
        else if(InteractionMsg.STOP==type)
            interactionStops(inter);
        else if(InteractionMsg.UPDATE==type)
            interactionStops(inter);
        else if(InteractionMsg.ABORT==type)
            interactionStops(inter);
    }


    public void interactionAborts(final InteractionComponent inter) {
        if(action!=null) {
            action.abort();

            InstrumentService service = getPortByName("instrumentServ", InstrumentService.class);

            // The instrument is notified about the aborting of the action.
            service.onActionAborted(action);

            if(isExecute())
                if(action instanceof Undoable)
                    ((Undoable)action).undo();
                else throw new MustBeUndoableActionException(action.getClass());

            action = null;
            service.interimFeedback();
        }
    }



    public void interactionStarts(final InteractionComponent inter) throws MustAbortStateMachineException {
        if(isInteractionMustBeAborted())
            throw new MustAbortStateMachineException();

        if(isRunnable() && action==null && getPortByName("instrumentServ", InstrumentService.class).isActivated() &&
            isConditionRespected()) {
            createAction();
            initAction();
            interimFeedback();
        }
    }



    public void interactionStops(final InteractionComponent inter) {
        if(action!=null) {
            InstrumentService service = getPortByName("instrumentServ", InstrumentService.class);

            if(isConditionRespected()) {
                if(!execute)
                    updateAction();

                if(action.doIt()) {
                    service.onActionExecuted(action);
                    action.done();
                    service.onActionDone(action);
                }

                if(action.hadEffect()) {
                    if(action.isRegisterable()) {
                        ActionsRegistry.INSTANCE.addAction(action, service.getInstrument());
                        service.onActionAdded(action);
                    }
                    else {
                        ActionsRegistry.INSTANCE.cancelActions(action);
                        service.onActionCancelled(action);
                    }
                }

                action = null;
            }
            else {
                action.abort();
                service.onActionAborted(action);
                action = null;
            }
            service.interimFeedback();
        }
    }



    public void interactionUpdates(final InteractionComponent inter) {
        if(isConditionRespected()) {
            if(action==null) {
                createAction();
                initAction();
            }

            updateAction();

            if(execute && action.canDo()) {
                action.doIt();
                getPortByName("instrumentServ", InstrumentService.class).onActionExecuted(action);
            }

            interimFeedback();
        }
    }


    @Start
    public void start() throws Exception {

    }

    @Stop
    public void stop() {
        setActivated(false);
    }


    /**
     * @return True if the action is executed on each evolution
     * of the interaction.
     */
    public boolean isExecute() {
        return execute;
    }


    /**
     * Defines the interim feedback of the link. If overridden, the interim
     * feedback of its instrument should be define too.
     */
    public void interimFeedback() {
        //
    }


    /**
     * Activates the link.
     * @param activated True: the link is activated. Otherwise, it is desactivated.
     * @since 3.0
     */
    public void setActivated(final boolean activated) {
        getPortByName("linkMsg", MessagePort.class).process(
                activated ? LinkMsg.ACTIVATE : LinkMsg.DEACTIVATE);
    }
}
