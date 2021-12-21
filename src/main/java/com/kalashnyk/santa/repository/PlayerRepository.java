package com.kalashnyk.santa.repository;

import com.kalashnyk.santa.model.Player;
import com.kalashnyk.santa.model.PlayerRequest;
import com.kalashnyk.santa.model.PlayerWithEnemies;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Repository
public class PlayerRepository {

    private final Map<Player, Set<Player>> playersWithEnemiesMap = new HashMap<>();

    public List<Player> finaAll() {
        return new ArrayList<>(playersWithEnemiesMap.keySet());
    }

    public Map<Player, List<Player>> findAllWithEnemies() {
        return playersWithEnemiesMap.entrySet().stream()
                .collect(Collectors.toMap(Map.Entry::getKey, e -> new ArrayList<>(e.getValue())));
    }

    public void addPlayers(List<Player> players) {
        players.forEach(p -> playersWithEnemiesMap.put(p, new HashSet<>()));
    }

    public void addEnemiesForPlayer(List<PlayerWithEnemies> playersWithEnemies) {
       playersWithEnemies.forEach(p -> playersWithEnemiesMap.put(convertToPlayer(p.getPlayer()),
               p.getEnemies().stream().map(this::convertToPlayer).collect(Collectors.toSet())));
    }

    private Player convertToPlayer(PlayerRequest playerRequest) {
        return new Player(playerRequest.getFirstName(), playerRequest.getLastName(), playerRequest.getEmail());
    }
}
