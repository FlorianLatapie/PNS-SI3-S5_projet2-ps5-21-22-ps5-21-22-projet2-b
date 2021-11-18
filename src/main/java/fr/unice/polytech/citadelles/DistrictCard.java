package fr.unice.polytech.citadelles;

public class DistrictCard extends Card {
    private int priceToBuild;
    private Color color;
    private DistrictName districtName;

    /**
     * @param priceToBuild price to build this district card
     * @deprecated because you should not use this constructor anymore
     */
    @Deprecated
    public DistrictCard(int priceToBuild) { // the default constructor creates a grey card
        this(Color.GREY, DistrictName.GreyHouse, priceToBuild);
    }

    public DistrictCard(Color color,DistrictName districtName, int priceToBuild) {
        super();                 // title and description will be in the super class
        if (priceToBuild <= 0) { // cannot instantiate card if it is not greater than 0
            throw new RuntimeException("The price of the card is not greater than 0 :" + priceToBuild);
        }
        this.priceToBuild = priceToBuild;
        this.color = color;
        this.districtName = districtName;
    }

    public int getPriceToBuild() {
        return priceToBuild;
    }

    public Color getColor() {
        return color;
    }

    public DistrictName getDistrictName() {
        return districtName;
    }

    @Override
    public String toString() {
        return "DistrictCard{" +
                "priceToBuild=" + priceToBuild +
                ", color=" + color +
                ", name="+ districtName +
                '}';
        // maybe use the super().toString() + "attribute1=" + attribute1 ...  when we have title and description
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
