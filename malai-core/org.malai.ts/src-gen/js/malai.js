var malai;
(function (malai) {
    var ActionStatus;
    (function (ActionStatus) {
        ActionStatus[ActionStatus["CREATED"] = 0] = "CREATED";
        ActionStatus[ActionStatus["EXECUTED"] = 1] = "EXECUTED";
        ActionStatus[ActionStatus["ABORTED"] = 2] = "ABORTED";
        ActionStatus[ActionStatus["DONE"] = 3] = "DONE";
        ActionStatus[ActionStatus["FLUSHED"] = 4] = "FLUSHED";
    })(ActionStatus = malai.ActionStatus || (malai.ActionStatus = {}));
})(malai || (malai = {}));
var malai;
(function (malai) {
    var UndoCollector = malai.UndoCollector;
    class ActionsRegistry {
        constructor() {
            this.sizeMax = 0;
            this.actions = [];
            this.handlers = [];
            this.sizeMax = 30;
        }
        onActionExecuted(action) {
            if (action != null) {
                this.handlers.forEach(handler => handler.onActionExecuted(action));
            }
        }
        onActionDone(action) {
            if (action != null) {
                this.handlers.forEach(handler => handler.onActionDone(action));
            }
        }
        getActions() {
            return this.actions;
        }
        unregisterActions(action) {
            if (action == null)
                return;
            let i = 0;
            while (i < this.actions.length) {
                if (this.actions[i].unregisteredBy(action)) {
                    let act = this.actions[i];
                    this.actions.splice(i, 1);
                    this.handlers.forEach(handler => handler.onActionCancelled(act));
                    act.flush();
                }
                else {
                    i++;
                }
            }
        }
        addAction(action, actionHandler) {
            if (action != null && actionHandler != null && this.actions.indexOf(action) === -1 && this.sizeMax > 0) {
                this.unregisterActions(action);
                if (this.actions.length === this.sizeMax) {
                    this.actions[0].flush();
                    this.actions.splice(0, 1);
                }
                this.actions.push(action);
                this.handlers.forEach(handler => handler.onActionAdded(action));
                if (malai.isUndoableInstance(action)) {
                    UndoCollector.INSTANCE.add(action, actionHandler);
                }
            }
        }
        removeAction(action) {
            if (action == null)
                return;
            let index = this.actions.indexOf(action);
            if (index !== -1) {
                this.actions.splice(index, 1);
                action.flush();
            }
        }
        addHandler(handler) {
            if (handler != null) {
                this.handlers.push(handler);
            }
        }
        removeHandler(handler) {
            if (handler != null) {
                let index = this.handlers.indexOf(handler);
                if (index !== -1) {
                    this.handlers.splice(index, 1);
                }
            }
        }
        removeAllHandlers() {
            this.handlers.length = 0;
        }
        clear() {
            this.actions.forEach(action => action.flush());
            this.actions.length = 0;
        }
        abortAction(action) {
            if (action != null) {
                action.abort();
                let index = this.actions.indexOf(action);
                if (index !== -1) {
                    this.actions.splice(index, 1);
                    this.handlers.forEach(handler => handler.onActionAborted(action));
                }
                action.flush();
            }
        }
        getSizeMax() {
            return this.sizeMax;
        }
        setSizeMax(newSizeMax) {
            if (newSizeMax >= 0) {
                if (newSizeMax < this.sizeMax) {
                    let removed = this.actions.splice(0, this.actions.length - newSizeMax);
                    removed.forEach(action => action.flush());
                }
                this.sizeMax = newSizeMax;
            }
        }
    }
    ActionsRegistry.INSTANCE = new ActionsRegistry();
    malai.ActionsRegistry = ActionsRegistry;
})(malai || (malai = {}));
var malai;
(function (malai) {
    class StateImpl {
        constructor(name) {
            this.name = name;
            this.transitions = [];
        }
        setInteraction(newInteraction) {
            this.interaction = newInteraction;
        }
        getInteraction() {
            return this.interaction;
        }
        addTransition(trans) {
            if (trans != null) {
                this.transitions.push(trans);
            }
        }
        getName() {
            return this.name;
        }
        getTransitions() {
            return this.transitions;
        }
        getTransition(i) {
            if (i > 0 && i <= this.transitions.length) {
                return this.transitions[i];
            }
            return;
        }
    }
    malai.StateImpl = StateImpl;
})(malai || (malai = {}));
var malai;
(function (malai) {
    class AbortingState extends malai.StateImpl {
        constructor(name) {
            super(name);
        }
        onIngoing() {
            this.interaction.onAborting();
        }
    }
    malai.AbortingState = AbortingState;
})(malai || (malai = {}));
var malai;
(function (malai) {
    class InitState extends malai.StateImpl {
        constructor() {
            super("Init");
        }
        onOutgoing() {
            this.interaction.onStarting();
        }
    }
    malai.InitState = InitState;
})(malai || (malai = {}));
var malai;
(function (malai) {
    class InteractionImpl {
        constructor(initState) {
            this.currentTimeout = undefined;
            this.activated = true;
            this.states = [];
            this.stillProcessingEvents = [];
            this.initState.setInteraction(this);
            this.initState = initState;
            this.addState(initState);
            this.reinit();
        }
        setActivated(activated) {
            this.activated = activated;
            if (!activated) {
                this.reinit();
                this.clearEventsStillInProcess();
            }
        }
        isActivated() {
            return this.activated;
        }
        getCurrentState() {
            return this.currentState;
        }
        reinit() {
            if (this.currentTimeout != null) {
                this.currentTimeout.stopTimeout();
            }
            this.currentTimeout = undefined;
            this.currentState = this.initState;
        }
        getHandlers() {
            return this.handlers;
        }
        addHandler(handler) {
            if (handler != null) {
                this.handlers.push(handler);
            }
        }
        notifyHandlersOnStart() {
            try {
                this.handlers.forEach(handler => handler.interactionStarts(this));
            }
            catch (ex) {
                this.notifyHandlersOnAborting();
                throw ex;
            }
        }
        notifyHandlersOnUpdate() {
            try {
                this.handlers.forEach(handler => handler.interactionUpdates(this));
            }
            catch (ex) {
                this.notifyHandlersOnAborting();
                throw ex;
            }
        }
        notifyHandlersOnStop() {
            try {
                this.handlers.forEach(handler => handler.interactionStops(this));
            }
            catch (ex) {
                this.notifyHandlersOnAborting();
                throw ex;
            }
        }
        notifyHandlersOnAborting() {
            this.handlers.forEach(handler => handler.interactionAborts(this));
        }
        addState(state) {
            if (state != null) {
                this.states.push(state);
                state.setInteraction(this);
            }
        }
        isRunning() {
            return this.activated && this.currentState !== this.initState;
        }
        executeTransition(transition) {
            if (this.activated && transition != null) {
                try {
                    transition.action();
                    transition.getInputState().onOutgoing();
                    this.currentState = transition.getOutputState();
                    transition.getOutputState().onIngoing();
                }
                catch (ex) {
                    this.reinit();
                }
            }
        }
        stopCurrentTimeout() {
            if (this.currentTimeout != null) {
                this.currentTimeout.stopTimeout();
                this.currentTimeout = undefined;
            }
        }
        checkTransition(transition) {
            let ok;
            if (transition != null && transition.isGuardRespected()) {
                this.stopCurrentTimeout();
                this.executeTransition(transition);
                ok = true;
            }
            else {
                ok = false;
            }
            return ok;
        }
        onTimeout(timeoutTransition) {
            if (this.activated && timeoutTransition != null) {
                this.executeTransition(timeoutTransition);
            }
        }
        processEvents() {
            while (this.stillProcessingEvents.length > 0) {
                this.processEvent(this.stillProcessingEvents.splice(0, 1)[0]);
            }
        }
        onTerminating() {
            this.notifyHandlersOnStop();
            this.reinit();
            this.processEvents();
        }
        onAborting() {
            this.notifyHandlersOnAborting();
            this.reinit();
            this.clearEventsStillInProcess();
        }
        onStarting() {
            this.notifyHandlersOnStart();
            this.checkTimeoutTransition();
        }
        onUpdating() {
            this.notifyHandlersOnUpdate();
            this.checkTimeoutTransition();
        }
        checkTimeoutTransition() {
            let again = true;
            for (let i = 0, j = this.currentState.getTransitions().length; i < j && again; i++) {
                let transition = this.currentState.getTransition(i);
                if (transition instanceof malai.TimeoutTransition) {
                    this.currentTimeout = transition;
                    again = false;
                    this.currentTimeout.startTimeout();
                }
            }
        }
        clearEventsStillInProcess() {
            if (this.stillProcessingEvents != null) {
                this.stillProcessingEvents.length = 0;
            }
        }
        clearEvents() {
            this.reinit();
            this.clearEventsStillInProcess();
        }
    }
    malai.InteractionImpl = InteractionImpl;
})(malai || (malai = {}));
var malai;
(function (malai) {
    class IntermediaryState extends malai.StateImpl {
        constructor(name) {
            super(name);
        }
        onIngoing() {
            this.interaction.onUpdating();
        }
        onOutgoing() {
        }
    }
    malai.IntermediaryState = IntermediaryState;
})(malai || (malai = {}));
var malai;
(function (malai) {
    class TerminalState extends malai.StateImpl {
        constructor(name) {
            super(name);
        }
        onIngoing() {
            this.interaction.onTerminating();
        }
    }
    malai.TerminalState = TerminalState;
})(malai || (malai = {}));
var malai;
(function (malai) {
    class TransitionImpl {
        constructor(inputState, outputState) {
            this.inputState = inputState;
            this.outputState = outputState;
            this.inputState.addTransition(this);
        }
        action() {
        }
        isGuardRespected() {
            return true;
        }
        getInputState() {
            return this.inputState;
        }
        getOutputState() {
            return this.outputState;
        }
    }
    malai.TransitionImpl = TransitionImpl;
})(malai || (malai = {}));
var malai;
(function (malai) {
    class TimeoutTransition extends malai.TransitionImpl {
        constructor(inputState, outputState, timeout) {
            super(inputState, outputState);
            this.timeout = timeout;
            this.timeoutInProgress = false;
        }
        startTimeout() {
            if (!this.timeoutInProgress) {
                this.timeoutInProgress = true;
                setInterval(() => {
                    if (this.timeoutInProgress) {
                        this.getInputState().getInteraction().onTimeout(this);
                        this.timeoutInProgress = false;
                    }
                }, this.timeout);
            }
        }
        stopTimeout() {
            this.timeoutInProgress = false;
        }
        setTimeout(timeout) {
            if (timeout > 0) {
                this.timeout = timeout;
            }
        }
        getTimeout() {
            return this.timeout;
        }
    }
    malai.TimeoutTransition = TimeoutTransition;
})(malai || (malai = {}));
var malai;
(function (malai) {
    class EmptyUndoHandler {
        constructor() {
        }
        onUndoableCleared() {
        }
        onUndoableAdded(_undoable) {
        }
        onUndoableUndo(_undoable) {
        }
        onUndoableRedo(_undoable) {
        }
    }
    malai.EmptyUndoHandler = EmptyUndoHandler;
})(malai || (malai = {}));
var malai;
(function (malai) {
    function isUndoableInstance(obj) {
        return obj.undo !== undefined && obj.redo !== undefined && obj.getUndoName !== undefined;
    }
    malai.isUndoableInstance = isUndoableInstance;
})(malai || (malai = {}));
var malai;
(function (malai) {
    class UndoCollector {
        constructor() {
            this.sizeMax = 0;
            this.handlers = [];
            this.undoables = [];
            this.redoables = [];
            this.undoHandlers = [];
            this.redoHandlers = [];
            this.sizeMax = 30;
        }
        addHandler(handler) {
            if (handler != null) {
                this.handlers.push(handler);
            }
        }
        removeHandler(handler) {
            if (handler != null) {
                let index = this.handlers.indexOf(handler);
                if (index != -1) {
                    this.handlers.splice(index, 1);
                }
            }
        }
        clear() {
            this.undoables.length = 0;
            this.redoables.length = 0;
            this.undoHandlers.length = 0;
            this.redoHandlers.length = 0;
            this.handlers.forEach(handler => handler.onUndoableCleared());
        }
        add(undoable, undoHandler) {
            if (undoable != null && this.sizeMax > 0) {
                if (this.undoables.length === this.sizeMax) {
                    this.undoables.pop();
                    this.undoHandlers.pop();
                }
                this.undoables.push(undoable);
                if (undoHandler == null) {
                    this.undoHandlers.push(UndoCollector.STUB_UNDO_HANDLER);
                }
                else {
                    this.undoHandlers.push(undoHandler);
                }
                this.redoables.length = 0;
                this.redoHandlers.length = 0;
                this.handlers.forEach(handler => handler.onUndoableAdded(undoable));
            }
        }
        undo() {
            if (this.undoables.length > 0) {
                let undoable = this.undoables.pop();
                let undoHandler = this.undoHandlers.pop();
                if (undoable != null && undoHandler != null) {
                    let realUndoable = undoable;
                    undoable.undo();
                    this.redoables.push(undoable);
                    this.redoHandlers.push(undoHandler);
                    undoHandler.onUndoableUndo(undoable);
                    this.handlers.forEach(handler => handler.onUndoableUndo(realUndoable));
                }
            }
        }
        redo() {
            if (this.redoables.length > 0) {
                let undoable = this.redoables.pop();
                let redoHandler = this.redoHandlers.pop();
                if (undoable != null && redoHandler != null) {
                    let realUndoable = undoable;
                    undoable.redo();
                    this.undoables.push(undoable);
                    this.undoHandlers.push(redoHandler);
                    redoHandler.onUndoableRedo(undoable);
                    this.handlers.forEach(handler => handler.onUndoableRedo(realUndoable));
                }
            }
        }
        getLastUndoMessage() {
            if (this.undoables.length > 0) {
                return this.undoables[this.undoables.length - 1].getUndoName();
            }
            return;
        }
        getLastRedoMessage() {
            if (this.redoables.length > 0) {
                return this.redoables[this.redoables.length - 1].getUndoName();
            }
            return;
        }
        getLastUndo() {
            if (this.undoables.length > 0) {
                return this.undoables[this.undoables.length - 1];
            }
            return;
        }
        getLastRedo() {
            if (this.redoables.length > 0) {
                return this.redoables[this.redoables.length - 1];
            }
            return;
        }
        getSizeMax() {
            return this.sizeMax;
        }
        setSizeMax(max) {
            if (max >= 0) {
                if (max < this.sizeMax) {
                    this.undoables.splice(0, this.sizeMax - max);
                    this.undoHandlers.splice(0, this.sizeMax - max);
                }
                this.sizeMax = max;
            }
        }
        getUndos() {
            return this.undoables;
        }
        getRedos() {
            return this.redoables;
        }
    }
    UndoCollector.INSTANCE = new UndoCollector();
    UndoCollector.EMPTY_REDO = "redo";
    UndoCollector.EMPTY_UNDO = "undo";
    UndoCollector.STUB_UNDO_HANDLER = new malai.EmptyUndoHandler();
    malai.UndoCollector = UndoCollector;
})(malai || (malai = {}));
