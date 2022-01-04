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
        Random random = new Random();
        IOforStats io = new IOforStats();
        List<List<Player>> winnersOfEachGame = new ArrayList<>();
        Player p1 = null, p2 = null, p3 = null;


        for (int i = 0; i < numberOfGames; i++) {
            io.println("this game is the " + i + "th");

            Strategy buildMaxDistrictSrategy = new CompleteStrategy(new CharacterStrat(), new BuildMaxDistrictStrategy());
            p1 = new Player("Player 1", buildMaxDistrictSrategy);

            Strategy buildMaxDistrictSrategyp2 = new CompleteStrategy(new CharacterStrat(), new BuildMaxDistrictStrategy());
            p2 = new Player("Player 2", buildMaxDistrictSrategyp2);

            Strategy buildMaxDistrictSrategyp3 = new CompleteStrategy(new CharacterStrat(), new BuildMaxDistrictStrategy());
            p3 = new Player("Player 3", buildMaxDistrictSrategyp3);

            GameEngine ge = new GameEngine(random, new DeckOfCards(random), io, true, p1, p2, p3);
            winnersOfEachGame.add(ge.launchGame());
            io.println("this game is the " + i + "th");
        }

        io.saveAndPrintStats(winnersOfEachGame, p1, p2, p3);
    }
}
