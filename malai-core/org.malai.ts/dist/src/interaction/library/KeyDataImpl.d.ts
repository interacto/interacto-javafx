import { Optional } from "../../util/Optional";
import { KeyData } from "./KeyData";
export declare class KeyDataImpl implements KeyData {
    protected key: String | undefined;
    protected target: EventTarget | undefined;
    constructor();
    reinitData(): void;
    getTarget(): Optional<EventTarget>;
    getKey(): String;
    setKeyData(event: KeyboardEvent): void;
}
