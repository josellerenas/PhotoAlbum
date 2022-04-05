package com.example.photoalbum;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    // Declaring variables that will be linked to the UI elements
    EditText editTxtUser, editTxtPassword;
    Button btnLogin;
    TextView txtSignIn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Calling a method to initialice variables
        init();

        // Making an hyperlink out of the Sign In TextView
        txtSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });

        //Login code
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseHelper databaseHelper = new DatabaseHelper(MainActivity.this);
                if (!emptyField()) {
                    if (databaseHelper.existsUser(editTxtUser.getText().toString())) {
                        if (databaseHelper.correctPassword(editTxtUser.getText().toString(),
                                editTxtPassword.getText().toString())) {
                            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                            intent.putExtra("id", databaseHelper.getId(editTxtUser.getText().toString()));
                            startActivity(intent);
                        } else {
                            Toast.makeText(MainActivity.this, "Wrong password", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(MainActivity.this, "User not registered",
                                Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(MainActivity.this, "Please be sure to fill all the fields",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    
    // Inicializing UI elements variables
    private void init () {
        editTxtUser = findViewById(R.id.editTxtUser);
        editTxtPassword = findViewById(R.id.editTxtPassword);
        btnLogin = findViewById(R.id.btnLogIn);
        txtSignIn = findViewById(R.id.txtSignIn);
    }

    // Verify if any of the register fields is empty
    private boolean emptyField() {
        if (editTxtUser.getText().toString().isEmpty() || editTxtPassword.getText().toString().isEmpty()) {
            return true;
        } else {
            return false;
        }
    }
}