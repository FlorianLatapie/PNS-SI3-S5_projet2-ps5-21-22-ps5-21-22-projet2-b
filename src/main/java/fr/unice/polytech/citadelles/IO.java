package fr.unice.polytech.citadelles;

/**
 * IO means Input/Output
 */
public class IO {
    public void printDistrictCardsInHandOf(Player player) {
        System.out.println(player.getName() + " has the following district cards in hand      : " + player.getDistrictCardsInHand());
    }

    public void printDistrictCardsBuiltBy(Player player) {
        System.out.println(player.getName() + " has the following district cards on the table : " + player.getDistrictCardsBuild());
    }

    public void printCoinsOf(Player player) {
        int coins = player.getCoins();
        if (coins > 1) {
            System.out.println(player.getName() + " has " + coins + " coins");
        } else {
            System.out.println(player.getName() + " has " + coins + " coin");
        }
    }

    public void printSeparator(String text) {
        String separator = "-----------------------------------------------------------------------------";
        if (!text.isEmpty()) text = " " + text + " ";
        System.out.println(separator + text + separator + "\n");
    }
}
