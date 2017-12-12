package com.example.choikim.hayday_proj;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.choikim.hayday_proj.model.BoardModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;

import java.text.SimpleDateFormat;
import java.util.Date;


/**
 * Created by khy12 on 2017-11-21.
 */

public class MakeBoardActivity extends Activity {
    private FirebaseStorage storage;
    private FirebaseAuth auth;
    TextView userId;
    Button btnBack,btnMakeBoard;
    EditText contextText;

    long now=System.currentTimeMillis();
    Date date =new Date(now);
    SimpleDateFormat sdfNow=new SimpleDateFormat("yyyy/MM/dd HH:mm");
    String formatDate=sdfNow.format(date);

    private DatabaseReference mDatabase;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_make_board);

        storage=FirebaseStorage.getInstance();

        userId=(TextView)findViewById(R.id.user_id);
        btnBack=(Button)findViewById(R.id.btn_back);
        btnMakeBoard=(Button)findViewById(R.id.btn_make_board);
        contextText=(EditText)findViewById(R.id.edittext_make_board);

        btnMakeBoard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                upload();
                finish();
            }
        });

        //뒤로가기
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }

    //upload board
    private void upload(){
        BoardModel boardModel = new BoardModel();
        boardModel.context=contextText.getText().toString();
        boardModel.uid=auth.getInstance().getCurrentUser().getUid();
        boardModel.imagePath=auth.getInstance().getCurrentUser().getPhotoUrl().toString();
        boardModel.name=auth.getInstance().getCurrentUser().getDisplayName();
        boardModel.wTime=formatDate;
        boardModel.flag="1";
        boardModel.cntComment=0;
        boardModel.boardUid=FirebaseDatabase.getInstance().getReference().child("boards").push().getKey();
        Log.i("hihihi",boardModel.boardUid);

        FirebaseDatabase.getInstance().getReference().child("boards").child(boardModel.boardUid).setValue(boardModel);

    }
}
