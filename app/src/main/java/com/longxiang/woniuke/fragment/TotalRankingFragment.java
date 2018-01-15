package com.longxiang.woniuke.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.longxiang.woniuke.R;
import com.longxiang.woniuke.adapter.TotalRankingAdapter;
import com.longxiang.woniuke.bean.TotalRankingData;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.image.ImageOptions;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/8/18.
 */
public class TotalRankingFragment extends Fragment {
    private ImageView icon01;
    private ImageView icon02;
    private ImageView icon03;
    private TextView name01;
    private TextView name02;
    private TextView name03;
    private TextView age01;
    private TextView age02;
    private TextView age03;
    private TextView jifen01;
    private TextView jifen02;
    private TextView jifen03;
    private LinearLayout layou01;
    private LinearLayout layou02;
    private LinearLayout layou03;
    private List<TotalRankingData.DataBean> totaldata=new ArrayList<>();
    private final ImageOptions options=new ImageOptions.Builder()
            //设置加载过程中的图片
            .setLoadingDrawableId(R.mipmap.woniu)
            //设置加载失败后的图片
            .setFailureDrawableId(R.mipmap.woniu)
            //    设置使用缓存
            .setUseMemCache(true)
            //设置显示圆形图片
            .setCircular(true)
            //设置支持gif
            .setIgnoreGif(false)
            .build();
    //    private PullToRefreshScrollView mPullRefreshScrollView;
    private ListView listView;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.total_ranking_fragment,null);
        setView(view);
        getRankList();
        return view;
    }
    private void getRankList() {
        RequestParams params=new RequestParams("http://bubblefish.jbserver.cn/api/friend/getRankList");
        params.addBodyParameter("type","1");
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Log.i("totalresult", "onSuccess: "+result);
                TotalRankingData data=new Gson().fromJson(result,TotalRankingData.class);
                if(data.getData().size()>=1){
                    TotalRankingData.DataBean data01 = data.getData().get(0);
                    layou01.setVisibility(View.VISIBLE);
                    showFirst(data01);
                }
                if(data.getData().size()>=2){
                    TotalRankingData.DataBean data02 = data.getData().get(1);
                    layou02.setVisibility(View.VISIBLE);
                    showFirst02(data02);
                }
                if(data.getData().size()>=3){
                    TotalRankingData.DataBean data03 = data.getData().get(2);
                    layou03.setVisibility(View.VISIBLE);
                    showFirst03(data03);
                }
                if(data.getData().size()>3){
                    for (int i=3;i<data.getData().size();i++){
                        totaldata.add(data.getData().get(i));
                    }
                    listView.setAdapter(new TotalRankingAdapter(getActivity(),totaldata));
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

    private void showFirst03(TotalRankingData.DataBean data03) {
        x.image().bind(icon03,data03.getHead_pic(),options);
        name03.setText(data03.getNickname());
        name03.setTextColor(Color.parseColor("#000000"));
        if(data03.getSex().equals("男")){
            age03.setText(" "+data03.getAge()+" ");
            age03.setSelected(true);
            age03.setBackgroundResource(R.drawable.conner_man_bg);
            age03.setTextColor(Color.parseColor("#ffffff"));
        }else{
            age03.setSelected(false);
            age03.setText(" "+data03.getAge()+" ");
            age03.setBackgroundResource(R.drawable.conner_women_bg);
            age03.setTextColor(Color.parseColor("#ffffff"));
        }
        jifen03.setText(data03.getAllrank());
        jifen03.setTextColor(Color.parseColor("#1EBAF3"));
    }

    private void showFirst02(TotalRankingData.DataBean data02) {
        x.image().bind(icon02,data02.getHead_pic(),options);
        name02.setText(data02.getNickname());
        name02.setTextColor(Color.parseColor("#000000"));
        if(data02.getSex().equals("男")){
            age02.setSelected(true);
            age02.setText(" "+data02.getAge()+" ");
            age02.setBackgroundResource(R.drawable.conner_man_bg);
            age02.setTextColor(Color.parseColor("#ffffff"));
        }else{
            age02.setSelected(false);
            age02.setText(" "+data02.getAge()+" ");
            age02.setBackgroundResource(R.drawable.conner_women_bg);
            age02.setTextColor(Color.parseColor("#ffffff"));
        }
        jifen02.setText(data02.getAllrank());
        jifen02.setTextColor(Color.parseColor("#1EBAF3"));
    }

    private void showFirst(TotalRankingData.DataBean data01) {
        x.image().bind(icon01,data01.getHead_pic(),options);
        name01.setText(data01.getNickname());
        name01.setTextColor(Color.parseColor("#000000"));
        if(data01.getSex().equals("男")){
            age01.setSelected(true);
            age01.setText(" "+data01.getAge()+" ");
            age01.setBackgroundResource(R.drawable.conner_man_bg);
            age01.setTextColor(Color.parseColor("#ffffff"));
        }else{
            age01.setSelected(false);
            age01.setText(" "+data01.getAge()+" ");
            age01.setBackgroundResource(R.drawable.conner_women_bg);
            age01.setTextColor(Color.parseColor("#ffffff"));
        }
        Log.i("monthrank", "showFirst: "+data01.getAllrank());
        jifen01.setText(data01.getAllrank());
        jifen01.setTextColor(Color.parseColor("#1EBAF3"));
    }

    private void setView(View view) {
        icon01= (ImageView) view.findViewById(R.id.total_ranking_iv_icon01);
        icon02= (ImageView) view.findViewById(R.id.total_ranking_iv_icon02);
        icon03= (ImageView) view.findViewById(R.id.total_ranking_iv_icon03);
        name01= (TextView) view.findViewById(R.id.total_ranking_tv_name01);
        name02= (TextView) view.findViewById(R.id.total_ranking_tv_name02);
        name03= (TextView) view.findViewById(R.id.total_ranking_tv_name03);
        age01= (TextView) view.findViewById(R.id.total_ranking_tv_age01);
        age02= (TextView) view.findViewById(R.id.total_ranking_tv_age02);
        age03= (TextView) view.findViewById(R.id.total_ranking_tv_age03);
        jifen01= (TextView) view.findViewById(R.id.total_ranking_tv_jifen01);
        jifen02= (TextView) view.findViewById(R.id.total_ranking_tv_jifen02);
        jifen03= (TextView) view.findViewById(R.id.total_ranking_tv_jifen03);
//        mPullRefreshScrollView= (PullToRefreshScrollView) view.findViewById(R.id.month_ranking_scrollview);
//        mPullRefreshScrollView.setMode(PullToRefreshBase.Mode.PULL_FROM_END);
//        mPullRefreshScrollView.getLoadingLayoutProxy().setRefreshingLabel("正在加载");
//        mPullRefreshScrollView.getLoadingLayoutProxy().setPullLabel("上拉加载更多");
//        mPullRefreshScrollView.getLoadingLayoutProxy().setReleaseLabel("释放开始加载");
        listView= (ListView) view.findViewById(R.id.total_ranking_listview);
        listView.setFocusable(false);
        layou01= (LinearLayout) view.findViewById(R.id.total_ranking_iv_layout01);
        layou02= (LinearLayout) view.findViewById(R.id.total_ranking_iv_layout02);
        layou03= (LinearLayout) view.findViewById(R.id.total_ranking_iv_layout03);
    }
}
