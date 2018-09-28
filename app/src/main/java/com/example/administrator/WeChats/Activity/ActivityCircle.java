package com.example.administrator.WeChats.Activity;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.administrator.WeChats.R;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

public class ActivityCircle extends AppCompatActivity
        implements View.OnClickListener
{
    private ImageView photo;
    private Uri imageUri;
    private static final int TAKE_PHOTO =1;
    private static final int SELECT_PHOTO =2;

    public static void actionStart(Context context) {
        Intent intent=new Intent(context,ActivityCircle.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        context.startActivity(intent);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_circle);

        ActionBar actionBar=getSupportActionBar();
        if(actionBar !=null)
            actionBar.setDisplayHomeAsUpEnabled(true);//返回箭头显示出来
//            {   actionBar.hide(); }

      ImageView  circle_photo = findViewById(R.id.circle_photo);
      photo = findViewById(R.id.photo);
      circle_photo.setOnClickListener(this);
      photo.setOnClickListener(this);
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.circle_photo:
                Toast.makeText(ActivityCircle.this, "the circle_photo is clicked", Toast.LENGTH_SHORT).show();
                 break;
            case R.id.photo:
                Toast.makeText(ActivityCircle.this, "the photo is clicked", Toast.LENGTH_SHORT).show();
                break;
            default :break;
        }
    }
    private void openAlbum()                //打开相册
    {
        Intent open_album = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);//"android.intent.action.GET_CONTNET"
        open_album.setType("image/*");
        startActivityForResult(open_album,SELECT_PHOTO);
    }
    private void handleImageBeforeKitKat(Intent data)        //图片真实的URI
    {
        Uri uri = data.getData();
        String imagePath = getImagePath(uri,null);
        displayImage(imagePath);
    }

    private String getImagePath(Uri uri,String selection)
    {
        String path = null;
        Cursor cursor  = getContentResolver().query(uri,null,selection,null,null);
        if(cursor != null) {
            if (cursor.moveToFirst()) {
                path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
            }
            cursor.close();
        }
        return path;
    }

    private void displayImage(String imagePath)
    {
        if(imagePath !=null)
        {
            Bitmap bitmap = BitmapFactory.decodeFile(imagePath);
            photo.setImageBitmap(bitmap);
        }else{
            Toast.makeText(this,"failed to get image",Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.photo_menu,menu);
       return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
            {
                finish();
            }break;

            case R.id.take_photo: //photo save!
            {
                File outputImage = new File(getExternalCacheDir(), "output_image.jpg");
                try {
                    if (outputImage.exists())
                        outputImage.delete();
                    outputImage.createNewFile();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                if (Build.VERSION.SDK_INT >= 24) {
                    imageUri = FileProvider.getUriForFile(ActivityCircle.this,
                            "com.example.administrator.WeChats.fileprovider", outputImage);
                } else {
                    imageUri = Uri.fromFile(outputImage);
                }
                //打开相机
                Intent open_camera = new Intent("android.media.action.IMAGE_CAPTURE");
                open_camera.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
                startActivityForResult(open_camera, TAKE_PHOTO);
            }break;

            case R.id.select_photo:
            {
                if (ContextCompat.checkSelfPermission(ActivityCircle.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {                                                                   //动态申请对sd卡读写能力
                    ActivityCompat.requestPermissions(ActivityCircle.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 3);
                } else {
                    openAlbum();
                }
            }break;
            default :break;
        }
       return super.onOptionsItemSelected(item);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,@NonNull int[] grantResults)
    {                                       //弹框请求权限
        switch(requestCode)
        {
            case 3:
                if(grantResults.length>0 && grantResults[0]==PackageManager.PERMISSION_GRANTED) {
                     openAlbum();
                     break;
                }
                else {
                    Toast.makeText(this,"you denied the permission",Toast.LENGTH_SHORT).show();
                    break;
                }
            default:
        }
    }

    @Override
    protected void onActivityResult(int requestCode,int resultCode,Intent data)
    {
        switch (requestCode)
        {
            case TAKE_PHOTO:
                if(resultCode == RESULT_OK)
                {
                    try{                                        //拍照成功，解析为bitmap格式显示
                        Bitmap bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(imageUri));
                        photo.setImageBitmap(bitmap);
                    }catch (FileNotFoundException e)    {e.printStackTrace();}
                    }break;

            case SELECT_PHOTO:
                if(resultCode == RESULT_OK)
                {
                    if(Build.VERSION.SDK_INT>=19)               //判断手机系统版本号
                    {   handleImageOnKitKat(data);    }
                    else
                        handleImageBeforeKitKat(data);
                }break;
            default:break;
        }
    }

    @TargetApi(19)
    private void handleImageOnKitKat(Intent data)           //封装的图片URI
    {
        String imagePath = null;
        Uri uri = data.getData();
        if(DocumentsContract.isDocumentUri(this,uri))
        {
            String docId = DocumentsContract.getDocumentId(uri);

            if("com.android.providers.media.documents".equals(uri.getAuthority()))
            {
                String id = docId.split(":")[1];
                String selection = MediaStore.Images.Media._ID+"="+id;
                imagePath = getImagePath(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,selection);
            }
            else if("com.android.providers.downloads.documents".equals(uri.getAuthority()))
            {
                Uri contentUri = ContentUris.withAppendedId(
                        Uri.parse("content://downloads/public_downloads"),Long.valueOf(docId));
                imagePath = getImagePath(contentUri,null);
            }
        }
        else if("content".equalsIgnoreCase(uri.getScheme()))
            imagePath = getImagePath(uri,null);
        else if ("file".equalsIgnoreCase(uri.getScheme()))
            imagePath = uri.getPath();
        displayImage(imagePath);
    }
}
