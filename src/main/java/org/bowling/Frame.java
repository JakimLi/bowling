package org.bowling;

import java.util.List;
import java.util.function.Predicate;
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
        return anyMatch(Roll::strike);
    }

    private boolean anyMatch(Predicate<Roll> strike) {
        return this.rolls.stream().anyMatch(strike);
    }

    private Integer pins(List<Roll> rolls) {
        return rolls.stream()
                .map(Roll::pins)
                .reduce(Integer::sum)
                .orElse(0);
    }

    private boolean spare() {
        return anyMatch(Roll::spare);
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
            if (frame.nextFrame == null) {
                return frame.rolls;
            }

            if (frame.nextFrame.rolls.size() >= count) {
                return frame.nextFrame.rolls.subList(0, count);
            }
            List<Roll> rolls = frame.nextFrame.rolls;
            List<Roll> nextRolls = nextRolls(frame.nextFrame.nextFrame, count - rolls.size());
            return Stream.concat(rolls.stream(), nextRolls.stream()).collect(Collectors.toList());
        }
    }
}
