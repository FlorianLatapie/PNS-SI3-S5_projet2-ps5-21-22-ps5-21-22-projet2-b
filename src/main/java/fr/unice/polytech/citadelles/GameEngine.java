package fr.unice.polytech.citadelles;

import java.util.*;
import java.util.stream.Collectors;

/**
 * "GameEngine" or "ge" also known as "MJ" or "Moteur de Jeu" in French
 */
public class GameEngine {
    private IO io;

    private int nbPlayers;
    private List<Player> listOfPlayers;
    private List<Player> playersWhoPlacedThe8Cards;
    private Player kingOfTheLastRound;


    private DeckOfCards deckOfCards;

    private Random random;

    public GameEngine() {
        this(4, new Random());
    }

    public GameEngine(int nbPlayers, Random random) {
        this.random = random;
        this.nbPlayers = nbPlayers;
        listOfPlayers = new ArrayList<>();
        io = new IO();
        playersWhoPlacedThe8Cards = new ArrayList<>();

        deckOfCards = new DeckOfCards(random);

        initPlayers();
    }

    private void initPlayers() {
        for (int i = 0; i < nbPlayers; i++) {
            List<DistrictCard> districtCards = new ArrayList<>();
            for (int j = 0; j < 4; j++) {
                districtCards.add(deckOfCards.getRandomDistrictCard());
            }
            Player playerToAdd = new Player("Player_" + (i + 1), districtCards, 0 , random);
            listOfPlayers.add(playerToAdd);
            if(i==0) kingOfTheLastRound = playerToAdd;
        }
    }

    public void launchGame() {
        int round = 1;
        List<CharacterCard> characterCardDeckOfTheRound;

        io.printSeparator("The game starts !");

        while (playersWhoPlacedThe8Cards.isEmpty() && round <= 4) {
            io.printSeparator("Start of the round " + round);
            characterCardDeckOfTheRound = deckOfCards.getNewCharacterCards(); // is a new copy of the 8 characters each new round

            for (Player player : listOfPlayers) {
                askToChooseCharacter(listOfPlayers.get((listOfPlayers.indexOf(kingOfTheLastRound)+listOfPlayers.indexOf(player))%nbPlayers), characterCardDeckOfTheRound);
            }

            List<Player> listOfPlayersSorted = sortPlayerListByCharacterSequence();
            updateKing(listOfPlayersSorted);

            for (Player player : listOfPlayersSorted){
                askToChooseCoinsOrCard(player);
                io.printDistrictCardsInHandOf(player);
                askToBuildDistrict(player);
                io.printDistrictCardsBuiltBy(player);

                io.printCoinsOf(player);

                io.printSeparator("End of turn " + round + " for " + player.getName());
            }
            round++;
        }
        io.printSeparator("The game is over !");
        getWinner();
    }

    private void askToBuildDistrict(Player player) {
        if (player.chooseToBuildDistrict()) {
            io.println(player.getName() + " has chose to build a district");
            io.printDistrictCardsInHandOf(player);
        }
    }

    private void getWinner() { // Player needs to implements Comparable<Player> to be cleaner
        Collections.sort(this.listOfPlayers,
                (player0, player1) -> player1.getNbOfPoints().compareTo(player0.getNbOfPoints()));
        io.printWinner(this.listOfPlayers);
    }

    public void giveCoins(Player player) {
        int nbCoinsToAdd = 2;
        io.println(player.getName() + " choose to receive "+nbCoinsToAdd+" coins.");
        player.receiveCoins(nbCoinsToAdd);
        io.printCoinsOf(player);

    }

    public void giveCard(Player player){
        DistrictCard card = deckOfCards.getRandomDistrictCard();
        io.println(player.getName() + " choose to draw a card\n"+player.getName()+" draws: "+ card.toString());
        player.receiveCard(card);
    }

    public void askToChooseCharacter(Player player, List<CharacterCard> characterCardDeckOfTheRound){
        CharacterCard choice = player.chooseCharacter(characterCardDeckOfTheRound);
        characterCardDeckOfTheRound.remove(choice);
        io.println(player.getName() + " chose " + player.getCharacterCard());
    }

    public void askToChooseCoinsOrCard(Player player){
        if(player.chooseCoinsOrCard()) giveCard(player);
        else giveCoins(player);
    }

    private List<Player> sortPlayerListByCharacterSequence(){
        return (
                listOfPlayers.stream()
                .sorted(Comparator.comparing(player -> player.getCharacterCard().getCharacterSequence()))
                .collect(Collectors.toList())
    );
    }

    private void updateKing(List<Player> listOfPlayers){
        listOfPlayers.forEach(player -> {
            if(player.getCharacterCard().getCharacterSequence()==4){
                kingOfTheLastRound = player;
            }});
    }

}
