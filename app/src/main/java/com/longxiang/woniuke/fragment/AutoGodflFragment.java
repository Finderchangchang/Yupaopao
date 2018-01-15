package com.longxiang.woniuke.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshScrollView;
import com.longxiang.woniuke.R;
import com.longxiang.woniuke.activity.DsflActivity;
import com.longxiang.woniuke.activity.FriendDataActivity;
import com.longxiang.woniuke.activity.GodskilldetailActivity;
import com.longxiang.woniuke.adapter.GodflAdapter;
import com.longxiang.woniuke.adapter.WeekRankingAdapter;
import com.longxiang.woniuke.bean.GodFlData;
import com.longxiang.woniuke.bean.ShaixuanData;
import com.longxiang.woniuke.bean.XdxmData;
import com.longxiang.woniuke.utils.ListScrollUtil;
import com.longxiang.woniuke.utils.MyApp;

import org.xutils.common.Callback;
import org.xutils.common.util.DensityUtil;
import org.xutils.http.RequestParams;
import org.xutils.image.ImageOptions;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

import de.greenrobot.event.EventBus;
import de.greenrobot.event.Subscribe;
import de.greenrobot.event.ThreadMode;

/**
 * Created by Administrator on 2016/8/13.
 */
public class AutoGodflFragment extends Fragment implements PullToRefreshBase.OnRefreshListener2<ScrollView>, AdapterView.OnItemClickListener {
    private PullToRefreshScrollView mPullRefreshScrollView;
    private ListView listView;
    private LinearLayout layout;
    private RelativeLayout layout01;
    private RelativeLayout layout02;
    private RelativeLayout layout03;
    private ImageView icon01;
    private ImageView icon02;
    private ImageView icon03;
    private TextView tvname01;
    private TextView tvname02;
    private TextView tvname03;
    private TextView tvage01;
    private TextView tvage02;
    private TextView tvage03;
    private TextView tvlevel01;
    private TextView tvlevel02;
    private TextView tvlevel03;
    private TextView tvmoney01;
    private TextView tvmoney02;
    private TextView tvmoney03;
    private TextView tvjdcount01;
    private TextView tvjdcount02;
    private TextView tvjdcount03;
    private TextView tvdistance01;
    private TextView tvdistance02;
    private TextView tvdistance03;
    private TextView tvtime01;
    private TextView tvtime02;
    private TextView tvtime03;
    private String sex="";
    private String djid="";
    private String price="";
    private int page=0;
    List<GodFlData.DataBean> autogodfllist=new ArrayList<>();
    private GodflAdapter adapter;
    private GodFlData data;
    private ProgressBar progressbar;
    Handler handler=new Handler();
    ImageOptions imageOptions = new ImageOptions.Builder()
            .setSize(DensityUtil.dip2px(500), DensityUtil.dip2px(500))
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
            .setUseMemCache(false)
            //设置支持gif
            .setIgnoreGif(false)
            //设置显示圆形图片
            .setCircular(false)
            .setSquare(true)
            .build();
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.auto_godfl_fragment,null);
        EventBus.getDefault().register(this);
        setView(view);
        setListener();
//        getGodbyTypeTop();
        getGodbyType();
        return view;
    }
    @Subscribe(threadMode = ThreadMode.MainThread)
    public void helloEventBus(ShaixuanData m){
        sex=m.gender;
        price=m.price;
        djid=m.djid;
        Log.i("lvzhiweiautogod", "helloEventBus: "+sex+","+price+","+djid);
        autogodfllist .clear();
        progressbar.setVisibility(View.VISIBLE);
        getGodbyType();
    }
    private void setListener() {
        mPullRefreshScrollView.setOnRefreshListener(this);
        listView.setOnItemClickListener(this);
    }

    private void setView(View view) {
        progressbar=(ProgressBar)view.findViewById(R.id.auto_godfl_progressBar);
        progressbar.setVisibility(View.VISIBLE);
        mPullRefreshScrollView= (PullToRefreshScrollView) view.findViewById(R.id.pull_refresh_scrollview);
        mPullRefreshScrollView.setMode(PullToRefreshBase.Mode.BOTH);
        mPullRefreshScrollView.getLoadingLayoutProxy().setRefreshingLabel("正在加载");
        mPullRefreshScrollView.getLoadingLayoutProxy().setPullLabel("上拉加载更多");
        mPullRefreshScrollView.getLoadingLayoutProxy().setReleaseLabel("释放开始加载");
        listView= (ListView) view.findViewById(R.id.dsfl_listview);
        layout= (LinearLayout) view.findViewById(R.id.dsfl_ll_layout);
        layout.setFocusable( true);
        layout.setFocusableInTouchMode( true);
        layout.requestFocus();
        icon01= (ImageView) view.findViewById(R.id.dsfl_iv_icon);
        icon02= (ImageView) view.findViewById(R.id.dsfl_iv_icon02);
        icon03= (ImageView) view.findViewById(R.id.dsfl_iv_icon03);
        tvname01= (TextView) view.findViewById(R.id.dsfl_tv_name);
        tvname02= (TextView) view.findViewById(R.id.dsfl_tv_name02);
        tvname03= (TextView) view.findViewById(R.id.dsfl_tv_name03);
        tvage01= (TextView) view.findViewById(R.id.dsfl_tv_age);
        tvage02= (TextView) view.findViewById(R.id.dsfl_tv_age02);
        tvage03= (TextView) view.findViewById(R.id.dsfl_tv_age03);
        tvlevel01= (TextView) view.findViewById(R.id.dsfl_tv_level);
        tvlevel02= (TextView) view.findViewById(R.id.dsfl_tv_level02);
        tvlevel03= (TextView) view.findViewById(R.id.dsfl_tv_level03);
        tvmoney01= (TextView) view.findViewById(R.id.dsfl_tv_danjia);
        tvmoney02= (TextView) view.findViewById(R.id.dsfl_tv_danjia02);
        tvmoney03= (TextView) view.findViewById(R.id.dsfl_tv_danjia03);
        tvjdcount01= (TextView) view.findViewById(R.id.dsfl_tv_jdcount);
        tvjdcount02= (TextView) view.findViewById(R.id.dsfl_tv_jdcount02);
        tvjdcount03= (TextView) view.findViewById(R.id.dsfl_tv_jdcount03);
        tvdistance01= (TextView) view.findViewById(R.id.dsfl_tv_distance);
        tvdistance02= (TextView) view.findViewById(R.id.dsfl_tv_distance02);
        tvdistance03= (TextView) view.findViewById(R.id.dsfl_tv_distance03);
        tvtime01= (TextView) view.findViewById(R.id.dsfl_tv_time);
        tvtime02= (TextView) view.findViewById(R.id.dsfl_tv_time02);
        tvtime03= (TextView) view.findViewById(R.id.dsfl_tv_time03);
        layout01= (RelativeLayout) view.findViewById(R.id.dsfl_rl_layout1);
        layout02= (RelativeLayout) view.findViewById(R.id.dsfl_rl_layout2);
        layout03= (RelativeLayout) view.findViewById(R.id.dsfl_rl_layout3);
    }
    private void getGodbyType() {
        RequestParams params=new RequestParams("http://bubblefish.jbserver.cn/api/index/getGodbyType");
        params.addBodyParameter("lat", MyApp.lat_经度);
        params.addBodyParameter("lng",MyApp.lng_纬度);
        params.addBodyParameter("jnid", DsflActivity.jnid);
        params.addBodyParameter("type","1");
        params.addBodyParameter("page",page+"");
        params.addBodyParameter("sex",sex);
        params.addBodyParameter("djid",djid);
        params.addBodyParameter("price",price);
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Log.i("godflfragmentresult", "onSuccess: "+result);
                data=new Gson().fromJson(result,GodFlData.class);
                if(data.getRetcode()==2000) {
                    listView.setVisibility(View.VISIBLE);
                    progressbar.setVisibility(View.GONE);
                if(data.getData().size()>=1){
                    final GodFlData.DataBean god = data.getData().get(0);
                    showpic01(god);
                    layout01.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent=new Intent(getActivity(), GodskilldetailActivity.class);
                            intent.putExtra("fid",god.getUid());
                            intent.putExtra("bgid",god.getBgid());
                            intent.putExtra("mobile",god.getMobile());
                            intent.putExtra("name",god.getNickname());
                            intent.putExtra("head_pic",god.getHead_pic());
                            startActivity(intent);
                        }
                    });
                    layout01.setVisibility(View.VISIBLE);
                    layout02.setVisibility(View.INVISIBLE);
                    layout03.setVisibility(View.INVISIBLE);
                }
                if(data.getData().size()>=2){
//                    GodFlData.DataBean god = data.getData().get(0);
                    final GodFlData.DataBean god2 = data.getData().get(1);
//                    showpic01(god);
                    showpic02(god2);
                    layout02.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent=new Intent(getActivity(), GodskilldetailActivity.class);
                            intent.putExtra("fid",god2.getUid());
                            intent.putExtra("bgid",god2.getBgid());
                            intent.putExtra("mobile",god2.getMobile());
                            intent.putExtra("name",god2.getNickname());
                            intent.putExtra("head_pic",god2.getHead_pic());
                            startActivity(intent);
                        }
                    });
                    layout01.setVisibility(View.VISIBLE);
                    layout02.setVisibility(View.VISIBLE);
                    layout03.setVisibility(View.INVISIBLE);
                }
                if(data.getData().size()>=3){
//                    GodFlData.DataBean god = data.getData().get(0);
//                    GodFlData.DataBean god2 = data.getData().get(1);
                    final GodFlData.DataBean god3 = data.getData().get(2);
//                    showpic01(god);
//                    showpic02(god2);
                    showpic03(god3);
                    layout03.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent=new Intent(getActivity(), GodskilldetailActivity.class);
                            intent.putExtra("fid",god3.getUid());
                            intent.putExtra("bgid",god3.getBgid());
                            intent.putExtra("mobile",god3.getMobile());
                            intent.putExtra("name",god3.getNickname());
                            intent.putExtra("head_pic",god3.getHead_pic());
                            startActivity(intent);
                        }
                    });
                    layout01.setVisibility(View.VISIBLE);
                    layout02.setVisibility(View.VISIBLE);
                    layout03.setVisibility(View.VISIBLE);
                }
                    if(data.getData().size()>3){
                        for (int i=3;i<data.getData().size();i++){
                            autogodfllist.add(data.getData().get(i));
                        }
                    }
                }
                if(data.getRetcode()==4000){
                    progressbar.setVisibility(View.GONE);
                    layout01.setVisibility(View.INVISIBLE);
                    layout02.setVisibility(View.INVISIBLE);
                    layout03.setVisibility(View.INVISIBLE);
                    listView.setVisibility(View.INVISIBLE);
                    Toast.makeText(getActivity().getApplicationContext(),data.getMsg(),Toast.LENGTH_SHORT).show();
                }
                adapter=new GodflAdapter(getActivity(),autogodfllist);
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

//    private void getGodbyTypeTop() {
//        RequestParams params=new RequestParams("http://bubblefish.jbserver.cn/api/index/getGodbyTypeTop");
//        params.addBodyParameter("lat", MyApp.lat_经度);
//        params.addBodyParameter("lng",MyApp.lng_纬度);
//        params.addBodyParameter("jnid",DsflActivity.jnid);
//        params.addBodyParameter("sex",sex);
//        params.addBodyParameter("djid",djid);
//        params.addBodyParameter("price",price);
//        x.http().post(params, new Callback.CommonCallback<String>() {
//            @Override
//            public void onSuccess(String result) {
//                Log.i("dsflactivity", "onSuccess: "+result);
//                GodFlData data=new Gson().fromJson(result,GodFlData.class);
//                if(data.getData().size()>=1){
//                    GodFlData.DataBean god = data.getData().get(0);
//                    showpic01(god);
//                    layout01.setVisibility(View.VISIBLE);
//                }
//                if(data.getData().size()>=2){
////                    GodFlData.DataBean god = data.getData().get(0);
//                    GodFlData.DataBean god2 = data.getData().get(1);
////                    showpic01(god);
//                    showpic02(god2);
//                    layout01.setVisibility(View.VISIBLE);
//                    layout02.setVisibility(View.VISIBLE);
//                }
//                if(data.getData().size()>=3){
////                    GodFlData.DataBean god = data.getData().get(0);
////                    GodFlData.DataBean god2 = data.getData().get(1);
//                    GodFlData.DataBean god3 = data.getData().get(2);
////                    showpic01(god);
////                    showpic02(god2);
//                    showpic03(god3);
//                    layout01.setVisibility(View.VISIBLE);
//                    layout02.setVisibility(View.VISIBLE);
//                    layout03.setVisibility(View.VISIBLE);
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
    private void showpic03(GodFlData.DataBean god3) {
        x.image().bind(icon03,god3.getHead_pic(),imageOptions);
        tvname03.setText(god3.getNickname());
        if(god3.getSex().equals("男")){
            tvage03.setSelected(true);
            tvage03.setText(" "+god3.getAge()+" ");
            tvage03.setBackgroundResource(R.drawable.conner_man_bg);
        }else{
            tvage03.setSelected(false);
            tvage03.setText(" "+god3.getAge()+" ");
            tvage03.setBackgroundResource(R.drawable.conner_women_bg);
        }
        tvlevel03.setText(god3.getLevelname());
        tvmoney03.setText(god3.getPrice()+"元/"+god3.getUnit());
        tvjdcount03.setText("接单"+god3.getTimes()+"次");
        tvdistance03.setText(god3.getDistance()+"km");
        tvtime03.setText(god3.getTime());
    }

    private void showpic02(GodFlData.DataBean god2) {
        x.image().bind(icon02,god2.getHead_pic(),imageOptions);
        tvname02.setText(god2.getNickname());
        if(god2.getSex().equals("男")){
            tvage02.setSelected(true);
            tvage02.setText(" "+god2.getAge()+" ");
            tvage02.setBackgroundResource(R.drawable.conner_man_bg);
        }else{
            tvage02.setText(" "+god2.getAge()+" ");
            tvage02.setSelected(false);
            tvage02.setBackgroundResource(R.drawable.conner_women_bg);
        }
        tvlevel02.setText(god2.getLevelname());
        tvmoney02.setText(god2.getPrice()+"元/"+god2.getUnit());
        tvjdcount02.setText("接单"+god2.getTimes()+"次");
        tvdistance02.setText(god2.getDistance()+"km");
        tvtime02.setText(god2.getTime());
    }

    private void showpic01(GodFlData.DataBean god) {
        x.image().bind(icon01,god.getHead_pic(),imageOptions);
        tvname01.setText(god.getNickname());
        if(god.getSex().equals("男")){
            tvage01.setSelected(true);
            tvage01.setText(" "+god.getAge()+" ");
            tvage01.setBackgroundResource(R.drawable.conner_man_bg);
        }else{
            tvage01.setSelected(false);
            tvage01.setText(" "+god.getAge()+" ");
            tvage01.setBackgroundResource(R.drawable.conner_women_bg);
        }
        tvlevel01.setText(god.getLevelname());
        tvmoney01.setText(god.getPrice()+"元/"+god.getUnit());
        tvjdcount01.setText("接单"+god.getTimes()+"次");
        tvdistance01.setText(god.getDistance()+"km");
        tvtime01.setText(god.getTime());
    }

    @Override
    public void onPullDownToRefresh(PullToRefreshBase<ScrollView> refreshView) {
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
              mPullRefreshScrollView.onRefreshComplete();
            }
        },1500);
    }

    @Override
    public void onPullUpToRefresh(PullToRefreshBase<ScrollView> refreshView) {
        page+=1;
        Log.i("autogodflfragment", "onPullUpToRefresh: "+page);
        getGodbyType02();
    }
    private void getGodbyType02() {
        RequestParams params=new RequestParams("http://bubblefish.jbserver.cn/api/index/getGodbyType");
        params.addBodyParameter("lat",MyApp.lat_经度);
        params.addBodyParameter("lng",MyApp.lng_纬度);
        params.addBodyParameter("jnid",DsflActivity.jnid);
        params.addBodyParameter("type","1");
        params.addBodyParameter("page",page+"");
        Log.i("autogodflfragmentpage", "getGodbyType02: "+page);
        params.addBodyParameter("sex",sex);
        params.addBodyParameter("djid",djid);
        params.addBodyParameter("price",price);
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Log.i("godflfragmentresult", "onSuccess: "+result);
                GodFlData data=new Gson().fromJson(result,GodFlData.class);
                if(data.getRetcode()==2000) {
                    autogodfllist.addAll(data.getData());
                    adapter.notifyDataSetChanged();
                }
                if(data.getRetcode()==4000){
                    page=page-1;
                    Toast.makeText(getContext().getApplicationContext(),data.getMsg(),Toast.LENGTH_SHORT).show();
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

//    @Override
//    public void onPause() {
//        super.onPause();
//        page=0;
//    }
@Override
public void onDestroy() {
    EventBus.getDefault().unregister(this);
    super.onDestroy();
}

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        String fid=autogodfllist.get(position).getUid();
        Intent intent=new Intent(getActivity(),GodskilldetailActivity.class);
        intent.putExtra("fid",fid);
        intent.putExtra("bgid",autogodfllist.get(position).getBgid());
        intent.putExtra("mobile",autogodfllist.get(position).getMobile());
        intent.putExtra("name",autogodfllist.get(position).getNickname());
        intent.putExtra("head_pic",autogodfllist.get(position).getHead_pic());
        startActivity(intent);
    }
}
