package fr.unice.polytech.citadelles.strategy.characterstrats;

import fr.unice.polytech.citadelles.GameEngine;
import fr.unice.polytech.citadelles.card.CharacterCard;
import fr.unice.polytech.citadelles.card.DeckOfCards;
import fr.unice.polytech.citadelles.card.DistrictCard;
import fr.unice.polytech.citadelles.character.Architect;
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

import static org.junit.jupiter.api.Assertions.*;
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
    void chooseMagicianTest(){
        GameEngine mockGE = mock(GameEngine.class);
        SuperCharacterStrat superstrat = new SuperCharacterStrat(mockGE);
        CompleteStrategy strat = new CompleteStrategy(superstrat, new BuildMaxDistrictStrategy());
        Player player = new Player("Player", strat);
        player.receiveCoins(1);

        DeckOfCards doc = new DeckOfCards();
        List<CharacterCard> characterCardsOfTheRound = doc.getNewCharacterCards();

        assertEquals(new CharacterCard(CharacterName.MAGICIAN), superstrat.chooseMagician(characterCardsOfTheRound));

        characterCardsOfTheRound.remove(new CharacterCard(CharacterName.MAGICIAN));
        assertEquals(null, superstrat.chooseMagician(characterCardsOfTheRound));
    }
    
    void chooseMerchantTest(){
        superCharacterStrat.init(player1);

        List<Player> listOfPlayers = new ArrayList<>();
        listOfPlayers.add(player1);
        listOfPlayers.add(player2);
        listOfPlayers.add(player3);

        superCharacterStrat.setListOfPlayers(listOfPlayers);

        characterCardDeckOfTheGame.remove(new CharacterCard(CharacterName.ARCHITECT));
        characterCardDeckOfTheGame.remove(new CharacterCard(CharacterName.KING));
        characterCardDeckOfTheGame.remove(new CharacterCard(CharacterName.ASSASSIN));

        assertEquals(true, superCharacterStrat.shouldIChooseMerchant());
        assertEquals(new CharacterCard(CharacterName.MERCHANT), superCharacterStrat.chooseMerchant(characterCardDeckOfTheGame));
        assertEquals(new CharacterCard(CharacterName.MERCHANT), superCharacterStrat.chooseCharacter(characterCardDeckOfTheGame));
    }

    @Test
    void chooseMerchantTestWithTooMuchRoundsInGame(){
        when(mockGE.getRound()).thenReturn(5);
        superCharacterStrat.init(player1);

        List<Player> listOfPlayers = new ArrayList<>();
        listOfPlayers.add(player1);
        listOfPlayers.add(player2);
        listOfPlayers.add(player3);

        superCharacterStrat.setListOfPlayers(listOfPlayers);

        characterCardDeckOfTheGame.remove(new CharacterCard(CharacterName.ARCHITECT));
        characterCardDeckOfTheGame.remove(new CharacterCard(CharacterName.KING));
        characterCardDeckOfTheGame.remove(new CharacterCard(CharacterName.ASSASSIN));

        assertEquals(false, superCharacterStrat.shouldIChooseMerchant());
        assertEquals(null, superCharacterStrat.chooseMerchant(characterCardDeckOfTheGame));
    }

    @Test
    void chooseMerchantTestWithTooMuchCoins(){
        when(mockGE.getRound()).thenReturn(5);
        superCharacterStrat.init(player2);

        List<Player> listOfPlayers = new ArrayList<>();
        listOfPlayers.add(player1);
        listOfPlayers.add(player2);
        listOfPlayers.add(player3);

        superCharacterStrat.setListOfPlayers(listOfPlayers);

        characterCardDeckOfTheGame.remove(new CharacterCard(CharacterName.ARCHITECT));
        characterCardDeckOfTheGame.remove(new CharacterCard(CharacterName.KING));
        characterCardDeckOfTheGame.remove(new CharacterCard(CharacterName.ASSASSIN));

        assertEquals(false, superCharacterStrat.shouldIChooseMerchant());
        assertEquals(null, superCharacterStrat.chooseMerchant(characterCardDeckOfTheGame));
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

        assertEquals(new CharacterCard(CharacterName.WARLORD), superstrat.chooseKing(characterCardsOfTheRound));

        characterCardsOfTheRound.remove(new CharacterCard(CharacterName.WARLORD));

        assertEquals(new CharacterCard(CharacterName.BISHOP), superstrat.chooseKing(characterCardsOfTheRound));

        characterCardsOfTheRound.remove(new CharacterCard(CharacterName.BISHOP));

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


    @Test
    void chooseAssassinTest(){
        GameEngine mockGE = mock(GameEngine.class);
        Player player = new Player("Player");
        Player player2 = new Player("Player 2");
        Player player3 = new Player("Player 3");

        List<Player> playersSorted = new ArrayList<>(List.of(player, player3,player2));
        when(mockGE.getListOfPlayersSorted()).thenReturn(playersSorted);
        when(mockGE.getNbOfDistrictsToWin()).thenReturn(4);

        SuperCharacterStrat superstrat = new SuperCharacterStrat(mockGE);
        CompleteStrategy strat = new CompleteStrategy(superstrat, new BuildMaxDistrictStrategy());
        player3.setStrategy(strat);

        List<Player> listPlayer = new ArrayList<>();
        listPlayer.add(player);
        listPlayer.add(player2);
        listPlayer.add(player3);
        superstrat.setListOfPlayers(listPlayer);

        DeckOfCards doc = new DeckOfCards();
        List<CharacterCard> characterCardsOfTheRound = doc.getNewCharacterCards();
        //test last to choose character and not about to win
        assertFalse(superstrat.chooseAssassin(characterCardsOfTheRound));

        //test has a lot of card and others do not
        superstrat.setHaveALotOfCard(2);
        player3.getDistrictCardsInHand().add(new DistrictCard(Color.GREY, DistrictName.NONE, 2));
        player3.getDistrictCardsInHand().add(new DistrictCard(Color.GREY, DistrictName.NONE, 2));
        player3.getDistrictCardsInHand().add(new DistrictCard(Color.GREY, DistrictName.NONE, 2));
        player.getDistrictCardsInHand().removeAll(player.getDistrictCardsInHand());
        player2.getDistrictCardsInHand().removeAll(player2.getDistrictCardsInHand());
        assertTrue(superstrat.chooseAssassin(characterCardsOfTheRound));
        characterCardsOfTheRound.remove(new CharacterCard(CharacterName.MAGICIAN));
        assertFalse(superstrat.chooseAssassin(characterCardsOfTheRound));

        //test coins and district card disparity
        player3.getDistrictCardsInHand().removeAll(player3.getDistrictCardsInHand());
        assertFalse(superstrat.districtCardsBuiltAndCoinsDisparity());

        //test si quelqu'un peut gagner avec l'assassin
        player2.getDistrictCardsBuilt().add(new DistrictCard(Color.GREY, DistrictName.NONE, 2));
        player2.getDistrictCardsBuilt().add(new DistrictCard(Color.GREY, DistrictName.NONE, 2));
        player2.getDistrictCardsBuilt().add(new DistrictCard(Color.GREY, DistrictName.NONE, 2));
        assertTrue(superstrat.districtCardsBuiltAndCoinsDisparity());
        //test si je suis sur le point de gagner avec l'assassin
        superstrat.init(player2);
        assertTrue(superstrat.chooseAssassin(characterCardsOfTheRound));

        //test pas d'assassin
        characterCardsOfTheRound.remove(new CharacterCard(CharacterName.ASSASSIN));
        assertFalse(superstrat.chooseAssassin(characterCardsOfTheRound));



    }

    @Test
    void checkDisparityTest(){
        GameEngine mockGE = mock(GameEngine.class);
        SuperCharacterStrat superstrat = new SuperCharacterStrat(mockGE);
        assertFalse(superstrat.checkDisparity(1, 2, 1));
        assertTrue(superstrat.checkDisparity(1, 3, 1));
        assertFalse(superstrat.checkDisparity(1, -2, 5));
    }

    @Test
    void isAbleToWinWithAssassin(){
        GameEngine mockGE = mock(GameEngine.class);
        when(mockGE.getNbOfDistrictsToWin()).thenReturn(4);
        SuperCharacterStrat superstrat = new SuperCharacterStrat(mockGE);

        CompleteStrategy strat = new CompleteStrategy(superstrat, new BuildStrat());
        Player player = new Player("Player 1", strat);
        player.receiveCoins(1);
        Player player2 = new Player("Player 2");
        Player player3 = new Player("Player 3");

        List<Player> listPlayer = new ArrayList<>();
        listPlayer.add(player);
        listPlayer.add(player2);
        listPlayer.add(player3);
        superstrat.setListOfPlayers(listPlayer);

        player.getDistrictCardsBuilt().add(new DistrictCard(Color.GREY, DistrictName.NONE, 2));
        player.getDistrictCardsBuilt().add(new DistrictCard(Color.GREY, DistrictName.NONE, 2));
        player.getDistrictCardsBuilt().add(new DistrictCard(Color.GREY, DistrictName.NONE, 2));
        assertEquals(player,superstrat.isAbleToWinWithAssassin());

        player2.getDistrictCardsBuilt().add(new DistrictCard(Color.GREY, DistrictName.NONE, 2));
        player2.getDistrictCardsBuilt().add(new DistrictCard(Color.GREY, DistrictName.NONE, 2));
        player2.getDistrictCardsBuilt().add(new DistrictCard(Color.GREY, DistrictName.NONE, 2));
        assertEquals(player,superstrat.isAbleToWinWithAssassin());

        player.getDistrictCardsBuilt().removeAll(player.getDistrictCardsBuilt());
        player2.getDistrictCardsBuilt().removeAll(player2.getDistrictCardsBuilt());
        assertNull(superstrat.isAbleToWinWithAssassin());

        player3.getDistrictCardsBuilt().add(new DistrictCard(Color.GREY, DistrictName.NONE, 2));
        player3.getDistrictCardsBuilt().add(new DistrictCard(Color.GREY, DistrictName.NONE, 2));
        assertNull(superstrat.isAbleToWinWithAssassin());
        player3.getDistrictCardsBuilt().add(new DistrictCard(Color.GREY, DistrictName.NONE, 2));
        assertEquals(player3,superstrat.isAbleToWinWithAssassin());

    }

    @Test
    void haveALotOfCard(){
        GameEngine mockGE = mock(GameEngine.class);
        SuperCharacterStrat superstrat = new SuperCharacterStrat(mockGE);
        CompleteStrategy strat = new CompleteStrategy(superstrat, new BuildStrat());
        Player player = new Player("Player 1", strat);
        player.receiveCoins(1);
        Player player2 = new Player("Player 2");
        Player player3 = new Player("Player 3");

        List<Player> listPlayer = new ArrayList<>();
        listPlayer.add(player);
        listPlayer.add(player2);
        listPlayer.add(player3);
        superstrat.setListOfPlayers(listPlayer);
        assertFalse(superstrat.haveALotOfCard());
        superstrat.setHaveALotOfCard(2);
        player.getDistrictCardsInHand().add(new DistrictCard(Color.GREY, DistrictName.NONE, 2));
        player.getDistrictCardsInHand().add(new DistrictCard(Color.GREY, DistrictName.NONE, 2));
        player.getDistrictCardsInHand().add(new DistrictCard(Color.GREY, DistrictName.NONE, 2));
        assertTrue(superstrat.haveALotOfCard());

        superstrat.setHaveALotOfCard(4);
        assertFalse(superstrat.haveALotOfCard());

        superstrat.setHaveALotOfCard(2);
        player3.getDistrictCardsInHand().add(new DistrictCard(Color.GREY, DistrictName.NONE, 2));
        player3.getDistrictCardsInHand().add(new DistrictCard(Color.GREY, DistrictName.NONE, 2));
        player3.getDistrictCardsInHand().add(new DistrictCard(Color.GREY, DistrictName.NONE, 2));
        assertFalse(superstrat.haveALotOfCard());


        player.getDistrictCardsInHand().removeAll(player.getDistrictCardsBuilt());
        assertFalse(superstrat.haveALotOfCard());

    }



    @Test
    void lastToChooseCharacterTest(){

        Player player = new Player("Player 1");
        player.receiveCoins(1);
        Player player2 = new Player("Player 2");
        Player player3 = new Player("Player 3");
        List<Player> playersSorted = new ArrayList<>(List.of(player, player2, player3));
        GameEngine mockGE = mock(GameEngine.class);
        when(mockGE.getListOfPlayersSorted()).thenReturn(playersSorted);
        SuperCharacterStrat superstrat = new SuperCharacterStrat(mockGE);
        CompleteStrategy strat = new CompleteStrategy(superstrat, new BuildStrat());
        player.setStrategy(strat);
        player.receiveCoins(1);
        assertFalse(superstrat.lastToChooseCharacter(player));
        assertTrue(superstrat.lastToChooseCharacter(player3));
    }

    @Test
    void districtCardsBuiltAndCoinsDisparity(){
        GameEngine mockGE = mock(GameEngine.class);
        SuperCharacterStrat superstrat = new SuperCharacterStrat(mockGE);
        CompleteStrategy strat = new CompleteStrategy(superstrat, new BuildStrat());
        Player player = new Player("Player 1", strat);
        player.receiveCoins(1);
        Player player2 = new Player("Player 2", strat);

        Player player3 = new Player("Player 3", strat);

        List<Player> listPlayer = new ArrayList<>();
        listPlayer.add(player);
        listPlayer.add(player2);
        listPlayer.add(player3);
        superstrat.setListOfPlayers(listPlayer);
        assertFalse(superstrat.districtCardsBuiltAndCoinsDisparity());
        player2.receiveCoins(8);
        player3.receiveCoins(10);
        assertTrue(superstrat.districtCardsBuiltAndCoinsDisparity());
        player2.removeCoins(8);
        player3.removeCoins(10);
        player.setDistrictCardsBuilt(districtCards);
        assertTrue(superstrat.districtCardsBuiltAndCoinsDisparity());
    }

    @Test
    void chooseWarlordTest(){
        GameEngine mockGE = mock(GameEngine.class);
        when(mockGE.getNbOfDistrictsToWin()).thenReturn(4);
        SuperCharacterStrat superstrat = new SuperCharacterStrat(mockGE);
        CompleteStrategy strat = new CompleteStrategy(superstrat, new BuildStrat());

        Player player = new Player("Player 1", strat);
        Player player2 = new Player("Player 2");
        Player player3 = new Player("Player 3");

        List<Player> listPlayer = new ArrayList<>();
        listPlayer.add(player);
        listPlayer.add(player2);
        listPlayer.add(player3);
        superstrat.setListOfPlayers(listPlayer);

        player.getDistrictCardsBuilt().add(new DistrictCard(Color.GREY, DistrictName.NONE, 2));
        player.getDistrictCardsBuilt().add(new DistrictCard(Color.GREY, DistrictName.NONE, 2));
        player.getDistrictCardsBuilt().add(new DistrictCard(Color.GREY, DistrictName.NONE, 2));
        assertNull(superstrat.isAboutToWin());
        assertFalse(superstrat.chooseWarlord(characterCardDeckOfTheGame));
        player2.getDistrictCardsBuilt().add(new DistrictCard(Color.GREY, DistrictName.NONE, 2));
        player2.getDistrictCardsBuilt().add(new DistrictCard(Color.GREY, DistrictName.NONE, 2));
        player2.getDistrictCardsBuilt().add(new DistrictCard(Color.GREY, DistrictName.NONE, 2));
        assertEquals(player2, superstrat.isAboutToWin());
        characterCardDeckOfTheGame.remove(new CharacterCard(CharacterName.ASSASSIN));
        characterCardDeckOfTheGame.remove(new CharacterCard(CharacterName.KING));
        characterCardDeckOfTheGame.remove(new CharacterCard(CharacterName.ARCHITECT));
        characterCardDeckOfTheGame.add(new CharacterCard(CharacterName.WARLORD));
        assertFalse(superstrat.chooseWarlord(characterCardDeckOfTheGame));
        player.receiveCoins(8);
        assertTrue(superstrat.chooseWarlord(characterCardDeckOfTheGame));
    }



    @Test
    void chooseCharacterTest(){
        GameEngine mockGE = mock(GameEngine.class);
        when(mockGE.getNbOfDistrictsToWin()).thenReturn(8);
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

        DeckOfCards doc = new DeckOfCards();
        List<CharacterCard> characterCardsOfTheRound = doc.getNewCharacterCards();

        assertEquals(superstrat.chooseKing(characterCardsOfTheRound), strat.chooseCharacter(characterCardsOfTheRound));
        player.getDistrictCardsBuilt().add(new DistrictCard(Color.GREY,DistrictName.NONE,1));
        assertEquals(new CharacterCard(CharacterName.ASSASSIN), strat.chooseCharacter(characterCardsOfTheRound));
        characterCardsOfTheRound.remove(new CharacterCard(CharacterName.ASSASSIN));
        characterCardsOfTheRound.remove(new CharacterCard(CharacterName.MERCHANT));
        characterCardsOfTheRound.remove(new CharacterCard(CharacterName.MAGICIAN));
        characterCardsOfTheRound.add(new CharacterCard(CharacterName.WARLORD));
        assertEquals(new CharacterCard(CharacterName.WARLORD), strat.chooseCharacter(characterCardsOfTheRound));

        characterCardsOfTheRound.add(new CharacterCard(CharacterName.ASSASSIN));
        characterCardsOfTheRound.add(new CharacterCard(CharacterName.MAGICIAN));
        characterCardsOfTheRound.add(new CharacterCard(CharacterName.MERCHANT));

        listPlayer.remove(player);
        superstrat.setListOfPlayers(listPlayer);
        superstrat.init(player);
        assertEquals(new CharacterCard(CharacterName.MAGICIAN), superstrat.chooseMagician(characterCardsOfTheRound));
    }

    @Test
    void killCharacterCardTest(){
        GameEngine mockGE = mock(GameEngine.class);
        Random mockRandom = mock(Random.class);
        when(mockRandom.nextInt(anyInt(), anyInt())).thenReturn(4);
        SuperCharacterStrat superstrat = new SuperCharacterStrat(mockGE);
        CompleteStrategy strat = new CompleteStrategy(superstrat, new BuildStrat());
        Player player = new Player("Player", mockRandom, strat);
        player.setDistrictCardsBuilt(districtCards);
        player.receiveCoins(1);
        Player player2 = new Player("Player 2", mockRandom, strat);
        Player player3 = new Player("Player 3", mockRandom, strat);

        List<Player> listPlayer = new ArrayList<>();
        listPlayer.add(player);
        listPlayer.add(player2);
        listPlayer.add(player3);
        superstrat.setListOfPlayers(listPlayer);

        DeckOfCards doc = new DeckOfCards();
        List<CharacterCard> characterCardsOfTheRound = doc.getNewCharacterCards();

        assertEquals(new CharacterCard(CharacterName.KING), strat.killCharacterCard(characterCardsOfTheRound));

        characterCardsOfTheRound.remove(new CharacterCard(CharacterName.KING));

        assertEquals(new CharacterCard(CharacterName.MERCHANT), strat.killCharacterCard(characterCardsOfTheRound));
    }


    @Test
    void chooseAPlayer(){
        GameEngine mockGE = mock(GameEngine.class);
        Random mockRandom = mock(Random.class);
        SuperCharacterStrat superstrat = new SuperCharacterStrat(mockGE);
        CompleteStrategy strat = new CompleteStrategy(superstrat, new BuildStrat());
        Player player = new Player("Player", mockRandom, strat);
        player.setDistrictCardsBuilt(districtCards);
        player.receiveCoins(1);
        Player player2 = new Player("Player 2", mockRandom, strat);
        Player player3 = new Player("Player 3", mockRandom, strat);

        List<Player> listPlayer = new ArrayList<>();
        listPlayer.add(player);
        listPlayer.add(player2);
        listPlayer.add(player3);
        superstrat.setListOfPlayers(listPlayer);

        when(mockRandom.nextBoolean()).thenReturn(true);
        when(mockRandom.nextInt(anyInt(),anyInt())).thenReturn(1);

        assertEquals(player, superstrat.chooseAPlayer(listPlayer));

        listPlayer.remove(player);

        assertEquals(player3, superstrat.chooseAPlayer(listPlayer));

    }

    @Test
    void chooseAplayerForWarlord(){
        GameEngine mockGE = mock(GameEngine.class);
        when(mockGE.getNbOfDistrictsToWin()).thenReturn(4);
        SuperCharacterStrat superstrat = new SuperCharacterStrat(mockGE);
        CompleteStrategy strat = new CompleteStrategy(superstrat, new BuildStrat());

        Player player = new Player("Player 1", strat);
        Player player2 = new Player("Player 2");
        Player player3 = new Player("Player 3");

        List<Player> listPlayer = new ArrayList<>();
        listPlayer.add(player);
        listPlayer.add(player2);
        listPlayer.add(player3);
        superstrat.setListOfPlayers(listPlayer);

        player.getDistrictCardsBuilt().add(new DistrictCard(Color.GREY, DistrictName.NONE, 2));
        player.getDistrictCardsBuilt().add(new DistrictCard(Color.GREY, DistrictName.NONE, 2));
        player.getDistrictCardsBuilt().add(new DistrictCard(Color.GREY, DistrictName.NONE, 2));
        player2.getDistrictCardsBuilt().add(new DistrictCard(Color.GREY, DistrictName.NONE, 2));
        player2.getDistrictCardsBuilt().add(new DistrictCard(Color.GREY, DistrictName.NONE, 2));
        player2.getDistrictCardsBuilt().add(new DistrictCard(Color.GREY, DistrictName.NONE, 2));
        characterCardDeckOfTheGame.remove(new CharacterCard(CharacterName.ASSASSIN));
        characterCardDeckOfTheGame.remove(new CharacterCard(CharacterName.KING));
        characterCardDeckOfTheGame.remove(new CharacterCard(CharacterName.ARCHITECT));
        characterCardDeckOfTheGame.add(new CharacterCard(CharacterName.WARLORD));
        assertEquals(player2,superstrat.chooseAPlayerForWarlord(listPlayer));
        assertNull(superstrat.chooseAPlayerForWarlord(new ArrayList<Player>()));
        List<Player> playersOne = new ArrayList<Player> ();
        playersOne.add(player3);
        assertEquals(player3, superstrat.chooseAPlayerForWarlord(playersOne));
    }
}
