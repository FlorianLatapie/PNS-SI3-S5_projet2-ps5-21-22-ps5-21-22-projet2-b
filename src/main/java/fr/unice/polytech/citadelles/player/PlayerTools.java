package fr.unice.polytech.citadelles.player;

import fr.unice.polytech.citadelles.card.DistrictCard;
import fr.unice.polytech.citadelles.enums.Color;

import java.util.*;

public class PlayerTools {
    private Player player;

    private PlayerTools() {
    }

    public PlayerTools(Player player) {
        this.player = player;
    }

    public Color mostCommonColorInBuiltDistricts() {
        List<DistrictCard> builtDistricts = player.getDistrictCardsBuilt();
        int redCount = 0, greenCount = 0, blueCount = 0, yellowCount = 0;
        List<Integer> countOfEachColor = new ArrayList<>(List.of(redCount, greenCount, blueCount, yellowCount));
        List<Color> colorsToSearch = new ArrayList<>(List.of(Color.values()));
        colorsToSearch.remove(Color.GREY);
        colorsToSearch.remove(Color.PURPLE);

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

    public DistrictCard getCheapestDistrictCard() {
        List<DistrictCard> districtCardsInHand = player.getDistrictCardsInHand();
        List<DistrictCard> copy = new ArrayList<>(districtCardsInHand);
        copy.removeAll(player.getDistrictCardsBuilt());
        return copy.stream()
                .min(Comparator.comparing(DistrictCard::getPriceToBuild))
                .orElse(null);
    }

    public DistrictCard getCheapestDistrictCard(List<DistrictCard> districtCards) {
        return districtCards.stream()
                .min(Comparator.comparing(DistrictCard::getPriceToBuild))
                .orElse(null);
    }

    public List<DistrictCard> getDistrictCardsInHandSorted() {
        if (player.getDistrictCardsInHand().isEmpty()) return new ArrayList<>();
        return player.getDistrictCardsInHand()
                .stream()
                .sorted(Comparator.comparing(DistrictCard::getPriceToBuild))
                .toList();
    }

    public DistrictCard getCheapestCardInHand() {
        if (getDistrictCardsInHandSorted().isEmpty()) return null;
        return getDistrictCardsInHandSorted().get(0);
    }

    public Map<Color, Integer> numberOfDistrictCardsBuiltByColor() {
        Map<Color, Integer> mapColorNumber = new HashMap<>();
        for (Color color : Color.values()) {
            int nb = 0;
            for (DistrictCard card : player.getDistrictCardsBuilt()) {
                if (card.getColor() == color) {
                    nb++;
                }
            }
            mapColorNumber.put(color, nb);
        }
        return mapColorNumber;
    }
}
