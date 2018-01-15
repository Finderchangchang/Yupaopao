package com.longxiang.woniuke.activity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.longxiang.woniuke.R;
import com.longxiang.woniuke.adapter.CommentAdapter;
import com.longxiang.woniuke.bean.DynamicDetailData;
import com.longxiang.woniuke.utils.DashangDialog;
import com.longxiang.woniuke.utils.FavorLayout;
import com.longxiang.woniuke.utils.MyApp;

import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.common.Callback;
import org.xutils.common.util.DensityUtil;
import org.xutils.http.RequestParams;
import org.xutils.image.ImageOptions;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

public class DynamicDetailActivity extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemClickListener {
    private TextView tvtitle;
    private String dmid;
    private TextView back;
    private ImageView icon;
    private TextView name;
    private TextView gender;
    private TextView tvdistance;
    private TextView tvtime;
    private TextView tvcontenrt;
    private ImageView pic;
    private TextView zancount;
    private ImageView ckzan;
    private EditText edittext;
    private Button send;
    private String distance;
    private String time;
    private Button btngz;
    private ListView listview;
    private TextView delete;
    private String uid;
    private FavorLayout mfavorlayout;
    private DynamicDetailData data;
    private int landtime;
    private DynamicDetailData data2;
    private String otheruid="0";
    private ImageView iv01;
    private ImageView iv02;
    private ImageView iv03;
    private ImageView iv04;
    private ImageView iv05;
    private ImageView iv06;
    private ImageView iv07;
    private ImageView iv08;
    private List<ImageView> imgs;
    private RelativeLayout layout;
    private Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case 1:
                    getComment();
                    break;
            }
            super.handleMessage(msg);
        }
    };
    private ArrayList<String> godlist;
    private String head_pic;
    private TextView dashang;
    private ImageView chakandashang;
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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dynamic_detail);
        setView();
        setData();
        setListener();
        Intent intent=getIntent();
        dmid=intent.getStringExtra("dmid");
        distance=intent.getStringExtra("distance");
        time=intent.getStringExtra("time");
        uid=intent.getStringExtra("uid");
        if(intent.getStringArrayListExtra("godlist")!=null){
            godlist=intent.getStringArrayListExtra("godlist");
            for (int i=0;i<godlist.size();i++){
                imgs.get(i).setVisibility(View.VISIBLE);
                x.image().bind(imgs.get(i),godlist.get(i));
            }
        }
        if(!uid.equals(MyApp.uid)){
            delete.setVisibility(View.GONE);
        }
        tvdistance.setText(distance+"km");
        tvtime.setText(time);
        getDynamicdetail();

    }

    private void setData() {
        imgs=new ArrayList<>();
        imgs.add(iv01);
        imgs.add(iv02);
        imgs.add(iv03);
        imgs.add(iv04);
        imgs.add(iv05);
        imgs.add(iv06);
        imgs.add(iv07);
        imgs.add(iv08);
    }

    private void setView() {
        back= (TextView) findViewById(R.id.dynamic_detail_tv_back);
        back.setTextColor(Color.parseColor("#ffffff"));
        tvtitle= (TextView) findViewById(R.id.dynamic_detail_tvtitle);
        tvtitle.setTextColor(Color.parseColor("#ffffff"));
        icon= (ImageView) findViewById(R.id.dynamic_detail_icon);
        name= (TextView) findViewById(R.id.dynamic_detail_name);
        gender= (TextView) findViewById(R.id.dynamic_detail_sex);
        gender.setTextColor(Color.parseColor("#ffffff"));
        tvdistance= (TextView) findViewById(R.id.dynamic_detail_distance);
        tvtime= (TextView) findViewById(R.id.dynamic_detail_time);
        pic= (ImageView) findViewById(R.id.dynamic_detail_pic);
        zancount= (TextView) findViewById(R.id.dynamic_detail_zan);
        ckzan= (ImageView) findViewById(R.id.dynamic_detail_ckzan);
        edittext= (EditText) findViewById(R.id.dynamic_detail_et);
        send= (Button) findViewById(R.id.dynamic_detail_send);
        send.setTextColor(Color.parseColor("#ffffff"));
        btngz= (Button) findViewById(R.id.dynamic_detail_gz);
        btngz.setTextColor(Color.parseColor("#31C0F4"));
        tvcontenrt= (TextView) findViewById(R.id.dynamic_detail_tvcontent);
        listview= (ListView) findViewById(R.id.dynamic_detail_listview);
        delete= (TextView) findViewById(R.id.dynamic_detail_delete);
        delete.setTextColor(Color.parseColor("#ffffff"));
        mfavorlayout= (FavorLayout) findViewById(R.id.dynamic_detail_favorlayout);
        iv01= (ImageView) findViewById(R.id.iv01_dynamic_detail);
        iv02= (ImageView) findViewById(R.id.iv02_dynamic_detail);
        iv03= (ImageView) findViewById(R.id.iv03_dynamic_detail);
        iv04= (ImageView) findViewById(R.id.iv04_dynamic_detail);
        iv05= (ImageView) findViewById(R.id.iv05_dynamic_detail);
        iv06= (ImageView) findViewById(R.id.iv06_dynamic_detail);
        iv07= (ImageView) findViewById(R.id.iv07_dynamic_detail);
        iv08= (ImageView) findViewById(R.id.iv08_dynamic_detail);
        layout= (RelativeLayout) findViewById(R.id.dynamic_detail_layout);
        dashang= (TextView) findViewById(R.id.dynamic_detail_dashang);
        chakandashang= (ImageView) findViewById(R.id.dynamic_detail_ckdashang);
    }

    private void setListener() {
        back.setOnClickListener(this);
        zancount.setOnClickListener(this);
        ckzan.setOnClickListener(this);
        send.setOnClickListener(this);
        btngz.setOnClickListener(this);
        delete.setOnClickListener(this);
        listview.setOnItemClickListener(this);
        icon.setOnClickListener(this);
        layout.setOnClickListener(this);
        dashang.setOnClickListener(this);
        chakandashang.setOnClickListener(this);
    }

    private void getDynamicdetail() {
        RequestParams params=new RequestParams("http://bubblefish.jbserver.cn/api/friend/getDynamicdetail");
        params.addBodyParameter("dmid",dmid);
        params.addBodyParameter("uid", MyApp.uid);
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Log.i("dynamicdetail", "onSuccess: "+result);
                data=new Gson().fromJson(result,DynamicDetailData.class);
                head_pic=data.getData().getHead_pic();
                x.image().bind(icon,head_pic);
                name.setText(data.getData().getNickname());
                if(data.getData().getSex().equals("男")){
                    gender.setSelected(true);
                    gender.setText(" "+data.getData().getAge()+" ");
                    gender.setBackgroundResource(R.drawable.conner_man_bg);
                }else if(data.getData().getSex().equals("女")){
                    gender.setSelected(false);
                    gender.setText(" "+data.getData().getAge()+" ");
                    gender.setBackgroundResource(R.drawable.conner_women_bg);
                }
                if(data.getData().getRewardtimes().equals("0")){
                    dashang.setText("打赏");
                }else{
                    dashang.setText(data.getData().getRewardtimes());
                }
                tvcontenrt.setText(data.getData().getContent());
                x.image().bind(pic,data.getData().getPic(),imageOptions);
                zancount.setText(data.getData().getLandtimes());
                if(data.getData().getFollowstate()==1){
                    if(uid.equals(MyApp.uid)){
                        btngz.setVisibility(View.GONE);
                    }else {
                        btngz.setText("已关注");
                    }
                } else if(data.getData().getFollowstate()==2){
                    if(uid.equals(MyApp.uid)){
                        btngz.setVisibility(View.GONE);
                    }else {
                        btngz.setText("关注");
                    }
                }else{
                    btngz.setVisibility(View.GONE);
                }
                handler.sendEmptyMessage(1);
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

    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()){
            case R.id.dynamic_detail_tv_back:
                finish();
                break;
            case R.id.dynamic_detail_zan:
                mfavorlayout.addFavor();
                landtime= Integer.parseInt(data.getData().getLandtimes());
                landtime++;
                data.getData().setLandtimes(landtime+"");
                Log.i("zanzanzan", "onClick: "+landtime);
                zancount.setText(landtime+"");
                break;
            case R.id.dynamic_detail_ckzan:
                intent=new Intent(this,ZanPepoleActivity.class);
                intent.putExtra("dmid",dmid);
                startActivity(intent);
                break;
            case R.id.dynamic_detail_send:
                    sendComment();
                break;
            case R.id.dynamic_detail_delete:
                AlertDialog.Builder dialog=new AlertDialog.Builder(DynamicDetailActivity.this);
                dialog.setMessage("是否删除?")
                        .setPositiveButton("否", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        }).setNegativeButton("是", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        deleteDynamic();
                    }
                }).create();
                dialog.show();
                break;
            case R.id.dynamic_detail_icon:
                intent=new Intent(this,ZoomInPhotoActivity.class);
                intent.putExtra("imgurl",head_pic);
                startActivity(intent);
                break;
            case R.id.dynamic_detail_layout:
                intent=new Intent(this,FriendDataActivity.class);
                intent.putExtra("fid",uid);
                startActivity(intent);
                break;
            case R.id.dynamic_detail_dashang:
                if(data.getData().getUid().equals(MyApp.uid)){
                    Toast.makeText(getApplicationContext(),"您不能打赏自己",Toast.LENGTH_SHORT).show();
                }else{
                    DashangDialog dialog1=new DashangDialog(this,dmid);
                }
                break;
            case R.id.dynamic_detail_ckdashang:
//                intent=new Intent(this,DashangPeopleActivity.class);
//                intent.putExtra("dmid",dmid);
                intent=new Intent(this,FriendDataActivity.class);
                intent.putExtra("fid",uid);
                startActivity(intent);
                break;
        }
    }

    private void sendComment() {
        RequestParams params=new RequestParams("http://bubblefish.jbserver.cn/api/friend/suggestDynamic");
        if(otheruid.equals("0")) {
            params.addBodyParameter("uid", MyApp.uid);
            params.addBodyParameter("dmid", dmid);
            params.addBodyParameter("content", edittext.getText().toString());
        }else{
            params.addBodyParameter("uid", MyApp.uid);
            params.addBodyParameter("dmid", dmid);
            params.addBodyParameter("content", edittext.getText().toString());
            params.addBodyParameter("otheruid",otheruid);
        }
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Log.i("comment", "onSuccess: "+result);
                edittext.setText("");
                edittext.setHint("");
                edittext.clearFocus();
                InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(edittext.getWindowToken(),0);
                otheruid=0+"" ;
                getComment();
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

    private void getComment() {
        RequestParams params=new RequestParams("http://bubblefish.jbserver.cn/api/friend/getDynamicdetail");
        params.addBodyParameter("dmid",dmid);
        params.addBodyParameter("uid", MyApp.uid);
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                    data2 = new Gson().fromJson(result, DynamicDetailData.class);
                    Log.i("lvzhiweicomment", "onSuccess: " + data2.getData().getComment());
                    listview.setAdapter(new CommentAdapter(DynamicDetailActivity.this, data.getData().getNickname(), data2.getData().getComment()));
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

    private void deleteDynamic() {
        RequestParams params=new RequestParams("http://bubblefish.jbserver.cn/api/friend/deleteDynamic");
        params.addBodyParameter("dmid",dmid);
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                try {
                    JSONObject obj=new JSONObject(result);
                    switch (obj.getString("retcode")){
                        case "2000":
                            Toast.makeText(getApplicationContext(),obj.getString("msg"),Toast.LENGTH_SHORT).show();
                            finish();
                            break;
                        case "4003":
                            Toast.makeText(getApplicationContext(),obj.getString("msg"),Toast.LENGTH_SHORT).show();
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
    private void zanCount() {
        RequestParams params=new RequestParams("http://bubblefish.jbserver.cn/api/friend/landbydetail");
        params.addBodyParameter("uid",MyApp.uid);
        params.addBodyParameter("dmid",dmid);
        params.addBodyParameter("landtimes",landtime+"");
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Log.i("zanzanzan",result);
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
    @Override
    protected void onPause() {
        super.onPause();
        zanCount();
    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        otheruid = data2.getData().getComment().get(position).getUid();
        data2.getData().getComment().get(position).setOtheruid(otheruid);
//        Log.i("dynamicdetail111", "onItemClick: "+otheruid);
        // 获取编辑框焦点
        edittext.setFocusable(true);
        edittext.setHint("回复"+data2.getData().getComment().get(position).getNickname()+":");
        //打开软键盘
        InputMethodManager imm = (InputMethodManager)DynamicDetailActivity.this.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
    }

    @Override
    protected void onResume() {
        super.onResume();
        getComment();
    }
}
