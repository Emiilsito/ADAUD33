package com.example.Ej2;

import com.example.Ej2.domain.Arcade;
import com.example.Ej2.domain.Cabinet;
import com.example.Ej2.domain.Game;
import com.example.Ej2.domain.Player;
import com.example.Ej2.dto.TopGameDto;
import com.example.Ej2.service.ArcadeService;
import com.example.Ej2.service.CabinetService;
import com.example.Ej2.service.GameService;
import com.example.Ej2.service.PlayerService;

import java.time.LocalDateTime;
import java.util.List;

public class MainConsultas {
    public static void main(String[] args) {
        ArcadeService arcadeService = new ArcadeService();
        GameService gameService = new GameService();
        CabinetService cabinetService = new CabinetService();
        PlayerService playerService = new PlayerService();
        LocalDateTime sinceDate = LocalDateTime.of(2025, 11, 1, 1, 1);

        System.out.println("[ Consulta 1: ]");
        List<Arcade> arcades = arcadeService.getArcadeByName();
        arcades.forEach(System.out::println);

        System.out.println("[ Consulta 2: ]");
        List<TopGameDto> games = gameService.getTopGamesByMatches();
        games.forEach(System.out::println);

        System.out.println("[ Consulta 4: ]");
        List<Cabinet> cabinets = cabinetService.getCabinetsActiveByGenre();
        cabinets.forEach(System.out::println);

        System.out.println("[ Consulta 5: ]");
        List<Player> players = playerService.findPlayersWithInactiveCardAndRecentMatches(sinceDate);
        System.out.println("Jugadores encontrados: " + players.size());
        players.forEach(System.out::println);
    }
}
