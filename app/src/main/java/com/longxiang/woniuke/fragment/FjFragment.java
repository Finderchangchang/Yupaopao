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
public class FjFragment extends Fragment implements AdapterView.OnItemClickListener {
    private ListView listView;
    private int count=10;
    private PullToRefreshListView mPullToRefreshListView;
    List<FragmentDyData.DataBean> fjlist=new ArrayList<>();
    private FragmentDyAdapter adapter;
    Handler handler=new Handler();
    ProgressBar progressBar;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fj_fragment,null);
        mPullToRefreshListView= (PullToRefreshListView) view.findViewById(R.id.fjfragment_listview);
        mPullToRefreshListView.setMode(PullToRefreshBase.Mode.PULL_FROM_START);
        progressBar= (ProgressBar) view.findViewById(R.id.fjprogressBar);
        setListener();
        getNearDynamic();
        return view;
    }

    private void getNearDynamic() {
        RequestParams params=new RequestParams("http://bubblefish.jbserver.cn/api/friend/getNearDynamic");
        params.addBodyParameter("lat", MyApp.lat_经度);
        params.addBodyParameter("lng",MyApp.lng_纬度);
        params.addBodyParameter("uid",MyApp.uid);
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(final String result) {
                Log.i("fjfragment", "onSuccess: "+result);
               handler.postDelayed(new Runnable() {
                   @Override
                   public void run() {
                       FragmentDyData data=new Gson().fromJson(result,FragmentDyData.class);
//                if(data.getRetcode()==4000){
//                    Toast.makeText(getActivity().getApplicationContext(),data.getMsg(),Toast.LENGTH_SHORT).show();
//                }
                       fjlist.clear();
                       progressBar.setVisibility(View.GONE);
                       if(data.getRetcode()==2000){
                           fjlist.addAll(data.getData());
                           adapter=new FragmentDyAdapter(getActivity(),fjlist);
                           mPullToRefreshListView.setAdapter(adapter);
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

    private void setListener() {
        mPullToRefreshListView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener<ListView>() {
            @Override
            public void onRefresh(PullToRefreshBase<ListView> refreshView) {
//                if(refreshView.isFooterShown()) {
//
//                    mPullToRefreshListView.getLoadingLayoutProxy().setRefreshingLabel("正在加载");
//                    mPullToRefreshListView.getLoadingLayoutProxy().setPullLabel("上拉加载更多");
//                    mPullToRefreshListView.getLoadingLayoutProxy().setReleaseLabel("释放开始加载");
//
//                }
                if(refreshView.isHeaderShown()){
                    getNearDynamic();
                }
            }
        });
        mPullToRefreshListView.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent=new Intent(getActivity(), DynamicDetailActivity.class);
        String dmid = fjlist.get(position-1).getDmid();
        double distance=fjlist.get(position-1).getDistance();
        String time=fjlist.get(position-1).getTime();
        String uid=fjlist.get(position-1).getUid();
        ArrayList<String> godlist=fjlist.get(position-1).getGodpic();
        if(godlist.size()!=0||godlist!=null){
            intent.putStringArrayListExtra("godlist", godlist);
        }
        intent.putExtra("dmid",dmid);
        intent.putExtra("distance",distance+"");
        intent.putExtra("time",time);
        intent.putExtra("uid",uid);
        startActivity(intent);
    }
}
