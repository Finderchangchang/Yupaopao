package com.longxiang.woniuke.activity;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.longxiang.woniuke.R;
import com.longxiang.woniuke.adapter.OrderAdapter;
import com.longxiang.woniuke.bean.AddFriendData;
import com.longxiang.woniuke.bean.OrderData;
import com.longxiang.woniuke.utils.MyApp;

import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

import de.greenrobot.event.EventBus;
import de.greenrobot.event.Subscribe;
import de.greenrobot.event.ThreadMode;

public class OrderCenterActivity extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemClickListener {
    private ImageView ivBack;
    private PullToRefreshListView mPullToRefreshListView;
    private List<OrderData.DataBean> orderlist = new ArrayList<>();
    private int page = 0;
    private OrderAdapter adapter;
    private TextView title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_center);
        EventBus.getDefault().register(this);
        setView();
        setListener();
        orderlist.clear();
        getOrder();
        setRobMessageReadState();
    }

    //state（1-抢单消息 2-订单消息 3-系统消息）
    private void setRobMessageReadState() {
        RequestParams params = new RequestParams("http://bubblefish.jbserver.cn/api/order/setRobMessageReadState");
        params.addBodyParameter("uid", MyApp.uid);
        params.addBodyParameter("state", "2");
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {

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

    private void getOrder() {
        RequestParams params = new RequestParams("http://bubblefish.jbserver.cn/api/order/getGeneralOrder");
        params.addBodyParameter("uid", MyApp.uid);
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Log.i("orderdata", "onSuccess: " + result);
                OrderData data = new Gson().fromJson(result, OrderData.class);
                orderlist.addAll(data.getData());
                adapter = new OrderAdapter(OrderCenterActivity.this, orderlist);
                mPullToRefreshListView.setAdapter(adapter);
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

    private void getOrder2() {
        RequestParams params = new RequestParams("http://bubblefish.jbserver.cn/api/order/getGeneralOrder");
        params.addBodyParameter("uid", MyApp.uid);
        Log.i("orderdata", "getOrder2: " + page);
        params.addBodyParameter("page", page + "");
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Log.i("orderdata", "onSuccess: " + result);
                try {
                    JSONObject obj = new JSONObject(result);
                    switch (obj.getString("retcode")) {
                        case "4000":
                            Log.i("search", "onSuccess: " + result + "111");
                            page = page - 1;
                            Toast.makeText(getApplicationContext(), obj.getString("msg"), Toast.LENGTH_SHORT).show();
                            break;
                        case "2000":
                            Log.i("search", "onSuccess: " + result + "222");
                            OrderData data = new Gson().fromJson(result, OrderData.class);
                            orderlist.addAll(data.getData());
                            adapter.notifyDataSetChanged();
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

    private void setView() {
        ivBack = (ImageView) findViewById(R.id.ordercenter_iv_back);
        mPullToRefreshListView = (PullToRefreshListView) findViewById(R.id.ordercenter_pull_refresh_list);
        mPullToRefreshListView.setMode(PullToRefreshBase.Mode.PULL_FROM_END);
        title = (TextView) findViewById(R.id.ordercenter_tv_title_name);
        title.setTextColor(Color.parseColor("#ffffff"));
    }

    private void setListener() {
        ivBack.setOnClickListener(this);
        mPullToRefreshListView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener<ListView>() {
            @Override
            public void onRefresh(PullToRefreshBase<ListView> refreshView) {
                if (refreshView.isFooterShown()) {
                    page += 1;
                    mPullToRefreshListView.getLoadingLayoutProxy().setRefreshingLabel("正在加载");
                    mPullToRefreshListView.getLoadingLayoutProxy().setPullLabel("上拉加载更多");
                    mPullToRefreshListView.getLoadingLayoutProxy().setReleaseLabel("释放开始加载");
                    getOrder2();
                }
//                if(refreshView.isHeaderShown()){
//                    mPullToRefreshListView.onRefreshComplete();
//                }
            }
        });
        mPullToRefreshListView.setOnItemClickListener(this);
    }

    @Override
    public void onClick(View v) {
        finish();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        String uid = orderlist.get(position - 1).getOrder().getUid();
        Log.i("orderlist", "onItemClick: " + uid);
        String head_pic = orderlist.get(position - 1).getGod().getHead_pic();
        String name = orderlist.get(position - 1).getGod().getNickname();
        String gender = orderlist.get(position - 1).getGod().getSex();
        String age = orderlist.get(position - 1).getGod().getAge();
        float level = orderlist.get(position - 1).getGod().getLevel();
        String mobile = orderlist.get(position - 1).getGod().getMobile();
        String type = orderlist.get(position - 1).getOrder().getTypename();
        String typeimg = orderlist.get(position - 1).getOrder().getTypeimg();
        String time = orderlist.get(position - 1).getOrder().getRealtime();
        String times = orderlist.get(position - 1).getOrder().getTimes();
        String price = orderlist.get(position - 1).getOrder().getAmount();
        String state = orderlist.get(position - 1).getOrder().getState();
        String oid = orderlist.get(position - 1).getOid();
        String bguid = orderlist.get(position - 1).getBguid();
        String bgid = orderlist.get(position - 1).getOrder().getBgid();
        String unit = orderlist.get(position - 1).getOrder().getUnit();
        Log.i("lkjlkj", "onItemClick: " + state);
        if (state.equals("4")) {
            Intent intent = new Intent(this, CompletedOrderActivity.class);
            intent.putExtra("head_pic", head_pic);
            intent.putExtra("name", name);
            intent.putExtra("typename", type);
            intent.putExtra("typeimg", typeimg);
            intent.putExtra("time", time);
            intent.putExtra("times", times);
            intent.putExtra("mobile", mobile);
            intent.putExtra("oid", oid);
            intent.putExtra("bguid", bguid);
            intent.putExtra("unit", unit);
            startActivity(intent);
        } else {
            Intent intent = new Intent(this, JdDetailActivity.class);
            intent.putExtra("head_pic", head_pic);
            intent.putExtra("name", name);
            intent.putExtra("gender", gender);
            intent.putExtra("age", age);
            intent.putExtra("level", level);
            intent.putExtra("mobile", mobile);
            intent.putExtra("type", type);
            intent.putExtra("time", time);
            intent.putExtra("times", times);
            intent.putExtra("price", price);
            intent.putExtra("state", state);
            intent.putExtra("oid", oid);
            intent.putExtra("bguid", bguid);
            intent.putExtra("bgid", bgid);
            intent.putExtra("unit", unit);
            if (uid.equals(MyApp.uid)) {
                intent.putExtra("uid", bguid);
            } else {
                intent.putExtra("uid", uid);
            }
            intent.putExtra("mlgbuid", uid);
            startActivity(intent);
        }
    }

    //    @Override
//    protected void onResume() {
//        super.onResume();
//        orderlist.clear();
//        getOrder();
//
//    }
    @Subscribe(threadMode = ThreadMode.MainThread)
    public void countEventBus(String post) {
        if (post.equals("refresh")) {
            page = 0;
            orderlist.clear();
            getOrder();
        }

    }

//    @Override
//    protected void onRestart() {
//        super.onRestart();
//        page=0;
//        orderlist.clear();
//        getOrder();
//
//    }
}
