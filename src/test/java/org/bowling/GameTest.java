package org.bowling;

import org.junit.Test;

import static org.bowling.Game.score;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class GameTest {

    @Test
    public void should_cacludate_scores_for_situation_that_has_no_spare_and_strikes() throws Exception {
        assertThat(score("12345123451234512345"), is(60));
        assertThat(score("22345123451234512345"), is(61));
    }

    @Test
    public void should_caclulate_scores_for_every_frame_heart_breaks() throws Exception {
        assertThat(score("9-9-9-9-9-9-9-9-9-9-"), is(90));
        assertThat(score("2-9-9-9-9-9-9-9-9-9-"), is(83));
    }

    @Test
    public void score_for_one_spare() throws Exception {
        assertThat(score("9/9-9-9-9-9-9-9-9-9-"), is(100));
        assertThat(score("8/9-9-9-9-9-9-9-9-9-"), is(100));
    }

    @Test
    public void score_for_all_strikes() throws Exception {
        assertThat(score("XXXXXXXXXXXX"), is(300));
        assertThat(score("XXXXXXXXXX11"), is(273));
        assertThat(score("XXXXXXXXXX1/"), is(281));
    }

    @Test
    public void score_for_one_spare_one_strike() throws Exception {
        assertThat(score("123/33X411-34224433"), is(68));
    }
}
