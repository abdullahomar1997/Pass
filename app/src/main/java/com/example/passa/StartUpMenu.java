package com.example.passa;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

public class StartUpMenu extends AppCompatActivity {

    private int currentApiVersion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_up_menu);

        TextView newGameBtn = (TextView)findViewById(R.id.playofflineBtn);

        newGameBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setUpNumberOfPlayersDialog();
            }
        });

        hideTopAndBottomNavigationBar();
    }

    private void hideTopAndBottomNavigationBar() {

        currentApiVersion = Build.VERSION.SDK_INT;
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
    }

    public void setUpNumberOfPlayersDialog(){

        Dialog numPlayersDialog = new Dialog(StartUpMenu.this);
        numPlayersDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        numPlayersDialog.setContentView(R.layout.numplayerslayout);

        ImageView twoPlayer = (ImageView)numPlayersDialog.findViewById(R.id.players2);
        ImageView threePlayer = (ImageView)numPlayersDialog.findViewById(R.id.players3);
        ImageView fourPlayer = (ImageView)numPlayersDialog.findViewById(R.id.players4);

        numPlayersDialog.show();

        twoPlayer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //numPlayersDialog.dismiss();
                Intent intent = new Intent(StartUpMenu.this, MainActivity.class);
                intent.putExtra("numplayers",2);
                intent.putExtra("status","");
                startActivity(intent);
            }
        });

        threePlayer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //numPlayersDialog.dismiss();
                Intent intent = new Intent(StartUpMenu.this, MainActivity.class);
                intent.putExtra("numplayers",3);
                intent.putExtra("status","");
                startActivity(intent);

            }
        });

        fourPlayer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //numPlayersDialog.dismiss();
                Intent intent = new Intent(StartUpMenu.this, MainActivity.class);
                intent.putExtra("numplayers",4);
                intent.putExtra("status","");
                startActivity(intent);
            }
        });
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
}