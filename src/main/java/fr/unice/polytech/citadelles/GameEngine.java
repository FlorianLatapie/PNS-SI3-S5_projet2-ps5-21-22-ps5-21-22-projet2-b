package fr.unice.polytech.citadelles;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * "GameEngine" or "ge" also known as "MJ" or "Moteur de Jeu" in French
 */
public class GameEngine {
    private IO io;

    private int nbPlayers;
    private List<Player> listOfPlayers;
    private List<Player> playersWhoPlacedThe8Cards;
    private Player kingOfTheLastRound;


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

    public GameEngine(Random random, Player ...players) {
        this.random = random;
        this.nbPlayers = nbPlayers;
        io = new IO();
        playersWhoPlacedThe8Cards = new ArrayList<>();

        deckOfCards = new DeckOfCards(random);

        listOfPlayers = List.of(players);
    }

    private void initPlayers() {
        for (int i = 0; i < nbPlayers; i++) {
            List<DistrictCard> districtCards = new ArrayList<>();
            for (int j = 0; j < 4; j++) {
                districtCards.add(deckOfCards.getRandomDistrictCard());
            }
            Player playerToAdd = new Player("Player_" + (i + 1), districtCards, 0, random);
            listOfPlayers.add(playerToAdd);
            if (i == 0) kingOfTheLastRound = playerToAdd;
        }
    }

    public void launchGame() {
        int round = 1;
        List<CharacterCard> characterCardDeckOfTheRound;

        io.printSeparator("The game starts !");

        while (playersWhoPlacedThe8Cards.isEmpty() && round <= 40) {
            io.printSeparator("Start of the round " + round);
            characterCardDeckOfTheRound = deckOfCards.getNewCharacterCards(); // is a new copy of the 8 characters each new round

            for (Player player : listOfPlayers) {
                askToChooseCharacter(listOfPlayers.get((listOfPlayers.indexOf(kingOfTheLastRound) + listOfPlayers.indexOf(player)) % nbPlayers), characterCardDeckOfTheRound);
            }

            List<Player> listOfPlayersSorted = sortPlayerListByCharacterSequence();
            updateKing(listOfPlayersSorted);
            io.printSeparator("All the players chose their role for round " + round + "!");

            for (Player player : listOfPlayersSorted) {
                io.println(player.getName() + " is " + player.getCharacterCard());

                askCoinsOrDraw2cards(player);

                boolean wantsToReceiveTaxesBeforeBuiling = askToGetTaxesNow(player);
                if (wantsToReceiveTaxesBeforeBuiling) {
                    getTaxes(player);
                }

                io.printDistrictCardsInHandOf(player);
                askToBuildDistrict(player);
                io.printDistrictCardsBuiltBy(player);

                if (!wantsToReceiveTaxesBeforeBuiling) {
                    getTaxes(player);
                }

                io.printSeparator("End of turn " + round + " for " + player.getName());
            }
            round++;
        }
        io.printSeparator("The game is over !");
        getWinner();
    }



    // public methods
    public boolean askToBuildDistrict(Player player) {
        boolean choice = player.chooseToBuildDistrict();
        if (choice) {
            io.println(player.getName() + " has chose to build a district");
            io.printDistrictCardsInHandOf(player);
        }
        return choice;
    }

    public CharacterCard askToChooseCharacter(Player player, List<CharacterCard> characterCardDeckOfTheRound) {
        if(characterCardDeckOfTheRound.isEmpty()){
            throw new IllegalArgumentException("Character card ceck of the round is empty : the player can't choose a character card.");
        }

        CharacterCard choice = player.chooseCharacter(characterCardDeckOfTheRound);
        characterCardDeckOfTheRound.remove(choice);
        io.println(player.getName() + " chose " + player.getCharacterCard());
        return choice;
    }

    public List<Player> sortPlayerListByCharacterSequence() {
        return listOfPlayers.stream()
                .sorted(Comparator.comparing(player -> player.getCharacterCard().getCharacterSequence()))
                .toList();
    }

    public boolean askToGetTaxesNow(Player player) {
        return player.chooseToGetTaxesAtBeginingOfTurn();
    }

    // private methods
    private void giveCoins(Player player) {
        int nbCoinsToAdd = 2;
        /*if (nbCoinsToAdd == 1){
            io.println(player.getName() + " receives " + nbCoinsToAdd + " coin");
        } else {*/
        io.println(player.getName() + " receives " + nbCoinsToAdd + " coins");
        player.receiveCoins(nbCoinsToAdd);
        io.printCoinsOf(player);
    }

    private void askCoinsOrDraw2cards(Player player) {
        // drawing cards not yet implemented
        giveCoins(player);
    }

    private void getTaxes(Player player) {
        if (player.getCharacterCard().getColor().equals(Color.GREY)) {
            io.println(player.getName() + " is " + player.getCharacterCard().getColor() + ", no taxes to compute");
        } else {
            AtomicInteger sum = new AtomicInteger();
            io.println(player.getName() + " computing taxes ...");
            if (player.getCharacterCard().getCharacterName().equals(CharacterName.MERCHANT)) {
                sum.getAndIncrement();
                player.receiveCoins(1);
                io.println(player.getName() + " receives 1 coin because he is a merchant");
            }

            player.getDistrictCardsBuilt().forEach(
                    districtCard -> {
                        if (districtCard.getColor().equals(player.getCharacterCard().getColor())) {
                            sum.getAndIncrement();
                            player.receiveCoins(1);
                            io.println(player.getName() + " receives 1 coin for " + districtCard);
                        }
                    });
            if (sum.get() == 1) {
                io.println(player.getName() + " taxes earned " + sum + " coin");
            } else {
                io.println(player.getName() + " taxes earned " + sum + " coins");
            }

            io.printCoinsOf(player);
        }
    }

    private void getWinner() { // Player needs to implements Comparable<Player> to be cleaner
        Collections.sort(this.listOfPlayers,
                (player0, player1) -> player1.getNbOfPoints().compareTo(player0.getNbOfPoints()));
        io.printWinner(this.listOfPlayers);
    }

    private void updateKing(List<Player> listOfPlayers) {
        listOfPlayers.forEach(player -> {
            if (player.getCharacterCard().getCharacterName() == CharacterName.KING) {
                kingOfTheLastRound = player;
            }
        });
    }

    // getters && setters
    public List<Player> getListOfPlayers() {
        return listOfPlayers;
    }

    public Player getKingOfTheLastRound() {
        return kingOfTheLastRound;
    }
}
