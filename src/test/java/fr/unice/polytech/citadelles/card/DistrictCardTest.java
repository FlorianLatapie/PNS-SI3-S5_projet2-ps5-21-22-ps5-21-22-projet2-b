package fr.unice.polytech.citadelles.card;

import fr.unice.polytech.citadelles.card.DistrictCard;
import fr.unice.polytech.citadelles.enums.Color;
import fr.unice.polytech.citadelles.enums.DistrictName;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DistrictCardTest {
    DistrictCard dcRed, dcGreen, dcBlue, dcYellow, dcGrey;

    @BeforeEach
    void setUp() {
        dcRed = new DistrictCard(Color.RED, DistrictName.BARRACKS, 4);
        dcGreen = new DistrictCard(Color.GREEN, DistrictName.TAVERN, 4);
        dcBlue = new DistrictCard(Color.BLUE, DistrictName.TEMPLE, 4);
        dcYellow = new DistrictCard(Color.YELLOW, DistrictName.PALACE, 4);
        dcGrey = new DistrictCard(Color.GREY, DistrictName.NONE, 4);
    }

    @Test
    void equalsTest() {
        assertEquals(dcGrey, new DistrictCard(Color.GREY, DistrictName.NONE, 4));
        assertNotEquals(dcGrey, 1); // wrong order of arguments to test the .equals method of dc and not the other object
        assertEquals(4, dcGrey.getPriceToBuild());

        assertEquals(dcRed, new DistrictCard(Color.RED, DistrictName.BARRACKS, 4));
        assertEquals(dcGreen, new DistrictCard(Color.GREEN, DistrictName.TAVERN, 4));
        assertEquals(dcBlue, new DistrictCard(Color.BLUE, DistrictName.TEMPLE, 4));
        assertEquals(dcYellow, new DistrictCard(Color.YELLOW, DistrictName.PALACE, 4));

        Exception exception = assertThrows(Exception.class, () -> new DistrictCard(Color.GREY, DistrictName.NONE, -1));
        assertEquals("The price of the card is not greater than 0: -1", exception.getMessage());
    }

    @Test
    void hashCodeTest() {
        assertEquals(dcGrey.hashCode(), new DistrictCard(Color.GREY, DistrictName.NONE, 4).hashCode());

        assertEquals(dcRed.hashCode(), new DistrictCard(Color.RED, DistrictName.BARRACKS, 4).hashCode());
        assertEquals(dcGreen.hashCode(), new DistrictCard(Color.GREEN, DistrictName.TAVERN, 4).hashCode());
        assertEquals(dcBlue.hashCode(), new DistrictCard(Color.BLUE, DistrictName.TEMPLE, 4).hashCode());
        assertEquals(dcYellow.hashCode(), new DistrictCard(Color.YELLOW, DistrictName.PALACE, 4).hashCode());
    }
}