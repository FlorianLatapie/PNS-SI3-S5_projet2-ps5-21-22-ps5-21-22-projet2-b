package fr.unice.polytech.citadelles;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;

public class CharacterTest {
    Character king, thief;

    @BeforeEach
    void setUp() {
        king = new Character(CharacterNames.KING);
        thief = new Character(CharacterNames.THIEF);
    }

    @Test
    void characterTest(){
        assertEquals(king.toString(), new Character(CharacterNames.KING).toString());
        assertEquals(thief.toString(), new Character(CharacterNames.THIEF).toString());
    }
}
