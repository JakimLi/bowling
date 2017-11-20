package org.bowling;

import java.util.Optional;
import java.util.function.BinaryOperator;

import static java.util.Arrays.stream;
import static org.bowling.Roll.Type.from;

class Roll {
    private static final int DASH = 45;
    private static final int SLASH = 47;
    private static final int X = 88;
    private int value;

    Roll(int value) {
        this.value = value;
    }

    int pins() {
        return from(this.value)
                .map(type -> type.score)
                .orElseGet(() -> this.value - 48);
    }

    boolean spare() {
        return this.value == SLASH;
    }

    boolean strike() {
        return this.value == X;
    }

    static BinaryOperator<Integer> sum() {
        return (a, b) -> b == Type.SPARE_SCORE ? 10 : a + b;
    }

    enum Type {
        STRIKE(X, 10), SPARE(SLASH, Type.SPARE_SCORE), HEARTBREAK(DASH, 0);

        private static final int SPARE_SCORE = -1;
        private int character;
        private int score;

        Type(int character, int score) {
            this.character = character;
            this.score = score;
        }

        public static Optional<Type> from(int aChar) {
            return stream(values())
                    .filter(type -> type.character == aChar)
                    .findFirst();
        }
    }
}
