package org.bowling;

import java.util.List;
import java.util.function.Predicate;

import static com.google.common.collect.Iterables.concat;
import static com.google.common.collect.Lists.newArrayList;
import static org.bowling.Frame.NextRolls.next;
import static org.bowling.Roll.pins;

class Frame {

    private List<Roll> rolls = newArrayList();
    private Frame nextFrame;

    private Frame() {

    }

    static Frame frame() {
        return new Frame();
    }

    Frame chainNextFrame(Frame frame) {
        this.nextFrame = frame;
        return this;
    }

    int score() {
        return pins(this.rolls) + bonus();
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

    static class NextRolls {

        private int count;

        NextRolls(int count) {
            this.count = count;
        }

        static NextRolls next(int count) {
            return new NextRolls(count);
        }

        List<Roll> rollsAfter(Frame frame) {
            return count == 0 ? newArrayList() : nextRolls(frame, count);
        }

        private List<Roll> nextRolls(Frame frame, int count) {
            if (frame.lastFrame()) {
                return frame.rolls;
            }

            List<Roll> rolls = frame.nextFrame.rolls;
            if (enough(rolls, count)) {
                return rolls.subList(0, count);
            }

            List<Roll> nextRolls = nextRolls(frame.nextFrame, count - rolls.size());
            return newArrayList(concat(rolls, nextRolls));
        }

        private boolean enough(List<Roll> rolls, int count) {
            return rolls.size() >= count;
        }
    }
}
