package org.bowling;

class Frame {
    private String value;
    private Frame lastFrame;
    private boolean bonus;

    Frame(String value, Frame lastFrame) {
        this.value = value;
        this.lastFrame = lastFrame;
    }

    private Frame(String value) {
        this.value = value;
    }

    private boolean spare() {
        return this.value.contains("/");
    }

    int score() {
        return currentScore() + lastBonus() + bonusBeforeOfLast();
    }

    private int bonusBeforeOfLast() {
        if (isNotStrikeBeforeOfLast()) {
            return 0;
        }
        return pins();
    }

    private int converterValue() {
        return Converter.toNumber(this.value.charAt(0));
    }

    private boolean isNotStrikeBeforeOfLast() {
        return lastFrame == null || lastFrame.lastFrame == null || !lastFrame.lastFrame.strike();
    }

    private boolean strike() {
        return this.value.contains("X");
    }

    private int lastBonus() {
        if (lastFrame == null || lastFrame.isBonus() || !isSpareOrStrikeLastFrame()) {
            return 0;
        }
        return converterValue();
    }

    private boolean isSpareOrStrikeLastFrame() {
        return lastFrame.spare() || lastFrame.strike();
    }

    private int currentScore() {
        if (isBonus()) {
            return 0;
        }
        return (spare() || strike()) ? 10 : pins();
    }

    private int pins() {
        return value.chars()
                .map(Converter::toNumber)
                .reduce(0, (a, b) -> b == -1 ? 10 : a + b);
    }

    static Frame none() {
        return new Frame("");
    }

    public void bonus(boolean bonus) {
        this.bonus = bonus;
    }

    public boolean isBonus() {
        return bonus;
    }
}
