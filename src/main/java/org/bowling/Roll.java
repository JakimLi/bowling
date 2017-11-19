package org.bowling;

import static com.google.common.collect.ImmutableMap.of;

class Roll {
    private static final int DASH = 45;
    private static final int SLASH = 47;
    private static final int X = 88;
    private int value;

    Roll(int value) {
        this.value = value;
    }

    int pins() {
        return of(DASH, 0, X, 10).getOrDefault(this.value, this.value - 48);
    }

    boolean spare() {
        return this.value == SLASH;
    }

    boolean strike() {
        return this.value == X;
    }
}
