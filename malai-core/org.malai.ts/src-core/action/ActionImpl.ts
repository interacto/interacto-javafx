/* Generated from Java with JSweet 2.0.1 - http://www.jsweet.org */
namespace malai {
    /**
     * The default constructor.
     * Initialises the current status to created.
     * @class
     * @author Arnaud BLOUIN
     */
    export abstract class ActionImpl implements Action {
        /**
         * The state of the action.
         */
        status : ActionStatus;

        public constructor() {
            if(this.status===undefined) this.status = null;
            this.status = ActionStatus.CREATED;
        }

        /**
         * 
         */
        public flush() {
            this.status = ActionStatus.FLUSHED;
        }

        /**
         * Actions may need to create a memento before their first execution.
         * This is the goal of the operation that should be overriden.
         * This operator is called a single time before the first execution of the action.
         */
        createMemento() {
        }

        /**
         * 
         * @return {boolean}
         */
        public doIt() : boolean {
            let ok : boolean;
            if((this.status === ActionStatus.CREATED || this.status === ActionStatus.EXECUTED) && this.canDo()) {
                if(this.status === ActionStatus.CREATED) {
                    this.createMemento();
                }
                ok = true;
                this.doActionBody();
                this.status = ActionStatus.EXECUTED;
                ActionsRegistry.INSTANCE_$LI$().onActionExecuted(this);
            } else {
                ok = false;
            }
            return ok;
        }

        /**
         * This method contains the statements to execute the action.
         * This method is automatically called by DoIt and must not be called explicitly.
         * @since 0.1
         */
        abstract doActionBody();

        /**
         * 
         * @return {Action.RegistrationPolicy}
         */
        public getRegistrationPolicy() : RegistrationPolicy {
            return this.hadEffect()?RegistrationPolicy.LIMITED:RegistrationPolicy.NONE;
        }

        /**
         * 
         * @return {boolean}
         */
        public hadEffect() : boolean {
            return this.isDone();
        }

        /**
         * 
         * @param {*} action
         * @return {boolean}
         */
        public unregisteredBy(action : Action) : boolean {
            return false;
        }

        /**
         * 
         */
        public done() {
            if(this.status === ActionStatus.CREATED || this.status === ActionStatus.EXECUTED) {
                this.status = ActionStatus.DONE;
                ActionsRegistry.INSTANCE_$LI$().onActionDone(this);
            }
        }

        /**
         * 
         * @return {boolean}
         */
        public isDone() : boolean {
            return this.status === ActionStatus.DONE;
        }

        /**
         * 
         * @return {string}
         */
        public toString() : string {
            return /* getSimpleName */(c => c["__class"]?c["__class"].substring(c["__class"].lastIndexOf('.')+1):c["name"].substring(c["name"].lastIndexOf('.')+1))((<any>this.constructor));
        }

        /**
         * 
         */
        public cancel() {
            this.status = ActionStatus.CANCELLED;
        }

        /**
         * 
         * @return {Action.ActionStatus}
         */
        public getStatus() : ActionStatus {
            return this.status;
        }

        /**
         * 
         * @return {*[]}
         */
        public followingActions() : Array<Action> {
            return /* emptyList */[];
        }

        public abstract canDo(): any;    }
    ActionImpl["__class"] = "malai.ActionImpl";
    ActionImpl["__interfaces"] = ["malai.Action"];


}

