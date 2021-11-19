package fr.unice.polytech.citadelles;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DistrictCardTest {
    DistrictCard dc, dcRed, dcGreen, dcBlue, dcYellow, dcGrey;

    @BeforeEach
    void setUp() {
        dcRed = new DistrictCard(Color.RED, DistrictName.BARRACKS, 4);
        dcGreen = new DistrictCard(Color.GREEN, DistrictName.TAVERN, 4);
        dcBlue = new DistrictCard(Color.BLUE, DistrictName.TEMPLE, 4);
        dcYellow = new DistrictCard(Color.YELLOW, DistrictName.PALACE, 4);
        dcGrey = new DistrictCard(Color.GREY, DistrictName.NONE, 4);
    }

    @Test
    void districtCardTest() {
        assertEquals(dcGrey, new DistrictCard(Color.GREY, DistrictName.NONE, 4));
        assertNotEquals(dcGrey, 1); // wrong order of arguments to test the .equals method of dc and not the other object
        assertEquals(4, dcGrey.getPriceToBuild());

        assertEquals(dcRed, new DistrictCard(Color.RED, DistrictName.BARRACKS, 4));
        assertEquals(dcGreen, new DistrictCard(Color.GREEN, DistrictName.TAVERN, 4));
        assertEquals(dcBlue, new DistrictCard(Color.BLUE, DistrictName.TEMPLE, 4));
        assertEquals(dcYellow, new DistrictCard(Color.YELLOW, DistrictName.PALACE, 4));

        assertThrows(RuntimeException.class, () -> new DistrictCard(Color.GREY, DistrictName.NONE, -1));
    }
}