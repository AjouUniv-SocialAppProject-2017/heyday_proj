package com.example.choikim.hayday_proj;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.choikim.hayday_proj.model.ClassModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jamti on 2017-11-30.
 */

public class ClassActivity extends AppCompatActivity{

    private RecyclerView recyclerView;

    private FirebaseAuth auth;
    private FirebaseDatabase database;

    private List<ClassModel> classdata = new ArrayList<>();
    private List<String> uidList = new ArrayList<>();

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_class);


        ImageButton btn_create_class = (ImageButton)findViewById(R.id.create_class);
        btn_create_class.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent = new Intent (ClassActivity.this,ClassCreateActivity.class);
                startActivity(intent);
            }

        });


        auth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();

        recyclerView  = (RecyclerView)findViewById(R.id.recycle_view);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        final ClassRecyclerViewAdapter classRecyclerViewAdapter = new ClassRecyclerViewAdapter();
        recyclerView.setAdapter(classRecyclerViewAdapter);

        database.getReference().child("Class").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                classdata.clear();
                for(DataSnapshot snapshot : dataSnapshot.getChildren()){
                    ClassModel classmodel = snapshot.getValue(ClassModel.class);
                    classdata.add(classmodel);
                }
                classRecyclerViewAdapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    class ClassRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_class,parent,false);
            return new CustomViewHolder(view);
        }

        private class CustomViewHolder extends RecyclerView.ViewHolder{
            ImageView class_image;
            TextView class_teacher;
            TextView class_name;

            public CustomViewHolder(View view){
                super(view);
                class_image = (ImageView) view.findViewById(R.id.class_image);
                class_teacher = (TextView) view.findViewById(R.id.class_teacher);
                class_name = (TextView) view.findViewById(R.id.class_name);
            }
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

            ((CustomViewHolder)holder).class_teacher.setText(classdata.get(position).user_name);
            ((CustomViewHolder)holder).class_name.setText(classdata.get(position).class_name);

            Glide.with(holder.itemView.getContext()).load(classdata.get(position).class_image).into(((CustomViewHolder)holder).class_image);
        }

        @Override
        public int getItemCount() {
            return classdata.size();
        }
    }

}