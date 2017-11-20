package org.tennis;

class Player {

    private String name;
    private int score;

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
}
