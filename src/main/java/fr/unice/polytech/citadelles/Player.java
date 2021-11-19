package fr.unice.polytech.citadelles;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Player {
    private List<DistrictCard> districtCardsInHand;
    private List<DistrictCard> districtCardsBuilt;
    private int coins;
    private String name;
    private Random random;

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

    public List<DistrictCard> getDistrictCardsInHand() {
        return districtCardsInHand;
    }

    void buildDistrictCardsInHand(DistrictCard cardToBuild) {
        removeCoins(cardToBuild.getPriceToBuild());
        districtCardsInHand.remove(cardToBuild);
        districtCardsBuilt.add(cardToBuild);
    }

    public List<DistrictCard> getDistrictCardsBuilt() {
        return districtCardsBuilt;
    }

    public void setDistrictCardsBuilt(List<DistrictCard> districtCardsBuilt) {
        this.districtCardsBuilt = districtCardsBuilt;
    }

    public int getCoins() {
        return coins;
    }

    public void receiveCoins(int nbCoins) {
        this.coins += nbCoins;
    }

    public boolean removeCoins(int nbCoins) {
        if (this.coins - nbCoins >= 0) {
            this.coins -= nbCoins;
            return true;
        } else {
            throw new RuntimeException("It is impossible to remove coins because the player " + this.name + " does not have enough coins : "
                    + this.coins + "-" + nbCoins + " = " + (this.coins - nbCoins) + " is less than 0");
        }
    }

    public String getName() {
        return name;
    }

    public boolean canBuildDistrict(DistrictCard district) {
        return coins >= district.getPriceToBuild();
    }

    public boolean chooseToBuildDistrict() {
        boolean choice = random.nextBoolean();
        DistrictCard district = districtCardsInHand.get(random.nextInt(0, districtCardsInHand.size()));

        if (!canBuildDistrict(district)) {
            choice = false;
            return choice;
        } else {
            if (choice) {
                buildDistrictCardsInHand(district);
            }
        }
        return choice;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Player) {
            Player playerToCompare = (Player) obj;
            return this.getDistrictCardsInHand().equals(playerToCompare.getDistrictCardsInHand());
        }
        return false;
    }

    public int getSumOfCardsBuilt() {
        return districtCardsBuilt.stream().mapToInt(DistrictCard::getPriceToBuild).sum();
    }

    public Integer getNbOfPoints() { // Integer au lieu de int pour avoir la méthode .compareTo() utilisé dans GameEngine
        return this.getSumOfCardsBuilt(); // comme ca quand on aura les roles il suffit d'ajouter des méthodes et de les additionner
    }

}
