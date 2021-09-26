package com.android.demos.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.android.demos.R;

public class MoveImageActivity extends AppCompatActivity
{
    //CustomImageView imgv_image;
    ImageView imgv_image;
    FrameLayout container;
    int xDelta, yDelta;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_move_image);

        imgv_image = findViewById(R.id.imgv_image);
        container = findViewById(R.id.container);

        //imgv_image.setOnTouchListener(onTouchListener());
        imgv_image.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent event) {
                switch (event.getActionMasked()) {
                    case MotionEvent.ACTION_DOWN:
                        xDelta = (int) (view.getX() - event.getRawX());
                        yDelta = (int) (view.getY() - event.getRawY());
                        break;
                    case MotionEvent.ACTION_MOVE:
                        view.animate().x(event.getRawX() + xDelta).y(event.getRawY() + yDelta).setDuration(0).start();
                        break;
                    default:
                        return false;
                }
                return true;
            }
        });
    }


    /*private View.OnTouchListener onTouchListener()
    {
        return new View.OnTouchListener()
        {

            @SuppressLint("ClickableViewAccessibility")
            @Override
            public boolean onTouch(View view, MotionEvent event)
            {

                final int x = (int) event.getRawX();
                final int y = (int) event.getRawY();

                switch (event.getAction() & MotionEvent.ACTION_MASK)
                {

                    case MotionEvent.ACTION_DOWN:
                        FrameLayout.LayoutParams lParams = (FrameLayout.LayoutParams) view.getLayoutParams();
                        xDelta = x - lParams.leftMargin;
                        yDelta = y - lParams.topMargin;
                        break;

                    case MotionEvent.ACTION_UP:
                        Toast.makeText(MoveImageActivity.this, "I'm here!", Toast.LENGTH_SHORT).show();
                        break;

                    case MotionEvent.ACTION_MOVE:
                        FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) view.getLayoutParams();
                        layoutParams.leftMargin = x - xDelta;
                        layoutParams.topMargin = y - yDelta;
                        layoutParams.rightMargin = 0;
                        layoutParams.bottomMargin = 0;
                        view.setLayoutParams(layoutParams);
                        break;
                }

                container.invalidate();
                return true;
            }
        };
    }*/
}