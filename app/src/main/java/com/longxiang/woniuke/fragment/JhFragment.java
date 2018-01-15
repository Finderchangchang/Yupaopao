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
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.gson.Gson;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.longxiang.woniuke.Interfaces.GzCallBack;
import com.longxiang.woniuke.R;
import com.longxiang.woniuke.activity.DynamicDetailActivity;
import com.longxiang.woniuke.adapter.FragmentDyAdapter;
import com.longxiang.woniuke.bean.FragmentDyData;
import com.longxiang.woniuke.utils.MyApp;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/8/15.
 */
public class JhFragment extends Fragment implements AdapterView.OnItemClickListener{
    private PullToRefreshListView mPullToRefreshListView;
    private int page=0;
    ArrayList<FragmentDyData.DataBean> dylist=new ArrayList<>();
    private FragmentDyAdapter adapter;
    Handler handler=new Handler();
    ProgressBar progressBar;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.jh_fragment,null);
        mPullToRefreshListView= (PullToRefreshListView) view.findViewById(R.id.jhfragment_listview);
        mPullToRefreshListView.setMode(PullToRefreshBase.Mode.BOTH);
        progressBar= (ProgressBar) view.findViewById(R.id.jhprogressBar);
        setListener();
        getEssencedynamic();
        return view;
    }

    private void getEssencedynamic() {
        RequestParams params=new RequestParams("http://bubblefish.jbserver.cn/api/friend/getEssencedynamic");
        params.addBodyParameter("lat", MyApp.lat_经度);
        params.addBodyParameter("lng",MyApp.lng_纬度);
        params.addBodyParameter("uid",MyApp.uid);
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(final String result) {

                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        dylist.clear();
                        progressBar.setVisibility(View.GONE);
                        Log.i("jffragment", "onSuccess: "+result);
                        FragmentDyData data=new Gson().fromJson(result,FragmentDyData.class);
                        dylist.addAll(data.getData());
                        adapter=new FragmentDyAdapter(getActivity(),dylist);
                        mPullToRefreshListView.setAdapter(adapter);
                        mPullToRefreshListView.onRefreshComplete();
                    }
                },1500);

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

    private void setListener() {
        mPullToRefreshListView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener<ListView>() {
            @Override
            public void onRefresh(PullToRefreshBase<ListView> refreshView) {
                if(refreshView.isFooterShown()) {
                    page+=1;
                    Log.i("jhfragment", "onRefresh: "+page);
                    Log.i("lvzhiweipage", "onRefresh: "+page);
                    mPullToRefreshListView.getLoadingLayoutProxy().setRefreshingLabel("正在加载");
                    mPullToRefreshListView.getLoadingLayoutProxy().setPullLabel("上拉加载更多");
                    mPullToRefreshListView.getLoadingLayoutProxy().setReleaseLabel("释放开始加载");
                    getEssencedynamic2();
                }
                if(refreshView.isHeaderShown()){
                    getEssencedynamic();
                }
            }
        });

        mPullToRefreshListView.setOnItemClickListener(this);
    }

    private void getEssencedynamic2() {
        RequestParams params=new RequestParams("http://bubblefish.jbserver.cn/api/friend/getEssencedynamic");
        params.addBodyParameter("lat", MyApp.lat_经度);
        params.addBodyParameter("lng",MyApp.lng_纬度);
        params.addBodyParameter("uid",MyApp.uid);
        params.addBodyParameter("page",page+"");
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(final String result) {
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Log.i("jffragment", "onSuccess: "+result);
                        FragmentDyData data=new Gson().fromJson(result,FragmentDyData.class);
                        if(data.getRetcode()==2000) {
                            dylist.addAll(data.getData());
                            adapter.notifyDataSetChanged();
                        }else{
                            page=page-1;
                            Toast.makeText(getActivity().getApplicationContext(),"没有更多啦",Toast.LENGTH_SHORT).show();
                        }
                        mPullToRefreshListView.onRefreshComplete();
                    }
                },1500);

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
        Intent intent=new Intent(getActivity(), DynamicDetailActivity.class);
        String dmid = dylist.get(position-1).getDmid();
        double distance=dylist.get(position-1).getDistance();
        String time=dylist.get(position-1).getTime();
        String uid=dylist.get(position-1).getUid();
        ArrayList<String> godlist=dylist.get(position-1).getGodpic();
        if(godlist.size()!=0||godlist!=null){
            intent.putStringArrayListExtra("godlist", godlist);
        }
        intent.putExtra("dmid",dmid);
        intent.putExtra("distance",distance+"");
        intent.putExtra("time",time);
        intent.putExtra("uid",uid);
        startActivity(intent);
    }
    private void zanTime() {
        RequestParams params=new RequestParams("http://bubblefish.jbserver.cn/api/friend/landByList");
        params.addBodyParameter("uid",MyApp.uid);
        params.addBodyParameter("json",getJsondata());
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Log.i("zanzanzan", "onSuccess: "+result);
                Log.i("zanzanzan", "onSuccess: "+getJsondata());
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

    private String getJsondata() {
        StringBuilder builder=new StringBuilder();
        builder.append("[");
        if (dylist.size()!=0) {
            for (int i = 0; i < dylist.size(); i++) {
                builder.append("{\"landtimes\":"+Integer.parseInt(dylist.get(i).getLandtimes())+",\"dmid\":"+Integer.parseInt(dylist.get(i).getDmid())+"},");
            }
            builder.deleteCharAt(builder.length()-1);
        }
        builder.append("]");

        return builder.toString();

    }
//    @Override
//    public void onResume() {
//        super.onResume();
//        dylist.clear();
//        getEssencedynamic();
//    }

    @Override
    public void onPause() {
        super.onPause();
        zanTime();
    }

}
