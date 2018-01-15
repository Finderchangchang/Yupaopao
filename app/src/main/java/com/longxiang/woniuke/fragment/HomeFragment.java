package com.longxiang.woniuke.fragment;

import android.app.Fragment;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClientOption;
import com.baidu.location.Poi;
//import com.longxiang.woniuke.activity.CityActivity;
import com.google.gson.Gson;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshScrollView;
import com.longxiang.woniuke.activity.AllTypeActivity;
import com.longxiang.woniuke.activity.DetailsActivity;
import com.longxiang.woniuke.activity.DsflActivity;
import com.longxiang.woniuke.activity.FriendDataActivity;
import com.longxiang.woniuke.activity.HomeWebViewActivity;
import com.longxiang.woniuke.activity.PurseActivity;
import com.longxiang.woniuke.R;
import com.longxiang.woniuke.adapter.HomeAdapter;
import com.longxiang.woniuke.adapter.HomeAdapter2;
import com.longxiang.woniuke.adapter.HomeGridviewAdapter;
import com.longxiang.woniuke.bean.HomeListData;
import com.longxiang.woniuke.bean.HomeListData2;
import com.longxiang.woniuke.bean.XmData;
import com.longxiang.woniuke.utils.ListScrollUtil;
import com.longxiang.woniuke.utils.MyApp;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import de.greenrobot.event.Subscribe;
import de.greenrobot.event.ThreadMode;

/**
 * Created by Administrator on 2016/5/16 0016.
 */
public class HomeFragment extends Fragment implements View.OnClickListener, BDLocationListener{
    private ScrollView mPullRefreshScrollView;
    private ListView lv;
    private View subview,headview1,headview2,headview3;
    private List<String> lists;
    private TextView title;
    private TextView tv_location;
    private List<ImageView> imgs;
    private List<ImageView> images;
    private List<TextView> tvs;
    private String tvTitle;
    private ImageView iv01;
    private ImageView iv02;
    private ImageView iv03;
    private GridView gridview;
    List<HomeListData> listdata=new ArrayList<>();
    List<HomeListData2> listdata2=new ArrayList<>();
    List<XmData.DataBean> otherlist=new ArrayList<>();
    Handler handler=new Handler();
    private XmData data;
    private HomeAdapter2 adapter2;
    private HomeAdapter adapter;
    private  int count=0;
    private ImageView ivMore;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.item_homefragment,null);
        init(view, inflater);
        getData();
        setListener();
        new Thread(){
            @Override
            public void run() {
                getHomedata();
                getGridviewdata();
            }
        }.start();
        return view;
    }
    private void getGridviewdata() {
        RequestParams params=new RequestParams("http://bubblefish.jbserver.cn/api/order/getAlltype");
        x.http().get(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Log.i("homefragment2", "onSuccess: "+result);

                data=new Gson().fromJson(result,XmData.class);
                for (int i=0;i<10;i++){
                    otherlist.add(data.getData().get(i));
                }
                gridview.setAdapter(new HomeGridviewAdapter(getActivity(),otherlist));
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

    private void getHomedata() {
        RequestParams params=new RequestParams("http://bubblefish.jbserver.cn/index.php/api/index/index");
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Log.i("ccccccccc",result);
                try {
                    JSONObject json=new JSONObject(result);
                    JSONObject obj=json.getJSONObject("data");
                    JSONArray activity=obj.getJSONArray("activty");
                    for (int i=0;i<activity.length();i++){
                        JSONObject obj1=activity.getJSONObject(i);
                        x.image().bind(images.get(i),obj1.getString("pic"));
                        final String url= obj1.getString("url");
                        final String title=obj1.getString("title");
                        images.get(i).setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent intent=new Intent(getActivity(),HomeWebViewActivity.class);
                                intent.putExtra("url",url);
                                intent.putExtra("title",title);
                                startActivity(intent);
                            }
                        });
                    }
                    JSONArray top=obj.getJSONArray("TOP100");
                    for (int i=0;i<top.length();i++){
                        List<String> pics=new ArrayList<String>();
                        JSONObject obj2=top.getJSONObject(i);
                        JSONArray godpic=obj2.getJSONArray("godpic");
                        Log.i("homefragment", "onSuccess: "+godpic.length());
                        for (int j=0;j<godpic.length();j++){
                            pics.add(godpic.getString(j));
                        }
                        HomeListData h1=new HomeListData(obj2.getString("uid"),obj2.getString("sumtimes"),obj2.getDouble("level"),obj2.getString("time"),obj2.getString("starchar"),obj2.getString("age"),obj2.getString("head_pic"),obj2.getString("nickname"),obj2.getString("sex"),pics);
                        listdata.add(h1);
//                        listdata.add(new HomeListData(obj2.getString("uid"),obj2.getString("sumtiems"),obj2.getString("level"),obj2.getDouble("distance"),obj2.getString("time"),obj2.getString("starchar"),obj2.getString("age"),obj2.getString("head_pic"),obj2.getString("nickname"),obj2.getString("sex"),pics));
                    }
                    adapter=new HomeAdapter(getActivity(),listdata);
                    lv.setAdapter(adapter);
                    ListScrollUtil. setListViewHeightBasedOnChildren(lv);
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

    private void setListener() {
        ivMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getActivity(),AllTypeActivity.class);
                startActivity(intent);
            }
        });
//        for (int i=0;i<imgs.size();i++){
//            final int j=i;
//            imgs.get(j).setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    tvTitle=tvs.get(j).getText().toString();
//                    Intent intent=new Intent(getActivity(),HomeWebViewActivity.class);
//                    intent.putExtra("title",tvTitle);
//                    startActivity(intent);
//                }
//            });
//        }
//        for (int i=0;i<images.size();i++){
//            images.get(i).setOnClickListener(this);
//        }
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent=new Intent(getActivity(),FriendDataActivity.class);
                String fid;
                String imgurl;
                if(listdata2.size()!=0) {
                    fid = listdata2.get(position).getUid();
                    imgurl = listdata2.get(position).getHead_pic();

                }else{
                    fid=listdata.get(position).getUid();
                    imgurl=listdata.get(position).getHead_pic();
                }
                intent.putExtra("fid", fid);
                intent.putExtra("imgurl", imgurl);
                startActivity(intent);
            }
        });
        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent;
                if(position==9){
                    intent=new Intent(getActivity(), AllTypeActivity.class);
                }else {

//                    XmData.DataBean bean= (XmData.DataBean) parent.getItemAtPosition(position);

                    XmData.DataBean bean=data.getData().get(position);
                    String jnid = bean.getId();
                    String jnname = bean.getTitle();
                    intent=new Intent(getActivity(), DsflActivity.class);
                    intent.putExtra("jnid", jnid);
                    intent.putExtra("jnname", jnname);
                    Log.i("homefragmentlist", "onItemClick: "+data.getData().get(position).getPrice().get(0));
                    intent.putStringArrayListExtra("price",data.getData().get(position).getPrice());
                }
                startActivity(intent);
            }
        });
    }

    private void init(View view,LayoutInflater inflater) {

        initLocation();//设置定位参数包括：定位模式（高精度定位模式，低功耗定位模式和仅用设备定位模式），
        // 返回坐标类型，是否打开GPS，是否返回地址信息、位置语义化信息、POI信息等等

        MyApp.mLocationClient.registerLocationListener(this);
        if (!MyApp.mLocationClient.isStarted()){
            MyApp.mLocationClient.start();}
        lv = (ListView) view.findViewById(R.id.lv_homefragment);
        subview=view.findViewById(R.id.sub_title_shouye);
        title = (TextView) subview.findViewById(R.id.tv_title_name);
        title.setTextColor(Color.WHITE);
        tv_location = (TextView) subview.findViewById(R.id.tv_city_location);
        tv_location.setTextColor(Color.WHITE);
        tv_location.setOnClickListener(this);
//        iv_search = (ImageView) subview.findViewById(R.id.iv_search);
        headview1=view.findViewById(R.id.sub_headview1);
        headview1.setFocusable( true);
        headview1.setFocusableInTouchMode( true);
        headview1.requestFocus();
        headview2=view.findViewById(R.id.sub_headview2);;
        headview3=view.findViewById(R.id.sub_headview3);;
        lists=new ArrayList<>();
//        lv.addHeaderView(headview1);
//        lv.addHeaderView(headview2);
//        lv.addHeaderView(headview3);
        gridview= (GridView) headview1.findViewById(R.id.home_gridview);
        gridview.setSelector(new ColorDrawable(Color.TRANSPARENT));
        lv.setSelector(new ColorDrawable(Color.TRANSPARENT));
        iv01= (ImageView) headview2.findViewById(R.id.home_item_iv01);
        iv02= (ImageView) headview2.findViewById(R.id.home_item_iv02);
        iv03= (ImageView) headview2.findViewById(R.id.home_item_iv03);
        mPullRefreshScrollView= (ScrollView) view.findViewById(R.id.homefragment_scrollview);
//        mPullRefreshScrollView.setMode(PullToRefreshBase.Mode.PULL_FROM_START);
//        mPullRefreshScrollView.setOnRefreshListener(this);
//        mPullRefreshScrollView.getLoadingLayoutProxy().setRefreshingLabel("正在加载");
//        mPullRefreshScrollView.getLoadingLayoutProxy().setPullLabel("上拉加载更多");
//        mPullRefreshScrollView.getLoadingLayoutProxy().setReleaseLabel("释放开始加载");
        ivMore= (ImageView) view.findViewById(R.id.homefragment_ivMore);
    }

    public void getData(){
        images=new ArrayList<>();
        images.add(iv01);
        images.add(iv02);
        images.add(iv03);
    }

    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()){
//            case R.id.tv_city_location:
//                intent=new Intent(getActivity(), CityActivity.class);
//                startActivity(intent);
//                break;
        }
    }

    private void initLocation(){
        LocationClientOption option = new LocationClientOption();
        option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy
        );//可选，默认高精度，设置定位模式，高精度，低功耗，仅设备
        option.setCoorType("bd09ll");//可选，默认gcj02，设置返回的定位结果坐标系
        int span=3000;
        option.setScanSpan(span);//可选，默认0，即仅定位一次，设置发起定位请求的间隔需要大于等于1000ms才是有效的
        option.setIsNeedAddress(true);//可选，设置是否需要地址信息，默认不需要
        option.setOpenGps(true);//可选，默认false,设置是否使用gps
        option.setLocationNotify(true);//可选，默认false，设置是否当gps有效时按照1S1次频率输出GPS结果
        option.setIsNeedLocationDescribe(true);//可选，默认false，设置是否需要位置语义化结果，可以在BDLocation.getLocationDescribe里得到，结果类似于“在北京天安门附近”
        option.setIsNeedLocationPoiList(true);//可选，默认false，设置是否需要POI结果，可以在BDLocation.getPoiList里得到
        option.setIgnoreKillProcess(false);//可选，默认true，定位SDK内部是一个SERVICE，并放到了独立进程，设置是否在stop的时候杀死这个进程，默认不杀死
        option.SetIgnoreCacheException(false);//可选，默认false，设置是否收集CRASH信息，默认收集
        option.setEnableSimulateGps(false);//可选，默认false，设置是否需要过滤gps仿真结果，默认需要
        MyApp.mLocationClient.setLocOption(option);
    }


    @Override
    public void onReceiveLocation(BDLocation location) {
        StringBuffer sb = new StringBuffer(256);
        sb.append("time : ");
        sb.append(location.getTime());
        sb.append("\nerror code : ");
        sb.append(location.getLocType());
        sb.append("\nlatitude : ");
        sb.append(location.getLatitude());
        sb.append("\nlontitude : ");
        sb.append(location.getLongitude());
        sb.append("\nradius : ");
        sb.append(location.getRadius());
        if (location.getLocType() == BDLocation.TypeGpsLocation){// GPS定位结果
            sb.append("\nspeed : ");
            sb.append(location.getSpeed());// 单位：公里每小时
            sb.append("\nsatellite : ");
            sb.append(location.getSatelliteNumber());
            sb.append("\nheight : ");
            sb.append(location.getAltitude());// 单位：米
            sb.append("\ndirection : ");
            sb.append(location.getDirection());// 单位度
            sb.append("\naddr : ");
            sb.append(location.getAddrStr());
            sb.append("\ndescribe : ");
            sb.append("gps定位成功");
            MyApp.lat_经度=""+location.getLatitude();
            MyApp.lng_纬度=""+location.getLongitude();
            MyApp.city_choose=location.getCity();
//            if (MyApp.city_choose.contains("市")) {
//                MyApp.city_choose = location.getCity().replace("市", "");
//            }
            tv_location.setText(MyApp.city_choose);
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    RequestParams params=new RequestParams("http://bubblefish.jbserver.cn/api/users/setposition");
                    params.addBodyParameter("uid",MyApp.uid);
                    params.addBodyParameter("lat",MyApp.lat_经度);
                    params.addBodyParameter("lng",MyApp.lng_纬度);
                    params.addBodyParameter("city",MyApp.city_choose);
                    x.http().post(params, new Callback.CommonCallback<String>() {
                        @Override
                        public void onSuccess(String result) {
                            Log.i("lvzhiwei888", "onSuccess: "+result+MyApp.lat_经度+","+MyApp.lng_纬度+","+MyApp.city_choose);
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
            },3000);
//            getHomedataDistance();
//            Toast.makeText(getActivity(), "定位成功", Toast.LENGTH_SHORT).show();
//            MyApp.mLocationClient.stop();
        } else if (location.getLocType() == BDLocation.TypeNetWorkLocation){// 网络定位结果
            sb.append("\naddr : ");
            sb.append(location.getAddrStr());
            //运营商信息
            sb.append("\noperationers : ");
            sb.append(location.getOperators());
            sb.append("\ndescribe : ");
            sb.append("网络定位成功");
            MyApp.lat_经度=""+location.getLatitude();
            MyApp.lng_纬度=""+location.getLongitude();
            MyApp.city_choose=location.getCity();
//            if (MyApp.city_choose.contains("市")) {
//                MyApp.city_choose = location.getCity().replace("市", "");
//            }
            tv_location.setText(MyApp.city_choose);
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    RequestParams params=new RequestParams("http://bubblefish.jbserver.cn/api/users/setposition");
                    params.addBodyParameter("uid",MyApp.uid);
                    params.addBodyParameter("lat",MyApp.lat_经度);
                    params.addBodyParameter("lng",MyApp.lng_纬度);
                    params.addBodyParameter("city",MyApp.city_choose);
                    x.http().post(params, new Callback.CommonCallback<String>() {
                        @Override
                        public void onSuccess(String result) {
                            Log.i("lvzhiwei888", "onSuccess: "+result+MyApp.lat_经度+","+MyApp.lng_纬度+","+MyApp.city_choose);
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
            },3000);
//            getHomedataDistance();
//            Toast.makeText(getActivity().getApplicationContext(), "定位成功", Toast.LENGTH_SHORT).show();
//            MyApp.mLocationClient.stop();
        } else if (location.getLocType() == BDLocation.TypeOffLineLocation) {// 离线定位结果
            sb.append("\ndescribe : ");
            sb.append("离线定位成功，离线定位结果也是有效的");

        } else if (location.getLocType() == BDLocation.TypeServerError) {
            sb.append("\ndescribe : ");
            sb.append("服务端网络定位失败，可以反馈IMEI号和大体定位时间到loc-bugs@baidu.com，会有人追查原因");
        } else if (location.getLocType() == BDLocation.TypeNetWorkException) {
            sb.append("\ndescribe : ");
            sb.append("网络不同导致定位失败，请检查网络是否通畅");
        } else if (location.getLocType() == BDLocation.TypeCriteriaException) {
            sb.append("\ndescribe : ");
            sb.append("无法获取有效定位依据导致定位失败，一般是由于手机的原因，处于飞行模式下一般会造成这种结果，可以试着重启手机");
        }
        sb.append("\nlocationdescribe : ");
        sb.append(location.getLocationDescribe());// 位置语义化信息
        List<Poi> list = location.getPoiList();// POI数据
        if (list != null) {
            sb.append("\npoilist size = : ");
            sb.append(list.size());
            for (Poi p : list) {
                sb.append("\npoi= : ");
                sb.append(p.getId() + " " + p.getName() + " " + p.getRank());
            }
        }
        Log.i("lvzhiwei999", "onReceiveLocation: "+sb);
//        if (!MyApp.isLocation){
//            Log.e("12345", "onReceiveLocation: "+result );
//            Toast.makeText(getActivity(), result, Toast.LENGTH_SHORT).show();
//            MyApp.isLocation=true;
//        }
//        tv_city.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                setResult(8013, intent);
//                finish();
//            }
//        });

//        Log.e(HttpUrl.TAG, "onReceiveLocation: " + Myapp.city_choose + Myapp.lat_纬度+"---"+Myapp.lon_经度);
//        Log.e("12345", "onReceiveLocation: "+result );
//        Toast.makeText(getActivity(), result, Toast.LENGTH_SHORT).show();
    }

    private void getHomedataDistance() {
        RequestParams params=new RequestParams("http://bubblefish.jbserver.cn/index.php/api/index/index");
        params.addBodyParameter("lat",MyApp.lat_经度);
        params.addBodyParameter("lng",MyApp.lng_纬度);
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Log.i("homefragment",result);
                try {
                    JSONObject json=new JSONObject(result);
                    JSONObject obj=json.getJSONObject("data");
                    JSONArray activity=obj.getJSONArray("activty");
                    for (int i=0;i<activity.length();i++){
                        JSONObject obj1=activity.getJSONObject(i);
                        x.image().bind(images.get(i),obj1.getString("pic"));
                        final String url= obj1.getString("url");
                        final String title=obj1.getString("title");
                        images.get(i).setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent intent=new Intent(getActivity(),HomeWebViewActivity.class);
                                intent.putExtra("url",url);
                                intent.putExtra("title",title);
                                startActivity(intent);
                            }
                        });
                    }
                    JSONArray top=obj.getJSONArray("TOP100");
                    for (int i=0;i<top.length();i++){
                        List<String> pics=new ArrayList<String>();
                        JSONObject obj2=top.getJSONObject(i);
                        JSONArray godpic=obj2.getJSONArray("godpic");
                        for (int j=0;j<godpic.length();j++){
                            pics.add(godpic.getString(j));
                        }
                        HomeListData2 h1=new HomeListData2(obj2.getString("uid"),obj2.getString("sumtimes"),obj2.getDouble("level"),obj2.getString("time"),obj2.getString("starchar"),obj2.getDouble("distance"),obj2.getString("age"),obj2.getString("head_pic"),obj2.getString("nickname"),obj2.getString("sex"),pics);
                        listdata2.add(h1);
//                        listdata.add(new HomeListData(obj2.getString("uid"),obj2.getString("sumtiems"),obj2.getString("level"),obj2.getDouble("distance"),obj2.getString("time"),obj2.getString("starchar"),obj2.getString("age"),obj2.getString("head_pic"),obj2.getString("nickname"),obj2.getString("sex"),pics));
                    }

                    adapter2=new HomeAdapter2(getActivity(),listdata2);
                    lv.setAdapter(adapter2);
                    ListScrollUtil. setListViewHeightBasedOnChildren(lv);
//                    mPullRefreshScrollView.onRefreshComplete();
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
    public void onResume() {
        super.onResume();

//        getGridviewdata();
        if(listdata.size()!=0&&listdata2.size()!=0) {
            listdata.clear();
        }
        if(count<2) {
            count++;
            listdata2.clear();
            getHomedataDistance();
        }
//        new Thread(){
//            @Override
//            public void run() {
//                listdata2.clear();
//                getHomedataDistance();
//            }
//        }.start();
    }
//
//    @Override
//    public void onPause() {
//        super.onPause();
//
//    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        MyApp.mLocationClient.stop();
    }

//    @Override
//    public void onPullDownToRefresh(PullToRefreshBase<ScrollView> refreshView) {
//        handler.postDelayed(new Runnable() {
//            @Override
//            public void run() {
////                mPullRefreshScrollView.onRefreshComplete();
//            }
//        },1500);
//    }
//
//    @Override
//    public void onPullUpToRefresh(PullToRefreshBase<ScrollView> refreshView) {
//
//    }
}
