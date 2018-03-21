System.register("src-core/action/Action", [], function (exports_1, context_1) {
    "use strict";
    var __moduleName = context_1 && context_1.id;
    var RegistrationPolicy, ActionStatus;
    return {
        setters: [],
        execute: function () {
            (function (RegistrationPolicy) {
                RegistrationPolicy[RegistrationPolicy["NONE"] = 0] = "NONE";
                RegistrationPolicy[RegistrationPolicy["UNLIMITED"] = 1] = "UNLIMITED";
                RegistrationPolicy[RegistrationPolicy["LIMITED"] = 2] = "LIMITED";
            })(RegistrationPolicy || (RegistrationPolicy = {}));
            exports_1("RegistrationPolicy", RegistrationPolicy);
            (function (ActionStatus) {
                ActionStatus[ActionStatus["CREATED"] = 0] = "CREATED";
                ActionStatus[ActionStatus["EXECUTED"] = 1] = "EXECUTED";
                ActionStatus[ActionStatus["CANCELLED"] = 2] = "CANCELLED";
                ActionStatus[ActionStatus["DONE"] = 3] = "DONE";
                ActionStatus[ActionStatus["FLUSHED"] = 4] = "FLUSHED";
            })(ActionStatus || (ActionStatus = {}));
            exports_1("ActionStatus", ActionStatus);
        }
    };
});
System.register("src-core/undo/Undoable", [], function (exports_2, context_2) {
    "use strict";
    var __moduleName = context_2 && context_2.id;
    function isUndoableType(obj) {
        return obj.undo !== undefined;
    }
    exports_2("isUndoableType", isUndoableType);
    return {
        setters: [],
        execute: function () {
        }
    };
});
System.register("src-core/undo/UndoHandler", [], function (exports_3, context_3) {
    "use strict";
    var __moduleName = context_3 && context_3.id;
    return {
        setters: [],
        execute: function () {
        }
    };
});
System.register("src-core/action/ActionHandler", [], function (exports_4, context_4) {
    "use strict";
    var __moduleName = context_4 && context_4.id;
    return {
        setters: [],
        execute: function () {
        }
    };
});
System.register("src/util/Optional", [], function (exports_5, context_5) {
    "use strict";
    var __moduleName = context_5 && context_5.id;
    var Optional;
    return {
        setters: [],
        execute: function () {
            Optional = class Optional {
                constructor(obj) {
                    this.value = obj;
                }
                static empty() {
                    return Optional.EMPTY;
                }
                static of(obj) {
                    return new Optional(obj);
                }
                static ofNullable(obj) {
                    return obj === undefined ? Optional.empty() : Optional.of(obj);
                }
                get() {
                    return this.value;
                }
                isPresent() {
                    return this.value !== undefined;
                }
                ifPresent(lambda) {
                    if (this.value !== undefined) {
                        lambda(this.value);
                    }
                }
                filter(predicate) {
                    if (!this.isPresent()) {
                        return this;
                    }
                    else {
                        return predicate(this.value) ? this : Optional.empty();
                    }
                }
                map(fct) {
                    return !this.isPresent() ? Optional.empty() : Optional.ofNullable(fct(this.value));
                }
            };
            Optional.EMPTY = new Optional();
            exports_5("Optional", Optional);
        }
    };
});
System.register("src-core/undo/EmptyUndoHandler", [], function (exports_6, context_6) {
    "use strict";
    var __moduleName = context_6 && context_6.id;
    var EmptyUndoHandler;
    return {
        setters: [],
        execute: function () {
            EmptyUndoHandler = class EmptyUndoHandler {
                constructor() {
                }
                onUndoableCleared() {
                }
                onUndoableAdded(undoable) {
                }
                onUndoableUndo(undoable) {
                }
                onUndoableRedo(undoable) {
                }
            };
            exports_6("EmptyUndoHandler", EmptyUndoHandler);
        }
    };
});
System.register("src-core/undo/UndoCollector", ["src/util/Optional", "src-core/undo/EmptyUndoHandler"], function (exports_7, context_7) {
    "use strict";
    var __moduleName = context_7 && context_7.id;
    var Optional_1, EmptyUndoHandler_1, UndoCollector;
    return {
        setters: [
            function (Optional_1_1) {
                Optional_1 = Optional_1_1;
            },
            function (EmptyUndoHandler_1_1) {
                EmptyUndoHandler_1 = EmptyUndoHandler_1_1;
            }
        ],
        execute: function () {
            UndoCollector = class UndoCollector {
                constructor() {
                    this.sizeMax = 0;
                    this.handlers = [];
                    this.undos = [];
                    this.redos = [];
                    this.undoHandlers = [];
                    this.redoHandlers = [];
                    this.sizeMax = 30;
                }
                addHandler(handler) {
                    if (handler !== undefined) {
                        this.handlers.push(handler);
                    }
                }
                removeHandler(handler) {
                    if (handler !== undefined) {
                        this.handlers.remove(handler);
                    }
                }
                getHandlers() {
                    return [...this.handlers];
                }
                clearHandlers() {
                    this.handlers.clear();
                }
                clear() {
                    this.undos.clear();
                    this.redos.clear();
                    this.undoHandlers.clear();
                    this.redoHandlers.clear();
                    this.handlers.forEach(handler => handler.onUndoableCleared());
                }
                add(undoable, undoHandler) {
                    if (undoable !== undefined && this.sizeMax > 0) {
                        if (this.undos.length === this.sizeMax) {
                            this.undos.pop();
                            this.undoHandlers.pop();
                        }
                        this.undos.push(undoable);
                        if (undoHandler === undefined) {
                            this.undoHandlers.push(UndoCollector.STUB_UNDO_HANDLER);
                        }
                        else {
                            this.undoHandlers.push(undoHandler);
                        }
                        this.redos.length = 0;
                        this.redoHandlers.length = 0;
                        this.handlers.forEach(handler => handler.onUndoableAdded(undoable));
                    }
                }
                undo() {
                    const undoable = this.undos.pop();
                    const undoHandler = this.undoHandlers.pop();
                    if (undoable !== undefined && undoHandler !== undefined) {
                        undoable.undo();
                        this.redos.push(undoable);
                        this.redoHandlers.push(undoHandler);
                        undoHandler.onUndoableUndo(undoable);
                        this.handlers.forEach(handler => handler.onUndoableUndo(undoable));
                    }
                }
                redo() {
                    const undoable = this.redos.pop();
                    const redoHandler = this.redoHandlers.pop();
                    if (undoable !== undefined && redoHandler !== undefined) {
                        undoable.redo();
                        this.undos.push(undoable);
                        this.undoHandlers.push(redoHandler);
                        redoHandler.onUndoableRedo(undoable);
                        this.handlers.forEach(handler => handler.onUndoableRedo(undoable));
                    }
                }
                getLastUndoMessage() {
                    return this.undos.isEmpty() ? Optional_1.Optional.empty() : Optional_1.Optional.ofNullable(this.undos.peek()).map(o => o.getUndoName());
                }
                getLastRedoMessage() {
                    return this.redos.isEmpty() ? Optional_1.Optional.empty() : Optional_1.Optional.ofNullable(this.redos.peek()).map(o => o.getUndoName());
                }
                getLastUndo() {
                    return this.undos.isEmpty() ? Optional_1.Optional.empty() : Optional_1.Optional.ofNullable(this.undos.peek());
                }
                getLastRedo() {
                    return this.redos.isEmpty() ? Optional_1.Optional.empty() : Optional_1.Optional.ofNullable(this.redos.peek());
                }
                getSizeMax() {
                    return this.sizeMax;
                }
                setSizeMax(max) {
                    if (max >= 0) {
                        for (let i = 0, nb = this.undos.length - max; i < nb; i++) {
                            this.undos.pop();
                            this.undoHandlers.pop();
                        }
                        this.sizeMax = max;
                    }
                }
                getUndo() {
                    return this.undos;
                }
                getRedo() {
                    return this.redos;
                }
            };
            UndoCollector.INSTANCE = new UndoCollector();
            UndoCollector.EMPTY_REDO = "redo";
            UndoCollector.EMPTY_UNDO = "undo";
            UndoCollector.STUB_UNDO_HANDLER = new EmptyUndoHandler_1.EmptyUndoHandler();
            exports_7("UndoCollector", UndoCollector);
        }
    };
});
System.register("src-core/action/ActionsRegistry", ["src-core/action/Action", "src-core/undo/Undoable", "src-core/undo/UndoCollector"], function (exports_8, context_8) {
    "use strict";
    var __moduleName = context_8 && context_8.id;
    var Action_1, Undoable_1, UndoCollector_1, ActionsRegistry;
    return {
        setters: [
            function (Action_1_1) {
                Action_1 = Action_1_1;
            },
            function (Undoable_1_1) {
                Undoable_1 = Undoable_1_1;
            },
            function (UndoCollector_1_1) {
                UndoCollector_1 = UndoCollector_1_1;
            }
        ],
        execute: function () {
            ActionsRegistry = class ActionsRegistry {
                constructor() {
                    this.actions = [];
                    this.handlers = [];
                    this.sizeMax = 50;
                }
                getHandlers() {
                    return [...this.handlers];
                }
                onActionExecuted(action) {
                    if (action !== undefined) {
                        this.handlers.forEach(handler => handler.onActionExecuted(action));
                    }
                }
                onActionDone(action) {
                    if (action !== undefined) {
                        this.handlers.forEach(handler => handler.onActionDone(action));
                    }
                }
                getActions() {
                    return this.actions;
                }
                unregisterActions(action) {
                    if (action === undefined) {
                        return;
                    }
                    let i = 0;
                    while ((i < this.actions.length)) {
                        if (this.actions[i].unregisteredBy(action)) {
                            const delAction = this.actions.removeAt(i);
                            if (delAction !== undefined) {
                                delAction.flush();
                            }
                        }
                        else {
                            i++;
                        }
                    }
                }
                addAction(action, actionHandler) {
                    if (action !== undefined && this.actions.indexOf(action) < 0 &&
                        (this.sizeMax > 0 || action.getRegistrationPolicy() === Action_1.RegistrationPolicy.UNLIMITED)) {
                        this.unregisterActions(action);
                        if (this.actions.length >= this.sizeMax) {
                            const act = this.actions.find(a => a.getRegistrationPolicy() !== Action_1.RegistrationPolicy.UNLIMITED);
                            if (act) {
                                this.actions.remove(act);
                                act.flush();
                            }
                        }
                        this.actions.push(action);
                        this.handlers.forEach(handler => handler.onActionAdded(action));
                        if (Undoable_1.isUndoableType(action)) {
                            UndoCollector_1.UndoCollector.INSTANCE.add(action, actionHandler);
                        }
                    }
                }
                removeAction(action) {
                    if (action !== undefined) {
                        this.actions.remove(action);
                        action.flush();
                    }
                }
                addHandler(handler) {
                    if (handler !== undefined) {
                        this.handlers.push(handler);
                    }
                }
                removeHandler(handler) {
                    if (handler !== undefined) {
                        this.handlers.push(handler);
                    }
                }
                removeAllHandlers() {
                    this.handlers.clear();
                }
                clear() {
                    this.actions.forEach(action => action.flush());
                    this.actions.clear();
                }
                cancelAction(action) {
                    if (action !== undefined) {
                        action.cancel();
                        this.actions.remove(action);
                        this.handlers.forEach(handler => handler.onActionCancelled(action));
                        action.flush();
                    }
                }
                getSizeMax() {
                    return this.sizeMax;
                }
                setSizeMax(newSizeMax) {
                    if (newSizeMax >= 0) {
                        let i = 0;
                        let nb = 0;
                        const toRemove = this.actions.length - newSizeMax;
                        while (nb < toRemove && i < this.actions.length) {
                            if (this.actions[i].getRegistrationPolicy() !== Action_1.RegistrationPolicy.UNLIMITED) {
                                const removed = this.actions.removeAt(i);
                                if (removed) {
                                    removed.flush();
                                }
                                nb++;
                            }
                            else {
                                i++;
                            }
                        }
                        this.sizeMax = newSizeMax;
                    }
                }
            };
            ActionsRegistry.INSTANCE = new ActionsRegistry();
            exports_8("ActionsRegistry", ActionsRegistry);
        }
    };
});
System.register("src-core/action/ActionImpl", ["src-core/action/Action", "src-core/action/ActionsRegistry"], function (exports_9, context_9) {
    "use strict";
    var __moduleName = context_9 && context_9.id;
    var Action_2, ActionsRegistry_1, ActionImpl;
    return {
        setters: [
            function (Action_2_1) {
                Action_2 = Action_2_1;
            },
            function (ActionsRegistry_1_1) {
                ActionsRegistry_1 = ActionsRegistry_1_1;
            }
        ],
        execute: function () {
            ActionImpl = class ActionImpl {
                constructor() {
                    this.status = Action_2.ActionStatus.CREATED;
                }
                flush() {
                    this.status = Action_2.ActionStatus.FLUSHED;
                }
                createMemento() {
                }
                doIt() {
                    let ok;
                    if ((this.status === Action_2.ActionStatus.CREATED || this.status === Action_2.ActionStatus.EXECUTED) && this.canDo()) {
                        if (this.status === Action_2.ActionStatus.CREATED) {
                            this.createMemento();
                        }
                        ok = true;
                        this.doActionBody();
                        this.status = Action_2.ActionStatus.EXECUTED;
                        ActionsRegistry_1.ActionsRegistry.INSTANCE.onActionExecuted(this);
                    }
                    else {
                        ok = false;
                    }
                    return ok;
                }
                getRegistrationPolicy() {
                    return this.hadEffect() ? Action_2.RegistrationPolicy.LIMITED : Action_2.RegistrationPolicy.NONE;
                }
                hadEffect() {
                    return this.isDone();
                }
                unregisteredBy(action) {
                    return false;
                }
                done() {
                    if (this.status === Action_2.ActionStatus.CREATED || this.status === Action_2.ActionStatus.EXECUTED) {
                        this.status = Action_2.ActionStatus.DONE;
                        ActionsRegistry_1.ActionsRegistry.INSTANCE.onActionDone(this);
                    }
                }
                isDone() {
                    return this.status === Action_2.ActionStatus.DONE;
                }
                cancel() {
                    this.status = Action_2.ActionStatus.CANCELLED;
                }
                getStatus() {
                    return this.status;
                }
                followingActions() {
                    return [];
                }
            };
            exports_9("ActionImpl", ActionImpl);
        }
    };
});
System.register("src-core/action/AnonAction", ["src-core/action/ActionImpl"], function (exports_10, context_10) {
    "use strict";
    var __moduleName = context_10 && context_10.id;
    var ActionImpl_1, AnonAction;
    return {
        setters: [
            function (ActionImpl_1_1) {
                ActionImpl_1 = ActionImpl_1_1;
            }
        ],
        execute: function () {
            AnonAction = class AnonAction extends ActionImpl_1.ActionImpl {
                constructor(fct) {
                    super();
                    this.exec = fct;
                }
                canDo() {
                    return this.exec !== undefined;
                }
                doActionBody() {
                    this.exec();
                }
            };
            exports_10("AnonAction", AnonAction);
        }
    };
});
function AutoUnbind() {
}
System.register("src-core/fsm/FSMHandler", [], function (exports_11, context_11) {
    "use strict";
    var __moduleName = context_11 && context_11.id;
    return {
        setters: [],
        execute: function () {
        }
    };
});
System.register("src-core/fsm/State", [], function (exports_12, context_12) {
    "use strict";
    var __moduleName = context_12 && context_12.id;
    return {
        setters: [],
        execute: function () {
        }
    };
});
System.register("src-core/fsm/StateImpl", [], function (exports_13, context_13) {
    "use strict";
    var __moduleName = context_13 && context_13.id;
    var StateImpl;
    return {
        setters: [],
        execute: function () {
            StateImpl = class StateImpl {
                constructor(stateMachine, stateName) {
                    this.fsm = stateMachine;
                    this.name = stateName;
                }
                checkStartingState() {
                    if (!this.fsm.isStarted() && this.fsm.getStartingState() === this) {
                        this.fsm.onStarting();
                    }
                }
                getName() {
                    return this.name;
                }
                getFSM() {
                    return this.fsm;
                }
            };
            exports_13("StateImpl", StateImpl);
        }
    };
});
System.register("src-core/fsm/InputState", [], function (exports_14, context_14) {
    "use strict";
    var __moduleName = context_14 && context_14.id;
    return {
        setters: [],
        execute: function () {
        }
    };
});
System.register("src-core/fsm/Transition", ["src/util/Optional"], function (exports_15, context_15) {
    "use strict";
    var __moduleName = context_15 && context_15.id;
    var Optional_2, Transition;
    return {
        setters: [
            function (Optional_2_1) {
                Optional_2 = Optional_2_1;
            }
        ],
        execute: function () {
            Transition = class Transition {
                constructor(srcState, tgtState) {
                    this.src = srcState;
                    this.tgt = tgtState;
                    this.src.addTransition(this);
                }
                execute(event) {
                    if (this.accept(event) && this.isGuardOK(event)) {
                        this.src.getFSM().stopCurrentTimeout();
                        this.action(event);
                        this.src.exit();
                        this.tgt.enter();
                        return Optional_2.Optional.of(this.tgt);
                    }
                    return Optional_2.Optional.empty();
                }
                action(event) {
                }
            };
            exports_15("Transition", Transition);
        }
    };
});
System.register("src-core/fsm/OutputState", [], function (exports_16, context_16) {
    "use strict";
    var __moduleName = context_16 && context_16.id;
    return {
        setters: [],
        execute: function () {
        }
    };
});
System.register("src-core/fsm/OutputStateImpl", ["src-core/fsm/StateImpl"], function (exports_17, context_17) {
    "use strict";
    var __moduleName = context_17 && context_17.id;
    var StateImpl_1, OutputStateImpl;
    return {
        setters: [
            function (StateImpl_1_1) {
                StateImpl_1 = StateImpl_1_1;
            }
        ],
        execute: function () {
            OutputStateImpl = class OutputStateImpl extends StateImpl_1.StateImpl {
                constructor(stateMachine, stateName) {
                    super(stateMachine, stateName);
                    this.transitions = [];
                }
                process(event) {
                    return this.getTransitions().find(tr => {
                        try {
                            if (tr.execute(event).isPresent()) {
                                return true;
                            }
                        }
                        catch (ignored) {
                        }
                        return false;
                    }) !== undefined;
                }
                checkStartingState() {
                    if (!this.getFSM().isStarted() && this.getFSM().getStartingState() === this) {
                        this.getFSM().onStarting();
                    }
                }
                getTransitions() {
                    return [...this.transitions];
                }
                addTransition(tr) {
                    if (tr !== undefined) {
                        this.transitions.push(tr);
                    }
                }
            };
            exports_17("OutputStateImpl", OutputStateImpl);
        }
    };
});
System.register("src-core/fsm/InitState", ["src-core/fsm/OutputStateImpl"], function (exports_18, context_18) {
    "use strict";
    var __moduleName = context_18 && context_18.id;
    var OutputStateImpl_1, InitState;
    return {
        setters: [
            function (OutputStateImpl_1_1) {
                OutputStateImpl_1 = OutputStateImpl_1_1;
            }
        ],
        execute: function () {
            InitState = class InitState extends OutputStateImpl_1.OutputStateImpl {
                constructor(stateMachine, stateName) {
                    super(stateMachine, stateName);
                }
                exit() {
                    this.checkStartingState();
                }
            };
            exports_18("InitState", InitState);
        }
    };
});
System.register("src-core/utils/ObsValue", [], function (exports_19, context_19) {
    "use strict";
    var __moduleName = context_19 && context_19.id;
    var ObsValue;
    return {
        setters: [],
        execute: function () {
            ObsValue = class ObsValue {
                constructor(value) {
                    this.value = value;
                    this.handlers = [];
                }
                get() {
                    return this.value;
                }
                set(value) {
                    if (value !== undefined) {
                        const oldValue = this.value;
                        this.value = value;
                        this.notifyChange(oldValue, value);
                    }
                }
                notifyChange(oldValue, newValue) {
                    this.handlers.forEach(handler => handler(oldValue, newValue));
                }
                obs(handler) {
                    if (handler !== undefined) {
                        this.handlers.push(handler);
                    }
                }
                unobs(handler) {
                    if (handler !== undefined) {
                        this.handlers.remove(handler);
                    }
                }
            };
            exports_19("ObsValue", ObsValue);
        }
    };
});
System.register("src-core/fsm/TimeoutTransition", ["src-core/fsm/Transition", "src/util/Optional"], function (exports_20, context_20) {
    "use strict";
    var __moduleName = context_20 && context_20.id;
    var Transition_1, Optional_3, TimeoutTransition;
    return {
        setters: [
            function (Transition_1_1) {
                Transition_1 = Transition_1_1;
            },
            function (Optional_3_1) {
                Optional_3 = Optional_3_1;
            }
        ],
        execute: function () {
            TimeoutTransition = class TimeoutTransition extends Transition_1.Transition {
                constructor(srcState, tgtState, timeout) {
                    super(srcState, tgtState);
                    this.timeouted = false;
                    this.timeoutDuration = timeout;
                    this.timeouted = false;
                }
                startTimeout() {
                    if (this.timeoutThread === undefined) {
                        const time = this.timeoutDuration();
                        if (time > 0) {
                            this.timeouted = true;
                            this.src.getFSM().onTimeout();
                        }
                    }
                }
                stopTimeout() {
                    if (this.timeoutThread !== undefined) {
                        this.timeoutThread = undefined;
                    }
                }
                accept(event) {
                    return this.timeouted;
                }
                isGuardOK(event) {
                    return this.timeouted;
                }
                execute(event) {
                    try {
                        if (this.accept(event) && this.isGuardOK(event)) {
                            this.src.exit();
                            this.action(event);
                            this.tgt.enter();
                            this.timeouted = false;
                            return Optional_3.Optional.of(this.tgt);
                        }
                        return Optional_3.Optional.empty();
                    }
                    catch (ex) {
                        this.timeouted = false;
                        throw ex;
                    }
                }
                getAcceptedEvents() {
                    return new Set();
                }
            };
            exports_20("TimeoutTransition", TimeoutTransition);
        }
    };
});
System.register("src-core/fsm/StdState", ["src-core/fsm/OutputStateImpl"], function (exports_21, context_21) {
    "use strict";
    var __moduleName = context_21 && context_21.id;
    var OutputStateImpl_2, StdState;
    return {
        setters: [
            function (OutputStateImpl_2_1) {
                OutputStateImpl_2 = OutputStateImpl_2_1;
            }
        ],
        execute: function () {
            StdState = class StdState extends OutputStateImpl_2.OutputStateImpl {
                constructor(stateMachine, stateName) {
                    super(stateMachine, stateName);
                }
                checkStartingState() {
                    if (!this.getFSM().isStarted() && this.getFSM().getStartingState() === this) {
                        this.getFSM().onStarting();
                    }
                }
                enter() {
                    this.checkStartingState();
                    this.fsm.enterStdState(this);
                }
                exit() {
                }
            };
            exports_21("StdState", StdState);
        }
    };
});
System.register("src-core/logging/ConfigLog", ["typescript-logging"], function (exports_22, context_22) {
    "use strict";
    var __moduleName = context_22 && context_22.id;
    var typescript_logging_1, options, factory;
    return {
        setters: [
            function (typescript_logging_1_1) {
                typescript_logging_1 = typescript_logging_1_1;
            }
        ],
        execute: function () {
            options = new typescript_logging_1.LoggerFactoryOptions()
                .addLogGroupRule(new typescript_logging_1.LogGroupRule(new RegExp("model.+"), typescript_logging_1.LogLevel.Debug))
                .addLogGroupRule(new typescript_logging_1.LogGroupRule(new RegExp(".+"), typescript_logging_1.LogLevel.Info));
            exports_22("factory", factory = typescript_logging_1.LFService.createNamedLoggerFactory("LoggerFactory", options));
        }
    };
});
System.register("src-core/fsm/FSM", ["src-core/fsm/InitState", "src-core/utils/ObsValue", "src-core/fsm/TimeoutTransition", "src-core/logging/ConfigLog"], function (exports_23, context_23) {
    "use strict";
    var __moduleName = context_23 && context_23.id;
    var InitState_1, ObsValue_1, TimeoutTransition_1, ConfigLog_1, FSM;
    return {
        setters: [
            function (InitState_1_1) {
                InitState_1 = InitState_1_1;
            },
            function (ObsValue_1_1) {
                ObsValue_1 = ObsValue_1_1;
            },
            function (TimeoutTransition_1_1) {
                TimeoutTransition_1 = TimeoutTransition_1_1;
            },
            function (ConfigLog_1_1) {
                ConfigLog_1 = ConfigLog_1_1;
            }
        ],
        execute: function () {
            FSM = class FSM {
                constructor() {
                    this.inner = false;
                    this.started = false;
                    this.started = false;
                    this.initState = new InitState_1.InitState(this, "init");
                    this.states = [this.initState];
                    this.startingState = this.initState;
                    this.currentState = new ObsValue_1.ObsValue(this.initState);
                    this.handlers = [];
                    this.eventsToProcess = [];
                }
                setCurrentSubFSM(subFSM) {
                    this.currentSubFSM = subFSM;
                }
                getCurrentState() {
                    return this.currentState.get();
                }
                setInner(inner) {
                    this.inner = inner;
                }
                process(event) {
                    if (event === undefined) {
                        return false;
                    }
                    if (this.currentSubFSM !== undefined) {
                        return this.currentSubFSM.process(event);
                    }
                    return this.currentState.get().process(event);
                }
                enterStdState(state) {
                    this.setCurrentState(state);
                    this.checkTimeoutTransition();
                    if (this.started) {
                        this.onUpdating();
                    }
                }
                isStarted() {
                    return this.started;
                }
                setCurrentState(state) {
                    this.currentState.set(state);
                }
                processRemainingEvents() {
                    if (this.eventsToProcess !== undefined) {
                        const list = [...this.eventsToProcess];
                        while (list.length > 0) {
                            const event = list.removeAt(0);
                            if (event) {
                                this.eventsToProcess.removeAt(0);
                                if (this.logger !== undefined) {
                                    this.logger.info("Recycling event: " + event);
                                }
                                this.process(event);
                            }
                        }
                    }
                }
                addRemaningEventsToProcess(event) {
                    if (event !== undefined) {
                        this.eventsToProcess.push(event);
                    }
                }
                onTerminating() {
                    if (this.logger !== undefined) {
                        this.logger.info("FSM ended");
                    }
                    if (this.started) {
                        this.notifyHandlerOnStop();
                    }
                    this.reinit();
                    this.processRemainingEvents();
                }
                onCancelling() {
                    if (this.logger !== undefined) {
                        this.logger.info("FSM cancelled");
                    }
                    if (this.started) {
                        this.notifyHandlerOnCancel();
                    }
                    this.fullReinit();
                }
                onStarting() {
                    if (this.logger !== undefined) {
                        this.logger.info("FSM started");
                    }
                    this.started = true;
                    this.notifyHandlerOnStart();
                }
                onUpdating() {
                    if (this.started) {
                        if (this.logger !== undefined) {
                            this.logger.info("FSM updated");
                        }
                        this.notifyHandlerOnUpdate();
                    }
                }
                addState(state) {
                    if (state !== undefined) {
                        this.states.push(state);
                    }
                }
                log(log) {
                    if (log) {
                        if (this.logger === undefined) {
                            this.logger = ConfigLog_1.factory.getLogger("FSM");
                        }
                    }
                    else {
                        this.logger = undefined;
                    }
                }
                reinit() {
                    if (this.logger !== undefined) {
                        this.logger.info("FSM reinitialised");
                    }
                    if (this.currentTimeout !== undefined) {
                        this.currentTimeout.stopTimeout();
                    }
                    this.started = false;
                    this.currentState.set(this.initState);
                    this.currentTimeout = undefined;
                    if (this.currentSubFSM !== undefined) {
                        this.currentSubFSM.reinit();
                    }
                }
                fullReinit() {
                    if (this.eventsToProcess !== undefined) {
                        this.eventsToProcess.clear();
                    }
                    this.reinit();
                    if (this.currentSubFSM !== undefined) {
                        this.currentSubFSM.fullReinit();
                    }
                }
                onTimeout() {
                    if (this.currentTimeout !== undefined) {
                        if (this.logger !== undefined) {
                            this.logger.info("Timeout");
                        }
                    }
                }
                stopCurrentTimeout() {
                    if (this.currentTimeout !== undefined) {
                        if (this.logger !== undefined) {
                            this.logger.info("Timeout stopped");
                        }
                        this.currentTimeout.stopTimeout();
                        this.currentTimeout = undefined;
                    }
                }
                checkTimeoutTransition() {
                    const tr = this.currentState.get().getTransitions().find(t => t instanceof TimeoutTransition_1.TimeoutTransition);
                    if (tr) {
                        if (this.logger !== undefined) {
                            this.logger.info("Timeout starting");
                        }
                        this.currentTimeout = tr;
                        this.currentTimeout.startTimeout();
                    }
                }
                addHandler(handler) {
                    if (handler !== undefined) {
                        this.handlers.push(handler);
                    }
                }
                removeHandler(handler) {
                    if (handler !== undefined) {
                        this.handlers.remove(handler);
                    }
                }
                notifyHandlerOnStart() {
                    try {
                        this.handlers.forEach(handler => handler.fsmStarts());
                    }
                    catch (ex) {
                        this.onCancelling();
                        throw ex;
                    }
                }
                notifyHandlerOnUpdate() {
                    try {
                        this.handlers.forEach(handler => handler.fsmUpdates());
                    }
                    catch (ex) {
                        this.onCancelling();
                        throw ex;
                    }
                }
                notifyHandlerOnStop() {
                    try {
                        this.handlers.forEach(handler => handler.fsmStops());
                    }
                    catch (ex) {
                        this.onCancelling();
                        throw ex;
                    }
                }
                notifyHandlerOnCancel() {
                    this.handlers.forEach(handler => handler.fsmCancels());
                }
                getStates() {
                    return [...this.states];
                }
                currentStateProp() {
                    return this.currentState;
                }
                getStartingState() {
                    return this.startingState;
                }
            };
            exports_23("FSM", FSM);
        }
    };
});
System.register("src-core/interaction/InteractionImpl", ["src-core/fsm/InitState", "src-core/logging/ConfigLog"], function (exports_24, context_24) {
    "use strict";
    var __moduleName = context_24 && context_24.id;
    var InitState_2, ConfigLog_2, InteractionImpl;
    return {
        setters: [
            function (InitState_2_1) {
                InitState_2 = InitState_2_1;
            },
            function (ConfigLog_2_1) {
                ConfigLog_2 = ConfigLog_2_1;
            }
        ],
        execute: function () {
            InteractionImpl = class InteractionImpl {
                constructor(fsm) {
                    this.activated = false;
                    this.fsm = fsm;
                    fsm.currentStateProp().obs((oldValue, newValue) => this.updateEventsRegistered(newValue, oldValue));
                    this.activated = true;
                }
                isRunning() {
                    return this.activated && !(this.fsm.getCurrentState() instanceof InitState_2.InitState);
                }
                fullReinit() {
                    this.fsm.fullReinit();
                }
                processEvent(event) {
                    if (this.isActivated()) {
                        this.fsm.process(event);
                    }
                }
                log(log) {
                    if (log) {
                        if (this.logger === undefined) {
                            this.logger = ConfigLog_2.factory.getLogger("Interaction");
                        }
                    }
                    else {
                        this.logger = undefined;
                    }
                    this.fsm.log(log);
                }
                isActivated() {
                    return this.activated;
                }
                setActivated(activated) {
                    if (this.logger !== undefined) {
                        this.logger.info("Interaction activation: " + activated);
                    }
                    this.activated = activated;
                    if (!activated) {
                        this.fsm.fullReinit();
                    }
                }
                getFsm() {
                    return this.fsm;
                }
                reinit() {
                    this.fsm.reinit();
                    this.reinitData();
                }
            };
            exports_24("InteractionImpl", InteractionImpl);
        }
    };
});
System.register("src-core/binding/WidgetBinding", [], function (exports_25, context_25) {
    "use strict";
    var __moduleName = context_25 && context_25.id;
    return {
        setters: [],
        execute: function () {
        }
    };
});
System.register("src-core/properties/Modifiable", [], function (exports_26, context_26) {
    "use strict";
    var __moduleName = context_26 && context_26.id;
    return {
        setters: [],
        execute: function () {
        }
    };
});
System.register("src-core/properties/Reinitialisable", [], function (exports_27, context_27) {
    "use strict";
    var __moduleName = context_27 && context_27.id;
    return {
        setters: [],
        execute: function () {
        }
    };
});
System.register("src-core/instrument/Instrument", [], function (exports_28, context_28) {
    "use strict";
    var __moduleName = context_28 && context_28.id;
    return {
        setters: [],
        execute: function () {
        }
    };
});
System.register("src-core/action/library/InstrumentAction", ["src-core/action/ActionImpl"], function (exports_29, context_29) {
    "use strict";
    var __moduleName = context_29 && context_29.id;
    var ActionImpl_2, InstrumentAction;
    return {
        setters: [
            function (ActionImpl_2_1) {
                ActionImpl_2 = ActionImpl_2_1;
            }
        ],
        execute: function () {
            InstrumentAction = class InstrumentAction extends ActionImpl_2.ActionImpl {
                constructor(instrument) {
                    super();
                    this.instrument = instrument;
                }
                flush() {
                    super.flush();
                    this.instrument = undefined;
                }
                canDo() {
                    return this.instrument !== undefined;
                }
                getInstrument() {
                    return this.instrument;
                }
                setInstrument(newInstrument) {
                    this.instrument = newInstrument;
                }
            };
            exports_29("InstrumentAction", InstrumentAction);
        }
    };
});
System.register("src-core/action/library/ActivateInstrument", ["src-core/action/library/InstrumentAction"], function (exports_30, context_30) {
    "use strict";
    var __moduleName = context_30 && context_30.id;
    var InstrumentAction_1, ActivateInstrument;
    return {
        setters: [
            function (InstrumentAction_1_1) {
                InstrumentAction_1 = InstrumentAction_1_1;
            }
        ],
        execute: function () {
            ActivateInstrument = class ActivateInstrument extends InstrumentAction_1.InstrumentAction {
                constructor(instrument) {
                    super(instrument);
                }
                doActionBody() {
                    if (this.instrument) {
                        this.instrument.setActivated(true);
                    }
                }
            };
            exports_30("ActivateInstrument", ActivateInstrument);
        }
    };
});
System.register("src-core/action/library/AnonymousAction", ["src-core/action/ActionImpl"], function (exports_31, context_31) {
    "use strict";
    var __moduleName = context_31 && context_31.id;
    var ActionImpl_3, AnonymousAction;
    return {
        setters: [
            function (ActionImpl_3_1) {
                ActionImpl_3 = ActionImpl_3_1;
            }
        ],
        execute: function () {
            AnonymousAction = class AnonymousAction extends ActionImpl_3.ActionImpl {
                constructor() {
                    super();
                }
                doActionBody() {
                    if (this.actionBody) {
                        this.actionBody();
                    }
                }
                canDo() {
                    return this.actionBody !== undefined;
                }
                setActionBody(body) {
                    this.actionBody = body;
                }
                getActionBody() {
                    return this.actionBody;
                }
            };
            exports_31("AnonymousAction", AnonymousAction);
        }
    };
});
System.register("src-core/action/library/InactivateInstrument", ["src-core/action/library/InstrumentAction"], function (exports_32, context_32) {
    "use strict";
    var __moduleName = context_32 && context_32.id;
    var InstrumentAction_2, InactivateInstrument;
    return {
        setters: [
            function (InstrumentAction_2_1) {
                InstrumentAction_2 = InstrumentAction_2_1;
            }
        ],
        execute: function () {
            InactivateInstrument = class InactivateInstrument extends InstrumentAction_2.InstrumentAction {
                constructor(instrument) {
                    super(instrument);
                }
                doActionBody() {
                    if (this.instrument) {
                        this.instrument.setActivated(false);
                    }
                }
            };
            exports_32("InactivateInstrument", InactivateInstrument);
        }
    };
});
System.register("src-core/action/library/ModifyValue", ["src-core/action/ActionImpl"], function (exports_33, context_33) {
    "use strict";
    var __moduleName = context_33 && context_33.id;
    var ActionImpl_4, ModifyValue;
    return {
        setters: [
            function (ActionImpl_4_1) {
                ActionImpl_4 = ActionImpl_4_1;
            }
        ],
        execute: function () {
            ModifyValue = class ModifyValue extends ActionImpl_4.ActionImpl {
                constructor(value) {
                    super();
                    this.value = value;
                }
                flush() {
                    super.flush();
                    this.value = undefined;
                }
                canDo() {
                    return this.value !== undefined && this.isValueMatchesProperty();
                }
                setValue(newValue) {
                    this.value = newValue;
                }
            };
            exports_33("ModifyValue", ModifyValue);
        }
    };
});
System.register("src-core/action/library/PositionAction", ["src-core/action/ActionImpl"], function (exports_34, context_34) {
    "use strict";
    var __moduleName = context_34 && context_34.id;
    var ActionImpl_5, PositionAction;
    return {
        setters: [
            function (ActionImpl_5_1) {
                ActionImpl_5 = ActionImpl_5_1;
            }
        ],
        execute: function () {
            PositionAction = class PositionAction extends ActionImpl_5.ActionImpl {
                constructor() {
                    super();
                    this.px = NaN;
                    this.py = NaN;
                }
                canDo() {
                    return !isNaN(this.px) && !isNaN(this.py);
                }
                setPx(px) {
                    this.px = px;
                }
                setPy(py) {
                    this.py = py;
                }
            };
            exports_34("PositionAction", PositionAction);
        }
    };
});
System.register("src-core/action/library/Redo", ["src-core/action/ActionImpl", "src-core/undo/UndoCollector"], function (exports_35, context_35) {
    "use strict";
    var __moduleName = context_35 && context_35.id;
    var ActionImpl_6, UndoCollector_2, Redo;
    return {
        setters: [
            function (ActionImpl_6_1) {
                ActionImpl_6 = ActionImpl_6_1;
            },
            function (UndoCollector_2_1) {
                UndoCollector_2 = UndoCollector_2_1;
            }
        ],
        execute: function () {
            Redo = class Redo extends ActionImpl_6.ActionImpl {
                constructor() {
                    super();
                }
                canDo() {
                    return UndoCollector_2.UndoCollector.INSTANCE.getLastRedo().isPresent();
                }
                doActionBody() {
                    UndoCollector_2.UndoCollector.INSTANCE.redo();
                    this.done();
                }
            };
            exports_35("Redo", Redo);
        }
    };
});
System.register("src-core/action/library/Undo", ["src-core/action/ActionImpl", "src-core/undo/UndoCollector"], function (exports_36, context_36) {
    "use strict";
    var __moduleName = context_36 && context_36.id;
    var ActionImpl_7, UndoCollector_3, Undo;
    return {
        setters: [
            function (ActionImpl_7_1) {
                ActionImpl_7 = ActionImpl_7_1;
            },
            function (UndoCollector_3_1) {
                UndoCollector_3 = UndoCollector_3_1;
            }
        ],
        execute: function () {
            Undo = class Undo extends ActionImpl_7.ActionImpl {
                constructor() {
                    super();
                }
                canDo() {
                    return UndoCollector_3.UndoCollector.INSTANCE.getLastUndo().isPresent();
                }
                doActionBody() {
                    UndoCollector_3.UndoCollector.INSTANCE.undo();
                    this.done();
                }
            };
            exports_36("Undo", Undo);
        }
    };
});
System.register("src-core/properties/Zoomable", [], function (exports_37, context_37) {
    "use strict";
    var __moduleName = context_37 && context_37.id;
    return {
        setters: [],
        execute: function () {
        }
    };
});
System.register("src-core/action/library/Zoom", ["src-core/action/library/PositionAction"], function (exports_38, context_38) {
    "use strict";
    var __moduleName = context_38 && context_38.id;
    var PositionAction_1, Zoom;
    return {
        setters: [
            function (PositionAction_1_1) {
                PositionAction_1 = PositionAction_1_1;
            }
        ],
        execute: function () {
            Zoom = class Zoom extends PositionAction_1.PositionAction {
                constructor() {
                    super();
                    this.zoomLevel = NaN;
                }
                flush() {
                    super.flush();
                    this.zoomable = undefined;
                }
                canDo() {
                    return this.zoomable !== undefined && this.zoomLevel >= this.zoomable.getMinZoom() && this.zoomLevel <= this.zoomable.getMaxZoom();
                }
                doActionBody() {
                    if (this.zoomable) {
                        this.zoomable.setZoom(this.px, this.py, this.zoomLevel);
                    }
                }
                setZoomable(newZoomable) {
                    this.zoomable = newZoomable;
                }
                setZoomLevel(newZoomLevel) {
                    this.zoomLevel = newZoomLevel;
                }
            };
            exports_38("Zoom", Zoom);
        }
    };
});
System.register("src-core/binding/MustBeUndoableActionException", [], function (exports_39, context_39) {
    "use strict";
    var __moduleName = context_39 && context_39.id;
    var MustBeUndoableActionException;
    return {
        setters: [],
        execute: function () {
            MustBeUndoableActionException = class MustBeUndoableActionException extends Error {
                constructor(actionProducer) {
                    super("The following action must be undoable: " + actionProducer);
                }
            };
            exports_39("MustBeUndoableActionException", MustBeUndoableActionException);
        }
    };
});
System.register("src-core/fsm/CancelFSMException", [], function (exports_40, context_40) {
    "use strict";
    var __moduleName = context_40 && context_40.id;
    var CancelFSMException;
    return {
        setters: [],
        execute: function () {
            CancelFSMException = class CancelFSMException extends Error {
                constructor() {
                    super();
                }
            };
            exports_40("CancelFSMException", CancelFSMException);
        }
    };
});
System.register("src-core/binding/WidgetBindingImpl", ["src-core/fsm/CancelFSMException", "src-core/action/Action", "src-core/action/ActionsRegistry", "src-core/undo/Undoable", "src-core/logging/ConfigLog", "src-core/binding/MustBeUndoableActionException"], function (exports_41, context_41) {
    "use strict";
    var __moduleName = context_41 && context_41.id;
    var CancelFSMException_1, Action_3, ActionsRegistry_2, Undoable_2, ConfigLog_3, MustBeUndoableActionException_1, WidgetBindingImpl;
    return {
        setters: [
            function (CancelFSMException_1_1) {
                CancelFSMException_1 = CancelFSMException_1_1;
            },
            function (Action_3_1) {
                Action_3 = Action_3_1;
            },
            function (ActionsRegistry_2_1) {
                ActionsRegistry_2 = ActionsRegistry_2_1;
            },
            function (Undoable_2_1) {
                Undoable_2 = Undoable_2_1;
            },
            function (ConfigLog_3_1) {
                ConfigLog_3 = ConfigLog_3_1;
            },
            function (MustBeUndoableActionException_1_1) {
                MustBeUndoableActionException_1 = MustBeUndoableActionException_1_1;
            }
        ],
        execute: function () {
            WidgetBindingImpl = class WidgetBindingImpl {
                constructor(exec, actionClass, interaction) {
                    this.execute = false;
                    this.async = false;
                    this.clazzAction = actionClass;
                    this.interaction = interaction;
                    this.action = undefined;
                    this.execute = exec;
                    this.interaction.getFsm().addHandler(this);
                    this.setActivated(true);
                }
                logBinding(log) {
                    if (log) {
                        if (this.loggerBinding === undefined) {
                            this.loggerBinding = ConfigLog_3.factory.getLogger("Binding");
                        }
                    }
                    else {
                    }
                }
                logAction(log) {
                    if (log) {
                        if (this.loggerAction === undefined) {
                            this.loggerAction = ConfigLog_3.factory.getLogger("Action");
                        }
                    }
                    else {
                        this.loggerAction = undefined;
                    }
                }
                logInteraction(log) {
                    this.interaction.log(log);
                }
                isAsync() {
                    return this.async;
                }
                setAsync(asyncAction) {
                    this.async = asyncAction;
                }
                clearEvents() {
                    this.interaction.fullReinit();
                }
                map() {
                    return this.clazzAction();
                }
                then() {
                }
                getInteraction() {
                    return this.interaction;
                }
                getAction() {
                    return this.action;
                }
                isActivated() {
                    return true;
                }
                isRunning() {
                    return this.interaction.isRunning();
                }
                isStrictStart() {
                    return false;
                }
                unbindActionAttributes() {
                    if (this.action !== undefined) {
                        this.unbindActionAttributesClass(this.action.constructor);
                        if (this.loggerAction !== undefined) {
                            this.loggerAction.info("Action unbound: " + this.action);
                        }
                    }
                }
                unbindActionAttributesClass(clazz) {
                }
                fsmCancels() {
                    if (this.action !== undefined) {
                        if (this.loggerBinding !== undefined) {
                            this.loggerBinding.info("Binding cancelled");
                        }
                        this.action.cancel();
                        if (this.loggerAction !== undefined) {
                            this.loggerAction.info("Action cancelled");
                        }
                        this.unbindActionAttributes();
                        if (this.isExecute() && this.action.hadEffect()) {
                            if (Undoable_2.isUndoableType(this.action)) {
                                this.action.undo();
                                if (this.loggerAction !== undefined) {
                                    this.loggerAction.info("Action undone");
                                }
                            }
                            else {
                                throw new MustBeUndoableActionException_1.MustBeUndoableActionException(this.clazzAction);
                            }
                        }
                        this.action = undefined;
                    }
                }
                fsmStarts() {
                    const ok = this.action === undefined && this.isActivated() && this.when();
                    if (this.loggerBinding !== undefined) {
                        this.loggerBinding.info("Starting binding: " + ok);
                    }
                    if (ok) {
                        this.action = this.map();
                        this.first();
                        if (this.loggerAction !== undefined) {
                            this.loggerAction.info("Action created and init: " + this.action);
                        }
                        this.feedback();
                    }
                    else {
                        if (this.isStrictStart()) {
                            if (this.loggerBinding !== undefined) {
                                this.loggerBinding.info("Cancelling starting interaction: " + this.interaction);
                            }
                            throw new CancelFSMException_1.CancelFSMException();
                        }
                    }
                }
                fsmStops() {
                    const ok = this.when();
                    if (this.loggerBinding !== undefined) {
                        this.loggerBinding.info("Binding stops with condition: " + ok);
                    }
                    if (ok) {
                        if (this.action === undefined) {
                            this.action = this.map();
                            this.first();
                            if (this.loggerAction !== undefined) {
                                this.loggerAction.info("Action created and init: " + this.action);
                            }
                        }
                        if (!this.execute) {
                            this.then();
                            if (this.loggerAction !== undefined) {
                                this.loggerAction.info("Action updated: " + this.action);
                            }
                        }
                        this.executeAction(this.action, this.async);
                        this.unbindActionAttributes();
                        this.action = undefined;
                    }
                    else {
                        if (this.action !== undefined) {
                            if (this.loggerAction !== undefined) {
                                this.loggerAction.info("Cancelling the action: " + this.action);
                            }
                            this.action.cancel();
                            this.unbindActionAttributes();
                            this.action = undefined;
                        }
                    }
                }
                executeAction(act, async) {
                    if (async) {
                        this.executeActionAsync(act);
                    }
                    else {
                        this.afterActionExecuted(act, act.doIt());
                    }
                }
                afterActionExecuted(act, ok) {
                    if (this.loggerAction !== undefined) {
                        this.loggerAction.info("Action execution did it: " + ok);
                    }
                    if (ok) {
                        act.done();
                    }
                    const hadEffect = act.hadEffect();
                    if (this.loggerAction !== undefined) {
                        this.loggerAction.info("Action execution had effect: " + hadEffect);
                    }
                    if (hadEffect) {
                        if (act.getRegistrationPolicy() !== Action_3.RegistrationPolicy.NONE) {
                            ActionsRegistry_2.ActionsRegistry.INSTANCE.addAction(act);
                        }
                        else {
                            ActionsRegistry_2.ActionsRegistry.INSTANCE.unregisterActions(act);
                        }
                        act.followingActions().forEach(actFollow => this.executeAction(actFollow, false));
                    }
                }
                fsmUpdates() {
                    const ok = this.when();
                    if (this.loggerBinding !== undefined) {
                        this.loggerBinding.info("Binding updates with condition: " + ok);
                    }
                    if (ok) {
                        if (this.action === undefined) {
                            if (this.loggerAction !== undefined) {
                                this.loggerAction.info("Action creation");
                            }
                            this.action = this.map();
                            this.first();
                        }
                        if (this.loggerAction !== undefined) {
                            this.loggerAction.info("Action update");
                        }
                        this.then();
                        if (this.execute && this.action.canDo()) {
                            if (this.loggerAction !== undefined) {
                                this.loggerAction.info("Action execution");
                            }
                            this.action.doIt();
                        }
                        this.feedback();
                    }
                }
                isExecute() {
                    return this.execute;
                }
                feedback() {
                }
                setActivated(activ) {
                    if (this.loggerBinding !== undefined) {
                        this.loggerBinding.info("Binding Activated: " + activ);
                    }
                    this.interaction.setActivated(activ);
                }
            };
            exports_41("WidgetBindingImpl", WidgetBindingImpl);
        }
    };
});
System.register("src-core/error/ErrorNotifier", [], function (exports_42, context_42) {
    "use strict";
    var __moduleName = context_42 && context_42.id;
    return {
        setters: [],
        execute: function () {
        }
    };
});
System.register("src-core/error/ErrorCatcher", [], function (exports_43, context_43) {
    "use strict";
    var __moduleName = context_43 && context_43.id;
    var ErrorCatcher;
    return {
        setters: [],
        execute: function () {
            ErrorCatcher = class ErrorCatcher {
                constructor() {
                }
                setNotifier(newNotifier) {
                    this.notifier = newNotifier;
                }
                getErrorNotifier() {
                    return this.notifier;
                }
                reportError(exception) {
                    if (exception !== undefined && this.notifier !== undefined) {
                        this.notifier.onException(exception);
                    }
                }
            };
            ErrorCatcher.INSTANCE = new ErrorCatcher();
            exports_43("ErrorCatcher", ErrorCatcher);
        }
    };
});
System.register("src-core/fsm/CancellingState", ["src-core/fsm/StateImpl"], function (exports_44, context_44) {
    "use strict";
    var __moduleName = context_44 && context_44.id;
    var StateImpl_2, CancellingState;
    return {
        setters: [
            function (StateImpl_2_1) {
                StateImpl_2 = StateImpl_2_1;
            }
        ],
        execute: function () {
            CancellingState = class CancellingState extends StateImpl_2.StateImpl {
                constructor(stateMachine, stateName) {
                    super(stateMachine, stateName);
                }
                checkStartingState() {
                    if (!this.getFSM().isStarted() && this.getFSM().getStartingState() === this) {
                        this.getFSM().onStarting();
                    }
                }
                enter() {
                    this.fsm.onCancelling();
                }
            };
            exports_44("CancellingState", CancellingState);
        }
    };
});
System.register("src-core/fsm/EpsilonTransition", ["src-core/fsm/Transition"], function (exports_45, context_45) {
    "use strict";
    var __moduleName = context_45 && context_45.id;
    var Transition_2, EpsilonTransition;
    return {
        setters: [
            function (Transition_2_1) {
                Transition_2 = Transition_2_1;
            }
        ],
        execute: function () {
            EpsilonTransition = class EpsilonTransition extends Transition_2.Transition {
                constructor(srcState, tgtState) {
                    super(srcState, tgtState);
                }
                accept(event) {
                    return true;
                }
                isGuardOK(event) {
                    return true;
                }
                getAcceptedEvents() {
                    return new Set([]);
                }
            };
            exports_45("EpsilonTransition", EpsilonTransition);
        }
    };
});
System.register("src-core/fsm/TerminalState", ["src-core/fsm/StateImpl"], function (exports_46, context_46) {
    "use strict";
    var __moduleName = context_46 && context_46.id;
    var StateImpl_3, TerminalState;
    return {
        setters: [
            function (StateImpl_3_1) {
                StateImpl_3 = StateImpl_3_1;
            }
        ],
        execute: function () {
            TerminalState = class TerminalState extends StateImpl_3.StateImpl {
                constructor(stateMachine, stateName) {
                    super(stateMachine, stateName);
                }
                checkStartingState() {
                    if (!this.getFSM().isStarted() && this.getFSM().getStartingState() === this) {
                        this.getFSM().onStarting();
                    }
                }
                enter() {
                    this.checkStartingState();
                    this.fsm.onTerminating();
                }
            };
            exports_46("TerminalState", TerminalState);
        }
    };
});
System.register("src-core/fsm/SubFSMTransition", ["src-core/fsm/Transition", "src/util/Optional", "src-core/fsm/TerminalState", "src-core/fsm/CancellingState", "src-core/fsm/OutputStateImpl"], function (exports_47, context_47) {
    "use strict";
    var __moduleName = context_47 && context_47.id;
    var Transition_3, Optional_4, TerminalState_1, CancellingState_1, OutputStateImpl_3, SubFSMTransition;
    return {
        setters: [
            function (Transition_3_1) {
                Transition_3 = Transition_3_1;
            },
            function (Optional_4_1) {
                Optional_4 = Optional_4_1;
            },
            function (TerminalState_1_1) {
                TerminalState_1 = TerminalState_1_1;
            },
            function (CancellingState_1_1) {
                CancellingState_1 = CancellingState_1_1;
            },
            function (OutputStateImpl_3_1) {
                OutputStateImpl_3 = OutputStateImpl_3_1;
            }
        ],
        execute: function () {
            SubFSMTransition = class SubFSMTransition extends Transition_3.Transition {
                constructor(srcState, tgtState, fsm) {
                    super(srcState, tgtState);
                    this.subFSM = fsm;
                    this.subFSM.setInner(true);
                    this.subFSMHandler = new class {
                        constructor(parentFSM) {
                            this._parent = parentFSM;
                        }
                        fsmStarts() {
                            this._parent.src.exit();
                        }
                        fsmUpdates() {
                            this._parent.src.getFSM().setCurrentState(this._parent.subFSM.getCurrentState());
                            this._parent.src.getFSM().onUpdating();
                        }
                        fsmStops() {
                            this._parent.action(undefined);
                            this._parent.subFSM.removeHandler(this._parent.subFSMHandler);
                            this._parent.src.getFSM().setCurrentSubFSM(undefined);
                            if (this._parent.tgt instanceof TerminalState_1.TerminalState) {
                                this._parent.tgt.enter();
                                return;
                            }
                            if (this._parent.tgt instanceof CancellingState_1.CancellingState) {
                                this.fsmCancels();
                                return;
                            }
                            if (this._parent.tgt instanceof OutputStateImpl_3.OutputStateImpl) {
                                this._parent.src.getFSM().setCurrentState(this._parent.tgt);
                                this._parent.tgt.enter();
                            }
                        }
                        fsmCancels() {
                            this._parent.subFSM.removeHandler(this._parent.subFSMHandler);
                            this._parent.src.getFSM().setCurrentSubFSM(undefined);
                            this._parent.src.getFSM().onCancelling();
                        }
                    }(this);
                }
                execute(event) {
                    if (this.isGuardOK(event)) {
                        this.src.getFSM().stopCurrentTimeout();
                        const transition = this.findTransition(event);
                        if (transition.isPresent()) {
                            this.subFSM.addHandler(this.subFSMHandler);
                            this.src.getFSM().setCurrentSubFSM(this.subFSM);
                            this.subFSM.process(event);
                            return Optional_4.Optional.ofNullable(transition.get()).map(t => t.tgt);
                        }
                    }
                    return Optional_4.Optional.empty();
                }
                accept(event) {
                    return this.findTransition(event).isPresent();
                }
                isGuardOK(event) {
                    return this.findTransition(event).filter(tr => tr.isGuardOK(event)).isPresent();
                }
                findTransition(event) {
                    return Optional_4.Optional.ofNullable(this.subFSM.initState.getTransitions().find(tr => tr.accept(event)));
                }
                getAcceptedEvents() {
                    return this.subFSM.initState.getTransitions().map(tr => tr.getAcceptedEvents()).reduce((a, b) => new Set([...a, ...b]));
                }
            };
            exports_47("SubFSMTransition", SubFSMTransition);
        }
    };
});
System.register("src-core/fsm/WidgetTransition", ["src-core/fsm/Transition"], function (exports_48, context_48) {
    "use strict";
    var __moduleName = context_48 && context_48.id;
    var Transition_4, WidgetTransition;
    return {
        setters: [
            function (Transition_4_1) {
                Transition_4 = Transition_4_1;
            }
        ],
        execute: function () {
            WidgetTransition = class WidgetTransition extends Transition_4.Transition {
                constructor(srcState, tgtState) {
                    super(srcState, tgtState);
                }
                getWidget() {
                    return this.widget;
                }
                setWidget(widget) {
                    if (widget !== undefined) {
                        this.widget = widget;
                    }
                }
            };
            exports_48("WidgetTransition", WidgetTransition);
        }
    };
});
System.register("src-core/instrument/InstrumentImpl", ["src-core/error/ErrorCatcher"], function (exports_49, context_49) {
    "use strict";
    var __moduleName = context_49 && context_49.id;
    var ErrorCatcher_1, InstrumentImpl;
    return {
        setters: [
            function (ErrorCatcher_1_1) {
                ErrorCatcher_1 = ErrorCatcher_1_1;
            }
        ],
        execute: function () {
            InstrumentImpl = class InstrumentImpl {
                constructor() {
                    this.activated = false;
                    this.modified = false;
                    this.bindings = [];
                }
                getNbWidgetBindings() {
                    return this.bindings.length;
                }
                hasWidgetBindings() {
                    return this.getNbWidgetBindings() > 0;
                }
                getWidgetBindings() {
                    return this.bindings;
                }
                addBinding(binding) {
                    if (binding !== undefined) {
                        this.bindings.push(binding);
                        binding.setActivated(this.isActivated());
                    }
                }
                removeBinding(binding) {
                    return binding !== undefined && this.bindings.remove(binding);
                }
                clearEvents() {
                    this.bindings.forEach(binding => binding.clearEvents());
                }
                isActivated() {
                    return this.activated;
                }
                setActivated(toBeActivated) {
                    this.activated = toBeActivated;
                    if (toBeActivated && !this.hasWidgetBindings()) {
                        try {
                            this.configureBindings();
                        }
                        catch (ex) {
                            ErrorCatcher_1.ErrorCatcher.INSTANCE.reportError(ex);
                        }
                    }
                    else {
                        this.bindings.forEach(binding => binding.setActivated(toBeActivated));
                    }
                    this.interimFeedback();
                }
                interimFeedback() {
                }
                isModified() {
                    return this.modified;
                }
                setModified(isModified) {
                    this.modified = isModified;
                }
                reinit() {
                }
                onUndoableCleared() {
                }
                onUndoableAdded(undoable) {
                }
                onUndoableUndo(undoable) {
                }
                onUndoableRedo(undoable) {
                }
                onActionAdded(action) {
                }
                onActionCancelled(action) {
                }
                onActionExecuted(action) {
                }
                onActionDone(action) {
                }
            };
            exports_49("InstrumentImpl", InstrumentImpl);
        }
    };
});
System.register("src-core/logging/LogLevel", [], function (exports_50, context_50) {
    "use strict";
    var __moduleName = context_50 && context_50.id;
    var LogLevel;
    return {
        setters: [],
        execute: function () {
            (function (LogLevel) {
                LogLevel[LogLevel["INTERACTION"] = 0] = "INTERACTION";
                LogLevel[LogLevel["BINDING"] = 1] = "BINDING";
                LogLevel[LogLevel["ACTION"] = 2] = "ACTION";
            })(LogLevel || (LogLevel = {}));
            exports_50("LogLevel", LogLevel);
        }
    };
});
System.register("src/interaction/Events", [], function (exports_51, context_51) {
    "use strict";
    var __moduleName = context_51 && context_51.id;
    var EventRegistrationToken, MousePressEvent, ButtonPressEvent, KeyPressEvent, KeyReleaseEvent;
    return {
        setters: [],
        execute: function () {
            (function (EventRegistrationToken) {
                EventRegistrationToken["MouseDown"] = "mousedown";
                EventRegistrationToken["MouseUp"] = "mouseup";
                EventRegistrationToken["MouseMove"] = "mousemove";
                EventRegistrationToken["KeyDown"] = "keydown";
                EventRegistrationToken["KeyPress"] = "keypress";
                EventRegistrationToken["KeyUp"] = "keyup";
            })(EventRegistrationToken || (EventRegistrationToken = {}));
            exports_51("EventRegistrationToken", EventRegistrationToken);
            MousePressEvent = class MousePressEvent extends MouseEvent {
            };
            exports_51("MousePressEvent", MousePressEvent);
            ButtonPressEvent = class ButtonPressEvent extends UIEvent {
            };
            exports_51("ButtonPressEvent", ButtonPressEvent);
            KeyPressEvent = class KeyPressEvent extends KeyboardEvent {
            };
            exports_51("KeyPressEvent", KeyPressEvent);
            KeyReleaseEvent = class KeyReleaseEvent extends KeyboardEvent {
            };
            exports_51("KeyReleaseEvent", KeyReleaseEvent);
        }
    };
});
System.register("src/interaction/TSInteraction", ["src-core/interaction/InteractionImpl", "src/interaction/Events"], function (exports_52, context_52) {
    "use strict";
    var __moduleName = context_52 && context_52.id;
    var InteractionImpl_1, Events_1, TSInteraction;
    return {
        setters: [
            function (InteractionImpl_1_1) {
                InteractionImpl_1 = InteractionImpl_1_1;
            },
            function (Events_1_1) {
                Events_1 = Events_1_1;
            }
        ],
        execute: function () {
            TSInteraction = class TSInteraction extends InteractionImpl_1.InteractionImpl {
                constructor(fsm) {
                    super(fsm);
                    this.registeredNodes = new Set();
                }
                get widget() {
                    return this._widget;
                }
                updateEventsRegistered(newState, oldState) {
                    if (newState === oldState || this.fsm.getStates().length === 2) {
                        return;
                    }
                    const currEvents = [...this.getEventTypesOf(newState)];
                    const events = [...this.getEventTypesOf(oldState)];
                    const eventsToRemove = events.filter(e => currEvents.indexOf(e) >= 0);
                    const eventsToAdd = currEvents.filter(e => events.indexOf(e) >= 0);
                    this.registeredNodes.forEach(n => {
                        eventsToRemove.forEach(type => this.unregisterEventToNode(type, n));
                        eventsToAdd.forEach(type => this.registerEventToNode(type, n));
                    });
                }
                getEventTypesOf(state) {
                    return state.getTransitions().map(t => t.getAcceptedEvents()).reduce((a, b) => new Set([...a, ...b]));
                }
                registerToNodes(widgets) {
                    if (widgets !== undefined) {
                        widgets.filter(w => w !== undefined).forEach(w => this.registeredNodes.add(w));
                    }
                }
                unregisterFromNodes(widgets) {
                    if (widgets !== undefined) {
                        widgets.filter(w => w !== undefined).forEach(w => this.registeredNodes.delete(w));
                    }
                }
                onNodeUnregistered(node) {
                    this.getEventTypesOf(this.fsm.getCurrentState()).forEach(type => this.unregisterEventToNode(type, node));
                }
                onNewNodeRegistered(node) {
                    this.getEventTypesOf(this.fsm.getCurrentState()).forEach(type => this.registerEventToNode(type, node));
                }
                registerEventToNode(eventType, node) {
                    if (Events_1.MousePressEvent.name === eventType) {
                        node.removeEventListener(Events_1.EventRegistrationToken.MouseDown, this.getMouseHandler());
                        return;
                    }
                }
                unregisterEventToNode(eventType, node) {
                    if (Events_1.MousePressEvent.name === eventType) {
                        node.addEventListener(Events_1.EventRegistrationToken.MouseDown, this.getMouseHandler());
                        return;
                    }
                }
                getMouseHandler() {
                    if (this.mouseHandler === undefined) {
                        this.mouseHandler = evt => this.processEvent(evt);
                    }
                    return this.mouseHandler;
                }
            };
            exports_52("TSInteraction", TSInteraction);
        }
    };
});
System.register("src/binding/TSWidgetBinding", ["src-core/binding/WidgetBindingImpl"], function (exports_53, context_53) {
    "use strict";
    var __moduleName = context_53 && context_53.id;
    var WidgetBindingImpl_1, TSWidgetBinding;
    return {
        setters: [
            function (WidgetBindingImpl_1_1) {
                WidgetBindingImpl_1 = WidgetBindingImpl_1_1;
            }
        ],
        execute: function () {
            TSWidgetBinding = class TSWidgetBinding extends WidgetBindingImpl_1.WidgetBindingImpl {
                constructor(exec, clazzAction, interaction, widgets) {
                    super(exec, clazzAction, interaction);
                    interaction.registerToNodes(widgets);
                }
                when() {
                    return true;
                }
                executeActionAsync(act) {
                }
            };
            exports_53("TSWidgetBinding", TSWidgetBinding);
        }
    };
});
System.register("src/binding/AnonNodeBinding", ["src/binding/TSWidgetBinding", "src-core/logging/LogLevel"], function (exports_54, context_54) {
    "use strict";
    var __moduleName = context_54 && context_54.id;
    var TSWidgetBinding_1, LogLevel_1, AnonNodeBinding;
    return {
        setters: [
            function (TSWidgetBinding_1_1) {
                TSWidgetBinding_1 = TSWidgetBinding_1_1;
            },
            function (LogLevel_1_1) {
                LogLevel_1 = LogLevel_1_1;
            }
        ],
        execute: function () {
            AnonNodeBinding = class AnonNodeBinding extends TSWidgetBinding_1.TSWidgetBinding {
                constructor(exec, clazzAction, interaction, initActionFct, updateActionFct, check, onEndFct, actionFunction, cancel, endOrCancel, feedback, widgets, asyncExec, strict, loggers) {
                    super(exec, clazzAction, interaction, widgets);
                    this.execInitAction = initActionFct;
                    this.execUpdateAction = updateActionFct;
                    this.cancelFct = cancel;
                    this.endOrCancelFct = endOrCancel;
                    this.feedbackFct = feedback;
                    this.actionProducer = actionFunction;
                    this.checkInteraction = check === undefined ? () => true : check;
                    this.async = asyncExec;
                    this.onEnd = onEndFct;
                    this.strictStart = strict;
                    this.currentAction = undefined;
                    this.configureLoggers(loggers);
                }
                configureLoggers(loggers) {
                    if (loggers !== undefined) {
                        this.logAction(loggers.indexOf(LogLevel_1.LogLevel.ACTION) >= 0);
                        this.logBinding(loggers.indexOf(LogLevel_1.LogLevel.BINDING) >= 0);
                        this.interaction.log(loggers.indexOf(LogLevel_1.LogLevel.INTERACTION) >= 0);
                    }
                }
                isStrictStart() {
                    return this.strictStart;
                }
                map() {
                    this.currentAction = this.actionProducer === undefined ? this.currentAction = super.map() :
                        this.currentAction = this.actionProducer(this.getInteraction());
                    return this.currentAction;
                }
                first() {
                    if (this.execInitAction !== undefined && this.currentAction !== undefined) {
                        this.execInitAction(this.getAction(), this.getInteraction());
                    }
                }
                then() {
                    if (this.execUpdateAction !== undefined) {
                        this.execUpdateAction(this.getAction(), this.getInteraction());
                    }
                }
                when() {
                    return this.checkInteraction === undefined || this.checkInteraction(this.getInteraction());
                }
                fsmCancels() {
                    if (this.endOrCancelFct !== undefined && this.currentAction !== undefined) {
                        this.endOrCancelFct(this.action, this.interaction);
                    }
                    if (this.cancelFct !== undefined && this.currentAction !== undefined) {
                        this.cancelFct(this.action, this.interaction);
                    }
                    super.fsmCancels();
                    this.currentAction = undefined;
                }
                feedback() {
                    if (this.feedbackFct !== undefined) {
                        this.feedbackFct();
                    }
                }
                fsmStops() {
                    super.fsmStops();
                    if (this.endOrCancelFct !== undefined && this.currentAction !== undefined) {
                        this.endOrCancelFct(this.currentAction, this.getInteraction());
                    }
                    if (this.onEnd !== undefined && this.currentAction !== undefined) {
                        this.onEnd(this.currentAction, this.getInteraction());
                    }
                    this.currentAction = undefined;
                }
            };
            exports_54("AnonNodeBinding", AnonNodeBinding);
        }
    };
});
System.register("src/interaction/TSTransition", ["src-core/fsm/Transition"], function (exports_55, context_55) {
    "use strict";
    var __moduleName = context_55 && context_55.id;
    var Transition_5, TSTransition;
    return {
        setters: [
            function (Transition_5_1) {
                Transition_5 = Transition_5_1;
            }
        ],
        execute: function () {
            TSTransition = class TSTransition extends Transition_5.Transition {
                constructor(srcState, tgtState) {
                    super(srcState, tgtState);
                }
            };
            exports_55("TSTransition", TSTransition);
        }
    };
});
System.register("src/interaction/ButtonPressedTransition", ["src/interaction/TSTransition", "src/interaction/Events"], function (exports_56, context_56) {
    "use strict";
    var __moduleName = context_56 && context_56.id;
    var TSTransition_1, Events_2, ButtonPressedTransition;
    return {
        setters: [
            function (TSTransition_1_1) {
                TSTransition_1 = TSTransition_1_1;
            },
            function (Events_2_1) {
                Events_2 = Events_2_1;
            }
        ],
        execute: function () {
            ButtonPressedTransition = class ButtonPressedTransition extends TSTransition_1.TSTransition {
                constructor(srcState, tgtState) {
                    super(srcState, tgtState);
                }
                accept(e) {
                    return e instanceof Events_2.ButtonPressEvent;
                }
                getAcceptedEvents() {
                    return new Set([Events_2.ButtonPressEvent.name]);
                }
                isGuardOK(event) {
                    return true;
                }
            };
            exports_56("ButtonPressedTransition", ButtonPressedTransition);
        }
    };
});
System.register("src/interaction/FSMDataHandler", [], function (exports_57, context_57) {
    "use strict";
    var __moduleName = context_57 && context_57.id;
    return {
        setters: [],
        execute: function () {
        }
    };
});
System.register("src/interaction/TSFSM", ["src-core/fsm/FSM", "src-core/fsm/InitState", "src/interaction/Events"], function (exports_58, context_58) {
    "use strict";
    var __moduleName = context_58 && context_58.id;
    var FSM_1, InitState_3, Events_3, TSFSM;
    return {
        setters: [
            function (FSM_1_1) {
                FSM_1 = FSM_1_1;
            },
            function (InitState_3_1) {
                InitState_3 = InitState_3_1;
            },
            function (Events_3_1) {
                Events_3 = Events_3_1;
            }
        ],
        execute: function () {
            TSFSM = class TSFSM extends FSM_1.FSM {
                buildFSM(dataHandler) {
                    if (this.states.length > 1) {
                        return;
                    }
                    this.dataHandler = dataHandler;
                }
                reinit() {
                    super.reinit();
                    if (this.dataHandler !== undefined && !this.inner) {
                        this.dataHandler.reinitData();
                    }
                }
                process(event) {
                    if (event instanceof Events_3.KeyPressEvent) {
                        this.removeKeyEvent(event.keyCode);
                    }
                    const processed = super.process(event);
                    if (processed && event instanceof Events_3.KeyPressEvent && !(this.getCurrentState() instanceof InitState_3.InitState) &&
                        (this.eventsToProcess === undefined ||
                            this.eventsToProcess.find(evt => evt.keyCode === (event).keyCode) === undefined)) {
                    }
                    return processed;
                }
                removeKeyEvent(key) {
                    if (this.eventsToProcess === undefined) {
                        return;
                    }
                    let removed = false;
                    for (let i = 0, size = this.eventsToProcess.length; i < size && !removed; i++) {
                        const event = this.eventsToProcess[i];
                        if (event instanceof KeyboardEvent && (event).keyCode === key) {
                            removed = true;
                            this.eventsToProcess.removeAt(i);
                        }
                    }
                }
                getDataHandler() {
                    return this.dataHandler;
                }
            };
            exports_58("TSFSM", TSFSM);
        }
    };
});
System.register("src/interaction/library/ButtonPressed", ["src/interaction/TSFSM", "src/interaction/ButtonPressedTransition", "src-core/fsm/TerminalState", "src/interaction/Events"], function (exports_59, context_59) {
    "use strict";
    var __moduleName = context_59 && context_59.id;
    var TSFSM_1, ButtonPressedTransition_1, TerminalState_2, Events_4, ButtonPressedFSM;
    return {
        setters: [
            function (TSFSM_1_1) {
                TSFSM_1 = TSFSM_1_1;
            },
            function (ButtonPressedTransition_1_1) {
                ButtonPressedTransition_1 = ButtonPressedTransition_1_1;
            },
            function (TerminalState_2_1) {
                TerminalState_2 = TerminalState_2_1;
            },
            function (Events_4_1) {
                Events_4 = Events_4_1;
            }
        ],
        execute: function () {
            ButtonPressedFSM = class ButtonPressedFSM extends TSFSM_1.TSFSM {
                constructor() {
                    super();
                }
                buildFSM(dataHandler) {
                    if (this.states.length > 1) {
                        return;
                    }
                    super.buildFSM(dataHandler);
                    const pressed = new TerminalState_2.TerminalState(this, "pressed");
                    this.addState(pressed);
                    new class extends ButtonPressedTransition_1.ButtonPressedTransition {
                        action(event) {
                            if (dataHandler !== undefined && event instanceof Events_4.ButtonPressEvent) {
                                dataHandler.initToPressedHandler(event);
                            }
                        }
                    }(this.initState, pressed);
                }
            };
            exports_59("ButtonPressedFSM", ButtonPressedFSM);
        }
    };
});
System.register("src/interaction/PressureTransition", ["src/interaction/TSTransition", "src/interaction/Events"], function (exports_60, context_60) {
    "use strict";
    var __moduleName = context_60 && context_60.id;
    var TSTransition_2, Events_5, PressureTransition;
    return {
        setters: [
            function (TSTransition_2_1) {
                TSTransition_2 = TSTransition_2_1;
            },
            function (Events_5_1) {
                Events_5 = Events_5_1;
            }
        ],
        execute: function () {
            PressureTransition = class PressureTransition extends TSTransition_2.TSTransition {
                constructor(srcState, tgtState) {
                    super(srcState, tgtState);
                }
                accept(e) {
                    return e instanceof Events_5.MousePressEvent;
                }
                getAcceptedEvents() {
                    return new Set([Events_5.MousePressEvent.name]);
                }
                isGuardOK(event) {
                    return true;
                }
            };
            exports_60("PressureTransition", PressureTransition);
        }
    };
});
Array.prototype.clear = function () {
    this.length = 0;
};
Array.prototype.peek = function () {
    return this.length === 0 ? undefined : this[this.length - 1];
};
Array.prototype.isEmpty = function () {
    return this.length === 0;
};
Array.prototype.remove = function (elt) {
    const index = this.indexOf(elt);
    if (index > -1) {
        this.splice(index, 1);
        return true;
    }
    return false;
};
Array.prototype.removeAt = function (index) {
    if (index > -1) {
        return this.splice(index, 1)[0];
    }
    return undefined;
};
//# sourceMappingURL=malai.js.map