package com.example.choikim.hayday_proj;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.choikim.hayday_proj.model.ClassModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ClassBestActivity extends AppCompatActivity {
    private RecyclerView recyclerView;


    private FirebaseAuth auth;
    private FirebaseDatabase database;

    private List<ClassModel> classdata = new ArrayList<>();
    private List<ClassModel> searchdata = new ArrayList<>();

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_class_new);

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
                        searchdata.add(classmodel);
                }
                classRecyclerViewAdapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        final EditText search_class = (EditText)findViewById(R.id.search_class);
        search_class.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                classdata.clear();
                for(int i=0; i<searchdata.size(); i++){
                    if(searchdata.get(i).class_name.contains(s)){
                        classdata.add(searchdata.get(i));
                    }
                }
                classRecyclerViewAdapter.notifyDataSetChanged();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


    }

    class ClassRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {


        private class CustomViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

            ImageView class_image;
            TextView class_teacher;
            TextView class_name;

            public CustomViewHolder(View view){
                super(view);
                class_image = (ImageView) view.findViewById(R.id.class_image);
                class_teacher = (TextView) view.findViewById(R.id.class_teacher);
                class_name = (TextView) view.findViewById(R.id.class_name);
                view.setOnClickListener(this);
            }

            @Override
            public void onClick(View view) {
                System.out.println(getPosition());
                Intent intent = new Intent (view.getContext(),ClassViewActivity.class);
                intent.putExtra("value",class_name.getText().toString());
                view.getContext().startActivity(intent);
            }
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

            ((ClassBestActivity.ClassRecyclerViewAdapter.CustomViewHolder)holder).class_teacher.setText(classdata.get(position).user_name);
            ((ClassBestActivity.ClassRecyclerViewAdapter.CustomViewHolder)holder).class_name.setText(classdata.get(position).class_name);
            Glide.with(holder.itemView.getContext()).load(classdata.get(position).class_image).into(((ClassBestActivity.ClassRecyclerViewAdapter.CustomViewHolder)holder).class_image);
        }

        @Override
        public int getItemCount() {
            return classdata.size();
        }



        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(final ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_class,parent,false);
            return new ClassBestActivity.ClassRecyclerViewAdapter.CustomViewHolder(view);
        }



    }
}
