package org.bowling;

class Game {
    static int score(String frames) {
        return frames.chars()
                .mapToObj(Roll::new)
                .map(Roll::value)
                .reduce(0, (a, b) -> a + b);
    }
}
