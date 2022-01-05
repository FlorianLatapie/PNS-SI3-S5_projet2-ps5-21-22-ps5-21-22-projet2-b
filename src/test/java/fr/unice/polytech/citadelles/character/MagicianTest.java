package fr.unice.polytech.citadelles.character;

import fr.unice.polytech.citadelles.GameEngine;
import fr.unice.polytech.citadelles.card.DistrictCard;
import fr.unice.polytech.citadelles.enums.Color;
import fr.unice.polytech.citadelles.enums.DistrictName;
import fr.unice.polytech.citadelles.player.Player;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.List;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class MagicianTest {
    static List<DistrictCard> districtCards;
    static ByteArrayOutputStream outContent;
    static final PrintStream originalOut = System.out;

    @BeforeAll
    static void beforeAllSetup() {
        outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
    }

    @AfterAll
    static void restoreStreams() {
        System.setOut(originalOut);
    }

    @Test
    void changeCardMagicianTest() {
        Random mockRandom = mock(Random.class);
        when(mockRandom.nextInt(anyInt(), anyInt())).thenReturn(0);
        when(mockRandom.nextInt(anyInt())).thenReturn(0);

        List<DistrictCard> deck = List.of(new DistrictCard(Color.GREY, DistrictName.NONE, 1), new DistrictCard(Color.BLUE, DistrictName.CHURCH, 2));

        Player player = new Player("Player", deck, 10, mockRandom);

        GameEngine ge = new GameEngine(mockRandom, player);
        Magician magicianEngine = new Magician(ge);
        magicianEngine.changeCardMagician(player);

        assertEquals("Player choose to change the card : NONE(1 coin, GREY)" + System.lineSeparator() +
                "Player choose to draw a card" + System.lineSeparator() +
                "Player draws: TEMPLE(1 coin, BLUE)" + System.lineSeparator(), outContent.toString());
    }

    @Test
    void giveDeckToMagicianTest() {
        Random mockRandom = mock(Random.class);
        when(mockRandom.nextInt(anyInt(), anyInt())).thenReturn(0);
        when(mockRandom.nextInt(anyInt())).thenReturn(0);


        List<DistrictCard> deck = List.of(new DistrictCard(Color.GREY, DistrictName.NONE, 1));
        List<DistrictCard> deck2 = List.of(new DistrictCard(Color.BLUE, DistrictName.CHURCH, 2));

        Player player = new Player("Player 1", deck, 10, mockRandom);
        Player player2 = new Player("Player 2", deck2, 10, mockRandom);

        GameEngine ge = new GameEngine(mockRandom, player);
        Magician magicianEngine = new Magician(ge);

        magicianEngine.giveDeckToMagician(player, player2);

        assertEquals(deck2, player.getDistrictCardsInHand());
        assertEquals(deck, player2.getDistrictCardsInHand());
    }
}
