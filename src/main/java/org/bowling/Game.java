package org.bowling;

class Game {
    static int score(String frames) {
        return frames.chars()
                .map(Game::toNumber)
                .reduce(0, (a, b) -> a + b);
    }

    private static int toNumber(int aChar) {
        return aChar - 48;
    }
}
