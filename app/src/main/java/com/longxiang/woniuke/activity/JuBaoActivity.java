package com.longxiang.woniuke.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
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
import java.util.ArrayList;
import java.util.List;

public class JuBaoActivity extends AppCompatActivity implements View.OnClickListener {
    private ImageView ivBack;
    private TextView title;
    private TextView tvname;
    private EditText etcontent;
//    private ListView listView;
    private ImageView iv01;
    private ImageView iv02;
    private ImageView iv03;
    private ImageView iv04;
    private Button btnCommit;
    private String fid;
    private String name;
    private static final String IMAGE_UNSPECIFIED = "image/*";
    private static final int ALBUM_REQUEST_CODE01 = 1;
    private static final int CAMERA_REQUEST_CODE01 = 2;
    private static final int CROP_REQUEST_CODE01 = 3;
    private static final int ALBUM_REQUEST_CODE02 = 4;
    private static final int CAMERA_REQUEST_CODE02 = 5;
    private static final int CROP_REQUEST_CODE02 = 6;
    private static final int ALBUM_REQUEST_CODE03 = 7;
    private static final int CAMERA_REQUEST_CODE03 = 8;
    private static final int CROP_REQUEST_CODE03 = 9;
    private static final int ALBUM_REQUEST_CODE04 = 10;
    private static final int CAMERA_REQUEST_CODE04 = 11;
    private static final int CROP_REQUEST_CODE04 = 12;
    private String head_pic;
    private String pictures;
    private Uri imageUri;
    private List<String> headString=new ArrayList<>();
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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ju_bao);
        imageUri = Uri.fromFile(new File(PhotoUploadUtils.IMAGE_FILE_LOCATION));
        Intent intent=getIntent();
        fid=intent.getStringExtra("uid");
        name=intent.getStringExtra("name");
        setView();
        setListener();
    }

    private void setView() {
        ivBack= (ImageView) findViewById(R.id.jubao_iv_back);
        title= (TextView) findViewById(R.id.jubao_tv_title_name);
        title.setTextColor(Color.parseColor("#ffffff"));
        tvname= (TextView) findViewById(R.id.jubao_tv_name);
        tvname.setTextColor(Color.parseColor("#000000"));
        tvname.setText(name);
        etcontent= (EditText) findViewById(R.id.jubao_et);
        etcontent.setTextColor(Color.parseColor("#000000"));
//        listView= (ListView) findViewById(R.id.jubao_listview);
        iv01= (ImageView) findViewById(R.id.jubao_iv01);
        iv02= (ImageView) findViewById(R.id.jubao_iv02);
        iv03= (ImageView) findViewById(R.id.jubao_iv03);
        iv04= (ImageView) findViewById(R.id.jubao_iv04);
        btnCommit= (Button) findViewById(R.id.jubao_btnConfirm);
        btnCommit.setTextColor(Color.parseColor("#ffffff"));
    }

    private void setListener() {
        ivBack.setOnClickListener(this);
        iv01.setOnClickListener(this);
        iv02.setOnClickListener(this);
        iv03.setOnClickListener(this);
        iv04.setOnClickListener(this);
        btnCommit.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.jubao_iv_back:
                finish();
                break;
            case R.id.jubao_iv01:
                alertDialog01();
                break;
            case R.id.jubao_iv02:
                alertDialog02();
                break;
            case R.id.jubao_iv03:
                alertDialog03();
                break;
            case R.id.jubao_iv04:
                alertDialog04();
                break;
            case R.id.jubao_btnConfirm:
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
                if(!TextUtils.isEmpty(etcontent.getText().toString())) {
                    final AlertDialog.Builder builder = new AlertDialog.Builder(this);
                    builder.setTitle("蜗牛壳")
                            .setMessage("确认提交举报信息?")
                            .setPositiveButton("取消", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            }).setNegativeButton("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            sendJbMsg();
                            finish();
                        }
                    }).create().show();
                }else{
                    Toast.makeText(JuBaoActivity.this,"请输入举报信息",Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }
    private void sendJbMsg() {
        RequestParams params=new RequestParams("http://bubblefish.jbserver.cn/api/users/report");
        params.addBodyParameter("uid", MyApp.uid);
        params.addBodyParameter("report",etcontent.getText().toString());
        params.addBodyParameter("images",pictures);
        Log.i("lvzhiweiPictureHead", "sendJbMsg: "+pictures);
        params.addBodyParameter("otheruid",fid);
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Log.i("lvzhiweiPictureHead", "onSuccess: "+result);
                try{
                    JSONObject obj =new JSONObject(result);
                    switch (obj.getString("retcode")){
                        case "4000":
                        case "4001":
                        case "4002":
                            Toast.makeText(JuBaoActivity.this,obj.getString("msg"),Toast.LENGTH_SHORT).show();
                            break;
                        case "2000":
                            Toast.makeText(JuBaoActivity.this,obj.getString("msg"),Toast.LENGTH_SHORT).show();
                            finish();
                            break;
                    }
                }catch (Exception e){

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
        final AlertDialog alertDialog = new AlertDialog.Builder(JuBaoActivity.this).create();
        alertDialog.setCanceledOnTouchOutside(true);
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
        final AlertDialog alertDialog = new AlertDialog.Builder(JuBaoActivity.this).create();
        alertDialog.setCanceledOnTouchOutside(true);
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
    private void alertDialog03() {
        final AlertDialog alertDialog = new AlertDialog.Builder(JuBaoActivity.this).create();
        alertDialog.setCanceledOnTouchOutside(true);
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
                startActivityForResult(intent, ALBUM_REQUEST_CODE03);
                alertDialog.dismiss();
            }
        });
        rlpai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(new File(Environment.
                        getExternalStorageDirectory(), "/temp.jpg")));
                startActivityForResult(intent, CAMERA_REQUEST_CODE03);
                alertDialog.dismiss();
            }
        });
    }
    private void alertDialog04() {
        final AlertDialog alertDialog = new AlertDialog.Builder(JuBaoActivity.this).create();
        alertDialog.setCanceledOnTouchOutside(true);
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
                startActivityForResult(intent, ALBUM_REQUEST_CODE04);
                alertDialog.dismiss();
            }
        });
        rlpai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(new File(Environment.
                        getExternalStorageDirectory(), "/temp.jpg")));
                startActivityForResult(intent, CAMERA_REQUEST_CODE04);
                alertDialog.dismiss();
            }
        });
    }
    private void startCrop01(Uri uri) {
        Intent intent = new Intent("com.android.camera.action.CROP");//调用Android系统自带的一个图片剪裁页面,
        intent.setDataAndType(uri, "image/*");
        intent.putExtra("crop", "true");//进行修剪
        intent.putExtra("aspectX",13);
        intent.putExtra("aspectY",16);
        // outputX outputY 是裁剪图片宽高
        intent.putExtra("outputX",650);
        intent.putExtra("outputY",800);
        intent.putExtra("return-data", false);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
        intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
        startActivityForResult(intent, CROP_REQUEST_CODE01);
    }
    private void startCrop02(Uri uri) {
        Intent intent = new Intent("com.android.camera.action.CROP");//调用Android系统自带的一个图片剪裁页面,
        intent.setDataAndType(uri, "image/*");
        intent.putExtra("crop", "true");//进行修剪
        intent.putExtra("aspectX",13);
        intent.putExtra("aspectY",16);
        // outputX outputY 是裁剪图片宽高
        intent.putExtra("outputX",650);
        intent.putExtra("outputY",800);
        intent.putExtra("return-data", false);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
        intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
        startActivityForResult(intent, CROP_REQUEST_CODE02);
    }
    private void startCrop03(Uri uri) {
        Intent intent = new Intent("com.android.camera.action.CROP");//调用Android系统自带的一个图片剪裁页面,
        intent.setDataAndType(uri, "image/*");
        intent.putExtra("crop", "true");//进行修剪
        intent.putExtra("aspectX",13);
        intent.putExtra("aspectY",16);
        // outputX outputY 是裁剪图片宽高
        intent.putExtra("outputX",650);
        intent.putExtra("outputY",800);
        intent.putExtra("return-data", false);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
        intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
        startActivityForResult(intent, CROP_REQUEST_CODE03);
    }
    private void startCrop04(Uri uri) {
        Intent intent = new Intent("com.android.camera.action.CROP");//调用Android系统自带的一个图片剪裁页面,
        intent.setDataAndType(uri, "image/*");
        intent.putExtra("crop", "true");//进行修剪
        intent.putExtra("aspectX",13);
        intent.putExtra("aspectY",16);
        // outputX outputY 是裁剪图片宽高
        intent.putExtra("outputX",650);
        intent.putExtra("outputY",800);
        intent.putExtra("return-data", false);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
        intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
        startActivityForResult(intent, CROP_REQUEST_CODE04);
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
                       photo.compress(Bitmap.CompressFormat.JPEG, 100, stream);// (0-100)压缩文件
                       //此处可以把Bitmap保存到sd卡中，具体请看：http://www.cnblogs.com/linjiqin/archive/2011/12/28/2304940.html
                       iv01.setImageBitmap(photo); //把图片显示在ImageView控件上
                       iv02.setVisibility(View.VISIBLE);
                       // 获得字节流
                       ByteArrayInputStream is = new ByteArrayInputStream(stream.toByteArray());
                       PhotoUploadTask put = new PhotoUploadTask(
                               "http://bubblefish.jbserver.cn/api/api/fileUpload", is,
                               this, handler);
                       put.start();
                       iv01.setClickable(false);
                   }
               }catch (Exception e){e.printStackTrace();}
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
                       photo.compress(Bitmap.CompressFormat.JPEG, 100, stream);// (0-100)压缩文件
                       //此处可以把Bitmap保存到sd卡中，具体请看：http://www.cnblogs.com/linjiqin/archive/2011/12/28/2304940.html
                       iv02.setImageBitmap(photo); //把图片显示在ImageView控件上
                       iv03.setVisibility(View.VISIBLE);
                       // 获得字节流
                       ByteArrayInputStream is = new ByteArrayInputStream(stream.toByteArray());
                       PhotoUploadTask put = new PhotoUploadTask(
                               "http://bubblefish.jbserver.cn/api/api/fileUpload", is,
                               this, handler);
                       put.start();
                       iv02.setClickable(false);
                   }
               }catch (Exception e){
                   e.printStackTrace();
               }
                break;
            case ALBUM_REQUEST_CODE03:
                if (data == null) {
                    return;
                }
                startCrop03(data.getData());
                break;
            case CAMERA_REQUEST_CODE03:
                Log.e("12345", "onActivityResult: "+data );
                File picture03 = new File(Environment.getExternalStorageDirectory()
                        + "/temp.jpg");
                startCrop03(Uri.fromFile(picture03));
                break;
            case CROP_REQUEST_CODE03:
               try {
                   if (data == null) {
                       // TODO 如果之前以后有设置过显示之前设置的图片 否则显示默认的图片
                       return;
                   }
                   Bundle extras3 = data.getExtras();
                   if (extras3 != null) {
                       Bitmap photo = PhotoUploadUtils.decodeUriAsBitmap(imageUri,this);
//                    Bitmap photo = extras.getParcelable("data");
                       ByteArrayOutputStream stream = new ByteArrayOutputStream();
                       photo.compress(Bitmap.CompressFormat.JPEG, 100, stream);// (0-100)压缩文件
                       //此处可以把Bitmap保存到sd卡中，具体请看：http://www.cnblogs.com/linjiqin/archive/2011/12/28/2304940.html
                       iv03.setImageBitmap(photo); //把图片显示在ImageView控件上
                       iv04.setVisibility(View.VISIBLE);
                       // 获得字节流
                       ByteArrayInputStream is = new ByteArrayInputStream(stream.toByteArray());
                       PhotoUploadTask put = new PhotoUploadTask(
                               "http://bubblefish.jbserver.cn/api/api/fileUpload", is,
                               this, handler);
                       put.start();
                       iv03.setClickable(false);
                   }
               }catch (Exception e){
                   e.printStackTrace();
               }
                break;
            case ALBUM_REQUEST_CODE04:
                if (data == null) {
                    return;
                }
                startCrop04(data.getData());
                break;
            case CAMERA_REQUEST_CODE04:
                Log.e("12345", "onActivityResult: "+data );
                File picture04 = new File(Environment.getExternalStorageDirectory()
                        + "/temp.jpg");
                startCrop04(Uri.fromFile(picture04));
                break;
            case CROP_REQUEST_CODE04:
               try {
                   if (data == null) {
                       // TODO 如果之前以后有设置过显示之前设置的图片 否则显示默认的图片
                       return;
                   }
                   Bundle extras4 = data.getExtras();
                   if (extras4 != null) {
                       Bitmap photo = PhotoUploadUtils.decodeUriAsBitmap(imageUri,this);
//                    Bitmap photo = extras.getParcelable("data");
                       ByteArrayOutputStream stream = new ByteArrayOutputStream();
                       photo.compress(Bitmap.CompressFormat.JPEG, 100, stream);// (0-100)压缩文件
                       //此处可以把Bitmap保存到sd卡中，具体请看：http://www.cnblogs.com/linjiqin/archive/2011/12/28/2304940.html
                       iv04.setImageBitmap(photo); //把图片显示在ImageView控件上
                       // 获得字节流
                       ByteArrayInputStream is = new ByteArrayInputStream(stream.toByteArray());
                       PhotoUploadTask put = new PhotoUploadTask(
                               "http://bubblefish.jbserver.cn/api/api/fileUpload", is,
                               this, handler);
                       put.start();
                       iv04.setClickable(false);
                   }
               }catch (Exception e){
                   e.printStackTrace();
               }
                break;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}
