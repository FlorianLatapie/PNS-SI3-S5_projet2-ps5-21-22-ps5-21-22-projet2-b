package fr.unice.polytech.citadelles.strategy.characterstrats;

import fr.unice.polytech.citadelles.GameEngine;
import fr.unice.polytech.citadelles.card.CharacterCard;
import fr.unice.polytech.citadelles.card.DeckOfCards;
import fr.unice.polytech.citadelles.card.DistrictCard;
import fr.unice.polytech.citadelles.enums.CharacterName;
import fr.unice.polytech.citadelles.enums.Color;
import fr.unice.polytech.citadelles.enums.DistrictName;
import fr.unice.polytech.citadelles.player.Player;
import fr.unice.polytech.citadelles.strategy.CompleteStrategy;
import fr.unice.polytech.citadelles.strategy.buildstrats.BuildMaxDistrictStrategy;
import fr.unice.polytech.citadelles.strategy.buildstrats.BuildStrat;
import net.bytebuddy.implementation.bind.annotation.Super;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.*;

public class SuperCharacterStratTest {

    static GameEngine mockGE;
    static SuperCharacterStrat superCharacterStrat;
    static CompleteStrategy completeStrategy;
    static Player player1;
    static Player player2;
    static Player player3;
    static List<DistrictCard> districtCardInHand;
    static List<DistrictCard> districtCardInHandPlayer1;
    static List<CharacterCard> characterCardDeckOfTheGame;
    static List<DistrictCard> districtCards;
    static List<DistrictCard> districtCardsV2;

    @BeforeEach
    void init() {
        mockGE = mock(GameEngine.class);
        when(mockGE.getNbOfDistrictsToWin()).thenReturn(8);
        superCharacterStrat = new SuperCharacterStrat(mockGE);
        completeStrategy = new CompleteStrategy(superCharacterStrat, new BuildMaxDistrictStrategy());

        districtCardInHandPlayer1 = new ArrayList<>();
        districtCardInHandPlayer1.add(new DistrictCard(Color.GREEN, DistrictName.COUNTER, 3));
        districtCardInHandPlayer1.add(new DistrictCard(Color.GREEN, DistrictName.SHOP, 2));

        districtCardInHand = new ArrayList<>();
        districtCardInHand.add(new DistrictCard(Color.GREEN, DistrictName.COUNTER, 3));
        districtCardInHand.add(new DistrictCard(Color.GREEN, DistrictName.SHOP, 2));
        districtCardInHand.add(new DistrictCard(Color.YELLOW, DistrictName.MANSION, 2));
        districtCardInHand.add(new DistrictCard(Color.BLUE, DistrictName.CITADEL, 4));
        districtCardInHand.add(new DistrictCard(Color.BLUE, DistrictName.TEMPLE, 1));

        player1 = new Player("Player 1", districtCardInHandPlayer1, 2, new Random(), completeStrategy);
        player2 = new Player("Player 2", districtCardInHand, 5, new Random(), completeStrategy);
        player3 = new Player("Player 3", districtCardInHand, 4, new Random(), completeStrategy);

        characterCardDeckOfTheGame = new ArrayList<>();
        characterCardDeckOfTheGame.add(new CharacterCard(CharacterName.ARCHITECT));
        characterCardDeckOfTheGame.add(new CharacterCard(CharacterName.KING));
        characterCardDeckOfTheGame.add(new CharacterCard(CharacterName.MERCHANT));
        characterCardDeckOfTheGame.add(new CharacterCard(CharacterName.MAGICIAN));
        characterCardDeckOfTheGame.add(new CharacterCard(CharacterName.ASSASSIN));

        player2.setDistrictCardsBuilt(districtCardInHand);

        //TESTS KING
        districtCards = new ArrayList<>();
        districtCards.add(new DistrictCard(Color.GREY, DistrictName.MONASTERY, 4));
        districtCards.add(new DistrictCard(Color.GREY, DistrictName.SHOP, 3));
        districtCards.add(new DistrictCard(Color.GREY, DistrictName.MARKET, 2));
        districtCards.add(new DistrictCard(Color.RED, DistrictName.TAVERN, 1));
        districtCards.add(new DistrictCard(Color.BLUE, DistrictName.JAIL, 1));
        districtCards.add(new DistrictCard(Color.YELLOW, DistrictName.MANSION, 2));

        districtCardsV2 = new ArrayList<>();
        districtCardsV2.add(new DistrictCard(Color.YELLOW, DistrictName.PALACE, 5));
        districtCardsV2.add(new DistrictCard(Color.RED, DistrictName.FORTRESS, 5));
        districtCardsV2.add(new DistrictCard(Color.GREEN, DistrictName.TAVERN, 1));
        districtCardsV2.add(new DistrictCard(Color.YELLOW, DistrictName.MANSION, 3));
    }

    @Test
    void chooseArchitectTestWhenIamPlayer1(){
        List<CharacterCard> characterCardDeckOfTheGame = new ArrayList<>();
        characterCardDeckOfTheGame.add(new CharacterCard(CharacterName.ARCHITECT));
        characterCardDeckOfTheGame.add(new CharacterCard(CharacterName.KING));

        List<Player> listOfPlayers = new ArrayList<>();
        listOfPlayers.add(player1);
        listOfPlayers.add(player2);
        listOfPlayers.add(player3);

        superCharacterStrat.setListOfPlayers(listOfPlayers);
        assertEquals(player2, superCharacterStrat.isAboutToWinArchitect(listOfPlayers));
        assertEquals(true, superCharacterStrat.shouldIChooseArchitect());
        assertEquals(new CharacterCard(CharacterName.ARCHITECT), superCharacterStrat.chooseCharacter(characterCardDeckOfTheGame));
    }

    @Test
    void chooseArchitectTestWhenIamPlayer2(){
        superCharacterStrat.init(player2);

        List<Player> listOfPlayers = new ArrayList<>();
        listOfPlayers.add(player1);
        listOfPlayers.add(player2);
        listOfPlayers.add(player3);

        superCharacterStrat.setListOfPlayers(listOfPlayers);
        assertEquals(player2, superCharacterStrat.isAboutToWinArchitect(listOfPlayers));

        assertEquals(player2, superCharacterStrat.isAboutToWinArchitect(listOfPlayers));
        assertEquals(true, superCharacterStrat.canArchitectWin(player2));
        assertEquals(false, superCharacterStrat.canArchitectWin(player1));
        assertEquals(true, superCharacterStrat.shouldIChooseArchitect());
        assertEquals(new CharacterCard(CharacterName.ARCHITECT), superCharacterStrat.chooseCharacter(characterCardDeckOfTheGame));
    }

    @Test
    void chooseArchitectTestWhenIamPlayer2ButArchitectIsNotAvailable(){
        superCharacterStrat.init(player2);

        List<Player> listOfPlayers = new ArrayList<>();
        listOfPlayers.add(player1);
        listOfPlayers.add(player2);
        listOfPlayers.add(player3);

        superCharacterStrat.setListOfPlayers(listOfPlayers);

        characterCardDeckOfTheGame.remove(new CharacterCard(CharacterName.ARCHITECT));

        assertEquals(player2, superCharacterStrat.isAboutToWinArchitect(listOfPlayers));
        assertEquals(true, superCharacterStrat.canArchitectWin(player2));
        assertEquals(false, superCharacterStrat.canArchitectWin(player1));
        assertEquals(true, superCharacterStrat.shouldIChooseArchitect());
        assertEquals(new CharacterCard(CharacterName.ASSASSIN), superCharacterStrat.chooseCharacter(characterCardDeckOfTheGame));
    }

    @Test
    void notArchitectTest(){
        List<CharacterCard> characterCardDeckOfTheGame = new ArrayList<>();
        characterCardDeckOfTheGame.add(new CharacterCard(CharacterName.ARCHITECT));
        characterCardDeckOfTheGame.add(new CharacterCard(CharacterName.KING));
        characterCardDeckOfTheGame.add(new CharacterCard(CharacterName.MERCHANT));
        characterCardDeckOfTheGame.add(new CharacterCard(CharacterName.MAGICIAN));

        Player player5 = new Player("Player 5", districtCardInHandPlayer1, 2, new Random(), completeStrategy);
        Player player6 = new Player("Player 6", districtCardInHandPlayer1, 5, new Random(), completeStrategy);
        Player player7 = new Player("Player 7", districtCardInHandPlayer1, 4, new Random(), completeStrategy);

        player5.setDistrictCardsBuilt(districtCardInHand);
        superCharacterStrat.init(player5);

        List<Player> listOfPlayer = new ArrayList<>();
        listOfPlayer.add(player5);
        listOfPlayer.add(player6);
        listOfPlayer.add(player7);

        assertEquals(false, superCharacterStrat.shouldIChooseArchitect());
        assertEquals(null, superCharacterStrat.isAboutToWinArchitect(listOfPlayer));
        assertEquals(false, superCharacterStrat.canArchitectWin(player5));
    }

    @Test
    void chooseKingTest(){
        GameEngine mockGE = mock(GameEngine.class);
        SuperCharacterStrat superstrat = new SuperCharacterStrat(mockGE);
        CompleteStrategy strat = new CompleteStrategy(superstrat, new BuildMaxDistrictStrategy());
        Player player = new Player("Player", strat);
        player.setDistrictCardsBuilt(districtCards);
        player.receiveCoins(1);
        Player player2 = new Player("Player 2", strat);
        Player player3 = new Player("Player 3", strat);

        List<Player> listPlayer = new ArrayList<>();
        listPlayer.add(player);
        listPlayer.add(player2);
        listPlayer.add(player3);
        superstrat.setListOfPlayers(listPlayer);

        DeckOfCards doc = new DeckOfCards();
        List<CharacterCard> characterCardsOfTheRound = doc.getNewCharacterCards();

        assertEquals(new CharacterCard(CharacterName.KING),superstrat.chooseKing(characterCardsOfTheRound));

        characterCardsOfTheRound.remove(new CharacterCard(CharacterName.KING));

        assertEquals(new CharacterCard(CharacterName.ASSASSIN), superstrat.chooseKing(characterCardsOfTheRound));

        characterCardsOfTheRound.remove(new CharacterCard(CharacterName.ASSASSIN));

        assertEquals(null, superstrat.chooseKing(characterCardsOfTheRound));

    }


    @Test
    void isAboutToWinWithKingTest(){
        GameEngine mockGE = mock(GameEngine.class);
        SuperCharacterStrat superstrat = new SuperCharacterStrat(mockGE);
        CompleteStrategy strat = new CompleteStrategy(superstrat, new BuildStrat());
        Player player = new Player("Player", strat);
        player.setDistrictCardsBuilt(districtCards);
        player.receiveCoins(1);
        Player player2 = new Player("Player 2", strat);
        Player player3 = new Player("Player 3", strat);

        List<Player> listPlayer = new ArrayList<>();
        listPlayer.add(player);
        listPlayer.add(player2);
        listPlayer.add(player3);
        superstrat.setListOfPlayers(listPlayer);


        assertEquals(player, superstrat.isAboutToWinWithKing());
    }
}
