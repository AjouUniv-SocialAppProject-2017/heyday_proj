package com.example.choikim.hayday_proj.fragment;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;


import com.example.choikim.hayday_proj.ClassCreateActivity;
import com.example.choikim.hayday_proj.MainActivity;
import com.example.choikim.hayday_proj.R;

/**
 * Created by jamti on 2017-11-19.
 */

public class MyFragment extends Fragment {


    public MyFragment () {

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        final MainActivity main = (MainActivity) getActivity();
        ImageButton create_class = (ImageButton)main.findViewById(R.id.create_class);

        create_class.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(main,ClassCreateActivity.class);
                startActivity(intent);
            }
        });


    }

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_my, container, false);


    }
}


