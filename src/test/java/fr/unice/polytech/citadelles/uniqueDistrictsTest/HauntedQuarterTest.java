package fr.unice.polytech.citadelles.uniqueDistrictsTest;

import fr.unice.polytech.citadelles.GameEngine;
import fr.unice.polytech.citadelles.IO;
import fr.unice.polytech.citadelles.card.DistrictCard;
import fr.unice.polytech.citadelles.card.unique_districts.HauntedQuarter;
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

class HauntedQuarterTest {
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
        Player player = new Player("Player");
        HauntedQuarter hq = new HauntedQuarter(ge,8,new DistrictCard(Color.PURPLE,DistrictName.HAUNTEDQUARTER,2),player);

        hq.useUniqueDistrictPower();

        assertEquals("Player uses his Haunted Quarter card power ..." + System.lineSeparator() +
                "Player change the color of HAUNTEDQUARTER(2 coins, BLUE) to BLUE"+ System.lineSeparator(), outContent.toString());
        outContent.reset();

        Random mockRandom = mock(Random.class);
        when(mockRandom.nextInt(anyInt(), anyInt())).thenReturn(0);

        List<DistrictCard> deck = List.of(
                new DistrictCard(Color.BLUE, DistrictName.NONE, 2),
                new DistrictCard(Color.RED, DistrictName.NONE, 2),
                new DistrictCard(Color.GREEN, DistrictName.NONE, 2),
                new DistrictCard(Color.YELLOW, DistrictName.NONE, 2));

        player = new Player("Player",deck);
        ge = new GameEngine(mockRandom, player);
        hq = new HauntedQuarter(ge,8,new DistrictCard(Color.PURPLE,DistrictName.HAUNTEDQUARTER,2),player);



        hq.useUniqueDistrictPower();
        assertEquals("Player uses his Haunted Quarter card power ..." + System.lineSeparator() +
                "Player change the color of HAUNTEDQUARTER(2 coins, BLUE) to BLUE"+ System.lineSeparator(), outContent.toString());
        outContent.reset();

        GameEngine mockGE = mock(GameEngine.class);
        when(mockGE.getIO()).thenReturn(new IO());
        when(mockGE.getRound()).thenReturn(9);

        hq = new HauntedQuarter(mockGE,8,new DistrictCard(Color.PURPLE,DistrictName.HAUNTEDQUARTER,2),player);

        hq.useUniqueDistrictPower();
        assertEquals("Player, unfortunately for you, the power of the card HAUNTEDQUARTER(2 coins, PURPLE) can't be used if you built it in the last round !" + System.lineSeparator(), outContent.toString());
    }

}
