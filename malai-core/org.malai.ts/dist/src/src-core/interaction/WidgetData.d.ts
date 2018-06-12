import { InteractionData } from "./InteractionData";
export interface WidgetData<T> extends InteractionData {
    getWidget(): T | undefined;
}
