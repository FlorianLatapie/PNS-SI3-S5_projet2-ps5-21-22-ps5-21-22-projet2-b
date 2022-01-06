package fr.unice.polytech.startingpoint;

import fr.unice.polytech.citadelles.GameEngine;
import fr.unice.polytech.citadelles.io.IO;
import fr.unice.polytech.citadelles.io.IOforStats;
import fr.unice.polytech.citadelles.player.Player;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MainWithStatsOnly {
    public static void main(String... args) throws IOException {
        double numberOfGames = 1000;

        IO io;
        if (args.length ==0 ){
            io = new IOforStats();
        } else {
            io = new IO();
        }

        List<List<Player>> winnersOfEachGame = new ArrayList<>();

        GameEngine ge = null;

        for (int i = 0; i < numberOfGames; i++) {
            io.println("this game is the " + i + "th");

            ge = new GameEngine(io);
            winnersOfEachGame.add(ge.launchGame());

            io.println("this game is the " + i + "th");
        }

        IOforStats io2 = new IOforStats();
        io2.saveAndPrintStats(winnersOfEachGame, ge.getListOfPlayers().toArray(new Player[0]));
    }
}
