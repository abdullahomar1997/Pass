package com.example.passa;

import java.util.ArrayList;
import java.util.Arrays;

public class PlayerGame {

    private int numberOfCardsLeft;

    private String gameId = "";

    private String playerId = "";

    private String playerAvatar = "";

    private int numberOfPasses = 0;

    private int score = 0;

    private String deckString = "";

    private int position = 0;

    private int rank = 0;

    private String playerLastCardPlayed = "o o";

    public String getPlayerLastCardPlayed() {
        return playerLastCardPlayed;
    }
    public void setPlayerLastCardPlayed(String playerLastCardPlayed) {
        this.playerLastCardPlayed = playerLastCardPlayed;
    }

    private int numberOfCardsPlayed = 0;

    private ArrayList<String> playerDeck = new ArrayList<>();

    public int getScore() {
        return score;
    }
    public void setScore(int score) {
        this.score = score;
    }

    public int getNumberOfPasses() {
        return numberOfPasses;
    }
    public void setNumberOfPasses(int numberOfPasses) {
        this.numberOfPasses = numberOfPasses;
    }
    public void addPlayerPasses() {
        numberOfPasses +=1;
    }

    public int getNumberOfCardsPlayed() {
        return numberOfCardsPlayed;
    }
    public void addNumberOfCardsPlayed(){
        numberOfCardsPlayed+=1;
    }
    public void setNumberOfCardsPlayed(int numberOfCardsPlayed) {
        this.numberOfCardsPlayed = numberOfCardsPlayed;
    }

    public String getGameId() {
        return gameId;
    }
    public void setGameId(String gameId) {
        this.gameId = gameId;
    }

    public String getPlayerId() {
        return playerId;
    }
    public void setPlayerId(String playerId) {
        this.playerId = playerId;
    }

    public void setPlayerDeckAtPosition(int number,String card) {
        playerDeck.set(number,card);
        deckString = convertListToString(playerDeck);
    }
    public String getCardAtDeckPosition(int cardNo) {
        return playerDeck.get(cardNo);
    }

    public void addCardToDeck(String card) {
        playerDeck.add(card);
        deckString = convertListToString(playerDeck);
    }

    public void setPlayerDeck(ArrayList<String> playerDeck) {
        deckString = convertListToString(playerDeck);
        this.playerDeck = playerDeck;
    }
    public ArrayList<String> getPlayerDeck() {
        return playerDeck;
    }

    public String getDeckString() {
        return deckString;
    }
    public void setDeckString(String deckString) {
        playerDeck = convertStringToList(deckString);
        this.deckString = deckString;
    }

    public int getplayerDeckSize(){
        return playerDeck.size();
    }

    public String convertListToString(ArrayList<String> list){

        String currentString = "";

        for (int i = 0;i < list.size()-1;++i){
            currentString+=list.get(i);
            currentString+=",";
        }

        currentString+=list.get(list.size()-1);

        return currentString;
    }
    public ArrayList<String> convertStringToList(String str){

        ArrayList<String> list = new ArrayList<>();

        String[] deckSplit = str.split(",");

        list.addAll(Arrays.asList(deckSplit));

        return list;
    }

    public void updateNoCards(){
        this.numberOfCardsLeft -= 1;
    }

    public void setBNoCards() {
        numberOfCardsLeft = playerDeck.size();
    }

    public int getNumberOfCardsLeft(){
        return numberOfCardsLeft;
    }

    public String getPlayerAvatar() {
        return playerAvatar;
    }

    public void setPlayerAvatar(String playerAvatar) {
        this.playerAvatar = playerAvatar;
    }
}