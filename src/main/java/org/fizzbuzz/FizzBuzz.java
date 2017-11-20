package org.fizzbuzz;

import java.util.Map;

import static com.google.common.collect.Maps.newHashMap;

class FizzBuzz {

    private Map<Integer, String> config = newHashMap();

    private FizzBuzz(Map<Integer, String> config) {
        this.config = config;
    }

    static FizzBuzz with(Map<Integer, String> config) {
        return new FizzBuzz(config);
    }

    String answer(int count) {
        return config.entrySet().stream()
                .filter(entry -> count % entry.getKey() == 0)
                .map(Map.Entry::getValue)
                .reduce(String::concat)
                .orElse(String.valueOf(count));
    }
}
