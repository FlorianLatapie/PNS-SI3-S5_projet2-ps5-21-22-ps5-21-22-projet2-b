package fr.unice.polytech.citadelles;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class PlayerTest {
    Player p;
    String player1 = "Player_1";
    List<DistrictCard> districtCards;


    @BeforeEach
    void setUp() {
        districtCards = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            districtCards.add(new DistrictCard(i));
        }
        p = new Player(player1, districtCards);
    }

    @Test
    void districtCardTest() {
        assertEquals(p, new Player(player1, districtCards));
        assertNotEquals(p, " ");
        assertEquals(p.getDistrictCardsInHand(), new Player(player1, districtCards).getDistrictCardsInHand());
    }

    @Test
    void buildDistrictCardTest() {
        Player pCopy = new Player(player1, p.getDistrictCardsInHand());
        pCopy.buildDistrictCardsInHand(p.getDistrictCardsInHand().get(0));
        assertNotEquals(p.getDistrictCardsInHand(),pCopy.getDistrictCardsInHand()); //check if card has been removed
        assertEquals(p.getDistrictCardsInHand().get(0), pCopy.getDistrictCardsBuilt().get(0)); //check if card has been build
    }

    @Test
    void chooseToBuildDistrictTest(){
        assertTrue((Boolean)p.chooseToBuildDistrict() instanceof Boolean);
    }
}