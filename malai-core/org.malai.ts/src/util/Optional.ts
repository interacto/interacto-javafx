
namespace util {
    export class Optional<T> {
        private static readonly EMPTY: Optional<any> = new Optional<any>();
        private readonly value: T;

        private constructor(obj?: T) {
            this.value = obj;
        }

        public static empty<T>(): Optional<T> {
            return Optional.EMPTY as Optional<T>;
        }

        public static of<T>(var0: T): Optional<T> {
            return new Optional(var0);
        }

        public static ofNullable<T>(var0: T): Optional<T> {
            return var0 === undefined ? Optional.empty() as Optional<T> : Optional.of(var0);
        }

        public get(): T {
            if (this.value === undefined) {
                throw new TypeError("No value present");
            } else {
                return this.value;
            }
        }

        public isPresent(): boolean {
            return this.value !== undefined;
        }

        // public ifPresent(var1 : Consumer<? super T>) : any {
        //     if (this.value != null) {
        //         var1.accept(this.value);
        //     }
        // }

        public filter(predicate: (obj: T) => boolean) : Optional<T> {
            if (!this.isPresent()) {
                return this;
            } else {
                return predicate(this.value) ? this : Optional.empty() as Optional<T>;
            }
        }
//
// public <U> Optional<U> map(Function<? super T, ? extends U> var1) {
//         Objects.requireNonNull(var1);
//         return !this.isPresent() ? empty() : ofNullable(var1.apply(this.value));
//     }
//
// public <U> Optional<U> flatMap(Function<? super T, Optional<U>> var1) {
//         Objects.requireNonNull(var1);
//         return !this.isPresent() ? empty() : (Optional)Objects.requireNonNull(var1.apply(this.value));
//     }
//
// public T orElse(T var1) {
//         return this.value != null ? this.value : var1;
//     }
//
// public T orElseGet(Supplier<? extends T> var1) {
//         return this.value != null ? this.value : var1.get();
//     }
    }
}
