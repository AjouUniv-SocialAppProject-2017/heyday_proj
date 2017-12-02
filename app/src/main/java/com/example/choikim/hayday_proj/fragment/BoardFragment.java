package com.example.choikim.hayday_proj.fragment;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.choikim.hayday_proj.MakeBoardActivity;
import com.example.choikim.hayday_proj.R;
import com.example.choikim.hayday_proj.model.BoardModel;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


import java.util.ArrayList;
import java.util.List;

/**
 * Created by jamti on 2017-11-19.
 */

public class BoardFragment extends Fragment{

    private RecyclerView recyclerView;
    ImageButton btnMakeBoard;
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

        return view;
    }

    class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

        List<BoardModel> boardModels;
        public RecyclerViewAdapter(){
            boardModels=new ArrayList<>();
            FirebaseDatabase.getInstance().getReference().child("boards").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    boardModels.clear();
                    for(DataSnapshot snapshot:dataSnapshot.getChildren()){
                        BoardModel boardModel=snapshot.getValue(BoardModel.class);
                        boardModels.add(boardModel);
                    }
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
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
            ((BoardViewHolder)holder).textView_context.setText(boardModels.get(position).context);
            ((BoardViewHolder)holder).textView_wTime.setText(boardModels.get(position).wTime);
            ((BoardViewHolder)holder).textView_name.setText(boardModels.get(position).name);
        }

        @Override
        public int getItemCount() {
            return boardModels.size();
        }

        private class BoardViewHolder extends RecyclerView.ViewHolder {
            public TextView textView_context;
            public TextView textView_wTime;
            public TextView textView_name;

            public BoardViewHolder(View view) {
                super(view);
                textView_context=(TextView)view.findViewById(R.id.board_item_context);
                textView_name=(TextView)view.findViewById(R.id.board_item_name);
                textView_wTime=(TextView)view.findViewById(R.id.board_item_date);

            }
        }
    }
}
