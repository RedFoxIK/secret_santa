package com.kalashnyk.santa.service;

import com.kalashnyk.santa.model.Player;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@Service
public class ShufflePlayerService {

    public List<Player> shufflePlayers(Map<Player, List<Player>> playersWithEnemies) {
        List<Player> shuffledPlayers = new ArrayList<>(playersWithEnemies.keySet());
        Collections.shuffle(shuffledPlayers);

        if (playersHasEnemies(playersWithEnemies)) {
            updatePlayersOrder(shuffledPlayers, playersWithEnemies);
        }
        return shuffledPlayers;
    }

    private boolean playersHasEnemies(Map<Player, List<Player>> playersWithEnemies) {
        return playersWithEnemies.values().stream()
                .anyMatch(enemies -> !enemies.isEmpty());
    }

    private void updatePlayersOrder(List<Player> shuffledPlayers, Map<Player, List<Player>> playersWithEnemies) {
        int modifications;
        int triesAmount = 0;
        int maxTries = shuffledPlayers.size();

        do {
            modifications = updatePlayersOrder(shuffledPlayers, playersWithEnemies, 0, 0);
            triesAmount++;
        } while (modifications > 0 && triesAmount < maxTries);

        assert triesAmount < maxTries : "Cannot be handled";
    }

    private int updatePlayersOrder(List<Player> players, Map<Player, List<Player>> playersWithEnemies,
                                   int index, int modifications) {

        if (index >= players.size() - 1) {
            return updateModifications(players, playersWithEnemies, players.get(0), index, modifications);
        }
        modifications = updateModifications(players, playersWithEnemies, players.get(index + 1), index, modifications);
        return updatePlayersOrder(players, playersWithEnemies, ++index, modifications);
    }

    private int updateModifications(List<Player> players, Map<Player, List<Player>> playersWithEnemies,
                                    Player nextPlayer, int index, int modifications) {

        Player currentPlayer = players.get(index);
        List<Player> currentEnemies = playersWithEnemies.get(currentPlayer);

        if (currentEnemies.contains(nextPlayer)) {
            changeOrder(players, index);
            return ++modifications;
        }
        return modifications;
    }

    private void changeOrder(List<Player> players, int index) {
        Player currentPlayer = players.get(index);
        int swapInx = index > 0 ? index - 1 : players.size() - 1;

        players.set(index, players.get(swapInx));
        players.set(swapInx, currentPlayer);
    }
}

