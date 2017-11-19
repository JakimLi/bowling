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
        List<Frame> frameList = frames(frames, none());
        for (int i = 10; i < frameList.size(); i++) {
            frameList.get(i).bonus(true);
        }
        return frameList;
    }

    private static List<Frame> frames(String frames, Frame lastFrame) {
        if (isEndCondition(frames)) {
            return newArrayList(new Frame(frames, lastFrame));
        }

        List<Frame> left = frames(frames.substring(0, getEndIndex(frames)), lastFrame);
        List<Frame> right = frames(frames.substring(getEndIndex(frames)), left.get(0));

        return Stream
                .concat(left.stream(), right.stream())
                .collect(Collectors.toList());
    }

    private static boolean isEndCondition(String frames) {
        return frames.length() < 2 || (frames.length() == 2 && !isStrike(frames));
    }

    private static boolean isStrike(String frames) {
        return frames.charAt(0) == 'X';
    }

    private static int getEndIndex(String frames) {
        return isStrike(frames) ? 1 : 2;
    }

}
