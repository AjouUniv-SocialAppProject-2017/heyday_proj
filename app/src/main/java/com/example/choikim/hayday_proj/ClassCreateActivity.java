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
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.choikim.hayday_proj.model.ClassModel;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;

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
    private Button class_uplord;
    private String img_path;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_class_create);
        auth = FirebaseAuth.getInstance();
        storage = FirebaseStorage.getInstance();
        database = FirebaseDatabase.getInstance();




        class_image = (ImageButton)findViewById(R.id.class_image);
        class_name = (EditText)findViewById(R.id.class_name);
        class_index = (EditText)findViewById(R.id.class_index);
        class_content = (EditText)findViewById(R.id.class_content);
        class_uplord = (Button)findViewById(R.id.class_uplord);


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

                upload(img_path);
                Toast.makeText(ClassCreateActivity.this, "강의 등록이 완료되었습니다", Toast.LENGTH_SHORT).show();
                finish();

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
                classModel.class_content = class_content.getText().toString();
                classModel.uid = auth.getCurrentUser().getUid();
                classModel.userId = auth.getCurrentUser().getEmail();
                classModel.user_name = auth.getCurrentUser().getDisplayName();

                database.getReference().child("Class").push().setValue(classModel);
            }
        });

    }

}