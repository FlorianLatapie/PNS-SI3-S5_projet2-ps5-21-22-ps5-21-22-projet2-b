package fr.unice.polytech.citadelles.strategy.buildstrats;

import fr.unice.polytech.citadelles.card.CharacterCard;
import fr.unice.polytech.citadelles.card.DistrictCard;
import fr.unice.polytech.citadelles.enums.CharacterName;
import fr.unice.polytech.citadelles.enums.Color;
import fr.unice.polytech.citadelles.player.Player;
import fr.unice.polytech.citadelles.player.PlayerTools;
import fr.unice.polytech.citadelles.strategy.CompleteStrategy;

import java.util.*;

public class BuildMaxDistrictStrategy extends BuildStrat {
    private PlayerTools playerTools;

    public BuildMaxDistrictStrategy(Player player){

        super(player);
        this.playerTools = new PlayerTools(player);
    }

    @Override
    public boolean getCoinsOverDrawingACard() {
        if (!player.getDistrictCardsInHand().isEmpty()) { // get coins if cards in hand are not empty
            DistrictCard districtCard = playerTools.getCheapestCardInHand();
            return player.isAllowedToBuildDistrict(districtCard) && !player.canBuildDistrict(districtCard);
        } else {
            return false;
        }
    }

    @Override
    public DistrictCard chooseBestDistrictCard(List<DistrictCard> districtCards){
        return playerTools.getCheapestDistrictCard(districtCards);
    }

    @Override
    public boolean getTaxesAtBeginningOfTurn() {
        return true;
    }

    @Override
    public DistrictCard buildDistrict() {
        List<DistrictCard> districtCardsInHand = player.getDistrictCardsInHand();

        if (districtCardsInHand.isEmpty()) {
            return null;
        }

        DistrictCard cheapestCardInHand = playerTools.getCheapestDistrictCard();
        if (cheapestCardInHand == null) return null;
        if (!player.canBuildDistrict(cheapestCardInHand)) {
            return null;
        } else {
            player.buildDistrictCardsInHand(cheapestCardInHand);
        }

        return cheapestCardInHand;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof BuildMaxDistrictStrategy)) return false;
        BuildMaxDistrictStrategy that = (BuildMaxDistrictStrategy) o;
        return Objects.equals(player, that.player) && Objects.equals(random, that.random);
    }

    @Override
    public int hashCode() {
        return Objects.hash(player, random);
    }

    @Override
    public String toString() {
        return "Build Max District Strategy";
    }
}
