package com.example.choikim.hayday_proj.fragment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;


import com.example.choikim.hayday_proj.HealthActivity;
import com.example.choikim.hayday_proj.R;

import static com.facebook.FacebookSdk.getApplicationContext;


/**
 * Created by jamti on 2017-11-19.
 */

public class ClassFragment extends Fragment {



    public ClassFragment() {

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_class, container, false);

    }

}
