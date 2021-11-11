package fr.unice.polytech.citadelles;

/**
 * IO means Input/Output
 */
public class IO {
    public void printDistrictCardsInHandOf(Player player) {
        System.out.println("The player has the following district cards in hand : " + player.getDistrictCardsInHand());
    }

    public void printCoinsOf(Player player) {
        int coins = player.getCoins();
        if (coins > 1) {
            System.out.println("The player has " + coins + " coins");
        } else {
            System.out.println("The player has " + coins + " coin");
        }
    }
}
