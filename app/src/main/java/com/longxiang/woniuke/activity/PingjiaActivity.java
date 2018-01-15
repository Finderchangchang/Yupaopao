package com.longxiang.woniuke.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.longxiang.woniuke.R;

import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import de.greenrobot.event.EventBus;

public class PingjiaActivity extends AppCompatActivity implements View.OnClickListener, CompoundButton.OnCheckedChangeListener {
private ImageView ivBack;
    private ImageView icon;
    private TextView tvname;
    private EditText etcontent;
    private RatingBar ratingBar;
    private Button btnConfirm;
    private String headpic;
    private String name;
    private String oid;
    private String bguid;
    private float rating;
    private CheckBox ckniming;
    private String noname="0";
    private TextView title;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pingjia);
        Intent intent=getIntent();
        headpic=intent.getStringExtra("head_pic");
        name=intent.getStringExtra("name");
        oid=intent.getStringExtra("oid");
        bguid=intent.getStringExtra("bguid");
        setView();
        setListener();
    }

    private void setView() {
        ivBack= (ImageView) findViewById(R.id.pingjia_iv_back);
        icon= (ImageView) findViewById(R.id.pingjia_icon);
        tvname= (TextView) findViewById(R.id.pingjia_name);
        etcontent= (EditText) findViewById(R.id.pingjia_et);
        ratingBar= (RatingBar) findViewById(R.id.pingjia_ratingbar);
        btnConfirm= (Button) findViewById(R.id.pingjia_btn);
        x.image().bind(icon,headpic);
        tvname.setText(name);
        ckniming= (CheckBox) findViewById(R.id.pingjia_niming);
        ckniming.setChecked(false);
        title= (TextView) findViewById(R.id.pingjia_title_name);
        title.setTextColor(Color.parseColor("#ffffff"));
        btnConfirm.setTextColor(Color.parseColor("#ffffff"));
    }

    private void setListener() {
        ivBack.setOnClickListener(this);
        btnConfirm.setOnClickListener(this);
        ckniming.setOnCheckedChangeListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.pingjia_iv_back:
                finish();
                break;
            case R.id.pingjia_btn:
                rating = ratingBar.getRating();
                if((int)rating==0){
                    Toast.makeText(PingjiaActivity.this,"大神不容易,请给大神评个分哦",Toast.LENGTH_SHORT).show();
                }else {
                    final AlertDialog.Builder builder = new AlertDialog.Builder(this);
                    builder.setTitle("蜗牛壳")
                            .setMessage("确认评价吗？")
                            .setPositiveButton("取消", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            }).setNegativeButton("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            pingjia();
                        }
                    }).create().show();
                }
                break;
        }
    }

    private void pingjia() {
        RequestParams params=new RequestParams("http://bubblefish.jbserver.cn/api/order/finishOrder");
        params.addBodyParameter("oid",oid);
        params.addBodyParameter("bguid",bguid);
        params.addBodyParameter("level",(int)rating+"");
        Log.i("rating", "pingjia: "+(int)rating+"");
        params.addBodyParameter("comment",etcontent.getText().toString());
        params.addBodyParameter("noname",noname);
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Log.i("pingjiaactivity", "onSuccess: "+result);
                try {
                    JSONObject obj=new JSONObject(result);
                    switch (obj.getString("retcode")){
                        case "2000":
                            Toast.makeText(getApplicationContext(),obj.getString("msg"),Toast.LENGTH_SHORT).show();
                            JdDetailActivity.instance.finish();
                            finish();
                            String json="refresh";
                            EventBus.getDefault().post(json);
                            break;
                        case "4000":
                        case "4001":
                        case "4003":
                        case "4004":
                        case "4010":
                        case "4011":
                            Toast.makeText(getApplicationContext(),obj.getString("msg"),Toast.LENGTH_SHORT).show();
                            break;
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
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

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if(isChecked){
            noname="1";
        }else{
            noname="0";
        }
    }
}
