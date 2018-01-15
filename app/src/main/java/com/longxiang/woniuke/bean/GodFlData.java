package com.longxiang.woniuke.bean;

import java.util.List;

/**
 * Created by Administrator on 2016/8/12.
 */
public class GodFlData {
    /**
     * retcode : 2000
     * msg : 获取成功!
     * data : [{"uid":"98","times":"2","level":4,"levelname":"音诱","price":"20.00","unit":"小时","distance":12244.35,"time":"刚刚","starchar":"摩羯座","age":"26","head_pic":"http://bubblefish.jbserver.cn/Uploads/Picture/2016-08-23/20160823144448181.jpg","nickname":"迪哥","sex":"男"}]
     */

    private int retcode;
    private String msg;
    /**
     * uid : 98
     * times : 2
     * level : 4
     * levelname : 音诱
     * price : 20.00
     * unit : 小时
     * distance : 12244.35
     * time : 刚刚
     * starchar : 摩羯座
     * age : 26
     * head_pic : http://bubblefish.jbserver.cn/Uploads/Picture/2016-08-23/20160823144448181.jpg
     * nickname : 迪哥
     * sex : 男
     */

    private List<DataBean> data;

    public int getRetcode() {
        return retcode;
    }

    public void setRetcode(int retcode) {
        this.retcode = retcode;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        private String uid;
        private String times;
        private int level;
        private String levelname;
        private String price;
        private String unit;
        private double distance;
        private String time;
        private String starchar;
        private String age;
        private String head_pic;
        private String nickname;
        private String sex;
        private String bgid;
        private String mobile;

        public String getBgid() {
            return bgid;
        }

        public void setBgid(String bgid) {
            this.bgid = bgid;
        }

        public String getMobile() {
            return mobile;
        }

        public void setMobile(String mobile) {
            this.mobile = mobile;
        }

        public String getUid() {
            return uid;
        }

        public void setUid(String uid) {
            this.uid = uid;
        }

        public String getTimes() {
            return times;
        }

        public void setTimes(String times) {
            this.times = times;
        }

        public int getLevel() {
            return level;
        }

        public void setLevel(int level) {
            this.level = level;
        }

        public String getLevelname() {
            return levelname;
        }

        public void setLevelname(String levelname) {
            this.levelname = levelname;
        }

        public String getPrice() {
            return price;
        }

        public void setPrice(String price) {
            this.price = price;
        }

        public String getUnit() {
            return unit;
        }

        public void setUnit(String unit) {
            this.unit = unit;
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

        public String getStarchar() {
            return starchar;
        }

        public void setStarchar(String starchar) {
            this.starchar = starchar;
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

        public String getNickname() {
            return nickname;
        }

        public void setNickname(String nickname) {
            this.nickname = nickname;
        }

        public String getSex() {
            return sex;
        }

        public void setSex(String sex) {
            this.sex = sex;
        }
    }
}
