package com.longxiang.woniuke.activity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Environment;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
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

import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

public class FabuDtActivity extends AppCompatActivity implements View.OnClickListener {
private TextView tvBack;
    private TextView tvFabu;
    private ImageView ivPic;
    private EditText etThink;
    private Button btnFenxiang;
    private ImageView ivFenxiang;
    private TextView tvZicount;
    private int count=0;
    private static final String IMAGE_UNSPECIFIED = "image/*";
    private static final int ALBUM_REQUEST_CODE = 1;
    private static final int CAMERA_REQUEST_CODE = 2;
    private static final int CROP_REQUEST_CODE = 4;
    private String head_pic;
    private String pic;
    private TextView title;
    private Uri imageUri;
    public Handler handler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            switch (msg.what){
                case 152:
                    String s = (String) msg.obj;
                    Log.i("icon",s);
                    BeanIcon beanicon=new Gson().fromJson(s,BeanIcon.class);
                    pic= beanicon.getData();
                    Log.i("lvzhiweihaha",head_pic);
                    break;
            }

        }
    };
    private String name;
    private String sex;
    private Uri cropUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fabu_dt);
//        imageUri = Uri.fromFile(new File(PhotoUploadUtils.IMAGE_FILE_LOCATION));
        setView();
        setListener();
        getUserInfo();
    }
    private void getUserInfo() {
        RequestParams params=new RequestParams("http://bubblefish.jbserver.cn/api/users/getmineinfo");
        params.addBodyParameter("uid", MyApp.uid);
        Log.i("lvzhiwei222", "getUserInfo: "+MyApp.uid);
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Log.i("lvzhiwei222", "onSuccess: "+result);
                try {
                    JSONObject json=new JSONObject(result);
                    JSONObject obj=json.getJSONObject("data");
                    head_pic=obj.getString("head_pic");
                    name=obj.getString("nickname");
                    sex=obj.getString("sex");
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
        tvBack= (TextView) findViewById(R.id.fabudt_tv_back);
        tvFabu= (TextView) findViewById(R.id.fabudt_tv_fabu);
        ivPic= (ImageView) findViewById(R.id.fabudt_iv_pic);
        etThink= (EditText) findViewById(R.id.fabudt_et_think);
        title= (TextView) findViewById(R.id.fabudt_tv_title_name);
        title.setTextColor(Color.parseColor("#ffffff"));
        tvBack.setTextColor(Color.parseColor("#ffffff"));
        tvFabu.setTextColor(Color.parseColor("#ffffff"));
//        btnFenxiang= (Button) findViewById(R.id.fabudt_btn_fenxiang);
//        ivFenxiang= (ImageView) findViewById(R.id.fabudt_iv_fenxiang);
        tvZicount= (TextView) findViewById(R.id.fabudt_tv_zicount);
    }

    private void setListener() {
        tvBack.setOnClickListener(this);
        tvFabu.setOnClickListener(this);
        ivPic.setOnClickListener(this);
//        btnFenxiang.setOnClickListener(this);
        etThink.addTextChangedListener(new EditIntroChangedListener());
    }

    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()){
            case R.id.fabudt_tv_back:
                count=0;
                finish();
                break;
            case R.id.fabudt_tv_fabu:
                send();
                break;
            case R.id.fabudt_iv_pic:
                alertDialog();
                break;
//            case R.id.fabudt_btn_fenxiang:
//                if(count==0){
//                    ivFenxiang.setVisibility(View.VISIBLE);
//                    count=1;
//                }else if(count==1){
//                    ivFenxiang.setVisibility(View.GONE);
//                    count=0;
//                }
//                break;
        }
    }

    private void send() {
        RequestParams params=new RequestParams("http://bubblefish.jbserver.cn/api/friend/publishDynamic");
        params.addBodyParameter("uid", MyApp.uid);
        params.addBodyParameter("pic",pic);
        params.addBodyParameter("content",etThink.getText().toString());
        params.addBodyParameter("nickname",name);
        params.addBodyParameter("sex",sex);
        params.addBodyParameter("head_pic",head_pic);
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Log.i("fabudt", "onSuccess: "+result);
                try {
                    JSONObject obj=new JSONObject(result);
                    switch (obj.getString("retcode")){
                        case "4000":
                        case "4001":
                        case "4002":
                        case "4003":
                            Toast.makeText(getApplicationContext(),obj.getString("msg"),Toast.LENGTH_SHORT).show();
                            break;
                        case "2000":
                            Toast.makeText(getApplicationContext(),obj.getString("msg"),Toast.LENGTH_SHORT).show();
                            finish();
                            break;
                    }
                } catch (JSONException e) {
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
        final AlertDialog alertDialog = new AlertDialog.Builder(FabuDtActivity.this).create();
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
    class EditIntroChangedListener implements TextWatcher {
        private CharSequence temp;//监听前的文本

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            temp = s;
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            tvZicount.setText(s.length()+"/50");

        }

        @Override
        public void afterTextChanged(Editable s) {
            if (temp.length()==50) {
                Toast.makeText(FabuDtActivity.this, "字数已经达到了限制！", Toast.LENGTH_LONG).show();
            }
        }
    };

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
                      Bitmap photo = PhotoUploadUtils.decodeUriAsBitmap(cropUri,this);
//                    Bitmap photo = extras.getParcelable("data");
                      ByteArrayOutputStream stream = new ByteArrayOutputStream();
//                    Bitmap bitmap=comp(photo);
                    if(photo!=null) {
                        photo.compress(Bitmap.CompressFormat.JPEG, 100, stream);// (0-100)压缩文件
                        //此处可以把Bitmap保存到sd卡中，具体请看：http://www.cnblogs.com/linjiqin/archive/2011/12/28/2304940.html
                        ivPic.setImageBitmap(photo); //把图片显示在ImageView控件上
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
        intent.putExtra("aspectX",16);
        intent.putExtra("aspectY",13);
        // outputX outputY 是裁剪图片宽高
        intent.putExtra("outputX",800);
        intent.putExtra("outputY",650);
        intent.putExtra("return-data", false);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, cropUri);
        intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
        startActivityForResult(intent, CROP_REQUEST_CODE);
    }

}
