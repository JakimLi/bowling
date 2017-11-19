package org.bowling;

class Frame {
    private boolean bonus;
    private String value;
    private Frame lastFrame;

    Frame(String value) {
        this.value = value;

        if (value.contains("/")) {
            this.bonus = true;
        }
    }

    int score() {
        return currentScore() + lastBonus();
    }

    private int lastBonus() {
        if (lastFrame != null && lastFrame.bonus) {
            return Integer.valueOf(this.value.substring(0, 1));
        }

        return 0;
    }

    private int currentScore() {
        return bonus ? 10 : pins();
    }

    private int pins() {
        return value.chars().map(Converter::toNumber).reduce(0, (a, b) -> a + b);
    }

    void lastFrame(Frame lastFrame) {
        this.lastFrame = lastFrame;
    }
}
