package fr.unice.polytech.citadelles;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PlayerTest {
    Player p;

    @BeforeEach
    void setUp() {
        p = new Player(new DistrictCard(1));
    }

    @Test
    void districtCardTest() {
        assertEquals(p, new Player(new DistrictCard(1)));
        assertNotEquals(" ", p);
        assertEquals(p.getDistrictCardsInHand(), new Player(new DistrictCard(1)).getDistrictCardsInHand());
    }
}