package fr.unice.polytech.citadelles;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Player {
    private List<DistrictCard> districtCardsInHand;
    private List<DistrictCard> districtCardsBuilt;
    private int coins;
    private String name;

    public Player(String name, List<DistrictCard> districtCards) {
        this(name, districtCards, Integer.MAX_VALUE);
    }

    public Player(String name, List<DistrictCard> districtCards, int coins) {
        this.name = name;
        this.coins = coins;
        districtCardsInHand = new ArrayList<>(districtCards);
        districtCardsBuilt = new ArrayList<>();
    }

    public List<DistrictCard> getDistrictCardsInHand() {
        return districtCardsInHand;
    }

    void buildDistrictCardsInHand(DistrictCard cardToBuild){
        this.coins -= cardToBuild.getPriceToBuild();
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

    public String getName() {
        return name;
    }

    public boolean chooseToBuildDistrict(){
        boolean choice = new Random().nextBoolean();
        DistrictCard card = districtCardsInHand.get(new Random().nextInt(0, districtCardsInHand.size()));
        if(choice){
            buildDistrictCardsInHand(card);
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
}
