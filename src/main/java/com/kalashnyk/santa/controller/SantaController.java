package com.kalashnyk.santa.controller;

import com.kalashnyk.santa.service.SantaService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class SantaController {

    private final SantaService santaService;

    @PostMapping("/start")
    public void startGame() {
        santaService.playSanta();
    }
}
