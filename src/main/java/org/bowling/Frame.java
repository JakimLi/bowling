package org.bowling;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.google.common.collect.Lists.newArrayList;
import static org.bowling.Frame.NextRolls.next;

class Frame {

    private List<Roll> rolls = newArrayList();
    private Frame nextFrame;

    private Frame() {

    }

    static Frame frame() {
        return new Frame();
    }

    Frame nextFrame(Frame frame) {
        this.nextFrame = frame;
        return this;
    }

    int score() {
        if (strike() || spare()) {
            return 10 + bonus();
        }
        return pins(this.rolls);
    }

    private Integer bonus() {
        return pins(next(bonusRoll()).rollsAfter(this));
    }

    private int bonusRoll() {
        if (spare()) return 1;
        if (strike()) return 2;
        return 0;
    }

    private boolean strike() {
        return this.rolls.stream().anyMatch(Roll::strike);
    }

    private Integer pins(List<Roll> rolls) {
        return rolls.stream()
                .map(Roll::pins)
                .reduce(0, (a, b) -> a + b);
    }

    private boolean spare() {
        return rolls.stream().anyMatch(Roll::spare);
    }

    Frame rolls(List<Roll> rolls) {
        this.rolls.addAll(rolls);
        return this;
    }

    static class NextRolls {
        private int count;

        NextRolls(int count) {
            this.count = count;
        }

        static NextRolls next(int count) {
            return new NextRolls(count);
        }

        List<Roll> rollsAfter(Frame frame) {
            return nextRolls(frame, count);
        }

        private List<Roll> nextRolls(Frame frame, int count) {
            if (frame.rolls.size() >= count) {
                return frame.rolls.subList(0, count);
            }
            List<Roll> rolls = frame.rolls;
            List<Roll> nextRolls = nextRolls(frame.nextFrame, count - rolls.size());
            return Stream.concat(rolls.stream(), nextRolls.stream()).collect(Collectors.toList());
        }
    }
}
