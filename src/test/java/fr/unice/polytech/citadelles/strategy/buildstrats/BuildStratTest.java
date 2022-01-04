package fr.unice.polytech.citadelles.strategy.buildstrats;

import fr.unice.polytech.citadelles.card.DistrictCard;
import fr.unice.polytech.citadelles.enums.Color;
import fr.unice.polytech.citadelles.enums.DistrictName;
import fr.unice.polytech.citadelles.player.Player;
import fr.unice.polytech.citadelles.strategy.CompleteStrategy;
import fr.unice.polytech.citadelles.strategy.characterstrats.CharacterStrat;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

public class BuildStratTest {
    List<DistrictCard> districtCards;

    @BeforeEach
    void init() {
        districtCards = new ArrayList<>();
        for (int i = 1; i < 5; i++) {
            districtCards.add(new DistrictCard(Color.GREY, DistrictName.NONE, i));
        }

    }

    @Test
    void getCoinsOverDrawingACardTest() {
        Random mockRandom = mock(Random.class);
        when(mockRandom.nextBoolean()).thenReturn(true, false);

        CompleteStrategy spy = spy(new CompleteStrategy(new CharacterStrat(), new BuildStrat()));
        Player player = new Player("Player 1", districtCards, 2, mockRandom, spy);

        assertTrue(player.chooseCoinsOverDrawingACard());
        assertFalse(player.chooseCoinsOverDrawingACard());

        verify(spy, times(2)).getCoinsOverDrawingACard();
    }

    @Test
    void chooseBestDistrictCardTest(){
        Random mockRandom = mock(Random.class);
        when(mockRandom.nextInt(anyInt(), anyInt())).thenReturn(0);

        CompleteStrategy spy = spy(new CompleteStrategy(new CharacterStrat(), new BuildStrat()));
        Player player = new Player("Player 1", districtCards, 2, mockRandom, spy);
        assertEquals(new DistrictCard(Color.GREY, DistrictName.NONE, 1), player.chooseBestDistrictCard(districtCards));
    }

    @Test
    void getTaxesAtBeginningOfTurnTest() {
        Random mockRandom = mock(Random.class);
        when(mockRandom.nextBoolean()).thenReturn(true, false);

        CompleteStrategy spy = spy(new CompleteStrategy(new CharacterStrat(), new BuildStrat()));
        Player player = new Player("Player 1", districtCards, 2, mockRandom, spy);

        assertTrue(player.chooseToGetTaxesAtBeginningOfTurn());
        assertFalse(player.chooseToGetTaxesAtBeginningOfTurn());

        verify(spy, times(2)).getTaxesAtBeginningOfTurn();
    }

    @Test
    void chooseToBuildDistrictTest() {
        Random mockRandom = mock(Random.class);
        when(mockRandom.nextBoolean()).thenReturn(true, false);

        List<DistrictCard> dc = List.of(new DistrictCard(Color.GREY, DistrictName.NONE, 1));

        CompleteStrategy spy = spy(new CompleteStrategy(new CharacterStrat(), new BuildStrat()));
        Player player = new Player("Player 1", dc, 200, mockRandom, spy);

        List<DistrictCard> expectedBuilt = List.of(new DistrictCard(Color.GREY, DistrictName.NONE, 1));

        assertEquals(new DistrictCard(Color.GREY, DistrictName.NONE, 1), player.chooseToBuildDistrict());
        assertEquals(expectedBuilt, player.getDistrictCardsBuilt());
        assertTrue(player.getDistrictCardsInHand().isEmpty());
        assertNull(player.chooseToBuildDistrict());

        verify(spy, times(2)).buildDistrict();
    }

    @Test
    void hashCodeTest() {
        Random random = new Random();
        Player player = new Player("Player 1", districtCards, 200, random, new CompleteStrategy(new CharacterStrat(), new BuildStrat()));
        assertEquals(Objects.hash(new CharacterStrat().hashCode(), new BuildStrat().hashCode()), player.getStrategy().hashCode());
    }

    @Test
    void equalsTest() {
        Player mockPlayer = mock(Player.class);
        Random r = new Random();
        when(mockPlayer.getRandom()).thenReturn(r);

        CompleteStrategy randomStrategy = new CompleteStrategy(new CharacterStrat(), new BuildStrat());
        randomStrategy.init(mockPlayer);

        CompleteStrategy randomStrategy2 = new CompleteStrategy(new CharacterStrat(), new BuildStrat());
        randomStrategy2.init(mockPlayer);

        assertEquals(randomStrategy, randomStrategy2);
        assertNotEquals(randomStrategy, 1); // wrong order of arguments to test the .equals method of p and not the other object
    }

    @Test
    void toStringTest(){
        BuildStrat strat = new BuildStrat();
        assertEquals("random Build Strategy",strat.toString());
    }
}
