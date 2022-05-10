package com.example.photoalbum;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

public class IndividualStampActivity extends AppCompatActivity {

    Button btnSelectImage;
    ImageView imgStamp;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_individual_stamp);

        init();
        
        btnSelectImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // TODO investigate how to use glide
                Glide.with(IndividualStampActivity.this)
                        .asBitmap()
                        .load("https://www.egames.news/__export/1632703547217/sites/debate/img/2021/09/26/por_qux_es_tan_popular_el_anime_de_shingeki_no_kyojin.jpg_1902800913.jpg")
                        .into(imgStamp);
            }
        });


//        Intent pickPhoto = new Intent(Intent.ACTION_PICK,
//                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
//        startActivityForResult(pickPhoto , 1);//one can be replaced with any action code
//
//        public void openSomeActivityForResult() {
//            Intent intent = new Intent(this, SomeActivity.class);
//            someActivityResultLauncher.launch(intent);
//        }
//
//// You can do the assignment inside onAttach or onCreate, i.e, before the activity is displayed
//        ActivityResultLauncher<Intent> someActivityResultLauncher = registerForActivityResult(
//                new ActivityResultContracts.StartActivityForResult(),
//                new ActivityResultCallback<ActivityResult>() {
//                    @Override
//                    public void onActivityResult(ActivityResult result) {
//                        if (result.getResultCode() == Activity.RESULT_OK) {
//                            // There are no request codes
//                            Intent data = result.getData();
//                            doSomeOperations();
//                        }
//                    }
//                });
    }

    public void selectImage() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
//        intent.setType("image/*");
//        Toast.makeText(this, "entré", Toast.LENGTH_SHORT).show();
        someActivityResultLauncher.launch(intent);
    }

    ActivityResultLauncher<Intent> someActivityResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == RESULT_OK) {
                        // There are no request codes
                        Intent data = result.getData();
                        Uri uri = data.getData();
                    }
                }
            });
    
    private void init() {
        btnSelectImage = findViewById(R.id.btnSelectImage);
        imgStamp = findViewById(R.id.imgStamp);
    }
}