package fr.unice.polytech.citadelles.uniqueDistricts;

import fr.unice.polytech.citadelles.GameEngine;
import fr.unice.polytech.citadelles.card.DistrictCard;
import fr.unice.polytech.citadelles.enums.Color;
import fr.unice.polytech.citadelles.enums.DistrictName;
import fr.unice.polytech.citadelles.player.Player;
import fr.unice.polytech.citadelles.strategy.CompleteStrategy;
import fr.unice.polytech.citadelles.strategy.Strategy;
import fr.unice.polytech.citadelles.strategy.buildstrats.BuildMaxDistrictStrategy;
import fr.unice.polytech.citadelles.strategy.buildstrats.BuildStrat;
import fr.unice.polytech.citadelles.strategy.characterstrats.CharacterStrat;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;

class SmithyTest {
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
        Strategy districtStrategy = new CompleteStrategy(new CharacterStrat(), new BuildStrat());
        Player player = new Player("Player", districtCards, 10, new Random(), districtStrategy);
        GameEngine gameEngine = new GameEngine(new Random(), player);

        player.getDistrictCardsBuilt().add(new DistrictCard(Color.PURPLE, DistrictName.SMITHY, 5));
        gameEngine.useUniqueDistrict(player);
        assertEquals(7, player.getCoins());
        assertEquals(7, player.getDistrictCardsInHand().size());
    }


}
