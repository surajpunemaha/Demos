package com.android.demos.activities.docbrowser;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.android.demos.R;
import com.android.demos.activities.PhotoEditingActivity;
import com.android.demos.activities.other.PermissionsCheck;

public class BrowseDocumentsActivity extends AppCompatActivity implements View.OnClickListener
{
    Button btn_browse;
    TextView txtPath;
    int PICK_FOLDER_PATH = 1234;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_browse_documents);
        addActionBar();
        initUI();
    }

    public void addActionBar()
    {
        try {
            getSupportActionBar().setTitle("Directory Browser");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        } catch (Exception e) {
        }
    }

    public void initUI()
    {
        btn_browse = findViewById(R.id.btn_browse);
        btn_browse.setOnClickListener(this);

        txtPath = findViewById(R.id.txtPath);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch (item.getItemId())
        {
            case android.R.id.home:
                onBackPressed();
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View view)
    {
        if(view.getId()==R.id.btn_browse)
        {
            boolean permitted = PermissionsCheck.checkStoragePermission(BrowseDocumentsActivity.this);
            if(permitted)
            {
                Intent viewList = new Intent(BrowseDocumentsActivity.this, DirectoryListScreen.class);
                startActivityForResult(viewList, PICK_FOLDER_PATH);
            }
            else
            {
                PermissionsCheck.getStoragePermissions(BrowseDocumentsActivity.this);
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        if (requestCode == PICK_FOLDER_PATH && resultCode == RESULT_OK)
        {
            Bundle extras = data.getExtras();
            String path = extras.getString("path");
            txtPath.setText("Your selected Path \n\n"+path);
        }
    }
}