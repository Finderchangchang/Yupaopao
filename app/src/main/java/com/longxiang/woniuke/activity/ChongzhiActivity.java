package com.longxiang.woniuke.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.longxiang.woniuke.R;
import com.longxiang.woniuke.utils.MyApp;
import com.pingplusplus.android.Pingpp;
import com.pingplusplus.android.PingppLog;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ChongzhiActivity extends AppCompatActivity implements View.OnClickListener, View.OnFocusChangeListener, TextView.OnEditorActionListener {
    private ImageView ivBack;
    private TextView tvMoney;
    private Button btn01;
    private Button btn02;
    private Button btn03;
    private Button btn04;
    private Button btn05;
    private Button btn06;
    private EditText etMoney;
    private RelativeLayout rlWeixin;
    private RelativeLayout rlZfb;
    private ImageView ivWeixin;
    private ImageView ivZfb;
    private Button btnZf;
    private List<Button> buttons;
    private int currentindex;
    private String money;
    private String order;
    private String CHANNEL = "wx";
    private String zmoney;
    private TextView title;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chongzhi);
        Intent intent=getIntent();
        zmoney=intent.getStringExtra("money");
        setView();
        setData();
        setListener();
        PingppLog.DEBUG = true;
    }

    private void setView() {
        ivBack= (ImageView) findViewById(R.id.chongzhi_iv_back);
        tvMoney= (TextView) findViewById(R.id.chongzhi_money);
        tvMoney.setText(zmoney);
        btn01= (Button) findViewById(R.id.chongzhi_btn01);
        btn02= (Button) findViewById(R.id.chongzhi_btn02);
        btn03= (Button) findViewById(R.id.chongzhi_btn03);
        btn04= (Button) findViewById(R.id.chongzhi_btn04);
        btn05= (Button) findViewById(R.id.chongzhi_btn05);
        btn06= (Button) findViewById(R.id.chongzhi_btn06);
        etMoney= (EditText) findViewById(R.id.chongzhi_etMoney);
        rlWeixin= (RelativeLayout) findViewById(R.id.chongzhi_rl_weixin);
        rlZfb= (RelativeLayout) findViewById(R.id.chongzhi_rl_zfb);
        ivWeixin= (ImageView) findViewById(R.id.chongzhi_iv_weixin);
        ivZfb= (ImageView) findViewById(R.id.chongzhi_iv_zfb);
        btnZf= (Button) findViewById(R.id.chongzhi_btnZf);
        etMoney.setFocusable(false);
        title= (TextView) findViewById(R.id.chongzhi_title_name);
        title.setTextColor(Color.parseColor("#ffffff"));
        btnZf.setTextColor(Color.parseColor("#ffffff"));
    }
    private void setData() {
        buttons=new ArrayList<>();
        buttons.add(btn01);
        buttons.add(btn02);
        buttons.add(btn03);
        buttons.add(btn04);
        buttons.add(btn05);
        buttons.add(btn06);
    }
    private void setListener() {
        for (int i=0;i<buttons.size();i++){
            buttons.get(i).setOnClickListener(this);
        }
//        etMoney.addTextChangedListener(new EditTitleChangedListener());
        etMoney.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnZf.setText("支付0元");
                etMoney.setFocusable(true);
                etMoney.setFocusableInTouchMode(true);
                etMoney.requestFocus();
                for (int i=0;i<buttons.size();i++){
                    buttons.get(i).setSelected(false);
                }

            }
        });
        etMoney.setOnFocusChangeListener(this);
        ivBack.setOnClickListener(this);
        rlWeixin.setOnClickListener(this);
        rlZfb.setOnClickListener(this);
        btnZf.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.chongzhi_iv_back:
                finish();
                break;
            case R.id.chongzhi_rl_weixin:
                ivWeixin.setImageResource(R.mipmap.lanquan);
                ivZfb.setImageResource(R.mipmap.huiquan);
                CHANNEL="wx";
                break;
            case R.id.chongzhi_rl_zfb:
                ivWeixin.setImageResource(R.mipmap.huiquan);
                ivZfb.setImageResource(R.mipmap.lanquan);
                CHANNEL="alipay";
                break;
            case R.id.chongzhi_btnZf:
                Log.i("lvzhiweimoney", "onClick: "+money);
                if(btnZf.getText().toString().equals("支付0元")){
                    Toast.makeText(getApplicationContext(),"请选择金额",Toast.LENGTH_SHORT).show();
                }else{
//                    Log.i("lvzhiweibtnzf", "onClick: "+CHANNEL+","+money);
                    btnZf.setOnClickListener(null);
                    getOrder();
                }
                break;
            case R.id.chongzhi_btn01:
                currentindex=0;
                changebtn();
                break;
            case R.id.chongzhi_btn02:
                currentindex=1;
                changebtn();
                break;
            case R.id.chongzhi_btn03:
                currentindex=2;
                changebtn();
                break;
            case R.id.chongzhi_btn04:
                currentindex=3;
                changebtn();
                break;
            case R.id.chongzhi_btn05:
                currentindex=4;
                changebtn();
                break;
            case R.id.chongzhi_btn06:
                currentindex=5;
                changebtn();
                break;
        }
    }
    public void changebtn(){
        etMoney.setFocusable(false);
        for (int i=0;i<buttons.size();i++){
            buttons.get(i).setSelected(false);
        }
        buttons.get(currentindex).setSelected(true);
        money=buttons.get(currentindex).getText().toString().substring(0,buttons.get(currentindex).getText().toString().length()-1);
        btnZf.setText("支付"+money+"元");
        etMoney.setText("");
        Log.i("moneymoney", "changebtn: "+money);
    }

    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        if(etMoney.hasFocus()){
//            etMoney.addTextChangedListener(new EditTitleChangedListener());
//            btnZf.setText("支付0元");
            etMoney.setOnEditorActionListener(this);
        }else{
            money=buttons.get(currentindex).getText().toString().substring(0,buttons.get(currentindex).getText().toString().length()-1);
            btnZf.setText("支付"+money+"元");
        }
    }

    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
        /*判断是否是“Search”键*/
        if(actionId == EditorInfo.IME_ACTION_DONE){
                          /*隐藏软键盘*/
                InputMethodManager imm = (InputMethodManager) v
                        .getContext().getSystemService(
                                Context.INPUT_METHOD_SERVICE);
                if (imm.isActive()) {
                    imm.hideSoftInputFromWindow(
                            v.getApplicationWindowToken(), 0);
                }
            if(etMoney.getText().toString().equals("")){
                money=0+"";
                btnZf.setText("支付"+money+"元");
            }else {
                money=etMoney.getText().toString();
                Log.i("lvzhiweimoney", "onEditorAction:"+money);
                btnZf.setText("支付"+money+"元");
            }
            return true;
        }
        return false;
    }

    private void getOrder() {
        RequestParams params=new RequestParams("http://bubblefish.jbserver.cn/api/order/czorder");
        params.addBodyParameter("uid", MyApp.uid);
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Log.i("lvzhiweiorder", "onSuccess: "+result);
                try {
                    JSONObject obj=new JSONObject(result);
                    if(obj.getString("retcode").equals("2000")) {
                        order = obj.getString("data");
                        Log.i("lvzhiweiorder", "onSuccess: "+Integer.parseInt(money)*100);
                        new PaymentTask().execute(new PaymentRequest(CHANNEL, Integer.parseInt(money)*100,order));
                    }
                    Log.i("lvzhiweiorder", "onSuccess: "+order);
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
    class PaymentTask extends AsyncTask<PaymentRequest, Void, String> {

        @Override
        protected void onPreExecute() {
            //按键点击之后的禁用，防止重复点击
            btnZf.setOnClickListener(null);
        }

        @Override
        protected String doInBackground(PaymentRequest... pr) {

            PaymentRequest paymentRequest = pr[0];
            String data = null;
            String json = new Gson().toJson(paymentRequest);
            Log.i("lvzhiweijson", "doInBackground: "+json);
            try {
                //向Your Ping++ Server SDK请求数据
                data = postJson("http://bubblefish.jbserver.cn/api/ping/czcharge", json);

            } catch (Exception e) {
                e.printStackTrace();
            }
            return data;
        }

        /**
         * 获得服务端的charge，调用ping++ sdk。
         */
        @Override
        protected void onPostExecute(String data) {
            if(null == data){
                Log.i("lvzhiweipingpp","请求出错"+"请检查URL"+"URL无法获取charge");
                return;
            }
            Log.d("chargedata", data);
            Pingpp.createPayment(ChongzhiActivity.this, data);
        }

    }
    private static String postJson(String url, String json) throws IOException {
        MediaType type = MediaType.parse("application/json; charset=utf-8");
        RequestBody body = RequestBody.create(type, json);
        Request request = new Request.Builder().url(url).post(body).build();

        OkHttpClient client = new OkHttpClient();
        Response response = client.newCall(request).execute();

        return response.body().string();
    }
    /**
     * onActivityResult 获得支付结果，如果支付成功，服务器会收到ping++ 服务器发送的异步通知。
     * 最终支付成功根据异步通知为准
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        btnZf.setOnClickListener(ChongzhiActivity.this);

        //支付页面返回处理
        if (requestCode == Pingpp.REQUEST_CODE_PAYMENT) {
            if (resultCode == Activity.RESULT_OK) {
                String result = data.getExtras().getString("pay_result");
                /* 处理返回值
                 * "success" - payment succeed
                 * "fail"    - payment failed
                 * "cancel"  - user canceld
                 * "invalid" - payment plugin not installed
                 */
                String errorMsg = data.getExtras().getString("error_msg"); // 错误信息
                String extraMsg = data.getExtras().getString("extra_msg"); // 错误信息
                if(result.equals("success")){
                    etMoney.setText("");
                    money=0+"";
                    btnZf.setText("支付"+money+"元");
                    btnZf.setOnClickListener(this);
//                    etMoney.setFocusable(false);
                    Toast.makeText(getApplicationContext(),"充值成功",Toast.LENGTH_SHORT).show();
                }
                if(result.equals("fail")){
                    Toast.makeText(getApplicationContext(),"充值失败",Toast.LENGTH_SHORT).show();
                    btnZf.setOnClickListener(this);
                }
                Log.i("lvzhiweipingpp", "onActivityResult: "+result+","+errorMsg+","+extraMsg);
            }
        }
    }

    class PaymentRequest {
        String channel;
        int amount;
        String order_no;
        public PaymentRequest(String channel, int amount,String order_no) {
            this.channel = channel;
            this.amount = amount;
            this.order_no=order_no;
        }
    }
    private void getAccount() {
        RequestParams params=new RequestParams("http://bubblefish.jbserver.cn/api/users/getmywallet");
        params.addBodyParameter("uid", MyApp.uid);
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Log.i("lvzhiwei231",result);
                try {
                    JSONObject json =new JSONObject(result);
                    JSONObject obj=json.getJSONObject("data");
                    zmoney=obj.getString("wallet");
                    tvMoney.setText(zmoney);
                }catch (Exception e){
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
    protected void onResume() {
        super.onResume();
        getAccount();
        btnZf.setOnClickListener(this);
    }
}
