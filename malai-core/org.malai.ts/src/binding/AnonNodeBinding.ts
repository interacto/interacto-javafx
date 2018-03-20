/*
 * This file is part of Malai.
 * Copyright (c) 2009-2018 Arnaud BLOUIN
 * Malai is free software; you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation; either version 2 of the License, or (at your option) any later version.
 * Malai is distributed without any warranty; without even the implied
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * General Public License for more details.
 */

/// <reference path="../../src-core/action/ActionImpl.ts" />
/// <reference path="../../src-core/logging/LogLevel.ts" />
/// <reference path="../interaction/TSInteraction.ts" />
/// <reference path="./TSWidgetBinding.ts" />

namespace malai {
    export class AnonNodeBinding<A extends ActionImpl, I extends TSInteraction<any, any>> extends TSWidgetBinding<A, I> {
        private readonly execInitAction: (A, I) => {};
        private readonly execUpdateAction: (A, I) => {};
        private readonly checkInteraction: (I) => boolean;
        private readonly actionProducer: (I) => A;
        private readonly cancelFct: (A, I) => {};
        private readonly endOrCancelFct: (A, I) => {};
        private readonly feedbackFct: () => {};
        private readonly onEnd: (A, I) => {};
        private readonly strictStart: boolean;
        /** Used rather than 'action' to catch the action during its creation.
         * Sometimes (eg onInteractionStops) can create the action, execute it, and forget it.
         */
        protected currentAction: A;

        /**
         * Creates a widget binding. This constructor must initialise the interaction. The binding is (de-)activated if the given
         * instrument is (de-)activated.
         * @param exec Specifies if the action must be execute or update on each evolution of the interaction.
         * @param clazzAction The type of the action that will be created. Used to instantiate the action by reflexivity.
         * The class must be public and must have a constructor with no parameter.
         * @param interaction The user interaction of the binding.
         * @param widgets The widgets used by the binding. Cannot be null.
         * @param initActionFct The function that initialises the action to execute. Cannot be null.
         * @param updateActionFct The function that updates the action. Can be null.
         * @throws IllegalArgumentException If the given interaction or instrument is null.
         */
        public constructor(exec: boolean, clazzAction: () => A, interaction: I, initActionFct: (A, I) => {},
                           updateActionFct: (A, I) => {}, check: (I) => boolean, onEndFct: (A, I) => {}, actionFunction: (I) => A,
                           cancel: (A, I) => {}, endOrCancel: (A, I) => {}, feedback: () => {}, widgets: Array<EventTarget>,
                           // List<ObservableList<? extends Node>> additionalWidgets, HelpAnimation animation, help : boolean
                           asyncExec: boolean, strict: boolean, loggers: Array<LogLevel>) {
            super(exec, clazzAction, interaction, widgets);
            this.execInitAction = initActionFct;
            this.execUpdateAction = updateActionFct;
            this.cancelFct = cancel;
            this.endOrCancelFct = endOrCancel;
            this.feedbackFct = feedback;
            this.actionProducer = actionFunction;
            this.checkInteraction = check == null ? () => true : check;
            this.async = asyncExec;
            this.onEnd = onEndFct;
            this.strictStart = strict;
            this.currentAction = null;
            this.configureLoggers(loggers);

            // if(additionalWidgets != null) {
            // additionalWidgets.stream().filter(Objects::nonNull).forEach(elt -> interaction.registerToObservableNodeList(elt));
        }

        private configureLoggers(loggers: Array<LogLevel>): any {
            if (loggers !== undefined) {
                this.logAction(loggers.indexOf(LogLevel.ACTION) >= 0);
                this.logBinding(loggers.indexOf(LogLevel.BINDING) >= 0);
                this.interaction.log(loggers.indexOf(LogLevel.INTERACTION) >= 0);
            }
        }

        public isStrictStart() : boolean {
            return this.strictStart;
        }

        protected map() : A {
            if(this.actionProducer === undefined) {
                this.currentAction = super.map();
            }else {
                this.currentAction = this.actionProducer(this.getInteraction());
            }
            return this.currentAction;
        }

        public first() : any {
            if(this.execInitAction !== undefined && this.currentAction !== undefined) {
                this.execInitAction(this.getAction(), this.getInteraction());
            }
        }

        public then() : any {
            if(this.execUpdateAction !== undefined) {
                this.execUpdateAction(this.getAction(), this.getInteraction());
            }
        }

        public when() : boolean {
            // if(loggerBinding != null) {
            //     loggerBinding.log(Level.INFO, "Checking condition: " + ok);
            // }
            return this.checkInteraction === undefined || this.checkInteraction(this.getInteraction());
        }

        public fsmCancels() :any {
            if(this.endOrCancelFct !== undefined && this.currentAction !== undefined) {
                this.endOrCancelFct(this.action, this.interaction);
            }
            if(this.cancelFct !== undefined && this.currentAction !== undefined) {
                this.cancelFct(this.action, this.interaction);
            }
            super.fsmCancels();
            this.currentAction = null;
        }

        public feedback() : any {
            if(this.feedbackFct !== undefined) {
                // if(loggerBinding != null) {
                //     loggerBinding.log(Level.INFO, "Feedback");
                // }
                this.feedbackFct();
            }
        }

        public fsmStops() : any {
            super.fsmStops();
            if(this.endOrCancelFct !== undefined && this.currentAction !== undefined) {
                this.endOrCancelFct(this.currentAction, this.getInteraction());
            }
            if(this.onEnd !== undefined && this.currentAction !== undefined) {
                this.onEnd(this.currentAction, this.getInteraction());
            }
            this.currentAction = null;
        }
    }
}