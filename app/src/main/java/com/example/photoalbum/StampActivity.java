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

public class StampActivity extends AppCompatActivity {

    private List<City> city;
    private TextView txtCitysStamps;
    private List<String> dataset, listStampsImageUrl;
    private DatabaseHelper databaseHelper;
    private RecyclerView recView;
    private PhotoRecViewAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stamp);

        // Initialize variables
        init();

        // Putting an arrow back icon in the action bar. Only the visual
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // The recyclerView is filled with the selected city's stamps
        txtCitysStamps.setText(getCityNameFromIntent());
        dataset = databaseHelper.getCitysStamps(getCityNameFromIntent());
        listStampsImageUrl = databaseHelper.getStampsImageUrl(getCityNameFromIntent());
        recView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL,
                false));
        adapter = new PhotoRecViewAdapter(this, dataset, listStampsImageUrl,
                getEmailFromIntent());
        recView.setAdapter(adapter);
    }

    private void init() {
        databaseHelper = new DatabaseHelper(this);
        txtCitysStamps = findViewById(R.id.txtCitysStamps);
        recView = findViewById(R.id.recViewStamps);
        city = new ArrayList<>();
        dataset = new ArrayList<>();
        listStampsImageUrl = new ArrayList<>();
    }

    private String getCityNameFromIntent() {
        Intent intent = getIntent();
        if (intent != null) {
            return intent.getStringExtra("city_name");
        } else {
            return "Error";
        }
    }

    private String getEmailFromIntent() {
        Intent intent = getIntent();
        if (intent != null) {
            return intent.getStringExtra("user_email");
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