package fr.unice.polytech.citadelles;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DistrictCardTest {
    DistrictCard dc, dcRed, dcGreen, dcBlue, dcYellow, dcGrey;

    @BeforeEach
    void setUp() {
        dc = new DistrictCard(1);
        dcRed = new DistrictCard(Color.RED, 4);
        dcGreen = new DistrictCard(Color.GREEN, 4);
        dcBlue = new DistrictCard(Color.BLUE, 4);
        dcYellow = new DistrictCard(Color.YELLOW, 4);
        dcGrey = new DistrictCard(Color.GREY, 4);
    }

    @Test
    void districtCardTest() {
        assertEquals(dc, new DistrictCard(1));
        assertNotEquals(dc, 1); // wrong order of arguments to test the .equals method of dc and not the other object
        assertEquals(1, new DistrictCard(1).getPriceToBuild());
        assertEquals(dcGrey, new DistrictCard(4)); // the default constructor needs to create a grey card

        assertEquals(dcRed, new DistrictCard(Color.RED, 4));
        assertEquals(dcGreen, new DistrictCard(Color.GREEN, 4));
        assertEquals(dcBlue, new DistrictCard(Color.BLUE, 4));
        assertEquals(dcYellow, new DistrictCard(Color.YELLOW, 4));

        assertThrows(RuntimeException.class, () -> new DistrictCard(-1));
    }
}