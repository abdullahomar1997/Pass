package com.example.passa.OnlineGame;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

//import com.google.firebase.database.DataSnapshot;
//import com.google.firebase.database.DatabaseError;
//import com.google.firebase.database.DatabaseReference;
//import com.google.firebase.database.FirebaseDatabase;
//import com.google.firebase.database.ValueEventListener;

import com.example.passa.Game;
import com.example.passa.PlayerGame;
import com.example.passa.R;
import com.example.passa.StartUpMenu;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class OnlineGameActivity extends AppCompatActivity {

    ImageView imageOfASpadeCardPlayed;
    ImageView imageOfKSpadeCardPlayed;
    ImageView imageOfQSpadeCardPlayed;
    ImageView imageOfJSpadeCardPlayed;
    ImageView imageOf10SpadeCardPlayed;
    ImageView imageOf9SpadeCardPlayed;
    ImageView imageOf8SpadeCardPlayed;
    ImageView imageOf7SpadeCardPlayed;
    ImageView imageOf6SpadeCardPlayed;
    ImageView imageOf5SpadeCardPlayed;
    ImageView imageOf4SpadeCardPlayed;
    ImageView imageOf3SpadeCardPlayed;
    ImageView imageOf2SpadeCardPlayed;

    ImageView imageOfAHeartCardPlayed;
    ImageView imageOfKHeartCardPlayed;
    ImageView imageOfQHeartCardPlayed;
    ImageView imageOfJHeartCardPlayed;
    ImageView imageOf10HeartCardPlayed;
    ImageView imageOf9HeartCardPlayed;
    ImageView imageOf8HeartCardPlayed;
    ImageView imageOf7HeartCardPlayed;
    ImageView imageOf6HeartCardPlayed;
    ImageView imageOf5HeartCardPlayed;
    ImageView imageOf4HeartCardPlayed;
    ImageView imageOf3HeartCardPlayed;
    ImageView imageOf2HeartCardPlayed;

    ImageView imageOfAFlowerCardPlayed;
    ImageView imageOfKFlowerCardPlayed;
    ImageView imageOfQFlowerCardPlayed;
    ImageView imageOfJFlowerCardPlayed;
    ImageView imageOf10FlowerCardPlayed;
    ImageView imageOf9FlowerCardPlayed;
    ImageView imageOf8FlowerCardPlayed;
    ImageView imageOf7FlowerCardPlayed;
    ImageView imageOf6FlowerCardPlayed;
    ImageView imageOf5FlowerCardPlayed;
    ImageView imageOf4FlowerCardPlayed;
    ImageView imageOf3FlowerCardPlayed;
    ImageView imageOf2FlowerCardPlayed;

    ImageView imageOfADiamondCardPlayed;
    ImageView imageOfKDiamondCardPlayed;
    ImageView imageOfQDiamondCardPlayed;
    ImageView imageOfJDiamondCardPlayed;
    ImageView imageOf10DiamondCardPlayed;
    ImageView imageOf9DiamondCardPlayed;
    ImageView imageOf8DiamondCardPlayed;
    ImageView imageOf7DiamondCardPlayed;
    ImageView imageOf6DiamondCardPlayed;
    ImageView imageOf5DiamondCardPlayed;
    ImageView imageOf4DiamondCardPlayed;
    ImageView imageOf3DiamondCardPlayed;
    ImageView imageOf2DiamondCardPlayed;

    ImageView cardButtonA1;
    ImageView cardButtonA2;
    ImageView cardButtonA3;
    ImageView cardButtonA4;
    ImageView cardButtonA5;
    ImageView cardButtonA6;
    ImageView cardButtonA7;

    ImageView cardButtonB1;
    ImageView cardButtonB2;
    ImageView cardButtonB3;
    ImageView cardButtonB4;
    ImageView cardButtonB5;
    ImageView cardButtonB6;
    ImageView cardButtonB7;

    ImageView cardButtonC1;
    ImageView cardButtonC2;
    ImageView cardButtonC3;
    ImageView cardButtonC4;
    ImageView cardButtonC5;
    ImageView cardButtonC6;
    ImageView cardButtonC7;

    ImageView cardButtonD1;
    ImageView cardButtonD2;
    ImageView cardButtonD3;
    ImageView cardButtonD4;
    ImageView cardButtonD5;

    ImageView leaveBtn;
    ImageView passButton;

    TextView messageTv;
    TextView passesTv;

    ArrayList<ImageView> buttons;
    ArrayList<ImageView> tableImagesD;
    ArrayList<ImageView> tableImagesS;
    ArrayList<ImageView> tableImagesF;
    ArrayList<ImageView> tableImagesH;

    FirebaseFirestore firestore;

    Dialog leaveDialog;
    Dialog scoreDialog;

    String playerId = "";
    String gameId = "";
    boolean myTurn;
    int position;
    int opponentPosition;
    int numPlayers;

    ArrayList<Integer> positionsList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent bundle = getIntent();
        gameId = bundle.getStringExtra("gameid");
        playerId = bundle.getStringExtra("playerid");
        numPlayers = bundle.getIntExtra("numberofplayers",2);

        createPositions(numPlayers);

        setupUserInterface();

        Game game = new Game();

        joinPlayerToGame(game, gameId, playerId);

        cardButtonA1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Play(game,0,messageTv,cardButtonA1);

            }
        });
        cardButtonA2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Play(game,1,messageTv,cardButtonA2);

            }
        });
        cardButtonA3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Play(game,2,messageTv,cardButtonA3);

            }
        });
        cardButtonA4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Play(game,3,messageTv,cardButtonA4);

            }
        });
        cardButtonA5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Play(game,4,messageTv,cardButtonA5);

            }
        });
        cardButtonA6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Play(game,5,messageTv,cardButtonA6);
            }
        });
        cardButtonA7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Play(game,6,messageTv,cardButtonA7);

            }
        });
        cardButtonB1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Play(game,7,messageTv,cardButtonB1);

            }
        });
        cardButtonB2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Play(game,8,messageTv,cardButtonB2);

            }
        });
        cardButtonB3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Play(game,9,messageTv,cardButtonB3);

            }
        });
        cardButtonB4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Play(game,10,messageTv,cardButtonB4);

            }
        });
        cardButtonB5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Play(game,11,messageTv,cardButtonB5);

            }
        });
        cardButtonB6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Play(game,12,messageTv,cardButtonB6);

            }
        });
        cardButtonB7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Play(game,13,messageTv,cardButtonB7);

            }
        });
        cardButtonC1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Play(game,14,messageTv,cardButtonC1);

            }
        });
        cardButtonC2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Play(game,15,messageTv,cardButtonC2);

            }
        });
        cardButtonC3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Play(game,16,messageTv,cardButtonC3);

            }
        });
        cardButtonC4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Play(game,17,messageTv,cardButtonC4);

            }
        });
        cardButtonC5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Play(game,18,messageTv,cardButtonC5);

            }
        });
        cardButtonC6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Play(game,19,messageTv,cardButtonC6);

            }
        });
        cardButtonC7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Play(game,20,messageTv,cardButtonC7);

            }
        });
        cardButtonD1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Play(game,21,messageTv,cardButtonD1);

            }
        });
        cardButtonD2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Play(game,22,messageTv,cardButtonD2);

            }
        });
        cardButtonD3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Play(game,23,messageTv,cardButtonD3);

            }
        });
        cardButtonD4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Play(game,24,messageTv,cardButtonD4);
            }
        });
        cardButtonD5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Play(game,25,messageTv,cardButtonD5);
            }
        });

        passButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                int passInt = game.getPlayer(0).getNumberOfPasses();

                passesTv.setText(String.valueOf(passInt));

                messageTv.setText("");

                passButton.setVisibility(View.INVISIBLE);

                switchPlayerTurn(game);
            }
        });

        leaveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                createLeaveDialog();

                ImageView noLeaveBtn = (ImageView) leaveDialog.findViewById(R.id.btnNoDelete);
                ImageView yesLeaveBtn = (ImageView) leaveDialog.findViewById(R.id.btnYesDelete);

                leaveDialog.show();

                noLeaveBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        leaveDialog.dismiss();
                    }
                });

                yesLeaveBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        finish();
                    }
                });
            }
        });
    }

    private void createPositions(int numplayers) {
        positionsList = new ArrayList<>();
        for(int i = 0;i < numplayers;++i){
            positionsList.add(i);
        }
        positionsList.add(0);
    }

    private void updatePlayerGame(Game game,String playerGameId, String playerId,String deck) {

        DocumentReference pgr = firestore.collection("playerGames").document(playerGameId);

        pgr.update("playerID",playerId).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {

                game.startGame(numPlayers,deck,"Online");

                assignCards(game,0,buttons);

                if(position == 0){
                    myTurn = true;
                }

                real(game);
            };
        });
    }

    private void joinPlayerToGame(Game game,String gameId,String playerId) {

        CollectionReference playergameRef = firestore.collection("playerGames");

        playergameRef.whereEqualTo("gameID", gameId).whereEqualTo("playerID", "").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {

                        String pgid = document.getId();

                        String deck = document.getString("deck");
                        position = document.getLong("position").intValue();

                        updatePlayerGame(game,pgid,playerId,deck);

                        break;
                    }
                }
            }
        });
    }

    private void switchPlayerTurn(Game game) {

        game.convertTableDeckToString();

        myTurn = false;

        messageTv.setText("");

        DocumentReference pgr = firestore.collection("games").document(gameId);

        pgr.update("gameTurn", String.valueOf(opponentPosition),"gameMessage",game.getGameDisplayMessage(),"gameWinner",game.getGameWinner(),"gameCardsInDeckLesserThanSevenString",game.getGameCardsInDeckLesserThanSevenString(),"gameCardsInDeckGreaterThanSevenString",game.getGameCardsInDeckGreaterThanSevenString()).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {

            }
        });
    }

    public void Play(Game game, int cardnum, TextView messageTv, ImageView button) {

        if(myTurn){

            String key = game.gamePlayersList.get(0).getCardAtDeckPosition(cardnum);

            if (game.isCardPlayable(key)) {

                game.gamePlayersList.get(0).setPlayerDeckAtPosition(cardnum, "o o");
                game.gamePlayersList.get(0).addNumberOfCardsPlayed();

                game.playCard(key);

                if (game.isOnlineGameOver()) {
                    game.setGameWinner(playerId);
                    displayScore(game,"You Win");
                }

                game.setGameDisplayMessage(playerId += " played "+ key);

                switchPlayerTurn(game);

                button.setVisibility(View.INVISIBLE);
            }
        }
    }

    public void updateCurrentGameTable(Game game) {

        for(int ii = 0;ii < game.getSizeOfGameCardsInDeckGreaterThanSeven();++ii){

            String card = game.getCardInGameCardsInDeckGreaterThanSevenAt(ii);
            if(card.contains("s")){

                String sa = card;
                int saa = conv(sa);

                for (int i = saa; i <= 7; ++i) {
                    tableImagesS.get(i).setVisibility(View.VISIBLE);
                }
            }

            if(card.contains("h")){

                String ha = card;
                int haa = conv(ha);

                for(int i = haa;i <= 7;++i){
                    tableImagesH.get(i).setVisibility(View.VISIBLE);
                }
            }

            if(card.contains("f")){

                String fa = card;
                int faa = conv(fa);

                for(int i = faa;i <= 7;++i){
                    tableImagesF.get(i).setVisibility(View.VISIBLE);
                }
            }

            if(card.contains("d")){

                String da = card;

                int daa = conv(da);

                for(int i = daa;i <= 7;++i){
                    tableImagesD.get(i).setVisibility(View.VISIBLE);
                }
            }
        }

        for(int ii = 0;ii < game.getSizeOfGameCardsInDeckLesserThanSeven();++ii){

            String card = game.getCardInGameCardsInDeckLesserThanSevenAt(ii);

            if(card.contains("s")){

                String sa = card;
                int saa = conv(sa);

                for (int i = 7; i <= saa; ++i) {
                    tableImagesS.get(i).setVisibility(View.VISIBLE);
                }
            }

            if(card.contains("h")){

                String ha = card;
                int haa = conv(ha);

                for(int i = 7;i <= haa;++i){
                    tableImagesH.get(i).setVisibility(View.VISIBLE);
                }
            }

            if(card.contains("f")){

                String fa = card;
                int faa = conv(fa);

                for(int i = 7;i <= faa;++i){
                    tableImagesF.get(i).setVisibility(View.VISIBLE);
                }
            }

            if(card.contains("d")){

                String da = card;
                int daa = conv(da);

                for(int i = 7;i <= daa;++i){
                    tableImagesD.get(i).setVisibility(View.VISIBLE);
                }
            }
        }
    }

    public Bitmap convertStringCardToImage(String Card) {

        Bitmap cardImage = BitmapFactory.decodeResource(getResources(), R.drawable.h7);

        String[] card = Card.split(" ");
        String cardNo = card[0];
        String cardSign = card[1];

        if (cardNo.equals("10") && cardSign.equals("h")) {
            cardImage = BitmapFactory.decodeResource(getResources(), R.drawable.h10);
        } else if (cardNo.equals("9") && cardSign.equals("h")) {
            cardImage = BitmapFactory.decodeResource(getResources(), R.drawable.h9);
        } else if (cardNo.equals("8") && cardSign.equals("h")) {
            cardImage = BitmapFactory.decodeResource(getResources(), R.drawable.h8);
        } else if (cardNo.equals("7") && cardSign.equals("h")) {
            cardImage = BitmapFactory.decodeResource(getResources(), R.drawable.h7);
        } else if (cardNo.equals("6") && cardSign.equals("h")) {
            cardImage = BitmapFactory.decodeResource(getResources(), R.drawable.h6);
        } else if (cardNo.equals("5") && cardSign.equals("h")) {
            cardImage = BitmapFactory.decodeResource(getResources(), R.drawable.h5);
        } else if (cardNo.equals("4") && cardSign.equals("h")) {
            cardImage = BitmapFactory.decodeResource(getResources(), R.drawable.h4);
        } else if (cardNo.equals("3") && cardSign.equals("h")) {
            cardImage = BitmapFactory.decodeResource(getResources(), R.drawable.h3);
        } else if (cardNo.equals("2") && cardSign.equals("h")) {
            cardImage = BitmapFactory.decodeResource(getResources(), R.drawable.h2);
        } else if (cardNo.equals("10") && cardSign.equals("s")) {
            cardImage = BitmapFactory.decodeResource(getResources(), R.drawable.s10);
        } else if (cardNo.equals("9") && cardSign.equals("s")) {
            cardImage = BitmapFactory.decodeResource(getResources(), R.drawable.s9);
        } else if (cardNo.equals("8") && cardSign.equals("s")) {
            cardImage = BitmapFactory.decodeResource(getResources(), R.drawable.s8);
        } else if (cardNo.equals("7") && cardSign.equals("s")) {
            cardImage = BitmapFactory.decodeResource(getResources(), R.drawable.s7);
        } else if (cardNo.equals("6") && cardSign.equals("s")) {
            cardImage = BitmapFactory.decodeResource(getResources(), R.drawable.s6);
        } else if (cardNo.equals("5") && cardSign.equals("s")) {
            cardImage = BitmapFactory.decodeResource(getResources(), R.drawable.s5);
        } else if (cardNo.equals("4") && cardSign.equals("s")) {
            cardImage = BitmapFactory.decodeResource(getResources(), R.drawable.s4);
        } else if (cardNo.equals("3") && cardSign.equals("s")) {
            cardImage = BitmapFactory.decodeResource(getResources(), R.drawable.s3);
        } else if (cardNo.equals("2") && cardSign.equals("s")) {
            cardImage = BitmapFactory.decodeResource(getResources(), R.drawable.s2);
        } else if (cardNo.equals("10") && cardSign.equals("f")) {
            cardImage = BitmapFactory.decodeResource(getResources(), R.drawable.f10);
        } else if (cardNo.equals("9") && cardSign.equals("f")) {
            cardImage = BitmapFactory.decodeResource(getResources(), R.drawable.f9);
        } else if (cardNo.equals("8") && cardSign.equals("f")) {
            cardImage = BitmapFactory.decodeResource(getResources(), R.drawable.f8);
        } else if (cardNo.equals("7") && cardSign.equals("f")) {
            cardImage = BitmapFactory.decodeResource(getResources(), R.drawable.f7);
        } else if (cardNo.equals("6") && cardSign.equals("f")) {
            cardImage = BitmapFactory.decodeResource(getResources(), R.drawable.f6);
        } else if (cardNo.equals("5") && cardSign.equals("f")) {
            cardImage = BitmapFactory.decodeResource(getResources(), R.drawable.f5);
        } else if (cardNo.equals("4") && cardSign.equals("f")) {
            cardImage = BitmapFactory.decodeResource(getResources(), R.drawable.f4);
        } else if (cardNo.equals("3") && cardSign.equals("f")) {
            cardImage = BitmapFactory.decodeResource(getResources(), R.drawable.f3);
        } else if (cardNo.equals("2") && cardSign.equals("f")) {
            cardImage = BitmapFactory.decodeResource(getResources(), R.drawable.f2);
        } else if (cardNo.equals("10") && cardSign.equals("d")) {
            cardImage = BitmapFactory.decodeResource(getResources(), R.drawable.cardback);
        } else if (cardNo.equals("9") && cardSign.equals("d")) {
            cardImage = BitmapFactory.decodeResource(getResources(), R.drawable.d9);
        } else if (cardNo.equals("8") && cardSign.equals("d")) {
            cardImage = BitmapFactory.decodeResource(getResources(), R.drawable.d8);
        } else if (cardNo.equals("7") && cardSign.equals("d")) {
            cardImage = BitmapFactory.decodeResource(getResources(), R.drawable.d7);
        } else if (cardNo.equals("6") && cardSign.equals("d")) {
            cardImage = BitmapFactory.decodeResource(getResources(), R.drawable.d6);
        } else if (cardNo.equals("5") && cardSign.equals("d")) {
            cardImage = BitmapFactory.decodeResource(getResources(), R.drawable.d5);
        } else if (cardNo.equals("4") && cardSign.equals("d")) {
            cardImage = BitmapFactory.decodeResource(getResources(), R.drawable.d4);
        } else if (cardNo.equals("3") && cardSign.equals("d")) {
            cardImage = BitmapFactory.decodeResource(getResources(), R.drawable.d3);
        } else if (cardNo.equals("2") && cardSign.equals("d")) {
            cardImage = BitmapFactory.decodeResource(getResources(), R.drawable.d2);
        } else if (cardNo.equals("o") && cardSign.equals("o")) {
            cardImage = BitmapFactory.decodeResource(getResources(), R.drawable.cardback);
        } else if (cardNo.equals("a") && cardSign.equals("d")) {
            cardImage = BitmapFactory.decodeResource(getResources(), R.drawable.da);
        } else if (cardNo.equals("k") && cardSign.equals("d")) {
            cardImage = BitmapFactory.decodeResource(getResources(), R.drawable.dk);
        } else if (cardNo.equals("q") && cardSign.equals("d")) {
            cardImage = BitmapFactory.decodeResource(getResources(), R.drawable.dq);
        } else if (cardNo.equals("j") && cardSign.equals("d")) {
            cardImage = BitmapFactory.decodeResource(getResources(), R.drawable.dj);
        } else if (cardNo.equals("a") && cardSign.equals("s")) {
            cardImage = BitmapFactory.decodeResource(getResources(), R.drawable.sa);
        } else if (cardNo.equals("k") && cardSign.equals("s")) {
            cardImage = BitmapFactory.decodeResource(getResources(), R.drawable.sk);
        } else if (cardNo.equals("q") && cardSign.equals("s")) {
            cardImage = BitmapFactory.decodeResource(getResources(), R.drawable.sq);
        } else if (cardNo.equals("j") && cardSign.equals("s")) {
            cardImage = BitmapFactory.decodeResource(getResources(), R.drawable.sj);
        } else if (cardNo.equals("a") && cardSign.equals("h")) {
            cardImage = BitmapFactory.decodeResource(getResources(), R.drawable.ha);
        } else if (cardNo.equals("k") && cardSign.equals("h")) {
            cardImage = BitmapFactory.decodeResource(getResources(), R.drawable.hk);
        } else if (cardNo.equals("q") && cardSign.equals("h")) {
            cardImage = BitmapFactory.decodeResource(getResources(), R.drawable.hq);
        } else if (cardNo.equals("j") && cardSign.equals("h")) {
            cardImage = BitmapFactory.decodeResource(getResources(), R.drawable.hj);
        } else if (cardNo.equals("a") && cardSign.equals("f")) {
            cardImage = BitmapFactory.decodeResource(getResources(), R.drawable.fa);
        } else if (cardNo.equals("k") && cardSign.equals("f")) {
            cardImage = BitmapFactory.decodeResource(getResources(), R.drawable.fk);
        } else if (cardNo.equals("q") && cardSign.equals("f")) {
            cardImage = BitmapFactory.decodeResource(getResources(), R.drawable.fq);
        } else if (cardNo.equals("j") && cardSign.equals("f")) {
            cardImage = BitmapFactory.decodeResource(getResources(), R.drawable.fj);
        }
        return cardImage;
    }

    private void assignCards(Game game, int playerPosition,ArrayList<ImageView> buttons) {

        PlayerGame playerGame = game.gamePlayersList.get(playerPosition);

        for (int i = 0; i < playerGame.getPlayerDeck().size(); ++i) {
            buttons.get(i).setVisibility(View.VISIBLE);
            buttons.get(i).setImageBitmap(convertStringCardToImage(playerGame.getCardAtDeckPosition(i)));
        }
    }

    private int conv(String Card) {

        String[] card = Card.split(" ");
        String cardNo = card[0];

        if (cardNo.equals("a")) {
            return 0;
        } else if (cardNo.equals("k")) {
            return 1;
        } else if (cardNo.equals("q")) {
            return 2;
        } else if (cardNo.equals("j")) {
            return 3;
        } else if (cardNo.equals("10")) {
            return 4;
        } else if (cardNo.equals("9")) {
            return 5;
        } else if (cardNo.equals("8")) {
            return 6;
        } else if (cardNo.equals("7")) {
            return 7;
        } else if (cardNo.equals("6")) {
            return 8;
        } else if (cardNo.equals("5")) {
            return 9;
        } else if (cardNo.equals("4")) {
            return 10;
        } else if (cardNo.equals("3")) {
            return 11;
        } else {
            return 12;
        }
    }

    public void real(Game game){

        DocumentReference documentReference = firestore.collection("games").document(gameId);

        documentReference.addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {

                if(value != null && value.exists()) {

                    String gameTurn = value.getString("gameTurn");
                    String gameLess = value.getString("gameCardsInDeckLesserThanSevenString");
                    String gameGreater = value.getString("gameCardsInDeckGreaterThanSevenString");
                    String gameWinner = value.getString("gameWinner");
                    String gameMessage = value.getString("gameMessage");

                    game.setGameCardsInDeckGreaterThanSevenString(gameGreater);
                    game.setGameCardsInDeckLesserThanSevenString(gameLess);
                    game.setGamePlayerTurn(gameTurn);
                    game.setGameWinner(gameWinner);
                    game.setGameDisplayMessage(gameMessage);

                    game.convertTableDeckToList();

                    updateCurrentGameTable(game);

                    if(!gameWinner.equals("")){
                        //displayScore(game,gameWinner);
                        messageTv.setText(gameWinner + " won this game ");
                    }

                    else if(gameMessage.equals("NewGame") && gameTurn.equals(String.valueOf(position))){

                        myTurn = true;

                        messageTv.setText("Play First");

                        if (game.playerHasNoPlayableCards(0)) {
                            passButton.setVisibility(View.VISIBLE);
                        }
                    }

                    else if (gameTurn.equals(String.valueOf(position))) {

                        myTurn = true;


                        messageTv.setText("Your Turn");

                        if (game.playerHasNoPlayableCards(0)) {
                            passButton.setVisibility(View.VISIBLE);
                        }
                    } else {
                        messageTv.setText("");
                        myTurn = false;
                    }
                }
            }
        });
    }

    public void setupUserInterface() {

        firestore = FirebaseFirestore.getInstance();

        buttons = new ArrayList<>();
        tableImagesD = new ArrayList<>();
        tableImagesS = new ArrayList<>();
        tableImagesF = new ArrayList<>();
        tableImagesH = new ArrayList<>();

        imageOfASpadeCardPlayed = (ImageView) findViewById(R.id.carda1);
        imageOfKSpadeCardPlayed = (ImageView) findViewById(R.id.carda2);
        imageOfQSpadeCardPlayed = (ImageView) findViewById(R.id.carda3);
        imageOfJSpadeCardPlayed = (ImageView) findViewById(R.id.carda4);
        imageOf10SpadeCardPlayed = (ImageView) findViewById(R.id.carda5);
        imageOf9SpadeCardPlayed = (ImageView) findViewById(R.id.carda6);
        imageOf8SpadeCardPlayed = (ImageView) findViewById(R.id.carda7);
        imageOf7SpadeCardPlayed = (ImageView) findViewById(R.id.carda8);
        imageOf6SpadeCardPlayed = (ImageView) findViewById(R.id.carda9);
        imageOf5SpadeCardPlayed = (ImageView) findViewById(R.id.carda10);
        imageOf4SpadeCardPlayed = (ImageView) findViewById(R.id.carda11);
        imageOf3SpadeCardPlayed = (ImageView) findViewById(R.id.carda12);
        imageOf2SpadeCardPlayed = (ImageView) findViewById(R.id.carda13);

        imageOfAHeartCardPlayed = (ImageView) findViewById(R.id.cardb1);
        imageOfKHeartCardPlayed = (ImageView) findViewById(R.id.cardb2);
        imageOfQHeartCardPlayed = (ImageView) findViewById(R.id.cardb3);
        imageOfJHeartCardPlayed = (ImageView) findViewById(R.id.cardb4);
        imageOf10HeartCardPlayed = (ImageView) findViewById(R.id.cardb5);
        imageOf9HeartCardPlayed = (ImageView) findViewById(R.id.cardb6);
        imageOf8HeartCardPlayed = (ImageView) findViewById(R.id.cardb7);
        imageOf7HeartCardPlayed = (ImageView) findViewById(R.id.cardb8);
        imageOf6HeartCardPlayed = (ImageView) findViewById(R.id.cardb9);
        imageOf5HeartCardPlayed = (ImageView) findViewById(R.id.cardb10);
        imageOf4HeartCardPlayed = (ImageView) findViewById(R.id.cardb11);
        imageOf3HeartCardPlayed = (ImageView) findViewById(R.id.cardb12);
        imageOf2HeartCardPlayed = (ImageView) findViewById(R.id.cardb13);

        imageOfAFlowerCardPlayed = (ImageView) findViewById(R.id.cardc1);
        imageOfKFlowerCardPlayed = (ImageView) findViewById(R.id.cardc2);
        imageOfQFlowerCardPlayed = (ImageView) findViewById(R.id.cardc3);
        imageOfJFlowerCardPlayed = (ImageView) findViewById(R.id.cardc4);
        imageOf10FlowerCardPlayed = (ImageView) findViewById(R.id.cardc5);
        imageOf9FlowerCardPlayed = (ImageView) findViewById(R.id.cardc6);
        imageOf8FlowerCardPlayed = (ImageView) findViewById(R.id.cardc7);
        imageOf7FlowerCardPlayed = (ImageView) findViewById(R.id.cardc8);
        imageOf6FlowerCardPlayed = (ImageView) findViewById(R.id.cardc9);
        imageOf5FlowerCardPlayed = (ImageView) findViewById(R.id.cardc10);
        imageOf4FlowerCardPlayed = (ImageView) findViewById(R.id.cardc11);
        imageOf3FlowerCardPlayed = (ImageView) findViewById(R.id.cardc12);
        imageOf2FlowerCardPlayed = (ImageView) findViewById(R.id.cardc13);

        imageOfADiamondCardPlayed = (ImageView) findViewById(R.id.cardd1);
        imageOfKDiamondCardPlayed = (ImageView) findViewById(R.id.cardd2);
        imageOfQDiamondCardPlayed = (ImageView) findViewById(R.id.cardd3);
        imageOfJDiamondCardPlayed = (ImageView) findViewById(R.id.cardd4);
        imageOf10DiamondCardPlayed = (ImageView) findViewById(R.id.cardd5);
        imageOf9DiamondCardPlayed = (ImageView) findViewById(R.id.cardd6);
        imageOf8DiamondCardPlayed = (ImageView) findViewById(R.id.cardd7);
        imageOf7DiamondCardPlayed = (ImageView) findViewById(R.id.cardd8);
        imageOf6DiamondCardPlayed = (ImageView) findViewById(R.id.cardd9);
        imageOf5DiamondCardPlayed = (ImageView) findViewById(R.id.cardd10);
        imageOf4DiamondCardPlayed = (ImageView) findViewById(R.id.cardd11);
        imageOf3DiamondCardPlayed = (ImageView) findViewById(R.id.cardd12);
        imageOf2DiamondCardPlayed = (ImageView) findViewById(R.id.cardd13);

        cardButtonA1 = (ImageView) findViewById(R.id.card1);
        cardButtonA2 = (ImageView) findViewById(R.id.card2);
        cardButtonA3 = (ImageView) findViewById(R.id.card3);
        cardButtonA4 = (ImageView) findViewById(R.id.card4);
        cardButtonA5 = (ImageView) findViewById(R.id.card5);
        cardButtonA6 = (ImageView) findViewById(R.id.card6);
        cardButtonA7 = (ImageView) findViewById(R.id.card7);

        cardButtonB1 = (ImageView) findViewById(R.id.card8);
        cardButtonB2 = (ImageView) findViewById(R.id.card9);
        cardButtonB3 = (ImageView) findViewById(R.id.card10);
        cardButtonB4 = (ImageView) findViewById(R.id.card11);
        cardButtonB5 = (ImageView) findViewById(R.id.card12);
        cardButtonB6 = (ImageView) findViewById(R.id.card13);
        cardButtonB7 = (ImageView) findViewById(R.id.card14);

        cardButtonC1 = (ImageView) findViewById(R.id.card15);
        cardButtonC2 = (ImageView) findViewById(R.id.card16);
        cardButtonC3 = (ImageView) findViewById(R.id.card17);
        cardButtonC4 = (ImageView) findViewById(R.id.card18);
        cardButtonC5 = (ImageView) findViewById(R.id.card19);
        cardButtonC6 = (ImageView) findViewById(R.id.card20);
        cardButtonC7 = (ImageView) findViewById(R.id.card21);

        cardButtonD1 = (ImageView) findViewById(R.id.card22);
        cardButtonD2 = (ImageView) findViewById(R.id.card23);
        cardButtonD3 = (ImageView) findViewById(R.id.card24);
        cardButtonD4 = (ImageView) findViewById(R.id.card25);
        cardButtonD5 = (ImageView) findViewById(R.id.card26);

       // passButton = (ImageView) findViewById(R.id.proceedbutton);
        leaveBtn = (ImageView) findViewById(R.id.cardoptions);

        messageTv = (TextView) findViewById(R.id.message);
        passesTv = (TextView) findViewById(R.id.numPassesTv);

        //overlay = findViewById(R.id.mainView);
        //overlay.setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY | View.SYSTEM_UI_FLAG_FULLSCREEN);

        this.getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);

        passButton.setVisibility(View.INVISIBLE);

        tableImagesD.add(imageOfADiamondCardPlayed);
        tableImagesD.add(imageOfKDiamondCardPlayed);
        tableImagesD.add(imageOfQDiamondCardPlayed);
        tableImagesD.add(imageOfJDiamondCardPlayed);
        tableImagesD.add(imageOf10DiamondCardPlayed);
        tableImagesD.add(imageOf9DiamondCardPlayed);
        tableImagesD.add(imageOf8DiamondCardPlayed);
        tableImagesD.add(imageOf7DiamondCardPlayed);
        tableImagesD.add(imageOf6DiamondCardPlayed);
        tableImagesD.add(imageOf5DiamondCardPlayed);
        tableImagesD.add(imageOf4DiamondCardPlayed);
        tableImagesD.add(imageOf3DiamondCardPlayed);
        tableImagesD.add(imageOf2DiamondCardPlayed);

        tableImagesF.add(imageOfAFlowerCardPlayed);
        tableImagesF.add(imageOfKFlowerCardPlayed);
        tableImagesF.add(imageOfQFlowerCardPlayed);
        tableImagesF.add(imageOfJFlowerCardPlayed);
        tableImagesF.add(imageOf10FlowerCardPlayed);
        tableImagesF.add(imageOf9FlowerCardPlayed);
        tableImagesF.add(imageOf8FlowerCardPlayed);
        tableImagesF.add(imageOf7FlowerCardPlayed);
        tableImagesF.add(imageOf6FlowerCardPlayed);
        tableImagesF.add(imageOf5FlowerCardPlayed);
        tableImagesF.add(imageOf4FlowerCardPlayed);
        tableImagesF.add(imageOf3FlowerCardPlayed);
        tableImagesF.add(imageOf2FlowerCardPlayed);

        tableImagesH.add(imageOfAHeartCardPlayed);
        tableImagesH.add(imageOfKHeartCardPlayed);
        tableImagesH.add(imageOfQHeartCardPlayed);
        tableImagesH.add(imageOfJHeartCardPlayed);
        tableImagesH.add(imageOf10HeartCardPlayed);
        tableImagesH.add(imageOf9HeartCardPlayed);
        tableImagesH.add(imageOf8HeartCardPlayed);
        tableImagesH.add(imageOf7HeartCardPlayed);
        tableImagesH.add(imageOf6HeartCardPlayed);
        tableImagesH.add(imageOf5HeartCardPlayed);
        tableImagesH.add(imageOf4HeartCardPlayed);
        tableImagesH.add(imageOf3HeartCardPlayed);
        tableImagesH.add(imageOf2HeartCardPlayed);

        tableImagesS.add(imageOfASpadeCardPlayed);
        tableImagesS.add(imageOfKSpadeCardPlayed);
        tableImagesS.add(imageOfQSpadeCardPlayed);
        tableImagesS.add(imageOfJSpadeCardPlayed);
        tableImagesS.add(imageOf10SpadeCardPlayed);
        tableImagesS.add(imageOf9SpadeCardPlayed);
        tableImagesS.add(imageOf8SpadeCardPlayed);
        tableImagesS.add(imageOf7SpadeCardPlayed);
        tableImagesS.add(imageOf6SpadeCardPlayed);
        tableImagesS.add(imageOf5SpadeCardPlayed);
        tableImagesS.add(imageOf4SpadeCardPlayed);
        tableImagesS.add(imageOf3SpadeCardPlayed);
        tableImagesS.add(imageOf2SpadeCardPlayed);

        for(int i = 0;i < tableImagesS.size();++i){
            tableImagesS.get(i).setVisibility(View.INVISIBLE);
            tableImagesF.get(i).setVisibility(View.INVISIBLE);
            tableImagesD.get(i).setVisibility(View.INVISIBLE);
            tableImagesH.get(i).setVisibility(View.INVISIBLE);
        }

        buttons.add(cardButtonA1);
        buttons.add(cardButtonA2);
        buttons.add(cardButtonA3);
        buttons.add(cardButtonA4);
        buttons.add(cardButtonA5);
        buttons.add(cardButtonA6);
        buttons.add(cardButtonA7);
        buttons.add(cardButtonB1);
        buttons.add(cardButtonB2);
        buttons.add(cardButtonB3);
        buttons.add(cardButtonB4);
        buttons.add(cardButtonB5);
        buttons.add(cardButtonB6);
        buttons.add(cardButtonB7);
        buttons.add(cardButtonC1);
        buttons.add(cardButtonC2);
        buttons.add(cardButtonC3);
        buttons.add(cardButtonC4);
        buttons.add(cardButtonC5);
        buttons.add(cardButtonC6);
        buttons.add(cardButtonC7);
        buttons.add(cardButtonD1);
        buttons.add(cardButtonD2);
        buttons.add(cardButtonD3);
        buttons.add(cardButtonD4);
        buttons.add(cardButtonD5);

        for (int i = 0; i < 26; ++i) {
            buttons.get(i).setVisibility(View.INVISIBLE);
        }
    }

    public void createLeaveDialog() {

        leaveDialog = new Dialog(OnlineGameActivity.this);
        leaveDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        leaveDialog.setCancelable(false);
        leaveDialog.setCanceledOnTouchOutside(false);
        leaveDialog.setContentView(R.layout.leavegamelayout);

    }

    private void displayScore(Game game,String outcome) {

        View view = getLayoutInflater().inflate(R.layout.scorelayout, null);
        ListView lv = (ListView) view.findViewById(R.id.listview);
        CustomListAdapterDialog clad = new CustomListAdapterDialog(this, game);
        lv.setAdapter(clad);

        scoreDialog = new Dialog(OnlineGameActivity.this);
        scoreDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        scoreDialog.setCancelable(false);
        scoreDialog.setCanceledOnTouchOutside(false);
        scoreDialog.setContentView(view);
        scoreDialog.show();

        TextView outcometv = (TextView) scoreDialog.findViewById(R.id.gameoutcometv);
        ImageView imageView_home = (ImageView) scoreDialog.findViewById(R.id.homeiv);

        outcometv.setText(outcome);

        imageView_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(OnlineGameActivity.this, StartUpMenu.class);
                startActivity(intent);
            }
        });
    }

    public class CustomListAdapterDialog extends BaseAdapter {

        private Game game2;

        private LayoutInflater layoutInflater;

        public CustomListAdapterDialog(Context context, Game game) {
            game2 = game;
            layoutInflater = LayoutInflater.from(context);
        }

        @Override
        public int getCount() {
            return numPlayers;
        }

        @Override
        public Object getItem(int position) {
            return position;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder;

            if (convertView == null) {

                convertView = layoutInflater.inflate(R.layout.scorelistitem, null);
                holder = new ViewHolder();
                holder.textView_rank = (TextView) convertView.findViewById(R.id.ranktv);
                holder.textView_playername = (TextView) convertView.findViewById(R.id.playernametv);
                holder.textView_score = (TextView) convertView.findViewById(R.id.scoretv);

                convertView.setTag(holder);
            } else {
                holder = (ViewHolder)convertView.getTag();
            }

            String pos = String.valueOf(position+1);
            String name = String.valueOf(game2.getPlayer(position).getPlayerId());
            String score = String.valueOf(game2.getPlayer(position).getScore());

            holder.textView_rank.setText(pos);
            holder.textView_playername.setText(name);
            holder.textView_score.setText(score);

            return convertView;
        }

        class ViewHolder {
            TextView textView_rank;
            TextView textView_playername;
            TextView textView_score;
        }
    }
//     <TextView
//                    android:id="@+id/message"
//                    android:layout_width="wrap_content"
//                    android:layout_height="wrap_content"
//                    android:text="Player 1"
//                    android:textColor="@android:color/white"
//                    android:textSize="10dp"
//                    />


}