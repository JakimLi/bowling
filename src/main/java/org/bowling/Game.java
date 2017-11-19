package org.bowling;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.google.common.collect.Lists.newArrayList;
import static org.bowling.Frame.none;

class Game {
    static int score(String round) {
        return frames(round).stream()
                .map(Frame::score)
                .reduce(0, (a, b) -> a + b);
    }

    private static List<Frame> frames(String frames) {
        return frames(frames, none());
    }

    private static List<Frame> frames(String frames, Frame lastFrame) {
        if (frames.length() == 2) {
            return newArrayList(new Frame(frames, lastFrame));
        }

        List<Frame> left = frames(frames.substring(0, 2), lastFrame);
        List<Frame> right = frames(frames.substring(2), left.get(0));

        return Stream
                .concat(left.stream(), right.stream())
                .collect(Collectors.toList());
    }

}
