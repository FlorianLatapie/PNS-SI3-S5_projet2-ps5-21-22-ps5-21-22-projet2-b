package fr.unice.polytech.citadelles;

import fr.unice.polytech.citadelles.strategy.BuildMaxDistrictStrategy;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

/**
 * "GameEngine" or "ge" also known as "MJ" or "Moteur de Jeu" in French
 */
public class GameEngine {
    private IO io;

    private int nbPlayers;
    private List<Player> listOfPlayers;
    private List<Player> playersWhoBuilt8Cards;
    private Player kingOfTheLastRound;
    private Player kingByDefault;

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
        playersWhoBuilt8Cards = new ArrayList<>();

        deckOfCards = new DeckOfCards(random);

        initPlayers();
    }

    public GameEngine(Random random, Player... players) {
        this.random = random;
        io = new IO();
        playersWhoBuilt8Cards = new ArrayList<>();

        deckOfCards = new DeckOfCards(random);

        listOfPlayers = new ArrayList<>(List.of(players));
        kingOfTheLastRound = listOfPlayers.get(0);
        kingByDefault = listOfPlayers.get(0);
        nbPlayers = listOfPlayers.size();
    }

    private void initPlayers() {
        for (int i = 0; i < nbPlayers; i++) {
            List<DistrictCard> districtCards = new ArrayList<>();
            for (int j = 0; j < 4; j++) {
                districtCards.add(deckOfCards.getRandomDistrictCard());
            }
            Player playerToAdd = new Player("Player_" + (i + 1), districtCards, 0, random, new BuildMaxDistrictStrategy());
            listOfPlayers.add(playerToAdd);
            if (i == 0) {
                kingOfTheLastRound = playerToAdd;
                kingByDefault = playerToAdd;
            }
        }
    }

    public void launchGame() {
        int round = 1;
        List<CharacterCard> characterCardDeckOfTheRound;

        io.printSeparator("The game starts !");

        while (playersWhoBuilt8Cards.isEmpty()) {
            io.printSeparator("Start of the round " + round);

            List<Player> listOfPlayersSorted = askPlayersRoleAndSortThemByRole(deckOfCards.getNewCharacterCards());// is a new copy of the 8 characters each new round
            io.printSeparator("All the players chose their role for round " + round + "!");

            for (Player player : listOfPlayersSorted) {
                io.println(player.getName() + " is " + player.getCharacterCard());

                askToChooseCoinsOverDrawingACard(player);

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

                hasThisPlayerPlaced8Cards(player);
                io.printSeparator("End of turn " + round + " for " + player.getName());
            }
            round++;
        }
        io.printSeparator("The game is over !");
        getWinner();
    }

    public boolean hasThisPlayerPlaced8Cards(Player player) {
        boolean hasPlaced8 = player.getDistrictCardsBuilt().size() >= 8;
        if (hasPlaced8) {
            playersWhoBuilt8Cards.add(player);
            io.println(player.getName() + " has placed 8 cards!");
        }
        return hasPlaced8;
    }

    public List<Player> askPlayersRoleAndSortThemByRole(List<CharacterCard> characterCardDeckOfTheRound) {
        for (Player player : listOfPlayers) {
            askToChooseCharacter(
                    listOfPlayers.get(
                            (listOfPlayers.indexOf(kingOfTheLastRound) + listOfPlayers.indexOf(player)) % nbPlayers),
                    characterCardDeckOfTheRound);
        }

        List<Player> listOfPlayersSorted = sortPlayerListByCharacterSequence();
        updateKing(listOfPlayersSorted);
        return listOfPlayersSorted;
    }

    public boolean askToBuildDistrict(Player player) {
        boolean choice = player.chooseToBuildDistrict();
        if (choice) {
            io.println(player.getName() + " has chose to build a district");
            io.printDistrictCardsInHandOf(player);
        }
        return choice;
    }

    public CharacterCard askToChooseCharacter(Player player, List<CharacterCard> characterCardDeckOfTheRound) {
        if (characterCardDeckOfTheRound.isEmpty()) {
            throw new IllegalArgumentException("Character card deck of the round is empty : the player can't choose a character card.");
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

    public void giveCoins(Player player) {
        int nbCoinsToAdd = 2;
        /*if (nbCoinsToAdd == 1){
            io.println(player.getName() + " receives " + nbCoinsToAdd + " coin");
        } else {*/
        io.println(player.getName() + " receives " + nbCoinsToAdd + " coins");
        player.receiveCoins(nbCoinsToAdd);
        io.printCoinsOf(player);
    }

    public void giveCard(Player player) {
        DistrictCard card = deckOfCards.getRandomDistrictCard();
        io.println(player.getName() + " choose to draw a card");
        io.println(player.getName() + " draws: " + card.toString());
        player.receiveCard(card);
    }

    public void askToChooseCoinsOverDrawingACard(Player player) {
        if (player.chooseCoinsOverDrawingACard() || deckOfCards.getDistrictCards().isEmpty()) {
            giveCoins(player);
        } else {
            giveCard(player);
        }
    }

    public void getTaxes(Player player) {
        if (player.getCharacterCard().getColor().equals(Color.GREY)) {
            io.println(player.getName() + " is " + player.getCharacterCard().getColor() + ", no taxes to compute");
        } else {
            AtomicInteger sum = new AtomicInteger();
            io.println(player.getName() + " computing taxes ...");

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

    public List<Player> getWinner() { // Player needs to implements Comparable<Player> to be cleaner
        List<Player> playersSorted = new ArrayList<>(listOfPlayers);
        Collections.sort(playersSorted,
                (player0, player1) -> player1.getNbOfPoints().compareTo(player0.getNbOfPoints()));
        io.printWinner(playersSorted);
        return playersSorted;
    }

    public Player updateKing(List<Player> listOfPlayers) {
        kingOfTheLastRound = kingByDefault;
        listOfPlayers.forEach(player -> {
            if (player.getCharacterCard().getCharacterName() == CharacterName.KING) {
                kingOfTheLastRound = player;
            }
        });
        return kingOfTheLastRound;
    }

    // getters && setters
    public List<Player> getListOfPlayers() {
        return listOfPlayers;
    }

    public DeckOfCards getDeckOfCards() {
        return deckOfCards;
    }

    public Player getKingOfTheLastRound() {
        return kingOfTheLastRound;
    }

    public List<Player> getPlayersWhoBuilt8Cards() {
        return playersWhoBuilt8Cards;
    }
}
