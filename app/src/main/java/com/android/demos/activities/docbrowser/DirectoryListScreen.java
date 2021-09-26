package com.android.demos.activities.docbrowser;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.android.demos.R;
import com.android.demos.activities.other.OnRecyclerItemClicked;

import java.io.File;
import java.util.ArrayList;

public class DirectoryListScreen extends AppCompatActivity implements OnRecyclerItemClicked
{
    TextView txtPath;
    RecyclerView recvFilesList;

    File[] files;
    ArrayList<FileDetailsBean> listFiles = new ArrayList<>();

    String currentPath = "";

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_directory_list_screen);
        addActionBar();
        initUI();


        String state = Environment.getExternalStorageState();
        if(state.equals("mounted"))
        {
            //File externalFilesDir = getExternalCacheDir();
            //System.out.println("Here => "+externalFilesDir.getAbsolutePath());

            File rootFile = Environment.getExternalStorageDirectory();
            System.out.println("Here2 => "+rootFile.getAbsolutePath());

            listFiles(rootFile);
        }
    }

    public void addActionBar()
    {
        try {
            getSupportActionBar().setTitle("Directory List");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        } catch (Exception e) {
        }
    }

    public void initUI()
    {
        txtPath = findViewById(R.id.txtPath);
        recvFilesList = findViewById(R.id.recvFilesList);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.menu_done, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch (item.getItemId())
        {
            case android.R.id.home:
                onBackPressed();
                break;
            case R.id.action_done:
                Intent returnIntent = new Intent();
                returnIntent.putExtra("path", txtPath.getText().toString());
                setResult(Activity.RESULT_OK,returnIntent);
                DirectoryListScreen.this.finish();
                break;
        }

        return super.onOptionsItemSelected(item);
    }


    public void listFiles(File dir)
    {
        txtPath.setText(dir.getAbsolutePath());

        listFiles.clear();

        files = dir.listFiles();

        if(files!=null)
        {
            for(int i=0; i < files.length; i++)
            {
                FileDetailsBean fileDetails = new FileDetailsBean();

                File f = files[i];
                if(f.isDirectory())
                {
                    fileDetails.setDirectory(true);

                    String absolutePath = files[i].getAbsolutePath();
                    String path = files[i].getPath();
                    String name = files[i].getName();

                    fileDetails.setAbsolutePath(absolutePath);
                    fileDetails.setPath(path);
                    fileDetails.setFileName(name);
                    fileDetails.setFileExtension("");
                }
                else
                {
                    fileDetails.setDirectory(false);

                    String absolutePath = files[i].getAbsolutePath();
                    String path = files[i].getPath();
                    String name = files[i].getName();

                    String extension = "";

                    try {
                        String[] splitName = name.split(".");
                        if(splitName.length>0)
                        {
                            extension = splitName[splitName.length-1];
                        }
                    }
                    catch (Exception e) {
                        e.printStackTrace();
                    }

                    fileDetails.setAbsolutePath(absolutePath);
                    fileDetails.setPath(path);
                    fileDetails.setFileName(name);
                    fileDetails.setFileExtension(extension);
                }

                listFiles.add(fileDetails);
            }
        }

        updateAdapter();
    }

    public void updateAdapter()
    {
        //StaggeredGridLayoutManager manager = new StaggeredGridLayoutManager(4, StaggeredGridLayoutManager.VERTICAL);
        GridLayoutManager manager = new GridLayoutManager(this, 4);
        AdapterFileList adapterFileList = new AdapterFileList(DirectoryListScreen.this, listFiles,
                this, recvFilesList.getId());
        recvFilesList.setLayoutManager(manager);
        recvFilesList.setAdapter(adapterFileList);
    }

    @Override
    public void onRecyclerItemClicked(int recycler_id, Object item, int position)
    {
        if(recycler_id==R.id.recvFilesList)
        {
            FileDetailsBean fileDetailsBean = (FileDetailsBean) item;
            String dirPath = fileDetailsBean.getAbsolutePath();
            File dir = new File(dirPath);
            listFiles(dir);
        }
    }

    @Override
    public void onBackPressed()
    {
        Intent returnIntent = new Intent();
        setResult(Activity.RESULT_CANCELED,returnIntent);

        super.onBackPressed();
    }
}