package fr.unice.polytech.citadelles.card;

import fr.unice.polytech.citadelles.enums.CharacterName;
import fr.unice.polytech.citadelles.enums.Color;
import fr.unice.polytech.citadelles.enums.DistrictName;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class DeckOfCards {
    private List<DistrictCard> districtCards;
    private List<CharacterCard> characterCardCards;
    private Random random;

    public DeckOfCards() {
        this(new Random());
    }

    public DeckOfCards(Random random) {
        districtCards = new ArrayList<>();
        characterCardCards = new ArrayList<>();
        this.random = random;
        createDistrictCards();
        createCharacterCards();
    }

    private List<DistrictCard> createDistrictCards() {
        districtCards.clear();
        for (int i = 0; i < 3; i++) { //Temple
            districtCards.add(new DistrictCard(Color.BLUE, DistrictName.TEMPLE, 1));
        }
        for (int i = 0; i < 4; i++) { //Church
            districtCards.add(new DistrictCard(Color.BLUE, DistrictName.CHURCH, 2));
        }
        for (int i = 0; i < 3; i++) { //Monastery
            districtCards.add(new DistrictCard(Color.BLUE, DistrictName.MONASTERY, 3));
        }
        for (int i = 0; i < 2; i++) { //Citadel
            districtCards.add(new DistrictCard(Color.BLUE, DistrictName.CITADEL, 4));
        }
        for (int i = 0; i < 5; i++) { //Mansion
            districtCards.add(new DistrictCard(Color.YELLOW, DistrictName.MANSION, 3));
        }
        for (int i = 0; i < 4; i++) { //Castle
            districtCards.add(new DistrictCard(Color.YELLOW, DistrictName.CASTLE, 4));
        }
        for (int i = 0; i < 2; i++) { //Palace
            districtCards.add(new DistrictCard(Color.YELLOW, DistrictName.PALACE, 5));
        }
        for (int i = 0; i < 5; i++) { //Tavern
            districtCards.add(new DistrictCard(Color.GREEN, DistrictName.TAVERN, 1));
        }
        for (int i = 0; i < 3; i++) { //Shop
            districtCards.add(new DistrictCard(Color.GREEN, DistrictName.SHOP, 2));
        }
        for (int i = 0; i < 4; i++) { //Market
            districtCards.add(new DistrictCard(Color.GREEN, DistrictName.MARKET, 2));
        }
        for (int i = 0; i < 3; i++) { //Counter
            districtCards.add(new DistrictCard(Color.GREEN, DistrictName.COUNTER, 3));
        }
        for (int i = 0; i < 3; i++) { //Port
            districtCards.add(new DistrictCard(Color.GREEN, DistrictName.PORT, 4));
        }
        for (int i = 0; i < 2; i++) { //City Hall
            districtCards.add(new DistrictCard(Color.GREEN, DistrictName.CITY_HALL, 5));
        }
        for (int i = 0; i < 3; i++) { //WatchTower
            districtCards.add(new DistrictCard(Color.RED, DistrictName.WATCH_TOWER, 1));
        }
        for (int i = 0; i < 3; i++) { //Jail
            districtCards.add(new DistrictCard(Color.RED, DistrictName.JAIL, 2));
        }
        for (int i = 0; i < 3; i++) { //Barracks
            districtCards.add(new DistrictCard(Color.RED, DistrictName.BARRACKS, 3));
        }
        for (int i = 0; i < 2; i++) { //Fortress
            districtCards.add(new DistrictCard(Color.RED, DistrictName.FORTRESS, 5));
        }

        //Creation of unique districts cards
        districtCards.add(new DistrictCard(Color.PURPLE, DistrictName.HAUNTED_QUARTER, 2));
        districtCards.add(new DistrictCard(Color.PURPLE, DistrictName.KEEP, 3));
        districtCards.add(new DistrictCard(Color.PURPLE, DistrictName.KEEP, 3));
        districtCards.add(new DistrictCard(Color.PURPLE, DistrictName.LABORATORY, 5));
        districtCards.add(new DistrictCard(Color.PURPLE, DistrictName.SMITHY, 5));
        districtCards.add(new DistrictCard(Color.PURPLE, DistrictName.OBSERVATORY, 5));
        districtCards.add(new DistrictCard(Color.PURPLE, DistrictName.GRAVEYARD, 5));
        districtCards.add(new DistrictCard(Color.PURPLE, DistrictName.LIBRARY, 5));
        districtCards.add(new DistrictCard(Color.PURPLE, DistrictName.SCHOOL_OF_MAGIC, 6));
        districtCards.add(new DistrictCard(Color.PURPLE, DistrictName.UNIVERSITY, 6));
        districtCards.add(new DistrictCard(Color.PURPLE, DistrictName.DRAGONGATE, 6));

        return districtCards;
    }

    public List<CharacterCard> createCharacterCards() {
        characterCardCards.clear();
        characterCardCards.add(new CharacterCard(CharacterName.ASSASSIN));
        characterCardCards.add(new CharacterCard(CharacterName.THIEF));
        characterCardCards.add(new CharacterCard(CharacterName.MAGICIAN));
        characterCardCards.add(new CharacterCard(CharacterName.KING));
        characterCardCards.add(new CharacterCard(CharacterName.BISHOP));
        characterCardCards.add(new CharacterCard(CharacterName.MERCHANT));
        characterCardCards.add(new CharacterCard(CharacterName.ARCHITECT));
        characterCardCards.add(new CharacterCard(CharacterName.WARLORD));
        return characterCardCards;
    }

    public DistrictCard getRandomDistrictCard() {
        if (districtCards.isEmpty()) {
            return null;
        }
        DistrictCard card = districtCards.get(random.nextInt(districtCards.size()));
        districtCards.remove(card);
        return card;
    }

    public boolean putDistrictCardInDeck(DistrictCard districtCard) {
        if (districtCard != null) {
            return districtCards.add(districtCard);
        }
        return false;
    }

    public List<CharacterCard> getNewCharacterCards() {
        return createCharacterCards();
    }

    public List<DistrictCard> getDistrictCards() {
        return districtCards;
    }
}