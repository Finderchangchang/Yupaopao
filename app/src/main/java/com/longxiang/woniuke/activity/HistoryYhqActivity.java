package com.longxiang.woniuke.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;

import com.longxiang.woniuke.R;

public class HistoryYhqActivity extends AppCompatActivity {
private ImageView ivBack;
    private ListView listView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history_yhq);
        ivBack= (ImageView) findViewById(R.id.historyyhq_iv_back);
        listView= (ListView) findViewById(R.id.historyyhq_listview);
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        listView.setClickable(false);
    }
}
