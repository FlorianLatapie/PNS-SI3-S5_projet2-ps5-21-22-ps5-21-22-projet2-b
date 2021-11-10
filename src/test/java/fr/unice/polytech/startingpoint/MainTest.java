package fr.unice.polytech.startingpoint;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MainTest {

    String varToBeInitInSetup;

    @BeforeEach
    void setUp() {
        varToBeInitInSetup = "Hello World!";
    }

    @Test
    void helloTest() {
        assertEquals(varToBeInitInSetup, Main.hello());
    }
}