package com.example.choikim.hayday_proj.fragment;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.choikim.hayday_proj.R;

/**
 * Created by khy12 on 2017-12-17.
 */

public class MyFragmentClass extends Fragment {
    public MyFragmentClass(){
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_class,container,false);
        return view;
    }
}
