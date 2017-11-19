package org.bowling;

import java.util.List;
import java.util.stream.Collectors;

import static com.google.common.collect.Iterables.concat;
import static com.google.common.collect.Lists.newArrayList;

class Game {
    static int score(String round) {
        return frames(rolls(round)).stream()
                .limit(10)
                .map(Frame::score)
                .reduce(Integer::sum)
                .orElse(0);
    }

    private static List<Roll> rolls(String round) {
        return round.chars()
                .mapToObj(Roll::new)
                .collect(Collectors.toList());
    }

    private static List<Frame> frames(List<Roll> rolls) {
        if (lastRoll(rolls) || lastFrame(rolls)) {
            return someFrames(frame(rolls));
        }

        List<Frame> lastFrames = frames(last(rolls));
        Frame frame = frame(first(rolls)).chainNextFrame(lastFrames.get(0));

        return newArrayList(concat(someFrames(frame), lastFrames));
    }

    private static boolean lastRoll(List<Roll> rolls) {
        return rolls.size() == 1;
    }

    private static boolean lastFrame(List<Roll> rolls) {
        return rolls.size() == 2 && noStrike(rolls);
    }

    private static List<Frame> someFrames(Frame frame) {
        return newArrayList(frame);
    }

    private static Frame frame(List<Roll> first) {
        return Frame.frame().rolls(first);
    }

    private static boolean noStrike(List<Roll> rolls) {
        return rolls.stream().noneMatch(Roll::strike);
    }

    private static List<Roll> first(List<Roll> rolls) {
        return rolls.subList(0, step(rolls));
    }

    private static List<Roll> last(List<Roll> rolls) {
        return rolls.subList(step(rolls), rolls.size());
    }

    private static int step(List<Roll> rolls) {
        return rolls.get(0).strike() ? 1 : 2;
    }
}
