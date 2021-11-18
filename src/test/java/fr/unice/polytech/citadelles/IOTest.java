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
    void printDistrictCardsInHandOfTest() {
        io.printDistrictCardsInHandOf(new Player(player1, districtCards));
        assertEquals("Player_1 has the following district cards in hand      : [DistrictCard{priceToBuild=1, color=GREY, name=GreyHouse}, DistrictCard{priceToBuild=2, color=GREY, name=GreyHouse}, DistrictCard{priceToBuild=3, color=GREY, name=GreyHouse}, DistrictCard{priceToBuild=4, color=GREY, name=GreyHouse}]" + System.lineSeparator(), outContent.toString());
    }

    @Test
    void printDistrictCardsBuiltByTest() {
        Player player2 = new Player("player_2", districtCards);
        List<DistrictCard> districtCardsBuilt = new ArrayList<>();
        for (int i = 1; i < 5; i++) {
            districtCardsBuilt.add(new DistrictCard(i));
        }
        player2.setDistrictCardsBuilt(districtCardsBuilt);

        io.printDistrictCardsBuiltBy(player2);
        assertEquals("player_2 has the following district cards on the table : [DistrictCard{priceToBuild=1, color=GREY, name=GreyHouse}, DistrictCard{priceToBuild=2, color=GREY, name=GreyHouse}, DistrictCard{priceToBuild=3, color=GREY, name=GreyHouse}, DistrictCard{priceToBuild=4, color=GREY, name=GreyHouse}]" + System.lineSeparator(), outContent.toString());
    }

    @Test
    void printDistrictCardsBuiltByTest2() {
        Player player2 = new Player("player_2", districtCards);
        List<DistrictCard> districtCardsBuilt = new ArrayList<>();
        player2.setDistrictCardsBuilt(districtCardsBuilt);
        io.printDistrictCardsBuiltBy(player2);
        assertEquals("player_2 has the following district cards on the table : []" + System.lineSeparator(), outContent.toString());
    }

    @Test
    void printCoinsOfTest() {
        Player playerTest = new Player(player1, districtCards);
        io.printCoinsOf(playerTest);
        assertEquals("Player_1 has " + playerTest.getCoins() + " coins" + System.lineSeparator(), outContent.toString());
    }

    @Test
    void printCoinsOfTest2() {
        io.printCoinsOf(new Player(player1, districtCards, 1));
        assertEquals("Player_1 has 1 coin" + System.lineSeparator(), outContent.toString());
    }

    @Test
    void printSeparatorTest() {
        io.printSeparator("test");
        assertEquals("----------------------------------------------------------------------------- test -----------------------------------------------------------------------------" + System.lineSeparator() + System.lineSeparator(), outContent.toString());
    }

    @Test
    void printlnTest() {
        io.println("test");
        assertEquals("test" + System.lineSeparator(), outContent.toString());
    }

    @Test
    void printWinnerTest() {
        ArrayList<Player> players = new ArrayList<>();
        for (int i = 1; i < 5; i++) {
            players.add(new Player("Player_" + i, districtCards));
        }
        players.get(0).buildDistrictCardsInHand(districtCards.get(0));
        io.printWinner(players);
        assertEquals("The winners podium !" + System.lineSeparator() + "Player_1 with 1 pts" + System.lineSeparator() + "Player_2 with 0 pts" + System.lineSeparator() + "Player_3 with 0 pts" + System.lineSeparator() + "Player_4 with 0 pts" + System.lineSeparator(), outContent.toString());
    }

    @AfterAll
    static void restoreStreams() {
        System.setOut(originalOut);
    }
}