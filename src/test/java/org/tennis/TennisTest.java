package org.tennis;

import org.junit.Before;
import org.junit.Test;

import static java.util.stream.IntStream.range;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class TennisTest {

    private static final String PLAYER_1 = "player1";
    private static final String PLAYER_2 = "player2";

    private Tennis game;

    @Before
    public void setUp() throws Exception {
        game = Tennis.game(PLAYER_1, PLAYER_2);
    }

    @Test
    public void should_0_to_love() throws Exception {
        assertThat(game.getScore(), is("love-love"));
    }

    @Test
    public void should_1_to_fifteen() throws Exception {
        won(PLAYER_1, 1);

        assertThat(game.getScore(), is("fifteen-love"));
    }

    @Test
    public void should_2_to_thirty() throws Exception {
        won(PLAYER_1, 2);

        assertThat(game.getScore(), is("thirty-love"));
    }

    @Test
    public void should_3_to_forty() throws Exception {
        won(PLAYER_1, 3);

        assertThat(game.getScore(), is("forty-love"));
    }

    @Test
    public void player1_win_at_5_to_3() throws Exception {
        won(PLAYER_1, 5);
        won(PLAYER_1, 3);

        assertThat(game.getScore(), is("win for player1"));
    }

    private void won(String player, int count) {
        range(0, count).forEach(i -> game.wonPoint(player));
    }
}
