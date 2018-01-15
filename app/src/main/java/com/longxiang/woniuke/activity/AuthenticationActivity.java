package com.longxiang.woniuke.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
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
import android.text.TextUtils;
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

import org.json.JSONObject;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

public class AuthenticationActivity extends AppCompatActivity implements View.OnClickListener {
private ImageView ivBack;
    private EditText etName;
    private TextView tvGender;
    private EditText etSfnum;
    private ImageView ivSFZ;
    private ImageView ivXXZ;
    private Button btnConfirm;
    private String name;
    private String number;
    private TextView title;
    private static final String IMAGE_UNSPECIFIED = "image/*";
    private static final int ALBUM_REQUEST_CODE01 = 1;
    private static final int CAMERA_REQUEST_CODE01 = 2;
    private static final int CROP_REQUEST_CODE01 = 3;
    private static final int ALBUM_REQUEST_CODE02 = 4;
    private static final int CAMERA_REQUEST_CODE02 = 5;
    private static final int CROP_REQUEST_CODE02 = 6;
    private String head_pic;
    private List<String> headString=new ArrayList<>();
    private Uri imageUri;
    public Handler handler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            switch (msg.what){
                case 152:
                    String s = (String) msg.obj;
                    Log.i("lvzhiweiPicture",s);
                    BeanIcon beanicon=new Gson().fromJson(s,BeanIcon.class);
                    head_pic = beanicon.getData();
                    headString.add(head_pic);
                    break;
            }

        }
    };
    private String pictures;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_authentication);
        imageUri = Uri.fromFile(new File(PhotoUploadUtils.IMAGE_FILE_LOCATION));
        setView();
        setListener();
        getUserInfo();
    }

    private void setView() {
        ivBack= (ImageView) findViewById(R.id.authentication_iv_back);
        etName= (EditText) findViewById(R.id.authentication_etName);
        tvGender= (TextView) findViewById(R.id.authentication_tvSex);
        tvGender.setTextColor(Color.parseColor("#000000"));
        etSfnum= (EditText) findViewById(R.id.authentication_etShen);
        ivSFZ= (ImageView) findViewById(R.id.authentication_ivSfZ);
        ivXXZ= (ImageView) findViewById(R.id.authentication_ivXXZ);
        btnConfirm= (Button) findViewById(R.id.authentication_btnConfirm);
        title= (TextView) findViewById(R.id.authentication_tv_title_name);
        title.setTextColor(Color.parseColor("#ffffff"));
        btnConfirm.setTextColor(Color.parseColor("#ffffff"));
    }
    public void  getData(){
        name=etName.getText().toString();
        number=etSfnum.getText().toString();
    }
    private void setListener() {
        ivBack.setOnClickListener(this);
        ivSFZ.setOnClickListener(this);
        ivXXZ.setOnClickListener(this);
        btnConfirm.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.authentication_iv_back:
                finish();
                break;
            case R.id.authentication_ivSfZ:
                alertDialog01();
                break;
            case R.id.authentication_ivXXZ:
                alertDialog02();
                break;
            case R.id.authentication_btnConfirm:
                String goodsPicture="";;
                if(headString.size()!=0) {
                    for(int i=0;i<headString.size();i++){
                        goodsPicture+=headString.get(i)+",";
                        Log.i("lvzhiweiPictureHead",headString.size()+"");
                    }
                    pictures = goodsPicture.substring(0, goodsPicture.length() - 1);
                }else{
                    pictures="";
                }
                if(!TextUtils.isEmpty(etName.getText().toString())&&!TextUtils.isEmpty(etSfnum.getText().toString())&&!pictures.equals("")) {
                    final AlertDialog.Builder builder = new AlertDialog.Builder(this);
                    builder.setTitle("蜗牛壳")
                            .setMessage("确认提交吗?")
                            .setPositiveButton("取消", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            }).setNegativeButton("确定", new DialogInterface.OnClickListener() {

                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            uploadIdCard();
                        }
                    }).create().show();
                }else{
                    Toast.makeText(AuthenticationActivity.this,"请输入完整信息",Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    private void uploadIdCard() {
        getData();
        RequestParams params =new RequestParams("http://bubblefish.jbserver.cn/api/users/setidcard");
        params.addBodyParameter("uid",MyApp.uid);
        params.addBodyParameter("real_name",name);
        params.addBodyParameter("idnum",number);
        Log.i("authentication", "uploadIdCard: "+pictures);
        params.addBodyParameter("card_face",pictures);
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Log.i("authentication", "onSuccess: "+result);
                try{
                    JSONObject obj=new JSONObject(result);
                    switch (obj.getString("retcode")){
                        case "2000":
                            Toast.makeText(getApplicationContext(),obj.getString("msg"),Toast.LENGTH_SHORT).show();
                            finish();
                            break;
                        case "4000":
                        case "4001":
                        case "4002":
                        case "4003":
                        case "4004":
                        case "4005":
                            Toast.makeText(getApplicationContext(),obj.getString("msg"),Toast.LENGTH_SHORT).show();
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
                    String gender = obj.getString("sex");
                    tvGender.setText(gender);
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
    private void alertDialog01() {
        final AlertDialog alertDialog = new AlertDialog.Builder(AuthenticationActivity.this).create();
        alertDialog.show();
        Window window = alertDialog.getWindow();
        window.setContentView(R.layout.change_pic);
        RelativeLayout rlxuan = (RelativeLayout) window.findViewById(R.id.xuan_pic);
        RelativeLayout rlpai = (RelativeLayout) window.findViewById(R.id.pai_pic);
        rlxuan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK, null);
                intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, IMAGE_UNSPECIFIED);
                startActivityForResult(intent, ALBUM_REQUEST_CODE01);
                alertDialog.dismiss();
            }
        });
        rlpai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(new File(Environment.
                        getExternalStorageDirectory(), "/temp.jpg")));
                startActivityForResult(intent, CAMERA_REQUEST_CODE01);
                alertDialog.dismiss();
            }
        });
    }
    private void alertDialog02() {
        final AlertDialog alertDialog = new AlertDialog.Builder(AuthenticationActivity.this).create();
        alertDialog.show();
        Window window = alertDialog.getWindow();
        window.setContentView(R.layout.change_pic);
        RelativeLayout rlxuan = (RelativeLayout) window.findViewById(R.id.xuan_pic);
        RelativeLayout rlpai = (RelativeLayout) window.findViewById(R.id.pai_pic);
        rlxuan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK, null);
                intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, IMAGE_UNSPECIFIED);
                startActivityForResult(intent, ALBUM_REQUEST_CODE02);
                alertDialog.dismiss();
            }
        });
        rlpai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(new File(Environment.
                        getExternalStorageDirectory(), "/temp.jpg")));
                startActivityForResult(intent, CAMERA_REQUEST_CODE02);
                alertDialog.dismiss();
            }
        });
    }
    private void startCrop01(Uri uri) {
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
        intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
        intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
        startActivityForResult(intent, CROP_REQUEST_CODE01);
    }
    private void startCrop02(Uri uri) {
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
        intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
        intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
        startActivityForResult(intent, CROP_REQUEST_CODE02);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case ALBUM_REQUEST_CODE01:
                if (data == null) {
                    return;
                }
                startCrop01(data.getData());
                break;
            case CAMERA_REQUEST_CODE01:
                Log.e("12345", "onActivityResult: "+data );
                File picture01 = new File(Environment.getExternalStorageDirectory()
                        + "/temp.jpg");
                startCrop01(Uri.fromFile(picture01));
                break;
            case CROP_REQUEST_CODE01:
              try{
                  if (data == null) {
                      // TODO 如果之前以后有设置过显示之前设置的图片 否则显示默认的图片
                      return;
                  }
                  Bundle extras1 = data.getExtras();
                  if (extras1 != null) {
                      Bitmap photo = PhotoUploadUtils.decodeUriAsBitmap(imageUri,this);
//                    Bitmap photo = extras.getParcelable("data");
                      ByteArrayOutputStream stream = new ByteArrayOutputStream();
//                    Bitmap bitmap=comp(photo);
                      photo.compress(Bitmap.CompressFormat.JPEG, 100, stream);// (0-100)压缩文件
                      //此处可以把Bitmap保存到sd卡中，具体请看：http://www.cnblogs.com/linjiqin/archive/2011/12/28/2304940.html
                      ivSFZ.setImageBitmap(photo); //把图片显示在ImageView控件上
                      // 获得字节流
                      ByteArrayInputStream is = new ByteArrayInputStream(stream.toByteArray());
                      PhotoUploadTask put = new PhotoUploadTask(
                              "http://bubblefish.jbserver.cn/api/api/fileUpload", is,
                              this, handler);
                      put.start();
                      ivSFZ.setClickable(false);
                  }
              }catch (Exception e){
                  e.printStackTrace();
              }
                break;
            case ALBUM_REQUEST_CODE02:
                if (data == null) {
                    return;
                }
                startCrop02(data.getData());
                break;
            case CAMERA_REQUEST_CODE02:
                Log.e("12345", "onActivityResult: "+data );
                File picture02 = new File(Environment.getExternalStorageDirectory()
                        + "/temp.jpg");
                startCrop02(Uri.fromFile(picture02));
                break;
            case CROP_REQUEST_CODE02:
              try {
                  if (data == null) {
                      // TODO 如果之前以后有设置过显示之前设置的图片 否则显示默认的图片
                      return;
                  }
                  Bundle extras2 = data.getExtras();
                  if (extras2 != null) {
                      Bitmap photo = PhotoUploadUtils.decodeUriAsBitmap(imageUri,this);
//                    Bitmap photo = extras.getParcelable("data");
                      ByteArrayOutputStream stream = new ByteArrayOutputStream();
//                    Bitmap bitmap=comp(photo);
                      photo.compress(Bitmap.CompressFormat.JPEG, 100, stream);// (0-100)压缩文件
                      //此处可以把Bitmap保存到sd卡中，具体请看：http://www.cnblogs.com/linjiqin/archive/2011/12/28/2304940.html
                      ivXXZ.setImageBitmap(photo); //把图片显示在ImageView控件上
                      // 获得字节流
                      ByteArrayInputStream is = new ByteArrayInputStream(stream.toByteArray());
                      PhotoUploadTask put = new PhotoUploadTask(
                              "http://bubblefish.jbserver.cn/api/api/fileUpload", is,
                              this, handler);
                      put.start();
                      ivXXZ.setClickable(false);
                  }
              }catch (Exception e){
                  e.printStackTrace();
              }
                break;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}
