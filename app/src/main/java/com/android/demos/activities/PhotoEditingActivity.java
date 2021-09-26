package com.android.demos.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.demos.R;
import com.android.demos.activities.other.PermissionsCheck;

import ja.burhanrashid52.photoeditor.PhotoEditor;
import ja.burhanrashid52.photoeditor.PhotoEditorView;

public class PhotoEditingActivity extends AppCompatActivity implements View.OnClickListener
{
    PhotoEditorView photoEditorView;
    PhotoEditor mPhotoEditor;
    Button btn_change, btn_add;
    ImageView imgv_frame;
    Button btn_canvas;

    int REQUEST_IMAGE_PICK =  7890;

    int[] frames = new int[] { R.drawable.frame_1, R.drawable.frame_2, R.drawable.frame_3, R.drawable.frame_4, R.drawable.frame_5 };
    int active_frame = 0;
    boolean canvasSet = false;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo_editing);

        initUI();

        photoEditorView.getSource().setScaleType(ImageView.ScaleType.FIT_XY);
        photoEditorView.getSource().setImageResource(R.drawable.canvas);
    }

    public void initUI()
    {
        imgv_frame = findViewById(R.id.imgv_frame);
        btn_add = findViewById(R.id.btn_add);
        btn_add.setOnClickListener(this);

        btn_change = findViewById(R.id.btn_change);
        btn_change.setOnClickListener(this);

        btn_canvas = findViewById(R.id.btn_canvas);
        btn_canvas.setOnClickListener(this);

        photoEditorView = findViewById(R.id.photoEditorView);

        mPhotoEditor = new PhotoEditor.Builder(this, photoEditorView)
                .setPinchTextScalable(true)
                .setClipSourceImage(true)
                .build();
    }

    public void addImage(Bitmap bitmap)
    {
        /*Bitmap icon = BitmapFactory.decodeResource(getResources(),
                R.drawable.shirely);*/
        mPhotoEditor.addImage(bitmap);
    }

    @Override
    public void onClick(View view)
    {
        if(view.getId()==R.id.btn_change)
        {
            if(active_frame==frames.length-1)
            {
                active_frame = 0;
            }
            else
            {
                active_frame ++;
            }

            imgv_frame.setImageResource(frames[active_frame]);
        }

        if(view.getId()==R.id.btn_add)
        {
            boolean permitted = PermissionsCheck.checkStoragePermission(PhotoEditingActivity.this);
            if(permitted)
            {
                pickImage();
            }
            else
            {
                PermissionsCheck.getStoragePermissions(PhotoEditingActivity.this);
            }
        }

        if(view.getId()==R.id.btn_canvas)
        {
            if(canvasSet)
            {
                photoEditorView.getSource().setScaleType(ImageView.ScaleType.FIT_XY);
                photoEditorView.getSource().setImageResource(R.drawable.canvas);
                btn_canvas.setText("Set Canvas");
                imgv_frame.setVisibility(View.VISIBLE);
                canvasSet = false;
            }
            else
            {
                photoEditorView.getSource().setScaleType(ImageView.ScaleType.FIT_XY);
                photoEditorView.getSource().setImageResource(R.drawable.canvas_1);
                btn_canvas.setText("Remove Canvas");
                imgv_frame.setVisibility(View.GONE);
                canvasSet = true;
            }
        }
    }

    public void pickImage()
    {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        Intent.createChooser(intent, "Select Image");
        intent.setType("image/*");
        intent.putExtra("return-data", true);
        startActivityForResult(intent, REQUEST_IMAGE_PICK);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        if (requestCode == REQUEST_IMAGE_PICK && resultCode == RESULT_OK)
        {
            if (data != null)
            {
                try
                {
                    Uri uri = data.getData();
                    Bitmap selected_img_bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                    addImage(selected_img_bitmap);
                }
                catch (Exception e)
                {
                    Toast.makeText(this, "error " + e, Toast.LENGTH_SHORT).show();
                }
            }
        }
    }
}