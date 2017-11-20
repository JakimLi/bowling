package org.tennis;

import java.util.function.Predicate;

class Rules {

    static boolean win(Player player) {
        return player.matches(score(atLeast(4)), advanced(atLeast(2)));
    }

    static boolean van(Player player) {
        return player.matches(bothScore(atLeast(3)), advanced(just(1)));
    }

    static boolean deuce(Player player) {
        return player.matches(bothScore(atLeast(3)), advanced(just(0)));
    }

    private static Predicate<Player> advanced(Matcher matcher) {
        return player -> matcher.match(player.getScore(), player.opponent().getScore());
    }

    private static Predicate<Player> score(Matcher matcher) {
        return player -> matcher.match(player.getScore(), 0);
    }

    private static Matcher atLeast(int gap) {
        return (a, b) -> a - b >= gap;
    }

    private static Matcher just(int gap) {
        return (a, b) -> a - b == gap;
    }

    private static Predicate<Player> bothScore(Matcher matcher) {
        return player -> score(matcher).test(player) && score(matcher).test(player.opponent());
    }

    interface Matcher {
        boolean match(int a, int b);
    }
}
