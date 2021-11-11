package fr.unice.polytech.citadelles;

import java.util.ArrayList;
import java.util.List;

public class Player {
    private List<DistrictCard> districtCardsInHand;

    public Player(DistrictCard... districtCards) {
        this.districtCardsInHand = new ArrayList<>();
        for (DistrictCard card : districtCards) {
            districtCardsInHand.add(card);
        }
    }

    public List<DistrictCard> getDistrictCardsInHand() {
        return districtCardsInHand;
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
