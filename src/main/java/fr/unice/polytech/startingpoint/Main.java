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

        Strategy buildMaxDistrictSrategy = new CompleteStrategy(new CharacterStrat(), new BuildMaxDistrictStrategy());
        Player p1 = new Player("Player 1", buildMaxDistrictSrategy);

        Strategy buildMaxDistrictSrategyp2 = new CompleteStrategy(new CharacterStrat(), new BuildMaxDistrictStrategy());
        Player p2 = new Player("Player 2", buildMaxDistrictSrategyp2);

        Strategy buildMaxDistrictSrategyp3 = new CompleteStrategy(new CharacterStrat(), new BuildMaxDistrictStrategy());
        Player p3 = new Player("Player 3", buildMaxDistrictSrategyp3);
        GameEngine ge = new GameEngine(new Random(), true, p1, p2, p3);

        ge.launchGame();
    }
}
