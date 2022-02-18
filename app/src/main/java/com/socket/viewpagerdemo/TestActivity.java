package com.socket.viewpagerdemo;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import pl.droidsonroids.gif.GifImageView;

public class TestActivity extends AppCompatActivity {
    private ZoomImageView imageView;
    private ImageView back;
    private TextView textView;
    private GifImageView gif11;
    private GifImageView gif22;

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
        setContentView(R.layout.activity_test);
        imageView = findViewById(R.id.iv_content);
        textView = findViewById(R.id.textView);
        gif11 = findViewById(R.id.gif11);
        gif22 = findViewById(R.id.gif22);
        back = findViewById(R.id.iv_back);
        if ("故障机理".equals(getIntent().getStringExtra("str"))) {
            imageView.setImageDrawable(getResources().getDrawable(R.drawable.three));
            textView.setText("故障机理");
        } else if ("故障现象".equals(getIntent().getStringExtra("str"))) {
            imageView.setImageDrawable(getResources().getDrawable(R.drawable.six));
            textView.setText("故障现象");
        }else if ("接线图".equals(getIntent().getStringExtra("str"))){
            imageView.setImageDrawable(getResources().getDrawable(R.drawable.five));
            textView.setText("接线图");
        }else if ("1".equals(getIntent().getStringExtra("change"))) {
            imageView.setImageDrawable(getResources().getDrawable(R.drawable.e));
            textView.setText("1");
        } else if ("2".equals(getIntent().getStringExtra("change"))) {
            imageView.setImageDrawable(getResources().getDrawable(R.drawable.f));
            textView.setText("2");
        }
        if ("1".equals(getIntent().getStringExtra("gif"))) {
            imageView.setVisibility(View.GONE);
            gif11.setVisibility(View.VISIBLE);
            gif22.setVisibility(View.GONE);
        } else if ("2".equals(getIntent().getStringExtra("gif"))) {
            imageView.setVisibility(View.GONE);
            gif11.setVisibility(View.GONE);
            gif22.setVisibility(View.VISIBLE);
        } else if ("3".equals(getIntent().getStringExtra("gif"))) {
            imageView.setImageDrawable(getResources().getDrawable(R.drawable.seven));
            gif11.setVisibility(View.GONE);
            gif22.setVisibility(View.GONE);
        }
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }


}
