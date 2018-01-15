package com.longxiang.woniuke.activity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Environment;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.longxiang.woniuke.R;
import com.longxiang.woniuke.bean.BeanIcon;
import com.longxiang.woniuke.receiver.MyReceiver;
import com.longxiang.woniuke.utils.DatePickerFragment;
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

public class RegistMsgActivity extends AppCompatActivity implements View.OnClickListener {
private ImageView ivBack;
    private ImageView ivIcon;
    private EditText etNickname;
    private RelativeLayout rlGender;
    private RelativeLayout rlAge;
    private TextView tvGender;
    public static TextView tvAge;
    private Button btnConfirm;
    private String phone;
    private String vercode;
    private String password;
    private LinearLayout llGender;
    private LinearLayout llNan;
    private LinearLayout llNv;
    private LinearLayout llCancel;
    private static final String IMAGE_UNSPECIFIED = "image/*";
    private static final int ALBUM_REQUEST_CODE = 1;
    private static final int CAMERA_REQUEST_CODE = 2;
    private static final int CROP_REQUEST_CODE = 4;
    private String head_pic;
    public Handler handler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            switch (msg.what){
                case 152:
                    String s = (String) msg.obj;
                    Log.i("icon",s);
                    BeanIcon beanicon=new Gson().fromJson(s,BeanIcon.class);
                    head_pic= beanicon.getData();
                    Log.i("lvzhiweihaha",head_pic);
                    break;
            }

        }
    };
    public static SharedPreferences.Editor editor;
    private String openid;
    private String nickname;
    private String headimgurl;
    private int sex;
    private String device_token;
    private Uri imageUri;
    private String type;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_regist_msg);
        imageUri = Uri.fromFile(new File(PhotoUploadUtils.IMAGE_FILE_LOCATION));
        setView();
        Intent intent=getIntent();
        if(intent.getStringExtra("type")!=null){
            type=intent.getStringExtra("type");
        }
        phone=intent.getStringExtra("phone");
        vercode=intent.getStringExtra("vercode");
        if(intent.getStringExtra("password")!=null) {
            password = intent.getStringExtra("password");
        }
        if(intent.getStringExtra("openid")!=null) {
            openid = intent.getStringExtra("openid");
        }
        if(intent.getStringExtra("nickname")!=null) {
            nickname = intent.getStringExtra("nickname");
            etNickname.setText(nickname);
        }
        if(intent.getStringExtra("headimgurl")!=null) {
            headimgurl = intent.getStringExtra("headimgurl");
            x.image().bind(ivIcon,headimgurl);
        }
        if(intent.getStringExtra("device_token")!=null){
            device_token=intent.getStringExtra("device_token");
        }
        sex=intent.getIntExtra("sex",-1);
        if(sex==1){
            tvGender.setText("男");
        }else if(sex==2){
            tvGender.setText("女");
        }else{
            tvGender.setText("");
        }

        Log.i("registmsgactivity", "onCreate: "+phone+","+vercode+","+password+","+openid+","+nickname+","+headimgurl+","+device_token);
        setListener();
        SharedPreferences prefs =  PreferenceManager.getDefaultSharedPreferences(this);
        editor=prefs.edit();
        String deviceID=prefs.getString("deviceId",null);
        if(deviceID==null) {
            SharedPreferences.Editor editor = prefs.edit();
            editor.putString("deviceId", MyReceiver.regId);
            MyApp.deviceId=MyReceiver.regId;
            Log.i("lvzhiweidsa", "onCreate: "+MyApp.deviceId);
            editor.commit();
        }else{
            MyApp.deviceId=deviceID;
            Log.i("lvzhiweidsa", "onCreate: "+MyApp.deviceId);
        }
    }

    private void setView() {
        ivBack= (ImageView) findViewById(R.id.regist_msg_back);
        ivIcon= (ImageView) findViewById(R.id.regist_msg_ivIcon);
        etNickname= (EditText) findViewById(R.id.regist_msg_etNickname);
        rlGender= (RelativeLayout) findViewById(R.id.regist_msg_rlGender);
        rlAge= (RelativeLayout) findViewById(R.id.regist_msg_rlAge);
        tvGender= (TextView) findViewById(R.id.regist_msg_tvGender);
        tvAge= (TextView) findViewById(R.id.regist_msg_tvAge);
        btnConfirm= (Button) findViewById(R.id.regist_msg_btnConfirm);
        btnConfirm.setTextColor(Color.parseColor("#ffffff"));
        llGender= (LinearLayout) findViewById(R.id.regist_msg_bottom_lls_gender);
        llNan= (LinearLayout) findViewById(R.id.regist_msg_ll_nan);
        llNv= (LinearLayout) findViewById(R.id.regist_msg_ll_nv);
        llCancel= (LinearLayout) findViewById(R.id.regist_msg_ll_cancel);
    }

    private void setListener() {
        ivBack.setOnClickListener(this);
        ivIcon.setOnClickListener(this);
        rlGender.setOnClickListener(this);
        rlAge.setOnClickListener(this);
        btnConfirm.setOnClickListener(this);
        llNan.setOnClickListener(this);
        llNv.setOnClickListener(this);
        llCancel.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.regist_msg_back:
                finish();
                break;
            case R.id.regist_msg_ivIcon:
                alertDialog();
                llGender.setVisibility(View.GONE);
                break;
            case R.id.regist_msg_rlGender:
                InputMethodManager imm = (InputMethodManager) v
                        .getContext().getSystemService(
                                Context.INPUT_METHOD_SERVICE);
                if (imm.isActive()) {
                    imm.hideSoftInputFromWindow(
                            v.getApplicationWindowToken(), 0);
                }
                llGender.setVisibility(View.VISIBLE);
                break;
            case R.id.regist_msg_ll_nan:
                tvGender.setText("男");
                llGender.setVisibility(View.GONE);
                break;
            case R.id.regist_msg_ll_nv:
                tvGender.setText("女");
                llGender.setVisibility(View.GONE);
                break;
            case R.id.regist_msg_ll_cancel:
                tvGender.setText("");
                llGender.setVisibility(View.GONE);
                break;
            case R.id.regist_msg_rlAge:
                showDatePickerDialog(tvAge);
                llGender.setVisibility(View.GONE);
                break;
            case R.id.regist_msg_btnConfirm:
                llGender.setVisibility(View.GONE);
                if(openid==null) {
                    regist();
                }else{
                    regOther();
                }
                break;
        }
    }

    private void regOther() {
        RequestParams params=new RequestParams("http://bubblefish.jbserver.cn/api/users/regOther");
        params.addBodyParameter("mobile",phone);
        params.addBodyParameter("head_pic",headimgurl);
        params.addBodyParameter("starchar",DatePickerFragment.star);
        params.addBodyParameter("birthday",DatePickerFragment.birthday);
        params.addBodyParameter("age",DatePickerFragment.age);
        params.addBodyParameter("nickname",nickname);
        params.addBodyParameter("sex",tvGender.getText().toString());
        params.addBodyParameter("device_token",device_token);
        params.addBodyParameter("openid",openid);
        params.addBodyParameter("type",type);
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Log.i("lvzhiweiregOther", "onSuccess: "+result);
                try{
                    JSONObject obj = new JSONObject(result);
                    switch (obj.getString("retcode")){
                        case "4000":
                        case "4001":
                        case "4002":
                        case "4003":
                        case "4004":
                        case "4005":
                        case "4006":
                        case "5000":
                        case "5001":
                        case "5002":
                        case "5003":
                        case "5004":
                        case "4888":
                            Toast.makeText(getApplicationContext(),obj.getString("msg"),Toast.LENGTH_SHORT).show();
                            break;
                        case "2000":
                            JSONObject obj1=obj.getJSONObject("data");
                            String uid = obj1.getString("uid");
                            String token=obj1.getString("r_token");
                            MyApp.token=token;
                            MyApp.uid=uid;
                            editor.putString("uid",MyApp.uid);
                            editor.putString("r_token",MyApp.token);
                            editor.commit();
                            Toast.makeText(getApplicationContext(),obj.getString("msg"),Toast.LENGTH_SHORT).show();
                            Intent intent=new Intent(RegistMsgActivity.this,MainActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(intent);
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

    private void regist() {
        RequestParams params=new RequestParams("http://bubblefish.jbserver.cn/api/users/register");
        params.addBodyParameter("mobile",phone);
        params.addBodyParameter("password",password);
        params.addBodyParameter("code",vercode);
        params.addBodyParameter("starchar",DatePickerFragment.star);
        params.addBodyParameter("birthday",DatePickerFragment.birthday);
        params.addBodyParameter("age",DatePickerFragment.age);
        params.addBodyParameter("nickname",etNickname.getText().toString());
        params.addBodyParameter("sex",tvGender.getText().toString());
        params.addBodyParameter("head_pic",head_pic);
        params.addBodyParameter("device_token", MyApp.deviceId);
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Log.i("lvzhiweiRegist", "onSuccess: "+result);
                try{
                    JSONObject obj = new JSONObject(result);
                    switch (obj.getString("retcode")){
                        case "4000":
                        case "4001":
                        case "4002":
                        case "4003":
                        case "4004":
                        case "4005":
                        case "4006":
                        case "5000":
                        case "5001":
                        case "5002":
                        case "5003":
                        case "5004":
                        case "4888":
                            Toast.makeText(RegistMsgActivity.this,obj.getString("msg"),Toast.LENGTH_SHORT).show();
                            break;
                        case "2000":
                            JSONObject obj1=obj.getJSONObject("data");
                            String uid = obj1.getString("uid");
                            String token=obj1.getString("r_token");
                            MyApp.token=token;
                            MyApp.uid=uid;
                            editor.putString("uid",MyApp.uid);
                            editor.putString("r_token",MyApp.token);
                            editor.commit();
                            Toast.makeText(RegistMsgActivity.this,obj.getString("msg"),Toast.LENGTH_SHORT).show();
                            Intent intent=new Intent(RegistMsgActivity.this,MainActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(intent);
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

    public void showDatePickerDialog(View view){
        DatePickerFragment datePicker = new DatePickerFragment();
        datePicker.show(getSupportFragmentManager(), "datePicker");
    }
    private void alertDialog() {
        final AlertDialog alertDialog = new AlertDialog.Builder(RegistMsgActivity.this).create();
        alertDialog.show();
        Window window = alertDialog.getWindow();
        window.setContentView(R.layout.change_pic);
        RelativeLayout rlxuan= (RelativeLayout) window.findViewById(R.id.xuan_pic);
        RelativeLayout rlpai= (RelativeLayout) window.findViewById(R.id.pai_pic);
        rlxuan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK, null);
                intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, IMAGE_UNSPECIFIED);
                startActivityForResult(intent, ALBUM_REQUEST_CODE);
                alertDialog.dismiss();
            }
        });
        rlpai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(new File(Environment.
                        getExternalStorageDirectory(), "/temp.jpg")));
                startActivityForResult(intent, CAMERA_REQUEST_CODE);
                alertDialog.dismiss();
            }
        });
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case ALBUM_REQUEST_CODE:
                if (data == null) {
                    return;
                }
                startCrop(data.getData());
                break;
            case CAMERA_REQUEST_CODE:
                Log.e("12345", "onActivityResult: "+data );
                File picture = new File(Environment.getExternalStorageDirectory()
                        + "/temp.jpg");
                startCrop(Uri.fromFile(picture));
                break;
            case CROP_REQUEST_CODE:
                if (data == null) {
                    // TODO 如果之前以后有设置过显示之前设置的图片 否则显示默认的图片
                    return;
                }
                Bundle extras = data.getExtras();
                if (extras != null) {
                    Bitmap photo = PhotoUploadUtils.decodeUriAsBitmap(imageUri,this);
//                    Bitmap photo = extras.getParcelable("data");
                    ByteArrayOutputStream stream = new ByteArrayOutputStream();
//                    Bitmap bitmap=comp(photo);
                    photo.compress(Bitmap.CompressFormat.JPEG, 100, stream);// (0-100)压缩文件
                    //此处可以把Bitmap保存到sd卡中，具体请看：http://www.cnblogs.com/linjiqin/archive/2011/12/28/2304940.html
                    ivIcon.setImageBitmap(photo); //把图片显示在ImageView控件上
                    // 获得字节流
                    ByteArrayInputStream is = new ByteArrayInputStream(stream.toByteArray());
                    PhotoUploadTask put = new PhotoUploadTask(
                            "http://bubblefish.jbserver.cn/api/api/fileUpload", is,
                            this, handler);
                    put.start();
                }
                break;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void startCrop(Uri uri) {
        Intent intent = new Intent("com.android.camera.action.CROP");//调用Android系统自带的一个图片剪裁页面,
        intent.setDataAndType(uri, "image/*");
        intent.putExtra("crop", "true");//进行修剪
        // aspectX aspectY 是宽高的比例
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        // outputX outputY 是裁剪图片宽高
        intent.putExtra("outputX", 300);
        intent.putExtra("outputY", 300);
        intent.putExtra("return-data", false);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
        intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
        startActivityForResult(intent, CROP_REQUEST_CODE);
    }
}
