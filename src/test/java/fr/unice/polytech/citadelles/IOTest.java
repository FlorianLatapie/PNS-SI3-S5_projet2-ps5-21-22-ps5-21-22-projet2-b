package fr.unice.polytech.citadelles;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class IOTest {
    String player1 = "Player_1";
    IO io;
    List<DistrictCard> districtCards;
    static ByteArrayOutputStream outContent;
    static final PrintStream originalOut = System.out;

    @BeforeAll
    static void baSetUp() {
        outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
    }

    @BeforeEach
    void setUp() {
        io = new IO();
        districtCards = new ArrayList<>();
        for (int i = 1; i < 5; i++) {
            districtCards.add(new DistrictCard(i));
        }
        outContent.reset();
    }

    @Test
    public void printDistrictCardsInHandOfTest() {
        io.printDistrictCardsInHandOf(new Player(player1, districtCards));
        assertEquals("Player_1 has the following district cards in hand      : [DistrictCard{priceToBuild=1}, DistrictCard{priceToBuild=2}, DistrictCard{priceToBuild=3}, DistrictCard{priceToBuild=4}]\n", outContent.toString());
    }

    @Test
    public void printDistrictCardsBuiltByTest() {
        Player player2 = new Player("player_2", districtCards);
        List<DistrictCard> districtCardsBuilt = new ArrayList<>();
        for (int i = 1; i < 5; i++) {
            districtCardsBuilt.add(new DistrictCard(i));
        }
        player2.setDistrictCardsBuilt(districtCardsBuilt);

        io.printDistrictCardsBuiltBy(player2);
        assertEquals("player_2 has the following district cards on the table : [DistrictCard{priceToBuild=1}, DistrictCard{priceToBuild=2}, DistrictCard{priceToBuild=3}, DistrictCard{priceToBuild=4}]\n", outContent.toString());
    }

    @Test
    public void printDistrictCardsBuiltByTest2() {
        Player player2 = new Player("player_2", districtCards);
        List<DistrictCard> districtCardsBuilt = new ArrayList<>();
        player2.setDistrictCardsBuilt(districtCardsBuilt);
        io.printDistrictCardsBuiltBy(player2);
        assertEquals("player_2 has the following district cards on the table : []\n", outContent.toString());
    }

    @Test
    public void printCoinsOfTest() {
        io.printCoinsOf(new Player(player1, districtCards));
        assertEquals("Player_1 has 2147483647 coins\n", outContent.toString());
    }

    @Test
    public void printCoinsOfTest2() {
        io.printCoinsOf(new Player(player1, districtCards, 1));
        assertEquals("Player_1 has 1 coin\n", outContent.toString());
    }

    @Test
    public void printSeparatorTest() {
        io.printSeparator("test");
        assertEquals("----------------------------------------------------------------------------- test -----------------------------------------------------------------------------\n\n", outContent.toString());
    }

    @Test
    public void printlnTest() {
        io.println("test");
        assertEquals("test\n", outContent.toString());
    }

    @AfterAll
    static void restoreStreams() {
        System.setOut(originalOut);
    }
}