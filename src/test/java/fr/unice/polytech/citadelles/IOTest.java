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
        for (int i = 0; i < 4; i++) {
            districtCards.add(new DistrictCard(i));
        }
        outContent.reset();
    }

    @Test
    public void printDistrictCardsInHandOfTest() {
        io.printDistrictCardsInHandOf(new Player(player1, districtCards));
        assertEquals("Player_1 has the following district cards in hand      : [DistrictCard{priceToBuild=0}, DistrictCard{priceToBuild=1}, DistrictCard{priceToBuild=2}, DistrictCard{priceToBuild=3}]\r\n", outContent.toString());
    }

    @Test
    public void printCoinsOfTest() {
        io.printCoinsOf(new Player(player1, districtCards));
        assertEquals("Player_1 has 2147483647 coins\r\n", outContent.toString());
    }

    @Test
    public void printCoinsOfTest2() {
        io.printCoinsOf(new Player(player1, districtCards, 1));
        assertEquals("Player_1 has 1 coin\r\n", outContent.toString());
    }

    @AfterAll
    static void restoreStreams() {
        System.setOut(originalOut);
    }
}