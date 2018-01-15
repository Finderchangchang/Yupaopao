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
public class GzFragment extends Fragment implements AdapterView.OnItemClickListener {
    private PullToRefreshListView mPullToRefreshListView;
    private int page=0;
    List<FragmentDyData.DataBean> gzlist=new ArrayList<>();
    private FragmentDyAdapter adapter;
    Handler handler=new Handler();
    ProgressBar progressBar;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.gz_fragment,null);
        mPullToRefreshListView= (PullToRefreshListView) view.findViewById(R.id.gzfragment_listview);
        mPullToRefreshListView.setMode(PullToRefreshBase.Mode.BOTH);
        progressBar= (ProgressBar) view.findViewById(R.id.gzprogressBar);
        setListener();
        getFollowingDynamic();
        return view;
    }

    private void setListener() {
        mPullToRefreshListView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener<ListView>() {
            @Override
            public void onRefresh(PullToRefreshBase<ListView> refreshView) {
                if(refreshView.isFooterShown()) {
                    page+=1;
                    mPullToRefreshListView.getLoadingLayoutProxy().setRefreshingLabel("正在加载");
                    mPullToRefreshListView.getLoadingLayoutProxy().setPullLabel("上拉加载更多");
                    mPullToRefreshListView.getLoadingLayoutProxy().setReleaseLabel("释放开始加载");
                    getFollowingDynamic2();
                }
                if(refreshView.isHeaderShown()){
                    getFollowingDynamic();
                }
            }
        });
        mPullToRefreshListView.setOnItemClickListener(this);
    }

    private void getFollowingDynamic2() {
        RequestParams params=new RequestParams("http://bubblefish.jbserver.cn/api/friend/getFollowingDynamic");
        params.addBodyParameter("lat", MyApp.lat_经度);
        params.addBodyParameter("lng",MyApp.lng_纬度);
        params.addBodyParameter("uid",MyApp.uid);
        params.addBodyParameter("page",page+"");
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(final String result) {
                Log.i("gzfragment", "onSuccess: "+result);
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        FragmentDyData data=new Gson().fromJson(result,FragmentDyData.class);
                        if(data.getRetcode()==2000){
                            gzlist.addAll(data.getData());
                            adapter.notifyDataSetChanged();
                        }else{
                            page=page-1;
                            Toast.makeText(getActivity().getApplicationContext(),"没有更多动态啦",Toast.LENGTH_SHORT).show();
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

    private void getFollowingDynamic() {
        RequestParams params=new RequestParams("http://bubblefish.jbserver.cn/api/friend/getFollowingDynamic");
        params.addBodyParameter("lat", MyApp.lat_经度);
        params.addBodyParameter("lng",MyApp.lng_纬度);
        params.addBodyParameter("uid",MyApp.uid);
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(final String result) {
                Log.i("gzfragment", "onSuccess: "+result);
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        gzlist.clear();
                        progressBar.setVisibility(View.GONE);
                        FragmentDyData data=new Gson().fromJson(result,FragmentDyData.class);
                        if(data.getRetcode()==2000) {
                            gzlist.addAll(data.getData());
                            adapter = new FragmentDyAdapter(getActivity().getApplicationContext(), gzlist);
                            mPullToRefreshListView.setAdapter(adapter);
                        }
                        mPullToRefreshListView.onRefreshComplete();
                    }
                },1500);
//                if(data.getRetcode()==4000){
//                    Toast.makeText(getActivity().getApplicationContext(),data.getMsg(),Toast.LENGTH_SHORT).show();
//                }
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
        String dmid = gzlist.get(position-1).getDmid();
        double distance=gzlist.get(position-1).getDistance();
        String time=gzlist.get(position-1).getTime();
        String uid=gzlist.get(position-1).getUid();
        ArrayList<String> godlist=gzlist.get(position-1).getGodpic();
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
        if (gzlist.size()!=0) {
            for (int i = 0; i < gzlist.size(); i++) {
                builder.append("{\"landtimes\":"+Integer.parseInt(gzlist.get(i).getLandtimes())+",\"dmid\":"+Integer.parseInt(gzlist.get(i).getDmid())+"},");
            }
            builder.deleteCharAt(builder.length()-1);
        }
        builder.append("]");

        return builder.toString();

    }
//    @Override
//    public void onResume() {
//        super.onResume();
//        gzlist.clear();
//        getFollowingDynamic();
//    }

    @Override
    public void onPause() {
        super.onPause();
        zanTime();
    }
}
