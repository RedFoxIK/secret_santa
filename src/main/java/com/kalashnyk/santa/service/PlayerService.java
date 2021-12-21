package com.kalashnyk.santa.service;

import com.kalashnyk.santa.model.Player;
import com.kalashnyk.santa.model.PlayerRequest;
import com.kalashnyk.santa.model.PlayerWithEnemies;
import com.kalashnyk.santa.repository.PlayerRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class PlayerService {

    private final PlayerRepository playerRepository;

    public List<Player> getPlayers() {
        return playerRepository.finaAll();
    }

    public Map<Player, List<Player>> getAllWithEnemies() {
        return playerRepository.findAllWithEnemies();
    }

    public void addPlayers(List<PlayerRequest> players) {
        List<Player> playerList = players.stream()
                .map(p -> new Player(p.getFirstName(), p.getLastName(), p.getEmail()))
                .collect(Collectors.toList());
        playerRepository.addPlayers(playerList);
    }

    public void addEnemies(List<PlayerWithEnemies> playersWithEnemies) {
        playerRepository.addEnemiesForPlayer(playersWithEnemies);
    }
}
