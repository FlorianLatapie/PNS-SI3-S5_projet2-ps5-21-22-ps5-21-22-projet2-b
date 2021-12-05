package fr.unice.polytech.citadelles.player;

import fr.unice.polytech.citadelles.card.CharacterCard;
import fr.unice.polytech.citadelles.card.DistrictCard;
import fr.unice.polytech.citadelles.strategy.RandomStrategy;
import fr.unice.polytech.citadelles.strategy.Strategy;

import java.util.*;

public class Player {
    private List<DistrictCard> districtCardsInHand;
    private List<DistrictCard> districtCardsBuilt;
    private int coins;
    private String name;
    private Random random;
    private CharacterCard characterCard;
    private Strategy strategy;

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
        this(name, districtCards, coins, random, new RandomStrategy());
    }

    public Player(String name, List<DistrictCard> districtCards, int coins, Random random, Strategy strategy) {
        this.name = name;
        this.coins = coins;
        this.random = random;
        districtCardsInHand = new ArrayList<>(districtCards);
        districtCardsBuilt = new ArrayList<>();
        this.strategy = strategy;
        strategy.init(this);
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
    public void receiveCard(DistrictCard districtCard) {
        districtCardsInHand.add(districtCard);
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

    public boolean chooseToBuildDistrict() {
        return strategy.buildDistrict();
    }

    public boolean chooseToGetTaxesAtBeginningOfTurn() {
        return strategy.getTaxesAtBeginningOfTurn();
    }

    public boolean chooseCoinsOverDrawingACard() {
        return strategy.getCoinsOverDrawingACard();
    }

    //--------------------------- CharacterCard powers / actions  ---------------------------
    public CharacterCard killCharacterCard(List<CharacterCard> killableCharacterCards) {
        return strategy.killCharacterCard(killableCharacterCards);
    }

    //---------------------------  Getter, Setters, Overrides ... ---------------------------
    public int getSumOfCardsBuilt() {
        return districtCardsBuilt.stream().mapToInt(DistrictCard::getPriceToBuild).sum();
    }

    public Integer getNbOfPoints() { // Integer instead of int in order to use the .compareTo() method used in GameEngine
        return this.getSumOfCardsBuilt();
    }

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

    public List<DistrictCard> getDistrictCardsInHand() {
        return districtCardsInHand;
    }

    public List<DistrictCard> getDistrictCardsInHandSorted() {
        return getDistrictCardsInHand()
                .stream()
                .sorted(Comparator.comparing(DistrictCard::getPriceToBuild))
                .toList();
    }

    public Strategy getStrategy() {
        return strategy;
    }

    public void setCharacterCard(CharacterCard characterCard) {
        this.characterCard = characterCard;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Player)) return false;
        Player player = (Player) o;
        return getCoins() == player.getCoins() && Objects.equals(getDistrictCardsInHand(), player.getDistrictCardsInHand()) && Objects.equals(getDistrictCardsBuilt(), player.getDistrictCardsBuilt()) && Objects.equals(getName(), player.getName()) && Objects.equals(getCharacterCard(), player.getCharacterCard());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getDistrictCardsInHand(), getDistrictCardsBuilt(), getCoins(), getName(), getCharacterCard());
    }

    @Override
    public String toString() {
        return "Player " + name + "{" + System.lineSeparator() +
                "districtCardsInHand=" + districtCardsInHand + "," + System.lineSeparator() +
                "districtCardsBuilt=" + districtCardsBuilt + "," + System.lineSeparator() +
                "coins=" + coins + "," + System.lineSeparator() +
                "random=" + random + "," + System.lineSeparator() +
                "characterCard=" + characterCard + System.lineSeparator() +
                '}';
    }

    public boolean isAllowedToBuildDistrict(DistrictCard districtCard) {
        return !districtCardsBuilt.contains(districtCard);
    }


}
