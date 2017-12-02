package com.example.choikim.hayday_proj;

import android.os.Bundle;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.app.FragmentManager;


import com.example.choikim.hayday_proj.fragment.BoardFragment;
import com.example.choikim.hayday_proj.fragment.ClassFragment;
import com.example.choikim.hayday_proj.fragment.MyFragment;
import com.example.choikim.hayday_proj.fragment.RecommendFragment;


public class MainActivity extends AppCompatActivity {

    ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        viewPager = (ViewPager)findViewById(R.id.viewpager);
        ImageButton btn_class = (ImageButton)findViewById(R.id.btn_class);
        ImageButton btn_board = (ImageButton)findViewById(R.id.btn_board);
        ImageButton btn_recommend = (ImageButton)findViewById(R.id.btn_recommend);
        ImageButton btn_my = (ImageButton)findViewById(R.id.btn_my);

        viewPager.setAdapter(new pagerAdapter(getSupportFragmentManager()));
        viewPager.setCurrentItem(0);

        btn_class.setOnClickListener(movePageListener);
        btn_class.setTag(0);
        btn_board.setOnClickListener(movePageListener);
        btn_board.setTag(1);
        btn_recommend.setOnClickListener(movePageListener);
        btn_recommend.setTag(2);
        btn_my.setOnClickListener(movePageListener);
        btn_my.setTag(3);
    }

    View.OnClickListener movePageListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            int tag = (int) v.getTag();
            viewPager.setCurrentItem(tag);
        }
    };

    private class pagerAdapter extends FragmentStatePagerAdapter
    {
        public pagerAdapter(android.support.v4.app.FragmentManager fragment) {
            super(fragment);
        }
        @Override
        public android.support.v4.app.Fragment getItem(int position) {
            switch(position) {
                case 0:
                    return new ClassFragment();
                case 1:
                    return new BoardFragment();
                case 2:
                    return new RecommendFragment();
                case 3:
                    return new MyFragment();
                default:
                    return null;
            }
        }


        @Override
        public int getCount()
        {
            return 4;
        }
    }
}


