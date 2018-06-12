export declare enum EventRegistrationToken {
    MouseDown = "mousedown",
    MouseUp = "mouseup",
    MouseMove = "mousemove",
    KeyDown = "keydown",
    KeyUp = "keyup",
    Click = "click",
    Input = "input",
    Scroll = "scroll",
    Change = "change",
    BeforeUnload = "beforeunload",
}
export declare function isButton(target: EventTarget): target is Element;
export declare function isCheckBox(target: EventTarget): target is Element;
export declare function isColorChoice(target: EventTarget): target is Element;
export declare function isComboBox(target: EventTarget): target is Element;
export declare function isDatePicker(target: EventTarget): target is Element;
export declare function isSpinner(target: EventTarget): target is Element;
export declare function isHyperLink(target: EventTarget): target is Element;
export declare function isChoiceBox(target: EventTarget): target is Element;
export declare function isTextInput(target: EventTarget): target is Element;
export declare function isWindowClosed(event: Event): boolean;
export declare function isKeyDownEvent(event: Event): event is KeyboardEvent;
export declare function isMouseDownEvent(event: Event): event is MouseEvent;
export declare function isMouseUpEvent(event: Event): event is MouseEvent;
export declare function isScrollEvent(event: Event): event is UIEvent;
export declare function isMenuButton(target: EventTarget): target is Element;
export declare enum KeyCode {
    ESCAPE = 27,
}
