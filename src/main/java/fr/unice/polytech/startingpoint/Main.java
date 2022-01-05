package fr.unice.polytech.startingpoint;

import fr.unice.polytech.citadelles.GameEngine;
import fr.unice.polytech.citadelles.io.IO;

public class Main {
    public static void main(String... args) {
        IO io = new IO();

        GameEngine ge = new GameEngine(io);

        ge.launchGame();
    }
}
