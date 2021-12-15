package fr.unice.polytech.citadelles.card;

import fr.unice.polytech.citadelles.card.CharacterCard;
import fr.unice.polytech.citadelles.enums.CharacterName;
import fr.unice.polytech.citadelles.enums.Color;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

class CharacterCardTest {
    CharacterCard king, thief;

    @BeforeEach
    void setUp() {
        king = new CharacterCard(CharacterName.KING);
        thief = new CharacterCard(CharacterName.THIEF);
    }

    @Test
    void equalsTest() {
        assertEquals(king, new CharacterCard(CharacterName.KING));
        assertEquals(thief, new CharacterCard(CharacterName.THIEF));
        assertNotEquals(thief, 1);
    }

    @Test
    void hashCodeTest() {
        assertEquals(king.hashCode(), new CharacterCard(CharacterName.KING).hashCode());
        assertEquals(thief.hashCode(), new CharacterCard(CharacterName.THIEF).hashCode());
    }

    @Test
    void toStringTest() {
        assertEquals("KING [sequence: 4, color: YELLOW]", new CharacterCard(CharacterName.KING).toString());
    }

    @Test
    void sequenceTest() {
        assertEquals(1, new CharacterCard(CharacterName.ASSASSIN).getCharacterSequence());
        assertEquals(2, new CharacterCard(CharacterName.THIEF).getCharacterSequence());
        assertEquals(3, new CharacterCard(CharacterName.MAGICIAN).getCharacterSequence());
        assertEquals(4, new CharacterCard(CharacterName.KING).getCharacterSequence());
        assertEquals(5, new CharacterCard(CharacterName.BISHOP).getCharacterSequence());
        assertEquals(6, new CharacterCard(CharacterName.MERCHANT).getCharacterSequence());
        assertEquals(7, new CharacterCard(CharacterName.ARCHITECT).getCharacterSequence());
        assertEquals(8, new CharacterCard(CharacterName.WARLORD).getCharacterSequence());
    }

    @Test
    void colorTest() {
        assertEquals(Color.GREY, new CharacterCard(CharacterName.ASSASSIN).getColor());
        assertEquals(Color.GREY, new CharacterCard(CharacterName.THIEF).getColor());
        assertEquals(Color.GREY, new CharacterCard(CharacterName.MAGICIAN).getColor());
        assertEquals(Color.YELLOW, new CharacterCard(CharacterName.KING).getColor());
        assertEquals(Color.BLUE, new CharacterCard(CharacterName.BISHOP).getColor());
        assertEquals(Color.GREEN, new CharacterCard(CharacterName.MERCHANT).getColor());
        assertEquals(Color.GREY, new CharacterCard(CharacterName.ARCHITECT).getColor());
        assertEquals(Color.RED, new CharacterCard(CharacterName.WARLORD).getColor());
    }
}
