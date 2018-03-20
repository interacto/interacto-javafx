/* Generated from Java with JSweet 2.0.1 - http://www.jsweet.org */

/// <reference path="../../src/util/Optional.ts" />

namespace malai {
    export class SubFSMTransition<E> extends Transition<E> {
        /*private*/ subFSM : FSM<E>;

        /*private*/ subFSMHandler : FSMHandler;

        public constructor(srcState : OutputState<E>, tgtState : InputState<E>, fsm : FSM<E>) {
            super(srcState, tgtState);
            if(this.subFSM===undefined) this.subFSM = null;
            if(this.subFSMHandler===undefined) this.subFSMHandler = null;
            if(fsm == null) {
                throw Object.defineProperty(new Error("sub fsm cannot be null"), '__classes', { configurable: true, value: ['java.lang.Throwable','java.lang.Object','java.lang.RuntimeException','java.lang.IllegalArgumentException','java.lang.Exception'] });
            }
            this.subFSM = fsm;
            this.subFSM.setInner(true);
            this.subFSMHandler = new SubFSMTransition$0(this);
        }

        /**
         * 
         * @param {*} event
         * @return {java.Optional}
         */
        public execute(event : E) : Optional<InputState<E>> {
            if(this.isGuardOK(event)) {
                this.src.getFSM().stopCurrentTimeout();
                let transition : Optional<Transition<E>> = this.findTransition(event);
                if(transition.isPresent()) {
                    this.subFSM.addHandler(this.subFSMHandler);
                    this.src.getFSM().currentSubFSM = this.subFSM;
                    this.subFSM.process(event);
                    return Optional.of(transition.get().tgt);
                }
            }
            return Optional.empty<any>();
        }

        /**
         * 
         * @param {*} event
         * @return {boolean}
         */
        accept(event : E) : boolean {
            return this.findTransition(event).isPresent();
        }

        /**
         * 
         * @param {*} event
         * @return {boolean}
         */
        isGuardOK(event : E) : boolean {
            return this.findTransition(event).filter((tr) => tr.isGuardOK(event)).isPresent();
        }

        /*private*/ findTransition(event : E) : Optional<Transition<E>> {
            return Optional.of(this.subFSM.initState.transitions.find((tr) => tr.accept(event)));
        }

        /**
         * 
         * @return {*[]}
         */
        public getAcceptedEvents() : Set<String> {
            return this.subFSM.initState.getTransitions().map(tr => tr.getAcceptedEvents()).reduce((a, b) => new Set([...a, ...b]));
        }
    }
    SubFSMTransition["__class"] = "malai.SubFSMTransition";


    export class SubFSMTransition$0 implements FSMHandler {
        public __parent: any;
        /**
         *
         */
        public fsmStarts() {
            this.__parent.src.exit();
        }

        /**
         *
         */
        public fsmUpdates() {
            this.__parent.src.getFSM().setCurrentState(this.__parent.subFSM.getCurrentState());
            this.__parent.src.getFSM().onUpdating();
        }

        /**
         *
         */
        public fsmStops() {
            this.__parent.action(null);
            this.__parent.subFSM.removeHandler(this.__parent.subFSMHandler);
            this.__parent.src.getFSM().currentSubFSM = null;
            if(this.__parent.tgt != null && this.__parent.tgt instanceof <any>TerminalState) {
                this.__parent.tgt.enter();
                return;
            }
            if(this.__parent.tgt != null && this.__parent.tgt instanceof <any>CancellingState) {
                this.fsmCancels();
                return;
            }
            if(this.__parent.tgt != null && (this.__parent.tgt["__interfaces"] != null && this.__parent.tgt["__interfaces"].indexOf("OutputState") >= 0 || this.__parent.tgt.constructor != null && this.__parent.tgt.constructor["__interfaces"] != null && this.__parent.tgt.constructor["__interfaces"].indexOf("OutputState") >= 0)) {
                this.__parent.src.getFSM().setCurrentState(<OutputState<any>><any>this.__parent.tgt);
                this.__parent.tgt.enter();
            }
        }

        /**
         *
         */
        public fsmCancels() {
            this.__parent.subFSM.removeHandler(this.__parent.subFSMHandler);
            this.__parent.src.getFSM().currentSubFSM = null;
            this.__parent.src.getFSM().onCancelling();
        }

        constructor(__parent: any) {
            this.__parent = __parent;
        }
    }
    SubFSMTransition$0["__interfaces"] = ["FSMHandler"];
}

