package fr.unice.polytech.citadelles;

import java.util.ArrayList;
import java.util.List;

public class Player {
    private List<DistrictCard> districtCardsInHand;
    private List<DistrictCard> districtCardsBuild = new ArrayList<>();
    private int coins;

    public Player(List<DistrictCard> districtCards) {
        this(districtCards, Integer.MAX_VALUE);
    }

    public Player(List<DistrictCard> districtCards, int coins) {
        this.coins = coins;
        this.districtCardsInHand = new ArrayList<>();
        for (DistrictCard card : districtCards) {
            districtCardsInHand.add(card);
        }
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

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Player) {
            Player playerToCompare = (Player) obj;
            return this.getDistrictCardsInHand().equals(playerToCompare.getDistrictCardsInHand());
        }
        return false;
    }
}
