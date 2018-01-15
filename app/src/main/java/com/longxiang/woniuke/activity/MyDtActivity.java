package com.longxiang.woniuke.activity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.longxiang.woniuke.R;
import com.longxiang.woniuke.adapter.DynamicAdapter;
import com.longxiang.woniuke.bean.DynamicData;
import com.longxiang.woniuke.utils.MyApp;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

public class MyDtActivity extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemClickListener {
private ImageView ivBack;
    private ListView listView;
    private ImageView ivNone;
    private Button btnFabu;
    private ImageView ivFabu;
    private DynamicData data;
    private String fid;
    private String name;
    private TextView tvTitle;
    private String dmid;
    private DynamicAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_dt);
        setView();
        Intent intent=getIntent();
        fid = intent.getStringExtra("fid");
        name=intent.getStringExtra("name");
        if(fid==null&&name==null){
            fid=MyApp.uid;
            name="我的动态";
        }else {
            name=name+"的动态";
            ivFabu.setVisibility(View.GONE);
        }
        tvTitle.setText(name);
        setListener();
        getDynamic();
    }

    private void getDynamic() {
        RequestParams params=new RequestParams("http://bubblefish.jbserver.cn/api/friend/getDynamicbyuid");
        params.addBodyParameter("uid", fid);
        params.addBodyParameter("lat",MyApp.lat_经度);
        params.addBodyParameter("lng",MyApp.lng_纬度);
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Log.i("dynamic", "onSuccess: "+result);
                data=new Gson().fromJson(result,DynamicData.class);
                if(data.getData().size()!=0) {
                    adapter=new DynamicAdapter(MyDtActivity.this, data.getData());
                    listView.setAdapter(adapter);
                    ivNone.setVisibility(View.GONE);
                    btnFabu.setVisibility(View.GONE);
                    listView.setVisibility(View.VISIBLE);
                }else{
                    listView.setVisibility(View.GONE);
                    ivNone.setVisibility(View.VISIBLE);
                    btnFabu.setVisibility(View.VISIBLE);
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
        ivBack= (ImageView) findViewById(R.id.mydt_iv_back);
        listView= (ListView) findViewById(R.id.mydt_listview);
        ivNone= (ImageView) findViewById(R.id.mydt_iv_meiyong);
        ivFabu= (ImageView) findViewById(R.id.mydt_iv_help);
        btnFabu= (Button) findViewById(R.id.mydt_btn_fabudt);
        tvTitle= (TextView) findViewById(R.id.mydt_tv_title_name);
        tvTitle.setTextColor(Color.parseColor("#ffffff"));
        btnFabu.setTextColor(Color.parseColor("#ffffff"));
    }

    private void setListener() {
        ivBack.setOnClickListener(this);
        ivFabu.setOnClickListener(this);
        btnFabu.setOnClickListener(this);
        listView.setOnItemClickListener(this);
        listView.setSelector(new ColorDrawable(Color.TRANSPARENT));
    }

    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()){
            case R.id.mydt_iv_back:
                finish();
                break;
            case R.id.mydt_iv_help:
                intent=new Intent(this,FabuDtActivity.class);
                startActivity(intent);
                break;
            case R.id.mydt_btn_fabudt:
                intent=new Intent(this,FabuDtActivity.class);
                startActivity(intent);
                break;
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        dmid = data.getData().get(position).getDmid();
        Intent intent=new Intent(MyDtActivity.this,DynamicDetailActivity.class);
        intent.putExtra("dmid",dmid);
        intent.putExtra("distance",""+(Double)data.getData().get(position).getDistance());
        intent.putExtra("time",data.getData().get(position).getTime());
        intent.putExtra("uid",data.getData().get(position).getUid());
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
        if (data.getData()!=null&&data.getData().size()!=0) {
            for (int i = 0; i < data.getData().size(); i++) {
                builder.append("{\"landtimes\":"+Integer.parseInt(data.getData().get(i).getLandtimes())+",\"dmid\":"+Integer.parseInt(data.getData().get(i).getDmid())+"},");
            }
            builder.deleteCharAt(builder.length()-1);
        }
        builder.append("]");

        return builder.toString();

    }

    @Override
    protected void onResume() {
        super.onResume();
        getDynamic();
    }

    @Override
    protected void onPause() {
        super.onPause();
        zanTime();
    }
}

