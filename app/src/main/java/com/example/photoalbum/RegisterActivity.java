package com.example.photoalbum;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.List;

public class RegisterActivity extends AppCompatActivity {

    // Declaring variables
    EditText editTxtFirstName, editTxtLastName, editTxtEmail, editTxtPassword, editTxtConfirmPassword;
    Spinner spCountry, spState, spCity;
    Button btnSignIn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        // Initialize variables
        initVariables();

        // Fill the Spinners
        fillSpinners();
        
        // OnClickListener for the btnSignIn
        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // If any of the fields is not empty
                if (!emptyField()) {
                    // If both password fields match
                    if (editTxtPassword.getText().toString().equals(editTxtConfirmPassword.getText().toString())) {
                        // Create an instance of the DatabaseHelper to start make queries
                        DatabaseHelper databaseHelper = new DatabaseHelper(RegisterActivity.this);
                        // If the email is not already registered
                        if (!databaseHelper.existsUser(editTxtEmail.getText().toString())) {
                            // Create a new instance of the class User
                            User newUser = new User(editTxtFirstName.getText().toString(),
                                    editTxtLastName.getText().toString(), editTxtEmail.getText().toString(),
                                    editTxtPassword.getText().toString(), spCountry.getSelectedItem().toString(),
                                    spState.getSelectedItem().toString(), spCity.getSelectedItem().toString());
                            // Add the new user to the database. It's inside an 'if' to troubleshooting matters
                            if (databaseHelper.registerUser(newUser)) {
                                Toast.makeText(RegisterActivity.this, "User registered successfully",
                                        Toast.LENGTH_SHORT).show();
                                // Redirect to the login activity
                                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                                intent.putExtra("first_name", editTxtFirstName.getText().toString());
                                intent.putExtra("email", editTxtEmail.getText().toString());
                                startActivity(intent);
                            } else {
                                Toast.makeText(RegisterActivity.this, "Error, please try again",
                                        Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(RegisterActivity.this, "This email is already registered",
                                    Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(RegisterActivity.this, "The passwords don't match",
                                Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(RegisterActivity.this, "Please be sure to fill all the fields",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    // Initialize variables
    private void initVariables() {
        editTxtFirstName = findViewById(R.id.editTxtFirstName);
        editTxtLastName = findViewById(R.id.editTxtLastName);
        editTxtEmail = findViewById(R.id.editTxtEmail);
        editTxtPassword = findViewById(R.id.editTxtPasswod_Register);
        editTxtConfirmPassword = findViewById(R.id.editTxtConfirmPassword_Register);
        spCountry = findViewById(R.id.spCountry);
        spState = findViewById(R.id.spState);
        spCity = findViewById(R.id.spCity);
        btnSignIn = findViewById(R.id.btnSignIn);
    }

    // Fill the Spinners
    private void fillSpinners() {
        // Declaring an instance of my databaseHelper
        DatabaseHelper databaseHelper = new DatabaseHelper(this);

        // Fill the spCountries
        List<String> countryList = databaseHelper.getCountries();
        ArrayAdapter<String> countryAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, countryList);
        spCountry.setAdapter(countryAdapter);

        // Fill the spStates
        List<String> stateList = databaseHelper.getStates();
        ArrayAdapter<String> stateAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, stateList);
        spState.setAdapter(stateAdapter);

        // Fill the spCities
        List<String> cityList = databaseHelper.getCities();
        ArrayAdapter<String> cityAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, cityList);
        spCity.setAdapter(cityAdapter);
    }

    // Verify if any of the register fields is empty
    private boolean emptyField() {
        if (editTxtFirstName.getText().toString().isEmpty() || editTxtLastName.getText().toString().isEmpty()
        || editTxtEmail.getText().toString().isEmpty() || editTxtPassword.getText().toString().isEmpty()
        || editTxtConfirmPassword.getText().toString().isEmpty() || spCountry.getSelectedItem().toString().isEmpty()
        || spState.getSelectedItem().toString().isEmpty() || spCity.getSelectedItem().toString().isEmpty()) {
            return true;
        } else {
            return false;
        }
    }

}