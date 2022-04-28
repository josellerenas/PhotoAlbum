package com.example.photoalbum;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class LoginActivity extends AppCompatActivity {

    // Declaring variables
    private TextView txtUser_Login;
    private RecyclerView recView;
    private PhotoRecViewAdapter adapter;
    private List<String> dataset;
    private DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Initialize variables
        init();

        // Putting an arrow back icon in the action bar. Only the visual
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // The recyclerView is filled with the user's origin state cities
        txtUser_Login.setText(getFirstNameFromIntent());
        dataset = databaseHelper.getUsersCities(getEmailFromIntent());


        recView = findViewById(R.id.recViewLatest);
        recView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL,
                false));

        adapter = new PhotoRecViewAdapter(dataset);
        recView.setAdapter(adapter);

        //TODO Check if I can improve the swiping of the cardviews, and keep working on the next
    }

    // Initialize variables
    private void init() {
        databaseHelper = new DatabaseHelper(this);
        txtUser_Login = findViewById(R.id.txtUser_Login);
        dataset = new ArrayList<>();
        dataset.add("Colima");
        dataset.add("Villa de Álvarez");
        dataset.add("Comala");
        dataset.add("Coquimatlán");
        dataset.add("Cuauhtémoc");
        dataset.add("Tecomán");
        dataset.add("Minatitlán");
        dataset.add("Ixtlahuacán");
        dataset.add("Armería");
        dataset.add("Manzanillo");
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