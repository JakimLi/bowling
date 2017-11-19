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
                .reduce(0, (a, b) -> a + b);
    }

    private static List<Roll> rolls(String round) {
        return round.chars()
                .mapToObj(Roll::new)
                .collect(Collectors.toList());
    }

    private static List<Frame> frames(List<Roll> rolls) {
        if (rolls.size() <= 2) {
            return newArrayList(frame(rolls));
        }

        List<Frame> frames = frames(rolls.subList(step(rolls), rolls.size()));
        Frame frame = frame(rolls.subList(0, step(rolls))).nextFrame(frames.get(0));

        return Stream.concat(newArrayList(frame).stream(), frames.stream())
                .collect(Collectors.toList());
    }

    private static int step(List<Roll> rolls) {
        return rolls.get(0).strike() ? 1 : 2;
    }

    private static Frame frame(List<Roll> rolls) {
        return Frame.frame().rolls(rolls);
    }
}
