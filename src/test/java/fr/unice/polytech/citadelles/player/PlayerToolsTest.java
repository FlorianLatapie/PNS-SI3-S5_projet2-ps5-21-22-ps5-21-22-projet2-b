package fr.unice.polytech.citadelles.player;

import fr.unice.polytech.citadelles.card.DistrictCard;
import fr.unice.polytech.citadelles.enums.Color;
import fr.unice.polytech.citadelles.enums.DistrictName;
import fr.unice.polytech.citadelles.player.Player;
import fr.unice.polytech.citadelles.player.PlayerTools;
import fr.unice.polytech.citadelles.strategy.BuildMaxDistrictStrategy;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class PlayerToolsTest {
    static List<DistrictCard> districtCards;

    @BeforeAll
    static void init() {
        districtCards = new ArrayList<>();
        districtCards.add(new DistrictCard(Color.GREY, DistrictName.MONASTERY, 4));
        districtCards.add(new DistrictCard(Color.GREY, DistrictName.SHOP, 3));
        districtCards.add(new DistrictCard(Color.GREY, DistrictName.MARKET, 2));
        districtCards.add(new DistrictCard(Color.RED, DistrictName.TAVERN, 1));
        districtCards.add(new DistrictCard(Color.BLUE, DistrictName.JAIL, 1));
    }

    @Test
    void getCheapestDistrictCardTest() {
        Player player = new Player("player", districtCards, 2, new Random(), new BuildMaxDistrictStrategy());
        DistrictCard districtCard = new PlayerTools(player).getCheapestDistrictCard();
        //Gets the first cheapest card
        assertEquals(DistrictName.TAVERN, districtCard.getDistrictName());

        Player player2 = new Player("player", new ArrayList<>(), 2, new Random(), new BuildMaxDistrictStrategy());
        DistrictCard districtCard2 = new PlayerTools(player2).getCheapestDistrictCard();

        assertNull(districtCard2);
    }

    @Test
    void getCheapestDistrictCardTest2() {
        Player player = new Player("player", districtCards, 2, new Random(), new BuildMaxDistrictStrategy());
        DistrictCard districtCard = new PlayerTools(player).getCheapestDistrictCard(districtCards);
        //Gets the first cheapest card
        assertEquals(DistrictName.TAVERN, districtCard.getDistrictName());
    }

    @Test
    void mostCommonColorInBuiltDistrictsTest() {
        Player player = new Player("player");
        PlayerTools playerTools = new PlayerTools(player);

        player.setDistrictCardsBuilt(new ArrayList<>(List.of(
                new DistrictCard(Color.RED, DistrictName.NONE, 10),
                new DistrictCard(Color.GREEN, DistrictName.NONE, 10),
                new DistrictCard(Color.BLUE, DistrictName.NONE, 10),
                new DistrictCard(Color.YELLOW, DistrictName.NONE, 10),
                new DistrictCard(Color.GREY, DistrictName.NONE, 10),
                new DistrictCard(Color.GREY, DistrictName.NONE, 10),
                new DistrictCard(Color.GREY, DistrictName.NONE, 10)

        )));
        Color color = playerTools.mostCommonColorInBuiltDistricts();
        assertEquals(Color.RED, color);

        player.setDistrictCardsBuilt(new ArrayList<>(List.of(
                new DistrictCard(Color.GREY, DistrictName.NONE, 10),
                new DistrictCard(Color.GREEN, DistrictName.NONE, 10),
                new DistrictCard(Color.BLUE, DistrictName.NONE, 10),
                new DistrictCard(Color.YELLOW, DistrictName.NONE, 10),
                new DistrictCard(Color.GREY, DistrictName.NONE, 10),
                new DistrictCard(Color.GREY, DistrictName.NONE, 10),
                new DistrictCard(Color.RED, DistrictName.NONE, 10)

        )));
        // arbitrary choice of order in case of a tie: RED, GREEN, BLUE, YELLOW
        assertEquals(Color.RED, playerTools.mostCommonColorInBuiltDistricts());

        player.setDistrictCardsBuilt(new ArrayList<>(List.of(
                new DistrictCard(Color.GREY, DistrictName.NONE, 10),
                new DistrictCard(Color.GREEN, DistrictName.NONE, 10),
                new DistrictCard(Color.BLUE, DistrictName.NONE, 10),
                new DistrictCard(Color.BLUE, DistrictName.NONE, 10),
                new DistrictCard(Color.YELLOW, DistrictName.NONE, 10),
                new DistrictCard(Color.GREY, DistrictName.NONE, 10),
                new DistrictCard(Color.GREY, DistrictName.NONE, 10),
                new DistrictCard(Color.RED, DistrictName.NONE, 10)

        )));
        assertEquals(Color.BLUE, playerTools.mostCommonColorInBuiltDistricts());
    }

    @Test
    void getDistrictCardsInHandSortedTest() {
        Player player = new Player("player", districtCards);
        PlayerTools playerTools = new PlayerTools(player);
        List<DistrictCard> districtCardsSortedExpected = new ArrayList<>(
                List.of(
                        new DistrictCard(Color.RED, DistrictName.TAVERN, 1),
                        new DistrictCard(Color.BLUE, DistrictName.JAIL, 1),
                        new DistrictCard(Color.GREY, DistrictName.MARKET, 2),
                        new DistrictCard(Color.GREY, DistrictName.SHOP, 3),
                        new DistrictCard(Color.GREY, DistrictName.MONASTERY, 4)
                ));

        assertEquals(districtCardsSortedExpected, playerTools.getDistrictCardsInHandSorted());
        assertEquals(new DistrictCard(Color.RED, DistrictName.TAVERN, 1), playerTools.getCheapestCardInHand());

        Player player2 = new Player("a");
        PlayerTools playerTools2 = new PlayerTools(player2);

        assertNull(playerTools2.getCheapestCardInHand());
    }
}
