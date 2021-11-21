package fr.unice.polytech.citadelles;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class CharacterCardTest {
    CharacterCard king, thief;

    @BeforeEach
    void setUp() {
        king = new CharacterCard(CharacterName.KING);
        thief = new CharacterCard(CharacterName.THIEF);
    }

    @Test
    void equalsTest(){
        assertEquals(king, new CharacterCard(CharacterName.KING));
        assertEquals(thief, new CharacterCard(CharacterName.THIEF));
        assertNotEquals(thief, 1);
    }
    @Test
    void toStringTest(){
        assertEquals("KING", new CharacterCard(CharacterName.KING).toString());
    }
}
