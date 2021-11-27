package fr.unice.polytech.citadelles;

import java.util.Objects;

public class DistrictCard extends Card {
    private int priceToBuild;
    private DistrictName districtName;

    public DistrictCard(Color color, DistrictName districtName, int priceToBuild) {
        super.setColor(color);
        if (priceToBuild <= 0) { // cannot instantiate card if it is not greater than 0
            throw new IllegalArgumentException("The price of the card is not greater than 0: " + priceToBuild);
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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof DistrictCard)) return false;
        DistrictCard card = (DistrictCard) o;
        return getPriceToBuild() == card.getPriceToBuild() && getDistrictName() == card.getDistrictName();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getPriceToBuild(), getDistrictName());
    }
}


