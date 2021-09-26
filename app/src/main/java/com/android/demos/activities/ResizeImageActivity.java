package com.android.demos.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.android.demos.R;

public class ResizeImageActivity extends AppCompatActivity {

    CustomImageView imgv_image;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resize_image);

        imgv_image = findViewById(R.id.imgv_image);
    }
}