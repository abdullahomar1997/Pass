package com.example.passa;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class OfflineGameMenu extends AppCompatActivity {

    DatabaseHelper databaseHelper;

    private int currentApiVersion;

    Dialog numPlayersDialog;
    Dialog gameOptionsDialog;
    Dialog instructionsDialog;
    Dialog scoreDialog;
    RelativeLayout help;
    RelativeLayout statistics;

    TextView gp2;
    TextView gp3;
    TextView gp4;
    TextView gw2;
    TextView gw3;
    TextView gw4;
    TextView gl2;
    TextView gl3;
    TextView gl4;
    TextView wp2;
    TextView wp3;
    TextView wp4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_offline_game_menu);

        databaseHelper = new DatabaseHelper(this);

       // addData2("2","1","0","20","7");

        //addData("new database");
        //addData("nova database");

//        Cursor data = databaseHelper.getData();
//
//        String ss = "";
//
//        while(data.moveToNext()){
//
//            ss+=data.getString(0)+ " "+data.getString(1) + " "+data.getString(2)+ " "+data.getString(3)+ " "+data.getString(4)+ " "+data.getString(5);
//
//            toastMessage(ss);
//        }

        if(!gameAvailable(databaseHelper)) {

            Intent intent = new Intent(OfflineGameMenu.this, MainActivity.class);
            intent.putExtra("numplayers", 2);
            intent.putExtra("status", "continue");
            startActivity(intent);
        }

        help = (RelativeLayout)findViewById(R.id.help);
        statistics = (RelativeLayout)findViewById(R.id.statistics);

        help.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                createInstructionsDialog();

                instructionsDialog.show();

            }
        });

        statistics.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                createScoreDialog();

                gp2 = (TextView) scoreDialog.findViewById(R.id.gamesplayed12);
                gp3 = (TextView) scoreDialog.findViewById(R.id.gamesplayed13);
                gp4 = (TextView) scoreDialog.findViewById(R.id.gamesplayed14);
                gw2 = (TextView) scoreDialog.findViewById(R.id.gameswon12);
                gw3 = (TextView) scoreDialog.findViewById(R.id.gameswon13);
                gw4 = (TextView) scoreDialog.findViewById(R.id.gameswon14);
                gl2 = (TextView) scoreDialog.findViewById(R.id.gameslost12);
                gl3 = (TextView) scoreDialog.findViewById(R.id.gameslost13);
                gl4 = (TextView) scoreDialog.findViewById(R.id.gameslost14);
                wp2 = (TextView) scoreDialog.findViewById(R.id.wp12);
                wp3 = (TextView) scoreDialog.findViewById(R.id.wp13);
                wp4 = (TextView) scoreDialog.findViewById(R.id.wp14);

                getFullTriumphScores();

                scoreDialog.show();

            }
        });

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

        TextView singleGame = (TextView)findViewById(R.id.singleGame);

        singleGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                createGameOptionsDialog();

                gameOptionsDialog.show();

                TextView normalGame = (TextView)gameOptionsDialog.findViewById(R.id.normalGame);

                normalGame.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        createNumberOfPlayersDialog();

                        ImageView twoPlayer = (ImageView)numPlayersDialog.findViewById(R.id.players2);
                        ImageView threePlayer = (ImageView)numPlayersDialog.findViewById(R.id.players3);
                        ImageView fourPlayer = (ImageView)numPlayersDialog.findViewById(R.id.players4);

                        numPlayersDialog.show();

                        twoPlayer.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {

                                //numPlayersDialog.dismiss();
                                Intent intent = new Intent(OfflineGameMenu.this, MainActivity.class);
                                intent.putExtra("numplayers",2);
                                startActivity(intent);

                            }
                        });

                        threePlayer.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {

                                //numPlayersDialog.dismiss();
                                Intent intent = new Intent(OfflineGameMenu.this, MainActivity.class);
                                intent.putExtra("numplayers",3);
                                startActivity(intent);

                            }
                        });

                        fourPlayer.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {

                                //numPlayersDialog.dismiss();
                                Intent intent = new Intent(OfflineGameMenu.this, MainActivity.class);
                                intent.putExtra("numplayers",4);
                                startActivity(intent);

                            }
                        });
                    }
                });
            }
        });
    }

    private boolean gameAvailable(DatabaseHelper databaseHelper) {

        Cursor cursor = databaseHelper.OngoingGameData();

        String result = "";

        while(cursor.moveToLast()){
            result = cursor.getString(1);
            break;
        }

        return result.equals("");
    }

    public void getFullTriumphScores(){

        int gamesplayed2 = 0;
        int gamesplayed3 = 0;
        int gamesplayed4 = 0;

        int gameswon2 = 0;
        int gameswon3 = 0;
        int gameswon4 = 0;

        int gameslost2 = 0;
        int gameslost3 = 0;
        int gameslost4 = 0;

        Cursor c = databaseHelper.getData("2");
        while (c.moveToNext()) {

            gamesplayed2 += 1;

            gameswon2 += Integer.parseInt(c.getString(2));
            gameslost2 += Integer.parseInt(c.getString(3));

            double gamesplayeddouble = gamesplayed2;
            double gameswondouble = gameswon2;

            double average = (gameswondouble/gamesplayeddouble)*100;

            average = 0;

            gp2.setText(String.valueOf(gamesplayed2));
            gw2.setText(String.valueOf(gameswon2));
            gl2.setText(String.valueOf(gameslost2));
            wp2.setText(String.valueOf(average));
        }
        c.close();

        c = databaseHelper.getData("3");
        while (c.moveToNext()) {

            gamesplayed3 += 1;

            gameswon3 += Integer.parseInt(c.getString(2));
            gameslost3 += Integer.parseInt(c.getString(3));


            double gamesplayeddouble = gamesplayed3;
            double gameswondouble = gameswon3;

            double average = (gameswondouble/gamesplayeddouble)*100;

            average = 0;

            gp3.setText(String.valueOf(gamesplayed3));
            gw3.setText(String.valueOf(gameswon3));
            gl3.setText(String.valueOf(gameslost3));
            wp3.setText(String.valueOf(average));

        }
        c.close();

        c = databaseHelper.getData("4");
        while (c.moveToNext()) {

            gamesplayed4+= 1;

            gameswon4 += Integer.parseInt(c.getString(2));
            gameslost4 += Integer.parseInt(c.getString(3));

            double gamesplayeddouble = gamesplayed4;
            double gameswondouble = gameswon4;

            double average = (gameswondouble/gamesplayeddouble)*100;

            average = 0;

            gp4.setText(String.valueOf(gamesplayed4));
            gw4.setText(String.valueOf(gameswon4));
            gl4.setText(String.valueOf(gameslost4));
            wp4.setText(String.valueOf(average));

        }
        c.close();
    }

    private void createGameOptionsDialog() {
        gameOptionsDialog = new Dialog(OfflineGameMenu.this);
        gameOptionsDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        gameOptionsDialog.setContentView(R.layout.gameoptions);
    }

    public void createNumberOfPlayersDialog(){
        numPlayersDialog = new Dialog(OfflineGameMenu.this);
        numPlayersDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        numPlayersDialog.setContentView(R.layout.numplayerslayout);
    }

    public void createInstructionsDialog(){
        instructionsDialog = new Dialog(OfflineGameMenu.this);
        instructionsDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        instructionsDialog.setContentView(R.layout.dialog_instructions);
    }

    public void createScoreDialog(){
        scoreDialog = new Dialog(OfflineGameMenu.this);
        scoreDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        scoreDialog.setContentView(R.layout.dialog_score);
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

    private void toastMessage(String message) {
        Toast.makeText(this,message,Toast.LENGTH_LONG).show();
    }
}