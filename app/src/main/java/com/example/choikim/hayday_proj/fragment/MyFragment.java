package com.example.choikim.hayday_proj.fragment;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.TextView;


import com.example.choikim.hayday_proj.ClassCreateActivity;
import com.example.choikim.hayday_proj.MainActivity;
import com.example.choikim.hayday_proj.R;
import com.google.firebase.auth.FirebaseAuth;

/**
 * Created by jamti on 2017-11-19.
 */

public class MyFragment extends Fragment {

    public ImageView userProfile;
    public ImageView userBackground;
    public TextView userName;
    public ImageView btnCallPopup;

    FirebaseAuth auth;

    public MyFragment () {

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        final MainActivity main = (MainActivity) getActivity();
        btnCallPopup = (ImageButton)main.findViewById(R.id.call_popup);

        btnCallPopup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intent = new Intent(main,ClassCreateActivity.class);
//                startActivity(intent);

                switch (v.getId()){
                    case R.id.call_popup:
                        PopupMenu popup=new PopupMenu(getActivity(),v);
                        MenuInflater inflater = popup.getMenuInflater();
                        inflater.inflate(R.menu.menu_popup_fragment_my,popup.getMenu());
                        popup.show();

                        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                            @Override
                            public boolean onMenuItemClick(MenuItem menuItem) {
                                switch (menuItem.getItemId()){
                                    case R.id.create_class:
                                        Intent intent = new Intent(main,ClassCreateActivity.class);
                                        startActivity(intent);
                                        break;
                                    case R.id.create_investigate_board:
                                        break;
                                    default:
                                        break;
                                }
                                return false;
                            }
                        });
                        break;
                }
            }
        });//popup menu end


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

