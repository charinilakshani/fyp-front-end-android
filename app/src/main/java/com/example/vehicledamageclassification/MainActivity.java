package com.example.vehicledamageclassification;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;

public class MainActivity extends AppCompatActivity  implements View.OnClickListener {
    private TextView result;
    private Button btn_choose,btn_upload;
    private ImageView image;
    private static final int STORAGE_PERMISSION_CODE= 2342;
    private  static  final int PICK_IMAGE_REQUST =22;
    private Uri filePath;
    private Bitmap bitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        requestStoragePermission();
        init();
    }

    private  void  init(){
        result =(TextView) findViewById(R.id.txt_result);
        image =(ImageView) findViewById(R.id.image) ;

        btn_choose =(Button) findViewById(R.id.btn_choose);
        btn_upload =(Button) findViewById(R.id.btn_upload);

        btn_choose.setOnClickListener(this);
        btn_upload.setOnClickListener(this);

    }

    private void requestStoragePermission(){
        if(ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)== PackageManager.PERMISSION_GRANTED)
            return;
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},STORAGE_PERMISSION_CODE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(requestCode ==STORAGE_PERMISSION_CODE){
            if(grantResults.length >0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                Toast.makeText(this,"permisson Granted",Toast.LENGTH_LONG).show();
            }else {
                Toast.makeText(this,"permisson  not Granted",Toast.LENGTH_LONG).show();

            }
        }
    }

    private void showFileChooser(){
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent,"select Picture"),PICK_IMAGE_REQUST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode ==PICK_IMAGE_REQUST && resultCode == RESULT_OK && data != null && data.getData() != null){
            filePath = data.getData();
            try {
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
                image.setImageBitmap(bitmap);
            }catch (IOException e){

            }
        }
    }

    private String getPath(Uri uri){
        Cursor cursor = getContentResolver().query(uri,null,null,null,null);
        cursor.moveToFirst();
        String documentId = cursor.getString(0);
        documentId = documentId.substring(documentId.lastIndexOf(":") +1);
        cursor.close();

        cursor = getContentResolver().query(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                null, MediaStore.Images.Media._ID +"=?", new String[]{documentId},null
        );
        cursor.moveToFirst();
        String path  =cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
        cursor.close();
        return path;

    }

    private  void uploadImage(){

        String path = getPath(filePath);



        System.out.print("image path"+ path);
        Toast.makeText(this,"Image upload susssful",Toast.LENGTH_LONG).show();
    }
//    cjjcjjcjc

    @Override
    public void onClick(View view) {

        if(view == btn_choose){
            showFileChooser();
        }else if  (view == btn_upload){
            uploadImage();


        }

    }
}
