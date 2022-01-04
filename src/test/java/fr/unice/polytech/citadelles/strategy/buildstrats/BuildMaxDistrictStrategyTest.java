package fr.unice.polytech.citadelles.strategy.buildstrats;

import fr.unice.polytech.citadelles.card.CharacterCard;
import fr.unice.polytech.citadelles.card.DeckOfCards;
import fr.unice.polytech.citadelles.card.DistrictCard;
import fr.unice.polytech.citadelles.enums.CharacterName;
import fr.unice.polytech.citadelles.enums.Color;
import fr.unice.polytech.citadelles.enums.DistrictName;
import fr.unice.polytech.citadelles.player.Player;
import fr.unice.polytech.citadelles.strategy.CompleteStrategy;
import fr.unice.polytech.citadelles.strategy.characterstrats.CharacterStrat;
import fr.unice.polytech.citadelles.strategy.characterstrats.MerchantOrColorStrategy;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

class BuildMaxDistrictStrategyTest {
    static List<DistrictCard> districtCards;
    static List<DistrictCard> districtCardsV2;

    @BeforeAll
    static void init() {
        districtCards = new ArrayList<>();
        districtCards.add(new DistrictCard(Color.GREY, DistrictName.MONASTERY, 4));
        districtCards.add(new DistrictCard(Color.GREY, DistrictName.SHOP, 3));
        districtCards.add(new DistrictCard(Color.GREY, DistrictName.MARKET, 2));
        districtCards.add(new DistrictCard(Color.RED, DistrictName.TAVERN, 1));
        districtCards.add(new DistrictCard(Color.BLUE, DistrictName.JAIL, 1));

        districtCardsV2 = new ArrayList<>();
        districtCardsV2.add(new DistrictCard(Color.YELLOW, DistrictName.PALACE, 5));
        districtCardsV2.add(new DistrictCard(Color.RED, DistrictName.FORTRESS, 5));
        districtCardsV2.add(new DistrictCard(Color.GREEN, DistrictName.TAVERN, 1));
        districtCardsV2.add(new DistrictCard(Color.YELLOW, DistrictName.MANSION, 3));
    }

    @Test
    void getCoinsOverDrawingACardTest() {
        CompleteStrategy districtStrategy = new CompleteStrategy(new CharacterStrat(), new BuildMaxDistrictStrategy());
        Player player1 = new Player("Player 1", districtCards, 0, new Random(), districtStrategy);
        assertTrue(districtStrategy.getCoinsOverDrawingACard());

        List<DistrictCard> emptyList = new ArrayList<>();
        Player player2 = new Player("Player 2", emptyList, 0, new Random(), districtStrategy);
        assertFalse(districtStrategy.getCoinsOverDrawingACard());
    }

    @Test
    void chooseBestDistrictCardTest(){
        CompleteStrategy districtStrategy = new CompleteStrategy(new CharacterStrat(), new BuildMaxDistrictStrategy());
        List <DistrictCard> districtCards = new ArrayList<>();
        districtCards.add(new DistrictCard(Color.GREY, DistrictName.MONASTERY, 4));
        districtCards.add(new DistrictCard(Color.RED, DistrictName.TAVERN, 1));

        Player player = new Player("Player 1", districtCards, 2, new Random(), districtStrategy);
        assertEquals(new DistrictCard(Color.RED, DistrictName.TAVERN, 1), player.chooseBestDistrictCard(districtCards));
    }

    @Test
    void getTaxesAtBeginningOfTurnTest() {
        CompleteStrategy districtStrategy = new CompleteStrategy(new CharacterStrat(), new BuildMaxDistrictStrategy());
        Player player = new Player("Player 1", districtCards, 2, new Random(), districtStrategy);
        //Always true (part of the strategy)
        assertTrue(districtStrategy.getTaxesAtBeginningOfTurn());
    }

    @Test
    void buildDistrict() {
        CompleteStrategy districtStrategy = new CompleteStrategy(new CharacterStrat(), new BuildMaxDistrictStrategy());
        Player player1 = new Player("Player 1", districtCards, 0, new Random(), districtStrategy);
        //Not enough coins
        assertNull(districtStrategy.buildDistrict());

        List<DistrictCard> emptyList = new ArrayList<>();
        Player player2 = new Player("Player 2", emptyList, 0, new Random(), districtStrategy);
        //Empty List
        assertNull(districtStrategy.buildDistrict());

        Player player3 = new Player("Player 3", districtCards, 1, new Random(), districtStrategy);
        assertEquals(new DistrictCard(Color.GREEN, DistrictName.TAVERN, 1), districtStrategy.buildDistrict());
    }

    @Test
    void hashCodeTest() {
        Random random = new Random();
        CompleteStrategy districtStrategy = new CompleteStrategy(new CharacterStrat(), new BuildMaxDistrictStrategy());
        Player player = new Player("Player 1", districtCards, 200, random, districtStrategy);
        assertEquals(Objects.hash(new CharacterStrat().hashCode(), new BuildMaxDistrictStrategy().hashCode()), player.getStrategy().hashCode());
    }

    @Test
    void equalsTest() {
        CompleteStrategy districtStrategy = new CompleteStrategy(new CharacterStrat(), new BuildMaxDistrictStrategy());
        Player player1 = new Player("Player 1", districtCards, 2, new Random(), districtStrategy);

        CompleteStrategy districtStrategy2 = new CompleteStrategy(new CharacterStrat(), new BuildMaxDistrictStrategy());
        Player player2 = new Player("Player 2", districtCards, 3, new Random(), districtStrategy);

        assertEquals(districtStrategy, districtStrategy2);
        assertNotEquals(districtStrategy, 1); // wrong order of arguments to test the .equals method of districtStrategy and not the other object
    }

    @Test
    void toStringTest(){
        BuildMaxDistrictStrategy strat = new BuildMaxDistrictStrategy();
        assertEquals("Build Max District Strategy", strat.toString());
    }
}
