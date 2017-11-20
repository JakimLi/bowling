package org.tennis;

import com.google.common.collect.ImmutableMap;

import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import static com.google.common.collect.Maps.newLinkedHashMap;
import static org.tennis.Player.player;

class Tennis {
    private Map<String, Player> players = newLinkedHashMap();

    private Tennis(String server, String receiver) {
        players.put(server, player(server));
        players.put(receiver, player(receiver));
    }

    static Tennis game(String server, String receiver) {
        return new Tennis(server, receiver);
    }

    void wonPoint(String player) {
        players.get(player).score();
    }

    String getScore() {
        return players.values().stream()
                .map(Player::getScore)
                .map(peculiar())
                .collect(Collectors.joining("-"));
    }

    private Function<Integer, String> peculiar() {
        return score -> ImmutableMap.of(0, "love", 1, "fifteen", 2, "thirty", 3, "forty")
                .getOrDefault(score, String.valueOf(score));
    }
}
