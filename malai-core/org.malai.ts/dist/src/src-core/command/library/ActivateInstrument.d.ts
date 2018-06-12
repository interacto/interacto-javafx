import { Instrument } from "../../instrument/Instrument";
import { WidgetBinding } from "../../binding/WidgetBinding";
import { InstrumentCommand } from "./InstrumentCommand";
export declare class ActivateInstrument extends InstrumentCommand {
    constructor(instrument?: Instrument<WidgetBinding>);
    protected doCmdBody(): void;
}
