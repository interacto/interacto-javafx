export interface ErrorNotifier {
    onException(exception: Error): void;
}
