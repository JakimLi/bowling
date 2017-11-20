package org.tennis;

import java.util.function.Predicate;
import java.util.stream.Stream;

class Player {

    private String name;
    private int score;
    private Player opponent;

    private Player(String name) {
        this.name = name;
    }

    static Player player(String name) {
        return new Player(name);
    }

    String name() {
        return name;
    }

    void score() {
        score++;
    }

    int getScore() {
        return score;
    }

    void opponent(Player opponent) {
        this.opponent = opponent;
    }

    Player opponent() {
        return opponent;
    }

    @SafeVarargs
    final boolean matches(Predicate<Player>... rules) {
        return Stream.of(rules).allMatch(rule -> rule.test(this));
    }
}
