package com.yqq.test.testphoto;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.yqq.test.testphoto.util.CircleImageView;
import com.yqq.test.testphoto.util.HttpUtil;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private Button btn_photo = null;
    private ImageView iv_photo = null;
    private CircleImageView civ_photo = null;
    private LinearLayout ly_photo = null;
    private Button btn_takephoto = null;
    private Button btn_photoSelect = null;
    private Button btn_photoUpload = null;
    private TextView tv_path = null;

    private Button btn_downPhoto = null;
    private Button btn_getPath = null;

    public final static int CHOOOSE = 1;
    public final static int CUT = 2;
    public final static int TAKEPAHOTO = 3;

    private Uri uritempFile;
    private File tempFile = new File(Environment.getExternalStorageDirectory().getPath(), getPhotoFileName());

    String UrlPath = HttpUtil.BASE_URL + "addHeadPhoto.do";
    String filePath = null;

    final String path = "/sdcard/small.jpg";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btn_photo = (Button) findViewById(R.id.btn_photo);
        iv_photo = (ImageView) findViewById(R.id.iv_photo);
        civ_photo = (CircleImageView) findViewById(R.id.civ_photo);
        ly_photo = (LinearLayout) findViewById(R.id.ly_photo);
        btn_takephoto = (Button) findViewById(R.id.btn_takephoto);
        btn_photoSelect = (Button) findViewById(R.id.btn_photoSelect);
        tv_path = (TextView) findViewById(R.id.tv_path);

        btn_photoUpload = (Button) findViewById(R.id.btn_photoUpload);
        btn_photoUpload.setOnClickListener(this);

        btn_downPhoto = (Button) findViewById(R.id.btn_downPhoto);
        btn_downPhoto.setOnClickListener(this);

        btn_getPath = (Button) findViewById(R.id.btn_getPath);
        btn_getPath.setOnClickListener(this);


        btn_photo.setOnClickListener(this);
        btn_takephoto.setOnClickListener(this);
        btn_photoSelect.setOnClickListener(this);

        File file =getFilesDir();

        tv_path.setText(file.getAbsolutePath());

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_photo:
//                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
//                intent.setType("image/*");
//                startActivityForResult(intent,CHOOOSE);
//                Intent intent2 = new Intent("android.intent.action.PICK");
//                intent2.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
//                startActivityForResult(intent2, GlobalParameter.COMMENTS_LOCAL_PHOTO);
                getLocalPhoto();
                break;
            case R.id.btn_takephoto:
//                Intent intent1 = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//                // 指定调用相机拍照后照片的储存路径
//                intent1.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(tempFile));
//                startActivityForResult(intent1,TAKEPAHOTO);
                getTakePhoto();
                break;
            case R.id.btn_photoSelect:
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("修改头像");
                builder.setItems(new String[]{"本地上传", "拍照上传"}, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which){
                            case 0:
                                getLocalPhoto();
                                break;
                            case 1:
                                getTakePhoto();
                                break;
                        }
                    }
                });
                builder.create().show();
                break;
            case R.id.btn_photoUpload:
                HttpUtil.uploadFile(UrlPath,uritempFile.getPath());
//                Log.d("test", path + "   ******************");
////                HttpUtil.uploadFile(HttpUtil.BASE_URL+"addHeadPhoto.do",path);
//                HttpUtil.upLoadFile();

                break;

            case R.id.btn_downPhoto:
                HttpUtil.downLoadFile();
                break;
            case R.id.btn_getPath:
                getPath();
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode != RESULT_CANCELED){
            switch (requestCode){
//                case CHOOOSE:
//                    if(data != null){
//                        startPhotoZoom(data.getData());
//                    }
//                    break;
//                case CUT:
//                    if(data != null){
//                        Bitmap bitmap = data.getParcelableExtra("data");
//                        iv_photo.setImageBitmap(bitmap);
//                    }
//                    break;
                case GlobalParameter.COMMENTS_LOCAL_PHOTO:
                    if(data != null) {
                        startPhotoZoom1(data.getData());
                    }
                    break;
                case CUT:
                    if (data != null) {
                        try {
                            setImageToView(data);
//                            uploadFile();
                        } catch (FileNotFoundException e) {
                            e.printStackTrace();
                        }
                    }
                    break;
                case TAKEPAHOTO:
                    startPhotoZoom1(Uri.fromFile(tempFile));
//                    try {
//                        setImageToView(data);
//                    } catch (FileNotFoundException e) {
//                        e.printStackTrace();
//                    }
                    break;

            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
    private void startPhotoZoom(Uri uri) {
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        // crop为true是设置在开启的intent中设置显示的view可以剪裁
        intent.putExtra("crop", "true");

        // aspectX aspectY 是宽高的比例
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);

        // outputX,outputY 是剪裁图片的宽高
        intent.putExtra("outputX", 300);
        intent.putExtra("outputY", 300);
        intent.putExtra("return-data", true);
        intent.putExtra("noFaceDetection", true);
        startActivityForResult(intent, CUT);
    }

    public void startPhotoZoom1(Uri uri) {
        if (uri == null) {
            Log.i("tag", "The uri is not exist.");
        }
        try {
            Intent intent = new Intent("com.android.camera.action.CROP");
            intent.setDataAndType(uri, "image/*");
            // 设置裁剪
            intent.putExtra("crop", "true");
            // aspectX aspectY 是宽高的比例
            intent.putExtra("aspectX", 1);
            intent.putExtra("aspectY", 1);
            // outputX outputY 是裁剪图片宽高
            intent.putExtra("outputX", 320);
            intent.putExtra("outputY", 320);
            intent.putExtra("return-data", true);
            intent.putExtra("scale", true);// 去黑边
            intent.putExtra("scaleUpIfNeeded", true);
            uritempFile = Uri.parse("file://" + "/" + Environment.getExternalStorageDirectory().getPath() + "/" + "small.jpg");

            filePath = Environment.getExternalStorageDirectory().getPath() + "/" + "small.jpg";

            tv_path.setText(uritempFile.getPath());

            Log.d("path", Environment.getExternalStorageDirectory().getPath());
            intent.putExtra(MediaStore.EXTRA_OUTPUT, uritempFile);
            intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
            startActivityForResult(intent, 2);
            Log.d("test","********stop");
        } catch (Exception e) {
            Bitmap bitmap = null;
            try {
                bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), uri);
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }
    }
    private void setImageToView(Intent data) throws FileNotFoundException {
//        Bitmap bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(uritempFile));
        Bitmap bitmap = data.getParcelableExtra("data");
        iv_photo.setImageBitmap(bitmap);
        civ_photo.setImageBitmap(bitmap);
    }
    private String getPhotoFileName() {
        Date date = new Date(System.currentTimeMillis());
        SimpleDateFormat dateFormat = new SimpleDateFormat("'IMG'_yyyyMMdd_HHmmss");
        return dateFormat.format(date) + ".jpg";
    }
    public void getLocalPhoto(){
//        Intent intent2 = new Intent("android.intent.action.PICK");
//        intent2.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
//        startActivityForResult(intent2, GlobalParameter.COMMENTS_LOCAL_PHOTO);
        Intent getAlbum = new Intent(Intent.ACTION_GET_CONTENT);
        getAlbum.setType("image/*");
        startActivityForResult(getAlbum,GlobalParameter.COMMENTS_LOCAL_PHOTO);
    }
    public void getTakePhoto(){
        Intent intent1 = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // 指定调用相机拍照后照片的储存路径
        intent1.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(tempFile));
        startActivityForResult(intent1,TAKEPAHOTO);
    }

    public void getPath() {

        String path = getApplicationContext().getFilesDir().getPath();
        tv_path.setText(path);


    }
}
