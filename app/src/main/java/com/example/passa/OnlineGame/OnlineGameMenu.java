package com.example.passa.OnlineGame;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.passa.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class OnlineGameMenu extends AppCompatActivity {

    Button onlineCreateBtn;
    Button onlineJoinBtn;
    String gameID = "";
    ArrayList<String> ids;
    String playerID;
    int numberOfPlayers;
    Dialog shareInviteDialog;
    EditText numberEt;
    ImageView startGameBtn;
    Button shareGameBtn;
    TextView tableCodeTv;

    FirebaseFirestore firestore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_online_game_menu);


//        ids = new ArrayList<>();
//        firestore = FirebaseFirestore.getInstance();
//
//        Intent bundle = getIntent();
//        playerID = bundle.getStringExtra("playerid");
//
//        numberOfPlayers = 2;
//
//        onlineCreateBtn = (Button) findViewById(R.id.onlinecreatebtn);
//        onlineJoinBtn = (Button) findViewById(R.id.onlinejoinbtn);
//
//        onlineCreateBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//                Game game = new Game();
//
//                //game.startGame(numberOfPlayers,"","");
//
//                ArrayList<String> decks = game.getAllPlayerDecksAsStrings();
//                ArrayList<Integer> positions = new ArrayList<>();
//
//                for(int i = 0;i < numberOfPlayers;++i){
//                    positions.add(i);
//                }
//                createGame(decks, positions);
//            }
//        });
//
//        onlineJoinBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(OnlineGameMenu.this, OnlineJoinGame.class);
//                intent.putExtra("playerid", playerID);
//
//                startActivity(intent);
//            }
//        });
    }

    private void createMultiplePlayerGames(String gameID, int numberOfPlayers, ArrayList<String> decks, ArrayList<Integer> positions) {

        for (int i = 0; i < numberOfPlayers; ++i) {
            createPlayerGame(gameID, decks.get(i), positions.get(i));
        }

        createNumberDialog();

        shareInviteDialog.show();

        tableCodeTv.setText(gameID);

        shareGameBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1 = new Intent();
                intent1.setAction(Intent.ACTION_SEND);
                intent1.putExtra(Intent.EXTRA_TEXT,gameID);

                intent1.setType("text/plain");
                intent1.setPackage("com.whatsapp");

                startActivity(intent1);
            }
        });

        startGameBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //boolean installed = isAppInstalled("com.whatsapp");
               // if(installed){

                    //String text = gameID;
                //}

               Intent intent = new Intent(OnlineGameMenu.this, OnlineGameActivity.class);
               intent.putExtra("gameid", gameID);
               intent.putExtra("playerid", playerID);
               //intent.putExtra("numplayers", String.valueOf(numberOfPlayers));

               startActivity(intent);

            }
        });
    }

    public void createGame(ArrayList<String> decks, ArrayList<Integer> positions) {

        Map<String, Object> gameMap = new HashMap<>();

        gameMap.put("gameTurn", "0");
        gameMap.put("gameMessage", "NewGame");
        gameMap.put("gameWinner", "");
        gameMap.put("gameCardsInDeckLesserThanSevenString", "");
        gameMap.put("gameCardsInDeckGreaterThanSevenString", "");

        firestore.collection("games").add(gameMap).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
            @Override
            public void onSuccess(DocumentReference documentReference) {

                gameID = documentReference.getId();
                createMultiplePlayerGames(gameID, numberOfPlayers, decks, positions);
            }
        })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        //Log.w(TAG, "Error adding document", e);
                    }
                });
    }

    public void createPlayerGame(String gameID, String deck, int position) {

        Map<String, Object> playerGameMap = new HashMap<>();

        playerGameMap.put("playerID", "");
        playerGameMap.put("playerNumberOfPasses", 0);
        playerGameMap.put("playerGameScore", 0);
        playerGameMap.put("gameID", gameID);
        playerGameMap.put("position", position);
        playerGameMap.put("deck", deck);

        firestore.collection("playerGames").add(playerGameMap)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        //Log.d(TAG, "DocumentSnapshot added with ID: " + documentReference.getId());
                        String pg = documentReference.getId();
                        //Toast.makeText(OnlineGameMenu.this, "Player is " + pg , Toast.LENGTH_SHORT).show();
                        ids.add(pg);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        //Log.w(TAG, "Error adding document", e);
                    }
                });
    }

    public void createNumberDialog() {

        shareInviteDialog = new Dialog(OnlineGameMenu.this);
        shareInviteDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        //numberDialog.setCancelable(false);
        //numberDialog.setCanceledOnTouchOutside(false);
        shareInviteDialog.setContentView(R.layout.numberlayout);

        numberEt = (EditText) shareInviteDialog.findViewById(R.id.searchGameEt);
        startGameBtn = (ImageView) shareInviteDialog.findViewById(R.id.btnNumber);
        shareGameBtn = (Button) shareInviteDialog.findViewById(R.id.btnShare);
        tableCodeTv = (TextView)shareInviteDialog.findViewById(R.id.tableCode);

    }

    private boolean isAppInstalled(String s) {

        PackageManager pm  = getPackageManager();

        boolean isInstalled = true;

        try {
            pm.getPackageInfo("",PackageManager.GET_ACTIVITIES);
            isInstalled = true;

        } catch (PackageManager.NameNotFoundException e) {
            isInstalled = false;
            e.printStackTrace();
        }
        return isInstalled;
    }

}