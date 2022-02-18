package com.socket.viewpagerdemo;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.android.material.tabs.TabLayout;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private ViewPager mViewPager;
    private ImageView imageView;
    private LinearLayout ll_layout;
    private String[] tabs = {"故障现象", "故障机理", "接线图", "接线插图", "可能原因", "故障检测"};
    private List<TabFragment> tabFragmentList = new ArrayList<>();
    private TabLayout tabLayout;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        getWindow().setStatusBarColor(Color.TRANSPARENT);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();

        tabLayout = findViewById(R.id.tab_layout);
        for (int i = 0; i < tabs.length; i++) {
            tabLayout.addTab(tabLayout.newTab().setText(tabs[i]));
            tabFragmentList.add(TabFragment.newInstance(tabs[i]));
        }
        initView();
    }
    private boolean isInstallByread(String packageName) {
        return new File("/data/data/" + packageName).exists();
    }
    private void initView() {
        mViewPager = findViewById(R.id.viewPager);
        ll_layout = findViewById(R.id.ll_layout);
        imageView = findViewById(R.id.iv_back);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
//                try {
//                    if (isInstallByread("com.autonavi.minimap")) {
//                        Intent intent = new Intent(
//                                "android.intent.action.VIEW",
//                                android.net.Uri.parse(
//                                        "androidamap://route?sourceApplication=应用名称" + "&dlat="+ 31.1198723//终点的经度
//                                                + "&dlon="+ 121.1099877//终点的纬度
//                                                +"&dname=终点地名"
//                                                + "&dev=0" + "&t=1"));
//                        startActivity(intent);
//                    } else {
//                        Toast.makeText(MainActivity.this, "没有安装百度地图客户端，请先下载该地图应用", Toast.LENGTH_SHORT).show();
//                    }
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }



            }
        });


        mViewPager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
            @NonNull
            @Override
            public Fragment getItem(int position) {
                return tabFragmentList.get(position);
            }

            @Override
            public int getCount() {
                return tabFragmentList.size();
            }

            @Nullable
            @Override
            public CharSequence getPageTitle(int position) {
                return tabs[position];
            }
        });
        tabLayout.setupWithViewPager(mViewPager, false);
        mViewPager.setPageMargin(20);
        mViewPager.setOffscreenPageLimit(tabs.length);
        mViewPager.setPageTransformer(true, new ZoomOutPageTransformer());//设置画廊模式
        mViewPager.setCurrentItem(0);
        ll_layout.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                return mViewPager.dispatchTouchEvent(motionEvent);
            }
        });
    }
}