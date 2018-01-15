package com.longxiang.woniuke.utils;

import android.util.Log;

/**
 * Created by jackiechan on 16/1/12.
 */
public class Friend implements Comparable<Friend> {
    private String name;
    private String uid;
    private String age;
    private String head_pic;
    private String sex;
    private String sign;
    private double distance;
    private String time;
    private String pinYin;

    public Friend(String name, String uid, String age, String head_pic, String sex, String sign, double distance, String time) {
        this.name = name;
        this.uid = uid;
        this.age = age;
        this.head_pic = head_pic;
        this.sex = sex;
        this.sign = sign;
        this.distance = distance;
        this.time = time;
        pinYin = PinYinUtils.GetPinyin(name);//防止比较的时候多次调用这个方法
//        if(pinYin.equals("")){
//            pinYin="#";
//        }
        Log.i("aksdjalskdj", "Friend: "+1111111+"");
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getHead_pic() {
        return head_pic;
    }

    public void setHead_pic(String head_pic) {
        this.head_pic = head_pic;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getPinYin() {
        return pinYin;
    }

    public void setPinYin(String pinYin) {
        this.pinYin = pinYin;
    }

    @Override
    public int compareTo(Friend another) {
        return pinYin.compareTo(another.getPinYin());
    }
}
