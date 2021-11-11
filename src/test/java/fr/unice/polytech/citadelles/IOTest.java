package fr.unice.polytech.citadelles;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.*;

class IOTest {
    IO io;
    static final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    static final PrintStream originalOut = System.out;

    @BeforeAll
    static void baSetUp(){
        System.setOut(new PrintStream(outContent));
    }
    @BeforeEach
    void setUp() {

        io = new IO();
    }

    @Test
    public void out() {
        io.printDistrictCardsInHandOf(new Player(new DistrictCard(1)));
        assertEquals("The player has the following district cards in hand : [DistrictCard{priceToBuild=1}]\r\n", outContent.toString());
    }

    @AfterAll
    static void restoreStreams() {
        System.setOut(originalOut);
    }
}