package com.example.passa;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;

public class Player {

    //git commit m

    String playerUserName = "Player";
    String playerFriendsString = "";
    String playerEmail = "";
    String PlayerPassword;

    ArrayList<String> playerFriendsList = new ArrayList<>();

    public String getPlayerEmail() {
        return playerEmail;
    }

    public void setPlayerEmail(String playerEmail) {
        this.playerEmail = playerEmail;
    }

    public String getPlayerPassword() {
        return PlayerPassword;
    }

    public void setPlayerPassword(String playerPassword) {
        PlayerPassword = playerPassword;
    }

    public String getPlayerUserName() {
        return playerUserName;
    }
    public void setPlayerUserName(String playerUserName) {
        this.playerUserName = playerUserName;
    }

    public String getPlayerFriendsString() {
        return playerFriendsString;
    }
    public void setPlayerFriendsString(String playerFriendsString) {
        this.playerFriendsList = convertStringToList(playerFriendsString);
        this.playerFriendsString = playerFriendsString;
    }

    public ArrayList<String> getPlayerFriendsList() {
        return playerFriendsList;
    }
    public void setPlayerFriendsList(ArrayList<String> playerFriendsList) {
        this.playerFriendsString = convertListToString(playerFriendsList);
        this.playerFriendsList = playerFriendsList;
    }

    public String convertListToString(ArrayList<String> list){

        String ss = list.toString();

        return ss.substring(1, ss.length() - 1);
    }
    public ArrayList<String> convertStringToList(String str){

        String[] DeckS = str.split(",");

        return new ArrayList<>(Arrays.asList(DeckS));
    }
}
