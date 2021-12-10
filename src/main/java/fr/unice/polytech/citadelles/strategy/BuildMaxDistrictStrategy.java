package fr.unice.polytech.citadelles.strategy;

import fr.unice.polytech.citadelles.card.CharacterCard;
import fr.unice.polytech.citadelles.card.DistrictCard;
import fr.unice.polytech.citadelles.enums.CharacterName;
import fr.unice.polytech.citadelles.enums.Color;
import fr.unice.polytech.citadelles.player.Player;
import fr.unice.polytech.citadelles.player.PlayerTools;

import java.util.*;

public class BuildMaxDistrictStrategy extends Strategy {
    private PlayerTools playerTools;

    @Override
    public CharacterCard chooseCharacter(List<CharacterCard> characterCardDeckOfTheGame) {
        CharacterCard favChar = new CharacterCard(CharacterName.MERCHANT);
        List<DistrictCard> builtDistricts = player.getDistrictCardsBuilt();

        if (characterCardDeckOfTheGame.contains(favChar)) {
            return favChar;
        } else {
            if (!builtDistricts.isEmpty()) {
                Color mostFrequentColor = playerTools.mostCommonColorInBuiltDistricts();
                for (CharacterCard characterCard : characterCardDeckOfTheGame) {
                    if (characterCard.getColor().equals(mostFrequentColor)) {
                        return characterCard;
                    }
                }
            }
            return characterCardDeckOfTheGame.get(random.nextInt(0, characterCardDeckOfTheGame.size()));
        }
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
    public void init(Player player){
        this.player = player;
        this.random = player.getRandom();
        this.playerTools = new PlayerTools(player);
    }
}
