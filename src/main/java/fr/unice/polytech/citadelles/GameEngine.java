package fr.unice.polytech.citadelles;

import fr.unice.polytech.citadelles.card.CharacterCard;
import fr.unice.polytech.citadelles.card.DeckOfCards;
import fr.unice.polytech.citadelles.card.DistrictCard;
import fr.unice.polytech.citadelles.card.unique_districts.Graveyard;
import fr.unice.polytech.citadelles.card.unique_districts.HauntedQuarter;
import fr.unice.polytech.citadelles.card.unique_districts.Laboratory;
import fr.unice.polytech.citadelles.card.unique_districts.Smithy;
import fr.unice.polytech.citadelles.character.*;
import fr.unice.polytech.citadelles.enums.CharacterName;
import fr.unice.polytech.citadelles.enums.Color;
import fr.unice.polytech.citadelles.enums.DistrictName;
import fr.unice.polytech.citadelles.player.Player;
import fr.unice.polytech.citadelles.strategy.BuildMaxDistrictStrategy;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * "GameEngine" or "ge" also known as "MJ" or "Moteur de Jeu" in French
 */
public class GameEngine {
    // attributes
    private IO io;

    private int nbPlayers;
    private int round;

    private List<Player> listOfPlayers;
    private List<Player> playersWhoBuilt8Cards;

    private Player playerThatCantPlay = null;
    private Player kingOfTheLastRound;
    private Player kingByDefault;

    private DeckOfCards deckOfCards;

    private CharacterCard characterKilled;
    private CharacterCard stolenCharacter;

    private HauntedQuarter hauntedQuarter;

    private Random random;

    // constructors

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
        round = 0;
    }

    public GameEngine(Random random, DeckOfCards deckOfCards, Player... players) {
        this.random = random;
        io = new IO();
        playersWhoBuilt8Cards = new ArrayList<>();

        this.deckOfCards = deckOfCards;

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
            Player playerToAdd = new Player("Player_" + (i + 1), districtCards, 2, random, new BuildMaxDistrictStrategy());
            listOfPlayers.add(playerToAdd);
            if (i == 0) {
                kingOfTheLastRound = playerToAdd;
                kingByDefault = playerToAdd;
            }
        }
    }

    // game

    public void launchGame() {
        round = 1;

        io.printSeparator("The game starts !");

        while (playersWhoBuilt8Cards.isEmpty()) {
            io.printSeparator("Start of the round " + round);
            resetThePenalties();

            List<Player> listOfPlayersSorted = askPlayersRoleAndSortThemByRole(deckOfCards.getNewCharacterCards());// is a new copy of the 8 characters each new round
            io.printSeparator("All players have chosen their role for round " + round + "!");

            for (Player player : listOfPlayersSorted) {
                if (canThisPlayerPlay(player)) {
                    io.println(player.getName() + " is " + player.getCharacterCard());

                    if (isStolenCharacter(player.getCharacterCard())) {
                        io.println(player.getName() + " lost " + player.getCoins() + " coins because of the thief");
                        player.removeCoins(player.getCoins());
                        io.println(player.getName() + " has " + player.getCoins() + " coins");
                    }

                    askToChooseCoinsOverDrawingACard(player);

                    boolean wantsToReceiveTaxesBeforeBuilding = askToGetTaxesNow(player);
                    if (wantsToReceiveTaxesBeforeBuilding) {
                        getTaxes(player);
                    }

                    io.printDistrictCardsInHandOf(player);
                    askToBuildDistrict(player);
                    io.printDistrictCardsBuiltBy(player);

                    if (!wantsToReceiveTaxesBeforeBuilding) {
                        getTaxes(player);
                    }

                    callCharacterCardAction(player);
                    useUniqueDistrict(player);

                    hasThisPlayerBuiltd8Cards(player);
                } else {
                    io.println(player.getName() + " was killed by the ASSASSIN, therefore he cannot play this round");
                }
                io.printSeparator("End of turn " + round + " for " + player.getName());
            }
            round++;
        }
        io.printSeparator("Unique cards powers");
        if (hauntedQuarter != null) hauntedQuarter.useUniqueDistrictPower();
        io.printSeparator("The game is over !");
        getWinner();
    }

    //--------------------------------- POWERS -------------------------------------

    public boolean canThisPlayerPlay(Player player) {
        return !player.equals(playerThatCantPlay);
    }

    public void callCharacterCardAction(Player player) {
        switch (player.getCharacterCard().getCharacterName()) {
            case ASSASSIN:
                new Assassin(this).callCharacterCardAction(player);
                break;
            case THIEF:
                new Thief(this).callCharacterCardAction(player);
                break;
            case MAGICIAN:
                new Magician(this).callCharacterCardAction(player);
                break;
            case WARLORD:
                new Warlord(this).callCharacterCardAction(player);
                break;
            case ARCHITECT:
                new Architect(this).callCharacterCardAction(player);
                break;
            case MERCHANT:
                new Merchant(this).callCharacterCardAction(player);
                break;
        }
    }

    public void resetThePenalties() {
        this.playerThatCantPlay = null;
        this.stolenCharacter = null;
    }

    public Player updatePlayersThatCantPlay(CharacterCard characterCard) {
        playerThatCantPlay = this.getPlayerWithCharacter(characterCard);
        return playerThatCantPlay;
    }

    //--------------------------------- UNIQUE DISTRICTS ---------------------------
    public void useUniqueDistrict(Player player) {
        List<DistrictCard> districtsOfPlayer = player.getDistrictCardsBuilt();

        // searching for purple district cards
        boolean hasLaboratory = false, hasGraveyard = false, hasSmithy = false;
        for (DistrictCard districtCard : districtsOfPlayer) {
            switch (districtCard.getDistrictName()) {
                case LABORATORY:
                    hasLaboratory = true;
                    break;
                case GRAVEYARD:
                    hasGraveyard = true;
                    break;
                case SMITHY:
                    hasSmithy = true;
                    break;
            }
        }

        if (hasLaboratory) {
            new Laboratory(this).useUniqueDistrictPower(player);
        }
        if (hasGraveyard) {
            new Graveyard(this).useUniqueDistrictPower(player);
        }
        if (hasSmithy){
            new Smithy(this).useUniqueDistrictPower(player);
        }
    }


    //------------------------------------------------------------------------------

    public boolean hasThisPlayerBuiltd8Cards(Player player) {
        boolean hasBuilt8 = player.getDistrictCardsBuilt().size() >= 8;
        if (hasBuilt8) {
            playersWhoBuilt8Cards.add(player);
            io.println(player.getName() + " built 8 cards!");
        }
        return hasBuilt8;
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
        DistrictCard districtCard = player.chooseToBuildDistrict();
        boolean choice = districtCard != null;
        if (choice) {
            if (districtCard.getDistrictName().equals(DistrictName.HAUNTED_QUARTER)) {
                hauntedQuarter = new HauntedQuarter(this, round, districtCard, player);
            }
            io.println(player.getName() + " has chosen to build a district : " + districtCard);
            io.printDistrictCardsInHandOf(player);
        }
        return choice;
    }

    public CharacterCard askToChooseCharacter(Player player, List<CharacterCard> characterCardDeckOfTheRound) {
        if (characterCardDeckOfTheRound.isEmpty()) {
            throw new IllegalArgumentException("characterCardDeckOfTheRound is empty: the player " + player.getName() + " cannot choose a character card.");
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
        return player.chooseToGetTaxesAtBeginningOfTurn();
    }

    public int giveCoins(Player player) {
        return giveCoins(player, 2);
    }

    public int giveCoins(Player player, int nbCoinsToAdd) {
        if (nbCoinsToAdd == 1) {
            io.println(player.getName() + " receives " + nbCoinsToAdd + " coin");
        } else {
            io.println(player.getName() + " receives " + nbCoinsToAdd + " coins");
        }
        player.receiveCoins(nbCoinsToAdd);
        io.printCoinsOf(player);
        return nbCoinsToAdd;
    }

    public void giveCard(Player player) {
        DistrictCard card = deckOfCards.getRandomDistrictCard();
        io.println(player.getName() + " choose to draw a card");
        io.println(player.getName() + " draws: " + card);
        player.receiveCard(card);
    }

    public DistrictCard pickCard(Player player) {
        DistrictCard card1 = deckOfCards.getRandomDistrictCard();
        DistrictCard card2 = deckOfCards.getRandomDistrictCard();

        List<DistrictCard> pickedCards = new ArrayList<>();

        if (card1 != null) {
            pickedCards.add(card1);
        }
        if (card2 != null) {
            pickedCards.add(card2);
        }

        DistrictCard choosenCard = player.chooseBestDistrictCard(pickedCards);

        if (card1.equals(choosenCard)) {
            deckOfCards.putDistrictCardInDeck(card2);
        }
        if (card2.equals(choosenCard)) {
            deckOfCards.putDistrictCardInDeck(card1);
        }

        io.println(player.getName() + " choose to draw a card");
        io.println(player.getName() + " draws: " + card1 + " and " + card2);
        io.println(player.getName() + " picks: " + choosenCard);
        player.receiveCard(choosenCard);

        return choosenCard;
    }

    public void askToChooseCoinsOverDrawingACard(Player player) {
        if (player.chooseCoinsOverDrawingACard() || deckOfCards.getDistrictCards().isEmpty()) {
            giveCoins(player);
        } else {
            pickCard(player);
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
            io.printTaxesEarned(player, sum);

            io.printCoinsOf(player);
        }
    }

    public List<Player> getWinner() {
        io.println("Computing bonus points ...");
        for (int i = 0; i < playersWhoBuilt8Cards.size(); i++) {
            if (i == 0) {
                playersWhoBuilt8Cards.get(i).addPoints(4);
                io.println(playersWhoBuilt8Cards.get(i).getName() + " receives 4 bonus points because he is the first to build 8 cards");
            } else {
                playersWhoBuilt8Cards.get(i).addPoints(2);
                io.println(playersWhoBuilt8Cards.get(i).getName() + " receives 2 bonus points because he also built 8 cards");
            }
        }

        for (Player player : listOfPlayers) {
            player.getDistrictCardsBuilt().forEach(districtCard -> {
                        if (districtCard.equals(new DistrictCard(Color.PURPLE, DistrictName.SCHOOL_OF_MAGIC, 6))
                        || districtCard.equals(new DistrictCard(Color.PURPLE, DistrictName.DRAGONGATE, 6))){
                            player.addPoints(2);
                            io.println(player.getName() + " receives 2 bonus points because he built " + districtCard);
                        }
                    }
            );
        }

        List<Player> playersSorted = new ArrayList<>(listOfPlayers);
        playersSorted.sort((player0, player1) -> player1.getNbOfPoints().compareTo(player0.getNbOfPoints()));

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

    public Player getPlayerWithCharacter(CharacterCard character) {
        List<Player> players = listOfPlayers.stream()
                .filter(elem -> elem.getCharacterCard().equals(character))
                .toList();

        if (!players.isEmpty()) {
            return players.get(0);
        } else {
            return null;
        }
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

    public Player getPlayerThatCantPlay() {
        return playerThatCantPlay;
    }

    public boolean isStolenCharacter(CharacterCard characterCard) {
        return characterCard.equals(stolenCharacter);
    }

    public void setStolenCharacter(CharacterCard stolenCharacter) {
        this.stolenCharacter = stolenCharacter;
    }

    public void setPlayerThatCantPlay(Player playerThatCantPlay) {
        this.playerThatCantPlay = playerThatCantPlay;
    }

    public void setDeckOfCards(DeckOfCards deckOfCards) {
        this.deckOfCards = deckOfCards;
    }

    public IO getIO() {
        return io;
    }

    public int getNbPlayers() {
        return nbPlayers;
    }

    public Player getKingByDefault() {
        return kingByDefault;
    }

    public CharacterCard getCharacterKilled() {
        return characterKilled;
    }

    public CharacterCard getStolenCharacter() {
        return stolenCharacter;
    }

    public Random getRandom() {
        return random;
    }

    public int getRound() {
        return round;
    }

    public void setPlayersWhoBuilt8Cards(List<Player> playersWhoBuilt8Cards) {
        this.playersWhoBuilt8Cards = playersWhoBuilt8Cards;
    }

    // legacy methods

    public void giveMoneyToThief(Player player, Player player2) {
        new Thief(this).giveMoneyToThief(player, player2);
    }

    public void warlordRemoveDistrictCardOfPlayer(Player warlord, Player player1) {
        new Warlord(this).warlordRemoveDistrictCardOfPlayer(warlord, player1);
    }

    public List<Player> canWarlordDestroyACardFromCharacter(Player warlord, List<Player> players) {
        return new Warlord(this).canWarlordDestroyACardFromCharacter(warlord, players);
    }

    public void give2DistrictCardsToArchitect(Player player) {
        new Architect(this).give2DistrictCardsToArchitect(player);
    }

    public void changeCardMagician(Player player) {
        new Magician(this).changeCardMagician(player);
    }

    public void giveDeckToMagician(Player player, Player player2) {
        new Magician(this).giveDeckToMagician(player, player2);
    }
}
