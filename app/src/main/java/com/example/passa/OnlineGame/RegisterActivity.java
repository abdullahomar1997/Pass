package com.example.passa.OnlineGame;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import android.content.Intent;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

import com.example.passa.MainActivity;
import com.example.passa.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class RegisterActivity extends AppCompatActivity {

    private static final Pattern PASSWORD_PATTERN =
            Pattern.compile("^" +
                    //"(?=.*[0-9])" +         //at least 1 digit
                    //"(?=.*[a-z])" +         //at least 1 lower case letter
                    //"(?=.*[A-Z])" +         //at least 1 upper case letter
                    "(?=.*[a-zA-Z])" +      //any letter
                    // "(?=.*[@#$%^&+=])" +    //at least 1 special character
                    "(?=\\S+$)" +           //no white spaces
                    ".{4,}" +               //at least 4 characters
                    "$");

    private EditText usernameEt;
    private EditText emailAddressEt;
    private EditText passwordEt;
    private EditText passwordConfirmationEt;
    private Button registerBtn;
    private TextView loginBtn;
    private ProgressBar progressBar;
    private FirebaseAuth firebaseauth;

    private FirebaseFirestore firestore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        firestore = FirebaseFirestore.getInstance();

        setupUserInterface();

        firebaseauth = FirebaseAuth.getInstance();

        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (inputIsValid()) {
                    registerPlayer();
                }
            }
        });

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                enterLoginPage();
            }
        });
    }

    private boolean inputIsValid() {
        return validateEmailAddress() && validatePassword() && validateUsername();
    }

    private void registerPlayer() {

        progressBar.setVisibility(View.VISIBLE);
        registerBtn.setVisibility(View.GONE);

        String emailAddress = emailAddressEt.getText().toString().trim();
        String password = passwordEt.getText().toString().trim();

        firebaseauth.createUserWithEmailAndPassword(emailAddress, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        progressBar.setVisibility(View.GONE);

                        if (task.isSuccessful()) {
                            sendEmailConfirmation();
                        } else {
                            registerBtn.setVisibility(View.VISIBLE);
                            Toast.makeText(RegisterActivity.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private void enterLoginPage() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    private boolean validateEmailAddress() {

        String emailInput = emailAddressEt.getText().toString().trim();

        if (emailInput.isEmpty()) {
            emailAddressEt.setError("Field can't be empty");
            return false;
        } else if (!Patterns.EMAIL_ADDRESS.matcher(emailInput).matches()) {
            emailAddressEt.setError("Please enter a valid email address");
            return false;
        } else {
            emailAddressEt.setError(null);
            return true;
        }
    }

    private boolean validateUsername() {

        String usernameInput = usernameEt.getText().toString().trim();

        if (usernameInput.isEmpty()) {
            usernameEt.setError("Field can't be empty");
            return false;
        } else if (usernameInput.length() > 15) {
            usernameEt.setError("Username too long");
            return false;
        } else {
            usernameEt.setError(null);
            return true;
        }

    }

    private boolean validatePassword() {

        String passwordInput = passwordEt.getText().toString().trim();
        String PasswordCorn = passwordConfirmationEt.getText().toString().trim();

        if (passwordInput.isEmpty()) {
            passwordEt.setError("Field can't be empty");
            return false;
        } else if (!PASSWORD_PATTERN.matcher(passwordInput).matches()) {
            passwordEt.setError("Password too weak");
            return false;
        } else if (!passwordInput.equals(PasswordCorn)) {
            passwordConfirmationEt.setError("Password does not match");
            return false;
        } else {
            passwordEt.setError(null);
            return true;
        }
    }

    private void sendEmailConfirmation() {

        firebaseauth.getCurrentUser().sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {

                if (task.isSuccessful()) {

                    String username = usernameEt.getText().toString().trim();
                    String emailAddress = emailAddressEt.getText().toString().trim();
                    String password = passwordEt.getText().toString().trim();

                    Map<String, Object> playerMap = new HashMap<>();

                    playerMap.put("playerUsername", username);
                    playerMap.put("playerEmail", emailAddress);
                    playerMap.put("playerPassword", password);

                    firestore.collection("players").add(playerMap).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                        @Override
                        public void onSuccess(DocumentReference documentReference) {

                            Toast.makeText(RegisterActivity.this, "Registration was Successful Please Check Your Email to Verify", Toast.LENGTH_LONG).show();

                            enterLoginPage();

                        }
                    })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                }
                            });
                } else {
                    Toast.makeText(RegisterActivity.this, "Send Email Verification failed please try again", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private void setupUserInterface() {

        usernameEt = (EditText) findViewById(R.id.EdtxtUsernameReg);
        emailAddressEt = (EditText) findViewById(R.id.EdtxtEmailAddressReg);
        passwordEt = (EditText) findViewById(R.id.EdtxtPasswordReg);
        passwordConfirmationEt = (EditText) findViewById(R.id.EdtxtPasswordConfirmationReg);
        registerBtn = (Button) findViewById(R.id.BtnRegisterReg);
        loginBtn = (TextView) findViewById(R.id.txtViewLoginReg);
        progressBar = (ProgressBar) findViewById(R.id.progressBarReg);

        progressBar.setVisibility(View.GONE);

    }
}