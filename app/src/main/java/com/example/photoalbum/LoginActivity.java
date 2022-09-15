package com.example.photoalbum;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class LoginActivity extends AppCompatActivity {

    // Declaring variables
    private TextView txtUser_Login;
    private RecyclerView recView;
    private PhotoRecViewAdapter adapter;
    private List<String> dataset, listCitiesImage;
    private DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Initialize variables
        databaseHelper = new DatabaseHelper(this);
        txtUser_Login = findViewById(R.id.txtUser_Login);
        dataset = new ArrayList<>();
        listCitiesImage = new ArrayList<>();

        // Putting an arrow back icon in the action bar. Only the visual
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // The recyclerView is filled with the user's origin state cities
        txtUser_Login.setText(getFirstNameFromIntent());
        dataset = databaseHelper.getUsersCities(getEmailFromIntent());

        listCitiesImage = databaseHelper.getCitiesImageUrl(getEmailFromIntent());
        recView = findViewById(R.id.recViewLatest);
        recView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL,
                false));
        adapter = new PhotoRecViewAdapter(this, dataset, listCitiesImage, getEmailFromIntent());
        recView.setAdapter(adapter);
    }

    // Obtain the user's first name, in order to salute them
    private String getFirstNameFromIntent() {
        Intent intent = getIntent();
        if (intent != null) {
            return intent.getStringExtra("first_name");
        } else {
            return "Error";
        }
    }

    // Obtain the user's email, in order to fill the recyclerView
    private String getEmailFromIntent() {
        Intent intent = getIntent();
        if (intent != null) {
            return intent.getStringExtra("email");
        } else {
            return "Error";
        }
    }

    // Method to actually get to work the arrow back icon in the action bar
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}