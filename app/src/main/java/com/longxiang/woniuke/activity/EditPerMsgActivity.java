package com.longxiang.woniuke.activity;

import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.longxiang.woniuke.R;
import com.longxiang.woniuke.bean.BeanIcon;
import com.longxiang.woniuke.utils.MyApp;
import com.longxiang.woniuke.utils.PhotoUploadTask;
import com.longxiang.woniuke.utils.PhotoUploadUtils;

import org.json.JSONObject;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import io.rong.imkit.RongIM;
import io.rong.imlib.model.UserInfo;

public class EditPerMsgActivity extends AppCompatActivity implements View.OnClickListener {
    private ImageView ivBack;
    private TextView title;
    private ImageView ivIcon;
    private RelativeLayout rl01;
    private RelativeLayout rl02;
    private RelativeLayout rl03;
    private RelativeLayout rl04;
    private RelativeLayout rl05;
    private RelativeLayout rl06;
    private RelativeLayout rl07;
    private RelativeLayout rl08;
    private Button btnConfirm;
    private TextView tv01;
    private TextView tv02;
    private TextView tv03;
    private TextView tv04;
    private TextView tv05;
    private TextView tv06;
    private TextView tv07;
    private List<RelativeLayout> rls;
    //    private String head_pic;
//    private static final String IMAGE_UNSPECIFIED = "image/*";
//    private static final int ALBUM_REQUEST_CODE = 1;
//    private static final int CAMERA_REQUEST_CODE = 2;
//    private static final int CROP_REQUEST_CODE = 4;
//    private Uri imageUri;
//    Handler handler =new Handler(){
//        @Override
//        public void handleMessage(Message msg) {
//            switch (msg.what){
//                case 152:
//                    String s = (String) msg.obj;
//                    Log.i("icon",s);
//                    BeanIcon beanicon=new Gson().fromJson(s,BeanIcon.class);
//                    head_pic= beanicon.getData();
//                    uploadIcon();
//                    break;
//            }
//            super.handleMessage(msg);
//        }
//    };
    private String name;
    private String birthday;
    private String age;
    private String star;
    private String sign;
    private String occupation;
    private String school;
    private String interest;
    private String name2="-1";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_per_msg);
//        imageUri = Uri.fromFile(new File(PhotoUploadUtils.IMAGE_FILE_LOCATION));
        setView();
        setData();
        setListener();
        getUserinfo();
    }

    private void getUserinfo() {
        RequestParams params=new RequestParams("http://bubblefish.jbserver.cn/api/users/getinformation");
        params.addBodyParameter("uid", MyApp.uid);
        params.addBodyParameter("lat",MyApp.lat_经度);
        params.addBodyParameter("lng",MyApp.lng_纬度);
//        Log.i("lvzhiwei222", "getUserInfo: "+MyApp.lat_经度);
//        Log.i("lvzhiwei222", "getUserInfo: "+MyApp.lng_纬度);
//        Log.i("lvzhiwei222", "getUserInfo: "+MyApp.uid);
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Log.i("lvzhiwei222", "onSuccess: "+result);
                try {
                    JSONObject json=new JSONObject(result);
                    JSONObject obj=json.getJSONObject("data");
//                    head_pic=obj.getString("head_pic");
//                    x.image().bind(ivIcon,head_pic);
                    name=obj.getString("nickname");
                    age=obj.getString("age");
                    star=obj.getString("starchar");
                    sign=obj.getString("sign");
                    occupation=obj.getString("occupation");
                    school=obj.getString("school");
                    interest=obj.getString("interest");
                    tv01.setText(name);
                    tv02.setText(age);
                    tv03.setText(star);
                    tv04.setText(sign);
                    tv05.setText(occupation);
                    tv06.setText(school);
                    tv07.setText(interest);
                    birthday=obj.getString("birthday");
//                    Bitmap bitmap=GetLocalOrNetBitmap(obj.getString("head_pic"));
//                    Log.i("lvzhiwei333", "onSuccess: "+bitmap);
//                    ivBg.setImageBitmap(blurImageAmeliorate(bitmap));
                    if(tv01.getText().toString().equals(name)){
                        name="";
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {

            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {

            }
        });
    }
    private void setView() {
        ivBack= (ImageView) findViewById(R.id.edit_msg_iv_back);
//        ivIcon= (ImageView) findViewById(R.id.edit_msg_ivIcon);
//        rl01= (RelativeLayout) findViewById(R.id.edit_msg_msg_rl01);
        rl02= (RelativeLayout) findViewById(R.id.edit_msg_msg_rl02);
        rl03= (RelativeLayout) findViewById(R.id.edit_msg_msg_rl03);
        rl04= (RelativeLayout) findViewById(R.id.edit_msg_msg_rl04);
        rl05= (RelativeLayout) findViewById(R.id.edit_msg_msg_rl05);
        rl06= (RelativeLayout) findViewById(R.id.edit_msg_msg_rl06);
        rl07= (RelativeLayout) findViewById(R.id.edit_msg_msg_rl07);
        rl08= (RelativeLayout) findViewById(R.id.edit_msg_msg_rl08);
        tv01= (TextView) findViewById(R.id.edit_msg_tvName);
        tv02= (TextView) findViewById(R.id.edit_msg_tvAge);
        tv03= (TextView) findViewById(R.id.edit_msg_tvStar);
        tv04= (TextView) findViewById(R.id.edit_msg_tvGexing);
        tv05= (TextView) findViewById(R.id.edit_msg_tvJob);
        tv06= (TextView) findViewById(R.id.edit_msg_tvSchool);
        tv07= (TextView) findViewById(R.id.edit_msg_tvHobby);
        btnConfirm= (Button) findViewById(R.id.edit_msg_btnConfirm);
        title= (TextView) findViewById(R.id.edit_msg_tv_title_name);
        title.setTextColor(Color.parseColor("#ffffff"));
        btnConfirm.setTextColor(Color.parseColor("#ffffff"));
    }

    private void setData() {
        rls=new ArrayList<>();
//        rls.add(rl01);
        rls.add(rl02);
        rls.add(rl03);
        rls.add(rl04);
        rls.add(rl05);
        rls.add(rl06);
        rls.add(rl07);
        rls.add(rl08);
    }

    private void setListener() {
        ivBack.setOnClickListener(this);
        btnConfirm.setOnClickListener(this);
        for(int i=0;i<rls.size();i++){
            rls.get(i).setOnClickListener(this);
        }
    }
    //    private void uploadIcon() {
//        RequestParams params=new RequestParams("http://bubblefish.jbserver.cn/api/users/headEdit");
//        params.addBodyParameter("uid",MyApp.uid);
//        params.addBodyParameter("head_pic",head_pic);
//        x.http().post(params, new Callback.CommonCallback<String>() {
//            @Override
//            public void onSuccess(String result) {
//                try{
//                    JSONObject obj=new JSONObject(result);
//                    switch (obj.getString("retcode")){
//                        case "4000":
//                        case "4001":
//                        case "4002":
//                        case "4003":
//                            Toast.makeText(getApplicationContext(),obj.getString("msg"),Toast.LENGTH_SHORT);
//                            break;
//                        case "2000":
//                            Toast.makeText(getApplicationContext(),obj.getString("msg"),Toast.LENGTH_SHORT);
//                            break;
//                    }
//                }catch (Exception e){
//                    e.printStackTrace();
//                }
//            }
//
//            @Override
//            public void onError(Throwable ex, boolean isOnCallback) {
//
//            }
//
//            @Override
//            public void onCancelled(CancelledException cex) {
//
//            }
//
//            @Override
//            public void onFinished() {
//
//            }
//        });
//    }
    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()){
            case R.id.edit_msg_iv_back:
                finish();
                break;
//            case R.id.edit_msg_msg_rl01:
//                Toast.makeText(this,"上一页修改头像",Toast.LENGTH_SHORT).show();
//                alertDialog();
//                break;
            case R.id.edit_msg_msg_rl02:
                intent=new Intent(this,NameActivity.class);
                intent.putExtra("name",tv01.getText().toString());
                startActivityForResult(intent,511);
                break;
            case R.id.edit_msg_msg_rl03:
                intent=new Intent(this,BirthdayActivity.class);
                intent.putExtra("birthday",birthday);
                intent.putExtra("age",age);
                intent.putExtra("star",star);
                startActivityForResult(intent,512);
                break;
            case R.id.edit_msg_msg_rl04:
                intent=new Intent(this,BirthdayActivity.class);
                intent.putExtra("birthday",birthday);
                intent.putExtra("age",age);
                intent.putExtra("star",star);
                startActivityForResult(intent,512);
                break;
            case R.id.edit_msg_msg_rl05:
                intent=new Intent(this,GexingActivity.class);
                intent.putExtra("gexing",tv04.getText().toString());
                startActivityForResult(intent,513);
                break;
            case R.id.edit_msg_msg_rl06:
                intent=new Intent(this,JobActivity.class);
                startActivityForResult(intent,514);
                break;
            case R.id.edit_msg_msg_rl07:
                intent=new Intent(this,SchoolActivity.class);
                intent.putExtra("school",tv06.getText().toString());
                startActivityForResult(intent,515);
                break;
            case R.id.edit_msg_msg_rl08:
                intent=new Intent(this,IntrestActivity.class);
                startActivityForResult(intent,516);
                break;
            case R.id.edit_msg_btnConfirm:
                editMsg();
                break;
        }
    }

    private void editMsg() {
        RequestParams params=new RequestParams("http://bubblefish.jbserver.cn/api/users/editinformation");
        params.addBodyParameter("uid",MyApp.uid);
        params.addBodyParameter("starchar",tv03.getText().toString());
        params.addBodyParameter("birthday",birthday);
        params.addBodyParameter("age",tv02.getText().toString());
        params.addBodyParameter("nickname", name);
        params.addBodyParameter("sign",tv04.getText().toString());
        params.addBodyParameter("occupation",tv05.getText().toString());
        params.addBodyParameter("school",tv06.getText().toString());
        params.addBodyParameter("interest",tv07.getText().toString());
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Log.i("lvzhiwei098", "onSuccess: "+result);
                try {
                    JSONObject obj=new JSONObject(result);
                    switch (obj.getString("retcode")){
                        case "4000":
                        case "4001":
                        case "5004":
                            Toast.makeText(getApplicationContext(),obj.getString("msg"),Toast.LENGTH_SHORT).show();
                            break;
                        case "2000":
                            Toast.makeText(getApplicationContext(),obj.getString("msg"),Toast.LENGTH_SHORT).show();
                            finish();
                            break;
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {

            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {

            }
        });
    }
    //
//    private void alertDialog() {
//        final AlertDialog alertDialog = new AlertDialog.Builder(EditPerMsgActivity.this).create();
//        alertDialog.show();
//        Window window = alertDialog.getWindow();
//        window.setContentView(R.layout.change_pic);
//        alertDialog.setCanceledOnTouchOutside(true);
//        RelativeLayout rlxuan= (RelativeLayout) window.findViewById(R.id.xuan_pic);
//        RelativeLayout rlpai= (RelativeLayout) window.findViewById(R.id.pai_pic);
//        rlxuan.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(Intent.ACTION_PICK, null);
//                intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, IMAGE_UNSPECIFIED);
//                startActivityForResult(intent, ALBUM_REQUEST_CODE);
//                alertDialog.dismiss();
//            }
//        });
//        rlpai.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//                intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(new File(Environment.
//                        getExternalStorageDirectory(), "/temp.jpg")));
//                startActivityForResult(intent, CAMERA_REQUEST_CODE);
//                alertDialog.dismiss();
//            }
//        });
//    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
//            case ALBUM_REQUEST_CODE:
//                if (data == null) {
//                    return;
//                }
//                startCrop(data.getData());
//                break;
//            case CAMERA_REQUEST_CODE:
//                Log.e("12345", "onActivityResult: "+data );
//                File picture = new File(Environment.getExternalStorageDirectory()
//                        + "/temp.jpg");
//                startCrop(Uri.fromFile(picture));
//                break;
//            case CROP_REQUEST_CODE:
//                if (data == null) {
//                    Log.i("asdasdasd", "onActivityResult: "+"1212");
//                    return;
//                }
//                Bundle extras = data.getExtras();
//                if (extras != null) {
//                    Bitmap photo = PhotoUploadUtils.decodeUriAsBitmap(imageUri,this);
//                    Log.i("asdasdasd", "onActivityResult: "+imageUri+","+photo);
////                    Bitmap photo = extras.getParcelable("data");
//                    ByteArrayOutputStream stream = new ByteArrayOutputStream();
////                    Bitmap bitmap=comp(photo);
//                    photo.compress(Bitmap.CompressFormat.JPEG, 100, stream);// (0-100)压缩文件
//                    //此处可以把Bitmap保存到sd卡中，具体请看：http://www.cnblogs.com/linjiqin/archive/2011/12/28/2304940.html
//                    ivIcon.setImageBitmap(photo); //把图片显示在ImageView控件上
//                    // 获得字节流
//                    ByteArrayInputStream is = new ByteArrayInputStream(stream.toByteArray());
//                    PhotoUploadTask put = new PhotoUploadTask(
//                            "http://bubblefish.jbserver.cn/api/api/fileUpload", is,
//                            this, handler);
//                    put.start();
//                }
//                break;
            case 511:
                try {
                    tv01.setText(data.getStringExtra("name"));
                    if(name.equals(data.getStringExtra("name"))){
                        name="";
                        Log.i("getintent", "onActivityResult1: "+name);
                    }else{
                        name=tv01.getText().toString();
                        Log.i("getintent", "onActivityResult2: "+name);
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
                break;
            case 512:
                try{
                    birthday=data.getStringExtra("birthday");
                    tv02.setText(data.getStringExtra("age"));
                    tv03.setText(data.getStringExtra("star"));
                }catch (Exception e){
                    e.printStackTrace();
                }
                break;
            case 513:
                try{
                    tv04.setText(data.getStringExtra("gexing"));
                }catch (Exception e){
                    e.printStackTrace();
                }
                break;
            case 514:
                try{
                    tv05.setText(data.getStringExtra("job"));
                }catch (Exception e){
                    e.printStackTrace();
                }
                break;
            case 515:
                try{
                    tv06.setText(data.getStringExtra("school"));
                }catch (Exception e){
                    e.printStackTrace();
                }
                break;
            case 516:
                try{
                    tv07.setText(data.getStringExtra("intrest"));
                }catch (Exception e){
                    e.printStackTrace();
                }
                break;

        }
        super.onActivityResult(requestCode, resultCode, data);
    }
//    private void startCrop(Uri uri) {
//        Intent intent = new Intent("com.android.camera.action.CROP");//调用Android系统自带的一个图片剪裁页面,
//        intent.setDataAndType(uri, "image/*");
//        intent.putExtra("crop", "true");//进行修剪
//        // aspectX aspectY 是宽高的比例
//        intent.putExtra("aspectX", 1);
//        intent.putExtra("aspectY", 1);
//        // outputX outputY 是裁剪图片宽高
//        intent.putExtra("outputX", 300);
//        intent.putExtra("outputY", 300);
//        intent.putExtra("return-data", false);
//        intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
//        intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
//        startActivityForResult(intent, CROP_REQUEST_CODE);
//    }

//    @Override
//    protected void onResume() {
//        super.onResume();
//        getUserinfo();
//    }
}
