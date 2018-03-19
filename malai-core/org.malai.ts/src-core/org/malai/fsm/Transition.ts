/* Generated from Java with JSweet 2.0.1 - http://www.jsweet.org */
namespace org.malai.fsm {
    export abstract class Transition<E> {
        src : org.malai.fsm.OutputState<E>;

        tgt : org.malai.fsm.InputState<E>;

        constructor(srcState : org.malai.fsm.OutputState<E>, tgtState : org.malai.fsm.InputState<E>) {
            if(this.src===undefined) this.src = null;
            if(this.tgt===undefined) this.tgt = null;
            if(srcState == null || tgtState == null) {
                throw Object.defineProperty(new Error("States cannot be null"), '__classes', { configurable: true, value: ['java.lang.Throwable','java.lang.Object','java.lang.RuntimeException','java.lang.IllegalArgumentException','java.lang.Exception'] });
            }
            this.src = srcState;
            this.tgt = tgtState;
            this.src.addTransition(this);
        }

        public execute(event : E) : util.Optional<org.malai.fsm.InputState<E>> {
            if(this.accept(event) && this.isGuardOK(event)) {
                this.src.getFSM().stopCurrentTimeout();
                this.action(event);
                this.src.exit();
                this.tgt.enter();
                return util.Optional.of<any>(this.tgt);
            }
            return util.Optional.empty<any>();
        }

        action(event : E) {
        }

        abstract accept(event : E) : boolean;

        abstract isGuardOK(event : E) : boolean;

        public abstract getAcceptedEvents() : Array<any>;
    }
    Transition["__class"] = "org.malai.fsm.Transition";

}

