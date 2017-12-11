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
