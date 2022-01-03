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

    public BuildStrat(Player player, Random random){
        this.player = player;
        this.random = random;
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
        int choice = random.nextInt(0, districtCards.size()-1);
        return districtCards.get(choice);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof BuildStrat)) return false;
        BuildStrat that = (BuildStrat) o;
        return Objects.equals(player, that.player) && Objects.equals(random, that.random);
    }

    @Override
    public int hashCode() {
        return Objects.hash(player, random);
    }

    @Override
    public String toString() {
        return "random Build Strategy";
    }
}
