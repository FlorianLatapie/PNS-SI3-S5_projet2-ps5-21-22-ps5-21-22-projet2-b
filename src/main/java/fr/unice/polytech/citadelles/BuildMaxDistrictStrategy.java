package fr.unice.polytech.citadelles;

import java.util.*;
import java.util.Map.Entry;
import java.util.function.Function;
import java.util.stream.Collectors;

public class BuildMaxDistrictStrategy implements Strategy {
    private Player player;
    private Random random;

    @Override
    public CharacterCard chooseCharacter(List<CharacterCard> characterCardDeckOfTheGame) {
        CharacterCard favChar = new CharacterCard(CharacterName.MERCHANT);
        List<DistrictCard> builtDistricts = player.getDistrictCardsBuilt();

        if(characterCardDeckOfTheGame.contains(favChar)){
            return favChar;
        } else {
            if(!builtDistricts.isEmpty()){
                Color mostFrequentColor = mostCommonColorInBuiltDistricts(builtDistricts);
                for(CharacterCard characterCard : characterCardDeckOfTheGame){
                    if(characterCard.getColor().equals(mostFrequentColor)){
                        return characterCard;
                    }
                }
            }

            return characterCardDeckOfTheGame.get(random.nextInt(0, characterCardDeckOfTheGame.size()));
        }
    }

    @Override
    public boolean getCoinsOverDrawingACard() {
        List<DistrictCard> districtCardsInHand = player.getDistrictCardsInHand();
        if(districtCardsInHand.isEmpty()){
            return false;
        } else {
            return true;
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

        if(!player.canBuildDistrict(cheapestCardInHand)){
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

    public Color mostCommonColorInBuiltDistricts(List<DistrictCard> builtDistricts){
        if(builtDistricts.isEmpty()){
            throw new RuntimeException("Most common color in built districts excepetion : No districts are built yet");
        }

        Map<Color, Integer> search = new HashMap<>();

        for(DistrictCard d : builtDistricts){
            Integer nbItems = search.get(d.getColor());
            search.put(d.getColor(), nbItems == null ? 1 : nbItems+1);
        }

        Entry<Color, Integer> mostFrequent = null;

        for (Entry<Color, Integer> current : search.entrySet()) {
            if (mostFrequent == null || current.getValue() > mostFrequent.getValue())
                mostFrequent = current;
        }

        return mostFrequent.getKey();
    }

    public DistrictCard getCheapestDistrictCard(List<DistrictCard> districtCardsInHand){
        return districtCardsInHand.stream()
                .min(Comparator.comparing(DistrictCard::getPriceToBuild))
                .orElseThrow(NoSuchElementException::new);
    }
}
