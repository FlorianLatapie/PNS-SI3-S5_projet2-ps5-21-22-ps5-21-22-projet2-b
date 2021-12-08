package fr.unice.polytech.citadelles;

import fr.unice.polytech.citadelles.card.CharacterCard;
import fr.unice.polytech.citadelles.card.DeckOfCards;
import fr.unice.polytech.citadelles.card.DistrictCard;
import fr.unice.polytech.citadelles.enums.CharacterName;
import fr.unice.polytech.citadelles.enums.Color;
import fr.unice.polytech.citadelles.enums.DistrictName;
import fr.unice.polytech.citadelles.player.Player;
import fr.unice.polytech.citadelles.strategy.RandomStrategy;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

class RandomStrategyTest {
    List<DistrictCard> districtCards;

    @BeforeEach
    void init() {
        districtCards = new ArrayList<>();
        for (int i = 1; i < 5; i++) {
            districtCards.add(new DistrictCard(Color.GREY, DistrictName.NONE, i));
        }

    }

    @Test
    void chooseCharacterTest() {
        Random mockRandom = mock(Random.class);
        when(mockRandom.nextInt(anyInt(), anyInt())).thenReturn(0);

        RandomStrategy spy = spy(new RandomStrategy());
        Player player = new Player("Player 1", districtCards, 2, mockRandom, spy);

        DeckOfCards doc = new DeckOfCards();
        List<CharacterCard> cardsOfTheRound = doc.getNewCharacterCards();
        assertEquals(new DeckOfCards().getNewCharacterCards().get(0), player.chooseCharacter(cardsOfTheRound));
        assertEquals(new CharacterCard(CharacterName.ASSASSIN), player.getCharacterCard());

        verify(spy, times(1)).chooseCharacter(anyList());
    }

    @Test
    void getCoinsOverDrawingACardTest() {
        Random mockRandom = mock(Random.class);
        when(mockRandom.nextBoolean()).thenReturn(true, false);

        RandomStrategy spy = spy(new RandomStrategy());
        Player player = new Player("Player 1", districtCards, 2, mockRandom, spy);

        assertTrue(player.chooseCoinsOverDrawingACard());
        assertFalse(player.chooseCoinsOverDrawingACard());

        verify(spy, times(2)).getCoinsOverDrawingACard();
    }

    @Test
    void getTaxesAtBeginningOfTurnTest() {
        Random mockRandom = mock(Random.class);
        when(mockRandom.nextBoolean()).thenReturn(true, false);

        RandomStrategy spy = spy(new RandomStrategy());
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

        RandomStrategy spy = spy(new RandomStrategy());
        Player player = new Player("Player 1", dc, 200, mockRandom, spy);

        List<DistrictCard> expectedBuilt = List.of(new DistrictCard(Color.GREY, DistrictName.NONE, 1));

        assertEquals(new DistrictCard(Color.GREY, DistrictName.NONE, 1), player.chooseToBuildDistrict());
        assertEquals(expectedBuilt, player.getDistrictCardsBuilt());
        assertTrue(player.getDistrictCardsInHand().isEmpty());
        assertNull(player.chooseToBuildDistrict());

        verify(spy, times(2)).buildDistrict();
    }

    @Test
    void killCharacterCardTest() {
        Random mockRandom = mock(Random.class);
        when(mockRandom.nextInt(anyInt(), anyInt())).thenReturn(0, 1, 2, 3, 4, 5, 6);

        List<DistrictCard> dc = List.of(new DistrictCard(Color.GREY, DistrictName.NONE, 1));

        Player player = new Player("Player 1", dc, 200, mockRandom, new RandomStrategy());

        List<CharacterCard> characterCards = new DeckOfCards().getNewCharacterCards();
        characterCards.remove(new CharacterCard(CharacterName.ASSASSIN));

        assertEquals(new CharacterCard(CharacterName.THIEF), player.killCharacterCard(characterCards));
        assertEquals(new CharacterCard(CharacterName.MAGICIAN), player.killCharacterCard(characterCards));
        assertEquals(new CharacterCard(CharacterName.KING), player.killCharacterCard(characterCards));
        assertEquals(new CharacterCard(CharacterName.BISHOP), player.killCharacterCard(characterCards));
        assertEquals(new CharacterCard(CharacterName.MERCHANT), player.killCharacterCard(characterCards));
        assertEquals(new CharacterCard(CharacterName.ARCHITECT), player.killCharacterCard(characterCards));
        assertEquals(new CharacterCard(CharacterName.WARLORD), player.killCharacterCard(characterCards));
    }

    @Test
    void stealCharacterCardTest() {
        Random mockRandom = mock(Random.class);
        when(mockRandom.nextInt(anyInt(), anyInt())).thenReturn(0, 1, 2, 3, 4, 5);

        List<DistrictCard> dc = List.of(new DistrictCard(Color.GREY, DistrictName.NONE, 1));

        Player player = new Player("Player 1", dc, 200, mockRandom, new RandomStrategy());

        List<CharacterCard> characterCards = new DeckOfCards().getNewCharacterCards();
        characterCards.remove(new CharacterCard(CharacterName.ASSASSIN));
        characterCards.remove(new CharacterCard(CharacterName.THIEF));
        characterCards.remove(new CharacterCard(CharacterName.BISHOP));

        assertEquals(new CharacterCard(CharacterName.MAGICIAN), player.stealCharacterCard(characterCards));
        assertEquals(new CharacterCard(CharacterName.KING), player.stealCharacterCard(characterCards));
        assertEquals(new CharacterCard(CharacterName.MERCHANT), player.stealCharacterCard(characterCards));
        assertEquals(new CharacterCard(CharacterName.ARCHITECT), player.stealCharacterCard(characterCards));
        assertEquals(new CharacterCard(CharacterName.WARLORD), player.stealCharacterCard(characterCards));
    }

    @Test
    void magicianMoveTest(){
        Random mockRandom = mock(Random.class);
        when(mockRandom.nextBoolean()).thenReturn(true, false);
        when(mockRandom.nextInt(anyInt(),anyInt())).thenReturn(1,1);

        List<DistrictCard> dc = List.of(new DistrictCard(Color.GREY, DistrictName.NONE, 1));

        Player player = new Player("Player 1", dc, 200, mockRandom, new RandomStrategy());
        Player player2 = new Player("Player 2", dc, 200, mockRandom, new RandomStrategy());

        List<Player> players = List.of(player,player2);

        assertEquals(player2, player.magicianMove(players));
        assertEquals(null, player.magicianMove(players));
    }

    @Test
    void hashCodeTest() {
        Random random = new Random();
        Player player = new Player("Player 1", districtCards, 200, random, new RandomStrategy());
        assertEquals(Objects.hash(player, random), player.getStrategy().hashCode());
    }

    @Test
    void equalsTest() {
        Player mockPlayer = mock(Player.class);
        Random r = new Random();
        when(mockPlayer.getRandom()).thenReturn(r);

        RandomStrategy randomStrategy = new RandomStrategy();
        randomStrategy.init(mockPlayer);

        RandomStrategy randomStrategy2 = new RandomStrategy();
        randomStrategy2.init(mockPlayer);

        assertEquals(randomStrategy, randomStrategy2);
        assertNotEquals(randomStrategy, 1); // wrong order of arguments to test the .equals method of p and not the other object
    }

    @Test
    void getSometimesRandomPlayerTest(){
        Random mockRandom = mock(Random.class);
        when(mockRandom.nextBoolean()).thenReturn(true, false);
        when(mockRandom.nextInt(anyInt(),anyInt())).thenReturn(1,1);

        List<DistrictCard> dc = List.of(new DistrictCard(Color.GREY, DistrictName.NONE, 1));

        Player player = new Player("Player 1", dc, 200, mockRandom, new RandomStrategy());
        Player player2 = new Player("Player 2", dc, 200, mockRandom, new RandomStrategy());

        List<Player> players = List.of(player,player2);

        assertEquals(player2, player.warlordChoosePlayer(players));
        assertEquals(null, player.warlordChoosePlayer(players));
    }

    @Test
    void changeCardToOtherTest(){
        Random mockRandom = mock(Random.class);
        when(mockRandom.nextInt(anyInt(),anyInt())).thenReturn(0);

        List<DistrictCard> deck = List.of(new DistrictCard(Color.GREY, DistrictName.NONE, 1));

        Player player = new Player("Player", deck, 10, mockRandom);

        assertEquals(new DistrictCard(Color.GREY, DistrictName.NONE, 1),player.changeCardToOther());

        player = new Player("Player", new ArrayList<>(), 10, mockRandom);

        assertEquals(null,player.changeCardToOther());
    }
}

