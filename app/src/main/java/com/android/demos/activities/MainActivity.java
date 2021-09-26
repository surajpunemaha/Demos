package com.android.demos.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.android.demos.R;
import com.android.demos.activities.docbrowser.BrowseDocumentsActivity;


public class MainActivity extends AppCompatActivity implements View.OnClickListener
{
    Button btn_moveImage, btn_resizeImage, btn_photoEditor, btn_directoryBrowser;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initUI();
    }

    public void initUI()
    {
        btn_moveImage = findViewById(R.id.btn_moveImage);
        btn_moveImage.setOnClickListener(this);

        btn_resizeImage = findViewById(R.id.btn_resizeImage);
        btn_resizeImage.setOnClickListener(this);

        btn_photoEditor = findViewById(R.id.btn_photoEditor);
        btn_photoEditor.setOnClickListener(this);

        btn_directoryBrowser = findViewById(R.id.btn_directoryBrowser);
        btn_directoryBrowser.setOnClickListener(this);
    }

    @Override
    public void onClick(View v)
    {
        if(v.getId()==R.id.btn_moveImage)
        {
            startActivity(new Intent(this, MoveImageActivity.class));
        }

        if(v.getId()==R.id.btn_resizeImage)
        {
            startActivity(new Intent(this, ResizeImageActivity.class));
        }

        if(v.getId()==R.id.btn_photoEditor)
        {
            startActivity(new Intent(this, PhotoEditingActivity.class));
        }

        if(v.getId()==R.id.btn_directoryBrowser)
        {
            startActivity(new Intent(this, BrowseDocumentsActivity.class));
        }
    }
}
