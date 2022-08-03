package com.example.photoalbum;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.Date;

public class IndividualStampActivity extends AppCompatActivity {

    // Declaring variables
    private Button btnSelectImage;
    private ImageView imgStamp;
    private DatabaseHelper databaseHelper;
    private String stampLink;
    private TextView txtTitle;
    final int REQUEST_IMAGE_OPEN = 1;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_individual_stamp);

        // Initializing variables
        btnSelectImage = findViewById(R.id.btnSelectImage);
        imgStamp = findViewById(R.id.imgStamp);
        databaseHelper = new DatabaseHelper(this);
        txtTitle = findViewById(R.id.txtTitle);

        // Putting an arrow back icon in the action bar. Only the visual
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // Set the title
        txtTitle.setText(getStampNameFromIntent());

        // Code for the onClickListener of the button
        btnSelectImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectImage();
            }
        });

        imgStamp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveStampInDatabase(stampLink);
            }
        });

    }

    public void selectImage() {
        Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
        intent.setType("image/*");
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        // Only the system receives the ACTION_OPEN_DOCUMENT, so no need to test.
        startActivityForResult(intent, REQUEST_IMAGE_OPEN);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_IMAGE_OPEN && resultCode == RESULT_OK) {
            Uri fullPhotoUri = data.getData();
            stampLink = fullPhotoUri.toString();
            Picasso.get()
                    .load(fullPhotoUri)
                    .into(imgStamp);
        }
    }

    private void saveStampInDatabase(String stampLink) {
        int userID = databaseHelper.getUserID(getEmailFromIntent());
        int stampID = databaseHelper.getStampID(getStampNameFromIntent());
        Date today = new Date();
        String uploadDate = today.toString();

        if (databaseHelper.saveStamp(userID, stampID, uploadDate, stampLink)) {
                Toast.makeText(this, "Stamp saved successfully!", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Error, please try again", Toast.LENGTH_SHORT).show();
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

    private String getStampNameFromIntent() {
        Intent intent = getIntent();
        if (intent != null) {
            return intent.getStringExtra("stamp_name");
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