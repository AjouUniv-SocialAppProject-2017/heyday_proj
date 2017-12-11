package com.example.choikim.hayday_proj.fragment;

import android.graphics.drawable.Drawable;
import android.support.v4.app.Fragment;;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import com.example.choikim.hayday_proj.BoardCommentActivity;
import com.example.choikim.hayday_proj.MakeBoardActivity;
import com.example.choikim.hayday_proj.R;
import com.example.choikim.hayday_proj.model.BoardModel;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by jamti on 2017-11-19.
 */

public class BoardFragment extends Fragment{

    private RecyclerView recyclerView;
    ImageButton btnMakeBoard;
    private DatabaseReference mDatabase;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_board,container,false);
        btnMakeBoard=(ImageButton)view.findViewById(R.id.btn_board_write);
        recyclerView=(RecyclerView)view.findViewById(R.id.BoradFragment_recyclerview);

        btnMakeBoard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), MakeBoardActivity.class);
                startActivity(intent);
            }
        });
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(new RecyclerViewAdapter());


        return view;
    }

    class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
        List<BoardModel> boardModels;

        RecyclerViewAdapter(){
            boardModels=new ArrayList<>();
            FirebaseDatabase.getInstance().getReference().child("boards").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    boardModels.clear();
                    for(DataSnapshot snapshot:dataSnapshot.getChildren()){
                        BoardModel boardModel=snapshot.getValue(BoardModel.class);
                        boardModels.add(boardModel);
                    }
                    Collections.reverse(boardModels);
                    notifyDataSetChanged();
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        }

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_board,parent,false);
            return new BoardViewHolder(view);
        }

        @Override
        public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {

            mDatabase=FirebaseDatabase.getInstance().getReference();

            ((BoardViewHolder)holder).textView_context.setText(boardModels.get(position).context);
            ((BoardViewHolder)holder).textView_wTime.setText(boardModels.get(position).wTime);
            ((BoardViewHolder)holder).textView_name.setText(boardModels.get(position).name);

            Glide.with(holder.itemView.getContext())
                    .load(boardModels.get(position).imagePath)
                    .apply(new RequestOptions().circleCrop())
                    .into(((BoardViewHolder)holder).imageView_profile);

            //board comment view listener
            ((BoardViewHolder)holder).btn_make_comment.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    //make board comment view
                    //send board key data to board comment activity
                    final DatabaseReference ref=mDatabase.child("boards");
                    Intent intent = new Intent(getActivity(), BoardCommentActivity.class);
                    intent.putExtra("key",boardModels.get(position).boardUid);
                    startActivity(intent);
                }
            });


            //board like button listener
            ((BoardViewHolder)holder).btn_like.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                }
            });

        }

        @Override
        public int getItemCount() {
            return boardModels.size();
        }

        public class BoardViewHolder extends RecyclerView.ViewHolder{
            public TextView textView_context;
            public TextView textView_wTime;
            public TextView textView_name;
            public ImageView imageView_profile;
            public Button btn_like;
            public Button btn_make_comment;

            public BoardViewHolder(View view) {
                super(view);
                imageView_profile=(ImageView)view.findViewById(R.id.imageView_item_board_profile);
                textView_context=(TextView)view.findViewById(R.id.board_item_context);
                textView_name=(TextView)view.findViewById(R.id.board_item_name);
                textView_wTime=(TextView)view.findViewById(R.id.board_item_date);
                btn_like = (Button)view.findViewById(R.id.btn_item_board_like);
                btn_make_comment = (Button)view.findViewById(R.id.btn_item_board_comment);
            }
        }
    }
}
