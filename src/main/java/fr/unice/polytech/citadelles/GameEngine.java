package fr.unice.polytech.citadelles;

import java.util.*;

/**
 * "GameEngine" or "ge" also known as "MJ" or "Moteur de Jeu" in French
 */
public class GameEngine {
    private int nbPlayers;
    private List<Player> listOfPlayers;
    private IO io;
    private List<Player> winner;
    private int round;

    public GameEngine() {
        this(4);
    }

    public GameEngine(int nbPlayers) {
        this.nbPlayers = nbPlayers;
        listOfPlayers = new ArrayList<>();
        io = new IO();
        winner = new ArrayList<>();
        round = 1;
        initPlayers();
    }

    private void initPlayers() {
        for (int i = 0; i < nbPlayers; i++) {
            List<DistrictCard> districtCards = new ArrayList<>();
            for (int j = 0; j < 4; j++) {
                districtCards.add(new DistrictCard(new Random().nextInt(1, 6)));
            }
            listOfPlayers.add(new Player("Player_"+(i+1), districtCards));
        }
    }

    public void launchGame() {
        io.printSeparator("The game starts !");

        while(winner.size()==0 && round<=4) {
            io.printSeparator("Start of the round " + round);
            for (Player player : listOfPlayers) {
                io.printDistrictCardsInHandOf(player);
                if (player.chooseToBuildDistrict()) {
                    io.println(player.getName() + " has chose to build a district");
                }
                io.printDistrictCardsBuiltBy(player);
                io.printCoinsOf(player);
                io.printSeparator("End of turn " + round + " for player : " + player.getName());
            }
            round++;
        }

        getWinner();
    }

    private void getWinner() {
        HashMap<Integer, Player> player = new HashMap<>();
        listOfPlayers.forEach(elem ->
                player.put(elem.getDistrictCardsBuilt().stream().map(card -> card.getPriceToBuild()).reduce(0,Integer::sum),elem)
        );
        io.printWinner(player.get(Collections.max(player.keySet())),Collections.max(player.keySet()));
    }
}
