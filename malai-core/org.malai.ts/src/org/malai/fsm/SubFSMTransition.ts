/* Generated from Java with JSweet 2.0.1 - http://www.jsweet.org */

/// <reference path="../../../util/Optional.ts" />
/// <reference path="../../../util/ArrayUtils.ts" />

namespace org.malai.fsm {
    import Optional = util.Optional;

    export class SubFSMTransition<E> extends org.malai.fsm.Transition<E> {
        /*private*/ subFSM : org.malai.fsm.FSM<E>;

        /*private*/ subFSMHandler : org.malai.fsm.FSMHandler;

        public constructor(srcState : org.malai.fsm.OutputState<E>, tgtState : org.malai.fsm.InputState<E>, fsm : org.malai.fsm.FSM<E>) {
            super(srcState, tgtState);
            if(this.subFSM===undefined) this.subFSM = null;
            if(this.subFSMHandler===undefined) this.subFSMHandler = null;
            if(fsm == null) {
                throw Object.defineProperty(new Error("sub fsm cannot be null"), '__classes', { configurable: true, value: ['java.lang.Throwable','java.lang.Object','java.lang.RuntimeException','java.lang.IllegalArgumentException','java.lang.Exception'] });
            }
            this.subFSM = fsm;
            this.subFSM.setInner(true);
            this.subFSMHandler = new SubFSMTransition.SubFSMTransition$0(this);
        }

        /**
         * 
         * @param {*} event
         * @return {java.util.Optional}
         */
        public execute(event : E) : util.Optional<org.malai.fsm.InputState<E>> {
            if(this.isGuardOK(event)) {
                this.src.getFSM().stopCurrentTimeout();
                let transition : util.Optional<org.malai.fsm.Transition<E>> = this.findTransition(event);
                if(transition.isPresent()) {
                    this.subFSM.addHandler(this.subFSMHandler);
                    this.src.getFSM().currentSubFSM = this.subFSM;
                    this.subFSM.process(event);
                    return util.Optional.of<any>(transition.get().tgt);
                }
            }
            return util.Optional.empty<any>();
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

        /*private*/ findTransition(event : E) : util.Optional<org.malai.fsm.Transition<E>> {
            return Optional.of(this.subFSM.initState.transitions.find((tr) => tr.accept(event)));
        }

        /**
         * 
         * @return {*[]}
         */
        public getAcceptedEvents() : Array<any> {
            //FIXME unique / distinct
            return <any>(this.subFSM.initState.getTransitions().map<any>((tr) => tr.getAcceptedEvents()).
                flatMap<any>((s) => s.stream()))
        }
    }
    SubFSMTransition["__class"] = "org.malai.fsm.SubFSMTransition";


    export namespace SubFSMTransition {

        export class SubFSMTransition$0 implements org.malai.fsm.FSMHandler {
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
                if(this.__parent.tgt != null && this.__parent.tgt instanceof <any>org.malai.fsm.TerminalState) {
                    this.__parent.tgt.enter();
                    return;
                }
                if(this.__parent.tgt != null && this.__parent.tgt instanceof <any>org.malai.fsm.CancellingState) {
                    this.fsmCancels();
                    return;
                }
                if(this.__parent.tgt != null && (this.__parent.tgt["__interfaces"] != null && this.__parent.tgt["__interfaces"].indexOf("org.malai.fsm.OutputState") >= 0 || this.__parent.tgt.constructor != null && this.__parent.tgt.constructor["__interfaces"] != null && this.__parent.tgt.constructor["__interfaces"].indexOf("org.malai.fsm.OutputState") >= 0)) {
                    this.__parent.src.getFSM().setCurrentState(<org.malai.fsm.OutputState<any>><any>this.__parent.tgt);
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
        SubFSMTransition$0["__interfaces"] = ["org.malai.fsm.FSMHandler"];


    }

}

