import { CommandImpl } from "../CommandImpl";
export declare abstract class ModifyValue extends CommandImpl {
    protected value: Object | undefined;
    protected constructor(value?: Object);
    flush(): void;
    canDo(): boolean;
    setValue(newValue: Object): void;
    abstract applyValue(obj: Object): void;
    abstract isValueMatchesProperty(): boolean;
}
