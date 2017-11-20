package org.bowling;

import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;

import static java.util.Arrays.stream;
import static org.bowling.Roll.Type.from;

class Roll {
    private static final int DASH = 45;
    private static final int SLASH = 47;
    private static final int X = 88;
    private int value;
    private int pins;

    Roll(int value) {
        this.value = value;
        this.pins = from(this.value).map(type -> type.score).orElseGet(() -> this.value - 48);
    }

    static Integer pins(List<Roll> rolls) {
        return rolls.stream()
                .peek(spare(rolls))
                .map(roll -> roll.pins)
                .reduce(Integer::sum)
                .orElse(0);
    }

    private static Consumer<Roll> spare(List<Roll> rolls) {
        return roll -> {
            if (roll.spare()) {
                roll.pins = 10 - last(rolls, roll).pins;
            }
        };
    }

    private static Roll last(List<Roll> rolls, Roll roll) {
        return rolls.get(rolls.indexOf(roll) - 1);
    }

    boolean spare() {
        return this.value == SLASH;
    }

    boolean strike() {
        return this.value == X;
    }

    enum Type {
        STRIKE(X, 10), SPARE(SLASH, 0), HEARTBREAK(DASH, 0);

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
