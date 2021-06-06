package com.example.passa.OnlineGame;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.passa.R;

public class OnlineJoinGame extends AppCompatActivity {

    EditText gameIdEt;
    Button joinBtn;
    String PlayerId;
    String GameId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_online_join_game);

        Intent bundle = getIntent();
        PlayerId = bundle.getStringExtra("playerid");

        gameIdEt = (EditText) findViewById(R.id.searchGameEt);
        joinBtn = (Button) findViewById(R.id.joinbtn);

        joinBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                GameId = (String)gameIdEt.getText().toString();

                Intent intent = new Intent(OnlineJoinGame.this, OnlineGameActivity.class);
                intent.putExtra("gameid", GameId);
                intent.putExtra("playerid", PlayerId);

                startActivity(intent);
/*
                boolean installed = isAppInstalled("com.whatsapp");

                if(installed){

                    String num = "+27763505005";
                    String text = GameId;

                    Intent intent1 = new Intent(Intent.ACTION_VIEW);
                    intent1.setData(Uri.parse("http://api.whatsapp.com/send?phone="+num+"&text="+"abc"));

                    startActivity(intent1);
                }*/
                
            }
        });
    }
}