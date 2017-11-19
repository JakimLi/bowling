package org.bowling;

import java.util.List;
import java.util.function.Predicate;

import static com.google.common.collect.Iterables.concat;
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

    private boolean anyMatch(Predicate<Roll> predicate) {
        return this.rolls.stream().anyMatch(predicate);
    }

    private Integer pins(List<Roll> rolls) {
        return rolls.stream()
                .map(Roll::pins)
                .reduce(Integer::sum)
                .orElse(0);
    }

    private boolean lastFrame() {
        return this.nextFrame == null;
    }

    private boolean spare() {
        return anyMatch(Roll::spare);
    }

    Frame rolls(List<Roll> rolls) {
        this.rolls.addAll(rolls);
        return this;
    }

    private Frame nextNextFrame() {
        return this.nextFrame.nextFrame;
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
            if (frame.lastFrame()) {
                return frame.rolls;
            }

            List<Roll> rolls = frame.nextFrame.rolls;
            if (enough(rolls, count)) {
                return rolls.subList(0, count);
            }

            List<Roll> nextRolls = nextRolls(frame.nextNextFrame(), count - rolls.size());
            return newArrayList(concat(rolls, nextRolls));
        }

        private boolean enough(List<Roll> rolls, int count) {
            return rolls.size() >= count;
        }
    }
}
