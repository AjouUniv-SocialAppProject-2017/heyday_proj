package com.example.choikim.hayday_proj.fragment;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.choikim.hayday_proj.R;
import com.example.choikim.hayday_proj.model.BoardModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by khy12 on 2017-12-17.
 */

public class MyFragmentBoard extends Fragment {

    private RecyclerView recyclerView;
    private DatabaseReference mDatabase;
    private FirebaseAuth auth;
    private FirebaseDatabase database;

    public MyFragmentBoard(){
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_my_board,container,false);

        auth=FirebaseAuth.getInstance();
        recyclerView=(RecyclerView)view.findViewById(R.id.myfragment_board_recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(new SurveyRecyclerViewAdapter());

        return view;
    }

    private class SurveyRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

        List<BoardModel> boardModels;
        SurveyRecyclerViewAdapter(){
            boardModels=new ArrayList<>();

            FirebaseDatabase.getInstance().getReference().child("boards").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    boardModels.clear();

                    for(DataSnapshot snapshot:dataSnapshot.getChildren()){
                        BoardModel boardModel = snapshot.getValue(BoardModel.class);
                        if((boardModel.uid).equals(auth.getCurrentUser().getUid())){
                            boardModels.add(boardModel);
                        }
                    }

                    Log.i("checkmodel", String.valueOf(boardModels.size()));
                    //Collections.reverse(boardModels);
                    notifyDataSetChanged();
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });

        }

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_survey,parent,false);
            return new SurveyViewHolder(view);
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
            mDatabase=FirebaseDatabase.getInstance().getReference();
            final String boardKey = boardModels.get(position).boardUid;

            ((SurveyViewHolder)holder).textView_context.setText(boardModels.get(position).context);
            ((SurveyViewHolder)holder).textView_wTime.setText(boardModels.get(position).wTime);
            ((SurveyViewHolder)holder).textView_name.setText(boardModels.get(position).name);
            ((SurveyViewHolder)holder).textView_like_cnt.setText(String.valueOf(boardModels.get(position).starCount));
            ((SurveyViewHolder)holder).textView_comment_cnt.setText(String.valueOf(boardModels.get(position).cntComment));


            Glide.with(holder.itemView.getContext())
                    .load(boardModels.get(position).imagePath)
                    .apply(new RequestOptions().circleCrop())
                    .into(((SurveyViewHolder)holder).imageView_profile);
            if((boardModels.get(position).flag).equals("1")){
                ((SurveyViewHolder)holder).btnMakeClass.setVisibility(View.INVISIBLE);
            }

            ((SurveyViewHolder)holder).btnMakeClass.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                }
            });



        }

        @Override
        public int getItemCount() {
            return boardModels.size();
        }
    }

    public class SurveyViewHolder extends RecyclerView.ViewHolder {
        public TextView textView_context;
        public TextView textView_wTime;
        public TextView textView_name;
        public ImageView imageView_profile;
        public Button btn_like;
        public Button btn_make_comment;
        public TextView textView_comment_cnt;
        public TextView textView_like_cnt;
        public LinearLayout layout_item_board;
        public Button btnMakeClass;

        public SurveyViewHolder(View view) {
            super(view);
            imageView_profile=(ImageView)view.findViewById(R.id.imageView_item_survey_profile);
            textView_context=(TextView)view.findViewById(R.id.survey_item_context);
            textView_name=(TextView)view.findViewById(R.id.survey_item_name);
            textView_wTime=(TextView)view.findViewById(R.id.survey_item_date);
            btn_like = (Button)view.findViewById(R.id.btn_item_survey_like);
            btn_make_comment = (Button)view.findViewById(R.id.btn_item_survey_comment);
            textView_comment_cnt=(TextView)view.findViewById(R.id.textView_item_survey_commentCnt);
            textView_like_cnt=(TextView)view.findViewById(R.id.textView_item_survey_likeCnt);
            layout_item_board=(LinearLayout)view.findViewById(R.id.item_survey_layout);
            btnMakeClass=(Button)view.findViewById(R.id.btn_make_class);

        }
    }
}
