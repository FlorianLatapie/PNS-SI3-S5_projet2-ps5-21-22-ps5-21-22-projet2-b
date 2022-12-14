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
            districtCards.add(new DistrictCard(Color.GREY, DistrictName.NONE, i));
        }
        outContent.reset();
    }

    @Test
    void printDistrictCardsInHandOfTest() {
        io.printDistrictCardsInHandOf(new Player(player1, districtCards));
        assertEquals("Player_1 has the following district cards in hand          : [NONE(1 coin, GREY), NONE(2 coins, GREY), NONE(3 coins, GREY), NONE(4 coins, GREY)]" + System.lineSeparator(), outContent.toString());
    }

    @Test
    void printDistrictCardsBuiltByTest() {
        Player player2 = new Player("player_2", districtCards);
        List<DistrictCard> districtCardsBuilt = new ArrayList<>();
        for (int i = 1; i < 5; i++) {
            districtCardsBuilt.add(new DistrictCard(Color.GREY, DistrictName.NONE, i));
        }
        player2.setDistrictCardsBuilt(districtCardsBuilt);

        io.printDistrictCardsBuiltBy(player2);
        assertEquals("player_2 has the following district cards on the table (4) : [NONE(1 coin, GREY), NONE(2 coins, GREY), NONE(3 coins, GREY), NONE(4 coins, GREY)]" + System.lineSeparator(), outContent.toString());
    }

    @Test
    void printDistrictCardsBuiltByTest2() {
        Player player2 = new Player("player_2", districtCards);
        List<DistrictCard> districtCardsBuilt = new ArrayList<>();
        player2.setDistrictCardsBuilt(districtCardsBuilt);
        io.printDistrictCardsBuiltBy(player2);
        assertEquals("player_2 has the following district cards on the table (0) : []" + System.lineSeparator(), outContent.toString());
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
        assertEquals("--------------------------------------------------------------------- test ---------------------------------------------------------------------" + System.lineSeparator() + System.lineSeparator(), outContent.toString());
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
            players.add(new Player("Player_" + i, districtCards, 10));
        }
        players.get(0).buildDistrictCardsInHand(districtCards.get(0));
        players.get(0).buildDistrictCardsInHand(districtCards.get(1));

        players.get(1).buildDistrictCardsInHand(districtCards.get(0));

        io.printWinner(players);
        assertEquals("--------------------------------------------------------------------- The winners podium ! ---------------------------------------------------------------------"
                        + System.lineSeparator() + System.lineSeparator()
                        + "Player_1 with 3 points" + System.lineSeparator()
                        + "Player_2 with 1 point" + System.lineSeparator()
                        + "Player_3 with 0 points" + System.lineSeparator()
                        + "Player_4 with 0 points" + System.lineSeparator(),
                outContent.toString());
    }

    @AfterAll
    static void restoreStreams() {
        System.setOut(originalOut);
    }
}