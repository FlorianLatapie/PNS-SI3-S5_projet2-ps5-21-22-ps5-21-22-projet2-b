package fr.unice.polytech.citadelles;

public class DistrictCard extends Card {
    private int priceToBuild;

    public DistrictCard(int priceToBuild) {
        super();
        this.priceToBuild = priceToBuild;
    }

    public int getPriceToBuild() {
        return priceToBuild;
    }

    @Override
    public String toString() {
        return "DistrictCard{" +
                "priceToBuild=" + priceToBuild +
                '}';
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof DistrictCard) {
            DistrictCard cardToCompare = (DistrictCard) obj;
            return this.getPriceToBuild() == cardToCompare.getPriceToBuild();
        }
        return false;
    }
}
