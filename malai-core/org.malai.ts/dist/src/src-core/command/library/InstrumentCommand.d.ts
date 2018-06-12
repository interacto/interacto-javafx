import { Instrument } from "../../instrument/Instrument";
import { WidgetBinding } from "../../binding/WidgetBinding";
import { CommandImpl } from "../CommandImpl";
export declare abstract class InstrumentCommand extends CommandImpl {
    protected instrument: Instrument<WidgetBinding> | undefined;
    protected constructor(instrument?: Instrument<WidgetBinding>);
    flush(): void;
    canDo(): boolean;
    getInstrument(): Instrument<WidgetBinding> | undefined;
    setInstrument(newInstrument: Instrument<WidgetBinding>): void;
}
