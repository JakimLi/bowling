package org.bowling;

import static com.google.common.collect.ImmutableMap.of;

class Roll {
    private static final int DASH = 45;
    private int value;

    Roll(int value) {
        this.value = value;
    }

    int value() {
        return of(DASH, 0).getOrDefault(this.value, this.value - 48);
    }
}
