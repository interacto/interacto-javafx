/* Generated from Java with JSweet 2.0.1 - http://www.jsweet.org */
namespace org.malai.action {
    /**
     * The default constructor.
     * Initialises the current status to created.
     * @class
     * @author Arnaud BLOUIN
     */
    export abstract class ActionImpl implements org.malai.action.Action {
        /**
         * The state of the action.
         */
        status : Action.ActionStatus;

        public constructor() {
            if(this.status===undefined) this.status = null;
            this.status = Action.ActionStatus.CREATED;
        }

        /**
         * 
         */
        public flush() {
            this.status = Action.ActionStatus.FLUSHED;
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
            if((this.status === Action.ActionStatus.CREATED || this.status === Action.ActionStatus.EXECUTED) && this.canDo()) {
                if(this.status === Action.ActionStatus.CREATED) {
                    this.createMemento();
                }
                ok = true;
                this.doActionBody();
                this.status = Action.ActionStatus.EXECUTED;
                org.malai.action.ActionsRegistry.INSTANCE_$LI$().onActionExecuted(this);
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
         * @return {org.malai.action.Action.RegistrationPolicy}
         */
        public getRegistrationPolicy() : Action.RegistrationPolicy {
            return this.hadEffect()?Action.RegistrationPolicy.LIMITED:Action.RegistrationPolicy.NONE;
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
        public unregisteredBy(action : org.malai.action.Action) : boolean {
            return false;
        }

        /**
         * 
         */
        public done() {
            if(this.status === Action.ActionStatus.CREATED || this.status === Action.ActionStatus.EXECUTED) {
                this.status = Action.ActionStatus.DONE;
                org.malai.action.ActionsRegistry.INSTANCE_$LI$().onActionDone(this);
            }
        }

        /**
         * 
         * @return {boolean}
         */
        public isDone() : boolean {
            return this.status === Action.ActionStatus.DONE;
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
            this.status = Action.ActionStatus.CANCELLED;
        }

        /**
         * 
         * @return {org.malai.action.Action.ActionStatus}
         */
        public getStatus() : Action.ActionStatus {
            return this.status;
        }

        /**
         * 
         * @return {*[]}
         */
        public followingActions() : Array<org.malai.action.Action> {
            return /* emptyList */[];
        }

        public abstract canDo(): any;    }
    ActionImpl["__class"] = "org.malai.action.ActionImpl";
    ActionImpl["__interfaces"] = ["org.malai.action.Action"];


}

