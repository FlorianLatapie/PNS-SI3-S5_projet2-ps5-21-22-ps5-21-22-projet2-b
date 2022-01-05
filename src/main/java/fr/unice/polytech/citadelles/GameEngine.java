package fr.unice.polytech.citadelles;

import fr.unice.polytech.citadelles.card.CharacterCard;
import fr.unice.polytech.citadelles.card.DeckOfCards;
import fr.unice.polytech.citadelles.card.DistrictCard;
import fr.unice.polytech.citadelles.card.unique_districts.*;
import fr.unice.polytech.citadelles.character.*;
import fr.unice.polytech.citadelles.enums.CharacterName;
import fr.unice.polytech.citadelles.enums.Color;
import fr.unice.polytech.citadelles.enums.DistrictName;
import fr.unice.polytech.citadelles.io.IO;
import fr.unice.polytech.citadelles.player.Player;
import fr.unice.polytech.citadelles.strategy.CompleteStrategy;
import fr.unice.polytech.citadelles.strategy.Strategy;
import fr.unice.polytech.citadelles.strategy.buildstrats.BuildMaxDistrictStrategy;
import fr.unice.polytech.citadelles.strategy.characterstrats.CharacterStrat;
import fr.unice.polytech.citadelles.strategy.characterstrats.SuperCharacterStrat;

import java.util.*;
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
    List<Player> listOfPlayersSorted;

    private Player playerThatCantPlay = null;
    private Player kingOfTheLastRound;
    private Player kingByDefault;

    private DeckOfCards deckOfCards;

    private CharacterCard characterKilled;
    private CharacterCard stolenCharacter;

    private HauntedQuarter hauntedQuarter;

    private Random random;

    private int nbOfDistrictsToWin = 8;

    // constructors
    @Deprecated
    public GameEngine() {
        this(7, new Random());
    }

    @Deprecated
    public GameEngine(int nbPlayers, Random random) {
        if (nbPlayers > 8 || nbPlayers < 3) {
            throw new IllegalArgumentException("Illegal number of players : " + nbPlayers);
        }

        this.random = random;
        this.nbPlayers = nbPlayers;
        listOfPlayers = new ArrayList<>();
        io = new IO();
        playersWhoBuilt8Cards = new ArrayList<>();
        deckOfCards = new DeckOfCards(random);

        initPlayers();
    }

    public GameEngine(Random random, Player... players) {
        this(random, new DeckOfCards(random), new IO(), false, players);
    }

    public GameEngine(Random random, boolean initPlayers, Player... players) {
        this(random, new DeckOfCards(random), new IO(), initPlayers, players);
    }

    public GameEngine(Random random, DeckOfCards deckOfCards, Player... players) {
        this(random, deckOfCards, new IO(), false, players);
    }

    public GameEngine(Random random, DeckOfCards deckOfCards, IO io, boolean initPlayers, Player... players) {
        this.random = random;
        this.io = io;
        playersWhoBuilt8Cards = new ArrayList<>();

        this.deckOfCards = deckOfCards;

        listOfPlayers = new ArrayList<>(List.of(players));
        kingOfTheLastRound = listOfPlayers.get(0);
        kingByDefault = listOfPlayers.get(0);
        nbPlayers = listOfPlayers.size();

        if (initPlayers) {
            for (Player p : players) {
                List<DistrictCard> districtCards = new ArrayList<>();
                for (int j = 0; j < 4; j++) {
                    districtCards.add(deckOfCards.getRandomDistrictCard());
                }
                p.setDistrictCardsInHand(districtCards);
            }
        }
    }


    public void initPlayers() {
        for (int i = 0; i < nbPlayers; i++) {
            List<DistrictCard> districtCards = new ArrayList<>();
            for (int j = 0; j < 4; j++) {
                districtCards.add(deckOfCards.getRandomDistrictCard());
            }
            Strategy buildMaxDistrictSrategy = new CompleteStrategy(new SuperCharacterStrat(this),new BuildMaxDistrictStrategy());
            Player playerToAdd = new Player("Player_" + (i + 1), districtCards, 2, random, buildMaxDistrictSrategy);
            listOfPlayers.add(playerToAdd);
            if (i == 0) {
                kingOfTheLastRound = playerToAdd;
                kingByDefault = playerToAdd;
            }
        }

    }

    // game

    public List<Player> launchGame() {
        round = 1;

        io.printSeparator("The game starts !");

        while (playersWhoBuilt8Cards.isEmpty()) {
            io.printSeparator("Start of the round " + round);
            resetThePenalties();

            listOfPlayersSorted = askPlayersRoleAndSortThemByRole(deckOfCards.getNewCharacterCards());// is a new copy of the 8 characters each new round
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
        return getWinner();
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
        boolean hasLaboratory = false, hasGraveyard = false, hasSmithy = false, hasSchoolOfMagic = false;
        // this weird switch case is because "districtsOfPlayer" might be updated by using the cards below
        // if its updated the for loop might break
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
                case SCHOOL_OF_MAGIC:
                    hasSchoolOfMagic = true;
                    break;
            }
        }

        if (hasLaboratory) {
            new Laboratory(this).useUniqueDistrictPower(player);
        }
        if (hasGraveyard) {
            new Graveyard(this).useUniqueDistrictPower(player);
        }
        if (hasSmithy) {
            new Smithy(this).useUniqueDistrictPower(player);
        }
        if (hasSchoolOfMagic) {
            new SchoolOfMagic(this).useUniqueDistrictPower(player);
        }
    }


    //------------------------------------------------------------------------------

    public boolean hasThisPlayerBuiltd8Cards(Player player) {
        boolean hasBuilt8 = player.getDistrictCardsBuilt().size() >= nbOfDistrictsToWin;
        if (hasBuilt8) {
            playersWhoBuilt8Cards.add(player);
            io.println(player.getName() + " built "+nbOfDistrictsToWin+" cards!");
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

        listOfPlayersSorted = sortPlayerListByCharacterSequence();
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

    public List<DistrictCard> pickCard(Player player) {
        List<DistrictCard> districtCardsPicked = player.pickCard(deckOfCards);
        io.println(player.getName() + " choose to draw a card");

        districtCardsPicked.forEach(districtCard -> {
            io.println(player.getName() + " picks: " + districtCard);
        });

        return districtCardsPicked;
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
        for (Player winner : playersWhoBuilt8Cards) {
            if (playersWhoBuilt8Cards.indexOf(winner) == 0) {
                winner.addPoints(4);
                io.println(winner.getName() + " receives 4 bonus points because he is the first to build 8 cards");
            } else{
                winner.addPoints(2);
                io.println(winner.getName() + " receives 2 bonus points because he also built 8 cards");
            }
        }


        for (Player player : listOfPlayers) {
            if (checkIfPlayerFinished5Colors(player)) {
                player.addPoints(3);
                io.println(player.getName() + " receives 3 bonus points because he built 5 district cards with different colors");
            }

            player.getDistrictCardsBuilt().forEach(districtCard -> {
                        if (districtCard.equals(new DistrictCard(Color.PURPLE, DistrictName.UNIVERSITY, 6))
                                || districtCard.equals(new DistrictCard(Color.PURPLE, DistrictName.DRAGONGATE, 6))) {
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

    public boolean checkIfPlayerFinished5Colors(Player player) {
        Set<Enum> colorSet = new HashSet<>();
        if (player.getDistrictCardsBuilt().isEmpty()) return false;
        player.getDistrictCardsBuilt().forEach(c -> colorSet.add(c.getColor()));
        if (colorSet.size() == 5) return true;
        return false;
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

    public int getNbOfDistrictsToWin(){ return nbOfDistrictsToWin;}

    public List<Player> getListOfPlayersSorted() {
        return listOfPlayersSorted;
    }

    public void setPlayersWhoBuilt8Cards(List<Player> playersWhoBuilt8Cards) {
        this.playersWhoBuilt8Cards = playersWhoBuilt8Cards;
    }
}
