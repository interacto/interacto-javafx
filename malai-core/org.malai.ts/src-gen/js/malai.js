var org;
(function (org) {
    var malai;
    (function (malai) {
        var action;
        (function (action_1) {
            var Action;
            (function (Action) {
                var RegistrationPolicy;
                (function (RegistrationPolicy) {
                    RegistrationPolicy[RegistrationPolicy["NONE"] = 0] = "NONE";
                    RegistrationPolicy[RegistrationPolicy["UNLIMITED"] = 1] = "UNLIMITED";
                    RegistrationPolicy[RegistrationPolicy["LIMITED"] = 2] = "LIMITED";
                })(RegistrationPolicy = Action.RegistrationPolicy || (Action.RegistrationPolicy = {}));
                var ActionStatus;
                (function (ActionStatus) {
                    ActionStatus[ActionStatus["CREATED"] = 0] = "CREATED";
                    ActionStatus[ActionStatus["EXECUTED"] = 1] = "EXECUTED";
                    ActionStatus[ActionStatus["CANCELLED"] = 2] = "CANCELLED";
                    ActionStatus[ActionStatus["DONE"] = 3] = "DONE";
                    ActionStatus[ActionStatus["FLUSHED"] = 4] = "FLUSHED";
                })(ActionStatus = Action.ActionStatus || (Action.ActionStatus = {}));
            })(Action = action_1.Action || (action_1.Action = {}));
        })(action = malai.action || (malai.action = {}));
    })(malai = org.malai || (org.malai = {}));
})(org || (org = {}));
var org;
(function (org) {
    var malai;
    (function (malai) {
        var action;
        (function (action_2) {
            class ActionImpl {
                constructor() {
                    if (this.status === undefined)
                        this.status = null;
                    this.status = action_2.Action.ActionStatus.CREATED;
                }
                flush() {
                    this.status = action_2.Action.ActionStatus.FLUSHED;
                }
                createMemento() {
                }
                doIt() {
                    let ok;
                    if ((this.status === action_2.Action.ActionStatus.CREATED || this.status === action_2.Action.ActionStatus.EXECUTED) && this.canDo()) {
                        if (this.status === action_2.Action.ActionStatus.CREATED) {
                            this.createMemento();
                        }
                        ok = true;
                        this.doActionBody();
                        this.status = action_2.Action.ActionStatus.EXECUTED;
                        org.malai.action.ActionsRegistry.INSTANCE_$LI$().onActionExecuted(this);
                    }
                    else {
                        ok = false;
                    }
                    return ok;
                }
                getRegistrationPolicy() {
                    return this.hadEffect() ? action_2.Action.RegistrationPolicy.LIMITED : action_2.Action.RegistrationPolicy.NONE;
                }
                hadEffect() {
                    return this.isDone();
                }
                unregisteredBy(action) {
                    return false;
                }
                done() {
                    if (this.status === action_2.Action.ActionStatus.CREATED || this.status === action_2.Action.ActionStatus.EXECUTED) {
                        this.status = action_2.Action.ActionStatus.DONE;
                        org.malai.action.ActionsRegistry.INSTANCE_$LI$().onActionDone(this);
                    }
                }
                isDone() {
                    return this.status === action_2.Action.ActionStatus.DONE;
                }
                toString() {
                    return (c => c["__class"] ? c["__class"].substring(c["__class"].lastIndexOf('.') + 1) : c["name"].substring(c["name"].lastIndexOf('.') + 1))(this.constructor);
                }
                cancel() {
                    this.status = action_2.Action.ActionStatus.CANCELLED;
                }
                getStatus() {
                    return this.status;
                }
                followingActions() {
                    return [];
                }
            }
            action_2.ActionImpl = ActionImpl;
            ActionImpl["__class"] = "org.malai.action.ActionImpl";
            ActionImpl["__interfaces"] = ["org.malai.action.Action"];
        })(action = malai.action || (malai.action = {}));
    })(malai = org.malai || (org.malai = {}));
})(org || (org = {}));
var org;
(function (org) {
    var malai;
    (function (malai) {
        var action;
        (function (action_3) {
            class ActionsRegistry {
                constructor() {
                    if (this.actions === undefined)
                        this.actions = null;
                    if (this.handlers === undefined)
                        this.handlers = null;
                    if (this.sizeMax === undefined)
                        this.sizeMax = 0;
                    this.actions = ([]);
                    this.handlers = ([]);
                    this.sizeMax = 50;
                }
                static INSTANCE_$LI$() { if (ActionsRegistry.INSTANCE == null)
                    ActionsRegistry.INSTANCE = new ActionsRegistry(); return ActionsRegistry.INSTANCE; }
                ;
                getHandlers() {
                    return this.handlers.slice(0);
                }
                onActionExecuted(action) {
                    if (action != null) {
                        {
                            this.handlers.forEach((handler) => handler.onActionExecuted(action));
                        }
                        ;
                    }
                }
                onActionDone(action) {
                    if (action != null) {
                        {
                            this.handlers.forEach((handler) => handler.onActionDone(action));
                        }
                        ;
                    }
                }
                getActions() {
                    return this.actions;
                }
                unregisterActions(action) {
                }
                addAction(action, actionHandler) {
                }
                removeAction(action) {
                    if (action != null) {
                        {
                            (a => { let index = a.indexOf(action); if (index >= 0) {
                                a.splice(index, 1);
                                return true;
                            }
                            else {
                                return false;
                            } })(this.actions);
                        }
                        ;
                        action.flush();
                    }
                }
                addHandler(handler) {
                    if (handler != null) {
                        {
                            (this.handlers.push(handler) > 0);
                        }
                        ;
                    }
                }
                removeHandler(handler) {
                    if (handler != null) {
                        {
                            (a => { let index = a.indexOf(handler); if (index >= 0) {
                                a.splice(index, 1);
                                return true;
                            }
                            else {
                                return false;
                            } })(this.handlers);
                        }
                        ;
                    }
                }
                removeAllHandlers() {
                    {
                        (this.handlers.length = 0);
                    }
                    ;
                }
                clear() {
                    {
                        this.actions.forEach((action) => action.flush());
                        (this.actions.length = 0);
                    }
                    ;
                }
                cancelAction(action) {
                    if (action != null) {
                        action.cancel();
                        {
                            (a => { let index = a.indexOf(action); if (index >= 0) {
                                a.splice(index, 1);
                                return true;
                            }
                            else {
                                return false;
                            } })(this.actions);
                        }
                        ;
                        {
                            this.handlers.forEach((handler) => handler.onActionCancelled(action));
                        }
                        ;
                        action.flush();
                    }
                }
                getSizeMax() {
                    return this.sizeMax;
                }
                setSizeMax(newSizeMax) {
                }
            }
            action_3.ActionsRegistry = ActionsRegistry;
            ActionsRegistry["__class"] = "org.malai.action.ActionsRegistry";
        })(action = malai.action || (malai.action = {}));
    })(malai = org.malai || (org.malai = {}));
})(org || (org = {}));
org.malai.action.ActionsRegistry.INSTANCE_$LI$();
var org;
(function (org) {
    var malai;
    (function (malai) {
        var action;
        (function (action) {
            class AnonAction extends org.malai.action.ActionImpl {
                constructor(__function) {
                    super();
                    if (this.exec === undefined)
                        this.exec = null;
                    this.exec = (__function);
                }
                canDo() {
                    return this.exec != null;
                }
                doActionBody() {
                    (target => (typeof target === 'function') ? target() : target.run())(this.exec);
                }
            }
            action.AnonAction = AnonAction;
            AnonAction["__class"] = "org.malai.action.AnonAction";
            AnonAction["__interfaces"] = ["org.malai.action.Action"];
        })(action = malai.action || (malai.action = {}));
    })(malai = org.malai || (org.malai = {}));
})(org || (org = {}));
var org;
(function (org) {
    var malai;
    (function (malai) {
        var action;
        (function (action) {
            var library;
            (function (library) {
                class InstrumentAction extends org.malai.action.ActionImpl {
                    constructor(instrument) {
                        if (((instrument != null && (instrument["__interfaces"] != null && instrument["__interfaces"].indexOf("org.malai.instrument.Instrument") >= 0 || instrument.constructor != null && instrument.constructor["__interfaces"] != null && instrument.constructor["__interfaces"].indexOf("org.malai.instrument.Instrument") >= 0)) || instrument === null)) {
                            let __args = Array.prototype.slice.call(arguments);
                            super();
                            if (this.instrument === undefined)
                                this.instrument = null;
                            if (this.instrument === undefined)
                                this.instrument = null;
                            (() => {
                                this.instrument = instrument;
                            })();
                        }
                        else if (instrument === undefined) {
                            let __args = Array.prototype.slice.call(arguments);
                            {
                                let __args = Array.prototype.slice.call(arguments);
                                let instrument = null;
                                super();
                                if (this.instrument === undefined)
                                    this.instrument = null;
                                if (this.instrument === undefined)
                                    this.instrument = null;
                                (() => {
                                    this.instrument = instrument;
                                })();
                            }
                        }
                        else
                            throw new Error('invalid overload');
                    }
                    flush() {
                        super.flush();
                        this.instrument = null;
                    }
                    canDo() {
                        return this.instrument != null;
                    }
                    getInstrument() {
                        return this.instrument;
                    }
                    setInstrument(newInstrument) {
                        this.instrument = newInstrument;
                    }
                }
                library.InstrumentAction = InstrumentAction;
                InstrumentAction["__class"] = "org.malai.action.library.InstrumentAction";
                InstrumentAction["__interfaces"] = ["org.malai.action.Action"];
            })(library = action.library || (action.library = {}));
        })(action = malai.action || (malai.action = {}));
    })(malai = org.malai || (org.malai = {}));
})(org || (org = {}));
var org;
(function (org) {
    var malai;
    (function (malai) {
        var action;
        (function (action) {
            var library;
            (function (library) {
                class ActivateInstrument extends org.malai.action.library.InstrumentAction {
                    constructor(instrument = null) {
                        super(instrument);
                    }
                    doActionBody() {
                        this.instrument.setActivated(true);
                    }
                }
                library.ActivateInstrument = ActivateInstrument;
                ActivateInstrument["__class"] = "org.malai.action.library.ActivateInstrument";
                ActivateInstrument["__interfaces"] = ["org.malai.action.Action"];
            })(library = action.library || (action.library = {}));
        })(action = malai.action || (malai.action = {}));
    })(malai = org.malai || (org.malai = {}));
})(org || (org = {}));
var org;
(function (org) {
    var malai;
    (function (malai) {
        var action;
        (function (action) {
            var library;
            (function (library) {
                class AnonymousAction extends org.malai.action.ActionImpl {
                    constructor() {
                        super();
                        if (this.actionBody === undefined)
                            this.actionBody = null;
                    }
                    doActionBody() {
                        (target => (typeof target === 'function') ? target() : target.run())(this.actionBody);
                    }
                    canDo() {
                        return this.actionBody != null;
                    }
                    setActionBody(body) {
                        this.actionBody = (body);
                    }
                    getActionBody() {
                        return (this.actionBody);
                    }
                }
                library.AnonymousAction = AnonymousAction;
                AnonymousAction["__class"] = "org.malai.action.library.AnonymousAction";
                AnonymousAction["__interfaces"] = ["org.malai.action.Action"];
            })(library = action.library || (action.library = {}));
        })(action = malai.action || (malai.action = {}));
    })(malai = org.malai || (org.malai = {}));
})(org || (org = {}));
var org;
(function (org) {
    var malai;
    (function (malai) {
        var action;
        (function (action) {
            var library;
            (function (library) {
                class InactivateInstrument extends org.malai.action.library.InstrumentAction {
                    constructor(instrument = null) {
                        super(instrument);
                    }
                    doActionBody() {
                        this.instrument.setActivated(false);
                    }
                }
                library.InactivateInstrument = InactivateInstrument;
                InactivateInstrument["__class"] = "org.malai.action.library.InactivateInstrument";
                InactivateInstrument["__interfaces"] = ["org.malai.action.Action"];
            })(library = action.library || (action.library = {}));
        })(action = malai.action || (malai.action = {}));
    })(malai = org.malai || (org.malai = {}));
})(org || (org = {}));
var org;
(function (org) {
    var malai;
    (function (malai) {
        var action;
        (function (action) {
            var library;
            (function (library) {
                class ModifyValue extends org.malai.action.ActionImpl {
                    constructor(value) {
                        if (((value != null) || value === null)) {
                            let __args = Array.prototype.slice.call(arguments);
                            super();
                            if (this.value === undefined)
                                this.value = null;
                            if (this.value === undefined)
                                this.value = null;
                            (() => {
                                this.value = value;
                            })();
                        }
                        else if (value === undefined) {
                            let __args = Array.prototype.slice.call(arguments);
                            super();
                            if (this.value === undefined)
                                this.value = null;
                            if (this.value === undefined)
                                this.value = null;
                        }
                        else
                            throw new Error('invalid overload');
                    }
                    flush() {
                        super.flush();
                        this.value = null;
                    }
                    canDo() {
                        return this.value != null && this.isValueMatchesProperty();
                    }
                    setValue(newValue) {
                        this.value = newValue;
                    }
                }
                library.ModifyValue = ModifyValue;
                ModifyValue["__class"] = "org.malai.action.library.ModifyValue";
                ModifyValue["__interfaces"] = ["org.malai.action.Action"];
            })(library = action.library || (action.library = {}));
        })(action = malai.action || (malai.action = {}));
    })(malai = org.malai || (org.malai = {}));
})(org || (org = {}));
var org;
(function (org) {
    var malai;
    (function (malai) {
        var action;
        (function (action) {
            var library;
            (function (library) {
                class PositionAction extends org.malai.action.ActionImpl {
                    constructor() {
                        super();
                        if (this.px === undefined)
                            this.px = 0;
                        if (this.py === undefined)
                            this.py = 0;
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
                }
                library.PositionAction = PositionAction;
                PositionAction["__class"] = "org.malai.action.library.PositionAction";
                PositionAction["__interfaces"] = ["org.malai.action.Action"];
            })(library = action.library || (action.library = {}));
        })(action = malai.action || (malai.action = {}));
    })(malai = org.malai || (org.malai = {}));
})(org || (org = {}));
var org;
(function (org) {
    var malai;
    (function (malai) {
        var action;
        (function (action) {
            var library;
            (function (library) {
                class Redo extends org.malai.action.ActionImpl {
                    constructor() {
                        super();
                    }
                    canDo() {
                        return org.malai.undo.UndoCollector.INSTANCE_$LI$().getLastRedo().isPresent();
                    }
                    doActionBody() {
                        org.malai.undo.UndoCollector.INSTANCE_$LI$().redo();
                        this.done();
                    }
                }
                library.Redo = Redo;
                Redo["__class"] = "org.malai.action.library.Redo";
                Redo["__interfaces"] = ["org.malai.action.Action"];
            })(library = action.library || (action.library = {}));
        })(action = malai.action || (malai.action = {}));
    })(malai = org.malai || (org.malai = {}));
})(org || (org = {}));
var org;
(function (org) {
    var malai;
    (function (malai) {
        var action;
        (function (action) {
            var library;
            (function (library) {
                class Undo extends org.malai.action.ActionImpl {
                    constructor() {
                        super();
                    }
                    canDo() {
                        return org.malai.undo.UndoCollector.INSTANCE_$LI$().getLastUndo().isPresent();
                    }
                    doActionBody() {
                        org.malai.undo.UndoCollector.INSTANCE_$LI$().undo();
                        this.done();
                    }
                }
                library.Undo = Undo;
                Undo["__class"] = "org.malai.action.library.Undo";
                Undo["__interfaces"] = ["org.malai.action.Action"];
            })(library = action.library || (action.library = {}));
        })(action = malai.action || (malai.action = {}));
    })(malai = org.malai || (org.malai = {}));
})(org || (org = {}));
var org;
(function (org) {
    var malai;
    (function (malai) {
        var action;
        (function (action) {
            var library;
            (function (library) {
                class Zoom extends org.malai.action.library.PositionAction {
                    constructor() {
                        super();
                        if (this.zoomable === undefined)
                            this.zoomable = null;
                        if (this.zoomLevel === undefined)
                            this.zoomLevel = 0;
                        this.zoomLevel = NaN;
                        this.zoomable = null;
                    }
                    flush() {
                        super.flush();
                        this.zoomable = null;
                    }
                    canDo() {
                        return this.zoomable != null && this.zoomLevel >= this.zoomable.getMinZoom() && this.zoomLevel <= this.zoomable.getMaxZoom();
                    }
                    doActionBody() {
                        this.zoomable.setZoom(this.px, this.py, this.zoomLevel);
                    }
                    setZoomable(newZoomable) {
                        this.zoomable = newZoomable;
                    }
                    setZoomLevel(newZoomLevel) {
                        this.zoomLevel = newZoomLevel;
                    }
                }
                library.Zoom = Zoom;
                Zoom["__class"] = "org.malai.action.library.Zoom";
                Zoom["__interfaces"] = ["org.malai.action.Action"];
            })(library = action.library || (action.library = {}));
        })(action = malai.action || (malai.action = {}));
    })(malai = org.malai || (org.malai = {}));
})(org || (org = {}));
var org;
(function (org) {
    var malai;
    (function (malai) {
        var binding;
        (function (binding) {
            class MustBeUndoableActionException extends Error {
                constructor(clazz) {
                    super("The following action must be undoable: " + (clazz == null ? "" : " " + (c => c["__class"] ? c["__class"] : c["name"])(clazz)));
                    this.message = "The following action must be undoable: " + (clazz == null ? "" : " " + (c => c["__class"] ? c["__class"] : c["name"])(clazz));
                    Object.setPrototypeOf(this, MustBeUndoableActionException.prototype);
                }
            }
            binding.MustBeUndoableActionException = MustBeUndoableActionException;
            MustBeUndoableActionException["__class"] = "org.malai.binding.MustBeUndoableActionException";
            MustBeUndoableActionException["__interfaces"] = ["java.io.Serializable"];
        })(binding = malai.binding || (malai.binding = {}));
    })(malai = org.malai || (org.malai = {}));
})(org || (org = {}));
var org;
(function (org) {
    var malai;
    (function (malai) {
        var binding;
        (function (binding) {
            class WidgetBindingImpl {
                constructor(ins, exec, actionClass, interaction) {
                    if (this.interaction === undefined)
                        this.interaction = null;
                    if (this.action === undefined)
                        this.action = null;
                    if (this.instrument === undefined)
                        this.instrument = null;
                    if (this.execute === undefined)
                        this.execute = false;
                    if (this.async === undefined)
                        this.async = false;
                    if (this.clazzAction === undefined)
                        this.clazzAction = null;
                    if (ins == null || actionClass == null || interaction == null) {
                        throw Object.defineProperty(new Error(), '__classes', { configurable: true, value: ['java.lang.Throwable', 'java.lang.Object', 'java.lang.RuntimeException', 'java.lang.IllegalArgumentException', 'java.lang.Exception'] });
                    }
                    this.clazzAction = actionClass;
                    this.interaction = interaction;
                    this.action = null;
                    this.instrument = ins;
                    this.execute = exec;
                    this.interaction.getFsm().addHandler(this);
                    this.setActivated(ins.isActivated());
                    this.async = false;
                }
                logBinding(log) {
                }
                logAction(log) {
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
                    try {
                        return new this.clazzAction();
                    }
                    catch (ex) {
                        org.malai.error.ErrorCatcher.INSTANCE_$LI$().reportError(ex);
                        return null;
                    }
                    ;
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
                    return this.instrument.isActivated();
                }
                isRunning() {
                    return this.interaction.isRunning();
                }
                isStrictStart() {
                    return false;
                }
                unbindActionAttributes() {
                    if (this.action != null) {
                        this.unbindActionAttributesClass(this.action.constructor);
                    }
                }
                unbindActionAttributesClass(clazz) {
                }
                fsmCancels() {
                    if (this.action != null) {
                        this.action.cancel();
                        this.unbindActionAttributes();
                        this.instrument.onActionCancelled(this.action);
                        if (this.isExecute() && this.action.hadEffect()) {
                            if (this.action != null && (this.action["__interfaces"] != null && this.action["__interfaces"].indexOf("org.malai.undo.Undoable") >= 0 || this.action.constructor != null && this.action.constructor["__interfaces"] != null && this.action.constructor["__interfaces"].indexOf("org.malai.undo.Undoable") >= 0)) {
                                this.action.undo();
                            }
                            else {
                                throw new org.malai.binding.MustBeUndoableActionException(this.action.constructor);
                            }
                        }
                        this.action = null;
                        this.instrument.interimFeedback();
                    }
                }
                fsmStarts() {
                    let ok = this.action == null && this.isActivated() && this.when();
                    if (ok) {
                        this.action = this.map();
                        this.first();
                        this.feedback();
                    }
                    else {
                        if (this.isStrictStart()) {
                            throw new org.malai.fsm.CancelFSMException();
                        }
                    }
                }
                fsmStops() {
                    let ok = this.when();
                    if (ok) {
                        if (this.action == null) {
                            this.action = this.map();
                            this.first();
                        }
                        if (!this.execute) {
                            this.then();
                        }
                        this.executeAction(this.action, this.async);
                        this.unbindActionAttributes();
                        this.action = null;
                        this.instrument.interimFeedback();
                    }
                    else {
                        if (this.action != null) {
                            this.action.cancel();
                            this.unbindActionAttributes();
                            this.instrument.onActionCancelled(this.action);
                            this.action = null;
                            this.instrument.interimFeedback();
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
                    if (ok) {
                        this.instrument.onActionExecuted(act);
                        act.done();
                        this.instrument.onActionDone(act);
                    }
                    let hadEffect = act.hadEffect();
                    if (hadEffect) {
                        if (act.getRegistrationPolicy() !== org.malai.action.Action.RegistrationPolicy.NONE) {
                            org.malai.action.ActionsRegistry.INSTANCE_$LI$().addAction(act, this.instrument);
                            this.instrument.onActionAdded(act);
                        }
                        else {
                            org.malai.action.ActionsRegistry.INSTANCE_$LI$().unregisterActions(act);
                        }
                        act.followingActions().forEach((actFollow) => this.executeAction(actFollow, false));
                    }
                }
                fsmUpdates() {
                    let ok = this.when();
                    if (ok) {
                        if (this.action == null) {
                            this.action = this.map();
                            this.first();
                        }
                        this.then();
                        if (this.execute && this.action.canDo()) {
                            this.action.doIt();
                            this.instrument.onActionExecuted(this.action);
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
                    this.interaction.setActivated(activ);
                }
                getInstrument() {
                    return this.instrument;
                }
            }
            binding.WidgetBindingImpl = WidgetBindingImpl;
            WidgetBindingImpl["__class"] = "org.malai.binding.WidgetBindingImpl";
            WidgetBindingImpl["__interfaces"] = ["org.malai.fsm.FSMHandler", "org.malai.binding.WidgetBinding"];
        })(binding = malai.binding || (malai.binding = {}));
    })(malai = org.malai || (org.malai = {}));
})(org || (org = {}));
var org;
(function (org) {
    var malai;
    (function (malai) {
        var error;
        (function (error) {
            class ErrorCatcher {
                constructor() {
                    if (this.notifier === undefined)
                        this.notifier = null;
                }
                static INSTANCE_$LI$() { if (ErrorCatcher.INSTANCE == null)
                    ErrorCatcher.INSTANCE = new ErrorCatcher(); return ErrorCatcher.INSTANCE; }
                ;
                setNotifier(newNotifier) {
                    this.notifier = newNotifier;
                }
                getErrorNotifier() {
                    return this.notifier;
                }
                reportError(exception) {
                    if (exception != null && this.notifier != null) {
                        this.notifier.onException(exception);
                    }
                }
            }
            error.ErrorCatcher = ErrorCatcher;
            ErrorCatcher["__class"] = "org.malai.error.ErrorCatcher";
        })(error = malai.error || (malai.error = {}));
    })(malai = org.malai || (org.malai = {}));
})(org || (org = {}));
org.malai.error.ErrorCatcher.INSTANCE_$LI$();
var org;
(function (org) {
    var malai;
    (function (malai) {
        var fsm;
        (function (fsm) {
            class CancelFSMException extends Error {
                constructor() {
                    super();
                    Object.setPrototypeOf(this, CancelFSMException.prototype);
                }
            }
            fsm.CancelFSMException = CancelFSMException;
            CancelFSMException["__class"] = "org.malai.fsm.CancelFSMException";
            CancelFSMException["__interfaces"] = ["java.io.Serializable"];
        })(fsm = malai.fsm || (malai.fsm = {}));
    })(malai = org.malai || (org.malai = {}));
})(org || (org = {}));
var org;
(function (org) {
    var malai;
    (function (malai) {
        var fsm;
        (function (fsm) {
            class StateImpl {
                constructor(stateMachine, stateName) {
                    if (this.fsm === undefined)
                        this.fsm = null;
                    if (this.name === undefined)
                        this.name = null;
                    this.fsm = stateMachine;
                    this.name = stateName;
                }
                checkStartingState() {
                    if (!this.getFSM().isStarted() && this.getFSM().startingState === this) {
                        this.getFSM().onStarting();
                    }
                }
                getName() {
                    return this.name;
                }
                getFSM() {
                    return this.fsm;
                }
                toString() {
                    return (c => c["__class"] ? c["__class"].substring(c["__class"].lastIndexOf('.') + 1) : c["name"].substring(c["name"].lastIndexOf('.') + 1))(this.constructor) + "{name=\'" + this.name + "\'}";
                }
            }
            fsm.StateImpl = StateImpl;
            StateImpl["__class"] = "org.malai.fsm.StateImpl";
            StateImpl["__interfaces"] = ["org.malai.fsm.State"];
        })(fsm = malai.fsm || (malai.fsm = {}));
    })(malai = org.malai || (org.malai = {}));
})(org || (org = {}));
var org;
(function (org) {
    var malai;
    (function (malai) {
        var fsm;
        (function (fsm) {
            class CancellingState extends org.malai.fsm.StateImpl {
                constructor(stateMachine, stateName) {
                    super(stateMachine, stateName);
                }
                checkStartingState() {
                    if (!this.getFSM().isStarted() && this.getFSM().startingState === this) {
                        this.getFSM().onStarting();
                    }
                }
                enter() {
                    this.fsm.onCancelling();
                }
            }
            fsm.CancellingState = CancellingState;
            CancellingState["__class"] = "org.malai.fsm.CancellingState";
            CancellingState["__interfaces"] = ["org.malai.fsm.State", "org.malai.fsm.InputState"];
        })(fsm = malai.fsm || (malai.fsm = {}));
    })(malai = org.malai || (org.malai = {}));
})(org || (org = {}));
var org;
(function (org) {
    var malai;
    (function (malai) {
        var fsm;
        (function (fsm) {
            class Transition {
                constructor(srcState, tgtState) {
                    if (this.src === undefined)
                        this.src = null;
                    if (this.tgt === undefined)
                        this.tgt = null;
                    if (srcState == null || tgtState == null) {
                        throw Object.defineProperty(new Error("States cannot be null"), '__classes', { configurable: true, value: ['java.lang.Throwable', 'java.lang.Object', 'java.lang.RuntimeException', 'java.lang.IllegalArgumentException', 'java.lang.Exception'] });
                    }
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
                        return util.Optional.of(this.tgt);
                    }
                    return util.Optional.empty();
                }
                action(event) {
                }
            }
            fsm.Transition = Transition;
            Transition["__class"] = "org.malai.fsm.Transition";
        })(fsm = malai.fsm || (malai.fsm = {}));
    })(malai = org.malai || (org.malai = {}));
})(org || (org = {}));
var org;
(function (org) {
    var malai;
    (function (malai) {
        var fsm;
        (function (fsm) {
            class EpsilonTransition extends org.malai.fsm.Transition {
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
                    return [];
                }
            }
            fsm.EpsilonTransition = EpsilonTransition;
            EpsilonTransition["__class"] = "org.malai.fsm.EpsilonTransition";
        })(fsm = malai.fsm || (malai.fsm = {}));
    })(malai = org.malai || (org.malai = {}));
})(org || (org = {}));
var org;
(function (org) {
    var malai;
    (function (malai) {
        var fsm;
        (function (fsm) {
            class FSM {
                constructor() {
                    if (this.inner === undefined)
                        this.inner = false;
                    if (this.startingState === undefined)
                        this.startingState = null;
                    if (this.started === undefined)
                        this.started = false;
                    if (this.initState === undefined)
                        this.initState = null;
                    if (this.currentState === undefined)
                        this.currentState = null;
                    if (this.states === undefined)
                        this.states = null;
                    if (this.handlers === undefined)
                        this.handlers = null;
                    if (this.eventsToProcess === undefined)
                        this.eventsToProcess = null;
                    if (this.currentTimeout === undefined)
                        this.currentTimeout = null;
                    if (this.currentSubFSM === undefined)
                        this.currentSubFSM = null;
                    this.started = false;
                    this.initState = (new org.malai.fsm.InitState(this, "init"));
                    this.states = ([]);
                    ((s, e) => { if (s.indexOf(e) == -1) {
                        s.push(e);
                        return true;
                    }
                    else {
                        return false;
                    } })(this.states, this.initState);
                    this.startingState = this.initState;
                    this.currentState = (new org.malai.utils.ObsValue(this.initState));
                    this.inner = false;
                    this.handlers = ([]);
                }
                getCurrentState() {
                    return this.currentState.get();
                }
                setInner(inner) {
                    this.inner = inner;
                }
                process(event) {
                    if (event == null) {
                        return false;
                    }
                    if (this.currentSubFSM != null) {
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
                }
                addRemaningEventsToProcess(event) {
                    if (event != null) {
                        if (this.eventsToProcess == null) {
                            this.eventsToProcess = ([]);
                        }
                        {
                            (this.eventsToProcess.push(event) > 0);
                        }
                        ;
                    }
                }
                onTerminating() {
                    if (this.started) {
                        this.notifyHandlerOnStop();
                    }
                    this.reinit();
                    this.processRemainingEvents();
                }
                onCancelling() {
                    if (this.started) {
                        this.notifyHandlerOnCancel();
                    }
                    this.fullReinit();
                }
                onStarting() {
                    this.started = true;
                    this.notifyHandlerOnStart();
                }
                onUpdating() {
                    if (this.started) {
                        this.notifyHandlerOnUpdate();
                    }
                }
                addState(state) {
                    if (state != null) {
                        ((s, e) => { if (s.indexOf(e) == -1) {
                            s.push(e);
                            return true;
                        }
                        else {
                            return false;
                        } })(this.states, state);
                    }
                }
                log(log) {
                }
                reinit() {
                    if (this.currentTimeout != null) {
                        this.currentTimeout.stopTimeout();
                    }
                    this.started = false;
                    this.currentState.set(this.initState);
                    this.currentTimeout = null;
                    if (this.currentSubFSM != null) {
                        this.currentSubFSM.reinit();
                    }
                }
                fullReinit() {
                    if (this.eventsToProcess != null) {
                        {
                            (this.eventsToProcess.length = 0);
                        }
                        ;
                    }
                    this.reinit();
                    if (this.currentSubFSM != null) {
                        this.currentSubFSM.fullReinit();
                    }
                }
                onTimeout() {
                }
                stopCurrentTimeout() {
                    if (this.currentTimeout != null) {
                        this.currentTimeout.stopTimeout();
                        this.currentTimeout = null;
                    }
                }
                checkTimeoutTransition() {
                }
                addHandler(handler) {
                    if (handler != null) {
                        ((s, e) => { if (s.indexOf(e) == -1) {
                            s.push(e);
                            return true;
                        }
                        else {
                            return false;
                        } })(this.handlers, handler);
                    }
                }
                removeHandler(handler) {
                    if (handler != null) {
                        (a => { let index = a.indexOf(handler); if (index >= 0) {
                            a.splice(index, 1);
                            return true;
                        }
                        else {
                            return false;
                        } })(this.handlers);
                    }
                }
                notifyHandlerOnStart() {
                    try {
                        for (let index125 = 0; index125 < this.handlers.length; index125++) {
                            let handler = this.handlers[index125];
                            {
                                handler.fsmStarts();
                            }
                        }
                    }
                    catch (ex) {
                        this.onCancelling();
                        throw ex;
                    }
                    ;
                }
                notifyHandlerOnUpdate() {
                    try {
                        for (let index126 = 0; index126 < this.handlers.length; index126++) {
                            let handler = this.handlers[index126];
                            {
                                handler.fsmUpdates();
                            }
                        }
                    }
                    catch (ex) {
                        this.onCancelling();
                        throw ex;
                    }
                    ;
                }
                notifyHandlerOnStop() {
                    try {
                        for (let index127 = 0; index127 < this.handlers.length; index127++) {
                            let handler = this.handlers[index127];
                            {
                                handler.fsmStops();
                            }
                        }
                    }
                    catch (ex) {
                        this.onCancelling();
                        throw ex;
                    }
                    ;
                }
                notifyHandlerOnCancel() {
                    this.handlers.forEach((handler) => handler.fsmCancels());
                }
                getStates() {
                    return this.states.slice(0);
                }
                currentStateProp() {
                    return this.currentState;
                }
            }
            fsm.FSM = FSM;
            FSM["__class"] = "org.malai.fsm.FSM";
        })(fsm = malai.fsm || (malai.fsm = {}));
    })(malai = org.malai || (org.malai = {}));
})(org || (org = {}));
var org;
(function (org) {
    var malai;
    (function (malai) {
        var fsm;
        (function (fsm) {
            class OutputStateImpl extends org.malai.fsm.StateImpl {
                constructor(stateMachine, stateName) {
                    super(stateMachine, stateName);
                    if (this.transitions === undefined)
                        this.transitions = null;
                    this.transitions = ([]);
                }
                process(event) {
                    {
                        let array129 = this.getTransitions();
                        for (let index128 = 0; index128 < array129.length; index128++) {
                            let tr = array129[index128];
                            {
                                try {
                                    if (tr.execute(event).isPresent()) {
                                        return true;
                                    }
                                }
                                catch (ignored) {
                                }
                                ;
                            }
                        }
                    }
                    return false;
                }
                checkStartingState() {
                    if (!this.getFSM().isStarted() && this.getFSM().startingState === this) {
                        this.getFSM().onStarting();
                    }
                }
                getTransitions() {
                    return this.transitions.slice(0);
                }
                addTransition(tr) {
                    if (tr != null) {
                        (this.transitions.push(tr) > 0);
                    }
                }
            }
            fsm.OutputStateImpl = OutputStateImpl;
            OutputStateImpl["__class"] = "org.malai.fsm.OutputStateImpl";
            OutputStateImpl["__interfaces"] = ["org.malai.fsm.State", "org.malai.fsm.OutputState"];
        })(fsm = malai.fsm || (malai.fsm = {}));
    })(malai = org.malai || (org.malai = {}));
})(org || (org = {}));
var org;
(function (org) {
    var malai;
    (function (malai) {
        var fsm;
        (function (fsm) {
            class InitState extends org.malai.fsm.OutputStateImpl {
                constructor(stateMachine, stateName) {
                    super(stateMachine, stateName);
                }
                exit() {
                    this.checkStartingState();
                }
            }
            fsm.InitState = InitState;
            InitState["__class"] = "org.malai.fsm.InitState";
            InitState["__interfaces"] = ["org.malai.fsm.State", "org.malai.fsm.OutputState"];
        })(fsm = malai.fsm || (malai.fsm = {}));
    })(malai = org.malai || (org.malai = {}));
})(org || (org = {}));
var org;
(function (org) {
    var malai;
    (function (malai) {
        var fsm;
        (function (fsm) {
            class StdState extends org.malai.fsm.OutputStateImpl {
                constructor(stateMachine, stateName) {
                    super(stateMachine, stateName);
                }
                checkStartingState() {
                    if (!this.getFSM().isStarted() && this.getFSM().startingState === this) {
                        this.getFSM().onStarting();
                    }
                }
                enter() {
                    this.checkStartingState();
                    this.fsm.enterStdState(this);
                }
                exit() {
                }
            }
            fsm.StdState = StdState;
            StdState["__class"] = "org.malai.fsm.StdState";
            StdState["__interfaces"] = ["org.malai.fsm.State", "org.malai.fsm.OutputState", "org.malai.fsm.InputState"];
        })(fsm = malai.fsm || (malai.fsm = {}));
    })(malai = org.malai || (org.malai = {}));
})(org || (org = {}));
var util;
(function (util) {
    class Optional {
        constructor(obj) {
            this.value = obj;
        }
        static empty() {
            return Optional.EMPTY;
        }
        static of(var0) {
            return new Optional(var0);
        }
        static ofNullable(var0) {
            return var0 === undefined ? Optional.empty() : Optional.of(var0);
        }
        get() {
            if (this.value === undefined) {
                throw new TypeError("No value present");
            }
            else {
                return this.value;
            }
        }
        isPresent() {
            return this.value !== undefined;
        }
        filter(predicate) {
            if (!this.isPresent()) {
                return this;
            }
            else {
                return predicate(this.value) ? this : Optional.empty();
            }
        }
    }
    Optional.EMPTY = new Optional();
    util.Optional = Optional;
})(util || (util = {}));
Object.defineProperty(Array.prototype, 'flatMap', {
    value: function (f) {
        return this.reduce((ys, x) => {
            return ys.concat(f.call(this, x));
        }, []);
    },
    enumerable: false,
});
var org;
(function (org) {
    var malai;
    (function (malai) {
        var fsm;
        (function (fsm_1) {
            var Optional = util.Optional;
            class SubFSMTransition extends org.malai.fsm.Transition {
                constructor(srcState, tgtState, fsm) {
                    super(srcState, tgtState);
                    if (this.subFSM === undefined)
                        this.subFSM = null;
                    if (this.subFSMHandler === undefined)
                        this.subFSMHandler = null;
                    if (fsm == null) {
                        throw Object.defineProperty(new Error("sub fsm cannot be null"), '__classes', { configurable: true, value: ['java.lang.Throwable', 'java.lang.Object', 'java.lang.RuntimeException', 'java.lang.IllegalArgumentException', 'java.lang.Exception'] });
                    }
                    this.subFSM = fsm;
                    this.subFSM.setInner(true);
                    this.subFSMHandler = new SubFSMTransition.SubFSMTransition$0(this);
                }
                execute(event) {
                    if (this.isGuardOK(event)) {
                        this.src.getFSM().stopCurrentTimeout();
                        let transition = this.findTransition(event);
                        if (transition.isPresent()) {
                            this.subFSM.addHandler(this.subFSMHandler);
                            this.src.getFSM().currentSubFSM = this.subFSM;
                            this.subFSM.process(event);
                            return util.Optional.of(transition.get().tgt);
                        }
                    }
                    return util.Optional.empty();
                }
                accept(event) {
                    return this.findTransition(event).isPresent();
                }
                isGuardOK(event) {
                    return this.findTransition(event).filter((tr) => tr.isGuardOK(event)).isPresent();
                }
                findTransition(event) {
                    return Optional.of(this.subFSM.initState.transitions.find((tr) => tr.accept(event)));
                }
                getAcceptedEvents() {
                    return (this.subFSM.initState.getTransitions().map((tr) => tr.getAcceptedEvents()).
                        flatMap((s) => s.stream()));
                }
            }
            fsm_1.SubFSMTransition = SubFSMTransition;
            SubFSMTransition["__class"] = "org.malai.fsm.SubFSMTransition";
            (function (SubFSMTransition) {
                class SubFSMTransition$0 {
                    constructor(__parent) {
                        this.__parent = __parent;
                    }
                    fsmStarts() {
                        this.__parent.src.exit();
                    }
                    fsmUpdates() {
                        this.__parent.src.getFSM().setCurrentState(this.__parent.subFSM.getCurrentState());
                        this.__parent.src.getFSM().onUpdating();
                    }
                    fsmStops() {
                        this.__parent.action(null);
                        this.__parent.subFSM.removeHandler(this.__parent.subFSMHandler);
                        this.__parent.src.getFSM().currentSubFSM = null;
                        if (this.__parent.tgt != null && this.__parent.tgt instanceof org.malai.fsm.TerminalState) {
                            this.__parent.tgt.enter();
                            return;
                        }
                        if (this.__parent.tgt != null && this.__parent.tgt instanceof org.malai.fsm.CancellingState) {
                            this.fsmCancels();
                            return;
                        }
                        if (this.__parent.tgt != null && (this.__parent.tgt["__interfaces"] != null && this.__parent.tgt["__interfaces"].indexOf("org.malai.fsm.OutputState") >= 0 || this.__parent.tgt.constructor != null && this.__parent.tgt.constructor["__interfaces"] != null && this.__parent.tgt.constructor["__interfaces"].indexOf("org.malai.fsm.OutputState") >= 0)) {
                            this.__parent.src.getFSM().setCurrentState(this.__parent.tgt);
                            this.__parent.tgt.enter();
                        }
                    }
                    fsmCancels() {
                        this.__parent.subFSM.removeHandler(this.__parent.subFSMHandler);
                        this.__parent.src.getFSM().currentSubFSM = null;
                        this.__parent.src.getFSM().onCancelling();
                    }
                }
                SubFSMTransition.SubFSMTransition$0 = SubFSMTransition$0;
                SubFSMTransition$0["__interfaces"] = ["org.malai.fsm.FSMHandler"];
            })(SubFSMTransition = fsm_1.SubFSMTransition || (fsm_1.SubFSMTransition = {}));
        })(fsm = malai.fsm || (malai.fsm = {}));
    })(malai = org.malai || (org.malai = {}));
})(org || (org = {}));
var org;
(function (org) {
    var malai;
    (function (malai) {
        var fsm;
        (function (fsm) {
            class TerminalState extends org.malai.fsm.StateImpl {
                constructor(stateMachine, stateName) {
                    super(stateMachine, stateName);
                }
                checkStartingState() {
                    if (!this.getFSM().isStarted() && this.getFSM().startingState === this) {
                        this.getFSM().onStarting();
                    }
                }
                enter() {
                    this.checkStartingState();
                    this.fsm.onTerminating();
                }
            }
            fsm.TerminalState = TerminalState;
            TerminalState["__class"] = "org.malai.fsm.TerminalState";
            TerminalState["__interfaces"] = ["org.malai.fsm.State", "org.malai.fsm.InputState"];
        })(fsm = malai.fsm || (malai.fsm = {}));
    })(malai = org.malai || (org.malai = {}));
})(org || (org = {}));
var org;
(function (org) {
    var malai;
    (function (malai) {
        var fsm;
        (function (fsm) {
            class TimeoutTransition extends org.malai.fsm.Transition {
                constructor(srcState, tgtState, timeout) {
                    super(srcState, tgtState);
                    if (this.timeoutDuration === undefined)
                        this.timeoutDuration = null;
                    if (this.timeouted === undefined)
                        this.timeouted = false;
                    if (timeout == null) {
                        throw Object.defineProperty(new Error(), '__classes', { configurable: true, value: ['java.lang.Throwable', 'java.lang.Object', 'java.lang.RuntimeException', 'java.lang.IllegalArgumentException', 'java.lang.Exception'] });
                    }
                    this.timeoutDuration = (timeout);
                    this.timeouted = false;
                }
                startTimeout() {
                }
                stopTimeout() {
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
                            return util.Optional.of(this.tgt);
                        }
                        return util.Optional.empty();
                    }
                    catch (ex) {
                        this.timeouted = false;
                        throw ex;
                    }
                    ;
                }
                getAcceptedEvents() {
                    return [];
                }
            }
            fsm.TimeoutTransition = TimeoutTransition;
            TimeoutTransition["__class"] = "org.malai.fsm.TimeoutTransition";
        })(fsm = malai.fsm || (malai.fsm = {}));
    })(malai = org.malai || (org.malai = {}));
})(org || (org = {}));
var org;
(function (org) {
    var malai;
    (function (malai) {
        var fsm;
        (function (fsm) {
            class WidgetTransition extends org.malai.fsm.Transition {
                constructor(srcState, tgtState) {
                    super(srcState, tgtState);
                    if (this.widget === undefined)
                        this.widget = null;
                }
                getWidget() {
                    return this.widget;
                }
                setWidget(widget) {
                    if (widget != null) {
                        this.widget = widget;
                    }
                }
            }
            fsm.WidgetTransition = WidgetTransition;
            WidgetTransition["__class"] = "org.malai.fsm.WidgetTransition";
        })(fsm = malai.fsm || (malai.fsm = {}));
    })(malai = org.malai || (org.malai = {}));
})(org || (org = {}));
var org;
(function (org) {
    var malai;
    (function (malai) {
        var instrument;
        (function (instrument) {
            class InstrumentImpl {
                constructor() {
                    if (this.activated === undefined)
                        this.activated = false;
                    if (this.bindings === undefined)
                        this.bindings = null;
                    if (this.modified === undefined)
                        this.modified = false;
                    this.activated = false;
                    this.modified = false;
                    this.bindings = ([]);
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
                    if (binding != null) {
                        (this.bindings.push(binding) > 0);
                        binding.setActivated(this.isActivated());
                    }
                }
                removeBinding(binding) {
                    return binding != null && (a => { let index = a.indexOf(binding); if (index >= 0) {
                        a.splice(index, 1);
                        return true;
                    }
                    else {
                        return false;
                    } })(this.bindings);
                }
                clearEvents() {
                    this.bindings.forEach((binding) => binding.clearEvents());
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
                            org.malai.error.ErrorCatcher.INSTANCE_$LI$().reportError(ex);
                        }
                        ;
                    }
                    else {
                        this.bindings.forEach((binding) => binding.setActivated(toBeActivated));
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
            }
            instrument.InstrumentImpl = InstrumentImpl;
            InstrumentImpl["__class"] = "org.malai.instrument.InstrumentImpl";
            InstrumentImpl["__interfaces"] = ["org.malai.instrument.Instrument", "org.malai.action.ActionHandler", "org.malai.properties.Preferenciable", "org.malai.properties.Reinitialisable", "org.malai.undo.UndoHandler", "org.malai.properties.Modifiable"];
        })(instrument = malai.instrument || (malai.instrument = {}));
    })(malai = org.malai || (org.malai = {}));
})(org || (org = {}));
var org;
(function (org) {
    var malai;
    (function (malai) {
        var interaction;
        (function (interaction) {
            class StateImpl {
                constructor(name) {
                    if (this.name === undefined)
                        this.name = null;
                    if (this.transitions === undefined)
                        this.transitions = null;
                    if (this.stateMachine === undefined)
                        this.stateMachine = null;
                    if (name == null)
                        throw Object.defineProperty(new Error(), '__classes', { configurable: true, value: ['java.lang.Throwable', 'java.lang.Object', 'java.lang.RuntimeException', 'java.lang.IllegalArgumentException', 'java.lang.Exception'] });
                    this.name = name;
                    this.stateMachine = null;
                    this.transitions = ([]);
                }
                setStateMachine(sm) {
                    if (sm != null && (sm["__interfaces"] != null && sm["__interfaces"].indexOf("org.malai.interaction.Interaction") >= 0 || sm.constructor != null && sm.constructor["__interfaces"] != null && sm.constructor["__interfaces"].indexOf("org.malai.interaction.Interaction") >= 0)) {
                        this.stateMachine = sm;
                    }
                }
                getStateMachine() {
                    return this.stateMachine;
                }
                addTransition(trans) {
                    if (trans != null) {
                        (this.transitions.push(trans) > 0);
                    }
                }
                getName() {
                    return this.name;
                }
                getTransitions() {
                    return this.transitions;
                }
                getTransition(i) {
                    return i < 0 || i >= this.transitions.length ? null : this.transitions[i];
                }
            }
            interaction.StateImpl = StateImpl;
            StateImpl["__class"] = "org.malai.interaction.StateImpl";
            StateImpl["__interfaces"] = ["org.malai.stateMachine.State"];
        })(interaction = malai.interaction || (malai.interaction = {}));
    })(malai = org.malai || (org.malai = {}));
})(org || (org = {}));
var org;
(function (org) {
    var malai;
    (function (malai) {
        var interaction;
        (function (interaction) {
            class CancellingState extends org.malai.interaction.StateImpl {
                constructor(name) {
                    super(name);
                }
                onIngoing() {
                    this.stateMachine.onCancelling();
                }
            }
            interaction.CancellingState = CancellingState;
            CancellingState["__class"] = "org.malai.interaction.CancellingState";
            CancellingState["__interfaces"] = ["org.malai.stateMachine.TargetableState", "org.malai.stateMachine.State"];
        })(interaction = malai.interaction || (malai.interaction = {}));
    })(malai = org.malai || (org.malai = {}));
})(org || (org = {}));
var org;
(function (org) {
    var malai;
    (function (malai) {
        var interaction;
        (function (interaction) {
            class InitState extends org.malai.interaction.StateImpl {
                constructor() {
                    super("Init");
                }
                onOutgoing() {
                    this.stateMachine.onStarting();
                }
            }
            interaction.InitState = InitState;
            InitState["__class"] = "org.malai.interaction.InitState";
            InitState["__interfaces"] = ["org.malai.stateMachine.SourceableState", "org.malai.stateMachine.State"];
        })(interaction = malai.interaction || (malai.interaction = {}));
    })(malai = org.malai || (org.malai = {}));
})(org || (org = {}));
var org;
(function (org) {
    var malai;
    (function (malai) {
        var interaction;
        (function (interaction) {
            class InteractionImpl {
                constructor(initState) {
                    if (((initState != null && initState instanceof org.malai.interaction.InitState) || initState === null)) {
                        let __args = Array.prototype.slice.call(arguments);
                        if (this.states === undefined)
                            this.states = null;
                        if (this.initState === undefined)
                            this.initState = null;
                        if (this.currentState === undefined)
                            this.currentState = null;
                        if (this.activated === undefined)
                            this.activated = false;
                        if (this.handlers === undefined)
                            this.handlers = null;
                        if (this.stillProcessingEvents === undefined)
                            this.stillProcessingEvents = null;
                        if (this.currentTimeout === undefined)
                            this.currentTimeout = null;
                        if (this.lastHIDUsed === undefined)
                            this.lastHIDUsed = 0;
                        if (this.states === undefined)
                            this.states = null;
                        if (this.initState === undefined)
                            this.initState = null;
                        if (this.currentState === undefined)
                            this.currentState = null;
                        if (this.activated === undefined)
                            this.activated = false;
                        if (this.handlers === undefined)
                            this.handlers = null;
                        if (this.stillProcessingEvents === undefined)
                            this.stillProcessingEvents = null;
                        if (this.currentTimeout === undefined)
                            this.currentTimeout = null;
                        if (this.lastHIDUsed === undefined)
                            this.lastHIDUsed = 0;
                        (() => {
                            if (initState == null) {
                                throw Object.defineProperty(new Error(), '__classes', { configurable: true, value: ['java.lang.Throwable', 'java.lang.Object', 'java.lang.RuntimeException', 'java.lang.IllegalArgumentException', 'java.lang.Exception'] });
                            }
                            this.currentTimeout = null;
                            this.activated = true;
                            this.states = ([]);
                            initState.stateMachine = this;
                            this.initState = initState;
                            this.addState(initState);
                            this.currentState = initState;
                            this.lastHIDUsed = -1;
                        })();
                    }
                    else if (initState === undefined) {
                        let __args = Array.prototype.slice.call(arguments);
                        {
                            let __args = Array.prototype.slice.call(arguments);
                            let initState = new org.malai.interaction.InitState();
                            if (this.states === undefined)
                                this.states = null;
                            if (this.initState === undefined)
                                this.initState = null;
                            if (this.currentState === undefined)
                                this.currentState = null;
                            if (this.activated === undefined)
                                this.activated = false;
                            if (this.handlers === undefined)
                                this.handlers = null;
                            if (this.stillProcessingEvents === undefined)
                                this.stillProcessingEvents = null;
                            if (this.currentTimeout === undefined)
                                this.currentTimeout = null;
                            if (this.lastHIDUsed === undefined)
                                this.lastHIDUsed = 0;
                            if (this.states === undefined)
                                this.states = null;
                            if (this.initState === undefined)
                                this.initState = null;
                            if (this.currentState === undefined)
                                this.currentState = null;
                            if (this.activated === undefined)
                                this.activated = false;
                            if (this.handlers === undefined)
                                this.handlers = null;
                            if (this.stillProcessingEvents === undefined)
                                this.stillProcessingEvents = null;
                            if (this.currentTimeout === undefined)
                                this.currentTimeout = null;
                            if (this.lastHIDUsed === undefined)
                                this.lastHIDUsed = 0;
                            (() => {
                                if (initState == null) {
                                    throw Object.defineProperty(new Error(), '__classes', { configurable: true, value: ['java.lang.Throwable', 'java.lang.Object', 'java.lang.RuntimeException', 'java.lang.IllegalArgumentException', 'java.lang.Exception'] });
                                }
                                this.currentTimeout = null;
                                this.activated = true;
                                this.states = ([]);
                                initState.stateMachine = this;
                                this.initState = initState;
                                this.addState(initState);
                                this.currentState = initState;
                                this.lastHIDUsed = -1;
                            })();
                        }
                    }
                    else
                        throw new Error('invalid overload');
                }
                log(log) {
                }
                setCurrentState(state) {
                    let oldState = this.currentState;
                    this.currentState = state;
                    this.changeEventsRegistered(oldState);
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
                    this.currentTimeout = null;
                    this.setCurrentState(this.initState);
                    this.lastHIDUsed = -1;
                }
                getHandlers() {
                    return this.handlers;
                }
                addHandler(handler) {
                    if (handler != null) {
                        if (this.handlers == null) {
                            this.handlers = ([]);
                        }
                        (this.handlers.push(handler) > 0);
                    }
                }
                notifyHandlersOnStart() {
                    try {
                        if (this.handlers != null) {
                            for (let index131 = 0; index131 < this.handlers.length; index131++) {
                                let handler = this.handlers[index131];
                                {
                                    handler.interactionStarts();
                                }
                            }
                        }
                    }
                    catch (ex) {
                        this.notifyHandlersOnCancel();
                        throw ex;
                    }
                    ;
                }
                notifyHandlersOnUpdate() {
                    try {
                        if (this.handlers != null) {
                            for (let index132 = 0; index132 < this.handlers.length; index132++) {
                                let handler = this.handlers[index132];
                                {
                                    handler.interactionUpdates();
                                }
                            }
                        }
                    }
                    catch (ex) {
                        this.notifyHandlersOnCancel();
                        throw ex;
                    }
                    ;
                }
                notifyHandlersOnStop() {
                    try {
                        if (this.handlers != null) {
                            for (let index133 = 0; index133 < this.handlers.length; index133++) {
                                let handler = this.handlers[index133];
                                {
                                    handler.interactionStops();
                                }
                            }
                        }
                    }
                    catch (ex) {
                        this.notifyHandlersOnCancel();
                        throw ex;
                    }
                    ;
                }
                notifyHandlersOnCancel() {
                    if (this.handlers != null) {
                        this.handlers.forEach((handler) => handler.interactionCancels());
                    }
                }
                addState(state) {
                    if (state != null) {
                        ((s, e) => { if (s.indexOf(e) == -1) {
                            s.push(e);
                            return true;
                        }
                        else {
                            return false;
                        } })(this.states, state);
                        state.setStateMachine(this);
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
                            this.setCurrentState(transition.getOutputState());
                            transition.getOutputState().onIngoing();
                        }
                        catch (ex) {
                            this.reinit();
                        }
                        ;
                    }
                }
                stopCurrentTimeout() {
                    if (this.currentTimeout != null) {
                        this.currentTimeout.stopTimeout();
                        this.currentTimeout = null;
                    }
                }
                checkTransition(transition) {
                    let ok;
                    if (transition != null && (this.states.indexOf((transition.getInputState())) >= 0) && transition.isGuardRespected()) {
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
                }
                onTerminating() {
                    this.notifyHandlersOnStop();
                    this.reinit();
                    this.processEvents();
                }
                onCancelling() {
                    this.notifyHandlersOnCancel();
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
                }
                getLastHIDUsed() {
                    return this.lastHIDUsed;
                }
                setLastHIDUsed(hid) {
                    this.lastHIDUsed = hid;
                }
                clearEventsStillInProcess() {
                    if (this.stillProcessingEvents != null) {
                        {
                            (this.stillProcessingEvents.length = 0);
                        }
                        ;
                    }
                }
                clearEvents() {
                    this.reinit();
                    this.clearEventsStillInProcess();
                }
            }
            interaction.InteractionImpl = InteractionImpl;
            InteractionImpl["__class"] = "org.malai.interaction.InteractionImpl";
            InteractionImpl["__interfaces"] = ["org.malai.interaction.Interaction", "org.malai.stateMachine.StateMachine", "org.malai.interaction.EventProcessor"];
            (function (InteractionImpl) {
                class Event {
                    constructor(idHID) {
                        if (this.idHID === undefined)
                            this.idHID = 0;
                        this.idHID = idHID;
                    }
                    getIdHID() {
                        return this.idHID;
                    }
                }
                InteractionImpl.Event = Event;
                Event["__class"] = "org.malai.interaction.InteractionImpl.Event";
            })(InteractionImpl = interaction.InteractionImpl || (interaction.InteractionImpl = {}));
        })(interaction = malai.interaction || (malai.interaction = {}));
    })(malai = org.malai || (org.malai = {}));
})(org || (org = {}));
var org;
(function (org) {
    var malai;
    (function (malai) {
        var interaction;
        (function (interaction) {
            class IntermediaryState extends org.malai.interaction.StateImpl {
                constructor(name) {
                    super(name);
                }
                onIngoing() {
                    this.stateMachine.onUpdating();
                }
                onOutgoing() {
                }
            }
            interaction.IntermediaryState = IntermediaryState;
            IntermediaryState["__class"] = "org.malai.interaction.IntermediaryState";
            IntermediaryState["__interfaces"] = ["org.malai.stateMachine.SourceableState", "org.malai.stateMachine.TargetableState", "org.malai.stateMachine.State"];
        })(interaction = malai.interaction || (malai.interaction = {}));
    })(malai = org.malai || (org.malai = {}));
})(org || (org = {}));
var org;
(function (org) {
    var malai;
    (function (malai) {
        var interaction;
        (function (interaction) {
            class TerminalState extends org.malai.interaction.StateImpl {
                constructor(name) {
                    super(name);
                }
                onIngoing() {
                    this.stateMachine.onTerminating();
                }
            }
            interaction.TerminalState = TerminalState;
            TerminalState["__class"] = "org.malai.interaction.TerminalState";
            TerminalState["__interfaces"] = ["org.malai.stateMachine.TargetableState", "org.malai.stateMachine.State"];
        })(interaction = malai.interaction || (malai.interaction = {}));
    })(malai = org.malai || (org.malai = {}));
})(org || (org = {}));
var org;
(function (org) {
    var malai;
    (function (malai) {
        var interaction;
        (function (interaction) {
            class TransitionImpl {
                constructor(inputState, outputState) {
                    if (this.inputState === undefined)
                        this.inputState = null;
                    if (this.outputState === undefined)
                        this.outputState = null;
                    if (this.hid === undefined)
                        this.hid = 0;
                    if (inputState == null || outputState == null)
                        throw Object.defineProperty(new Error(), '__classes', { configurable: true, value: ['java.lang.Throwable', 'java.lang.Object', 'java.lang.RuntimeException', 'java.lang.IllegalArgumentException', 'java.lang.Exception'] });
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
                toString() {
                    return (c => c["__class"] ? c["__class"].substring(c["__class"].lastIndexOf('.') + 1) : c["name"].substring(c["name"].lastIndexOf('.') + 1))(this.constructor) + '[' + this.inputState.getName() + ">>" + this.outputState.getName() + ']';
                }
                getHid() {
                    return this.hid;
                }
                setHid(hid) {
                    this.hid = hid;
                }
            }
            interaction.TransitionImpl = TransitionImpl;
            TransitionImpl["__class"] = "org.malai.interaction.TransitionImpl";
            TransitionImpl["__interfaces"] = ["org.malai.stateMachine.Transition"];
        })(interaction = malai.interaction || (malai.interaction = {}));
    })(malai = org.malai || (org.malai = {}));
})(org || (org = {}));
var org;
(function (org) {
    var malai;
    (function (malai) {
        var interaction;
        (function (interaction) {
            class TimeoutTransition extends org.malai.interaction.TransitionImpl {
                constructor(inputState, outputState, timeout) {
                    super(inputState, outputState);
                    if (this.timeout === undefined)
                        this.timeout = null;
                    if (timeout == null) {
                        throw Object.defineProperty(new Error(), '__classes', { configurable: true, value: ['java.lang.Throwable', 'java.lang.Object', 'java.lang.RuntimeException', 'java.lang.IllegalArgumentException', 'java.lang.Exception'] });
                    }
                    this.timeout = (timeout);
                }
                startTimeout() {
                }
                stopTimeout() {
                }
                getEventType() {
                    return null;
                }
            }
            interaction.TimeoutTransition = TimeoutTransition;
            TimeoutTransition["__class"] = "org.malai.interaction.TimeoutTransition";
            TimeoutTransition["__interfaces"] = ["org.malai.stateMachine.Transition"];
        })(interaction = malai.interaction || (malai.interaction = {}));
    })(malai = org.malai || (org.malai = {}));
})(org || (org = {}));
var org;
(function (org) {
    var malai;
    (function (malai) {
        var interaction;
        (function (interaction) {
            class WidgetTransition extends org.malai.interaction.TransitionImpl {
                constructor(inputState, outputState) {
                    super(inputState, outputState);
                    if (this.widget === undefined)
                        this.widget = null;
                }
                getWidget() {
                    return this.widget;
                }
                setWidget(widget) {
                    if (widget != null) {
                        this.widget = widget;
                    }
                }
            }
            interaction.WidgetTransition = WidgetTransition;
            WidgetTransition["__class"] = "org.malai.interaction.WidgetTransition";
            WidgetTransition["__interfaces"] = ["org.malai.stateMachine.Transition"];
        })(interaction = malai.interaction || (malai.interaction = {}));
    })(malai = org.malai || (org.malai = {}));
})(org || (org = {}));
var org;
(function (org) {
    var malai;
    (function (malai) {
        var interaction2;
        (function (interaction2) {
            class Interaction {
                constructor(fsm) {
                    if (this.fsm === undefined)
                        this.fsm = null;
                    if (this.activated === undefined)
                        this.activated = false;
                    if (fsm == null) {
                        throw Object.defineProperty(new Error("null fsm"), '__classes', { configurable: true, value: ['java.lang.Throwable', 'java.lang.Object', 'java.lang.RuntimeException', 'java.lang.IllegalArgumentException', 'java.lang.Exception'] });
                    }
                    this.fsm = fsm;
                    fsm.currentStateProp().obs({ onChange: (oldValue, newValue) => this.updateEventsRegistered(newValue, oldValue) });
                    this.activated = true;
                }
                isRunning() {
                    return this.activated && !(this.fsm.getCurrentState() != null && this.fsm.getCurrentState() instanceof org.malai.fsm.InitState);
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
                    this.fsm.log(log);
                }
                isActivated() {
                    return this.activated;
                }
                setActivated(activated) {
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
            }
            interaction2.Interaction = Interaction;
            Interaction["__class"] = "org.malai.interaction2.Interaction";
        })(interaction2 = malai.interaction2 || (malai.interaction2 = {}));
    })(malai = org.malai || (org.malai = {}));
})(org || (org = {}));
var org;
(function (org) {
    var malai;
    (function (malai) {
        var logging;
        (function (logging) {
            var LogLevel;
            (function (LogLevel) {
                LogLevel[LogLevel["INTERACTION"] = 0] = "INTERACTION";
                LogLevel[LogLevel["BINDING"] = 1] = "BINDING";
                LogLevel[LogLevel["ACTION"] = 2] = "ACTION";
            })(LogLevel = logging.LogLevel || (logging.LogLevel = {}));
        })(logging = malai.logging || (malai.logging = {}));
    })(malai = org.malai || (org.malai = {}));
})(org || (org = {}));
var org;
(function (org) {
    var malai;
    (function (malai) {
        var stateMachine;
        (function (stateMachine) {
            class MustCancelStateMachineException extends Error {
                constructor() {
                    super();
                    Object.setPrototypeOf(this, MustCancelStateMachineException.prototype);
                }
            }
            stateMachine.MustCancelStateMachineException = MustCancelStateMachineException;
            MustCancelStateMachineException["__class"] = "org.malai.stateMachine.MustCancelStateMachineException";
            MustCancelStateMachineException["__interfaces"] = ["java.io.Serializable"];
        })(stateMachine = malai.stateMachine || (malai.stateMachine = {}));
    })(malai = org.malai || (org.malai = {}));
})(org || (org = {}));
var org;
(function (org) {
    var malai;
    (function (malai) {
        var undo;
        (function (undo) {
            class EmptyUndoHandler {
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
            }
            undo.EmptyUndoHandler = EmptyUndoHandler;
            EmptyUndoHandler["__class"] = "org.malai.undo.EmptyUndoHandler";
            EmptyUndoHandler["__interfaces"] = ["org.malai.undo.UndoHandler"];
        })(undo = malai.undo || (malai.undo = {}));
    })(malai = org.malai || (org.malai = {}));
})(org || (org = {}));
var org;
(function (org) {
    var malai;
    (function (malai) {
        var undo;
        (function (undo) {
            class UndoCollector {
                constructor() {
                    if (this.undoHandlers === undefined)
                        this.undoHandlers = null;
                    if (this.redoHandlers === undefined)
                        this.redoHandlers = null;
                    if (this.__undo === undefined)
                        this.__undo = null;
                    if (this.__redo === undefined)
                        this.__redo = null;
                    if (this.sizeMax === undefined)
                        this.sizeMax = 0;
                    if (this.handlers === undefined)
                        this.handlers = null;
                    this.handlers = ([]);
                    this.__undo = [];
                    this.__redo = [];
                    this.undoHandlers = [];
                    this.redoHandlers = [];
                    this.sizeMax = 30;
                }
                static INSTANCE_$LI$() { if (UndoCollector.INSTANCE == null)
                    UndoCollector.INSTANCE = new UndoCollector(); return UndoCollector.INSTANCE; }
                ;
                static STUB_UNDO_HANDLER_$LI$() { if (UndoCollector.STUB_UNDO_HANDLER == null)
                    UndoCollector.STUB_UNDO_HANDLER = new org.malai.undo.EmptyUndoHandler(); return UndoCollector.STUB_UNDO_HANDLER; }
                ;
                addHandler(handler) {
                    if (handler != null) {
                        (this.handlers.push(handler) > 0);
                    }
                }
                removeHandler(handler) {
                    if (handler != null) {
                        (a => { let index = a.indexOf(handler); if (index >= 0) {
                            a.splice(index, 1);
                            return true;
                        }
                        else {
                            return false;
                        } })(this.handlers);
                    }
                }
                getHandlers() {
                    return this.handlers.slice(0);
                }
                clearHandlers() {
                    (this.handlers.length = 0);
                }
                clear() {
                    (this.__undo.length = 0);
                    (this.__redo.length = 0);
                    (this.undoHandlers.length = 0);
                    (this.redoHandlers.length = 0);
                    for (let index121 = 0; index121 < this.handlers.length; index121++) {
                        let h = this.handlers[index121];
                        {
                            h.onUndoableCleared();
                        }
                    }
                }
                add(undoable, undoHandler) {
                }
                undo() {
                    if (!(this.__undo.length == 0)) {
                        let undoable = this.__undo.pop();
                        let undoHandler = this.undoHandlers.pop();
                        undoable.undo();
                        (this.__redo.push(undoable) > 0);
                        (this.redoHandlers.push(undoHandler) > 0);
                        undoHandler.onUndoableUndo(undoable);
                        for (let index123 = 0; index123 < this.handlers.length; index123++) {
                            let handler = this.handlers[index123];
                            {
                                handler.onUndoableUndo(undoable);
                            }
                        }
                    }
                }
                redo() {
                    if (!(this.__redo.length == 0)) {
                        let undoable = this.__redo.pop();
                        let redoHandler = this.redoHandlers.pop();
                        undoable.redo();
                        (this.__undo.push(undoable) > 0);
                        (this.undoHandlers.push(redoHandler) > 0);
                        redoHandler.onUndoableRedo(undoable);
                        for (let index124 = 0; index124 < this.handlers.length; index124++) {
                            let handler = this.handlers[index124];
                            {
                                handler.onUndoableRedo(undoable);
                            }
                        }
                    }
                }
                getLastUndoMessage() {
                    return (this.__undo.length == 0) ? util.Optional.empty() : util.Optional.ofNullable(((s) => { return s[s.length - 1]; })(this.__undo).getUndoName());
                }
                getLastRedoMessage() {
                    return (this.__redo.length == 0) ? util.Optional.empty() : util.Optional.ofNullable(((s) => { return s[s.length - 1]; })(this.__redo).getUndoName());
                }
                getLastUndo() {
                    return (this.__undo.length == 0) ? util.Optional.empty() : util.Optional.ofNullable(((s) => { return s[s.length - 1]; })(this.__undo));
                }
                getLastRedo() {
                    return (this.__redo.length == 0) ? util.Optional.empty() : util.Optional.ofNullable(((s) => { return s[s.length - 1]; })(this.__redo));
                }
                getSizeMax() {
                    return this.sizeMax;
                }
                setSizeMax(max) {
                }
                getUndo() {
                    return this.__undo;
                }
                getRedo() {
                    return this.__redo;
                }
            }
            UndoCollector.EMPTY_REDO = "redo";
            UndoCollector.EMPTY_UNDO = "undo";
            undo.UndoCollector = UndoCollector;
            UndoCollector["__class"] = "org.malai.undo.UndoCollector";
        })(undo = malai.undo || (malai.undo = {}));
    })(malai = org.malai || (org.malai = {}));
})(org || (org = {}));
org.malai.undo.UndoCollector.STUB_UNDO_HANDLER_$LI$();
org.malai.undo.UndoCollector.INSTANCE_$LI$();
var org;
(function (org) {
    var malai;
    (function (malai) {
        var utils;
        (function (utils) {
            class ObsValue {
                constructor(value) {
                    if (((value != null) || value === null)) {
                        let __args = Array.prototype.slice.call(arguments);
                        if (this.value === undefined)
                            this.value = null;
                        if (this.handlers === undefined)
                            this.handlers = null;
                        if (this.value === undefined)
                            this.value = null;
                        if (this.handlers === undefined)
                            this.handlers = null;
                        (() => {
                            this.value = value;
                            this.handlers = ([]);
                        })();
                    }
                    else if (value === undefined) {
                        let __args = Array.prototype.slice.call(arguments);
                        {
                            let __args = Array.prototype.slice.call(arguments);
                            let value = null;
                            if (this.value === undefined)
                                this.value = null;
                            if (this.handlers === undefined)
                                this.handlers = null;
                            if (this.value === undefined)
                                this.value = null;
                            if (this.handlers === undefined)
                                this.handlers = null;
                            (() => {
                                this.value = value;
                                this.handlers = ([]);
                            })();
                        }
                    }
                    else
                        throw new Error('invalid overload');
                }
                get() {
                    return this.value;
                }
                set(value) {
                    let oldValue = this.value;
                    this.value = value;
                    this.notifyChange(oldValue, value);
                }
                notifyChange(oldValue, newValue) {
                    this.handlers.forEach((handler) => handler.onChange(oldValue, newValue));
                }
                obs(handler) {
                    if (handler != null) {
                        ((s, e) => { if (s.indexOf(e) == -1) {
                            s.push(e);
                            return true;
                        }
                        else {
                            return false;
                        } })(this.handlers, handler);
                    }
                }
                unobs(handler) {
                    if (handler != null) {
                        (a => { let index = a.indexOf(handler); if (index >= 0) {
                            a.splice(index, 1);
                            return true;
                        }
                        else {
                            return false;
                        } })(this.handlers);
                    }
                }
            }
            utils.ObsValue = ObsValue;
            ObsValue["__class"] = "org.malai.utils.ObsValue";
        })(utils = malai.utils || (malai.utils = {}));
    })(malai = org.malai || (org.malai = {}));
})(org || (org = {}));
