package fr.unice.polytech.startingpoint;

import fr.unice.polytech.citadelles.GameEngine;
import fr.unice.polytech.citadelles.io.IOforStats;
import fr.unice.polytech.citadelles.card.DeckOfCards;
import fr.unice.polytech.citadelles.player.Player;
import fr.unice.polytech.citadelles.strategy.CompleteStrategy;
import fr.unice.polytech.citadelles.strategy.Strategy;
import fr.unice.polytech.citadelles.strategy.buildstrats.BuildMaxDistrictStrategy;
import fr.unice.polytech.citadelles.strategy.characterstrats.CharacterStrat;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MainWithStatsOnly {
    public static void main(String... args) throws IOException {
        double numberOfGames = 1000;
        IOforStats io = new IOforStats();
        List<List<Player>> winnersOfEachGame = new ArrayList<>();

        GameEngine ge = null;

        for (int i = 0; i < numberOfGames; i++) {
            io.println("this game is the " + i + "th");

            ge = new GameEngine(io);
            winnersOfEachGame.add(ge.launchGame());

            io.println("this game is the " + i + "th");
        }

        io.saveAndPrintStats(winnersOfEachGame, ge.getListOfPlayers().toArray(new Player[0]));
    }
}
