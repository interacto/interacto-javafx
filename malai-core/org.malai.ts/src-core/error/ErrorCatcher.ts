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

import {ErrorNotifier} from "./ErrorNotifier";

/**
 * The singleton ErrorCatcher collects errors.
 * The ErrorCatcher sends the gathered exception to an ErrorNotifier (if one is defined).
 * @author Arnaud BLOUIN
 * @since 0.2
 */
export class ErrorCatcher {
    /**
     * The singleton.
     */
    public static INSTANCE: ErrorCatcher = new ErrorCatcher();

    /**
     * The notifier object.
     */
    private notifier: ErrorNotifier | undefined;

    private constructor() {
    }

    /**
     * Sets the notifier that will be notified about the collected exceptions.
     * @param {*} newNotifier The notifier that will be notified the collected exceptions. Can be null.
     * @since 0.2
     */
    public setNotifier(newNotifier: ErrorNotifier): void {
        this.notifier = newNotifier;
    }

    /**
     * @return {*} The notifier that is notified about the collected exceptions.
     * @since 0.2
     */
    public getErrorNotifier(): ErrorNotifier | undefined {
        return this.notifier;
    }

    /**
     * Gathers exceptions. The notifier is then notified of the exceptions (if defined).
     * @param {Error} exception The errors to gather.
     * @since 0.1
     */
    public reportError(exception: Error): void {
        if (this.notifier !== undefined) {
            this.notifier.onException(exception);
        }
    }
}
