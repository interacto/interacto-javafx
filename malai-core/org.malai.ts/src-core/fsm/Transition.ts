/* Generated from Java with JSweet 2.0.1 - http://www.jsweet.org */
namespace malai {
    export abstract class Transition<E> {
        src : OutputState<E>;

        tgt : InputState<E>;

        constructor(srcState : OutputState<E>, tgtState : InputState<E>) {
            if(this.src===undefined) this.src = null;
            if(this.tgt===undefined) this.tgt = null;
            if(srcState == null || tgtState == null) {
                throw Object.defineProperty(new Error("States cannot be null"), '__classes', { configurable: true, value: ['java.lang.Throwable','java.lang.Object','java.lang.RuntimeException','java.lang.IllegalArgumentException','java.lang.Exception'] });
            }
            this.src = srcState;
            this.tgt = tgtState;
            this.src.addTransition(this);
        }

        public execute(event : E) : Optional<InputState<E>> {
            if(this.accept(event) && this.isGuardOK(event)) {
                this.src.getFSM().stopCurrentTimeout();
                this.action(event);
                this.src.exit();
                this.tgt.enter();
                return Optional.of<any>(this.tgt);
            }
            return Optional.empty<any>();
        }

        action(event : E) {
        }

        abstract accept(event : E) : boolean;

        abstract isGuardOK(event : E) : boolean;

        public abstract getAcceptedEvents() : Set<String>;
    }
    Transition["__class"] = "malai.Transition";

}

