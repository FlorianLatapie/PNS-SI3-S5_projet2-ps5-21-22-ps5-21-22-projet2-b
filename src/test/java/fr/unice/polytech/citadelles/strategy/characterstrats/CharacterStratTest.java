package fr.unice.polytech.citadelles.strategy.characterstrats;

import fr.unice.polytech.citadelles.card.CharacterCard;
import fr.unice.polytech.citadelles.card.DeckOfCards;
import fr.unice.polytech.citadelles.card.DistrictCard;
import fr.unice.polytech.citadelles.enums.CharacterName;
import fr.unice.polytech.citadelles.enums.Color;
import fr.unice.polytech.citadelles.enums.DistrictName;
import fr.unice.polytech.citadelles.player.Player;
import fr.unice.polytech.citadelles.strategy.CompleteStrategy;
import fr.unice.polytech.citadelles.strategy.buildstrats.BuildStrat;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.*;

public class CharacterStratTest {
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

        CompleteStrategy spy = spy(new CompleteStrategy());
        Player player = new Player("Player 1", districtCards, 2, mockRandom, spy);

        DeckOfCards doc = new DeckOfCards();
        List<CharacterCard> cardsOfTheRound = doc.getNewCharacterCards();
        assertEquals(new DeckOfCards().getNewCharacterCards().get(0), player.chooseCharacter(cardsOfTheRound));
        assertEquals(new CharacterCard(CharacterName.ASSASSIN), player.getCharacterCard());

        verify(spy, times(1)).chooseCharacter(anyList());
    }

    @Test
    void killCharacterCardTest() {
        Random mockRandom = mock(Random.class);
        when(mockRandom.nextInt(anyInt(), anyInt())).thenReturn(0, 1, 2, 3, 4, 5, 6);

        List<DistrictCard> dc = List.of(new DistrictCard(Color.GREY, DistrictName.NONE, 1));

        Player player = new Player("Player 1", dc, 200, mockRandom, new CompleteStrategy());

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

        Player player = new Player("Player 1", dc, 200, mockRandom, new CompleteStrategy());

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

        Player player = new Player("Player 1", dc, 200, mockRandom, new CompleteStrategy());
        Player player2 = new Player("Player 2", dc, 200, mockRandom, new CompleteStrategy());

        List<Player> players = List.of(player,player2);

        assertEquals(player2, player.magicianMove(players));
        assertEquals(null, player.magicianMove(players));
    }

    @Test
    void hashCodeTest() {
        Random random = new Random();
        Player player = new Player("Player 1", districtCards, 200, random, new CompleteStrategy());
        assertEquals(Objects.hash(player, random), player.getStrategy().hashCode());
    }

    @Test
    void equalsTest() {
        Player mockPlayer = mock(Player.class);
        Random r = new Random();
        when(mockPlayer.getRandom()).thenReturn(r);

        CompleteStrategy randomStrategy = new CompleteStrategy();
        randomStrategy.init(mockPlayer, r, new CharacterStrat(mockPlayer), new BuildStrat(mockPlayer));

        CompleteStrategy randomStrategy2 = new CompleteStrategy();
        randomStrategy2.init(mockPlayer, r, new CharacterStrat(mockPlayer), new BuildStrat(mockPlayer));

        assertEquals(randomStrategy, randomStrategy2);
        assertNotEquals(randomStrategy, 1); // wrong order of arguments to test the .equals method of p and not the other object
    }

    @Test
    void getSometimesRandomPlayerTest(){
        Random mockRandom = mock(Random.class);
        when(mockRandom.nextBoolean()).thenReturn(true, false);
        when(mockRandom.nextInt(anyInt(),anyInt())).thenReturn(1,1);

        List<DistrictCard> dc = List.of(new DistrictCard(Color.GREY, DistrictName.NONE, 1));

        Player player = new Player("Player 1", dc, 200, mockRandom, new CompleteStrategy());
        Player player2 = new Player("Player 2", dc, 200, mockRandom, new CompleteStrategy());

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

    @Test
    void repairDistrictTest(){
        Player p = new Player("player", new ArrayList<>(), 2, new Random(), new CompleteStrategy());
        p.setCharacterCard(new CharacterCard(CharacterName.THIEF));
        assertNull(p.chooseToRepairDistrict());
        DistrictCard districtCard = new DistrictCard(Color.GREY, DistrictName.NONE, 1);
        p.setDestroyedDistricts(new ArrayList<>(List.of(districtCard)));
        assertEquals(districtCard, p.chooseToRepairDistrict());
    }
}
