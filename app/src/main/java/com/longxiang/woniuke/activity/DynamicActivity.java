package com.longxiang.woniuke.activity;

import android.content.Intent;
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

import java.util.ArrayList;
import java.util.List;

public class DynamicActivity extends AppCompatActivity implements View.OnClickListener, RadioGroup.OnCheckedChangeListener, ViewPager.OnPageChangeListener {
    private ImageView ivBack;
    private ImageView ivFbdynamic;
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
        setContentView(R.layout.activity_dynamic);
        setView();
        setListener();
    }

    private void setView() {
        ivBack= (ImageView) findViewById(R.id.dynamic_iv_back);
        title= (TextView) findViewById(R.id.dynamic_tv_title);
        title.setTextColor(Color.parseColor("#ffffff"));
        ivFbdynamic= (ImageView) findViewById(R.id.dynamic_iv_fbdynamic);
//        tab_FindFragment_title= (TabLayout) findViewById(R.id.dynamic_tabLayout);
        vp_FindFragment_pager= (ViewPager) findViewById(R.id.dynamic_viewPager);
        vp_FindFragment_pager.setOffscreenPageLimit(3);
        list_fragment=new ArrayList<>();
        list_fragment.add(new JhFragment());
        list_fragment.add(new FjFragment());
        list_fragment.add(new GzFragment());
        list_title=new ArrayList<>();
        list_title.add("精华");
        list_title.add("附近");
        list_title.add("关注");
        rg= (RadioGroup) findViewById(R.id.dynamic_rg);
        rb01= (RadioButton) findViewById(R.id.dynamic_rb01);
        rb02= (RadioButton) findViewById(R.id.dynamic_rb02);
        rb03= (RadioButton) findViewById(R.id.dynamic_rb03);
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
        ivBack.setOnClickListener(this);
        ivFbdynamic.setOnClickListener(this);
        rg.setOnCheckedChangeListener(this);
        vp_FindFragment_pager.addOnPageChangeListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.dynamic_iv_back:
                finish();
                break;
            case R.id.dynamic_iv_fbdynamic:
                Intent intent=new Intent(this,FabuDtActivity.class);
                startActivity(intent);
                break;
        }
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
            case R.id.dynamic_rb01:
                vp_FindFragment_pager.setCurrentItem(0);
                break;
            case R.id.dynamic_rb02:
                vp_FindFragment_pager.setCurrentItem(1);
                break;
            case R.id.dynamic_rb03:
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
