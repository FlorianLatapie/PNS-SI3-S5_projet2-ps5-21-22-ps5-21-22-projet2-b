package fr.unice.polytech.citadelles;

enum Color {
    RED, GREEN, BLUE, YELLOW, GREY;
}

public class Card {
    private Color color;

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }
}
