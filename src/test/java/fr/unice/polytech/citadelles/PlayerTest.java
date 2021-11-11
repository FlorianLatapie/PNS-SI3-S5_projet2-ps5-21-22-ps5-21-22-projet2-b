package fr.unice.polytech.citadelles;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

class PlayerTest {
    Player p;
    List<DistrictCard> districtCards;

    @BeforeEach
    void setUp() {
        districtCards = new ArrayList<>();
        districtCards = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            districtCards.add(new DistrictCard(i));
        }
        p = new Player(districtCards);
    }

    @Test
    void districtCardTest() {
        assertEquals(p, new Player(districtCards));
        assertNotEquals(p, " ");
        assertEquals(p.getDistrictCardsInHand(), new Player(districtCards).getDistrictCardsInHand());
    }
}