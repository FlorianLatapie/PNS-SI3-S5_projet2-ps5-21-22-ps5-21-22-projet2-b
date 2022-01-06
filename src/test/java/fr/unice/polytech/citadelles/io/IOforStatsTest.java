package fr.unice.polytech.citadelles.io;

import au.com.bytecode.opencsv.CSVReader;
import fr.unice.polytech.citadelles.player.Player;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class IOforStatsTest {
    IOforStats iOforStats;
    static ByteArrayOutputStream outContent;
    static ByteArrayOutputStream errContent;
    static final PrintStream originalOut = System.out;
    static final PrintStream originalErr = System.err;

    @BeforeAll
    static void baSetUp() {
        outContent = new ByteArrayOutputStream();
        errContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
        System.setErr(new PrintStream(errContent));
    }

    @BeforeEach
    void setUp() {
        iOforStats = new IOforStats();
        outContent.reset();
        errContent.reset();
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
    void errorlogTest() {
        iOforStats.errorlog("something");
        assertEquals("something" + System.lineSeparator(), errContent.toString());
    }

    @Test
    void printStatsTest() throws IOException {
        List<List<Player>> winnersForEachGame = new ArrayList<>();
        Player p1 = new Player("Player 1");
        Player p2 = new Player("Player 2");
        Player p3 = new Player("Player 3");

        Map<Player, List<Double>> res = iOforStats.readAndComputeStatsAndPrintThem(System.getProperty("user.dir") + "/saveForTests/results.csv", System.getProperty("user.dir") + "/saveForTests/resultsComputed.txt", p1, p2, p3);

        double moyenne = 0;
        for (Player p : res.keySet()) {
            moyenne += res.get(p).get(1) / 3000.0 * 3;
        }
        moyenne = moyenne / 3;

        assertEquals(18.496, moyenne); // expected value computed with ms excel
    }

    @Test
    void printStatsTest2() throws IOException {
        Player p1 = new Player("Player 1");
        Player p2 = new Player("Player 2");
        Player p3 = new Player("Player 3");

        Map<Player, List<Double>> res = iOforStats.readAndComputeStatsAndPrintThem(System.getProperty("user.dir") + "/saveForTests/results2.csv", System.getProperty("user.dir") + "/saveForTests/resultsComputed2.txt", p1, p2, p3);

        double nbWinP1 = res.get(p1).get(0);
        double nbWinP2 = res.get(p2).get(0);
        double nbWinP3 = res.get(p3).get(0);

        double scoreP1 = res.get(p1).get(1) / 3;
        double scoreP2 = res.get(p2).get(1) / 3;
        double scoreP3 = res.get(p3).get(1) / 3;

        assertEquals(2, nbWinP1);
        assertEquals(1, nbWinP2);
        assertEquals(0, nbWinP3);

        assertEquals(5, scoreP1);
        assertEquals(10, scoreP2);
        assertEquals(1, scoreP3);
    }

    @Test
    void printStatsTest3() throws IOException {
        Player p1 = new Player("Richard-Alphonse");
        Player p2 = new Player("Our AI");
        Player p3 = new Player("Random");

        String saveForTestsPath = System.getProperty("user.dir") + "/saveForTests/";
        String corruptedCSVPath = saveForTestsPath + "corrupted.csv";
        String corruptedTXTPath = saveForTestsPath + "corrupted.txt";

        File corruptedTxt = new File(corruptedTXTPath);
        corruptedTxt.delete();
        Map<Player, List<Double>> res = iOforStats.readAndComputeStatsAndPrintThem(corruptedCSVPath, corruptedTXTPath, p1, p2, p3);

        assertNull(res);
        assertEquals("", outContent.toString());
    }

    @Test
    void createCSVFileTest() throws IOException {
        String saveFolderPath = System.getProperty("user.dir") + "/save/";

        File saveFolder = new File(saveFolderPath);

        iOforStats.createFile("resultsForTests.csv", false);

        assertTrue(saveFolder.exists());
        assertTrue(saveFolder.list().length >= 1);

        File resultsForTestsFile = new File(saveFolderPath + "resultsForTests.csv");
        resultsForTestsFile.delete();
    }

    @Test
    void createCSVFileTest2() throws IOException {
        String saveFolderPath = System.getProperty("user.dir") + "/save/";

        File saveFolder = new File(saveFolderPath);

        iOforStats.createFile("resultsForTests.csv", true);

        assertTrue(saveFolder.exists());
        assertTrue(saveFolder.list().length >= 1);

        File resultsForTestsFile = new File(saveFolderPath + "resultsForTests.csv");
        resultsForTestsFile.delete();
    }

    @Test
    void appendStatsToCSVTest() throws IOException {
        String csvFilePath = System.getProperty("user.dir") + "/saveForTests/results3.csv";
        File results3csv = new File(csvFilePath);
        results3csv.delete();
        results3csv.createNewFile();

        List<List<Player>> winnersOfEachGame = new ArrayList<>();
        Player p1, p2;
        p1 = new Player("P1");
        p1.addPoints(10);
        p2 = new Player("P2");
        p2.addPoints(9);
        winnersOfEachGame.add(List.of(p1, p2));

        iOforStats.appendStatsToCSV(winnersOfEachGame, csvFilePath);

        CSVReader reader = new CSVReader(new FileReader(csvFilePath), ';', '"', 0);
        List<String[]> expected = new ArrayList<>();
        expected.add(new String[]{"P1", "1", "10", "10", "2", "0.0", "0", "0", "0", "0", "0", "0"});
        expected.add(new String[]{"P2", "0", "9", "9", "2", "0.0", "0", "0", "0", "0", "0", "0"});

        List<String[]> readAll = reader.readAll();
        assertEquals(2, readAll.size());

        for (int i = 0; i < readAll.size(); i++) {
            for (int j = 0; j < readAll.get(i).length; j++) {
                assertEquals(expected.get(i)[j], readAll.get(i)[j]);
            }
        }
    }

    @AfterAll
    static void restoreStreams() {
        System.setOut(originalOut);
        System.setErr(originalErr);
    }
}