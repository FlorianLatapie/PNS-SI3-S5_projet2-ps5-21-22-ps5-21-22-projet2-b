package fr.unice.polytech.citadelles;

import fr.unice.polytech.citadelles.player.Player;

import java.util.*;

/**
 * @author Florian Latapie
 */
public class IOforStats extends IO {
    @Override
    public void println(Object o) {
        return;
    }

    public Map<Player, List<Integer>> printStats(List<Player> players, List<List<Player>> winnersOfEachGame, double numberOfGames) {
        winnersOfEachGame = new ArrayList(winnersOfEachGame);
        Map<Player, List<Integer>> statsForEachPlayer = new HashMap<>();
        // value at 0 : number of win
        // value at 1 : sum of the points of the player
        for (Player p : players) {
            statsForEachPlayer.put(p, new ArrayList<>(List.of(0, 0)));
        }

        for (List<Player> winnersForThisGame : winnersOfEachGame) {
            for (Player playerToSearch : players) {
                if (winnersForThisGame.get(0).getName().equals(playerToSearch.getName())) {
                    statsForEachPlayer.get(playerToSearch).set(0, statsForEachPlayer.get(playerToSearch).get(0) + 1);
                }
                statsForEachPlayer.get(playerToSearch).set(1, statsForEachPlayer.get(playerToSearch).get(1) + playerToSearch.getNbOfPoints());
            }
        }

        for (Player p : players) {
            System.out.println(p.getName() + " has won " +
                    statsForEachPlayer.get(p).get(0) + " games, " +
                    "average : " + statsForEachPlayer.get(p).get(1)/numberOfGames+ " points" +
                    " with strategy : " + p.getStrategy());
        }
        return statsForEachPlayer;
    }
}
