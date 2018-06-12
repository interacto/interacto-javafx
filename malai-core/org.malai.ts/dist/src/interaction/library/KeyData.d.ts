import { Optional } from "../../util/Optional";
export interface KeyData {
    getTarget(): Optional<EventTarget>;
    getKey(): String;
}
