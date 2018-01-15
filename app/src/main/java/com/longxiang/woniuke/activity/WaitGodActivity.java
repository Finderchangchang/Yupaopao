package com.longxiang.woniuke.activity;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.longxiang.woniuke.R;

import java.util.Random;

public class WaitGodActivity extends AppCompatActivity {
//private ImageView ivBack;
    private TextView tvNum;
    private String bgnum;
    private String oid;
//    private ImageView iv;
    private String ucmoney="";
    private Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
//                case 1:
//                    if(Integer.parseInt(num)<=Integer.parseInt(bgnum)){
//                        int number=Integer.parseInt(num)+new Random().nextInt(5);
//                        number+=new Random().nextInt(3);
//                        tvNum.setText(number+"");
//                        handler.sendEmptyMessageDelayed(1,300);
//                    }else{
//                        tvNum.setText(bgnum);
//                    }
//                    break;
                case 2:
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            Intent intent=new Intent(WaitGodActivity.this,SelectGodActivity.class);
                            intent.putExtra("oid",oid);
                            intent.putExtra("ucmoney",ucmoney);
                            startActivity(intent);
                            finish();
                        }
                    },5000);

                    break;
            }
            super.handleMessage(msg);
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wait_god);
        Intent intent=getIntent();
        bgnum=intent.getStringExtra("bgnum");
        oid=intent.getStringExtra("oid");
        ucmoney=intent.getStringExtra("ucmoney");
        Toast.makeText(this,"请勿操作,即将选择大神",Toast.LENGTH_LONG).show();
        setView();
        handler.sendEmptyMessage(2);
    }

    private void setView() {
        tvNum= (TextView) findViewById(R.id.wait_god_tv_num);
        tvNum.setText(bgnum);
//        iv= (ImageView) findViewById(R.id.wait_god_iv);
//        Glide.with(this).load(R.drawable.dongdong).into(iv);
//        handler.sendEmptyMessageDelayed(1,300);
    }
}
