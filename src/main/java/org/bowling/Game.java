package org.bowling;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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
        if (rolls.size() == 1 || rolls.size() == 2 && noStrike(rolls)) {
            return newArrayList(frame(rolls));
        }

        List<Frame> lastFrames = frames(last(rolls));
        Frame frame = frame(first(rolls)).nextFrame(lastFrames.get(0));

        return Stream.concat(newArrayList(frame).stream(), lastFrames.stream())
                .collect(Collectors.toList());
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

    private static Frame frame(List<Roll> rolls) {
        return Frame.frame().rolls(rolls);
    }
}
