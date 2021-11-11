package fr.unice.polytech.citadelles;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.*;

class GameEngineTest {
    GameEngine ge;

    static final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    static final PrintStream originalOut = System.out;

    @BeforeAll
    static void baSetUp(){
        System.setOut(new PrintStream(outContent));
    }

    @BeforeEach
    void setUp() {
        ge = new GameEngine();
    }

    @Test
    void districtCardTest() {
        //prints nothing before launch
        assertTrue(outContent.toString().isEmpty());
        assertTrue(outContent.toString().isBlank());
        //random cards, we test only if it outputs
        ge.launchGame();
        assertFalse(outContent.toString().isEmpty());
        assertFalse(outContent.toString().isBlank());
    }

    @AfterAll
    static void restoreStreams() {
        System.setOut(originalOut);
    }
}