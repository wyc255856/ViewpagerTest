package com.socket.viewpagerdemo;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

public class WelcomeActivity extends AppCompatActivity implements Handler.Callback {
    private Button button;
    private ProgressBar progress_bar;

    private int progress = 0;
    private int sendProgress = 0;
    private Handler handler;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        getWindow().setStatusBarColor(Color.TRANSPARENT);
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }
        setContentView(R.layout.activity_welcome);
        handler = new Handler(this);

        button = findViewById(R.id.button_welcome);
        progress_bar = findViewById(R.id.progress_bar);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(WelcomeActivity.this, ListActivity.class));
            }
        });
        handler = new Handler(this);
        // 循环方法1
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                handler.postDelayed(this, 30);
                handler.sendEmptyMessage(1);
            }
        }, 0);

    }

    @Override
    public boolean handleMessage(@NonNull Message msg) {
        switch (msg.what) {
            case 1:
                if (progress == 100) {
                    progress = 101;
                    startActivity(new Intent(WelcomeActivity.this, ListActivity.class));
                    finish();
                } else {
                    progress++;
                    progress_bar.setProgress(progress);
                }
                break;
        }
        return true;
    }
}
