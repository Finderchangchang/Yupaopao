package com.longxiang.woniuke.activity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshScrollView;
import com.longxiang.woniuke.R;
import com.longxiang.woniuke.adapter.GodCommentAdapter;
import com.longxiang.woniuke.bean.FriendData;
import com.longxiang.woniuke.bean.GodcommentData;
import com.longxiang.woniuke.bean.GodskilldetailData;
import com.longxiang.woniuke.utils.ListScrollUtil;
import com.longxiang.woniuke.utils.MyApp;

import org.xutils.common.Callback;
import org.xutils.common.util.DensityUtil;
import org.xutils.http.RequestParams;
import org.xutils.image.ImageOptions;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

import io.rong.imkit.RongIM;

public class GodskilldetailActivity extends AppCompatActivity implements View.OnClickListener,PullToRefreshBase.OnRefreshListener2<ScrollView> {
    private ImageView ivBack;
    private TextView title;
    private String fid;
    private String bgid;
    private ImageView ivicon;
    private TextView tvname;
    private TextView tvage;
    private ImageView ivpic;
    private ImageView ivtypeimg;
    private TextView tvtypename;
    private TextView tvmoney;
    private TextView tvbasicprice;
    private TextView tvdistance;
    private TextView tvtime;
    private TextView tvjdcount;
    private TextView tvintro;
    private LinearLayout layout;
    private TextView tvcommenttimes;
    private TextView tvavglevel;
    private PullToRefreshScrollView mPullRefreshScrollView;
    private ListView listView;
    private int page=0;
    private GodCommentAdapter adapter;
    private List<GodcommentData.DataBean> commentlist=new ArrayList<>();
    private String mobile;
    private Button btnchat;
    private Button btnxd;
    private String head_pic;
    private String name;
    private ImageView ivRenzheng;
    private String imgurl;
    private String jnid;
    private String jnname;
    private String unit;
    private String djid;
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
        setContentView(R.layout.activity_godskilldetail);
        Intent intent=getIntent();
        fid=intent.getStringExtra("fid");
        bgid=intent.getStringExtra("bgid");
        mobile=intent.getStringExtra("mobile");
        name=intent.getStringExtra("name");
        head_pic=intent.getStringExtra("head_pic");
        setView();
        setListener();
        getGodDetail();
        getOrderComment();
    }
    /*
    获取留言列表
     */
    private void getOrderComment() {
        RequestParams params=new RequestParams("http://bubblefish.jbserver.cn/api/biggods/getOrderComment");
        params.addBodyParameter("bgid",bgid);
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Log.i("getordercomment", "onSuccess: "+result);
                GodcommentData data=new Gson().fromJson(result,GodcommentData.class);
                commentlist.addAll(data.getData());
                adapter=new GodCommentAdapter(GodskilldetailActivity.this,commentlist);
                listView.setAdapter(adapter);
                ListScrollUtil. setListViewHeightBasedOnChildren(listView);
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
    /*
        获取大神详情
     */
    private void getGodDetail() {
        RequestParams params=new RequestParams("http://bubblefish.jbserver.cn/api/biggods/getGodDetail");
        params.addBodyParameter("uid",fid);
        params.addBodyParameter("bgid",bgid);
        params.addBodyParameter("lat", MyApp.lat_经度);
        params.addBodyParameter("lng",MyApp.lng_纬度);
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Log.i("godskilldetail", "onSuccess: "+result);
                GodskilldetailData data=new Gson().fromJson(result,GodskilldetailData.class);
                GodskilldetailData.DataBean datas = data.getData();
                jnid=data.getData().getGodinfo().getJnid();
                jnname=data.getData().getGodinfo().getSkill();
                unit=data.getData().getGodinfo().getUnit();
                djid=data.getData().getGodinfo().getDjid();
                x.image().bind(ivicon,datas.getUserinfo().getHead_pic());
                tvname.setText(datas.getUserinfo().getNickname());
                if(datas.getUserinfo().getSex().equals("男")){
                    tvage.setSelected(true);
                    tvage.setText(" "+datas.getUserinfo().getAge()+" ");
                    tvage.setBackgroundResource(R.drawable.conner_man_bg);
                }else{
                    tvage.setSelected(false);
                    tvage.setText(" "+datas.getUserinfo().getAge()+" ");
                    tvage.setBackgroundResource(R.drawable.conner_women_bg);
                }
                imgurl=datas.getGodinfo().getPic();
                x.image().bind(ivpic,imgurl,imageOptions);
                x.image().bind(ivtypeimg,datas.getGodinfo().getSkill_pic());
                tvtypename.setText(jnname+" · "+datas.getGodinfo().getLevel());
                tvmoney.setText(datas.getGodinfo().getPrice()+"元/"+datas.getGodinfo().getUnit());
                if(datas.getGodinfo().getBasicprice().equals("")){
                    tvbasicprice.setVisibility(View.GONE);
                }else {
                    tvbasicprice.setVisibility(View.VISIBLE);
                    tvbasicprice.setText(datas.getGodinfo().getBasicprice() + "元/" + datas.getGodinfo().getUnit());
                }
                tvdistance.setText(datas.getOther().getDistance()+"km");
                tvtime.setText(datas.getOther().getTime());
                tvjdcount.setText("接单"+datas.getGodinfo().getTimes()+"次");
                tvintro.setText(datas.getGodinfo().getExplain());
                tvcommenttimes.setText("("+datas.getGodinfo().getCommenttimes()+"次)");
                if(datas.getGodinfo().getAvglevel()==null){
                    tvavglevel.setText("0");
                }else{
                    tvavglevel.setText(datas.getGodinfo().getAvglevel()+"");
                }
                if(datas.getUserinfo().getReal_name().equals("0")){
                    ivRenzheng.setImageResource(R.mipmap.weirenzheng);
                }else{
                    ivRenzheng.setImageResource(R.mipmap.renzheng);
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
    /*
    初始化
     */
    private void setView() {
        ivRenzheng= (ImageView) findViewById(R.id.god_skill_iv_renzheng);
        btnchat= (Button) findViewById(R.id.god_skill_btnchat);
        btnchat.setTextColor(Color.parseColor("#31C0F4"));
        btnxd= (Button) findViewById(R.id.god_skill_btnorder);
        btnxd.setTextColor(Color.parseColor("#ffffff"));
        layout= (LinearLayout) findViewById(R.id.god_skill_layout);
        layout.setFocusable(true);
        layout.setFocusableInTouchMode(true);
        layout.requestFocus();
        ivBack= (ImageView) findViewById(R.id.god_skill_iv_back);
        title= (TextView) findViewById(R.id.god_skill_title_name);
        title.setTextColor(Color.parseColor("#ffffff"));
        ivicon= (ImageView) findViewById(R.id.god_skill_icon);
        tvname= (TextView) findViewById(R.id.god_skill_name);
        tvage= (TextView) findViewById(R.id.god_skill_age);
        ivpic= (ImageView) findViewById(R.id.god_skill_pic);
        ivtypeimg= (ImageView) findViewById(R.id.god_skill_typeimg);
        tvtypename= (TextView) findViewById(R.id.god_skill_typename);
        tvmoney= (TextView) findViewById(R.id.god_skill_danjia);
        tvmoney.setTextColor(Color.parseColor("#ff1111"));
        tvtime= (TextView) findViewById(R.id.god_skill_time);
        tvjdcount= (TextView) findViewById(R.id.god_skill_jdcount);
        tvintro= (TextView) findViewById(R.id.god_skill_intro);
//        tvintro.setFocusable(true);
//        tvintro.requestFocus();
        tvdistance= (TextView) findViewById(R.id.god_skill_distance);
        tvbasicprice= (TextView) findViewById(R.id.god_skill_basicprice);
        tvbasicprice.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
        tvage.setTextColor(Color.parseColor("#ffffff"));
        tvcommenttimes= (TextView) findViewById(R.id.god_skill_plcount);
        tvcommenttimes.setTextColor(Color.parseColor("#1EBAF3"));
        tvavglevel= (TextView) findViewById(R.id.god_skill_tv_pf);
        mPullRefreshScrollView= (PullToRefreshScrollView) findViewById(R.id.godskill_pull_refresh_scrollview);
        mPullRefreshScrollView.setMode(PullToRefreshBase.Mode.PULL_FROM_END);
        mPullRefreshScrollView.getLoadingLayoutProxy().setRefreshingLabel("正在加载");
        mPullRefreshScrollView.getLoadingLayoutProxy().setPullLabel("上拉加载更多");
        mPullRefreshScrollView.getLoadingLayoutProxy().setReleaseLabel("释放开始加载");
        listView= (ListView) findViewById(R.id.god_skill_listview);
        listView.setSelector(new ColorDrawable(Color.TRANSPARENT));
    }

    private void setListener() {
        ivBack.setOnClickListener(this);
        mPullRefreshScrollView.setOnRefreshListener(this);
        btnchat.setOnClickListener(this);
        btnxd.setOnClickListener(this);
        layout.setOnClickListener(this);
        ivpic.setOnClickListener(this);
    }
    /*
    点击事件
     */
    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()){
            case R.id.god_skill_iv_back:
                finish();
                break;
            case R.id.god_skill_btnchat:
                if(MyApp.uid.equals(fid)){
                    Toast.makeText(getApplicationContext(),"亲,您不可以和自己聊天哦",Toast.LENGTH_SHORT).show();
                }else {
                    if (RongIM.getInstance() != null) {
                        RongIM.getInstance().startPrivateChat(this,mobile,name);
//                    startActivity(intent);
                    }
                }
                break;
            case R.id.god_skill_btnorder:
                if(fid.equals(MyApp.uid)){
                    Toast.makeText(getApplicationContext(),"亲,您不可以给自己下单哦",Toast.LENGTH_SHORT).show();
                }else {
                    intent = new Intent(this, XdActivity.class);
                    intent.putExtra("fid", fid);
                    intent.putExtra("pic", head_pic);
                    intent.putExtra("name", name);
                    intent.putExtra("jnid",jnid);
                    intent.putExtra("jnname",jnname);
                    intent.putExtra("unit",unit);
                    intent.putExtra("bgid",bgid);
                    intent.putExtra("djid",djid);
                    startActivity(intent);
                }
                break;
            case R.id.god_skill_layout:
                intent=new Intent(this, FriendDataActivity.class);
                intent.putExtra("fid",fid);
                startActivity(intent);
                break;
            case R.id.god_skill_pic:
                intent=new Intent(this,ZoomInPhotoActivity.class);
                intent.putExtra("imgurl",imgurl);
                startActivity(intent);
                break;
        }
    }
    /*
    分页加载
     */
    @Override
    public void onPullDownToRefresh(PullToRefreshBase<ScrollView> refreshView) {

    }

    @Override
    public void onPullUpToRefresh(PullToRefreshBase<ScrollView> refreshView) {
        page+=1;
        getOrderComment2();
    }

    private void getOrderComment2() {
        RequestParams params=new RequestParams("http://bubblefish.jbserver.cn/api/biggods/getOrderComment");
        params.addBodyParameter("bgid",bgid);
        params.addBodyParameter("page",page+"");
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Log.i("getordercomment", "onSuccess: "+result);
                GodcommentData data=new Gson().fromJson(result,GodcommentData.class);
                commentlist.addAll(data.getData());
                adapter.notifyDataSetChanged();
                if(data.getRetcode()==4001){
                    Toast.makeText(getApplicationContext(),data.getMsg(),Toast.LENGTH_SHORT).show();
                }
                ListScrollUtil. setListViewHeightBasedOnChildren(listView);
                mPullRefreshScrollView.onRefreshComplete();
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
}
