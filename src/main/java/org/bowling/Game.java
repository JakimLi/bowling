package org.bowling;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.google.common.collect.Lists.newArrayList;
import static org.bowling.Frame.frame;

class Game {
    static int score(String round) {
        return frames(rolls(round)).stream()
                .map(Frame::score)
                .reduce(0, (a, b) -> a + b);
    }

    private static List<Roll> rolls(String round) {
        return round.chars()
                .mapToObj(Roll::new)
                .collect(Collectors.toList());
    }

    private static List<Frame> frames(List<Roll> rolls) {
        Roll firstRoll = rolls.get(0);
        Roll secondRoll = rolls.get(1);

        if (rolls.size() == 2) {
            return newArrayList(firstFrame(firstRoll, secondRoll));
        }

        List<Frame> frames = frames(rolls.subList(2, rolls.size()));
        Frame frame = firstFrame(firstRoll, secondRoll).next(frames.get(0));

        return Stream.concat(newArrayList(frame).stream(), frames.stream())
                .collect(Collectors.toList());
    }

    private static Frame firstFrame(Roll firstRoll, Roll secondRoll) {
        return frame().rolls(firstRoll, secondRoll);
    }
}
