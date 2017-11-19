package org.bowling;

import java.util.ArrayList;
import java.util.List;
import java.util.function.IntFunction;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static com.google.common.collect.Lists.newArrayList;
import static com.google.common.collect.Lists.newLinkedList;

class Game {
    static int score(String round) {
        List<Frame> frames = getFrames(round);
        chainFrames(frames);
        return scores(frames);
    }

    private static void chainFrames(List<Frame> frames) {
        for (int i = 1; i < frames.size(); i++) {
            frames.get(i).lastFrame(frames.get(i - 1));
        }
    }

    private static Integer scores(List<Frame> frames1) {
        return frames1.stream()
                .map(Frame::score)
                .reduce(0, (a, b) -> a + b);
    }

    private static List<Frame> getFrames(String frames) {
        List<Frame> aFrames = newArrayList();
        for (int i = 0; i < frames.toCharArray().length; i+=2) {
            aFrames.add(new Frame(frames.substring(i, i + 2)));
        }
        return aFrames;
    }
}
