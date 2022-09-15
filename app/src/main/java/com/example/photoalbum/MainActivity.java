package com.example.photoalbum;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

/*
    MainActivity -> Checked and working 01/08/22
    RegisteredActivity -> Checked and working 01/08/22
    LoginActivity -> Checked and working 02/08/22

    Last update 14-sept-22: I can upload images to Firebase Storage.
    TODO
    IndividualStampActivity: Load images already in Firebase Storage.
    Move database to Firebase.
    All Activities -> Handling the 'back' button
 */

public class MainActivity extends AppCompatActivity {

    // Declaring variables that will be linked to the UI elements.
    EditText editTxtUser, editTxtPassword;
    Button btnLogin;
    TextView txtSignIn;

    // OnCreate event. Where the stuff happens.
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize variables
        editTxtUser = findViewById(R.id.editTxtUser);
        editTxtPassword = findViewById(R.id.editTxtPassword);
        btnLogin = findViewById(R.id.btnLogIn);
        txtSignIn = findViewById(R.id.txtSignIn);

        // Making an hyperlink from the Sign In TextView
        txtSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });

        // Login button code
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseHelper databaseHelper = new DatabaseHelper(MainActivity.this);
                if (editTxtUser.getText().toString().isEmpty() || editTxtPassword.getText().toString().isEmpty()) {
                    Toast.makeText(MainActivity.this, "Please be sure to fill all the fields",
                            Toast.LENGTH_SHORT).show();
                } else {
                    if (databaseHelper.existsUser(editTxtUser.getText().toString())) {
                        if (databaseHelper.correctPassword(editTxtUser.getText().toString(),
                                editTxtPassword.getText().toString())) {
                            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                            intent.putExtra("first_name", databaseHelper.getFirstName(editTxtUser.getText().toString()));
                            intent.putExtra("email", editTxtUser.getText().toString());
                            startActivity(intent);
                        } else {
                            Toast.makeText(MainActivity.this, "Wrong password", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(MainActivity.this, "User not registered",
                                Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }
}