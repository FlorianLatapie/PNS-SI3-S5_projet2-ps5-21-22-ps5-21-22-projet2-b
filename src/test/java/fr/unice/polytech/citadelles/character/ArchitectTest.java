package fr.unice.polytech.citadelles.character;

import fr.unice.polytech.citadelles.GameEngine;
import fr.unice.polytech.citadelles.card.DeckOfCards;
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
import java.util.List;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * @author Florian Latapie
 */
public class ArchitectTest {
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
    void give2DistrictCardsToArchitectTest() {
        Player player = new Player("Player");

        DeckOfCards deck = mock(DeckOfCards.class);
        when(deck.getRandomDistrictCard()).thenReturn(
                new DistrictCard(Color.GREEN, DistrictName.TAVERN, 2),
                new DistrictCard(Color.BLUE, DistrictName.CHURCH, 3),
                new DistrictCard(Color.RED, DistrictName.JAIL, 1),
                null
        );

        GameEngine ge = new GameEngine(new Random(), player);
        ge.setDeckOfCards(deck);

        Architect architectEngine = new Architect(ge);
        architectEngine.give2DistrictCardsToArchitect(player);

        assertEquals(List.of(
                        new DistrictCard(Color.GREEN, DistrictName.TAVERN, 2),
                        new DistrictCard(Color.BLUE, DistrictName.CHURCH, 3)),
                player.getDistrictCardsInHand());
        assertEquals("", outContent.toString());
        outContent.reset();

        architectEngine.give2DistrictCardsToArchitect(player);
        assertEquals(List.of(
                        new DistrictCard(Color.GREEN, DistrictName.TAVERN, 2),
                        new DistrictCard(Color.BLUE, DistrictName.CHURCH, 3),
                        new DistrictCard(Color.RED, DistrictName.JAIL, 1)),
                player.getDistrictCardsInHand());
        assertEquals("Player can't draw a district card because the deck is empty." + System.lineSeparator(), outContent.toString());
        outContent.reset();

        architectEngine.give2DistrictCardsToArchitect(player);
        assertEquals(List.of(
                        new DistrictCard(Color.GREEN, DistrictName.TAVERN, 2),
                        new DistrictCard(Color.BLUE, DistrictName.CHURCH, 3),
                        new DistrictCard(Color.RED, DistrictName.JAIL, 1)),
                player.getDistrictCardsInHand());
        assertEquals("Player can't draw a district card because the deck is empty." + System.lineSeparator(), outContent.toString());
        outContent.reset();
    }
}
