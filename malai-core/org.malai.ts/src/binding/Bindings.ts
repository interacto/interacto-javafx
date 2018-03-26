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

import {ActionImpl} from "../../src-core/action/ActionImpl";
import {ButtonBinder} from "./ButtonBinder";
import {TSInteraction} from "../interaction/TSInteraction";
import {FSM} from "../../src-core/fsm/FSM";
import {NodeBinder} from "./NodeBinder";
import {AnonActionBinder} from "./AnonActionBinder";

/**
 * Creates binding builder to build a binding between a given interaction and the given action type.
 * This builder is dedicated to bind node interactions to actions.
 * Do not forget to call bind() at the end of the build to execute the builder.
 * @param actionProducer The action to produce.
 * @param interaction The user interaction to perform on nodes
 * @return The binding builder. Cannot be null.
 * @throws NullPointerException If the given class is null.
 */
export function nodeBinder<A extends ActionImpl, I extends TSInteraction<FSM<Event>, {}>>(actionProducer: () => A,
                                                                                          interaction: I): NodeBinder<A, I> {
    return new NodeBinder(actionProducer, interaction);
}

/**
 * Creates binding builder to build a binding between a button interaction and the given action type.
 * Do not forget to call bind() at the end of the build to execute the builder.
 * @param actionProducer The action to produce.
 * @return The binding builder. Cannot be null.
 * @throws NullPointerException If the given class is null.
 */
export function buttonBinder<A extends ActionImpl>(actionProducer: () => A): ButtonBinder<A> {
    return new ButtonBinder<A>(actionProducer);
}

/**
 * Creates binding builder to build a binding between a KeysPressure interaction (done on a Node) and the given action type.
 * Do not forget to call bind() at the end of the build to execute the builder.
 * @param action The anonymous action to produce.
 * @return The binding builder. Cannot be null.
 * @throws NullPointerException If the given class is null.
 */
export function anonActionBinder<I extends TSInteraction<FSM<Event>, {}>>(action: () => void, interaction: I): AnonActionBinder<I> {
    return new AnonActionBinder(action, interaction);
}

// /**
//  * Creates binding builder to build a binding between a KeysPressure interaction (done on a Node) and the given action type.
//  * Do not forget to call bind() at the end of the build to execute the builder.
//  * @param actionClass The action to produce.
//  * @param <A> The type of the action.
//  * @return The binding builder. Cannot be null.
//  * @throws NullPointerException If the given class is null.
//  */
// protected <A extends ActionImpl> KeyNodeBinder<A> keyNodeBinder(Class<A> actionClass) {
//     return new KeyNodeBinder<>(actionClass, this);
// }
//
// /**
//  * Creates binding builder to build a binding between a KeysPressure interaction (done on a Window) and the given action type.
//  * Do not forget to call bind() at the end of the build to execute the builder.
//  * @param actionClass The action to produce.
//  * @param <A> The type of the action.
//  * @return The binding builder. Cannot be null.
//  * @throws NullPointerException If the given class is null.
//  */
// protected <A extends ActionImpl> KeyWindowBinder<A> keyWindowBinder(Class<A> actionClass) {
//     return new KeyWindowBinder<>(actionClass, this);
// }

// /**
//  * Creates binding builder to build a binding between a toggle button interaction and the given action type.
//  * Do not forget to call bind() at the end of the build to execute the builder.
//  * @param actionClass The action to produce.
//  * @param <A> The type of the action.
//  * @return The binding builder. Cannot be null.
//  * @throws NullPointerException If the given class is null.
//  */
// protected <A extends ActionImpl> ToggleButtonBinder<A> toggleButtonBinder(Class<A> actionClass) {
//     return new ToggleButtonBinder<>(actionClass, this);
// }
//
// /**
//  * Creates binding builder to build a binding between a checkbox interaction and the given action type.
//  * Do not forget to call bind() at the end of the build to execute the builder.
//  * @param actionClass The action to produce.
//  * @param <A> The type of the action.
//  * @return The binding builder. Cannot be null.
//  * @throws NullPointerException If the given class is null.
//  */
// protected <A extends ActionImpl> CheckBoxBinder<A> checkboxBinder(Class<A> actionClass) {
//     return new CheckBoxBinder<>(actionClass, this);
// }
//
// /**
//  * Creates binding builder to build a binding between a color picker interaction and the given action type.
//  * Do not forget to call bind() at the end of the build to execute the builder.
//  * @param actionClass The action to produce.
//  * @param <A> The type of the action.
//  * @return The binding builder. Cannot be null.
//  * @throws NullPointerException If the given class is null.
//  */
// protected <A extends ActionImpl> ColorPickerBinder<A> colorPickerBinder(Class<A> actionClass) {
//     return new ColorPickerBinder<>(actionClass, this);
// }
//
// /**
//  * Creates binding builder to build a binding between a spinner interaction and the given action type.
//  * Do not forget to call bind() at the end of the build to execute the builder.
//  * @param actionClass The action to produce.
//  * @param <A> The type of the action.
//  * @return The binding builder. Cannot be null.
//  * @throws NullPointerException If the given class is null.
//  */
// protected <A extends ActionImpl> SpinnerBinder<A> spinnerBinder(Class<A> actionClass) {
//     return new SpinnerBinder<>(actionClass, this);
// }
//
// /**
//  * Creates binding builder to build a binding between a text input interaction and the given action type.
//  * Do not forget to call bind() at the end of the build to execute the builder.
//  * @param actionClass The action to produce.
//  * @param <A> The type of the action.
//  * @return The binding builder. Cannot be null.
//  * @throws NullPointerException If the given class is null.
//  */
// protected <A extends ActionImpl, W extends TextInputControl> TextInputBinder<A, W> textInputBinder(Class<A> actionClass) {
//     return new TextInputBinder<>(actionClass, this);
// }
//
// /**
//  * Creates binding builder to build a binding between a menu item interaction and the given action type.
//  * Do not forget to call bind() at the end of the build to execute the builder.
//  * @param actionClass The action to produce.
//  * @param <A> The type of the action.
//  * @return The binding builder. Cannot be null.
//  * @throws NullPointerException If the given class is null.
//  */
// protected <A extends ActionImpl> MenuItemBinder<A> menuItemBinder(Class<A> actionClass) {
//     return new MenuItemBinder<>(actionClass, this);
// }
//
// /**
//  * Creates binding builder to build a binding between a combobox interaction and the given action type.
//  * Do not forget to call bind() at the end of the build to execute the builder.
//  * @param actionClass The action to produce.
//  * @param <A> The type of the action.
//  * @return The binding builder. Cannot be null.
//  * @throws NullPointerException If the given class is null.
//  */
// protected <A extends ActionImpl> ComboBoxBinder<A> comboboxBinder(Class<A> actionClass) {
//     return new ComboBoxBinder<>(actionClass, this);
// }
//
// /**
//  * Creates binding builder to build a binding between a tab interaction (on tabs of a TabPane) and the given action type.
//  * Do not forget to call bind() at the end of the build to execute the builder.
//  * @param actionClass The action to produce.
//  * @param <A> The type of the action.
//  * @return The binding builder. Cannot be null.
//  * @throws NullPointerException If the given class is null.
//  */
// protected <A extends ActionImpl> TabBinder<A> tabBinder(Class<A> actionClass) {
//     return new TabBinder<>(actionClass, this);
// }
//
// /**
//  * Creates binding builder to build a binding between a given interaction and the given action type.
//  * This builder is dedicated to bind window interactions to actions.
//  * Do not forget to call bind() at the end of the build to execute the builder.
//  * @param action The action to produce.
//  * @param interaction The user interaction to perform on windows
//  * @param <A> The type of the action.
//  * @param <I> The type of the user interaction.
//  * @return The binding builder. Cannot be null.
//  * @throws NullPointerException If the given class is null.
//  */
// protected <A extends ActionImpl, I extends JfxInteraction<?, ?>> WindowBinder<A, I> windowBinder(Class<A> action, I interaction) {
//     return new WindowBinder<>(action, interaction, this);
// }
