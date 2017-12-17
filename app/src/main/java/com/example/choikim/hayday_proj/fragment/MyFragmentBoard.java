package com.example.choikim.hayday_proj.fragment;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.example.choikim.hayday_proj.R;

/**
 * Created by khy12 on 2017-12-17.
 */

public class MyFragmentBoard extends Fragment {
    public MyFragmentBoard(){
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_my,container,false);
        return view;
    }
}
