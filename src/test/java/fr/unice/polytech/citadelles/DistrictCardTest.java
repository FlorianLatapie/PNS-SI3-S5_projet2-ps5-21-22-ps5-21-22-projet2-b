package fr.unice.polytech.citadelles;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

class DistrictCardTest {
    DistrictCard dc;

    @BeforeEach
    void setUp() {
        dc = new DistrictCard(1);
    }

    @Test
    void districtCardTest() {
        assertEquals(dc, new DistrictCard(1));
        assertNotEquals(dc, 1);
        assertEquals(1, new DistrictCard(1).getPriceToBuild());

    }
}