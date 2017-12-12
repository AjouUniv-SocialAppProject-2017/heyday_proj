package com.example.choikim.hayday_proj;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.choikim.hayday_proj.model.ClassModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.MutableData;
import com.google.firebase.database.Transaction;
import com.google.firebase.database.ValueEventListener;

public class ClassViewActivity extends AppCompatActivity {

    private FirebaseAuth auth;
    private FirebaseDatabase database;

    private String uid;
    int temp = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_class_view);

        Intent intent = getIntent();
        final String class_name = intent.getExtras().getString("value");

        auth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();

        database.getReference().child("Class").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    ClassModel classmodel = snapshot.getValue(ClassModel.class);
                    if(class_name.equals(classmodel.class_name)==true){
                        setClassdata(classmodel);
                        uid = snapshot.getKey();
                    }
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


    }

    public void setClassdata(ClassModel classdata) {
        TextView class_name;
        class_name = (TextView) findViewById(R.id.class_name);
        class_name.setText(classdata.class_name);

        TextView class_content;
        class_content = (TextView) findViewById(R.id.class_content);
        class_content.setText(classdata.class_content);

        TextView class_local;
        class_local = (TextView) findViewById(R.id.class_local);
        class_local.setText(classdata.class_local);

        TextView class_time;
        class_time = (TextView) findViewById(R.id.class_time);
        class_time.setText(classdata.class_time);

        TextView class_teacher_name;
        class_teacher_name =(TextView) findViewById(R.id.class_teacher_name);
        class_teacher_name.setText(classdata.user_name);

        TextView class_teacher_mail;
        class_teacher_mail = (TextView) findViewById(R.id.class_teacher_mail);
        class_teacher_mail.setText(classdata.userId);

        ImageView class_image;
        class_image = (ImageView)findViewById(R.id.class_image);
        Glide.with(this).load(classdata.class_image).into(class_image);

        ImageView teacher_image;
        teacher_image = (ImageView)findViewById(R.id.class_teacher);
        Glide.with(this).load(classdata.imagePath).into(teacher_image);

        ImageView class_in;
        class_in = (ImageView)findViewById(R.id.class_in);
        class_in.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onStarClicked(database.getReference().child("Class").child(uid));
                if(temp == 0) {
                    temp = temp + 1;
                    Toast.makeText(ClassViewActivity.this, "강의등록이 완료되었습니다.", Toast.LENGTH_LONG).show();
                }
                else{
                    temp = temp - 1;
                    Toast.makeText(ClassViewActivity.this,"강의등록이 취소되었습니다.",Toast.LENGTH_LONG).show();
                }
            }
        });

    }

    private void onStarClicked(DatabaseReference postRef) {
        postRef.runTransaction(new Transaction.Handler() {
            @Override
            public Transaction.Result doTransaction(MutableData mutableData) {
                ClassModel p = mutableData.getValue(ClassModel.class);
                if (p == null) {
                    return Transaction.success(mutableData);
                }

                if (p.participant.containsKey(auth.getCurrentUser().getUid())) {
                    // Unstar the post and remove self from stars
                    p.starCount = p.starCount - 1;
                    p.participant.remove(auth.getCurrentUser().getUid());
                } else {
                    // Star the post and add self to stars
                    p.starCount = p.starCount + 1;
                    p.participant.put(auth.getCurrentUser().getUid(), auth.getCurrentUser().getUid());
                }

                // Set value and report transaction success
                mutableData.setValue(p);
                return Transaction.success(mutableData);
            }

            @Override
            public void onComplete(DatabaseError databaseError, boolean b,
                                   DataSnapshot dataSnapshot) {
            }
        });
    }




}
