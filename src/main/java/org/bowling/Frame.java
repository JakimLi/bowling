package org.bowling;

import java.util.List;

import static com.google.common.collect.Lists.newArrayList;

class Frame {

    private List<Roll> rolls = newArrayList();
    private Frame nextFrame;

    private Frame() {

    }

    static Frame frame() {
        return new Frame();
    }

    Frame next(Frame frame) {
        this.nextFrame = frame;
        return this;
    }

    int score() {
        return spare() ? 10 + nextRoll().pins() : pins();
    }

    private Integer pins() {
        return this.rolls.stream()
                .map(Roll::pins)
                .reduce(0, (a, b) -> a + b);
    }

    private Roll nextRoll() {
        return this.nextFrame.rolls.stream().findFirst().get();
    }

    private boolean spare() {
        return rolls.stream().anyMatch(Roll::spare);
    }

    Frame rolls(List<Roll> rolls) {
        this.rolls.addAll(rolls);
        return this;
    }
}
