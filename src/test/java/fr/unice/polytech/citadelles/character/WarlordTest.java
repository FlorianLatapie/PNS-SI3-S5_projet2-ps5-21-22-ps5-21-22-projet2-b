package fr.unice.polytech.citadelles.character;

import fr.unice.polytech.citadelles.GameEngine;
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
import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

class WarlordTest {
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

    @BeforeEach
    void setUp() {
        outContent.reset();
    }

    @Test
    void warlordRemoveDistrictCardOfPlayerTest() {
        Player warlord = new Player("Player_1", new ArrayList<>(), 100);
        Player player1 = new Player("Player_2");
        GameEngine ge = new GameEngine(new Random(), warlord, player1);

        player1.getDistrictCardsBuilt().add(new DistrictCard(Color.BLUE, DistrictName.CHURCH, 1));
        List<DistrictCard> districtCardList = new ArrayList<>();
        districtCardList.add(new DistrictCard(Color.BLUE, DistrictName.CHURCH, 1));
        new Warlord(ge).warlordRemoveDistrictCardOfPlayer(warlord, player1);
        assertNotEquals(districtCardList, player1.getDistrictCardsBuilt());
        assertEquals("Player_1 destroys CHURCH of Player_2. It costs him: 0 gold" + System.lineSeparator(), outContent.toString());
    }

    @Test
    void canWarlordDestroyACardFromCharacterTest() {

        Player warlord = new Player("Player_1", new ArrayList<DistrictCard>(), 2);
        Player player1 = new Player("Player_2");

        GameEngine ge = new GameEngine(new Random(), warlord, player1);

        player1.getDistrictCardsBuilt().add(new DistrictCard(Color.BLUE, DistrictName.CHURCH, 1));
        List<Player> players = new ArrayList<>();
        players.add(player1);

        Warlord warlordEngine = new Warlord(ge);
        assertEquals(players, warlordEngine.canWarlordDestroyACardFromPlayers(warlord, players));
        player1.getDistrictCardsBuilt().remove(0);
        assertNotEquals(players, warlordEngine.canWarlordDestroyACardFromPlayers(warlord, players));
        player1.getDistrictCardsBuilt().add(new DistrictCard(Color.BLUE, DistrictName.CHURCH, 3));
        assertEquals(players, warlordEngine.canWarlordDestroyACardFromPlayers(warlord, players));
        player1.getDistrictCardsBuilt().add(new DistrictCard(Color.BLUE, DistrictName.MONASTERY, 2));
        assertEquals(players, warlordEngine.canWarlordDestroyACardFromPlayers(warlord, players));
    }
}
