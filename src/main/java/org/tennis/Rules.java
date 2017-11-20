package org.tennis;

class Rules {

    static boolean win(Player player) {
        return player.matches(score(atLeast(4)), advanced(atLeast(2)));
    }

    static boolean van(Player player) {
        return player.matches(bothScore(atLeast(3)), advanced(just(1)));
    }

    static boolean deuce(Player player) {
        return player.matches(bothScore(atLeast(3)), deuce());
    }

    private static Rule deuce() {
        return advanced(just(0));
    }

    private static Rule advanced(Matcher matcher) {
        return player -> matcher.match(player.getScore(), player.opponent().getScore());
    }

    private static Rule score(Matcher matcher) {
        return player -> matcher.match(player.getScore(), 0);
    }

    private static Rule bothScore(Matcher matcher) {
        return player -> score(matcher).match(player) && score(matcher).match(player.opponent());
    }

    private static Matcher atLeast(int gap) {
        return (a, b) -> a - b >= gap;
    }

    private static Matcher just(int gap) {
        return (a, b) -> a - b == gap;
    }

    interface Rule {
        boolean match(Player player);
    }

    interface Matcher {
        boolean match(int a, int b);
    }
}
