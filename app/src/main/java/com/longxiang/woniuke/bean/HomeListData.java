package com.longxiang.woniuke.bean;

import java.util.List;

/**
 * Created by Administrator on 2016/7/28.
 */
public class HomeListData {
    private String uid;

    private String sumtiems;

    private double level;

    private double distance;

    private String time;

    private String starchar;

    private String age;

    private String head_pic;

    private String nickname;

    private String sex;

    private List<String> godpic ;

    public HomeListData(String uid, String sumtiems, double level, String time, String starchar, String age, String head_pic, String nickname, String sex, List<String> godpic) {
        this.uid = uid;
        this.sumtiems = sumtiems;
        this.level = level;
        this.time = time;
        this.starchar = starchar;
        this.age = age;
        this.head_pic = head_pic;
        this.nickname = nickname;
        this.sex = sex;
        this.godpic = godpic;
    }

    public void setUid(String uid){
        this.uid = uid;
    }
    public String getUid(){
        return this.uid;
    }
    public void setSumtiems(String sumtiems){
        this.sumtiems = sumtiems;
    }
    public String getSumtiems(){
        return this.sumtiems;
    }
    public void setLevel(double level){
        this.level = level;
    }
    public double getLevel(){
        return this.level;
    }
    public void setTime(String time){
        this.time = time;
    }
    public String getTime(){
        return this.time;
    }
    public void setStarchar(String starchar){
        this.starchar = starchar;
    }
    public String getStarchar(){
        return this.starchar;
    }
    public void setAge(String age){
        this.age = age;
    }
    public String getAge(){
        return this.age;
    }
    public void setHead_pic(String head_pic){
        this.head_pic = head_pic;
    }
    public String getHead_pic(){
        return this.head_pic;
    }
    public void setNickname(String nickname){
        this.nickname = nickname;
    }
    public String getNickname(){
        return this.nickname;
    }
    public void setSex(String sex){
        this.sex = sex;
    }
    public String getSex(){
        return this.sex;
    }
    public void setGodpic(List<String> godpic){
        this.godpic = godpic;
    }
    public List<String> getGodpic(){
        return this.godpic;
    }
}
