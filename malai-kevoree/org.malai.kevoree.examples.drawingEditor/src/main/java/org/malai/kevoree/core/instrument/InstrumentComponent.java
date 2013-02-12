package org.malai.kevoree.core.instrument;

import org.kevoree.annotation.*;
import org.kevoree.framework.AbstractComponentType;
import org.malai.action.Action;
import org.malai.action.ActionHandler;
import org.malai.interaction.Eventable;
import org.malai.preferences.Preferenciable;
import org.malai.properties.Modifiable;
import org.malai.properties.Reinitialisable;
import org.malai.undo.Undoable;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: ablouin
 * Date: 17/07/12
 * Time: 11:19
 * To change this template use File | Settings | File Templates.
 */
@Library(name = "Malai")
@Provides({
        @ProvidedPort(name = "instrumentServ", type = PortType.SERVICE, className = InstrumentService.class)
})
@Requires({
        @RequiredPort(name = "instrumentMsg", type = PortType.MESSAGE)
})
@ComponentFragment
public abstract class InstrumentComponent extends AbstractComponentType implements Preferenciable,
        Modifiable, Reinitialisable, ActionHandler, InstrumentService {
    /**  Defines if the instrument is activated or not. */
    protected boolean activated;

    /**  The links of the instrument. */
    protected List<LinkComponent<?>> links;

    /** Defined if the instrument has been modified. */
    protected boolean modified;

    /** The eventable objects that the instrument uses. */
    protected List<Eventable> eventables;


    /**
     * Creates and initialises the instrument.
     * @since 0.1
     */
    public InstrumentComponent() {
        activated = false;
        modified  = false;
        links	  = new ArrayList<LinkComponent<?>>();
    }


    @Start
    public void start() throws Exception {

    }

    @Stop
    public void stop() {
        setActivated(false);
    }


    /**
     * @return The number of links that compose the instrument.
     * @since 0.2
     */
    public int getSizeLinks() {
        return links.size();
    }


    /**
     * @return True: the instrument has at least one link. False otherwise.
     * @since 0.2
     */
    public boolean hasLinks() {
        return getSizeLinks()>0;
    }


    /**
     * @return The links that compose the instrument. Cannot be null.
     * @since 0.2
     */
    @Port(name = "instrumentServ",method = "getLinks")
    public List<LinkComponent<?>> getLinks() {
        return links;
    }


    /**
     * Initialises the links of the instrument.
     * @since 0.2
     */
    protected abstract void initialiseLinks();


    @Port(name="instrumentMsg")
    public void onInstrumentMessage(Object obj) {
        Object[] objs = (Object[]) obj;
        if(InstrumentMsg.LINK_ADDED==objs[0])
            addLink((LinkComponent<?>)objs[1]);
        else if(InstrumentMsg.LINK_REMOVED==objs[0])
            removeLink((LinkComponent<?>)objs[1]);
        else if(InstrumentMsg.EVENTABLE_ADDED==objs[0])
            addEventable((Eventable)objs[1]);
    }


    protected void addLink(LinkComponent<?> link) {
        if(link!=null) {
            links.add(link);

            if(eventables!=null)
                for(final Eventable eventable : eventables)
                    link.addEventable(eventable);
        }
    }


    /**
     * Removes the given link from the list of links of the instrument.
     * @param link The link to remove.
     * @return True: the given link has been removed. False otherwise.
     * @since 0.2
     */
    protected boolean removeLink(final LinkComponent<?> link) {
        return link==null ? false : links.remove(link);
    }


    /**
     * Binds the interaction of the links of the instrument to a Eventable object that produces
     * events used by the interactions.
     * @param eventable The eventable object that gathers event used by the interactions.
     * @since 0.2
     */
    protected void addEventable(final Eventable eventable) {
        if(eventable!=null) {
            if(eventables==null)
                eventables = new ArrayList<Eventable>();

            eventables.add(eventable);

            for(LinkComponent<?> link : links)
                link.addEventable(eventable);
        }
    }


    /**
     * Stops the interactions of the instrument and clears all its events waiting for a process.
     * @since 0.2
     */
    public void clearEvents() {
        for(final LinkComponent<?> link : links)
            link.clearEvents();
    }


    /**
     * @return True if the instrument is activated.
     */
    @Port(name = "instrumentServ",method = "isActivated")
    public boolean isActivated() {
        return activated;
    }


    /**
     * Activates or deactivates the instrument.
     * @param activated True = activation.
     */
    public void setActivated(final boolean activated) {
        this.activated = activated;

        if(activated && !hasLinks())
            initialiseLinks();
        else
            for(final LinkComponent<?> link : links)
                link.setActivated(activated);

        interimFeedback();
    }



    /**
     * Reinitialises the interim feedback of the instrument.
     * Must be overridden.
     */
    @Port(name = "instrumentServ",method = "interimFeedback")
    public void interimFeedback() {
        // Nothing to do
    }



    @Override
    public void save(final boolean generalPreferences, final String nsURI, final Document document, final Element root) {
        // Should be overridden.
    }



    @Override
    public void load(final boolean generalPreferences, final String nsURI, final Element meta) {
        // Should be overridden.
    }


    @Override
    public void setModified(final boolean modified) {
        this.modified = modified;
    }


    @Override
    public boolean isModified() {
        return modified;
    }


    @Override
    public void reinit() {
        // Should be overridden.
    }


    @Override
    public void onUndoableAdded(final Undoable undoable) {
        // Should be overridden.
    }

    @Override
    public void onUndoableUndo(final Undoable undoable) {
        // Should be overridden.
    }

    @Override
    public void onUndoableRedo(final Undoable undoable) {
        // Should be overridden.
    }

    @Override
    @Port(name = "instrumentServ",method = "onActionCancelled")
    public void onActionCancelled(final Action action) {
        // Should be overridden.
    }

    @Override
    @Port(name = "instrumentServ",method = "onActionAdded")
    public void onActionAdded(final Action action) {
        // Should be overridden.
    }

    @Override
    @Port(name = "instrumentServ",method = "onActionAborted")
    public void onActionAborted(final Action action) {
        // Should be overridden.
    }

    @Override
    @Port(name = "instrumentServ",method = "onActionExecuted")
    public void onActionExecuted(final Action action) {
        // Should be overridden.
    }

    @Override
    @Port(name = "instrumentServ",method = "onActionDone")
    public void onActionDone(final Action action) {
        // Should be overridden.
    }

    @Port(name = "instrumentServ",method = "getInstrument")
    public InstrumentComponent getInstrument() {
        return this;
    }
}
