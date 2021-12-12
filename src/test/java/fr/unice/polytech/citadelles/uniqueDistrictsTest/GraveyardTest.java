package fr.unice.polytech.citadelles.uniqueDistrictsTest;

import fr.unice.polytech.citadelles.GameEngine;
import fr.unice.polytech.citadelles.card.DistrictCard;
import fr.unice.polytech.citadelles.card.unique_districts.Graveyard;
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
import java.util.Random;

import static fr.unice.polytech.citadelles.uniqueDistrictsTest.HauntedQuarterTest.outContent;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class GraveyardTest {
    static List<DistrictCard> districtCards;
    static final PrintStream originalOut = System.out;

    @BeforeAll
    static void beforeAllSetup() {
        outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        districtCards = new ArrayList<>(List.of(
                new DistrictCard(Color.GREY, DistrictName.SHOP, 3),
                new DistrictCard(Color.GREY, DistrictName.MARKET, 2),
                new DistrictCard(Color.RED, DistrictName.TAVERN, 1),
                new DistrictCard(Color.BLUE, DistrictName.JAIL, 1)
        ));
    }

    @BeforeEach
    void setUp() {
        outContent.reset();
    }

    @AfterAll
    static void restoreStreams() {
        System.setOut(originalOut);
    }

    @Test
    void useUniqueDistrictPowerTest() {
        Player mockPlayer = mock(Player.class);
        when(mockPlayer.getName()).thenReturn("mockPlayer");
        when(mockPlayer.chooseToRepairDistrict()).thenReturn(null, new DistrictCard(Color.GREY, DistrictName.NONE, 1));
        when(mockPlayer.getDistrictCardsBuilt()).thenReturn(List.of(new DistrictCard(Color.GREY, DistrictName.NONE, 1)));

        GameEngine gameEngine = new GameEngine(new Random(), mockPlayer);
        Graveyard graveyard = new Graveyard(gameEngine);

        graveyard.useUniqueDistrictPower(mockPlayer);
        assertEquals("mockPlayer uses his Graveyard district  ..." + System.lineSeparator() +
                "mockPlayer cannot repair his districts []" + System.lineSeparator(), outContent.toString());

        outContent.reset();
        graveyard.useUniqueDistrictPower(mockPlayer);
        assertEquals("mockPlayer uses his Graveyard district  ..." + System.lineSeparator() +
                "mockPlayer repaired NONE(1 coin, GREY)" + System.lineSeparator() +
                "mockPlayer has the following district cards on the table (1) : [NONE(1 coin, GREY)]" + System.lineSeparator(), outContent.toString());
    }
}
