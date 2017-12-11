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
import com.example.choikim.hayday_proj.MainActivity;
import com.example.choikim.hayday_proj.R;


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

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        final MainActivity activity = (MainActivity)getActivity();

        Button btn_health_beauty = (Button)activity.findViewById(R.id.btn_health_beauty);
        btn_health_beauty.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(activity,ClassActivity.class);
                intent.putExtra("value","1");
                startActivity(intent);
            }
        });
        Button btn_foregin = (Button)activity.findViewById(R.id.btn_foregin);
        btn_foregin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(activity,ClassActivity.class);
                intent.putExtra("value","2");
                startActivity(intent);
            }
        });
        Button btn_computer = (Button)activity.findViewById(R.id.btn_computer);
        btn_computer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(activity,ClassActivity.class);
                intent.putExtra("value","3");
                startActivity(intent);
            }
        });
        Button btn_music_art = (Button)activity.findViewById(R.id.btn_music_art);
        btn_music_art.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(activity,ClassActivity.class);
                intent.putExtra("value","4");
                startActivity(intent);
            }
        });
        Button btn_social_issue = (Button)activity.findViewById(R.id.btn_social_issue);
        btn_social_issue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(activity,ClassActivity.class);
                intent.putExtra("value","5");
                startActivity(intent);
            }
        });
        Button btn_sport = (Button)activity.findViewById(R.id.btn_sport);
        btn_sport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(activity,ClassActivity.class);
                intent.putExtra("value","6");
                startActivity(intent);
            }
        });
        Button btn_hobby = (Button)activity.findViewById(R.id.btn_hobby);
        btn_hobby.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(activity,ClassActivity.class);
                intent.putExtra("value","7");
                startActivity(intent);
            }
        });
        Button btn_all = (Button)activity.findViewById(R.id.btn_all);
        btn_all.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(activity,ClassActivity.class);
                intent.putExtra("value","8");
                startActivity(intent);
            }
        });

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_class, container, false);
    }



}
