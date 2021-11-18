package fr.unice.polytech.citadelles;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class DeckOfCardsTest {
    private DeckOfCards doc;

    @Test
    void getRandomDistrictCardTest() {
        Random mockRandom = mock(Random.class);
        when(mockRandom.nextInt(anyInt())).thenReturn(3);
        doc = new DeckOfCards(mockRandom);

        assertEquals(new DistrictCard(Color.BLUE, 2), doc.getRandomDistrictCard());
    }

    @Test
    void getDistrictCardsTest() {
        doc = new DeckOfCards();
        assertEquals(54, doc.getDistrictCards().size());
    }

}
