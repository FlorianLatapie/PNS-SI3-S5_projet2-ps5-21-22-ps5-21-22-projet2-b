package fr.unice.polytech.citadelles.uniqueDistricts;

import fr.unice.polytech.citadelles.GameEngine;
import fr.unice.polytech.citadelles.card.DistrictCard;
import fr.unice.polytech.citadelles.card.unique_districts.Laboratory;
import fr.unice.polytech.citadelles.enums.Color;
import fr.unice.polytech.citadelles.enums.DistrictName;
import fr.unice.polytech.citadelles.player.Player;
import fr.unice.polytech.citadelles.strategy.CompleteStrategy;
import fr.unice.polytech.citadelles.strategy.buildstrats.BuildMaxDistrictStrategy;
import fr.unice.polytech.citadelles.strategy.characterstrats.CharacterStrat;
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

class LaboratoryTest {
    static List<DistrictCard> districtCards;

    static ByteArrayOutputStream outContent;
    static final PrintStream originalOut = System.out;

    @AfterAll
    static void restoreStreams() {
        System.setOut(originalOut);
    }

    @BeforeEach
    void setUp() {
        outContent.reset();
    }

    @BeforeAll
    static void init() {
        outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
        districtCards = new ArrayList<>();
        districtCards.add(new DistrictCard(Color.GREY, DistrictName.SHOP, 3));
        districtCards.add(new DistrictCard(Color.GREY, DistrictName.MARKET, 2));
        districtCards.add(new DistrictCard(Color.RED, DistrictName.TAVERN, 1));
        districtCards.add(new DistrictCard(Color.BLUE, DistrictName.JAIL, 1));
    }

    @Test
    void useUniqueDistrictPowerTest() {
        CompleteStrategy districtStrategy = new CompleteStrategy(new CharacterStrat(), new BuildMaxDistrictStrategy());
        Player player = new Player("Player", districtCards, 10, new Random(), districtStrategy);
        GameEngine gameEngine = new GameEngine(new Random(), player);

        player.buildDistrictCardsInHand(new DistrictCard(Color.PURPLE, DistrictName.LABORATORY, 5));
        assertEquals(5, player.getCoins());

        assertEquals(new DistrictCard(Color.RED, DistrictName.TAVERN, 1), player.chooseCardToDiscard());

        gameEngine.useUniqueDistrict(player);
        assertEquals(6, player.getCoins());

        List<DistrictCard> cardsInHandAfterPowerUsed = districtCards;
        cardsInHandAfterPowerUsed.remove(new DistrictCard(Color.RED, DistrictName.TAVERN, 1));

        assertEquals(cardsInHandAfterPowerUsed, player.getDistrictCardsInHand());
        assertEquals("Player uses his Laboratory district  ..." + System.lineSeparator() +
                "Player has chosen to destroy : TAVERN" + System.lineSeparator() +
                "Player receives 1 coin" + System.lineSeparator() +
                "Player has 6 coins" + System.lineSeparator() +
                "Player receives 1 extra coin." + System.lineSeparator(), outContent.toString());
    }

    @Test
    void destroyCardTest() {
        CompleteStrategy districtStrategy = new CompleteStrategy(new CharacterStrat(), new BuildMaxDistrictStrategy());
        Player player = new Player("Player", districtCards, 10, new Random(), districtStrategy);
        GameEngine gameEngine = new GameEngine(new Random(), player);

        Laboratory lab = new Laboratory(gameEngine);
        Boolean isDestroyed = lab.discardCard(player, player.getDistrictCardsInHand(), player.chooseCardToDiscard());

        assertEquals(true, isDestroyed);
    }
}
