import { InteractionData, Optional } from "../..";
export interface KeyData extends InteractionData {
    getTarget(): Optional<EventTarget>;
    getKey(): String;
}
