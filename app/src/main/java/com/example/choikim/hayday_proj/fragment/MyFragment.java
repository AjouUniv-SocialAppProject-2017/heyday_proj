package com.example.choikim.hayday_proj.fragment;

import android.app.ActionBar;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
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
import android.widget.RelativeLayout;
import android.widget.TextView;


import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.choikim.hayday_proj.ClassCreateActivity;
import com.example.choikim.hayday_proj.MainActivity;
import com.example.choikim.hayday_proj.MakeSurveyActivity;
import com.example.choikim.hayday_proj.R;
import com.example.choikim.hayday_proj.loginAcitivy.LoginActivity;
import com.example.choikim.hayday_proj.model.UserModel;
import com.example.choikim.hayday_proj.fragment.MyFragmentBoard;
import com.example.choikim.hayday_proj.fragment.MyFragmentClass;
import com.example.choikim.hayday_proj.tool.HeightWrappingViewPager;
import com.facebook.login.LoginManager;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

/**
 * Created by jamti on 2017-11-19.
 */

public class MyFragment extends Fragment {

    public ImageView userProfile;
    public ImageView userBackground;
    public TextView userName;
    public ImageView btnCallPopup;
    public Button btnLogOut;

    public HeightWrappingViewPager viewPager;
    public LinearLayout linearlayoutViewPager;
    public Button btnMyBoard;
    public Button btnMyClass;

    private FirebaseAuth auth;

    public UserModel currentUser=new UserModel();

    public MyFragment () {

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        final MainActivity main = (MainActivity) getActivity();

        auth=FirebaseAuth.getInstance();

        btnCallPopup = (ImageButton)main.findViewById(R.id.call_popup);
        userName=(TextView)main.findViewById(R.id.textView_my_fragment_name);
        userProfile=(ImageView)main.findViewById(R.id.imageView_my_fragment_profile);
        btnLogOut=(Button)main.findViewById(R.id.btn_my_fagment_logout);

        //view pager item
        viewPager=(HeightWrappingViewPager)main.findViewById(R.id.viewpager_my_fragment);
        btnMyBoard=(Button)main.findViewById(R.id.btn_my_board);
        btnMyClass=(Button)main.findViewById(R.id.btn_my__class);
        linearlayoutViewPager=(LinearLayout)main.findViewById(R.id.linearlayout_viewpager_my_fragment);



        //popup menu on click listener
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
                                        Intent intent1 = new Intent(main, MakeSurveyActivity.class);
                                        startActivity(intent1);
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

        //firebase current user data
        FirebaseDatabase.getInstance().getReference().child("users").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot snapshot:dataSnapshot.getChildren()){
                    UserModel user = snapshot.getValue(UserModel.class);
                    if((user.uid).equals(auth.getInstance().getCurrentUser().getUid().toString())==true){
                            currentUser.uid=user.uid;
                            currentUser.profileImagePath=user.profileImagePath;
                            currentUser.name=user.name;
                            currentUser.email=user.email;
                            currentUser.gender=user.gender;
                            currentUser.backgroundImagePath=user.backgroundImagePath;
                            currentUser.age=user.age;
                            currentUser.introduce=user.introduce;
                        Log.i("username", currentUser.name+" "+currentUser.profileImagePath);

                        userName.setText(currentUser.name);

                        Glide.with(getActivity())
                                .load(currentUser.profileImagePath)
                                .apply(new RequestOptions().circleCrop())
                                .into(userProfile);

                    }
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });//current user data base 가져오기

        btnLogOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                auth.signOut();
                LoginManager.getInstance().logOut();
                main.finish();
                Intent intent=new Intent(getActivity(), LoginActivity.class);
                startActivity(intent);
            }
        });


        //view pager
        viewPager.setAdapter(new pagerAdapter(getChildFragmentManager()));
        viewPager.setCurrentItem(0);

        btnMyClass.setOnClickListener(movePageListener);
        btnMyClass.setTag(1);
        btnMyBoard.setOnClickListener(movePageListener);
        btnMyBoard.setTag(0);


        viewPager.measure(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.WRAP_CONTENT);

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

                //resizePage(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

//        int viewpagerWidth=viewPager.getWidth();
//        int viewpagerHeight=viewPager.getHeight();
//
//        Log.i("heightviewpager", String.valueOf(viewpagerHeight));
        //LinearLayout.LayoutParams params=new LinearLayout.LayoutParams(viewpagerWidth,viewpagerHeight);
        //linearlayoutViewPager.setLayoutParams(params);
    }
        public void resizePage(int position){
        View view = viewPager.findViewById(position);
        if(view==null)
            return;

        view.measure(ViewPager.LayoutParams.MATCH_PARENT, ViewPager.LayoutParams.WRAP_CONTENT);
        int height=view.getMeasuredHeight();
        int width=view.getMeasuredWidth();

            Log.i("sizesize", height+" "+width);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(width,height);
            viewPager.setLayoutParams(params);
        }

    View.OnClickListener movePageListener = new View.OnClickListener(){
        @Override
        public void onClick(View view) {
            int tag=(int)view.getTag();
            viewPager.setCurrentItem(tag);
        }
    };


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_my, container, false);
        return view;
    }


    private class pagerAdapter extends FragmentStatePagerAdapter {
        public pagerAdapter(android.support.v4.app.FragmentManager fragment) {
            super(fragment);
        }

        @Override
        public android.support.v4.app.Fragment getItem(int position) {

            switch (position) {
                case 0:
                    return new MyFragmentBoard();
                case 1:
                    return new MyFragmentClass();
                default:
                return null;
            }
        }

        @Override
        public int getCount() {
            return 2;
        }
    }
}

