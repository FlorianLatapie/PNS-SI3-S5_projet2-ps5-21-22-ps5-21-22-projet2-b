package fr.unice.polytech.citadelles.uniqueDistrictsTest;

import fr.unice.polytech.citadelles.GameEngine;
import fr.unice.polytech.citadelles.IO;
import fr.unice.polytech.citadelles.card.CharacterCard;
import fr.unice.polytech.citadelles.card.DistrictCard;
import fr.unice.polytech.citadelles.card.unique_districts.HauntedQuarter;
import fr.unice.polytech.citadelles.card.unique_districts.SchoolOfMagic;
import fr.unice.polytech.citadelles.enums.CharacterName;
import fr.unice.polytech.citadelles.enums.Color;
import fr.unice.polytech.citadelles.enums.DistrictName;
import fr.unice.polytech.citadelles.player.Player;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class SchoolOfMagicTest {
    static List<DistrictCard> districtCards;
    static ByteArrayOutputStream outContent;
    static final PrintStream originalOut = System.out;

    @BeforeAll
    static void beforeAllSetup() {
        outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
    }

    @BeforeAll
    static void init() {
        districtCards = new ArrayList<>();
        districtCards.add(new DistrictCard(Color.GREY, DistrictName.SHOP, 3));
        districtCards.add(new DistrictCard(Color.GREY, DistrictName.MARKET, 2));
        districtCards.add(new DistrictCard(Color.RED, DistrictName.TAVERN, 1));
        districtCards.add(new DistrictCard(Color.BLUE, DistrictName.JAIL, 1));
    }

    @AfterAll
    static void restoreStreams() {
        System.setOut(originalOut);
    }

    @Test
    void useUniqueDistrictPowerTest(){
        GameEngine ge = new GameEngine();
        Player mockPlayer = mock(Player.class);
        when(mockPlayer.getName()).thenReturn("Player");
        when(mockPlayer.getCharacterCard()).thenReturn(new CharacterCard(CharacterName.MERCHANT));
        SchoolOfMagic som = new SchoolOfMagic(ge);

        som.useUniqueDistrictPower(mockPlayer);
        assertEquals("Player uses his School of Magic card power ..." + System.lineSeparator() +
                "Player change the color of the School of Magic to GREEN" + System.lineSeparator() +
                "Player receive 1 coin because he is MERCHANT" + System.lineSeparator(), outContent.toString());

    }

}
