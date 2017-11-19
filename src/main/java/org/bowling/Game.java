package org.bowling;

class Game {
    static int score(String frames) {
        return frames.chars()
                .map(Converter::toNumber)
                .reduce(0, (a, b) -> a + b);
    }
}
