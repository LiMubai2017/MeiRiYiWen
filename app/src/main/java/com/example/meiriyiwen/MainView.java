package com.example.meiriyiwen;

import android.content.Context;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.widget.ImageView;


import com.bumptech.glide.Glide;
import com.example.meiriyiwen.view.FirstFragment;
import com.example.meiriyiwen.presenter.MyPageAdapter;
import com.example.meiriyiwen.view.SecondFragment;
import com.example.meiriyiwen.view.ThirdFragment;

import java.util.ArrayList;
import java.util.List;

public class MainView extends AppCompatActivity {
    private ViewPager viewPager;
    private MyPageAdapter pageAdapter;
    private List<Fragment> fragments;
    private Toolbar toolbar;
    private TabLayout tabLayout;
    static ImageView hint1;
    static private Context context ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_view);
        context = getApplicationContext();
        initRes();
    }

    private void initRes() {
        List<String> tabs = new ArrayList<String>();
        fragments = new ArrayList<Fragment>();
        LayoutInflater inflater = getLayoutInflater();
        viewPager = findViewById(R.id.viewpager);
        toolbar = findViewById(R.id.toolbar);
        tabLayout = findViewById(R.id.tablayout);
        hint1 = findViewById(R.id.hintview_first_imageview);

        toolbar.setTitle("每日一文");

        fragments.add(new FirstFragment());
        fragments.add(new SecondFragment());
        fragments.add(new ThirdFragment());

        tabs.add("文章");
        tabs.add("声音");
        tabs.add("书架");
        pageAdapter = new MyPageAdapter(getSupportFragmentManager(), fragments,tabs);
        viewPager.setAdapter(pageAdapter);
        tabLayout.setupWithViewPager(viewPager);

        Log.d("package name:",getPackageName());
    }

}
