package fr.unice.polytech.citadelles;

import java.util.List;

/**
 * IO means Input/Output
 */
public class IO {
    public void printDistrictCardsInHandOf(Player player) {
        System.out.println(player.getName() + " has the following district cards in hand          : " + player.getDistrictCardsInHand());
    }

    public void printDistrictCardsBuiltBy(Player player) {
        System.out.println(player.getName() + " has the following district cards on the table ("+player.getDistrictCardsBuilt().size()+") : " + player.getDistrictCardsBuilt());
    }

    public void printCoinsOf(Player player) {
        int coins = player.getCoins();
        if (coins == 1) {
            System.out.println(player.getName() + " has " + coins + " coin");
        } else {
            System.out.println(player.getName() + " has " + coins + " coins");
        }
    }

    public void printSeparator(String text) {
        String separator = "-----------------------------------------------------------------------------";
        if (!text.isEmpty()) text = " " + text + " ";
        System.out.println(separator + text + separator + System.lineSeparator());
    }

    public void println(Object o) {
        System.out.println(o);
    }


    public void printWinner(List<Player> winners) {
        this.println("The winners podium !");
        winners.forEach(winner -> {
                    if (winner.getNbOfPoints() == 1) {
                        System.out.println(winner.getName() + " with " + winner.getNbOfPoints() + " point");
                    } else {
                        System.out.println(winner.getName() + " with " + winner.getNbOfPoints() + " points");
                    }
                }
        );
    }
}
