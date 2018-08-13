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

/**
 * This interface must be used by objects that want to be notified about the exceptions collected by the ErrorCatcher.
 * @author Arnaud BLOUIN
 * @since 0.2
 * @class
 */
export interface ErrorNotifier {
    /**
     * Notifies that an exception has been thrown.
     * @param {Error} exception The thrown exception.
     * @since 0.2
     */
    onException(exception: Error): void;
}

