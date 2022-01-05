package fr.unice.polytech.citadelles.strategy.buildstrats;

import fr.unice.polytech.citadelles.card.DistrictCard;
import fr.unice.polytech.citadelles.player.Player;
import fr.unice.polytech.citadelles.strategy.CompleteStrategy;

import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.Random;

public class BuildStrat {
    Player player;
    Random random;

    public BuildStrat(){
    }

    public void init(Player player) {
        this.player = player;
        this.random = player.getRandom();
    }

    public boolean getCoinsOverDrawingACard() {
        return random.nextBoolean();
    }

    public boolean getTaxesAtBeginningOfTurn() {
        return random.nextBoolean();
    }

    public DistrictCard buildDistrict() {
        List<DistrictCard> districtCardsInHand = player.getDistrictCardsInHand();

        boolean choice = random.nextBoolean();

        if (districtCardsInHand.isEmpty()) {
            return null;
        }

        DistrictCard district = districtCardsInHand.get(random.nextInt(0, districtCardsInHand.size()));

        if (!player.canBuildDistrict(district)) {
            return null;
        } else {
            if (choice) {
                player.buildDistrictCardsInHand(district);
            }
        }
        return district;
    }

    public DistrictCard chooseBestDistrictCard(List<DistrictCard> districtCards) {
        if (districtCards.isEmpty()){
            return null;
        }
        int choice = random.nextInt(0, districtCards.size());
        return districtCards.get(choice);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof BuildStrat)) return false;
        return true;
    }

    @Override
    public int hashCode() {
        return 1;
    }

    @Override
    public String toString() {
        return "random Build Strategy";
    }
}
