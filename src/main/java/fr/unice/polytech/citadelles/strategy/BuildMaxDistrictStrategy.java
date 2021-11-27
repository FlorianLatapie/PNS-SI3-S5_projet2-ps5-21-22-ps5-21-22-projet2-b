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
        return !player.getDistrictCardsInHand().isEmpty(); // get coins if cards in hand are not empty
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
        Map<Color, Integer> search = new HashMap<>();

        for (DistrictCard d : builtDistricts) {
            Integer nbItems = search.get(d.getColor());
            search.put(d.getColor(), nbItems == null ? 1 : nbItems + 1);
        }

        int max = 0;
        Color colorMax = null;

        for (Color c : search.keySet()) {
            if (search.get(c) > max) {
                max = search.get(c);
                colorMax = c;
            }
        }
        return colorMax;
    }

    public DistrictCard getCheapestDistrictCard(List<DistrictCard> districtCardsInHand) {
        return districtCardsInHand.stream()
                .min(Comparator.comparing(DistrictCard::getPriceToBuild))
                .orElseThrow(NoSuchElementException::new);
    }
}
