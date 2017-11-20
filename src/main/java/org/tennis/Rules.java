package org.tennis;

import java.util.function.Predicate;

import static com.google.common.collect.Lists.newArrayList;

class Rules {

    static boolean win(Player player) {
        return player.matches(newArrayList(atLeast(4), advanced(2)));
    }

    static boolean van(Player player) {
        return player.matches(newArrayList(bothAtLeast(3), advanced(1)));
    }

    static boolean deuce(Player player) {
        return player.matches(newArrayList(bothAtLeast(3), equal()));
    }

    private static Predicate<Player> advanced(int points) {
        return player1 -> player1.getScore() - player1.opponent().getScore() >= points;
    }

    private static Predicate<Player> equal() {
        return player -> player.getScore() == player.opponent().getScore();
    }

    private static Predicate<Player> atLeast(int points) {
        return player1 -> player1.getScore() >= points;
    }

    private static Predicate<Player> bothAtLeast(int points) {
        return player -> atLeast(points).test(player) && atLeast(points).test(player.opponent());
    }
}
