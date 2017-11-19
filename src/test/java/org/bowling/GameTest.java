package org.bowling;

import org.junit.Test;

import static org.bowling.Game.score;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class GameTest {

    @Test
    public void score_has_no_spare_and_no_strike() throws Exception {
        assertThat(score("12345123451234512345"), is(60));
        assertThat(score("22345123451234512345"), is(61));
    }

    @Test
    public void score_with_many_heartbreaks() throws Exception {
        assertThat(score("9-9-9-9-9-9-9-9-9-9-"), is(90));
    }

    @Test
    public void score_with_some_spares() throws Exception {
        assertThat(score("5/5/5/5/5/5/5/5/5/5-"), is(140));
    }

    @Test
    public void score_with_all_spares() throws Exception {
        assertThat(score("5/5/5/5/5/5/5/5/5/5/5"), is(150));
    }

    @Test
    public void score_with_all_strikes() throws Exception {
        assertThat(score("XXXXXXXXXXXX"), is(300));
    }
}
