package fr.unice.polytech.citadelles;


import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class DeckOfCards {
    private List<DistrictCard> districtCards;
    private Random random;

    public DeckOfCards() {
        this(new Random());
    }

    public DeckOfCards(Random random) {
        districtCards = new ArrayList<>();
        this.random = random;
        createDistrictCards();
    }

    private List<DistrictCard> createDistrictCards() {
        for (int i = 0; i < 3; i++) { //Temple
            districtCards.add(new DistrictCard(Color.BLUE, 1));
        }
        for (int i = 0; i < 4; i++) { //Church
            districtCards.add(new DistrictCard(Color.BLUE, 2));
        }
        for (int i = 0; i < 3; i++) { //Monastery
            districtCards.add(new DistrictCard(Color.BLUE, 3));
        }
        for (int i = 0; i < 2; i++) { //Citadel
            districtCards.add(new DistrictCard(Color.BLUE, 4));
        }
        for (int i = 0; i < 5; i++) { //Mansion
            districtCards.add(new DistrictCard(Color.YELLOW, 3));
        }
        for (int i = 0; i < 4; i++) { //Castle
            districtCards.add(new DistrictCard(Color.YELLOW, 4));
        }
        for (int i = 0; i < 2; i++) { //Palace
            districtCards.add(new DistrictCard(Color.YELLOW, 5));
        }
        for (int i = 0; i < 5; i++) { //Tavern
            districtCards.add(new DistrictCard(Color.GREEN, 1));
        }
        for (int i = 0; i < 3; i++) { //Shop
            districtCards.add(new DistrictCard(Color.GREEN, 2));
        }
        for (int i = 0; i < 4; i++) { //Market
            districtCards.add(new DistrictCard(Color.GREEN, 2));
        }
        for (int i = 0; i < 3; i++) { //Counter
            districtCards.add(new DistrictCard(Color.GREEN, 3));
        }
        for (int i = 0; i < 3; i++) { //Port
            districtCards.add(new DistrictCard(Color.GREEN, 4));
        }
        for (int i = 0; i < 2; i++) { //City Hall
            districtCards.add(new DistrictCard(Color.GREEN, 5));
        }
        for (int i = 0; i < 3; i++) { //WatchTower
            districtCards.add(new DistrictCard(Color.RED, 1));
        }
        for (int i = 0; i < 3; i++) { //Jail
            districtCards.add(new DistrictCard(Color.RED, 2));
        }
        for (int i = 0; i < 3; i++) { //Barracks
            districtCards.add(new DistrictCard(Color.RED, 3));
        }
        for (int i = 0; i < 2; i++) { //Fortress
            districtCards.add(new DistrictCard(Color.RED, 5));
        }
        return districtCards;
    }

    public DistrictCard getRandomDistrictCard() {
        DistrictCard card = districtCards.get(random.nextInt(districtCards.size()));
        districtCards.remove(card);
        return card;
    }

    public List<DistrictCard> getDistrictCards() {
        return districtCards;
    }
}