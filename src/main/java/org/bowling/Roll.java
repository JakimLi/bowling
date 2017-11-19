package org.bowling;

class Roll {
    private int value;

    Roll(int value) {
        this.value = value;
    }

    int value() {
        return this.value - 48;
    }
}
