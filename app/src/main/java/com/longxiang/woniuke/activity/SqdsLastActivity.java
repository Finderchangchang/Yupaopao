package com.longxiang.woniuke.activity;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.longxiang.woniuke.R;
import com.longxiang.woniuke.adapter.JobAdapter;
import com.longxiang.woniuke.adapter.SqlvAdapter;
import com.longxiang.woniuke.bean.BeanIcon;
import com.longxiang.woniuke.bean.JobData;
import com.longxiang.woniuke.bean.SqlvData;
import com.longxiang.woniuke.utils.MyApp;
import com.longxiang.woniuke.utils.PhotoUploadTask;

import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.common.Callback;
import org.xutils.common.util.DensityUtil;
import org.xutils.http.RequestParams;
import org.xutils.image.ImageOptions;
import org.xutils.x;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class SqdsLastActivity extends AppCompatActivity implements View.OnClickListener {
    private ImageView ivBack;
    private TextView tvTitle;
    private String pid;
    private String title;
    private TextView tvlv;
    private TextView tvIntro;
    private TextView tvMoney;
    private ImageView ivPic;
    private RelativeLayout rl01;
    private RelativeLayout rl02;
    //    private RelativeLayout rl03;
    private Button btnConfirm;
    private static final String IMAGE_UNSPECIFIED = "image/*";
    private static final int ALBUM_REQUEST_CODE = 1;
    private static final int CAMERA_REQUEST_CODE = 2;
    private static final int CROP_REQUEST_CODE = 4;
    private Uri imageUri;
    private static final String IMAGE_FILE_LOCATION = Environment.getExternalStorageDirectory().getAbsolutePath()+"/temp.jpg";
    private String head_pic;
    private String lvname;
    private SqlvData data;
    private String lvid;
    private ListView listView;
    private Button btnEdit;
    private TextView tv;
    //    private List<String> pricelist=new ArrayList<>();
    private android.support.v7.app.AlertDialog alertDialoglv;
    private String price;
    ImageOptions imageOptions = new ImageOptions.Builder()
            .setSize(DensityUtil.dip2px(800), DensityUtil.dip2px(700))
            .setRadius(DensityUtil.dip2px(5))
//            .setCrop(true)
            // 加载中或错误图片的ScaleType
//            .setPlaceholderScaleType(ImageView.ScaleType.MATRIX)
            .setImageScaleType(ImageView.ScaleType.FIT_XY)
            //设置加载过程中的图片
            .setLoadingDrawableId(R.mipmap.woniu)
            //设置加载失败后的图片
            .setFailureDrawableId(R.mipmap.woniu)
            //设置使用缓存
            .setUseMemCache(true)
            //设置支持gif
            .setIgnoreGif(false)
            //设置显示圆形图片
            .setCircular(false)
            .setSquare(true)
            .build();
    Handler handler =new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case 152:
                    String s = (String) msg.obj;
                    Log.i("icon",s);
                    BeanIcon beanicon=new Gson().fromJson(s,BeanIcon.class);
                    head_pic= beanicon.getData();
                    break;
                case 2:
                    listView.setAdapter(new SqlvAdapter(SqdsLastActivity.this,data.getData()));
                    listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            Log.i("lvlvlv", "onItemClick: "+position);
                            lvid=data.getData().get(position).getId();
                            lvname=data.getData().get(position).getTitle();
//                            pricelist.addAll(data.getData().get(position).getPrice());
                            price=data.getData().get(position).getPrice().get(0);
                            tvlv.setText(lvname);
                            alertDialoglv.dismiss();
                        }
                    });
                    break;
            }
            super.handleMessage(msg);
        }
    };
    private String bgid;
    private String updjid;
    private Uri cropUri;
//    private ListView listView02;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sqds_last);
//        imageUri = Uri.fromFile(new File(IMAGE_FILE_LOCATION));
        Intent intent=getIntent();
        pid=intent.getStringExtra("pid");
        title=intent.getStringExtra("title");
        setView();
        setListener();
        getGodInfoByBgid();
    }

    private void getGodInfoByBgid() {
        RequestParams params=new RequestParams("http://bubblefish.jbserver.cn/api/biggods/getGodInfoByBgid");
        params.addBodyParameter("uid",MyApp.uid);
        params.addBodyParameter("jnid",pid);
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Log.i("34567", "onSuccess: "+result);
                try {
                    JSONObject json=new JSONObject(result);
                    if(json.getString("retcode").equals("2000")) {
                        JSONObject obj = json.getJSONObject("data");
                        btnConfirm.setText("升级");
                        updjid=obj.getString("updjid");
                        if(!updjid.equals("0")){
                            btnConfirm.setText("升级审核中");
                            btnConfirm.setTextColor(Color.parseColor("#eeeeee"));
                            btnConfirm.setClickable(false);
                        }
                        String pic = obj.getString("pic");
                        String level = obj.getString("level");
                        String explain = obj.getString("explain");
                        bgid=obj.getString("bgid");
                        x.image().bind(ivPic, pic,imageOptions);
                        tvIntro.setText(explain);
                        tvlv.setText(level);
                        btnEdit.setVisibility(View.VISIBLE);
                        tv.setVisibility(View.VISIBLE);
                    }
                    if(json.getString("retcode").equals("4001")){
                        btnEdit.setVisibility(View.GONE);
                        tv.setVisibility(View.GONE);
                        btnConfirm.setText("提交审核");
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

    private void setView() {
        btnEdit= (Button) findViewById(R.id.sqdslast_btnedit);
        btnEdit.setTextColor(Color.parseColor("#ffffff"));
        tv= (TextView) findViewById(R.id.sqdslast_tv);
        ivBack= (ImageView) findViewById(R.id.sqdslast_iv_back);
        tvTitle= (TextView) findViewById(R.id.sqdslast_tv_title_name);
        tvTitle.setText(title+"资质");
        tvTitle.setTextColor(Color.parseColor("#ffffff"));
//        tvMoney= (TextView) findViewById(R.id.sqdslast_tvMoney);
        ivPic= (ImageView) findViewById(R.id.sqdslast_iv);
        rl01= (RelativeLayout) findViewById(R.id.sqdslast_rl_rl01);
        rl02= (RelativeLayout) findViewById(R.id.sqdslast_rl_rl02);
//        rl03= (RelativeLayout) findViewById(R.id.sqdslast_rl_rl03);
        btnConfirm= (Button) findViewById(R.id.sqdslast_btn_confirm);
        btnConfirm.setTextColor(Color.parseColor("#ffffff"));
        tvlv= (TextView) findViewById(R.id.sqdslast_tvlv);
        tvIntro= (TextView) findViewById(R.id.sqdslast_tvIntro);
    }

    private void setListener() {
        ivBack.setOnClickListener(this);
        ivPic.setOnClickListener(this);
        rl01.setOnClickListener(this);
        rl02.setOnClickListener(this);
//        rl03.setOnClickListener(this);
        btnConfirm.setOnClickListener(this);
        btnEdit.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.sqdslast_iv_back:
                finish();
                break;
            case R.id.sqdslast_iv:
                alertDialog();
                break;
            case R.id.sqdslast_rl_rl01:
                lvdialog();
                break;
            case R.id.sqdslast_rl_rl02:
                Intent intent=new Intent(this,SqdsIntroActivity.class);
                startActivityForResult(intent,601);
                break;
//            case R.id.sqdslast_rl_rl03:
//                if(tvlv.getText().toString().equals("")){
//                    Toast.makeText(getApplicationContext(),"请选择您的等级",Toast.LENGTH_SHORT).show();
//                }else {
////                    Intent intent1 = new Intent(this, PriceActivity.class);
////                    startActivityForResult(intent1, 602);
//                    pricedialog();
//                }
//                break;
            case R.id.sqdslast_btn_confirm:
                btnConfirm.setOnClickListener(null);
                if(btnConfirm.getText().toString().equals("升级")){
                    LevelUp();
                }
                if(btnConfirm.getText().toString().equals("提交审核")){
                    sendSqds();
                }
                if(btnConfirm.getText().toString().equals("")){
                    Toast.makeText(getApplicationContext(),"网络不佳，请返回重试...",Toast.LENGTH_SHORT).show();
                    btnConfirm.setOnClickListener(SqdsLastActivity.this);
                }
                break;
            case R.id.sqdslast_btnedit:
                updateGodPic();
                break;
        }
    }

    private void LevelUp() {
        RequestParams params=new RequestParams("http://bubblefish.jbserver.cn/api/biggods/LevelUp");
        params.addBodyParameter("bgid",bgid);
        params.addBodyParameter("updjid",lvid);
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Log.i("sqdslastactivity2", "onSuccess: "+result+lvid+"----"+bgid);
                try {
                    JSONObject obj=new JSONObject(result);
                    switch (obj.getString("retcode")){
                        case "2000":
                            Toast.makeText(getApplicationContext(),obj.getString("msg"),Toast.LENGTH_SHORT).show();
                            finish();
                            break;
                        case "4001":
                            Toast.makeText(getApplicationContext(),obj.getString("msg"),Toast.LENGTH_SHORT).show();
                            btnConfirm.setOnClickListener(SqdsLastActivity.this);
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

    private void updateGodPic() {
        RequestParams params=new RequestParams("http://bubblefish.jbserver.cn/api/biggods/updateGodPic");
        params.addBodyParameter("bgid",bgid);
        params.addBodyParameter("explain",tvIntro.getText().toString());
        params.addBodyParameter("pic",head_pic);
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Log.i("sqdslastactivity", "onSuccess: "+result);
                try {
                    JSONObject obj=new JSONObject(result);
                    switch (obj.getString("retcode")){
                        case "2000":
                            Toast.makeText(getApplicationContext(),obj.getString("msg"),Toast.LENGTH_SHORT).show();
                            finish();
                            break;
                        case "4001":
                            Toast.makeText(getApplicationContext(),obj.getString("msg"),Toast.LENGTH_SHORT).show();
                            btnConfirm.setOnClickListener(SqdsLastActivity.this);
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

    private void sendSqds() {
        RequestParams params=new RequestParams("http://bubblefish.jbserver.cn/api/biggods/askforgod");
        params.addBodyParameter("uid", MyApp.uid);
        params.addBodyParameter("jnid",pid);
        params.addBodyParameter("djid",lvid);
        params.addBodyParameter("explain",tvIntro.getText().toString());
        params.addBodyParameter("pic",head_pic);
        params.addBodyParameter("price",price);
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Log.i("lvlvasd",result);
                try{
                    JSONObject obj=new JSONObject(result);
                    switch (obj.getString("retcode")){
                        case "2000":
                            Toast.makeText(getApplicationContext(),obj.getString("msg"),Toast.LENGTH_SHORT).show();
                            finish();
                            SqxmActivity.instance.finish();
                            SqdsActivity.instance.finish();
                            break;
                        case "4000":
                        case "4001":
                        case "4002":
                        case "4003":
                        case "4004":
                        case "4005":
                            Toast.makeText(getApplicationContext(),obj.getString("msg"),Toast.LENGTH_SHORT).show();
                            btnConfirm.setOnClickListener(SqdsLastActivity.this);
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

    private void lvdialog() {
        alertDialoglv = new android.support.v7.app.AlertDialog.Builder(SqdsLastActivity.this).create();
        alertDialoglv.show();
        listView=new ListView(this);
        Window window = alertDialoglv.getWindow();
        window.setContentView(listView);
        getLv();
    }
    //    private void pricedialog() {
//        alertDialoglv = new android.support.v7.app.AlertDialog.Builder(SqdsLastActivity.this).create();
//        alertDialoglv.show();
//        listView02=new ListView(this);
//        Window window = alertDialoglv.getWindow();
//        window.setContentView(listView02);
//        if(pricelist.size()!=0) {
//            listView02.setAdapter(new ArrayAdapter<String>(this,R.layout.item_job,R.id.item_job_tv,pricelist));
//        }
//        listView02.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                String price=pricelist.get(position);
//                tvMoney.setText(price+"元/"+unit);
//                alertDialoglv.dismiss();
//            }
//        });
//    }
    private void getLv() {
        RequestParams params=new RequestParams("http://bubblefish.jbserver.cn/api/order/getRank");
        params.addBodyParameter("pid",pid);
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Log.i("sqdslastactivity", "onSuccess: "+result);
                data=new Gson().fromJson(result,SqlvData.class);
                handler.sendEmptyMessage(2);
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
        final AlertDialog alertDialog = new AlertDialog.Builder(SqdsLastActivity.this).create();
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
                    Bitmap photo = decodeUriAsBitmap(cropUri);
                    ByteArrayOutputStream stream = new ByteArrayOutputStream();
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
            case 601:
                try{
                    tvIntro.setText(data.getStringExtra("lvintro"));
                }catch (Exception e){e.printStackTrace();}
                break;
//            case 602:
//                try{
//                    tvMoney.setText(data.getStringExtra("price")+"元");
//                }catch (Exception e){e.printStackTrace();}
//                break;
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
    private Bitmap decodeUriAsBitmap(Uri uri) {
        Bitmap bitmap = null;
        try {
            // 先通过getContentResolver方法获得一个ContentResolver实例，
            // 调用openInputStream(Uri)方法获得uri关联的数据流stream
            // 把上一步获得的数据流解析成为bitmap
            bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(uri));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        }
        return bitmap;
    }
}
