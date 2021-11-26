package fr.unice.polytech.citadelles;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Random;

public class Player {
    private List<DistrictCard> districtCardsInHand;
    private List<DistrictCard> districtCardsBuilt;
    private int coins;
    private String name;
    private Random random;
    private CharacterCard characterCard;

    public Player(String name, List<DistrictCard> districtCards) {
        this(name, districtCards, 2, new Random());
    }

    public Player(String name, List<DistrictCard> districtCards, int coins) {
        this(name, districtCards, coins, new Random());
    }

    public Player(String name, List<DistrictCard> districtCards, int coins, Random random) {
        this.name = name;
        this.coins = coins;
        this.random = random;
        districtCardsInHand = new ArrayList<>(districtCards);
        districtCardsBuilt = new ArrayList<>();
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
    public void receiveCard(DistrictCard districtCard){districtCardsInHand.add(districtCard);}


    //---------------------------  Choices ... ---------------------------

    public CharacterCard chooseCharacter(List<CharacterCard> characterCardDeckOfTheGame) {
        if (characterCardDeckOfTheGame.isEmpty()) {
            throw new IllegalArgumentException("Character deck of card is empty, " + name + " cannot choose a card");
        }
        CharacterCard choice = characterCardDeckOfTheGame.get(random.nextInt(0, characterCardDeckOfTheGame.size()));
        characterCard = choice;
        return choice;
    }

    public boolean canBuildDistrict(DistrictCard district) {
        return coins >= district.getPriceToBuild();
    }

    public boolean chooseToBuildDistrict() {
        boolean choice = random.nextBoolean();

        if (districtCardsInHand.isEmpty()) {
            return false;
        }

        DistrictCard district = districtCardsInHand.get(random.nextInt(0, districtCardsInHand.size()));

        if (!canBuildDistrict(district)) {
            return false;
        } else {
            if (choice) {
                buildDistrictCardsInHand(district);
            }
        }
        return choice;
    }

    void buildDistrictCardsInHand(DistrictCard cardToBuild) {
        removeCoins(cardToBuild.getPriceToBuild());
        districtCardsInHand.remove(cardToBuild);
        districtCardsBuilt.add(cardToBuild);
    }

    public boolean chooseToGetTaxesAtBeginingOfTurn() {
        return random.nextBoolean();
    }

    public boolean canBuildADistrict(){ //check if player has enough coins to build a district
        for(DistrictCard districtCard : districtCardsInHand){
            if(canBuildDistrict(districtCard))
                return true;
        }
        return false;
    }

    public boolean chooseCoinsOrCard(){
        //return random.nextBoolean();
        return canBuildADistrict(); //si le joueur a assez de pièces pour construire un des quartiers alors il pioche une carte
    }
    //---------------------------  Getter, Setters, Overrides ... ---------------------------
    public int getSumOfCardsBuilt() {
        return districtCardsBuilt.stream().mapToInt(DistrictCard::getPriceToBuild).sum();
    }

    public Integer getNbOfPoints() { // Integer au lieu de int pour avoir la méthode .compareTo() utilisé dans GameEngine
        return this.getSumOfCardsBuilt(); // comme ca quand on aura les roles il suffit d'ajouter des méthodes et de les additionner
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
}
