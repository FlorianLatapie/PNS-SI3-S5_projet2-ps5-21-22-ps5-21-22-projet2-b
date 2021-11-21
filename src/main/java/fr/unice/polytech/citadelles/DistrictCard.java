package fr.unice.polytech.citadelles;

public class DistrictCard extends Card {
    private int priceToBuild;
    private DistrictName districtName;

    public DistrictCard(Color color, DistrictName districtName, int priceToBuild) {
        super.setColor(color);
        if (priceToBuild <= 0) { // cannot instantiate card if it is not greater than 0
            throw new RuntimeException("The price of the card is not greater than 0 :" + priceToBuild);
        }
        this.priceToBuild = priceToBuild;
        this.districtName = districtName;
    }

    public int getPriceToBuild() {
        return priceToBuild;
    }

    public DistrictName getDistrictName() {
        return districtName;
    }

    @Override
    public String toString() {
        if (priceToBuild == 1) {
            return districtName + "(" +
                    priceToBuild + " coin" +
                    ", " + super.getColor() + ")";
        } else {
            return districtName + "(" +
                    priceToBuild + " coins" +
                    ", " + super.getColor() + ")";
        }
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof DistrictCard) {
            DistrictCard cardToCompare = (DistrictCard) obj;
            return this.getPriceToBuild() == cardToCompare.getPriceToBuild()
                    && this.getColor().equals(cardToCompare.getColor())
                    && this.getDistrictName().equals(cardToCompare.getDistrictName());
        }
        return false;
    }
}

enum DistrictName {
    TEMPLE, CHURCH, MONASTERY, CITADEL, MANSION, CASTLE, PALACE, TAVERN, SHOP, MARKET, COUNTER, PORT, CITY_HALL, WATCH_TOWER, JAIL, BARRACKS, FORTRESS, NONE
}
