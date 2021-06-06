package com.example.passa;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.passa.ImageActivities.ImageOfFingers;
import com.example.passa.ImageActivities.ImageViewOfCardsPlayed;
import com.example.passa.ImageActivities.ImageViewOfDeck;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    //0e3421
    //36393f
    //2f3136
    //2b2b2b
    //3c3f41
    //3c3f41
    //3e434c
    //365880

    final ImageOfFingers imageOfFingers = new ImageOfFingers();
    private final ImageViewOfCardsPlayed imageViewOfCardsPlayed = new ImageViewOfCardsPlayed();
    private final ImageViewOfDeck imageViewOfDeck = new ImageViewOfDeck();

    String PlayerCards = "";
    String PlayedCards = "";

    String cardsPlayed = "none:";

    Animation animation;

    ArrayList<ImageView> fingerList;
    ArrayList<ImageView> fingerp;

    private int currentApiVersion;

    private ArrayList<TextView> passTextsList;

    private ArrayList<TextView> noCardsList;

    private ArrayList<ImageView> avatarsList;

    private ArrayList<TextView> namesTextList;

    private boolean myTurn = true;

    private Animation slidedown;
    private Animation slideup;

    private ImageView leaveGameBtn;
    private ImageView passButton;
    private ImageView gameInfoBtn;

    private TextView messageTv;
    private Dialog leaveDialog;

    private boolean gameover = false;
    private int myPosition = 0;

    private Dialog playerScoresDialog;

    private ArrayList<ImageView> tableImagesD;
    private ArrayList<ImageView> tableImagesS;
    private ArrayList<ImageView> tableImagesF;
    private ArrayList<ImageView> tableImagesH;

    DatabaseHelper databaseHelper;

    @Override
    public void onBackPressed() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.onb_layout);

        databaseHelper = new DatabaseHelper(this);

        Intent bundle = getIntent();

        int numberOfPlayers = bundle.getIntExtra("numplayers", 2);
        String status = bundle.getStringExtra("status");

        Game game = new Game();

        if (status.equals("continue")) {

            getCardsFromOngoingGame(databaseHelper);

            game.continueGame(PlayerCards);
            
            setupUserInterface();

            setUpPlayerCards(game, myPosition);

            setUpPlayerLayout();

            setUpFingers(game);

            setUpPlayers(game);

            animation = new AlphaAnimation(1, 0); //to change visibility from visible to invisible

            fingerList.get(0).setVisibility(View.VISIBLE);

            updateOngoingGameTable(PlayedCards);
            cpuPlay(game,"");
        } else {
            game.startGame(numberOfPlayers);

            setupUserInterface();

            setUpPlayerCards(game, myPosition);

            setUpPlayerLayout();

            setUpFingers(game);

            setUpPlayers(game);

            animation = new AlphaAnimation(1, 0); //to change visibility from visible to invisible

            fingerList.get(0).setVisibility(View.VISIBLE);

        }

        if (game.playerHasNoPlayableCards(myPosition) && game.getGameWinner().equals("")) {
            enablePassButton();
        }

        cardsOnClick(game);

        gameInfoBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               // displayCurrentGameInfo(game);
            }
        });

        leaveGameBtn.setOnClickListener(new View.OnClickListener() {
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
                        //overlay.setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY | View.SYSTEM_UI_FLAG_FULLSCREEN);
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

        passButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                cpuPlay(game, "");

            }
        });
    }

    private void cardsOnClick(Game game) {

        imageViewOfDeck.getCardButtonA1().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Play(game, 0, messageTv, imageViewOfDeck.getCardButtonA1());

            }
        });
        imageViewOfDeck.getCardButtonA2().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Play(game, 1, messageTv, imageViewOfDeck.getCardButtonA2());

            }
        });
        imageViewOfDeck.getCardButtonA3().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Play(game, 2, messageTv, imageViewOfDeck.getCardButtonA3());

            }
        });
        imageViewOfDeck.getCardButtonA4().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Play(game, 3, messageTv, imageViewOfDeck.getCardButtonA4());

            }
        });
        imageViewOfDeck.getCardButtonA5().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Play(game, 4, messageTv, imageViewOfDeck.getCardButtonA5());

            }
        });
        imageViewOfDeck.getCardButtonA6().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Play(game, 5, messageTv, imageViewOfDeck.getCardButtonA6());
            }
        });
        imageViewOfDeck.getCardButtonA7().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Play(game, 6, messageTv, imageViewOfDeck.getCardButtonA7());

            }
        });
        imageViewOfDeck.getCardButtonB1().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Play(game, 7, messageTv, imageViewOfDeck.getCardButtonB1());

            }
        });
        imageViewOfDeck.getCardButtonB2().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Play(game, 8, messageTv, imageViewOfDeck.getCardButtonB2());

            }
        });
        imageViewOfDeck.getCardButtonB3().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Play(game, 9, messageTv, imageViewOfDeck.getCardButtonB3());

            }
        });
        imageViewOfDeck.getCardButtonB4().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Play(game, 10, messageTv, imageViewOfDeck.getCardButtonB4());

            }
        });
        imageViewOfDeck.getCardButtonB5().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Play(game, 11, messageTv, imageViewOfDeck.getCardButtonB5());

            }
        });
        imageViewOfDeck.getCardButtonB6().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Play(game, 12, messageTv, imageViewOfDeck.getCardButtonB6());

            }
        });
        imageViewOfDeck.getCardButtonB7().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Play(game, 13, messageTv, imageViewOfDeck.getCardButtonB7());

            }
        });
        imageViewOfDeck.getCardButtonC1().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Play(game, 14, messageTv, imageViewOfDeck.getCardButtonC1());

            }
        });
        imageViewOfDeck.getCardButtonC2().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Play(game, 15, messageTv, imageViewOfDeck.getCardButtonC2());

            }
        });
        imageViewOfDeck.getCardButtonC3().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Play(game, 16, messageTv, imageViewOfDeck.getCardButtonC3());

            }
        });
        imageViewOfDeck.getCardButtonC4().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Play(game, 17, messageTv, imageViewOfDeck.getCardButtonC4());

            }
        });
        imageViewOfDeck.getCardButtonC5().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Play(game, 18, messageTv, imageViewOfDeck.getCardButtonC5());

            }
        });
        imageViewOfDeck.getCardButtonC6().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Play(game, 19, messageTv, imageViewOfDeck.getCardButtonC6());

            }
        });
        imageViewOfDeck.getCardButtonC7().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Play(game, 20, messageTv, imageViewOfDeck.getCardButtonC7());

            }
        });
        imageViewOfDeck.getCardButtonD1().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Play(game, 21, messageTv, imageViewOfDeck.getCardButtonD1());

            }
        });
        imageViewOfDeck.getCardButtonD2().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Play(game, 22, messageTv, imageViewOfDeck.getCardButtonD2());

            }
        });
        imageViewOfDeck.getCardButtonD3().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Play(game, 23, messageTv, imageViewOfDeck.getCardButtonD3());

            }
        });
        imageViewOfDeck.getCardButtonD4().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Play(game, 24, messageTv, imageViewOfDeck.getCardButtonD4());
            }
        });
        imageViewOfDeck.getCardButtonD5().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Play(game, 25, messageTv, imageViewOfDeck.getCardButtonD5());
            }
        });
    }

    private void updateOngoingGameTable(String playedCards) {

        String[] cards = playedCards.split(":");

        for(int i = 1;i < cards.length;++i){
            updateGameTable(cards[i]);
        }
    }

    private void setUpFingers(Game game) {

        fingerp = new ArrayList<>();

        for (int i = 1; i < game.getGameNoOfPlayers(); ++i) {
            fingerp.add(fingerList.get(i - 1));
        }
        fingerp.add(0, fingerList.get(game.getGameNoOfPlayers() - 1));
    }

    private int getRandomPosition(int maximum, int minimum) {
        return ((int) (Math.random() * (maximum - minimum))) + minimum;
    }

    public void Play(Game game, int cardNumber, TextView messageTv, ImageView button) {

        fingerList.get(0).clearAnimation();

        if (myTurn) {

            String selectedCard = game.getPlayer(myPosition).getCardAtDeckPosition(cardNumber);
            cardsPlayed+=":"+selectedCard;

            if (game.isCardPlayable(selectedCard) && !gameover) {

                game.getPlayer(myPosition).setPlayerDeckAtPosition(cardNumber, "o o");
                game.getPlayer(myPosition).addNumberOfCardsPlayed();
                game.getPlayer(myPosition).setPlayerLastCardPlayed(selectedCard);
                game.getPlayer(myPosition).updateNoCards();

                game.playCard(selectedCard);
                updateGameTable(selectedCard);

                if (game.isGameOver(myPosition)) {
                    saveGameScores(String.valueOf(game.getGameNoOfPlayers()), "1", "0", String.valueOf(game.getPlayer(0).getScore()), String.valueOf(game.getPlayer(0).getNumberOfPasses()));
                    saveCurrentGameData(game, "done");
                    gameover = true;
                    game.setGameWinner("Me");
                    displayGameScore(game, "You Win");
                }

                button.setVisibility(View.INVISIBLE);

                myTurn = false;

                if (!gameover) {
                    saveCurrentGameData(game, "");
                    cpuPlay(game, "");
                }
            }

        }
    }

    private void cpuPlay(Game game, String type) {

        // if(!type.equals("begin")) {
        doSomething(game, myPosition + 1, game.getGameNoOfPlayers());
        //  }

        //doSomething(game,0,myPosition);

        disablePassButton();

    }

    private void doSomething(Game game, int i, int n) {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (i < n) {
                    cpuPlayCard(game, i);
                    doSomething(game, i + 1, n);
                }
            }
        }, 1000);
    }

    private void cpuPlayCard(Game game, int m) {

        fingerp.get(m).setVisibility(View.INVISIBLE);
        fingerList.get(m).setVisibility(View.VISIBLE);

        String cpuCard = game.cpuAutoPlayCardNum(m);
        cardsPlayed+=":"+cpuCard;

        setUpPlayers(game);

        if (!cpuCard.equals("")) {
            updateGameTable(cpuCard);
        }

        if (m == game.getGameNoOfPlayers() - 1) {

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {

                    fingerList.get(0).setVisibility(View.VISIBLE);
                    fingerp.get(0).setVisibility(View.INVISIBLE);

                    myTurn = true;

                    if (game.playerHasNoPlayableCards(myPosition) && game.getGameWinner().equals("")) {
                        enablePassButton();
                    }
                    if (!game.getGameWinner().equals("")) {
                        saveGameScores(String.valueOf(game.getGameNoOfPlayers()), "0", "1", String.valueOf(game.getPlayer(0).getScore()), String.valueOf(game.getPlayer(0).getNumberOfPasses()));
                        saveCurrentGameData(game, "done");
                        displayGameScore(game, "You Lose");
                    }
                }
            }, 1000);
        }
    }

    public void enablePassButton() {

        passButton.setImageBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.passcard));
        passButton.setEnabled(true);
    }

    public void disablePassButton() {

        passButton.setImageBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.passcardbw));
        passButton.setEnabled(false);

    }

    public void updateGameTable(String card) {

        String[] cardS = card.split(" ");
        String cc = cardS[0];

        int i = getCardPosition(cc);

        if ("akjq10987".contains(cc)) {
            if (card.contains("s")) {

                tableImagesS.get(i).setVisibility(View.VISIBLE);
                tableImagesS.get(i).startAnimation(slidedown);
            } else if (card.contains("h")) {

                tableImagesH.get(i).setVisibility(View.VISIBLE);
                tableImagesH.get(i).startAnimation(slidedown);

            } else if (card.contains("f")) {

                tableImagesF.get(i).setVisibility(View.VISIBLE);
                tableImagesF.get(i).startAnimation(slidedown);

            } else if (card.contains("d")) {

                tableImagesD.get(i).setVisibility(View.VISIBLE);
                tableImagesD.get(i).startAnimation(slidedown);
            }
        }
        /////////////////////////////
        else if ("65432".contains(cc)) {
            if (card.contains("s")) {

                tableImagesS.get(i).setVisibility(View.VISIBLE);
                tableImagesS.get(i).startAnimation(slideup);

            } else if (card.contains("h")) {

                tableImagesH.get(i).setVisibility(View.VISIBLE);
                tableImagesH.get(i).startAnimation(slideup);

            } else if (card.contains("f")) {

                tableImagesF.get(i).setVisibility(View.VISIBLE);
                tableImagesF.get(i).startAnimation(slideup);

            } else if (card.contains("d")) {

                tableImagesD.get(i).setVisibility(View.VISIBLE);
                tableImagesD.get(i).startAnimation(slideup);
            }
        }
    }

    private int getCardPosition(String card) {
        switch (card) {
            case "a":
                return 0;
            case "k":
                return 1;
            case "q":
                return 2;
            case "j":
                return 3;
            case "10":
                return 4;
            case "9":
                return 5;
            case "8":
                return 6;
            case "7":
                return 7;
            case "6":
                return 8;
            case "5":
                return 9;
            case "4":
                return 10;
            case "3":
                return 11;
            case "2":
                return 12;
        }
        return 0;
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
            cardImage = BitmapFactory.decodeResource(getResources(), R.drawable.d10);
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
            cardImage = BitmapFactory.decodeResource(getResources(), R.drawable.emptycardback);
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
        } else if (cardNo.equals("v") && cardSign.equals("ks")) {
            cardImage = BitmapFactory.decodeResource(getResources(), R.drawable.kings);
        } else if (cardNo.equals("v") && cardSign.equals("kh")) {
            cardImage = BitmapFactory.decodeResource(getResources(), R.drawable.kingd);
        } else if (cardNo.equals("v") && cardSign.equals("qf")) {
            cardImage = BitmapFactory.decodeResource(getResources(), R.drawable.queens);
        } else if (cardNo.equals("v") && cardSign.equals("qd")) {
            cardImage = BitmapFactory.decodeResource(getResources(), R.drawable.queend);
        } else if (cardNo.equals("v") && cardSign.equals("js")) {
            cardImage = BitmapFactory.decodeResource(getResources(), R.drawable.jackf);
        } else if (cardNo.equals("v") && cardSign.equals("jh")) {
            cardImage = BitmapFactory.decodeResource(getResources(), R.drawable.jackd);
        }

        return cardImage;
    }

    private void setUpPlayerCards(Game game, int playerPositions) {

        tableImagesD.add(imageViewOfCardsPlayed.getImageOfADiamondCardPlayed());
        tableImagesD.add(imageViewOfCardsPlayed.getImageOfKDiamondCardPlayed());
        tableImagesD.add(imageViewOfCardsPlayed.getImageOfQDiamondCardPlayed());
        tableImagesD.add(imageViewOfCardsPlayed.getImageOfJDiamondCardPlayed());
        tableImagesD.add(imageViewOfCardsPlayed.getImageOf10DiamondCardPlayed());
        tableImagesD.add(imageViewOfCardsPlayed.getImageOf9DiamondCardPlayed());
        tableImagesD.add(imageViewOfCardsPlayed.getImageOf8DiamondCardPlayed());
        tableImagesD.add(imageViewOfCardsPlayed.getImageOf7DiamondCardPlayed());
        tableImagesD.add(imageViewOfCardsPlayed.getImageOf6DiamondCardPlayed());
        tableImagesD.add(imageViewOfCardsPlayed.getImageOf5DiamondCardPlayed());
        tableImagesD.add(imageViewOfCardsPlayed.getImageOf4DiamondCardPlayed());
        tableImagesD.add(imageViewOfCardsPlayed.getImageOf3DiamondCardPlayed());
        tableImagesD.add(imageViewOfCardsPlayed.getImageOf2DiamondCardPlayed());

        tableImagesF.add(imageViewOfCardsPlayed.getImageOfAFlowerCardPlayed());
        tableImagesF.add(imageViewOfCardsPlayed.getImageOfKFlowerCardPlayed());
        tableImagesF.add(imageViewOfCardsPlayed.getImageOfQFlowerCardPlayed());
        tableImagesF.add(imageViewOfCardsPlayed.getImageOfJFlowerCardPlayed());
        tableImagesF.add(imageViewOfCardsPlayed.getImageOf10FlowerCardPlayed());
        tableImagesF.add(imageViewOfCardsPlayed.getImageOf9FlowerCardPlayed());
        tableImagesF.add(imageViewOfCardsPlayed.getImageOf8FlowerCardPlayed());
        tableImagesF.add(imageViewOfCardsPlayed.getImageOf7FlowerCardPlayed());
        tableImagesF.add(imageViewOfCardsPlayed.getImageOf6FlowerCardPlayed());
        tableImagesF.add(imageViewOfCardsPlayed.getImageOf5FlowerCardPlayed());
        tableImagesF.add(imageViewOfCardsPlayed.getImageOf4FlowerCardPlayed());
        tableImagesF.add(imageViewOfCardsPlayed.getImageOf3FlowerCardPlayed());
        tableImagesF.add(imageViewOfCardsPlayed.getImageOf2FlowerCardPlayed());

        tableImagesH.add(imageViewOfCardsPlayed.getImageOfAHeartCardPlayed());
        tableImagesH.add(imageViewOfCardsPlayed.getImageOfKHeartCardPlayed());
        tableImagesH.add(imageViewOfCardsPlayed.getImageOfQHeartCardPlayed());
        tableImagesH.add(imageViewOfCardsPlayed.getImageOfJHeartCardPlayed());
        tableImagesH.add(imageViewOfCardsPlayed.getImageOf10HeartCardPlayed());
        tableImagesH.add(imageViewOfCardsPlayed.getImageOf9HeartCardPlayed());
        tableImagesH.add(imageViewOfCardsPlayed.getImageOf8HeartCardPlayed());
        tableImagesH.add(imageViewOfCardsPlayed.getImageOf7HeartCardPlayed());
        tableImagesH.add(imageViewOfCardsPlayed.getImageOf6HeartCardPlayed());
        tableImagesH.add(imageViewOfCardsPlayed.getImageOf5HeartCardPlayed());
        tableImagesH.add(imageViewOfCardsPlayed.getImageOf4HeartCardPlayed());
        tableImagesH.add(imageViewOfCardsPlayed.getImageOf3HeartCardPlayed());
        tableImagesH.add(imageViewOfCardsPlayed.getImageOf2HeartCardPlayed());

        tableImagesS.add(imageViewOfCardsPlayed.getImageOfASpadeCardPlayed());
        tableImagesS.add(imageViewOfCardsPlayed.getImageOfKSpadeCardPlayed());
        tableImagesS.add(imageViewOfCardsPlayed.getImageOfQSpadeCardPlayed());
        tableImagesS.add(imageViewOfCardsPlayed.getImageOfJSpadeCardPlayed());
        tableImagesS.add(imageViewOfCardsPlayed.getImageOf10SpadeCardPlayed());
        tableImagesS.add(imageViewOfCardsPlayed.getImageOf9SpadeCardPlayed());
        tableImagesS.add(imageViewOfCardsPlayed.getImageOf8SpadeCardPlayed());
        tableImagesS.add(imageViewOfCardsPlayed.getImageOf7SpadeCardPlayed());
        tableImagesS.add(imageViewOfCardsPlayed.getImageOf6SpadeCardPlayed());
        tableImagesS.add(imageViewOfCardsPlayed.getImageOf5SpadeCardPlayed());
        tableImagesS.add(imageViewOfCardsPlayed.getImageOf4SpadeCardPlayed());
        tableImagesS.add(imageViewOfCardsPlayed.getImageOf3SpadeCardPlayed());
        tableImagesS.add(imageViewOfCardsPlayed.getImageOf2SpadeCardPlayed());

        for (int i = 0; i < tableImagesS.size(); ++i) {
            tableImagesS.get(i).setVisibility(View.INVISIBLE);
            tableImagesF.get(i).setVisibility(View.INVISIBLE);
            tableImagesD.get(i).setVisibility(View.INVISIBLE);
            tableImagesH.get(i).setVisibility(View.INVISIBLE);
        }

        ArrayList<ImageView> buttons = new ArrayList<>();
        buttons.add(imageViewOfDeck.getCardButtonA1());
        buttons.add(imageViewOfDeck.getCardButtonA2());
        buttons.add(imageViewOfDeck.getCardButtonA3());
        buttons.add(imageViewOfDeck.getCardButtonA4());
        buttons.add(imageViewOfDeck.getCardButtonA5());
        buttons.add(imageViewOfDeck.getCardButtonA6());
        buttons.add(imageViewOfDeck.getCardButtonA7());
        buttons.add(imageViewOfDeck.getCardButtonB1());
        buttons.add(imageViewOfDeck.getCardButtonB2());
        buttons.add(imageViewOfDeck.getCardButtonB3());
        buttons.add(imageViewOfDeck.getCardButtonB4());
        buttons.add(imageViewOfDeck.getCardButtonB5());
        buttons.add(imageViewOfDeck.getCardButtonB6());
        buttons.add(imageViewOfDeck.getCardButtonB7());
        buttons.add(imageViewOfDeck.getCardButtonC1());
        buttons.add(imageViewOfDeck.getCardButtonC2());
        buttons.add(imageViewOfDeck.getCardButtonC3());
        buttons.add(imageViewOfDeck.getCardButtonC4());
        buttons.add(imageViewOfDeck.getCardButtonC5());
        buttons.add(imageViewOfDeck.getCardButtonC6());
        buttons.add(imageViewOfDeck.getCardButtonC7());
        buttons.add(imageViewOfDeck.getCardButtonD1());
        buttons.add(imageViewOfDeck.getCardButtonD2());
        buttons.add(imageViewOfDeck.getCardButtonD3());
        buttons.add(imageViewOfDeck.getCardButtonD4());
        buttons.add(imageViewOfDeck.getCardButtonD5());

        PlayerGame playerGame = game.getPlayer(playerPositions);
        playerGame.setPlayerId("You");

        for (int i = 0; i < 26; ++i) {
            buttons.get(i).setVisibility(View.INVISIBLE);
        }

        for (int i = 0; i < playerGame.getplayerDeckSize(); ++i) {
            buttons.get(i).setVisibility(View.VISIBLE);
            buttons.get(i).setImageBitmap(convertStringCardToImage(playerGame.getCardAtDeckPosition(i)));
        }

        for (int i = playerGame.getplayerDeckSize(); i < 26; ++i) {
            //buttons.get(i).setVisibility(View.GONE);
        }
    }

    public void setupUserInterface() {

        tableImagesD = new ArrayList<>();
        tableImagesS = new ArrayList<>();
        tableImagesF = new ArrayList<>();
        tableImagesH = new ArrayList<>();

        imageViewOfCardsPlayed.setImageOfASpadeCardPlayed((ImageView) findViewById(R.id.carda1));
        imageViewOfCardsPlayed.setImageOfKSpadeCardPlayed((ImageView) findViewById(R.id.carda2));
        imageViewOfCardsPlayed.setImageOfQSpadeCardPlayed((ImageView) findViewById(R.id.carda3));
        imageViewOfCardsPlayed.setImageOfJSpadeCardPlayed((ImageView) findViewById(R.id.carda4));
        imageViewOfCardsPlayed.setImageOf10SpadeCardPlayed((ImageView) findViewById(R.id.carda5));
        imageViewOfCardsPlayed.setImageOf9SpadeCardPlayed((ImageView) findViewById(R.id.carda6));
        imageViewOfCardsPlayed.setImageOf8SpadeCardPlayed((ImageView) findViewById(R.id.carda7));
        imageViewOfCardsPlayed.setImageOf7SpadeCardPlayed((ImageView) findViewById(R.id.carda8));
        imageViewOfCardsPlayed.setImageOf6SpadeCardPlayed((ImageView) findViewById(R.id.carda9));
        imageViewOfCardsPlayed.setImageOf5SpadeCardPlayed((ImageView) findViewById(R.id.carda10));
        imageViewOfCardsPlayed.setImageOf4SpadeCardPlayed((ImageView) findViewById(R.id.carda11));
        imageViewOfCardsPlayed.setImageOf3SpadeCardPlayed((ImageView) findViewById(R.id.carda12));
        imageViewOfCardsPlayed.setImageOf2SpadeCardPlayed((ImageView) findViewById(R.id.carda13));

        imageViewOfCardsPlayed.setImageOfAHeartCardPlayed((ImageView) findViewById(R.id.cardb1));
        imageViewOfCardsPlayed.setImageOfKHeartCardPlayed((ImageView) findViewById(R.id.cardb2));
        imageViewOfCardsPlayed.setImageOfQHeartCardPlayed((ImageView) findViewById(R.id.cardb3));
        imageViewOfCardsPlayed.setImageOfJHeartCardPlayed((ImageView) findViewById(R.id.cardb4));
        imageViewOfCardsPlayed.setImageOf10HeartCardPlayed((ImageView) findViewById(R.id.cardb5));
        imageViewOfCardsPlayed.setImageOf9HeartCardPlayed((ImageView) findViewById(R.id.cardb6));
        imageViewOfCardsPlayed.setImageOf8HeartCardPlayed((ImageView) findViewById(R.id.cardb7));
        imageViewOfCardsPlayed.setImageOf7HeartCardPlayed((ImageView) findViewById(R.id.cardb8));
        imageViewOfCardsPlayed.setImageOf6HeartCardPlayed((ImageView) findViewById(R.id.cardb9));
        imageViewOfCardsPlayed.setImageOf5HeartCardPlayed((ImageView) findViewById(R.id.cardb10));
        imageViewOfCardsPlayed.setImageOf4HeartCardPlayed((ImageView) findViewById(R.id.cardb11));
        imageViewOfCardsPlayed.setImageOf3HeartCardPlayed((ImageView) findViewById(R.id.cardb12));
        imageViewOfCardsPlayed.setImageOf2HeartCardPlayed((ImageView) findViewById(R.id.cardb13));

        imageViewOfCardsPlayed.setImageOfAFlowerCardPlayed((ImageView) findViewById(R.id.cardc1));
        imageViewOfCardsPlayed.setImageOfKFlowerCardPlayed((ImageView) findViewById(R.id.cardc2));
        imageViewOfCardsPlayed.setImageOfQFlowerCardPlayed((ImageView) findViewById(R.id.cardc3));
        imageViewOfCardsPlayed.setImageOfJFlowerCardPlayed((ImageView) findViewById(R.id.cardc4));
        imageViewOfCardsPlayed.setImageOf10FlowerCardPlayed((ImageView) findViewById(R.id.cardc5));
        imageViewOfCardsPlayed.setImageOf9FlowerCardPlayed((ImageView) findViewById(R.id.cardc6));
        imageViewOfCardsPlayed.setImageOf8FlowerCardPlayed((ImageView) findViewById(R.id.cardc7));
        imageViewOfCardsPlayed.setImageOf7FlowerCardPlayed((ImageView) findViewById(R.id.cardc8));
        imageViewOfCardsPlayed.setImageOf6FlowerCardPlayed((ImageView) findViewById(R.id.cardc9));
        imageViewOfCardsPlayed.setImageOf5FlowerCardPlayed((ImageView) findViewById(R.id.cardc10));
        imageViewOfCardsPlayed.setImageOf4FlowerCardPlayed((ImageView) findViewById(R.id.cardc11));
        imageViewOfCardsPlayed.setImageOf3FlowerCardPlayed((ImageView) findViewById(R.id.cardc12));
        imageViewOfCardsPlayed.setImageOf2FlowerCardPlayed((ImageView) findViewById(R.id.cardc13));

        imageViewOfCardsPlayed.setImageOfADiamondCardPlayed((ImageView) findViewById(R.id.cardd1));
        imageViewOfCardsPlayed.setImageOfKDiamondCardPlayed((ImageView) findViewById(R.id.cardd2));
        imageViewOfCardsPlayed.setImageOfQDiamondCardPlayed((ImageView) findViewById(R.id.cardd3));
        imageViewOfCardsPlayed.setImageOfJDiamondCardPlayed((ImageView) findViewById(R.id.cardd4));
        imageViewOfCardsPlayed.setImageOf10DiamondCardPlayed((ImageView) findViewById(R.id.cardd5));
        imageViewOfCardsPlayed.setImageOf9DiamondCardPlayed((ImageView) findViewById(R.id.cardd6));
        imageViewOfCardsPlayed.setImageOf8DiamondCardPlayed((ImageView) findViewById(R.id.cardd7));
        imageViewOfCardsPlayed.setImageOf7DiamondCardPlayed((ImageView) findViewById(R.id.cardd8));
        imageViewOfCardsPlayed.setImageOf6DiamondCardPlayed((ImageView) findViewById(R.id.cardd9));
        imageViewOfCardsPlayed.setImageOf5DiamondCardPlayed((ImageView) findViewById(R.id.cardd10));
        imageViewOfCardsPlayed.setImageOf4DiamondCardPlayed((ImageView) findViewById(R.id.cardd11));
        imageViewOfCardsPlayed.setImageOf3DiamondCardPlayed((ImageView) findViewById(R.id.cardd12));
        imageViewOfCardsPlayed.setImageOf2DiamondCardPlayed((ImageView) findViewById(R.id.cardd13));

        imageViewOfDeck.setCardButtonA1((ImageView) findViewById(R.id.card1));
        imageViewOfDeck.setCardButtonA2((ImageView) findViewById(R.id.card2));
        imageViewOfDeck.setCardButtonA3((ImageView) findViewById(R.id.card3));
        imageViewOfDeck.setCardButtonA4((ImageView) findViewById(R.id.card4));
        imageViewOfDeck.setCardButtonA5((ImageView) findViewById(R.id.card5));
        imageViewOfDeck.setCardButtonA6((ImageView) findViewById(R.id.card6));
        imageViewOfDeck.setCardButtonA7((ImageView) findViewById(R.id.card7));

        imageViewOfDeck.setCardButtonB1((ImageView) findViewById(R.id.card8));
        imageViewOfDeck.setCardButtonB2((ImageView) findViewById(R.id.card9));
        imageViewOfDeck.setCardButtonB3((ImageView) findViewById(R.id.card10));
        imageViewOfDeck.setCardButtonB4((ImageView) findViewById(R.id.card11));
        imageViewOfDeck.setCardButtonB5((ImageView) findViewById(R.id.card12));
        imageViewOfDeck.setCardButtonB6((ImageView) findViewById(R.id.card13));
        imageViewOfDeck.setCardButtonB7((ImageView) findViewById(R.id.card14));

        imageViewOfDeck.setCardButtonC1((ImageView) findViewById(R.id.card15));
        imageViewOfDeck.setCardButtonC2((ImageView) findViewById(R.id.card16));
        imageViewOfDeck.setCardButtonC3((ImageView) findViewById(R.id.card17));
        imageViewOfDeck.setCardButtonC4((ImageView) findViewById(R.id.card18));
        imageViewOfDeck.setCardButtonC5((ImageView) findViewById(R.id.card19));
        imageViewOfDeck.setCardButtonC6((ImageView) findViewById(R.id.card20));
        imageViewOfDeck.setCardButtonC7((ImageView) findViewById(R.id.card21));

        imageViewOfDeck.setCardButtonD1((ImageView) findViewById(R.id.card22));
        imageViewOfDeck.setCardButtonD2((ImageView) findViewById(R.id.card23));
        imageViewOfDeck.setCardButtonD3((ImageView) findViewById(R.id.card24));
        imageViewOfDeck.setCardButtonD4((ImageView) findViewById(R.id.card25));
        imageViewOfDeck.setCardButtonD5((ImageView) findViewById(R.id.card26));
        passButton = (ImageView) findViewById(R.id.passcard);

        leaveGameBtn = (ImageView) findViewById(R.id.cardoptions);
        gameInfoBtn = (ImageView) findViewById(R.id.info);

        messageTv = (TextView) findViewById(R.id.message);

        currentApiVersion = android.os.Build.VERSION.SDK_INT;
        final int flags = View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION |
                View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION |
                View.SYSTEM_UI_FLAG_FULLSCREEN | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;
        if (currentApiVersion >= Build.VERSION_CODES.KITKAT) {
            getWindow().getDecorView().setSystemUiVisibility(flags);
            final View decorView = getWindow().getDecorView();
            decorView.setOnSystemUiVisibilityChangeListener(new View.OnSystemUiVisibilityChangeListener() {
                @Override
                public void onSystemUiVisibilityChange(int visibility) {
                    if ((visibility & View.SYSTEM_UI_FLAG_FULLSCREEN) == 0) {
                        decorView.setSystemUiVisibility(flags);
                    }
                }
            });
        }

        slidedown = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide);
        slideup = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide2);

        //sdown = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slidedown);
        //sup = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slideup);
        //sright = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slideright);
        //sleft = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slideleft);
    }

    private void displayGameScore(Game game, String outcome) {

        @SuppressLint("InflateParams") View view = getLayoutInflater().inflate(R.layout.gameoverlayout, null);
        ListView playerScoreLv = (ListView) view.findViewById(R.id.listview);
        gameOverAdapterDialog clad = new gameOverAdapterDialog(MainActivity.this, game);
        playerScoreLv.setAdapter(clad);

        createAndDisplayGameOverDialog(view);

        TextView outcomeTv = (TextView) playerScoresDialog.findViewById(R.id.gameoutcometv);
        ImageView newGameIv = (ImageView) playerScoresDialog.findViewById(R.id.newiv);
        ImageView goBackHomeIv = (ImageView) playerScoresDialog.findViewById(R.id.homeiv);

        outcomeTv.setText(outcome);

        goBackHomeIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, StartUpMenu.class);
                startActivity(intent);
            }
        });

        newGameIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, MainActivity.class);
                intent.putExtra("numplayers", game.getGameNoOfPlayers());
                startActivity(intent);
            }
        });
    }

    public void createLeaveDialog() {

        leaveDialog = new Dialog(MainActivity.this);
        leaveDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        leaveDialog.setCancelable(false);
        leaveDialog.setCanceledOnTouchOutside(false);
        leaveDialog.setContentView(R.layout.leavegamelayout);
    }

    private void createAndDisplayGameOverDialog(View view) {
        playerScoresDialog = new Dialog(MainActivity.this);
        playerScoresDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        playerScoresDialog.setCancelable(false);
        playerScoresDialog.setCanceledOnTouchOutside(false);
        playerScoresDialog.setContentView(view);
        playerScoresDialog.show();
    }

    public static class gameOverAdapterDialog extends BaseAdapter {

        private final Game game;

        private final LayoutInflater layoutInflater;

        public gameOverAdapterDialog(Context context, Game game) {
            this.game = game;
            layoutInflater = LayoutInflater.from(context);
        }

        @Override
        public int getCount() {
            return game.getGameNoOfPlayers();
        }

        @Override
        public Object getItem(int position) {
            return position;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @SuppressLint("InflateParams")
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder;

            if (convertView == null) {

                convertView = layoutInflater.inflate(R.layout.gameoverlistitem, null);
                holder = new ViewHolder();
                holder.playerRankTv = (TextView) convertView.findViewById(R.id.ranktv);
                holder.playernameTv = (TextView) convertView.findViewById(R.id.playernametv);
                holder.playerScoreTv = (TextView) convertView.findViewById(R.id.scoretv);

                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }

            String pos = String.valueOf(position + 1);
            String name = String.valueOf(game.getPlayer(position).getPlayerId());
            String score = String.valueOf(game.getPlayer(position).getScore());

            holder.playerRankTv.setText(pos);
            holder.playernameTv.setText(name);
            holder.playerScoreTv.setText(score);

            return convertView;
        }

        static class ViewHolder {
            TextView playerRankTv;
            TextView playernameTv;
            TextView playerScoreTv;
        }
    }

    public void setUpPlayerLayout() {

        imageOfFingers.setFingerPointPlayer1((ImageView) findViewById(R.id.pointleftiv));
        imageOfFingers.setFingerPointPlayer2((ImageView) findViewById(R.id.pointleftiv2));
        imageOfFingers.setFingerPointPlayer3((ImageView) findViewById(R.id.pointleftiv3));
        imageOfFingers.setFingerPointPlayer4((ImageView) findViewById(R.id.pointleftiv4));

        imageOfFingers.getFingerPointPlayer1().setVisibility(View.INVISIBLE);
        imageOfFingers.getFingerPointPlayer2().setVisibility(View.INVISIBLE);
        imageOfFingers.getFingerPointPlayer3().setVisibility(View.INVISIBLE);
        imageOfFingers.getFingerPointPlayer4().setVisibility(View.INVISIBLE);

        fingerList = new ArrayList<>();

        fingerList.add(imageOfFingers.getFingerPointPlayer1());
        fingerList.add(imageOfFingers.getFingerPointPlayer2());
        fingerList.add(imageOfFingers.getFingerPointPlayer3());
        fingerList.add(imageOfFingers.getFingerPointPlayer4());

        RelativeLayout currentGameInfoLayoutPlayer1 = (RelativeLayout) findViewById(R.id.playerlayout1);
        RelativeLayout currentGameInfoLayoutPlayer2 = (RelativeLayout) findViewById(R.id.playerlayout2);
        RelativeLayout currentGameInfoLayoutPlayer3 = (RelativeLayout) findViewById(R.id.playerlayout3);
        RelativeLayout currentGameInfoLayoutPlayer4 = (RelativeLayout) findViewById(R.id.playerlayout4);

//        ArrayList<RelativeLayout> currentGameInfoPlayerLayouts = new ArrayList<>();
//
//        currentGameInfoPlayerLayouts.add(currentGameInfoLayoutPlayer1);
//        currentGameInfoPlayerLayouts.add(currentGameInfoLayoutPlayer2);
//        currentGameInfoPlayerLayouts.add(currentGameInfoLayoutPlayer3);
//        currentGameInfoPlayerLayouts.add(currentGameInfoLayoutPlayer4);

        TextView passTextPlayer1 = (TextView) findViewById(R.id.playerpass1);
        TextView passTextPlayer2 = (TextView) findViewById(R.id.playerpass2);
        TextView passTextPlayer3 = (TextView) findViewById(R.id.playerpass3);
        TextView passTextPlayer4 = (TextView) findViewById(R.id.playerpass4);

        passTextsList = new ArrayList<>();

        passTextsList.add(passTextPlayer1);
        passTextsList.add(passTextPlayer2);
        passTextsList.add(passTextPlayer3);
        passTextsList.add(passTextPlayer4);

        TextView noCardsPlayer1 = (TextView) findViewById(R.id.playercards1);
        TextView noCardsPlayer2 = (TextView) findViewById(R.id.playercards2);
        TextView noCardsPlayer3 = (TextView) findViewById(R.id.playercards3);
        TextView noCardsPlayer4 = (TextView) findViewById(R.id.playercards4);

        noCardsList = new ArrayList<>();

        noCardsList.add(noCardsPlayer1);
        noCardsList.add(noCardsPlayer2);
        noCardsList.add(noCardsPlayer3);
        noCardsList.add(noCardsPlayer4);

        ImageView avatarPlayer1 = (ImageView) findViewById(R.id.playeravatar1);
        ImageView avatarPlayer2 = (ImageView) findViewById(R.id.playeravatar2);
        ImageView avatarPlayer3 = (ImageView) findViewById(R.id.playeravatar3);
        ImageView avatarPlayer4 = (ImageView) findViewById(R.id.playeravatar4);

        avatarsList = new ArrayList<>();

        avatarsList.add(avatarPlayer1);
        avatarsList.add(avatarPlayer2);
        avatarsList.add(avatarPlayer3);
        avatarsList.add(avatarPlayer4);

        TextView nameTextPlayer1 = (TextView) findViewById(R.id.playername1);
        TextView nameTextPlayer2 = (TextView) findViewById(R.id.playername2);
        TextView nameTextPlayer3 = (TextView) findViewById(R.id.playername3);
        TextView nameTextPlayer4 = (TextView) findViewById(R.id.playername4);

        namesTextList = new ArrayList<>();

        namesTextList.add(nameTextPlayer1);
        namesTextList.add(nameTextPlayer2);
        namesTextList.add(nameTextPlayer3);
        namesTextList.add(nameTextPlayer4);
    }

    public void setUpPlayers(Game game) {

        for (int i = 0; i < game.getGameNoOfPlayers(); ++i) {

            PlayerGame player = game.getPlayer(i);

            namesTextList.get(i).setText(player.getPlayerId());

            passTextsList.get(i).setText(String.valueOf(player.getNumberOfPasses()));

            noCardsList.get(i).setText(String.valueOf(player.getNumberOfCardsLeft()));

            avatarsList.get(i).setImageBitmap(convertStringCardToImage(player.getPlayerAvatar()));
        }
    }

    @SuppressLint("NewApi")
    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (currentApiVersion >= Build.VERSION_CODES.KITKAT && hasFocus) {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION |
                    View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION |
                    View.SYSTEM_UI_FLAG_FULLSCREEN | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
        }
    }

    public void saveGameScores(String gameType, String gamesWon, String gamesLost, String gameScore, String numpasses) {
        databaseHelper.saveGameScores(gameType, gamesWon, gamesLost, gameScore, numpasses);
    }

    private void toastMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }

    private void saveCurrentGameData(Game game, String gameStatus) {

        if (gameStatus.equals("done")) {
            return;
        }

        StringBuilder playerData = new StringBuilder(game.getGameNoOfPlayers() + "");

        for (PlayerGame playerGame : game.gamePlayersList) {
            String data = playerGame.getDeckString() + ":" + playerGame.getNumberOfCardsPlayed() + ":" + playerGame.getNumberOfPasses() + ":" + playerGame.getPlayerAvatar();
            playerData.append(">").append(data);
        }

        databaseHelper.saveCurrentGameData(playerData.toString(),cardsPlayed);
    }

    private void getCardsFromOngoingGame(DatabaseHelper databaseHelper) {

        Cursor cursor = databaseHelper.OngoingGameData();

        while (cursor.moveToLast()) {
            PlayerCards = cursor.getString(1);
            PlayedCards = cursor.getString(2);
            break;
        }

        cursor.close();

    }


    //16 1.2
    //32 2.4
    //48 3.6
    //
    //
}