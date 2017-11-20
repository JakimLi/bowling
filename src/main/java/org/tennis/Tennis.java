package org.tennis;

import com.google.common.collect.ImmutableMap;

import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

import static com.google.common.collect.Maps.newLinkedHashMap;
import static org.tennis.Player.player;

class Tennis {
    private Map<String, Player> players = newLinkedHashMap();

    private Tennis(String server, String receiver) {
        Player playerServer = player(server);
        Player playerReceiver = player(receiver);

        playerServer.opponent(playerReceiver);
        playerReceiver.opponent(playerServer);

        players.put(server, playerServer);
        players.put(receiver, playerReceiver);
    }

    static Tennis game(String server, String receiver) {
        return new Tennis(server, receiver);
    }

    void wonPoint(String player) {
        players.get(player).score();
    }

    String getScore() {
        return oneWin().orElse(score());
    }

    private Optional<String> oneWin() {
        return this.players.values().stream()
                .filter(WinningRule::winner)
                .findFirst()
                .map(this::cheer);
    }

    private String cheer(Player winner) {
        return String.format("win for %s", winner.name());
    }

    private String score() {
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
