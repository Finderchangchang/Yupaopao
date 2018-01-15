package com.longxiang.woniuke.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.longxiang.woniuke.R;
import com.longxiang.woniuke.adapter.FriendAdapter;
import com.longxiang.woniuke.bean.AddFriendData;
import com.longxiang.woniuke.bean.FriendData;
import com.longxiang.woniuke.utils.MyApp;

import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

public class AddFriendActivity extends AppCompatActivity implements View.OnClickListener, TextView.OnEditorActionListener, AdapterView.OnItemClickListener {
    private ImageView ivBack;
    private EditText search;
    private PullToRefreshListView mPullToRefreshListView;
    private List<AddFriendData.DataBean> friendlist=new ArrayList<>();
    private int page=0;
    private FriendAdapter adapter;
    private TextView title;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_friend);
        setView();
        setListener();
    }

    private void setView() {
        ivBack= (ImageView) findViewById(R.id.addfriend_iv_back);
        search= (EditText) findViewById(R.id.addfriend_search);
        title= (TextView) findViewById(R.id.addfriend_title_name);
        title.setTextColor(Color.parseColor("#ffffff"));
        mPullToRefreshListView= (PullToRefreshListView) findViewById(R.id.pull_refresh_list);
//        mListView = mPullToRefreshListView.getRefreshableView();
        mPullToRefreshListView.setMode(PullToRefreshBase.Mode.PULL_FROM_END);

    }

    private void setListener() {
        ivBack.setOnClickListener(this);
        search.setOnEditorActionListener(this);
        mPullToRefreshListView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener<ListView>() {
            @Override
            public void onRefresh(PullToRefreshBase<ListView> refreshView) {
                if(refreshView.isFooterShown()) {
                    page+=1;
                    mPullToRefreshListView.getLoadingLayoutProxy().setRefreshingLabel("正在加载");
                    mPullToRefreshListView.getLoadingLayoutProxy().setPullLabel("上拉加载更多");
                    mPullToRefreshListView.getLoadingLayoutProxy().setReleaseLabel("释放开始加载");
                    searchFriend2();
                }
//                if(refreshView.isHeaderShown()){
//                    mPullToRefreshListView.onRefreshComplete();
//                }
            }
        });
        mPullToRefreshListView.setOnItemClickListener(this);
    }

    private void searchFriend2() {
        RequestParams params=new RequestParams("http://bubblefish.jbserver.cn/api/friend/searchUser");
        params.addBodyParameter("search",search.getText().toString());
        params.addBodyParameter("uid", MyApp.uid);
        params.addBodyParameter("lat",MyApp.lat_经度);
        params.addBodyParameter("lng",MyApp.lng_纬度);
        params.addBodyParameter("page",page+"");
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                try {
                    JSONObject obj=new JSONObject(result);
                    switch (obj.getString("retcode")){
                        case "4002":
                            Log.i("search", "onSuccess: "+result+"111");
                            Toast.makeText(getApplicationContext(),"没有更多数据啦",Toast.LENGTH_SHORT).show();
                            break;
                        case "2000":
                            Log.i("search", "onSuccess: "+result+"222");
                            AddFriendData data=new Gson().fromJson(result,AddFriendData.class);
                            friendlist.addAll(data.getData());
                            Log.i("search", "onSuccess: "+friendlist.size());
                            adapter.notifyDataSetChanged();
//                handler.sendEmptyMessage(1);
                            break;
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                mPullToRefreshListView.onRefreshComplete();
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
        finish();
    }

    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
        /*判断是否是“Search”键*/
        if(actionId == EditorInfo.IME_ACTION_SEARCH){
                          /*隐藏软键盘*/
            InputMethodManager imm = (InputMethodManager) v
                    .getContext().getSystemService(
                            Context.INPUT_METHOD_SERVICE);
            if (imm.isActive()) {
                imm.hideSoftInputFromWindow(
                        v.getApplicationWindowToken(), 0);
            }
            friendlist.clear();
            searchFriend();
            return true;
        }
        return false;
    }

    private void searchFriend() {
        RequestParams params=new RequestParams("http://bubblefish.jbserver.cn/api/friend/searchUser");
        params.addBodyParameter("search",search.getText().toString());
        params.addBodyParameter("uid", MyApp.uid);
        params.addBodyParameter("lat",MyApp.lat_经度);
        params.addBodyParameter("lng",MyApp.lng_纬度);
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Log.i("search", "onSuccess: "+result);
                try {
                    JSONObject obj=new JSONObject(result);
                    switch (obj.getString("retcode")){
                        case "4002":
                            Toast.makeText(getApplicationContext(),obj.getString("msg"),Toast.LENGTH_SHORT).show();
                            break;
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                AddFriendData data=new Gson().fromJson(result,AddFriendData.class);
                friendlist.addAll(data.getData());
                adapter=new FriendAdapter(AddFriendActivity.this,friendlist);
                mPullToRefreshListView.setAdapter(adapter);
//                handler.sendEmptyMessage(1);
//                mPullToRefreshListView.onRefreshComplete();
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
    protected void onDestroy() {
        super.onDestroy();
        page=0;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Log.i("friendsize", "onItemClick: "+friendlist.size());
        String fid=friendlist.get(position-1).getUid();
        Log.i("friendsize", "onItemClick: "+fid);
        Intent intent =new Intent(this,FriendDataActivity.class);
        intent.putExtra("fid",fid);
        startActivity(intent);
    }
}
