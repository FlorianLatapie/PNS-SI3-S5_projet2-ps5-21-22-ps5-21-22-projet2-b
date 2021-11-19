package fr.unice.polytech.citadelles;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * "GameEngine" or "ge" also known as "MJ" or "Moteur de Jeu" in French
 */
public class GameEngine {
    private int nbPlayers;
    private List<Player> listOfPlayers;
    private IO io;
    private List<Player> winner;
    private DeckOfCards deckOfCards;
    private int round;

    public GameEngine() {
        this(4);
    }

    public GameEngine(int nbPlayers) {
        this.nbPlayers = nbPlayers;
        listOfPlayers = new ArrayList<>();
        io = new IO();
        winner = new ArrayList<>();
        round = 1;
        deckOfCards = new DeckOfCards();
        initPlayers();
    }

    private void initPlayers() {
        for (int i = 0; i < nbPlayers; i++) {
            List<DistrictCard> districtCards = new ArrayList<>();
            for (int j = 0; j < 4; j++) {
                districtCards.add(deckOfCards.getRandomDistrictCard());
            }
            listOfPlayers.add(new Player("Player_" + (i + 1), districtCards));
        }
    }

    public void launchGame() {
        io.printSeparator("The game starts !");

        while (winner.isEmpty() && round <= 4) {
            io.printSeparator("Start of the round " + round);
            deckOfCards.createCharacterCards();
            for (Player player : listOfPlayers) {
                offerPlayer(player);
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

    private void getWinner() {
        Collections.sort(this.listOfPlayers,
                (player0, player1) -> player1.getNbOfPoints().compareTo(player0.getNbOfPoints()));
        io.printWinner(this.listOfPlayers);
    }

    public void giveCoins(Player player) {
        if (round != 1) {
            System.out.println(player.getName() + " receives 2 coins.");
            player.receiveCoins(2);
            io.printCoinsOf(player);
        }
    }

    public void offerPlayer(Player player){
        System.out.println(player.getName() + " has chose " + player.chooseCharacter(deckOfCards));
    }
}
