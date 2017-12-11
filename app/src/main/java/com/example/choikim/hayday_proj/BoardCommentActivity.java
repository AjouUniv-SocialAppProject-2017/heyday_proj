package com.example.choikim.hayday_proj;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.choikim.hayday_proj.model.BoardModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;

import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Created by khy12 on 2017-12-05.
 */

public class BoardCommentActivity extends Activity{
    public ImageButton btnBackSpace;
    public EditText editTextContext;
    public ImageButton btnMakeComment;
    public String boardKey;


    //for upload
    private FirebaseStorage storage;
    private FirebaseAuth auth;
    private DatabaseReference mDatabase= FirebaseDatabase.getInstance().getReference();;

    //time stamp function
    long now = System.currentTimeMillis();
    Date date = new Date(now);
    SimpleDateFormat sdfNow=new SimpleDateFormat("yyy/MM/dd hh:mm");
    String formatDate = sdfNow.format(date);


    //for recycler view
    private RecyclerView commentRecyclerView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_board_comment);
        btnBackSpace =(ImageButton)findViewById(R.id.btn_comment_activity_backbutton);
        btnMakeComment = (ImageButton)findViewById(R.id.btn_comment_activity_make_comment);
        editTextContext=(EditText)findViewById(R.id.editText_comment_ativity);



        Intent intent = getIntent();
        boardKey = intent.getStringExtra("key");

        //recycler view
        commentRecyclerView=(RecyclerView)findViewById(R.id.board_comment_activity_recyclerview);
        commentRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        commentRecyclerView.setAdapter(new CommentRecyclerViewAdapter());


        btnBackSpace.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        btnMakeComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                upload();
                editTextContext.setText("");

            }
        });
    }

    //upload
    private void upload(){
        BoardModel.Comment commentModel = new BoardModel.Comment();
        commentModel.imagePath=auth.getInstance().getCurrentUser().getPhotoUrl().toString();
        commentModel.name=auth.getInstance().getCurrentUser().getDisplayName();
        commentModel.wTime=formatDate;
        commentModel.message=editTextContext.getText().toString();
        commentModel.uid=auth.getInstance().getCurrentUser().getUid();
        mDatabase.child("boards").child(boardKey).child("comments").push().setValue(commentModel);
    }



    //Recycler view adapter class
    class CommentRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
        List<BoardModel.Comment> commentsModels;

        CommentRecyclerViewAdapter(){
            commentsModels=new ArrayList<>();
            FirebaseDatabase.getInstance().getReference()
                    .child("boards").child(boardKey).child("comments").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    commentsModels.clear();
                    for(DataSnapshot snapshot:dataSnapshot.getChildren()){
                        BoardModel.Comment comment = snapshot.getValue(BoardModel.Comment.class);
                        commentsModels.add(comment);
                    }
                    notifyDataSetChanged();
                    Map<String,Object> childUpdates =new HashMap<>();
                    childUpdates.put("/boards/"+boardKey+"/cntComment/",commentsModels.size());
                    mDatabase.updateChildren(childUpdates);
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        }


        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_comment,parent,false);


            return new CommentViewHolder(view);
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

            ((CommentViewHolder)holder).textView_wTime.setText(commentsModels.get(position).wTime);
            ((CommentViewHolder)holder).textView_name.setText(commentsModels.get(position).name);
            ((CommentViewHolder)holder).textView_context.setText(commentsModels.get(position).message);

            Glide.with(holder.itemView.getContext())
                    .load(commentsModels.get(position).imagePath)
                    .apply(new RequestOptions().circleCrop())
                    .into(((CommentViewHolder)holder).imageView_profile);
        }

        @Override
        public int getItemCount() {
            return commentsModels.size();
        }

        private class CommentViewHolder extends RecyclerView.ViewHolder {
            public TextView textView_name;
            public TextView textView_wTime;
            public TextView textView_context;
            public ImageView imageView_profile;

            public CommentViewHolder(View view) {
                super(view);
                textView_context=(TextView)view.findViewById(R.id.textView_item_comment_context);
                textView_name=(TextView)view.findViewById(R.id.textView_item_comment_name);
                textView_wTime=(TextView)view.findViewById(R.id.textView_item_comment_wTime);
                imageView_profile=(ImageView)view.findViewById(R.id.imageView_item_comment_profile);
            }
        }
    }

}
