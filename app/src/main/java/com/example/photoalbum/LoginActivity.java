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

public class LoginActivity extends AppCompatActivity {

    // Declaring variables
    private TextView txtUser_Login;
    private int user_id;
    private RecyclerView recView;
    private PhotoRecViewAdapter adapter;
    private ArrayList<String> dataset;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Initialize variables
        init();

        // Putting an arrow back icon in the action bar. Only the visual
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        txtUser_Login.setText(String.valueOf(getIdFromIntent()));

        recView = findViewById(R.id.recViewLatest);
        recView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL,
                false));

        adapter = new PhotoRecViewAdapter(dataset);
        recView.setAdapter(adapter);
    }

    // Initialize variables
    private void init() {
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

    private int getIdFromIntent() {
        Intent intent = getIntent();
        if (intent != null) {
            return intent.getIntExtra("id", -1);
        } else {
            return -2;
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