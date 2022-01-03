package fr.unice.polytech.citadelles.player;

import fr.unice.polytech.citadelles.card.CharacterCard;
import fr.unice.polytech.citadelles.card.DeckOfCards;
import fr.unice.polytech.citadelles.card.DistrictCard;
import fr.unice.polytech.citadelles.enums.CharacterName;
import fr.unice.polytech.citadelles.enums.Color;
import fr.unice.polytech.citadelles.enums.DistrictName;
import fr.unice.polytech.citadelles.strategy.CompleteStrategy;
import fr.unice.polytech.citadelles.strategy.Strategy;
import fr.unice.polytech.citadelles.strategy.buildstrats.BuildStrat;
import fr.unice.polytech.citadelles.strategy.characterstrats.CharacterStrat;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Random;

public class Player {
    private List<DistrictCard> districtCardsInHand;
    private List<DistrictCard> districtCardsBuilt;
    private List<DistrictCard> destroyedDistricts;
    private int coins;
    private String name;
    private Random random;
    private CharacterCard characterCard;
    private Strategy strategy;
    private PlayerTools playerTools = new PlayerTools(this);
    private int bonusPoints = 0;

    public Player(String name) {
        this(name, new ArrayList<>(), 2, new Random());
    }

    public Player(String name, List<DistrictCard> districtCards) {
        this(name, districtCards, 2, new Random());
    }

    public Player(String name, List<DistrictCard> districtCards, int coins) {
        this(name, districtCards, coins, new Random());
    }

    public Player(String name, List<DistrictCard> districtCards, int coins, Random random) {
        this(name, districtCards, coins, random, new CompleteStrategy());
    }

    public Player(String name, List<DistrictCard> districtCards, int coins, Random random, Strategy strategy) {
        this.name = name;
        this.coins = coins;
        this.random = random;

        districtCardsInHand = new ArrayList<>(districtCards);
        districtCardsBuilt = new ArrayList<>();
        destroyedDistricts = new ArrayList<>();

        this.strategy = strategy;
        strategy.init(this, random, new CharacterStrat(this), new BuildStrat(this, random));
    }

    //---------------------------  Coins ... ---------------------------

    public void receiveCoins(int nbCoins) {
        this.coins += nbCoins;
    }

    public boolean removeCoins(int nbCoins) {
        if (this.coins - nbCoins >= 0) {
            this.coins -= nbCoins;
            return true;
        } else {
            throw new IllegalArgumentException("It is impossible to remove coins because the player " + this.name + " does not have enough coins: "
                    + this.coins + "-" + nbCoins + " = " + (this.coins - nbCoins) + " is less than 0");
        }
    }

    //---------------------------  Cards ... ---------------------------
    public boolean receiveCard(DistrictCard districtCard) {
        if (districtCard == null) {
            return false;
        } else {
            return districtCardsInHand.add(districtCard);
        }
    }

    public void receiveCards(List<DistrictCard> districtCards){
        districtCards.forEach(districtCard -> receiveCard(districtCard));
    }

    public void buildDistrictCardsInHand(DistrictCard cardToBuild) {
        removeCoins(cardToBuild.getPriceToBuild());
        districtCardsInHand.remove(cardToBuild);
        districtCardsBuilt.add(cardToBuild);
    }

    public boolean canBuildDistrict(DistrictCard district) {
        return coins >= district.getPriceToBuild() && isAllowedToBuildDistrict(district);
    }

    //---------------------------  Choices ... ---------------------------

    public CharacterCard chooseCharacter(List<CharacterCard> characterCardDeckOfTheGame) {
        if (characterCardDeckOfTheGame.isEmpty()) {
            throw new IllegalArgumentException("Character deck of card is empty, " + name + " cannot choose a card");
        }
        CharacterCard choice = strategy.chooseCharacter(characterCardDeckOfTheGame);
        characterCard = choice;
        return choice;
    }

    public DistrictCard chooseToBuildDistrict() {
        return strategy.buildDistrict();
    }

    public boolean chooseToGetTaxesAtBeginningOfTurn() {
        return strategy.getTaxesAtBeginningOfTurn();
    }

    public boolean chooseCoinsOverDrawingACard() {
        return strategy.getCoinsOverDrawingACard();
    }

    public DistrictCard chooseCardToDiscard() {
        return playerTools.getCheapestDistrictCard();
    }

    public DistrictCard chooseBestDistrictCard(List<DistrictCard> districtCards) {
        return strategy.chooseBestDistrictCard(districtCards);
    }

    public boolean chooseToExchangeCoinsForCards(){
        return strategy.chooseToExchangeCoinsForCards();
    }

    public List<DistrictCard> pickCard(DeckOfCards deckOfCards) {
        DistrictCard library = new DistrictCard(Color.PURPLE, DistrictName.LIBRARY, 5);
        DistrictCard observatory = new DistrictCard(Color.PURPLE, DistrictName.OBSERVATORY, 5);

        DistrictCard card1 = deckOfCards.getRandomDistrictCard();
        DistrictCard card2 = deckOfCards.getRandomDistrictCard();

        List<DistrictCard> seenCards = new ArrayList<>();

        if (card1 != null) {
            seenCards.add(card1);
        }
        if (card2 != null) {
            seenCards.add(card2);
        }

        List<DistrictCard> chosenCards = new ArrayList<>();

        if(getDistrictCardsBuilt().contains(library) && getDistrictCardsBuilt().contains(observatory)) {
            return pickCardObservatoryAndLibrary(deckOfCards, seenCards, chosenCards);
        }

        if(getDistrictCardsBuilt().contains(observatory)){
            return pickCardObservatory(deckOfCards, seenCards, chosenCards);
        }

        if(getDistrictCardsBuilt().contains(library)) {
            receiveCards(seenCards);
            return seenCards;
        }

        DistrictCard chosenCard = chooseBestDistrictCard(seenCards);
        chosenCards.add(chosenCard);

        if (Objects.equals(card1, chosenCard)) {
            deckOfCards.putDistrictCardInDeck(card2);
        }
        if (Objects.equals(card2, chosenCard)) {
            deckOfCards.putDistrictCardInDeck(card1);
        }

        receiveCard(chosenCard);
        return chosenCards;
    }

    public List<DistrictCard> pickCardObservatoryAndLibrary(DeckOfCards deckOfCards, List<DistrictCard> seenCards, List<DistrictCard> chosenCards) {
        DistrictCard card3 = deckOfCards.getRandomDistrictCard();
        if (card3 != null) {
            seenCards.add(card3);
        }
        DistrictCard chosenCard1 = chooseBestDistrictCard(seenCards);
        seenCards.remove(chosenCard1);
        DistrictCard chosenCard2 = chooseBestDistrictCard(seenCards);
        seenCards.remove(chosenCard2);

        chosenCards.add(chosenCard1);
        chosenCards.add(chosenCard2);

        for(DistrictCard card : seenCards){
            if(!(chosenCards.contains(card))){
                deckOfCards.putDistrictCardInDeck(card);
            }
        }

        receiveCards(chosenCards);
        return chosenCards;
    }

    public List<DistrictCard> pickCardObservatory(DeckOfCards deckOfCards, List<DistrictCard> seenCards, List<DistrictCard> chosenCards) {
        DistrictCard card3 = deckOfCards.getRandomDistrictCard();
        if (card3 != null) {
            seenCards.add(card3);
        }
        DistrictCard chosenCard = chooseBestDistrictCard(seenCards);
        for(DistrictCard card : seenCards){
            if(!(card.equals(chosenCard))){
                deckOfCards.putDistrictCardInDeck(card);
            }
        }
        receiveCard(chosenCard);
        return chosenCards;
    }


        //--------------------------- CharacterCard powers / actions  ---------------------------
    public CharacterCard killCharacterCard(List<CharacterCard> killableCharacterCards) {
        return strategy.killCharacterCard(killableCharacterCards);
    }

    public CharacterCard stealCharacterCard(List<CharacterCard> ableToStealCharacterCards) {
        return strategy.stealCharacterCard(ableToStealCharacterCards);
    }

    public Player magicianMove(List<Player> players) {
        return strategy.magicianMove(players);
    }

    public Player warlordChoosePlayer(List<Player> players) {
        return strategy.getSometimesRandomPlayer(players);
    }

    public DistrictCard warlordChooseDistrictToDestroy(Player player) {
        List<DistrictCard> districtCardsOfPlayerThatCanBeDestroy = new ArrayList<>(player.districtCardsBuilt);
        districtCardsOfPlayerThatCanBeDestroy = districtCardsOfPlayerThatCanBeDestroy.stream()
                .filter(c -> c.getPriceToBuild() - 1 <= coins)
                .filter(c -> c.getDistrictName()!= DistrictName.KEEP) //merveille donjon
                .toList();
        DistrictCard districtCardChoose = strategy.warlordChooseDistrictToDestroy(districtCardsOfPlayerThatCanBeDestroy);
        return districtCardChoose;
    }

    public boolean drawADistrictCard(DistrictCard card) {
        return receiveCard(card);
    }

    public DistrictCard chooseToRepairDistrict() {
        if (characterCard == null){
            throw new RuntimeException(name + " has no character card, therefore he cannot repair a district");
        }

        if (!characterCard.equals(new CharacterCard(CharacterName.WARLORD))) {
            DistrictCard districtCardToRepair = strategy.repairDistrict(destroyedDistricts);

            if (districtCardToRepair == null) {
                return null;
            }

            if (coins > 0) {
                districtCardsBuilt.add(districtCardToRepair);
                destroyedDistricts.remove(districtCardToRepair);
                removeCoins(1);
                return districtCardToRepair;
            } else {
                return null;
            }
        } else {
            return null;
        }
    }

    //--------------------------- Points ... ---------------------------
    public int getSumOfCardsBuilt() {
        return districtCardsBuilt.stream().mapToInt(DistrictCard::getPriceToBuild).sum();
    }

    public Integer getNbOfPoints() { // Integer instead of int in order to use the .compareTo() method used in GameEngine
        return this.getSumOfCardsBuilt() + this.bonusPoints;
    }

    public void addPoints(int nbPointsToAdd) {
        this.bonusPoints += nbPointsToAdd;
    }

    //---------------------------  Getter, Setters, Overrides ... ---------------------------

    public Random getRandom() {
        return random;
    }

    public String getName() {
        return name;
    }

    public int getCoins() {
        return coins;
    }

    public CharacterCard getCharacterCard() {
        return characterCard;
    }

    public List<DistrictCard> getDistrictCardsBuilt() {
        return districtCardsBuilt;
    }

    public void setDistrictCardsBuilt(List<DistrictCard> districtCardsBuilt) {
        this.districtCardsBuilt = districtCardsBuilt;
    }

    public void setDistrictCardsInHand(List<DistrictCard> districtCardsInHand) {
        this.districtCardsInHand = districtCardsInHand;
    }

    public List<DistrictCard> getDistrictCardsInHand() {
        return districtCardsInHand;
    }

    public Strategy getStrategy() {
        return strategy;
    }

    public void setCharacterCard(CharacterCard characterCard) {
        this.characterCard = characterCard;
    }

    public List<DistrictCard> getDestroyedDistricts() {
        return destroyedDistricts;
    }

    public void setDestroyedDistricts(List<DistrictCard> destroyedDistricts) {
        this.destroyedDistricts = destroyedDistricts;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Player)) return false;
        Player player = (Player) o;
        return getCoins() == player.getCoins() && bonusPoints == player.bonusPoints && Objects.equals(getDistrictCardsInHand(), player.getDistrictCardsInHand()) && Objects.equals(getDistrictCardsBuilt(), player.getDistrictCardsBuilt()) && Objects.equals(destroyedDistricts, player.destroyedDistricts) && Objects.equals(getName(), player.getName()) && Objects.equals(getRandom(), player.getRandom()) && Objects.equals(getCharacterCard(), player.getCharacterCard());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getDistrictCardsInHand(), getDistrictCardsBuilt(), getDestroyedDistricts(), getCoins(), getName(), getRandom(), getCharacterCard(), playerTools, bonusPoints);
    }

    @Override
    public String toString() {
        return "Player " + name + "{" + System.lineSeparator() +
                "districtCardsInHand=" + districtCardsInHand + "," + System.lineSeparator() +
                "districtCardsBuilt=" + districtCardsBuilt + "," + System.lineSeparator() +
                "destroyedDistricts=" + destroyedDistricts + "," + System.lineSeparator() +
                "coins=" + coins + "," + System.lineSeparator() +
                "random=" + random + "," + System.lineSeparator() +
                "characterCard=" + characterCard + "," + System.lineSeparator() +
                "strategy=" + strategy.getClass().getSimpleName() + "," + System.lineSeparator() +
                "bonusPoints=" + bonusPoints + System.lineSeparator() +
                '}';
    }

    public boolean isAllowedToBuildDistrict(DistrictCard districtCard) {
        return !districtCardsBuilt.contains(districtCard);
    }

    public DistrictCard changeCardToOther() {
        return strategy.changeCardToOther();
    }

    public void removeDistrictCardBuilt(DistrictCard districtCardToRemove) {
        districtCardsBuilt.remove(districtCardToRemove);
        destroyedDistricts.add(districtCardToRemove);
    }
}
