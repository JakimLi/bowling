package org.tennis;

import com.google.common.collect.ImmutableMap;

import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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
        Optional<String> winning = one(Rules::win, this::cheerWin);
        Optional<String> vanning = one(Rules::van, this::cheerVan);
        Optional<String> deuce = one(Rules::deuce, player -> deuce());

        return Stream.of(deuce, winning, vanning)
                .filter(Optional::isPresent)
                .findFirst()
                .map(Optional::get)
                .orElse(score());
    }

    private String deuce() {
        return "deuce";
    }

    private Optional<String> one(Predicate<Player> rule, Function<Player, String> cheer) {
        return this.players.values().stream()
                .filter(rule)
                .findFirst()
                .map(cheer);
    }

    private String cheerVan(Player player) {
        return String.format("advantage %s", player.name());
    }

    private String cheerWin(Player winner) {
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
