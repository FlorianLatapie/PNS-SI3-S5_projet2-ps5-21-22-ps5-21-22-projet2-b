package fr.unice.polytech.startingpoint;

import fr.unice.polytech.citadelles.GameEngine;
import fr.unice.polytech.citadelles.player.Player;
import fr.unice.polytech.citadelles.strategy.CompleteStrategy;
import fr.unice.polytech.citadelles.strategy.Strategy;
import fr.unice.polytech.citadelles.strategy.buildstrats.BuildMaxDistrictStrategy;
import fr.unice.polytech.citadelles.strategy.characterstrats.CharacterStrat;

import java.util.Random;

public class Main {
    public static void main(String... args) {
        Random random = new Random();

        Strategy buildMaxDistrictSrategy = new CompleteStrategy();
        Player p1 = new Player("Player 1", buildMaxDistrictSrategy);
        buildMaxDistrictSrategy.init(p1, random, new CharacterStrat(p1), new BuildMaxDistrictStrategy(p1));

        Strategy buildMaxDistrictSrategyp2 = new CompleteStrategy();
        Player p2 = new Player("Player 2", buildMaxDistrictSrategyp2);
        buildMaxDistrictSrategyp2.init(p2, random, new CharacterStrat(p2), new BuildMaxDistrictStrategy(p2));

        Strategy buildMaxDistrictSrategyp3 = new CompleteStrategy();
        Player p3 = new Player("Player 3", buildMaxDistrictSrategyp3);
        buildMaxDistrictSrategyp3.init(p3, random, new CharacterStrat(p3), new BuildMaxDistrictStrategy(p3));
        GameEngine ge = new GameEngine(new Random(), true, p1, p2, p3);

        ge.launchGame();
    }
}
