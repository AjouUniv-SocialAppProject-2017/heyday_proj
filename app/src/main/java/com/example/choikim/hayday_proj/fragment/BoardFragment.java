package com.example.choikim.hayday_proj.fragment;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.example.choikim.hayday_proj.MakeBoardActivity;
import com.example.choikim.hayday_proj.R;

/**
 * Created by jamti on 2017-11-19.
 */

public class BoardFragment extends Fragment implements View.OnClickListener{
    ImageButton btnMakeBoard;

    public BoardFragment() {

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_board,container,false);

        btnMakeBoard=(ImageButton)view.findViewById(R.id.btn_board_write);

        btnMakeBoard.setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick(View v){
        Intent intent = new Intent(getActivity(), MakeBoardActivity.class);
        startActivity(intent);
        
    }


}
