package com.longxiang.woniuke.activity;

import android.graphics.Color;
//import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.longxiang.woniuke.R;
import com.longxiang.woniuke.fragment.FjFragment;
import com.longxiang.woniuke.fragment.GzFragment;
import com.longxiang.woniuke.fragment.JhFragment;
import com.longxiang.woniuke.fragment.MonthRankingFragment;
import com.longxiang.woniuke.fragment.TotalRankingFragment;
import com.longxiang.woniuke.fragment.WeekRankingFragment;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

public class RankingActivity extends AppCompatActivity implements RadioGroup.OnCheckedChangeListener, ViewPager.OnPageChangeListener {
    private ImageView ivBack;
//    private TabLayout tab_FindFragment_title;                            //定义TabLayout
    private ViewPager vp_FindFragment_pager;                             //定义viewPager
    private FragmentPagerAdapter fAdapter;
    private List<Fragment> list_fragment;                                //定义要装fragment的列表
    private List<String> list_title;
    private TextView title;
    private RadioGroup rg;
    private RadioButton rb01;
    private RadioButton rb02;
    private RadioButton rb03;
    private List<RadioButton> rbs;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ranking);
        setView();
        setListener();
        updateRank();
    }

    private void updateRank() {
        RequestParams params=new RequestParams("http://bubblefish.jbserver.cn/api/friend/updateRank");
        x.http().get(params, new Callback.CommonCallback<String>() {
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

    private void setView() {
        title= (TextView) findViewById(R.id.ranking_tv_title_name);
        title.setTextColor(Color.parseColor("#ffffff"));
        ivBack= (ImageView) findViewById(R.id.ranking_iv_back);
//        tab_FindFragment_title= (TabLayout) findViewById(R.id.ranking_tabLayout);
        vp_FindFragment_pager= (ViewPager) findViewById(R.id.ranking_viewPager);
        vp_FindFragment_pager.setOffscreenPageLimit(3);
        list_fragment=new ArrayList<>();
        list_fragment.add(new WeekRankingFragment());
        list_fragment.add(new MonthRankingFragment());
        list_fragment.add(new TotalRankingFragment());
        list_title=new ArrayList<>();
        list_title.add("周榜");
        list_title.add("月榜");
        list_title.add("总榜");
        rg= (RadioGroup) findViewById(R.id.ranking_rg);
        rb01= (RadioButton) findViewById(R.id.ranking_rb01);
        rb02= (RadioButton) findViewById(R.id.ranking_rb02);
        rb03= (RadioButton) findViewById(R.id.ranking_rb03);
        rbs=new ArrayList<>();
        rbs.add(rb01);
        rbs.add(rb02);
        rbs.add(rb03);
        //设置TabLayout的模式
//        tab_FindFragment_title.setTabMode(TabLayout.MODE_FIXED);
        //为TabLayout添加tab名称
//        tab_FindFragment_title.addTab(tab_FindFragment_title.newTab().setText(list_title.get(0)));
//        tab_FindFragment_title.addTab(tab_FindFragment_title.newTab().setText(list_title.get(1)));
//        tab_FindFragment_title.addTab(tab_FindFragment_title.newTab().setText(list_title.get(2)));

        fAdapter = new Find_tab_Adapter(getSupportFragmentManager(),list_fragment,list_title);

        //viewpager加载adapter
        vp_FindFragment_pager.setAdapter(fAdapter);
        //tab_FindFragment_title.setViewPager(vp_FindFragment_pager);
        //TabLayout加载viewpager
//        tab_FindFragment_title.setupWithViewPager(vp_FindFragment_pager);
    }

    private void setListener() {
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        rg.setOnCheckedChangeListener(this);
        vp_FindFragment_pager.addOnPageChangeListener(this);
    }
    public class Find_tab_Adapter extends FragmentPagerAdapter {

        private List<Fragment> list_fragment;                         //fragment列表
        private List<String> list_Title;                              //tab名的列表
        public Find_tab_Adapter(FragmentManager fm, List<Fragment> list_fragment, List<String> list_Title) {
            super(fm);
            this.list_fragment = list_fragment;
            this.list_Title = list_Title;
        }
        @Override
        public Fragment getItem(int position) {
            return list_fragment.get(position);
        }

        @Override
        public int getCount() {
            return list_Title.size();
        }

        //此方法用来显示tab上的名字
        @Override
        public CharSequence getPageTitle(int position) {

            return list_Title.get(position % list_Title.size());
        }
    }
    @Override
    public void onCheckedChanged(RadioGroup radioGroup, int i) {
        switch (i){
            case R.id.ranking_rb01:
                vp_FindFragment_pager.setCurrentItem(0);
                break;
            case R.id.ranking_rb02:
                vp_FindFragment_pager.setCurrentItem(1);
                break;
            case R.id.ranking_rb03:
                vp_FindFragment_pager.setCurrentItem(2);
                break;
        }
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        rbs.get(position).setChecked(true);
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
}
