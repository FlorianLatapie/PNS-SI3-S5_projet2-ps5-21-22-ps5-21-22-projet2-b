package fr.unice.polytech.citadelles.player;

import fr.unice.polytech.citadelles.card.CharacterCard;
import fr.unice.polytech.citadelles.card.DistrictCard;
import fr.unice.polytech.citadelles.enums.Color;
import fr.unice.polytech.citadelles.enums.DistrictName;
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
    private PlayerTools playerTools = new PlayerTools(this);

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

    public DistrictCard chooseToBuildDistrict() {
        return strategy.buildDistrict();
    }

    public boolean chooseToGetTaxesAtBeginningOfTurn() {
        return strategy.getTaxesAtBeginningOfTurn();
    }

    public boolean chooseCoinsOverDrawingACard() {
        return strategy.getCoinsOverDrawingACard();
    }

    public DistrictCard chooseCardToDestroy(){
        return playerTools.getCheapestDistrictCard();
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

    public Player warlordChoosePlayer(List<Player> players) {return strategy.getSometimesRandomPlayer(players);}

    public DistrictCard warlordChooseDistrictToDestroy(Player player){
        List<DistrictCard> districtCardsOfPlayerThatCanBeDestroy = new ArrayList<>(player.districtCardsBuilt);
        districtCardsOfPlayerThatCanBeDestroy = districtCardsOfPlayerThatCanBeDestroy.stream().filter(c -> c.getPriceToBuild()-1<= coins).toList();
        DistrictCard districtCardChoose = strategy.warlordChooseDistrictToDestroy(districtCardsOfPlayerThatCanBeDestroy);
        return  districtCardChoose;
    }
    
    public boolean drawADistrictCard(DistrictCard card){
        return districtCardsInHand.add(card);
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

    public DistrictCard changeCardToOther() {
        return strategy.changeCardToOther();
    }

    public void removeDistrictCardBuilt(DistrictCard districtCardToRemove){
        districtCardsBuilt.remove(districtCardToRemove);
    }

    public Map<Color,Integer> numberOfDistrictCardsBuiltByColor() {
        Map<Color,Integer> mapColorNumber = new HashMap<>();
        for(Color color : Color.values()){
            int nb = 0;
            for(DistrictCard card : districtCardsBuilt){
                if(card.getColor() == color){
                    nb++;
                }
            }
            mapColorNumber.put(color,nb);
        }
        return mapColorNumber;
    }
}
