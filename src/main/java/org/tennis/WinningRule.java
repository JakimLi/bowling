package org.tennis;

import static com.google.common.collect.Lists.newArrayList;

class WinningRule {

    static boolean winner(Player player) {
        return player.win(newArrayList(atLeast4(), moreThan2()));
    }

    private static Rule moreThan2() {
        return player1 -> player1.getScore() - player1.opponent().getScore() >= 2;
    }

    private static Rule atLeast4() {
        return player1 -> player1.getScore() >= 4;
    }
}
