package fr.unice.polytech.citadelles;


import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;

public class DeckOfCards {
    private List<DistrictCard> districtCards;
    private List<Character> characters;
    private Random random;

    public DeckOfCards() {
        this(new Random());
    }

    public DeckOfCards(Random random) {
        districtCards = new ArrayList<>();
        characters = new ArrayList<>();
        this.random = random;
        createDistrictCards();
    }

    private List<DistrictCard> createDistrictCards() {
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
        return districtCards;
    }

    public void createCharacterCards(){
        characters.removeAll(characters);
        characters.add(new Character(CharacterNames.ASSASSIN));
        characters.add(new Character(CharacterNames.THIEF));
        characters.add(new Character(CharacterNames.MAGICIAN));
        characters.add(new Character(CharacterNames.KING));
        characters.add(new Character(CharacterNames.BISHOP));
        characters.add(new Character(CharacterNames.MERCHANT));
        characters.add(new Character(CharacterNames.ARCHITECT));
        characters.add(new Character(CharacterNames.WARLORD));
    }

    public DistrictCard getRandomDistrictCard() {
        DistrictCard card = districtCards.get(random.nextInt(districtCards.size()));
        districtCards.remove(card);
        return card;
    }

    public Character getRandomCharacterCard(){
        Character card = characters.get(random.nextInt(characters.size()));
        characters.remove(card);
        return card;
    }


    public List<DistrictCard> getDistrictCards() {
        return districtCards;
    }
}