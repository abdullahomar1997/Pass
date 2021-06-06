package com.example.passa;

import java.net.CookieHandler;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

//Sample branch 1

public class Game {
    //.0e1823 .2f1e16 .303030 ;6e6e6e .27383e .3a322b .042550 .1a1a1a .442f28 .2c3344 .333332 .173846 ;ebd3af

    //ArrayList<String> cardsPlayed = new ArrayList<>();

    private String gameMode = "Normal";
    private String gameWinner = "";
    private int gameNoOfPlayers;
    public ArrayList<PlayerGame> gamePlayersList = new ArrayList<>();

    private ArrayList<String> gameCardsInDeckGreaterThanSevenList = new ArrayList();
    private ArrayList<String> gameCardsInDeckLesserThanSevenList = new ArrayList();

    private ArrayList<String> gameDeck = new ArrayList();

    public void startGame(int noOfPlayers) {

        this.gameNoOfPlayers = noOfPlayers;

        createPlayers();
        createAndShuffleDeck();
        distributeCardsToPlayers();

    }

    public void continueGame(String playerCards) {

        String[] ss = playerCards.split(">");

        gameNoOfPlayers = Integer.parseInt(ss[0]);

        createPlayers();

        int i = 1;

        for (PlayerGame playerGame:gamePlayersList){

            String[] ss2 = ss[i].split(":");

            playerGame.setDeckString(ss2[0]);
            playerGame.setNumberOfCardsPlayed(Integer.parseInt(ss2[1]));
            playerGame.setNumberOfPasses(Integer.parseInt(ss2[2]));
            playerGame.setPlayerAvatar(ss2[3]);

            i+=1;

        }
    }


    public void createPlayers() {

        ArrayList<String> avs = new ArrayList<>();

        avs.add("v js");
        avs.add("v qf");
        avs.add("v jh");
        avs.add("v qd");
        avs.add("v ks");
        avs.add("v kh");

        for (int noPlayersIndex = 1; noPlayersIndex <= gameNoOfPlayers; ++noPlayersIndex) {
            PlayerGame newPlayer = new PlayerGame();

            newPlayer.setPlayerId("Player " + noPlayersIndex);
            newPlayer.setPlayerAvatar(avs.get(noPlayersIndex-1));

            gamePlayersList.add(newPlayer);
        }
    }

    public void createAndShuffleDeck() {

        gameDeck.add("a s");gameDeck.add("k s");gameDeck.add("j s");gameDeck.add("q s");
        gameDeck.add("10 s");gameDeck.add("9 s");gameDeck.add("8 s");gameDeck.add("7 s");
        gameDeck.add("6 s");gameDeck.add("5 s");gameDeck.add("4 s");gameDeck.add("3 s");gameDeck.add("2 s");

        gameDeck.add("a h");gameDeck.add("k h");gameDeck.add("j h");gameDeck.add("q h");
        gameDeck.add("10 h");gameDeck.add("9 h");gameDeck.add("8 h");gameDeck.add("7 h");
        gameDeck.add("6 h");gameDeck.add("5 h");gameDeck.add("4 h");gameDeck.add("3 h");gameDeck.add("2 h");

        gameDeck.add("a f");gameDeck.add("k f");gameDeck.add("j f");gameDeck.add("q f");
        gameDeck.add("10 f");gameDeck.add("9 f");gameDeck.add("8 f");gameDeck.add("7 f");
        gameDeck.add("6 f");gameDeck.add("5 f");gameDeck.add("4 f");gameDeck.add("3 f");gameDeck.add("2 f");

        gameDeck.add("a d");gameDeck.add("k d");gameDeck.add("j d");gameDeck.add("q d");
        gameDeck.add("10 d");gameDeck.add("9 d");gameDeck.add("8 d");gameDeck.add("7 d");
        gameDeck.add("6 d");gameDeck.add("5 d");gameDeck.add("4 d");gameDeck.add("3 d");gameDeck.add("2 d");

        Collections.shuffle(gameDeck);
    }

    public void distributeCardsToPlayers() {
        while (!gameDeck.isEmpty()) {
            for (int i = 0; i < gameNoOfPlayers; ++i) {
                if (!gameDeck.isEmpty()) {
                    gamePlayersList.get(i).addCardToDeck(gameDeck.get(0));
                    gameDeck.remove(0);
                }
            }
        }

        for (int noPlayersIndex = 0; noPlayersIndex < gamePlayersList.size(); ++noPlayersIndex) {
            gamePlayersList.get(noPlayersIndex).setBNoCards();
        }
    }

    public int getGameNoOfPlayers() {
        return gameNoOfPlayers;
    }

    public void playCard(String card) {

        String cardSign = getCardSign(card);
        int cardNo = getCardNo(card);

        if (cardNo == 7) {
            gameCardsInDeckGreaterThanSevenList.add(card);
            gameCardsInDeckLesserThanSevenList.add(card);
        }

        else {
            if (cardNo > 7) {
                for (int deckIndex = 0; deckIndex < gameCardsInDeckGreaterThanSevenList.size(); ++deckIndex) {

                    String deckCard = gameCardsInDeckGreaterThanSevenList.get(deckIndex);

                    String deckCardSign = getCardSign(deckCard);
                    int deckCardNo = getCardNo(deckCard);

                    if (cardSign.equals(deckCardSign) && (cardNo == deckCardNo + 1)) {
                        gameCardsInDeckGreaterThanSevenList.set(deckIndex, card);
                        break;
                    }
                }
            }

            else{
                for (int deckIndex = 0; deckIndex < gameCardsInDeckLesserThanSevenList.size(); ++deckIndex) {

                    String deckCard = gameCardsInDeckLesserThanSevenList.get(deckIndex);

                    String deckCardSign = getCardSign(deckCard);
                    int deckCardNo = getCardNo(deckCard);

                    if (cardSign.equals(deckCardSign) && (cardNo == deckCardNo - 1)) {
                        gameCardsInDeckLesserThanSevenList.set(deckIndex, card);
                        break;
                    }
                }
            }
        }
    }

    public String cpuAutoPlayCardNum(int playersIndex) {

        if(gameWinner.equals("")) {

            PlayerGame playerGame = gamePlayersList.get(playersIndex);

            for (int deckIndex = 0; deckIndex < playerGame.getplayerDeckSize(); ++deckIndex) {

                String cpuCard = playerGame.getCardAtDeckPosition(deckIndex);

                if (isCardPlayable(cpuCard)) {
                    playerGame.setPlayerLastCardPlayed(cpuCard);
                    playCard(cpuCard);
                    playerGame.addNumberOfCardsPlayed();
                    playerGame.setPlayerDeckAtPosition(deckIndex, "o o");
                    playerGame.updateNoCards();

                    if (isGameOver(playersIndex)) {
                        gameWinner = playerGame.getPlayerId();
                    }
                    return cpuCard;
                }
            }
            playerGame.addPlayerPasses();
        }
        return "";
    }

    public boolean playerHasNoPlayableCards(int playerGamePosition) {

        PlayerGame playerGame = gamePlayersList.get(playerGamePosition);

        for (int deckIndex = 0; deckIndex < playerGame.getplayerDeckSize(); ++deckIndex) {

            String deckCard = playerGame.getCardAtDeckPosition(deckIndex);

            String deckCardSign = getCardSign(deckCard);
            int deckCardNo = getCardNo(deckCard);

            if(deckCard.contains("o")){
                continue;
            }

            if (deckCardNo == 7) {
                return false;
            }

            else {
                if (deckCardNo > 7) {

                    for (int i = 0; i < gameCardsInDeckGreaterThanSevenList.size(); ++i) {

                        String cardS2 = gameCardsInDeckGreaterThanSevenList.get(i);

                        String cardSign2 = getCardSign(cardS2);
                        int CardNo2 = getCardNo(cardS2);

                        if (deckCardSign.equals(cardSign2) && (deckCardNo == CardNo2 + 1)) {
                            return false;
                        }
                    }
                }

                else{
                    for (int i = 0; i < gameCardsInDeckLesserThanSevenList.size(); ++i) {

                        String cardS2 = gameCardsInDeckLesserThanSevenList.get(i);

                        String cardSign2 = getCardSign(cardS2);
                        int CardNo2 = getCardNo(cardS2);

                        if (deckCardSign.equals(cardSign2) && (deckCardNo == CardNo2 - 1)) {
                            return false;
                        }
                    }
                }
            }
        }
        playerGame.addPlayerPasses();
        return true;
    }

    public boolean isCardPlayable(String card) {

        String cardSign = getCardSign(card);
        int cardNo = getCardNo(card);

        if(cardNo == 7) {
            return true;
        }
        else{
            if(cardNo > 7) {
                for (int deckIndex = 0; deckIndex < gameCardsInDeckGreaterThanSevenList.size(); ++deckIndex) {

                    String deckCard = gameCardsInDeckGreaterThanSevenList.get(deckIndex);

                    String deckCardSign = getCardSign(deckCard);
                    int deckCardNo = getCardNo(deckCard);

                    if (cardSign.equals(deckCardSign) && (cardNo == deckCardNo + 1)) {
                        return true;
                    }
                }
            }
            else{

                for (int deckIndex = 0; deckIndex < gameCardsInDeckLesserThanSevenList.size(); ++deckIndex) {

                    String deckCard = gameCardsInDeckLesserThanSevenList.get(deckIndex);

                    String deckCardSign = getCardSign(deckCard);
                    int deckCardNo = getCardNo(deckCard);

                    if (cardSign.equals(deckCardSign) && (cardNo == deckCardNo - 1)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public boolean isGameOver(int gamePlayerPosition){

        PlayerGame player = gamePlayersList.get(gamePlayerPosition);

        if(gameMode.equals("Normal") && player.getNumberOfCardsPlayed() == player.getplayerDeckSize()){
            calculateAllPlayerScores();
            return true;
        }

        if(gameMode.equals("HighPass") && player.getNumberOfCardsPlayed() == player.getplayerDeckSize()){
            calculateAllPlayerScoresHp();
            return true;
        }

        if(gameMode.equals("YuuPassYouLose") && player.getNumberOfPasses() > 0){
            //calculateAllPlayerScores();
            return true;
        }

        return false;
    }

    private int calculateScore(ArrayList<String> deck) {

        int score = 0;

        for(int i = 0;i < deck.size();++i){

            if(deck.get(i).contains("o")){
                continue;
            }

            if(deck.get(i).contains("a") || deck.get(i).contains("k") || deck.get(i).contains("j") || deck.get(i).contains("q") || deck.get(i).contains("10") ){
                score+=10;
            }

            else{
                score+=Integer.parseInt(deck.get(i).substring(0,1));
            }
        }
        return score;
    }

    private void calculateAllPlayerScores() {
        for(PlayerGame playerGame:gamePlayersList){
            playerGame.setScore(calculateScore(playerGame.getPlayerDeck()));
        }
        calculatePlayerRanks();
    }

    private void calculateAllPlayerScoresHp() {
        calculatePlayerRanksHp();
    }

    private void calculatePlayerRanks() {

        Collections.sort(gamePlayersList, new Comparator<PlayerGame>(){
            public int compare(PlayerGame s1,PlayerGame s2) {
                return String.valueOf(s1.getScore()).compareToIgnoreCase(String.valueOf(s2.getScore()));
            }
        });
    }

    private void calculatePlayerRanksHp() {

        Collections.sort(gamePlayersList, new Comparator<PlayerGame>(){
            public int compare(PlayerGame s1,PlayerGame s2) {
                return String.valueOf(s1.getNumberOfPasses()).compareToIgnoreCase(String.valueOf(s2.getNumberOfPasses()));
            }
        });

        Collections.reverse(gamePlayersList);
    }

    public int getCardNo(String card){

        Map m1 = new HashMap();
        m1.put("o",0);
        m1.put("2",2);
        m1.put("3",3);
        m1.put("4",4);
        m1.put("5",5);
        m1.put("6",6);
        m1.put("7",7);
        m1.put("8",8);
        m1.put("9",9);
        m1.put("10",10);
        m1.put("j",11);
        m1.put("q",12);
        m1.put("k",13);
        m1.put("a",14);

        String[] cardS = card.split(" ");

        String cardNo = cardS[0];

        return Integer.parseInt(String.valueOf(m1.get(cardNo)));
    }

    public String getCardSign(String card){

        String[] cardSplit = card.split(" ");

        return cardSplit[1];
    }

    public PlayerGame getPlayer(int playerNumber){
        return gamePlayersList.get(playerNumber);
    }

    public String getGameWinner() {
        return gameWinner;
    }

    public void setGameWinner(String gameWinner) {
        this.gameWinner = gameWinner;
    }

    public void startGame(int numPlayers, String deck, String online) {


    }

    public void convertTableDeckToString() {
    }

    public Object getGameDisplayMessage() {
        return null;
    }

    public Object getGameCardsInDeckLesserThanSevenString() {
        return null;
    }

    public Object getGameCardsInDeckGreaterThanSevenString() {
        return null;
    }

    public boolean isOnlineGameOver() {
        return false;
    }

    public void setGameDisplayMessage(String s) {
    }

    public int getSizeOfGameCardsInDeckGreaterThanSeven() {
        return 0;
    }

    public String getCardInGameCardsInDeckGreaterThanSevenAt(int ii) {
        return null;
    }

    public int getSizeOfGameCardsInDeckLesserThanSeven() {
        return 0;
    }

    public String getCardInGameCardsInDeckLesserThanSevenAt(int ii) {
        return null;
    }

    public void setGameCardsInDeckGreaterThanSevenString(String gameGreater) {

    }

    public void setGameCardsInDeckLesserThanSevenString(String gameLess) {

    }

    public void setGamePlayerTurn(String gameTurn) {

    }

    public void convertTableDeckToList() {

    }
}