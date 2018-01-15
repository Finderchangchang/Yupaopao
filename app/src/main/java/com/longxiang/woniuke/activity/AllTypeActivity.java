package com.longxiang.woniuke.activity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.longxiang.woniuke.R;
import com.longxiang.woniuke.adapter.HomeGridviewAdapter;
import com.longxiang.woniuke.bean.XmData;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

public class AllTypeActivity extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemClickListener {
private ImageView ivBack;
    private TextView tvTitle;
    private GridView gridView;
    private XmData data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_type);
        setView();
        setListener();
        getGridviewdata();
    }

    private void setView() {
        ivBack= (ImageView) findViewById(R.id.alltype_iv_back);
        tvTitle= (TextView) findViewById(R.id.alltype_title);
        tvTitle.setTextColor(Color.parseColor("#ffffff"));
        gridView= (GridView) findViewById(R.id.alltype_gridview);
        gridView.setSelector(new ColorDrawable(Color.TRANSPARENT));
    }

    private void setListener() {
        ivBack.setOnClickListener(this);
        gridView.setOnItemClickListener(this);
    }
    private void getGridviewdata() {
        RequestParams params=new RequestParams("http://bubblefish.jbserver.cn/api/order/getAlltype");
        x.http().get(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Log.i("homefragment2", "onSuccess: "+result);
                data=new Gson().fromJson(result,XmData.class);
                gridView.setAdapter(new AllTypeAdapter(AllTypeActivity.this,data.getData()));
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
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        XmData.DataBean datas = data.getData().get(position);
        String jnid = datas.getId();
        String jnname = datas.getTitle();

        Intent intent=new Intent(AllTypeActivity.this, DsflActivity.class);
        intent.putExtra("jnid", jnid);
        intent.putExtra("jnname", jnname);
        intent.putStringArrayListExtra("price",datas.getPrice());
        startActivity(intent);
    }
}
