package com.longxiang.woniuke.activity;

import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.media.MediaPlayer;
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
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.longxiang.woniuke.R;
import com.longxiang.woniuke.bean.BeanIcon;
import com.longxiang.woniuke.utils.GaussianUtil;
import com.longxiang.woniuke.utils.MyApp;
import com.longxiang.woniuke.utils.PhotoUploadTask;
import com.longxiang.woniuke.utils.PhotoUploadUtils;

import org.json.JSONObject;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

import io.rong.imkit.RongIM;
import io.rong.imlib.model.UserInfo;

public class EditPersonMsgActivity extends AppCompatActivity implements View.OnClickListener, MediaPlayer.OnCompletionListener {
    private ImageView startVoice;
    private Button luVoice;
    private ImageView ivBack;
    private ImageView ivIcon;
    private TextView tvNickname;
    private ImageView ivBg;
    private ImageView ivEdit;
    private static final String IMAGE_UNSPECIFIED = "image/*";
    private static final int ALBUM_REQUEST_CODE = 1;
    private static final int CAMERA_REQUEST_CODE = 2;
    private static final int CROP_REQUEST_CODE = 4;
    private RelativeLayout rl01;
    private RelativeLayout rl02;
    private RelativeLayout rl03;
    private RelativeLayout rl04;
    private RelativeLayout rl05;
    private RelativeLayout rl06;
    private RelativeLayout rl07;
    private TextView tvName;
    private TextView tvAges;
    private TextView tvAge;
    private TextView tvStar;
    private TextView tvGexing;
    private TextView tvJob;
    private TextView tvSchool;
    private TextView tvHobby;
    private List<RelativeLayout> rls;
    private List<TextView> tvs;
    private MediaPlayer player;
    private String media;
    private String head_pic;

    private static final String IMAGE_FILE_LOCATION = Environment.getExternalStorageDirectory().getAbsolutePath()+"/temp.jpg";
    Handler handler =new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case 1:
                    getBitmap(head_pic);
                    break;
                case 152:
                    String s = (String) msg.obj;
                    Log.i("icon",s);
                    BeanIcon beanicon=new Gson().fromJson(s,BeanIcon.class);
                    head_pic= beanicon.getData();
                    getBitmap("http://bubblefish.jbserver.cn/"+head_pic);
                    uploadIcon();
                    break;
//                case 2:
//                    getUserInfo();
//                    break;
            }
            super.handleMessage(msg);
        }
    };
    private String sex;
    private Uri cropUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_person_msg);
//        cropUri = Uri.fromFile(new File(IMAGE_FILE_LOCATION));
        init();
//        setData();
        setListener();
        getUserInfo();
    }
    private void init() {
        ivBack= (ImageView) findViewById(R.id.edit_person_ivBack);
        ivIcon= (ImageView) findViewById(R.id.edit_person_ivPic);
        tvNickname= (TextView) findViewById(R.id.edit_person_tvNickname);
        tvNickname.setTextColor(Color.parseColor("#ffffff"));
        tvAges= (TextView) findViewById(R.id.edit_person_tvStarAndAge);
        tvAges.setTextColor(Color.parseColor("#ffffff"));
        ivEdit= (ImageView) findViewById(R.id.edit_person_ivEdit);
//        webView= (WebView) findViewById(R.id.edit_person_webview);
        ivBg= (ImageView) findViewById(R.id.edit_person_bg);
        startVoice= (ImageView) findViewById(R.id.edit_person_startvoice);
        luVoice= (Button) findViewById(R.id.edit_person_luvoice);
//        rl01= (RelativeLayout) findViewById(R.id.edit_person_msg_rl01);
//        rl02= (RelativeLayout) findViewById(R.id.edit_person_msg_rl02);
//        rl03= (RelativeLayout) findViewById(R.id.edit_person_msg_rl03);
//        rl04= (RelativeLayout) findViewById(R.id.edit_person_msg_rl04);
//        rl05= (RelativeLayout) findViewById(R.id.edit_person_msg_rl05);
//        rl06= (RelativeLayout) findViewById(R.id.edit_person_msg_rl06);
//        rl07= (RelativeLayout) findViewById(R.id.edit_person_msg_rl07);
        tvName= (TextView) findViewById(R.id.edit_person_tvName);
        tvAge= (TextView) findViewById(R.id.edit_person_tvAge);
        tvStar= (TextView) findViewById(R.id.edit_person_tvStar);
        tvGexing= (TextView) findViewById(R.id.edit_person_tvGexing);
        tvJob= (TextView) findViewById(R.id.edit_person_tvJob);
        tvSchool= (TextView) findViewById(R.id.edit_person_tvSchool);
        tvHobby= (TextView) findViewById(R.id.edit_person_tvHobby);
        tvName.setTextColor(Color.parseColor("#000000"));
        tvAge.setTextColor(Color.parseColor("#000000"));
        tvStar.setTextColor(Color.parseColor("#000000"));
        tvGexing.setTextColor(Color.parseColor("#000000"));
        tvJob.setTextColor(Color.parseColor("#000000"));
        tvSchool.setTextColor(Color.parseColor("#000000"));
        tvHobby.setTextColor(Color.parseColor("#000000"));
        player=new MediaPlayer();
    }
//    private void setData() {
//        rls=new ArrayList<>();
//        rls.add(rl01);
//        rls.add(rl02);
//        rls.add(rl03);
//        rls.add(rl04);
//        rls.add(rl05);
//        rls.add(rl06);
//        rls.add(rl07);
//        tvs=new ArrayList<>();
//        tvs.add(tvName);
//        tvs.add(tvAge);
//        tvs.add(tvStar);
//        tvs.add(tvGexing);
//        tvs.add(tvJob);
//        tvs.add(tvSchool);
//        tvs.add(tvHobby);
//    }

    private void setListener() {
        ivBack.setOnClickListener(this);
        ivIcon.setOnClickListener(this);
        ivEdit.setOnClickListener(this);
        startVoice.setOnClickListener(this);
        luVoice.setOnClickListener(this);
        player.setOnCompletionListener(this);
//        for (int i=0;i<rls.size();i++){
//            rls.get(i).setOnClickListener(this);
//        }
    }

    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()){
            case R.id.edit_person_ivBack:
                finish();
                break;
            case R.id.edit_person_ivPic:
                alertDialog();
                break;
            case R.id.edit_person_ivEdit:
                intent=new Intent(this,EditPerMsgActivity.class);
                startActivity(intent);
                break;
            case R.id.edit_person_startvoice:
                Toast.makeText(getApplicationContext(),"缓冲中...",Toast.LENGTH_SHORT).show();
                startVoice.setOnClickListener(null);
                try {

                    player.reset();
                    player.setDataSource(media);
                    player.prepare();
                    player.start();

                }catch (Exception e){
                    e.printStackTrace();
                }
                break;
            case R.id.edit_person_luvoice:
                intent=new Intent(this,VoiceActivity.class);
                startActivity(intent);
                break;
//            case R.id.edit_person_msg_rl01:
//                break;
//            case R.id.edit_person_msg_rl02:
//                break;
//            case R.id.edit_person_msg_rl03:
//                break;
//            case R.id.edit_person_msg_rl04:
//                break;
//            case R.id.edit_person_msg_rl05:
//                break;
//            case R.id.edit_person_msg_rl06:
//                break;
//            case R.id.edit_person_msg_rl07:
//                break;
        }
    }
    public void getBitmap(String url){
        RequestParams params=new RequestParams(url);
        x.http().get(params, new Callback.CommonCallback<byte []>() {
            @Override
            public void onSuccess(byte[] result) {
                Bitmap bitmap=BitmapFactory.decodeByteArray(result,0,result.length);
                ivBg.setImageBitmap(GaussianUtil.blurBitmap(bitmap,EditPersonMsgActivity.this));
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
    private void getUserInfo() {
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
                    head_pic=obj.getString("head_pic");
                    x.image().bind(ivIcon,head_pic);
                    tvNickname.setText(obj.getString("nickname"));
                    tvName.setText(obj.getString("nickname"));
                    tvAge.setText(obj.getString("age"));
                    tvStar.setText(obj.getString("starchar"));
                    tvGexing.setText(obj.getString("sign"));
                    tvJob.setText(obj.getString("occupation"));
                    tvSchool.setText(obj.getString("school"));
                    tvHobby.setText(obj.getString("interest"));
                    media=obj.getString("media");
                    sex=obj.getString("sex");
                    if(media.equals("http://bubblefish.jbserver.cn/")){
                        startVoice.setVisibility(View.GONE);
                    }else{
                        startVoice.setVisibility(View.VISIBLE);

                    }
//                    Bitmap bitmap=GetLocalOrNetBitmap(obj.getString("head_pic"));
//                    Log.i("lvzhiwei333", "onSuccess: "+bitmap);
//                    ivBg.setImageBitmap(blurImageAmeliorate(bitmap));
                    if(sex.equals("男")){
                        tvAges.setSelected(true);
                        tvAges.setText("  "+obj.getString("age")+" ");
                        tvAges.setBackgroundResource(R.drawable.conner_man_bg);
                    }else if(obj.getString("sex").equals("女")){
                        tvAges.setSelected(false);
                        tvAges.setText(" "+obj.getString("age")+" ");
                        tvAges.setBackgroundResource(R.drawable.conner_women_bg);
                    }
                    handler.sendEmptyMessage(1);
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
    private void getUserInfo02() {
        RequestParams params=new RequestParams("http://bubblefish.jbserver.cn/api/users/getinformation");
        params.addBodyParameter("uid", MyApp.uid);
        params.addBodyParameter("lat",MyApp.lat_经度);
        params.addBodyParameter("lng",MyApp.lng_纬度);
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Log.i("lvzhiwei222", "onSuccess: "+result);
                try {
                    JSONObject json=new JSONObject(result);
                    JSONObject obj=json.getJSONObject("data");
                    tvNickname.setText(obj.getString("nickname"));
                    tvName.setText(obj.getString("nickname"));
                    tvAge.setText(obj.getString("age"));
                    tvStar.setText(obj.getString("starchar"));
                    tvGexing.setText(obj.getString("sign"));
                    tvJob.setText(obj.getString("occupation"));
                    tvSchool.setText(obj.getString("school"));
                    tvHobby.setText(obj.getString("interest"));
                    media=obj.getString("media");
                    sex=obj.getString("sex");
                    if(media.equals("http://bubblefish.jbserver.cn/")){
                        startVoice.setVisibility(View.GONE);
                    }else{
                        startVoice.setVisibility(View.VISIBLE);

                    }
//                    Bitmap bitmap=GetLocalOrNetBitmap(obj.getString("head_pic"));
//                    Log.i("lvzhiwei333", "onSuccess: "+bitmap);
//                    ivBg.setImageBitmap(blurImageAmeliorate(bitmap));
                    if(sex.equals("男")){
                        tvAges.setSelected(true);
                        tvAges.setText("  "+obj.getString("age")+" ");
                        tvAges.setBackgroundResource(R.drawable.conner_man_bg);
                    }else if(obj.getString("sex").equals("女")){
                        tvAges.setSelected(false);
                        tvAges.setText(" "+obj.getString("age")+" ");
                        tvAges.setBackgroundResource(R.drawable.conner_women_bg);
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
    private void uploadIcon() {
        RequestParams params=new RequestParams("http://bubblefish.jbserver.cn/api/users/headEdit");
        params.addBodyParameter("uid",MyApp.uid);
        params.addBodyParameter("head_pic",head_pic);
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Log.i("uploadicon", "onSuccess: "+result);
                try{
                    JSONObject obj=new JSONObject(result);
                    switch (obj.getString("retcode")){
                        case "4000":
                        case "4001":
                        case "4002":
                        case "4003":
                            Toast.makeText(getApplicationContext(),obj.getString("msg"),Toast.LENGTH_SHORT);
                            break;
                        case "2000":
                            RongIM.getInstance().refreshUserInfoCache(new UserInfo(MyApp.RCuserid, tvName.getText().toString(), Uri.parse(head_pic)));
                            Toast.makeText(getApplicationContext(),obj.getString("msg"),Toast.LENGTH_SHORT);
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
    private void alertDialog() {
        final AlertDialog alertDialog = new AlertDialog.Builder(EditPersonMsgActivity.this).create();
        alertDialog.show();
        Window window = alertDialog.getWindow();
        window.setContentView(R.layout.change_pic);
        alertDialog.setCanceledOnTouchOutside(true);
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
                    return;
                }
                Bundle extras = data.getExtras();
                if (extras != null) {
                    Bitmap photo = PhotoUploadUtils.decodeUriAsBitmap(cropUri,this);
//                    Bitmap photo = extras.getParcelable("data");
                    ByteArrayOutputStream stream = new ByteArrayOutputStream();
//                    Bitmap bitmap=comp(photo);
                    if(photo!=null) {
                        photo.compress(Bitmap.CompressFormat.JPEG, 100, stream);// (0-100)压缩文件
                        //此处可以把Bitmap保存到sd卡中，具体请看：http://www.cnblogs.com/linjiqin/archive/2011/12/28/2304940.html
                        ivIcon.setImageBitmap(photo); //把图片显示在ImageView控件上
                    }
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
        File cropImage = new File(Environment.getExternalStorageDirectory(), "1.jpg");
        try {
            if (cropImage.exists()) {
                cropImage.delete();
            }
            cropImage.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        cropUri = Uri.fromFile(cropImage);
        Intent intent = new Intent("com.android.camera.action.CROP");//调用Android系统自带的一个图片剪裁页面,
        intent.setDataAndType(uri, "image/*");
        intent.putExtra("crop", "true");//进行修剪
        // aspectX aspectY 是宽高的比例
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        // outputX outputY 是裁剪图片宽高
        intent.putExtra("outputX", 500);
        intent.putExtra("outputY", 500);
        intent.putExtra("return-data", false);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, cropUri);
        intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
        startActivityForResult(intent, CROP_REQUEST_CODE);
    }



    @Override
    protected void onResume() {
        super.onResume();
        getUserInfo02();
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(player!=null) {
            player.stop();
            player.release();
        }
    }

    @Override
    public void onCompletion(MediaPlayer mp) {
        startVoice.setOnClickListener(this);

    }
}
