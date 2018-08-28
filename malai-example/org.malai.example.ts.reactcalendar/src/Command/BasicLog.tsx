import {CommandImpl} from "org.malai.ts-dev";

export class BasicLog extends CommandImpl {

    private srcObject: EventTarget | undefined;

    constructor(target: EventTarget | undefined) {
        super();
        this.srcObject = target;
    }

    public canDo(): boolean {
        return true;
    }

    protected doCmdBody(): void {
        console.log(this.srcObject as HTMLElement);
    }

}