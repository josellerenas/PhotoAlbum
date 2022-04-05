package com.example.photoalbum;

import androidx.appcompat.app.AppCompatActivity;

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
        init();

        // Fill the Spinners
        fillSpinners();
        
        // OnClickListener for the btnSignIn
        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                User newUser = new User(editTxtFirstName.getText().toString(),
                        editTxtLastName.getText().toString(), editTxtEmail.getText().toString(),
                        editTxtPassword.getText().toString(), spCountry.getSelectedItem().toString(),
                        spState.getSelectedItem().toString(), spCity.getSelectedItem().toString());

                DatabaseHelper databaseHelper = new DatabaseHelper(RegisterActivity.this);

                if (!emptyField()) {
                    if (editTxtPassword.getText().toString().equals(editTxtConfirmPassword.getText().toString())) {
                        if (!databaseHelper.existsUser(editTxtEmail.getText().toString())) {
                            if (databaseHelper.registerUser(newUser)) {
                                Toast.makeText(RegisterActivity.this, "User registered successfully",
                                        Toast.LENGTH_SHORT).show();
                                //TODO make an intent to go to another activity
                            } else {
                                Toast.makeText(RegisterActivity.this, "Error, please try again",
                                        Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(RegisterActivity.this, "This email is already registered",
                                    Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(RegisterActivity.this, "The passwords doesn't match",
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
    private void init() {
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