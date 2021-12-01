package fr.unice.polytech.citadelles.strategy;

import fr.unice.polytech.citadelles.CharacterName;
import fr.unice.polytech.citadelles.*;

import java.util.*;

public class BuildMaxDistrictStrategy implements Strategy {
    private Player player;
    private Random random;

    @Override
    public CharacterCard chooseCharacter(List<CharacterCard> characterCardDeckOfTheGame) {
        CharacterCard favChar = new CharacterCard(CharacterName.MERCHANT);
        List<DistrictCard> builtDistricts = player.getDistrictCardsBuilt();

        if (characterCardDeckOfTheGame.contains(favChar)) {
            return favChar;
        } else {
            if (!builtDistricts.isEmpty()) {
                Color mostFrequentColor = mostCommonColorInBuiltDistricts(builtDistricts);
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
        if(!player.getDistrictCardsInHand().isEmpty()){ // get coins if cards in hand are not empty
            DistrictCard districtCard = player.getDistrictCardsInHandSorted().get(0);
            return player.isAllowedToBuildDistrict(districtCard) && !player.canBuildDistrict(districtCard);
        }
        else{
            return false;
        }
    }

    @Override
    public boolean getTaxesAtBeginingOfTurn() {
        return true;
    }

    @Override
    public boolean buildDistrict() {
        List<DistrictCard> districtCardsInHand = player.getDistrictCardsInHand();

        Boolean choice = true;

        if (districtCardsInHand.isEmpty()) {
            return false;
        }

        DistrictCard cheapestCardInHand = getCheapestDistrictCard(districtCardsInHand);
        if(cheapestCardInHand==null) return false;
        if (!player.canBuildDistrict(cheapestCardInHand)) {
            return false;
        } else {
            player.buildDistrictCardsInHand(cheapestCardInHand);
        }

        return choice;
    }

    @Override
    public void init(Player player) {
        this.player = player;
        this.random = player.getRandom();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof BuildMaxDistrictStrategy)) return false;
        BuildMaxDistrictStrategy that = (BuildMaxDistrictStrategy) o;
        return Objects.equals(player, that.player) && Objects.equals(random, that.random);
    }

    public Color mostCommonColorInBuiltDistricts(List<DistrictCard> builtDistricts) {
        int redCount = 0, greenCount = 0, blueCount = 0, yellowCount = 0;
        List<Integer> countOfEachColor = new ArrayList<>(List.of(redCount, greenCount, blueCount, yellowCount));
        List<Color> colorsToSearch = new ArrayList<>(List.of(Color.values()));
        colorsToSearch.remove(Color.GREY);

        for (int i = 0; i < colorsToSearch.size(); i++) {
            for (DistrictCard districtCard : builtDistricts) {
                if (districtCard.getColor().equals(colorsToSearch.get(i))) {
                    countOfEachColor.set(i, countOfEachColor.get(i) + 1);
                }
            }
        }
        int firstMaxCount = Collections.max(countOfEachColor);
        int index = countOfEachColor.indexOf(firstMaxCount);

        return Color.values()[index];
    }

    public DistrictCard getCheapestDistrictCard(List<DistrictCard> districtCardsInHand) {
        List<DistrictCard> copy = new ArrayList<>(districtCardsInHand);
        copy.removeAll(player.getDistrictCardsBuilt());
        return copy.stream()
                .min(Comparator.comparing(DistrictCard::getPriceToBuild))
                .orElse(null);
    }
}
