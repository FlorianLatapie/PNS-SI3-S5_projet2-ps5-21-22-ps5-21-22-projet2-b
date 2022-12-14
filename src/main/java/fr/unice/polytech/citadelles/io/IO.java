package fr.unice.polytech.citadelles.io;

import fr.unice.polytech.citadelles.player.Player;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * IO means Input/Output
 */
public class IO {
    /*private final static Logger LOGGER = Logger.getLogger(IO.class.getName());

    public IO (){
        LOGGER.setLevel(Level.INFO);
    }*/

    public void println(Object o) {
        //LOGGER.log(Level.INFO, o.toString());
        System.out.println(o);
    }

    public void printDistrictCardsInHandOf(Player player) {
        this.println(player.getName() + " has the following district cards in hand          : " + player.getDistrictCardsInHand());
    }

    public void printDistrictCardsBuiltBy(Player player) {
        this.println(player.getName() + " has the following district cards on the table (" + player.getDistrictCardsBuilt().size() + ") : " + player.getDistrictCardsBuilt());
    }

    public void printCoinsOf(Player player) {
        int coins = player.getCoins();
        if (coins == 1) {
            this.println(player.getName() + " has " + coins + " coin");
        } else {
            this.println(player.getName() + " has " + coins + " coins");
        }
    }

    public void printSeparator(String text) {
        String separator = "---------------------------------------------------------------------";
        if (!text.isEmpty()) text = " " + text.trim() + " ";
        this.println(separator + text + separator + System.lineSeparator());
    }

    public void printWinner(List<Player> winners) {
        this.printSeparator("The winners podium !");
        winners.forEach(winner -> {
                    if (winner.getNbOfPoints() == 1) {
                        this.println(winner.getName() + " with " + winner.getNbOfPoints() + " point");
                    } else {
                        this.println(winner.getName() + " with " + winner.getNbOfPoints() + " points");
                    }
                }
        );
    }

    public void printTaxesEarned(Player player, AtomicInteger sum) {
        if (sum.get() == 1) {
            this.println(player.getName() + " taxes earned " + sum + " coin");
        } else {
            this.println(player.getName() + " taxes earned " + sum + " coins");
        }
    }
}
