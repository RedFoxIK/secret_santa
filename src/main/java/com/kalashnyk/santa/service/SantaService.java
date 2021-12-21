package com.kalashnyk.santa.service;

import com.kalashnyk.santa.model.Player;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
@AllArgsConstructor
public class SantaService {

    private final EmailService emailService;
    private final PlayerService playerService;
    private final ShufflePlayerService shufflePlayerService;

    public void playSanta() {
        Map<Player, List<Player>> playersWithEnemies = playerService.getAllWithEnemies();
        List<Player> allPlayers = shufflePlayerService.shufflePlayers(playersWithEnemies);
        sendEmails(allPlayers);
    }

    private void sendEmails(List<Player> players) {
        assert players.size() >= 2 : "Too small users amount";

        for (int i = 1; i < players.size(); i++) {
            emailService.sendEmail(players.get(i -1), players.get(i));
        }
        emailService.sendEmail(players.get(players.size() - 1), players.get(0));
    }
}
