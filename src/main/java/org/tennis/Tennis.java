package org.tennis;

import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.google.common.collect.ImmutableMap.of;
import static com.google.common.collect.Maps.newLinkedHashMap;
import static org.tennis.Player.player;

class Tennis {
    private Map<String, Player> players = newLinkedHashMap();

    private Tennis(String server, String receiver) {
        Player theServer = player(server);
        Player theReceiver = player(receiver);

        theServer.against(theReceiver);

        players.putAll(of(server, theServer, receiver, theReceiver));
    }

    static Tennis game(String server, String receiver) {
        return new Tennis(server, receiver);
    }

    void wonPoint(String player) {
        players.get(player).score();
    }

    String getScore() {
        Optional<String> winning = someone(Rules::win, winner -> cheer("win for %s", winner));
        Optional<String> vanning = someone(Rules::van, vanner -> cheer("advantage %s", vanner));
        Optional<String> deuce = someone(Rules::deuce, player -> "deuce");

        return Stream.of(deuce, winning, vanning)
                .filter(Optional::isPresent)
                .findFirst()
                .map(Optional::get)
                .orElse(score());
    }

    private Optional<String> someone(Predicate<Player> rule,
                                     Function<Player, String> cheer) {
        return this.players.values().stream()
                .filter(rule)
                .findFirst()
                .map(cheer);
    }

    private String cheer(String message, Player player) {
        return String.format(message, player.name());
    }

    private String score() {
        return players.values().stream()
                .map(Player::getScore)
                .map(peculiar())
                .collect(Collectors.joining("-"));
    }

    private Function<Integer, String> peculiar() {
        return score -> of(0, "love", 1, "fifteen", 2, "thirty", 3, "forty")
                .getOrDefault(score, String.valueOf(score));
    }
}
