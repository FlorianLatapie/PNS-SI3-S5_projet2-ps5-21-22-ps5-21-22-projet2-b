package fr.unice.polytech.citadelles.uniqueDistrictsTest;

import fr.unice.polytech.citadelles.GameEngine;
import fr.unice.polytech.citadelles.card.DistrictCard;
import fr.unice.polytech.citadelles.card.unique_districts.Laboratory;
import fr.unice.polytech.citadelles.enums.Color;
import fr.unice.polytech.citadelles.enums.DistrictName;
import fr.unice.polytech.citadelles.player.Player;
import fr.unice.polytech.citadelles.strategy.BuildMaxDistrictStrategy;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;

class LaboratoryTest {
    static List<DistrictCard> districtCards;

    @BeforeAll
    static void init() {
        districtCards = new ArrayList<>();
        districtCards.add(new DistrictCard(Color.GREY, DistrictName.SHOP, 3));
        districtCards.add(new DistrictCard(Color.GREY, DistrictName.MARKET, 2));
        districtCards.add(new DistrictCard(Color.RED, DistrictName.TAVERN, 1));
        districtCards.add(new DistrictCard(Color.BLUE, DistrictName.JAIL, 1));
    }

    @Test
    void useUniqueDistrictPowerTest(){
        BuildMaxDistrictStrategy districtStrategy = new BuildMaxDistrictStrategy();
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
    }

    @Test
    void destroyCardTest(){
        BuildMaxDistrictStrategy districtStrategy = new BuildMaxDistrictStrategy();
        Player player = new Player("Player", districtCards, 10, new Random(), districtStrategy);
        GameEngine gameEngine = new GameEngine(new Random(), player);

        Laboratory lab = new Laboratory(gameEngine);
        Boolean isDestroyed = lab.discardCard(player, player.getDistrictCardsInHand(), player.chooseCardToDiscard());

        assertEquals(true, isDestroyed);
    }
}
