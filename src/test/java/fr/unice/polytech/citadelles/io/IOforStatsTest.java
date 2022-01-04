package fr.unice.polytech.citadelles.io;

import au.com.bytecode.opencsv.CSVReader;
import fr.unice.polytech.citadelles.card.DistrictCard;
import fr.unice.polytech.citadelles.enums.Color;
import fr.unice.polytech.citadelles.enums.DistrictName;
import fr.unice.polytech.citadelles.player.Player;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    void printStatsTest() throws IOException {
        List<List<Player>> winnersForEachGame = new ArrayList<>();
        Player p1 = new Player("Player 1");
        Player p2 = new Player("Player 2");
        Player p3 = new Player("Player 3");

        Map<Player, List<Integer>> res = iOforStats.readAndComputeStats(System.getProperty("user.dir")+ "/saveForTests/results.csv", p1, p2, p3);

        //System.err.println(res);
        double moyenne= 0;
        for(Player p : res.keySet()){
            moyenne+=res.get(p).get(1)/12000.0*3;
        }
        moyenne = moyenne/3;

        assertEquals(18.599833333333333, moyenne); // expected value computed with ms excel
    }

    @Test
    void printStatsTest2() throws IOException {
        List<List<Player>> winnersForEachGame = new ArrayList<>();
        Player p1 = new Player("Player 1");
        Player p2 = new Player("Player 2");
        Player p3 = new Player("Player 3");

        Map<Player, List<Integer>> res = iOforStats.readAndComputeStats(System.getProperty("user.dir")+ "/saveForTests/results2.csv", p1, p2, p3);

        double nbWinP1 = res.get(p1).get(0);
        double nbWinP2 = res.get(p2).get(0);
        double nbWinP3 = res.get(p3).get(0);

        double scoreP1 = res.get(p1).get(1)/3;
        double scoreP2 = res.get(p2).get(1)/3;
        double scoreP3 = res.get(p3).get(1)/3;

        assertEquals(2, nbWinP1);
        assertEquals(1, nbWinP2);
        assertEquals(0, nbWinP3);

        assertEquals(5, scoreP1);
        assertEquals(10, scoreP2);
        assertEquals(1, scoreP3);
    }

    @AfterAll
    static void restoreStreams() {
        System.setOut(originalOut);
    }
}