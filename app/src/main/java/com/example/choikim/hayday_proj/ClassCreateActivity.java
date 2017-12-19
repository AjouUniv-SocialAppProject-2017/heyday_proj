package com.example.choikim.hayday_proj;

import android.Manifest;
import android.content.CursorLoader;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.choikim.hayday_proj.model.BoardModel;
import com.example.choikim.hayday_proj.model.ClassModel;
import com.example.choikim.hayday_proj.model.NotificationModel;
import com.example.choikim.hayday_proj.model.UserModel;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.google.gson.Gson;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by jamti on 2017-12-01.
 */

public class ClassCreateActivity extends AppCompatActivity {

    private static final int GALLERY_CODE = 1;
    private FirebaseAuth auth;
    private FirebaseStorage storage;
    private FirebaseDatabase database;

    private ImageButton class_image;
    private EditText class_name;
    private EditText class_index;
    private EditText class_content;
    private EditText class_local;
    private EditText class_time;
    private Button class_uplord;
    private String img_path;

    private List<String> uidList = new ArrayList<>();
    String boardKey;
    String boardFlag;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_class_create);
        auth = FirebaseAuth.getInstance();
        storage = FirebaseStorage.getInstance();
        database = FirebaseDatabase.getInstance();



        class_image = (ImageButton)findViewById(R.id.class_image);
        class_name = (EditText)findViewById(R.id.class_name);
        class_index = (EditText)findViewById(R.id.class_index);
        class_local = (EditText)findViewById(R.id.class_local);
        class_time = (EditText)findViewById(R.id.class_time);
        class_content = (EditText)findViewById(R.id.class_content);
        class_uplord = (Button)findViewById(R.id.class_uplord);

        Intent intent_board=getIntent();
        if(intent_board.getExtras().getString("boardUid")!=null) {
            boardKey = intent_board.getExtras().getString("boardUid");
            boardFlag = intent_board.getExtras().getString("boardFlag");
            callUidKey();
        }



        class_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType(MediaStore.Images.Media.CONTENT_TYPE);
                startActivityForResult(intent,GALLERY_CODE);
            }
        });

        class_uplord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i("checkflag", boardFlag);

                if(boardFlag.equals("1")){
                    sendNoti();
                }
                upload(img_path);
                Toast.makeText(ClassCreateActivity.this, "강의 등록이 완료되었습니다", Toast.LENGTH_SHORT).show();
                finish();

            }
        });
    }

    void sendNoti(){

        Log.i("checkflag", "in sendNoti");

        for(final String uid:uidList){
            FirebaseDatabase.getInstance().getReference()
                    .child("users").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    for(DataSnapshot snapshot:dataSnapshot.getChildren()){
                        UserModel user=snapshot.getValue(UserModel.class);
                        String uidToken;
                        if((user.uid).equals(uid)) {
                            Log.i("checkuid", user.uid);
                            uidToken = user.pushToken;
                            Log.i("checkuid", user.pushToken);
                            sendGcm(uidToken);
                        }
                    }
                }
                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        }
    }

    //boards -> stars에 있는 uid 불러오는 것
    void callUidKey(){
        FirebaseDatabase.getInstance().getReference()
                .child("boards").child(boardKey).child("stars").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot snapshot:dataSnapshot.getChildren()){
                    String uidKey = snapshot.getKey();
                    uidList.add(uidKey);
                    Log.i("testtest", uidKey);
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    //notification 보내는 것
    void sendGcm(String pushToken){

        Log.i("checklog", "sendGcm: "+pushToken);
        Gson gson = new Gson();

        String userName = FirebaseAuth.getInstance().getCurrentUser().getDisplayName();
        NotificationModel notificationModel = new NotificationModel();
        notificationModel.to = pushToken;

        notificationModel.notification.title = "kim";
        notificationModel.notification.text = "king";

        //notificationModel.notification.title = userName;
        //notificationModel.notification.text = class_name.getText().toString();
        notificationModel.data.title = userName;
        notificationModel.data.text = class_name.getText().toString();


        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json; charset=utf8"),gson.toJson(notificationModel));

        Request request = new Request.Builder()
                .header("Content-Type","application/json")
                .addHeader("Authorization","key=AIzaSyDJpD7Zjfyvtl4aD7PiK9ld8yRkHeHWS2w")

                .url("https://gcm-http.googleapis.com/gcm/send")
                .post(requestBody)
                .build();
        OkHttpClient okHttpClient = new OkHttpClient();
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

            }
        });

    }




    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if(requestCode == GALLERY_CODE){

            System.out.println(data.getData());
            System.out.println(getPath(data.getData()));

            img_path = getPath(data.getData());
            File f = new File(img_path);
            class_image.setImageURI(Uri.fromFile(f));
        }
    }

    public String getPath(Uri uri){

        String[] proj =  {MediaStore.Images.Media.DATA};
        CursorLoader cursorLoader = new CursorLoader(this,uri,proj,null,null,null);

        Cursor cursor = cursorLoader.loadInBackground();
        int index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);

        cursor.moveToFirst();

        return cursor.getString(index);

    }

    private void upload(String uri){

        StorageReference storageRef = storage.getReference();

        Uri file = Uri.fromFile(new File(uri));
        StorageReference riversRef = storageRef.child("image/"+file.getLastPathSegment());
        UploadTask uploadTask = riversRef.putFile(file);

// Register observers to listen for when the download is done or if it fails
        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                // Handle unsuccessful uploads
                Toast.makeText(ClassCreateActivity.this, "강의 등록이 실패했습니다.", Toast.LENGTH_SHORT).show();
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                // taskSnapshot.getMetadata() contains file metadata such as size, content-type, and download URL.
                Uri downloadUrl = taskSnapshot.getDownloadUrl();

                ClassModel classModel = new ClassModel();
                classModel.class_image = downloadUrl.toString();
                classModel.class_name = class_name.getText().toString();
                classModel.class_index = class_index.getText().toString();
                classModel.class_time = class_time.getText().toString();
                classModel.class_local = class_local.getText().toString();
                classModel.class_content = class_content.getText().toString();
                classModel.uid = auth.getCurrentUser().getUid();
                classModel.imagePath = auth.getCurrentUser().getPhotoUrl().toString();
                classModel.userId = auth.getCurrentUser().getEmail();
                classModel.user_name = auth.getCurrentUser().getDisplayName();

                database.getReference().child("Class").push().setValue(classModel);
            }
        });

    }

}
