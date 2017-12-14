package com.example.choikim.hayday_proj.fragment;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.choikim.hayday_proj.ClassActivity;
import com.example.choikim.hayday_proj.ClassBestActivity;
import com.example.choikim.hayday_proj.ClassNewActivity;
import com.example.choikim.hayday_proj.MainActivity;
import com.example.choikim.hayday_proj.R;

/**
 * Created by jamti on 2017-11-19.
 */

public class RecommendFragment extends Fragment {
    public RecommendFragment() {

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_recommend,container,false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        final MainActivity activity = (MainActivity)getActivity();

        Button btn_new_class = (Button)activity.findViewById(R.id.btn_new_class);
        btn_new_class.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(activity,ClassNewActivity.class);
                intent.putExtra("value","1");
                startActivity(intent);
            }
        });

        Button btn_best_class = (Button)activity.findViewById(R.id.btn_best_class);
        btn_best_class.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(activity,ClassBestActivity.class);
                intent.putExtra("value","1");
                startActivity(intent);
            }
        });


    }
}
