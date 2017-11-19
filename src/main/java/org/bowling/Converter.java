package org.bowling;

import com.google.common.collect.ImmutableMap;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static com.google.common.collect.ImmutableMap.of;
import static java.util.Optional.ofNullable;

class Converter {

    static int toNumber(int aChar) {
        return get((char) aChar).orElse(aChar - 48);
    }

    private static Optional<Integer> get(char aChar) {
        return ofNullable(of('-', 0, 'X', 10).get(aChar));
    }
}
