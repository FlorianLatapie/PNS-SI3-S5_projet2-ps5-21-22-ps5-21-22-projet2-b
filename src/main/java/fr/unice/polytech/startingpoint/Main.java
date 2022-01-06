package fr.unice.polytech.startingpoint;

import fr.unice.polytech.citadelles.GameEngine;
import fr.unice.polytech.citadelles.io.IO;
import fr.unice.polytech.citadelles.io.IOforStats;

public class Main {
    public static void main(String... args) {
        IO io;
        if (args.length == 0) {
            io = new IO();
        } else {
            io = new IOforStats();
        }

        GameEngine ge = new GameEngine(io);

        ge.launchGame();
    }
}
