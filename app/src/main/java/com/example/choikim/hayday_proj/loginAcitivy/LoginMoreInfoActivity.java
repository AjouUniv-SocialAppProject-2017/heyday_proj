package com.example.choikim.hayday_proj.loginAcitivy;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.choikim.hayday_proj.MainActivity;
import com.example.choikim.hayday_proj.R;
import com.example.choikim.hayday_proj.fragment.BoardFragment;
import com.example.choikim.hayday_proj.model.UserModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by khy12 on 2017-12-15.
 */

public class LoginMoreInfoActivity extends Activity{
    public ImageView profile;
    public TextView name;
    public TextView email;
    public EditText age;
    public RadioGroup btnGender;
    public EditText location;
    public EditText introduce;
    public LinearLayout finish;
    public FirebaseAuth auth;
    public RadioGroup rgGender;
    public RadioButton rbGender;

    DatabaseReference userDatabase;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_moreinfo);

        userDatabase= FirebaseDatabase.getInstance().getReference();

        profile=(ImageView)findViewById(R.id.imageView_login_moreinfo_profile);
        name=(TextView)findViewById(R.id.textView_login_moreinfo_name);
        email=(TextView)findViewById(R.id.textView_login_moreinfo_email);
        age=(EditText)findViewById(R.id.editText_login_moreinfo_age);
        location=(EditText)findViewById(R.id.editText_login_moreinfo_local);
        introduce=(EditText)findViewById(R.id.editText_login_moreinfo_intro);
        finish=(LinearLayout)findViewById(R.id.btn_login_moreinfo_next);

        rgGender=(RadioGroup)findViewById(R.id.radiogroup_login_moreinfo_gender);



        //set profile image
        Glide.with(this)
                .load(auth.getInstance().getCurrentUser().getPhotoUrl().toString())
                .apply(new RequestOptions().circleCrop())
                .into(profile);

        //edit name,email data from firebase auth
        name.setText(auth.getInstance().getCurrentUser().getDisplayName());
        email.setText(auth.getInstance().getCurrentUser().getEmail());

        finish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //user data upload
                userDataUpload();

                // activity change loginmore info -> main activity
                Intent intent = new Intent(LoginMoreInfoActivity.this, MainActivity.class);
                startActivity(intent);

            }
        });
    }

    private void userDataUpload(){
        UserModel userModel=new UserModel();

        userModel.email=auth.getInstance().getCurrentUser().getEmail().toString();
        userModel.name=auth.getInstance().getCurrentUser().getDisplayName();
        userModel.uid=auth.getInstance().getCurrentUser().getUid();

        userModel.profileImagePath=auth.getInstance().getCurrentUser().getPhotoUrl().toString();
        userModel.location=location.getText().toString();
        userModel.introduce=introduce.getText().toString();
        userModel.age=Long.parseLong(age.getText().toString());

        //radio button data
        rbGender=(RadioButton)findViewById(rgGender.getCheckedRadioButtonId());
        userModel.gender=rbGender.getText().toString();

        //firebase upload
        userDatabase.child("users").push().setValue(userModel);

    }
}
