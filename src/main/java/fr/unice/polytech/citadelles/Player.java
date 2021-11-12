package fr.unice.polytech.citadelles;

import java.util.ArrayList;
import java.util.List;

public class Player {
    private List<DistrictCard> districtCardsInHand;
    private List<DistrictCard> districtCardsBuild;
    private int coins;
    private String name;

    public Player(String name, List<DistrictCard> districtCards) {
        this(name, districtCards, Integer.MAX_VALUE);
    }

    public Player(String name, List<DistrictCard> districtCards, int coins) {
        this.name = name;
        this.coins = coins;
        districtCardsInHand = new ArrayList<>(districtCards);
        districtCardsBuild = new ArrayList<>();
    }

    public List<DistrictCard> getDistrictCardsInHand() {
        return districtCardsInHand;
    }

    void buildDistrictCardsInHand(DistrictCard cardToBuild){
        this.coins -= cardToBuild.getPriceToBuild();
        districtCardsInHand.remove(cardToBuild);
        districtCardsBuild.add(cardToBuild);
    }

    public List<DistrictCard> getDistrictCardsBuild() {
        return districtCardsBuild;
    }

    public int getCoins() {
        return coins;
    }

    public String getName() {
        return name;
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
