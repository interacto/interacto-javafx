/* Generated from Java with JSweet 2.0.1 - http://www.jsweet.org */
namespace malai {
    /**
     * Creates and initialises the instrument.
     * @class
     * @author Arnaud BLOUIN
     */
    export abstract class InstrumentImpl<T extends WidgetBinding> implements Instrument<T> {
        /**
         * Defines whether the instrument is activated.
         */
        activated : boolean;

        /**
         * The widget bindings of the instrument.
         */
        bindings : Array<T>;

        /**
         * Defined whether the instrument has been modified.
         */
        modified : boolean;

        public constructor() {
            if(this.activated===undefined) this.activated = false;
            if(this.bindings===undefined) this.bindings = null;
            if(this.modified===undefined) this.modified = false;
            this.activated = false;
            this.modified = false;
            this.bindings = <any>([]);
        }

        /**
         * 
         * @return {number}
         */
        public getNbWidgetBindings() : number {
            return /* size */(<number>this.bindings.length);
        }

        /**
         * 
         * @return {boolean}
         */
        public hasWidgetBindings() : boolean {
            return this.getNbWidgetBindings() > 0;
        }

        /**
         * 
         * @return {*[]}
         */
        public getWidgetBindings() : Array<T> {
            return this.bindings;
        }

        /**
         * Initialises the bindings of the instrument.
         * @throws InstantiationException When a widget binding cannot instantiate its interaction.
         * @throws IllegalAccessException When a widget binding cannot instantiate its interaction.
         */
        abstract configureBindings();

        /**
         * Adds the given widget binding to the list of bindings of the instrument.
         * @param {*} binding The widget binding to add. If null, nothing is done.
         */
        public addBinding(binding : T) {
            if(binding != null) {
                /* add */(this.bindings.push(binding)>0);
                binding.setActivated(this.isActivated());
            }
        }

        /**
         * Removes the given widget binding from the list of bindings of the instrument.
         * @param {*} binding The widget binding to remove.
         * @return {boolean} True: the given widget binding has been removed. False otherwise.
         */
        public removeBinding(binding : T) : boolean {
            return binding != null && /* remove */(a => { let index = a.indexOf(binding); if(index>=0) { a.splice(index, 1); return true; } else { return false; }})(this.bindings);
        }

        /**
         * 
         */
        public clearEvents() {
            this.bindings.forEach((binding) => binding.clearEvents());
        }

        /**
         * 
         * @return {boolean}
         */
        public isActivated() : boolean {
            return this.activated;
        }

        /**
         * 
         * @param {boolean} toBeActivated
         */
        public setActivated(toBeActivated : boolean) {
            this.activated = toBeActivated;
            if(toBeActivated && !this.hasWidgetBindings()) {
                try {
                    this.configureBindings();
                } catch(ex) {
                    ErrorCatcher.INSTANCE_$LI$().reportError(ex);
                };
            } else {
                this.bindings.forEach((binding) => binding.setActivated(toBeActivated));
            }
            this.interimFeedback();
        }

        /**
         * 
         */
        public interimFeedback() {
        }

        // /**
        //  *
        //  * @param {boolean} generalPreferences
        //  * @param {string} nsURI
        //  * @param {*} document
        //  * @param {*} root
        //  */
        // public save(generalPreferences : boolean, nsURI : string, document : org.w3c.dom.Document, root : org.w3c.dom.Element) {
        // }
        //
        // /**
        //  *
        //  * @param {boolean} generalPreferences
        //  * @param {string} nsURI
        //  * @param {*} meta
        //  */
        // public load(generalPreferences : boolean, nsURI : string, meta : org.w3c.dom.Element) {
        // }

        /**
         * 
         * @return {boolean}
         */
        public isModified() : boolean {
            return this.modified;
        }

        /**
         * 
         * @param {boolean} isModified
         */
        public setModified(isModified : boolean) {
            this.modified = isModified;
        }

        /**
         * 
         */
        public reinit() {
        }

        /**
         * 
         */
        public onUndoableCleared() {
        }

        /**
         * 
         * @param {*} undoable
         */
        public onUndoableAdded(undoable : Undoable) {
        }

        /**
         * 
         * @param {*} undoable
         */
        public onUndoableUndo(undoable : Undoable) {
        }

        /**
         * 
         * @param {*} undoable
         */
        public onUndoableRedo(undoable : Undoable) {
        }

        /**
         * 
         * @param {*} action
         */
        public onActionAdded(action : Action) {
        }

        /**
         * 
         * @param {*} action
         */
        public onActionCancelled(action : Action) {
        }

        /**
         * 
         * @param {*} action
         */
        public onActionExecuted(action : Action) {
        }

        /**
         * 
         * @param {*} action
         */
        public onActionDone(action : Action) {
        }
    }
    InstrumentImpl["__class"] = "malai.InstrumentImpl";
    InstrumentImpl["__interfaces"] = ["malai.Instrument","malai.ActionHandler","malai.Preferenciable","malai.Reinitialisable","malai.UndoHandler","malai.Modifiable"];


}

