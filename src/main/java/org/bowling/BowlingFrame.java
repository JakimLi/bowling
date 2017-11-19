package org.bowling;

import java.util.List;

public class BowlingFrame {
    private List<Character> chars;

    public BowlingFrame(List<Character> chars) {
        this.chars = chars;
    }

    public List<Character> getChars() {
        return chars;
    }
}
