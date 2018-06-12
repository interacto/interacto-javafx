import { ErrorNotifier } from "./ErrorNotifier";
export declare class ErrorCatcher {
    static INSTANCE: ErrorCatcher;
    private notifier;
    private constructor();
    setNotifier(newNotifier: ErrorNotifier): void;
    getErrorNotifier(): ErrorNotifier | undefined;
    reportError(exception: Error): void;
}
