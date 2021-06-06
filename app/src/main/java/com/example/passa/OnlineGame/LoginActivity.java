package com.example.passa.OnlineGame;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.passa.Player;
import com.example.passa.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

public class LoginActivity extends AppCompatActivity {

    EditText Username;
    EditText Password;
    Button Login;
    TextView ForgotPassword;
    TextView Register;
    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;
    ProgressBar progressBar;
    Player player;
    FirebaseFirestore firestore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        SetupUserInterface();

        progressBar.setVisibility(View.GONE);

        player = new Player();

        firebaseAuth = FirebaseAuth.getInstance();

        if (Username.getText().toString() != "" && Password.getText().toString() != "") {

            Login.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    progressBar.setVisibility(View.VISIBLE);
                    Login.setVisibility(View.GONE);

                    firebaseAuth.signInWithEmailAndPassword(Username.getText().toString(), Password.getText().toString()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {

                                firebaseUser = firebaseAuth.getCurrentUser();

                                if (firebaseUser.isEmailVerified()) {
                                    progressBar.setVisibility(View.GONE);
                                    Login.setVisibility(View.VISIBLE);
                                    EnterHomePage(Username.getText().toString(),Password.getText().toString());
                                    Username.setText("");
                                    Password.setText("");
                                } else {
                                    progressBar.setVisibility(View.GONE);
                                    Login.setVisibility(View.VISIBLE);
                                    Toast.makeText(LoginActivity.this, "Email not yet verified , Please Go To Your Email", Toast.LENGTH_SHORT).show();
                                }
                            } else {

                                progressBar.setVisibility(View.GONE);
                                Login.setVisibility(View.VISIBLE);
                                Toast.makeText(LoginActivity.this, task.getException().getMessage(), Toast.LENGTH_LONG).show();
                                Username.setError("Wrong Email of password");
                            }
                        }
                    });
                }
            });

            Register.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    EnterRegistrationPage();
                }
            });
        }
    }

    private void EnterRegistrationPage() {

        Intent intent = new Intent(this, RegisterActivity.class);
        startActivity(intent);
    }

    private void EnterHomePage(String email,String password) {

        //String playerid = getPlayerId(email,password);

       Intent intent = new Intent(this, OnlineGameMenu.class);
       intent.putExtra("playerid", email);

       startActivity(intent);
    }

    private String getPlayerId(String email, String password) {

        CollectionReference playerRef = firestore.collection("players");

        final String[] playerId = {""};

        playerRef.whereEqualTo("playerEmail", email).whereEqualTo("playerPassword", password).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        playerId[0] = document.getString("playerUsername");
                        break;
                    }
                }
            }
        });

        return playerId[0];
    }

    private void SetupUserInterface(){

        Username = (EditText) findViewById(R.id.EdtxtUsernameLogin);
        Password = (EditText) findViewById(R.id.EdTxtPasswordLogin);
        Login = (Button) findViewById(R.id.BtnLogin);
        ForgotPassword = (TextView)findViewById(R.id.txtViewForgotPasswordLogin);
        Register = (TextView) findViewById(R.id.txtViewRegisterLogin);
        progressBar = (ProgressBar)findViewById(R.id.progressBarLog);

        firestore = FirebaseFirestore.getInstance();
    }
}
