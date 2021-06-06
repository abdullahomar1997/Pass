package com.example.passa.OnlineGame;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.example.passa.R;

import java.util.ArrayList;

public class OnlineCreateGame extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_online_create_game);

        /*int numberOfPlayers = 2;

        Game game = new Game();

        game.startGame(numberOfPlayers);

        ArrayList<String> Decks = game.getPlayerDecks();

        String myDeck = Decks.get(0);

        //create game table with groupid ,playerdecks,

        //send deck to new intent

        //if all players joined go to new intent

        Intent intent = new Intent(OnlineCreateGame.this, OnlineGameActivity.class);
        intent.putExtra("gameid", gi);
        intent.putExtra("type", "create");
        intent.putExtra("deck", myDeck);


        startActivity(intent);*/

    }
}