package org.tennis;

import java.util.List;
import java.util.function.Predicate;

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

    boolean matches(List<Predicate<Player>> rules) {
        return rules.stream().allMatch(rule -> rule.test(this));
    }
}
