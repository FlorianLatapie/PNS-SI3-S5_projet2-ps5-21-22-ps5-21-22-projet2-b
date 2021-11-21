package fr.unice.polytech.citadelles;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

/**
 * "GameEngine" or "ge" also known as "MJ" or "Moteur de Jeu" in French
 */
public class GameEngine {
    private IO io;

    private int nbPlayers;
    private List<Player> listOfPlayers;
    private List<Player> playersWhoPlacedThe8Cards;

    private DeckOfCards deckOfCards;

    private Random random;

    public GameEngine() {
        this(4, new Random());
    }

    public GameEngine(int nbPlayers, Random random) {
        this.random = random;
        this.nbPlayers = nbPlayers;
        listOfPlayers = new ArrayList<>();
        io = new IO();
        playersWhoPlacedThe8Cards = new ArrayList<>();

        deckOfCards = new DeckOfCards(random);

        initPlayers();
    }

    private void initPlayers() {
        for (int i = 0; i < nbPlayers; i++) {
            List<DistrictCard> districtCards = new ArrayList<>();
            for (int j = 0; j < 4; j++) {
                districtCards.add(deckOfCards.getRandomDistrictCard());
            }
            listOfPlayers.add(new Player("Player_" + (i + 1), districtCards, 0 , random));
        }
    }

    public void launchGame() {
        int round = 1;
        List<CharacterCard> characterCardDeckOfTheRound;

        io.printSeparator("The game starts !");

        while (playersWhoPlacedThe8Cards.isEmpty() && round <= 4) {
            io.printSeparator("Start of the round " + round);
            characterCardDeckOfTheRound = deckOfCards.getNewCharacterCards(); // is a new copy of the 8 characters each new round

            for (Player player : listOfPlayers) {
                askToChooseCharacter(player, characterCardDeckOfTheRound);

                giveCoins(player);

                io.printDistrictCardsInHandOf(player);
                askToBuildDistrict(player);
                io.printDistrictCardsBuiltBy(player);

                io.printCoinsOf(player);

                io.printSeparator("End of turn " + round + " for " + player.getName());
            }
            round++;
        }
        io.printSeparator("The game is over !");
        getWinner();
    }

    private void askToBuildDistrict(Player player) {
        if (player.chooseToBuildDistrict()) {
            io.println(player.getName() + " has chose to build a district");
            io.printDistrictCardsInHandOf(player);
        }
    }

    private void getWinner() { // Player needs to implements Comparable<Player> to be cleaner
        Collections.sort(this.listOfPlayers,
                (player0, player1) -> player1.getNbOfPoints().compareTo(player0.getNbOfPoints()));
        io.printWinner(this.listOfPlayers);
    }

    public void giveCoins(Player player) {
        int nbCoinsToAdd = 2;
        io.println(player.getName() + " receives "+nbCoinsToAdd+" coins.");
        player.receiveCoins(nbCoinsToAdd);
        io.printCoinsOf(player);

    }

    public void askToChooseCharacter(Player player, List<CharacterCard> characterCardDeckOfTheRound){
        CharacterCard choice = player.chooseCharacter(characterCardDeckOfTheRound);
        characterCardDeckOfTheRound.remove(choice);
        io.println(player.getName() + " chose " + player.getCharacterCard());
    }
}
