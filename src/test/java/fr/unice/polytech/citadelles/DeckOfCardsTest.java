package fr.unice.polytech.citadelles;

import fr.unice.polytech.citadelles.card.CharacterCard;
import fr.unice.polytech.citadelles.card.DeckOfCards;
import fr.unice.polytech.citadelles.card.DistrictCard;
import fr.unice.polytech.citadelles.enums.Color;
import fr.unice.polytech.citadelles.enums.DistrictName;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;
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

        assertEquals(new DistrictCard(Color.BLUE, DistrictName.CHURCH, 2), doc.getRandomDistrictCard());
    }

    @Test
    void getRandomDistrictCardTest2() {
        doc = new DeckOfCards(new Random());
        for (int i = 0; i < 65; i++) {
            doc.getRandomDistrictCard();
        }

        assertEquals(null, doc.getRandomDistrictCard());
    }

    @Test
    void getDistrictCardsTest() {
        doc = new DeckOfCards();
        assertEquals(65, doc.getDistrictCards().size());
    }

    @Test
    void getCharacterCardsTest() {
        doc = new DeckOfCards();
        assertEquals(8, doc.getNewCharacterCards().size());
        List<CharacterCard> cards = doc.getNewCharacterCards();
        cards.remove(0);
        assertEquals(7, cards.size());
        assertEquals(8, doc.getNewCharacterCards().size());
    }

    @Test
    void putDistrictCardInDeck(){
        DeckOfCards doc = new DeckOfCards();
        DistrictCard cardToAdd = new DistrictCard(Color.RED, DistrictName.NONE, 1);
        doc.putDistrictCardInDeck(cardToAdd);
        assertEquals(66, doc.getDistrictCards().size());
    }
}
