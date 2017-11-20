package org.bowling.fizzbuzz;

import com.google.common.collect.ImmutableMap;
import org.junit.Test;

import static org.bowling.fizzbuzz.FizzBuzz.with;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class FizzBuzzTest {

    private final FizzBuzz fizzBuzz = with(ImmutableMap.of(3, "Fizz", 5, "Buzz", 7, "Whizz"));

    @Test
    public void point_normal_count() throws Exception {
        assertThat(fizzBuzz.answer(1), is("1"));
        assertThat(fizzBuzz.answer(2), is("2"));
        assertThat(fizzBuzz.answer(3), is("Fizz"));
        assertThat(fizzBuzz.answer(4), is("4"));
        assertThat(fizzBuzz.answer(5), is("Buzz"));
        assertThat(fizzBuzz.answer(15), is("FizzBuzz"));
        assertThat(fizzBuzz.answer(7), is("Whizz"));
        assertThat(fizzBuzz.answer(21), is("FizzWhizz"));
        assertThat(fizzBuzz.answer(105), is("FizzBuzzWhizz"));
    }
}
