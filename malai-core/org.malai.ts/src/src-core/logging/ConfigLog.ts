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

import {LoggerFactoryOptions, LFService, LogGroupRule, LogLevel, LoggerFactory} from "typescript-logging";

const options = new LoggerFactoryOptions()
    .addLogGroupRule(new LogGroupRule(new RegExp("model.+"), LogLevel.Debug))
    .addLogGroupRule(new LogGroupRule(new RegExp(".+"), LogLevel.Info));

export const factory: LoggerFactory = LFService.createNamedLoggerFactory("LoggerFactory", options);
