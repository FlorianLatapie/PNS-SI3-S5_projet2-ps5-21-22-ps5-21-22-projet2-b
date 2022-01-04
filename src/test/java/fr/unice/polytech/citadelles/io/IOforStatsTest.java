package fr.unice.polytech.citadelles.io;

import fr.unice.polytech.citadelles.card.DistrictCard;
import fr.unice.polytech.citadelles.enums.Color;
import fr.unice.polytech.citadelles.enums.DistrictName;
import fr.unice.polytech.citadelles.player.Player;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class IOforStatsTest {
    IOforStats iOforStats;
    static ByteArrayOutputStream outContent;
    static final PrintStream originalOut = System.out;

    @BeforeAll
    static void baSetUp() {
        outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
    }

    @BeforeEach
    void setUp() {
        iOforStats = new IOforStats();
        outContent.reset();
    }

    @Test
    void printlnTest() {
        iOforStats.println("something");
        assertEquals("", outContent.toString());
    }

    @Test
    void logTest() {
        iOforStats.log("something");
        assertEquals("something" + System.lineSeparator(), outContent.toString());
    }

    @Test
    void printStatsTest() {
        List<List<Player>> winnersForEachGame = new ArrayList<>();
        Player p,p2;

        /*p2 = new Player("p2");
        p2.addPoints(10);
        p = new Player("p");
        p.addPoints(5);
        winnersForEachGame.add(List.of(p2, p));*/

        p = new Player("p");
        p.addPoints(10);
        p2 = new Player("p2");
        p2.addPoints(1);
        winnersForEachGame.add(List.of(p, p2));

        p2 = new Player("p2");
        p2.addPoints(19);
        p = new Player("p");
        p.addPoints(0);
        winnersForEachGame.add(List.of(p2, p));

        p2 = new Player("p2");
        p2.addPoints(10);
        p = new Player("p");
        p.addPoints(5);
        winnersForEachGame.add(List.of(p2, p));

        for (int i = 0; i < winnersForEachGame.size(); i++) {
            System.err.println("game #" + i);
            List<Player> winnersOfThisGame = winnersForEachGame.get(i);
            for (int j = 0; j < winnersOfThisGame.size(); j++) {
                Player pj = winnersOfThisGame.get(j);
                System.err.println(pj.getName() + " " + pj.getNbOfPoints());
            }

        }

        iOforStats.printStats(winnersForEachGame, winnersForEachGame.size(), p, p2);

        assertEquals("p has won 1 games, average : 5.0 points with strategy : CompleteStrategy{characterStrat=random Character Strategy, buildStrat=random Build Strategy}" + System.lineSeparator() +
                "p2 has won 2 games, average : 10.0 points with strategy : CompleteStrategy{characterStrat=random Character Strategy, buildStrat=random Build Strategy}" + System.lineSeparator(), outContent.toString());
    }

    @AfterAll
    static void restoreStreams() {
        System.setOut(originalOut);
    }
}