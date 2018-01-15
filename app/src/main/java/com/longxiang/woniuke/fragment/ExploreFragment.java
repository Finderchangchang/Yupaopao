package com.longxiang.woniuke.fragment;

import android.app.Fragment;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.longxiang.woniuke.R;
import com.longxiang.woniuke.activity.DynamicActivity;
import com.longxiang.woniuke.activity.MainActivity;
import com.longxiang.woniuke.activity.NearPeopleActivity;
import com.longxiang.woniuke.activity.OfficalActivity;
import com.longxiang.woniuke.activity.RankingActivity;

/**
 * Created by Administrator on 2016/5/17 0017.
 */
public class ExploreFragment extends Fragment implements View.OnClickListener {
    private View sub;
    private TextView title;
    private LinearLayout llDynamic;
    private LinearLayout llPaihang;
    private LinearLayout llNear;
    private LinearLayout llOfficial;
    private ImageView ivhongdian;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.item_explorefragment,null);
        init(view);
        setListener();
        return view;
    }

    private void init(View view) {
        sub = (View) view.findViewById(R.id.sub_title_explore);
        title = (TextView) sub.findViewById(R.id.tv_title_name);
        title.setText("探索");
        title.setTextColor(Color.WHITE);
        llDynamic= (LinearLayout) view.findViewById(R.id.explore_ll01);
        llPaihang= (LinearLayout) view.findViewById(R.id.explore_ll02);
        llNear= (LinearLayout) view.findViewById(R.id.explore_ll03);
        llOfficial= (LinearLayout) view.findViewById(R.id.explore_ll04);
        ivhongdian= (ImageView) view.findViewById(R.id.explore_ll01_hongdian);
    }
    private void setListener() {
        llDynamic.setOnClickListener(this);
        llPaihang.setOnClickListener(this);
        llNear.setOnClickListener(this);
        llOfficial.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()){
            case R.id.explore_ll01:
                intent=new Intent(getActivity(), DynamicActivity.class);
                ivhongdian.setVisibility(View.GONE);
                MainActivity.ivsechd.setVisibility(View.GONE);
                startActivity(intent);
                break;
            case R.id.explore_ll02:
                intent=new Intent(getActivity(), RankingActivity.class);
                startActivity(intent);
                break;
            case R.id.explore_ll03:
                intent=new Intent(getActivity(), NearPeopleActivity.class);
                startActivity(intent);
                break;
            case R.id.explore_ll04:
                intent=new Intent(getActivity(), OfficalActivity.class);
                startActivity(intent);
                break;
        }
    }
}
