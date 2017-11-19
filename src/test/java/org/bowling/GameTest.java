package org.bowling;

import org.junit.Test;

import static org.bowling.Game.score;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class GameTest {

    @Test
    public void should_cacludate_scores_for_situation_that_has_no_spare_and_strikes() throws Exception {
        assertThat(score("12345123451234512345"), is(60));
    }
}
