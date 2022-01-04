package fr.unice.polytech.startingpoint;

import fr.unice.polytech.citadelles.GameEngine;
import fr.unice.polytech.citadelles.IO;
import fr.unice.polytech.citadelles.IOforStats;
import fr.unice.polytech.citadelles.card.DeckOfCards;
import fr.unice.polytech.citadelles.player.Player;
import fr.unice.polytech.citadelles.strategy.CompleteStrategy;
import fr.unice.polytech.citadelles.strategy.Strategy;
import fr.unice.polytech.citadelles.strategy.buildstrats.BuildMaxDistrictStrategy;
import fr.unice.polytech.citadelles.strategy.characterstrats.CharacterStrat;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MainWithStatsOnly {
    public static void main(String... args) {
        Random random = new Random();
        IOforStats io = new IOforStats();
        //IO io = new IO();
        List<List<Player>> winnersOfEachGame = new ArrayList<>();
        Player p1 = null, p2 = null, p3 = null;


        for (int i = 0; i < 1000; i++) {
            io.println("this game is the " + i + "th");

            Strategy buildMaxDistrictSrategy = new CompleteStrategy();
            p1 = new Player("Player 1", buildMaxDistrictSrategy);
            buildMaxDistrictSrategy.init(p1, random, new CharacterStrat(p1), new BuildMaxDistrictStrategy(p1));


            Strategy buildMaxDistrictSrategyp2 = new CompleteStrategy();
            p2 = new Player("Player 2", buildMaxDistrictSrategyp2);
            buildMaxDistrictSrategyp2.init(p2, random, new CharacterStrat(p2), new BuildMaxDistrictStrategy(p2));

            Strategy buildMaxDistrictSrategyp3 = new CompleteStrategy();
            p3 = new Player("Player 3", buildMaxDistrictSrategyp3);
            buildMaxDistrictSrategyp3.init(p3, random, new CharacterStrat(p3), new BuildMaxDistrictStrategy(p3));

            GameEngine ge = new GameEngine(random, new DeckOfCards(random), io, true, p1, p2, p3);
            winnersOfEachGame.add(ge.launchGame());
            io.println("this game is the " + i + "th");
        }

        io.printStats(List.of(p1,p2,p3), winnersOfEachGame);
    }
}
