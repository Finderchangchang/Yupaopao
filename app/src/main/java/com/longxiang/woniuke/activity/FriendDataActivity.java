package com.longxiang.woniuke.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.longxiang.woniuke.R;
import com.longxiang.woniuke.adapter.SkillAdapter;
import com.longxiang.woniuke.bean.SkillData;
import com.longxiang.woniuke.bean.ZlDynamicData;
import com.longxiang.woniuke.utils.GaussianUtil;
import com.longxiang.woniuke.utils.MyApp;
import com.longxiang.woniuke.utils.OperationPopupWindow;

import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

import io.rong.imkit.RongIM;
import io.rong.imkit.manager.AudioPlayManager;

public class FriendDataActivity extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemClickListener, MediaPlayer.OnCompletionListener {
    private ImageView tvCao;
    private ImageView ivBack;
    private ImageView ivBg;
    private ImageView ivIcon;
    private TextView tvName;
    private TextView tvGender;
    private TextView tvIntro;
    private TextView tvDistance;
    private TextView tvTime;
    private Button btnGz;
    private String fid;
    private Button btnChat;
    private Button btnOrder;
    private LinearLayout llservice;
    private LinearLayout lldongtai;
    private LinearLayout llziliao;
    private ListView serviceListview;
    private ImageView ivdongtaipic01;
    private ImageView ivdongtaipic02;
    private ImageView ivdongtaipic03;
    private ImageView ivdongtaipic04;
    private List<ImageView> imgs=new ArrayList<>();
    private LinearLayout llziliaostar;
    private LinearLayout llziliaojob;
    private LinearLayout llziliaointrest;
    private TextView ziliaostar;
    private TextView ziliaojob;
    private TextView ziliaointrest;
    private ImageView luVoice;
    private String head_pic;
    private String followstate;
    private String targetId;
    private String name;
    private String star;
    private String job;
    private String intrest;
    private String media;
    private MediaPlayer player;
    private OperationPopupWindow menuWindow;
    private SkillData data;
    private TextView tvSx;
    private String real_name;
    private ImageView ivrenzheng;
    private String sex;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friend_data);
        Intent intent=getIntent();
        fid=intent.getStringExtra("fid");
        head_pic=intent.getStringExtra("imgurl");
        getBitmap(head_pic);
        setView();
        setData();
        setListener();
        getfriendData();
        getService();
        getDynamicpic();
        if(MyApp.uid!=null&&!MyApp.uid.equals(fid)) {
            btnGz.setVisibility(View.VISIBLE);
        }else{
            btnGz.setVisibility(View.INVISIBLE);
        }
    }

    private void setData() {
        imgs.add(ivdongtaipic01);
        imgs.add(ivdongtaipic02);
        imgs.add(ivdongtaipic03);
        imgs.add(ivdongtaipic04);
    }

    private void getDynamicpic() {
        RequestParams params=new RequestParams("http://bubblefish.jbserver.cn/api/friend/getDynamicpic");
        params.addBodyParameter("uid",fid);
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                ZlDynamicData data=new Gson().fromJson(result,ZlDynamicData.class);
                if(data.getData().size()!=0){
                    lldongtai.setVisibility(View.VISIBLE);
                    for (int i=0;i<data.getData().size();i++){
                        x.image().bind(imgs.get(i),data.getData().get(i).getPic());
                    }
                }else{
                    lldongtai.setVisibility(View.GONE);
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

    private void getService() {
        RequestParams params=new RequestParams("http://bubblefish.jbserver.cn/api/biggods/getgodskill");
        params.addBodyParameter("uid",fid);
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Log.i("skillservice", "onSuccess: "+result);
                try {
                    JSONObject obj=new JSONObject(result);
                    switch (obj.getString("retcode")){
                        case "2000":
                            llservice.setVisibility(View.VISIBLE);
                            data=new Gson().fromJson(result,SkillData.class);
                            serviceListview.setAdapter(new SkillAdapter(FriendDataActivity.this,data.getData()));
                            break;
                        case "4001":
                            llservice.setVisibility(View.GONE);
                            Log.i("skillservice", "onSuccess: "+"4001");
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

    private void getfriendData() {
        RequestParams params=new RequestParams("http://bubblefish.jbserver.cn/api/users/getinformation");
        params.addBodyParameter("uid",fid);
        params.addBodyParameter("lat",MyApp.lat_经度);
        params.addBodyParameter("lng",MyApp.lng_纬度);
        params.addBodyParameter("selfuid",MyApp.uid);
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                try {
                    Log.i("frienddata", "onSuccess: "+result);
                    JSONObject json=new JSONObject(result);
                    JSONObject obj=json.getJSONObject("data");
                    head_pic=obj.getString("head_pic");
                    x.image().bind(ivIcon,head_pic);
                    name=obj.getString("nickname");
                    star=obj.getString("starchar");
                    job=obj.getString("occupation");
                    intrest=obj.getString("interest");
                    media=obj.getString("media");
                    Log.i("lvzhiweimedia", "onSuccess: "+media);
                    sex=obj.getString("sex");
                    real_name=obj.getString("real_name");
                    if(real_name.equals("0")){
                        ivrenzheng.setImageResource(R.mipmap.weirenzheng);
                    }else{
                        ivrenzheng.setImageResource(R.mipmap.renzheng);
                    }
                    if(media.equals("http://bubblefish.jbserver.cn/")){
                        luVoice.setVisibility(View.GONE);
                    }else{
                        luVoice.setVisibility(View.VISIBLE);
                        luVoice.setImageResource(R.mipmap.zantingzhong);
                    }
                    if(star.equals("")){
                        llziliaostar.setVisibility(View.GONE);
                    }
                    if(job.equals("")){
                        llziliaojob.setVisibility(View.GONE);
                    }
                    if(intrest.equals("")) {
                        llziliaointrest.setVisibility(View.GONE);
                    }

                    ziliaostar.setText(star);
                    ziliaojob.setText(job);
                    ziliaointrest.setText(intrest);
                    tvName.setText(name);
                    if(sex.equals("男")){
                        tvGender.setSelected(true);
                        tvGender.setText(" "+obj.getString("age"));
                        tvGender.setBackgroundResource(R.drawable.conner_man_bg);
//                        tvGender.setBackgroundColor(Color.parseColor("#31C0F4"));
                    }else{
                        tvGender.setSelected(false);
                        tvGender.setText(" "+obj.getString("age"));
                        tvGender.setBackgroundResource(R.drawable.conner_women_bg);
//                        tvGender.setBackgroundColor(Color.parseColor("#FC88AF"));
                    }
                    followstate=obj.getString("followstate");
                    if(followstate.equals("1")){
                        btnGz.setVisibility(View.INVISIBLE);
                    }else{
                        btnGz.setText("+   关注");
                    }
                    tvIntro.setText(obj.getString("sign"));
                    tvDistance.setText(obj.getString("distance")+"km");
                    tvTime.setText(obj.getString("moment"));
                    targetId=obj.getString("mobile");
                    getBitmap(head_pic);
                    btnChat.setOnClickListener(FriendDataActivity.this);
                    btnOrder.setOnClickListener(FriendDataActivity.this);
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
        ivrenzheng= (ImageView) findViewById(R.id.friend_data_iv_renzheng);
        ivBack= (ImageView) findViewById(R.id.friend_data_ivBack);
        ivBg= (ImageView) findViewById(R.id.friend_data_bg);
        ivIcon= (ImageView) findViewById(R.id.friend_data_ivPic);
        tvName= (TextView) findViewById(R.id.friend_data_tvNickname);
        tvName.setTextColor(Color.parseColor("#ffffff"));
        tvGender= (TextView) findViewById(R.id.friend_data_tvGender);
        tvGender.setTextColor(Color.parseColor("#ffffff"));
        tvIntro= (TextView) findViewById(R.id.friend_data_tvIntro);
        tvIntro.setTextColor(Color.parseColor("#ffffff"));
        tvDistance= (TextView) findViewById(R.id.friend_data_tvDistance);
        tvDistance.setTextColor(Color.parseColor("#EDEEE9"));
        tvTime= (TextView) findViewById(R.id.friend_data_tvtime);
        tvTime.setTextColor(Color.parseColor("#EDEEE9"));
        tvSx=(TextView)findViewById(R.id.friend_data_tvshuxian);
        tvSx.setTextColor(Color.parseColor("#EDEEE9"));
        btnGz= (Button) findViewById(R.id.friend_data_guanzhu);
        btnChat= (Button) findViewById(R.id.friend_data_btnchat);
        btnChat.setTextColor(Color.parseColor("#31C0F4"));
        btnOrder= (Button) findViewById(R.id.friend_data_btnorder);
        btnOrder.setTextColor(Color.parseColor("#ffffff"));
        llservice= (LinearLayout) findViewById(R.id.friend_data_service);
        lldongtai= (LinearLayout) findViewById(R.id.friend_data_dongtai);
        llziliao= (LinearLayout) findViewById(R.id.friend_data_ziliao);
        serviceListview= (ListView) findViewById(R.id.friend_data_listview);
        ivdongtaipic01= (ImageView) findViewById(R.id.friend_data_iv01);
        ivdongtaipic02=(ImageView) findViewById(R.id.friend_data_iv02);
        ivdongtaipic03=(ImageView) findViewById(R.id.friend_data_iv03);
        ivdongtaipic04=(ImageView) findViewById(R.id.friend_data_iv04);
        llziliaostar= (LinearLayout) findViewById(R.id.friend_data_ll_star);
        llziliaojob= (LinearLayout) findViewById(R.id.friend_data_ll_job);
        llziliaointrest= (LinearLayout) findViewById(R.id.friend_data_ll_intrest);
        ziliaostar= (TextView) findViewById(R.id.friend_data_tvstar);
        ziliaojob= (TextView) findViewById(R.id.friend_data_tvJob);
        ziliaointrest= (TextView) findViewById(R.id.friend_data_tvIntrest);
        ziliaostar.setTextColor(Color.parseColor("#000000"));
        ziliaojob.setTextColor(Color.parseColor("#000000"));
        ziliaointrest.setTextColor(Color.parseColor("#000000"));
        luVoice= (ImageView) findViewById(R.id.friend_data_luVoice);
        tvCao= (ImageView) findViewById(R.id.friend_data_tvcaozuo);
        btnGz.setTextColor(Color.parseColor("#ffffff"));
        player=new MediaPlayer();
        if(fid.equals(MyApp.uid)){
            tvCao.setVisibility(View.GONE);
        }
    }

    private void setListener() {
        ivBack.setOnClickListener(this);
        btnGz.setOnClickListener(this);
        btnChat.setOnClickListener(null);
        btnOrder.setOnClickListener(null);
        lldongtai.setOnClickListener(this);
        tvCao.setOnClickListener(this);
        serviceListview.setOnItemClickListener(this);
        luVoice.setOnClickListener(this);
        ivIcon.setOnClickListener(this);
        player.setOnCompletionListener(this);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.friend_data_ivBack:
                finish();
                break;
            case R.id.friend_data_guanzhu:
                if(fid.equals(MyApp.uid)){
                    Toast.makeText(getApplicationContext(),"哎呀,不要那么自恋啦!^-^",Toast.LENGTH_SHORT).show();
                }else {
                    if (btnGz.getText().toString().equals("+   关注")) {
                        gzfriend();
                    }
//                    else {
//                        cancelgz();
//                    }
                }
//                getfriendData();
                break;
            case R.id.friend_data_btnchat:
                if(MyApp.uid!=null) {
                    if (MyApp.uid.equals(fid)) {
                        Toast.makeText(getApplicationContext(), "亲,您不可以和自己聊天哦", Toast.LENGTH_SHORT).show();
                    } else {
                        if (RongIM.getInstance() != null) {
                            RongIM.getInstance().startPrivateChat(this, targetId, name);
//                    startActivity(intent);
                        }
                    }
                }else{
                    btnChat.setOnClickListener(null);
                }
                break;
            case R.id.friend_data_btnorder:
                if(fid.equals(MyApp.uid)){
                   Toast.makeText(getApplicationContext(),"亲,您不可以给自己下单哦",Toast.LENGTH_SHORT).show();
                }else {
                Intent intent1 = new Intent(this, XdActivity.class);
                intent1.putExtra("fid", fid);
                intent1.putExtra("pic", head_pic);
                intent1.putExtra("name", name);
                startActivity(intent1);
            }
                break;
            case R.id.friend_data_dongtai:
                Intent intent=new Intent(this,MyDtActivity.class);
                intent.putExtra("name",name);
                intent.putExtra("fid",fid);
                startActivity(intent);
                break;
            case R.id.friend_data_tvcaozuo:
                menuWindow = new OperationPopupWindow(FriendDataActivity.this, itemsOnClick);
                if(menuWindow.isShowing()){
                    menuWindow.dismiss();
                }else {
                    //显示窗口
                    menuWindow.showAtLocation(FriendDataActivity.this.findViewById(R.id.friend_data_layout), Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
                }
                break;
            case R.id.friend_data_luVoice:
                Toast.makeText(getApplicationContext(),"缓冲中...",Toast.LENGTH_SHORT).show();
                luVoice.setOnClickListener(null);
                try {
                    player.reset();
                    player.setDataSource(media);
                    player.prepare();
                    player.start();
                    luVoice.setImageResource(R.mipmap.bofangzhong);
                }catch (Exception e){
                    e.printStackTrace();
                }
                break;
            case R.id.friend_data_ivPic:
                intent=new Intent(this,ZoomInPhotoActivity.class);
                intent.putExtra("imgurl",head_pic);
                startActivity(intent);
                break;
        }
    }

    private void cancelgz() {
        RequestParams params=new RequestParams("http://bubblefish.jbserver.cn/api/friend/overfollow");
        params.addBodyParameter("selfuid",MyApp.uid);
        params.addBodyParameter("otheruid",fid);
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                try {
                    JSONObject obj=new JSONObject(result);
                    switch (obj.getString("retcode")){
                        case "2000":
                            btnGz.setVisibility(View.VISIBLE);
                            btnGz.setText("+   关注");
                            Toast.makeText(getApplicationContext(),obj.getString("msg"),Toast.LENGTH_SHORT).show();
                            break;
                        case "4000":
                        case "4001":
                            btnGz.setVisibility(View.INVISIBLE);
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

    private void gzfriend() {
        RequestParams params=new RequestParams("http://bubblefish.jbserver.cn/api/friend/followOne");
        params.addBodyParameter("uid",MyApp.uid);
        params.addBodyParameter("fuid",fid);
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                try {
                    JSONObject obj=new JSONObject(result);
                    switch (obj.getString("retcode")){
                        case "2000":
                            btnGz.setVisibility(View.INVISIBLE);
                            Toast.makeText(getApplicationContext(),obj.getString("msg"),Toast.LENGTH_SHORT).show();
                            break;
                        case "4002":
                        case "4001":
                            btnGz.setText("+   关注");
                            Toast.makeText(getApplicationContext(),obj.getString("msg"),Toast.LENGTH_SHORT).show();
                            break;
                        case "8881":
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

    public void getBitmap(String url){
        RequestParams params=new RequestParams(url);
        x.http().get(params, new Callback.CommonCallback<byte []>() {
            @Override
            public void onSuccess(byte[] result) {
                Log.i("frienddaatabitmap", "onSuccess: "+result);
                Bitmap bitmap= BitmapFactory.decodeByteArray(result,0,result.length);
//                ivBg.setImageBitmap(EditPersonMsgActivity.fastblur(bitmap,10));
                ivBg.setImageBitmap(GaussianUtil.blurBitmap(bitmap,FriendDataActivity.this));
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
    //为弹出窗口实现监听类
    private View.OnClickListener itemsOnClick = new View.OnClickListener(){

        public void onClick(View v) {
            menuWindow.dismiss();
            switch (v.getId()) {
                case R.id.friend_operation_jubao:
                    Intent intent =new Intent(FriendDataActivity.this,JuBaoActivity.class);
                    intent.putExtra("uid",fid);
                    intent.putExtra("name",name);
                    startActivity(intent);
                    break;
                case R.id.friend_operation_lahei:
                    setOneToBlacklist();
                    break;
                case R.id.friend_operation_quguan:
                    cancelgz();
                    break;
            }
        }

    };

    private void setOneToBlacklist() {
        RequestParams params=new RequestParams("http://bubblefish.jbserver.cn/api/friend/setOneToBlacklist");
        params.addBodyParameter("uid",MyApp.uid);
        params.addBodyParameter("fuid",fid);
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Log.i("frienddataactivity", "onSuccess: "+result);
                try {
                    JSONObject obj=new JSONObject(result);
                    switch (obj.getString("retcode")){
                        case "2000":
                            Toast.makeText(getApplicationContext(),obj.getString("msg"),Toast.LENGTH_SHORT).show();
                            menuWindow.dismiss();
                            break;
                        case "4001":
                        case "4002":
                        case "8888":
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

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent=new Intent(this,GodskilldetailActivity.class);
        intent.putExtra("mobile",targetId);
        intent.putExtra("fid",fid);
        intent.putExtra("bgid",data.getData().get(position).getBgid());
        intent.putExtra("head_pic",head_pic);
        intent.putExtra("name",name);
        startActivity(intent);
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
        luVoice.setOnClickListener(this);
        luVoice.setImageResource(R.mipmap.zantingzhong);
    }
}
