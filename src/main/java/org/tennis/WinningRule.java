package org.tennis;

import static com.google.common.collect.Lists.newArrayList;

class WinningRule {

    static boolean winner(Player player) {
        return player.win(newArrayList(atLeast(4), advanced(2)));
    }

    private static Rule advanced(int points) {
        return player1 -> player1.getScore() - player1.opponent().getScore() >= points;
    }

    private static Rule atLeast(int points) {
        return player1 -> player1.getScore() >= points;
    }

    private static Rule bothAtLeast(int points) {
        return player -> atLeast(points).match(player) && atLeast(points).match(player.opponent());
    }

    static boolean vanner(Player player) {
        return player.win(newArrayList(bothAtLeast(3), advanced(1)));
    }
}
