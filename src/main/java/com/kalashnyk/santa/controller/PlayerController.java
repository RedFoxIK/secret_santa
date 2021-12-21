package com.kalashnyk.santa.controller;

import com.kalashnyk.santa.model.Player;
import com.kalashnyk.santa.model.PlayerRequest;
import com.kalashnyk.santa.model.PlayerWithEnemies;
import com.kalashnyk.santa.service.PlayerService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@AllArgsConstructor
public class PlayerController {

    private final PlayerService playerService;

    @GetMapping("/players")
    public List<Player> getPlayers() {
        return playerService.getPlayers();
    }

    @PostMapping("/players")
    public void addPlayers(@RequestBody List<PlayerRequest> players) {
        playerService.addPlayers(players);
    }

    @PostMapping("/enemies")
    public void addPlayerEnemies(@RequestBody List<PlayerWithEnemies> playersWithEnemies) {
        playerService.addEnemies(playersWithEnemies);
    }
}
