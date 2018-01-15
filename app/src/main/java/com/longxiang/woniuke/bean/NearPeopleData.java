package com.longxiang.woniuke.bean;

import java.util.List;

/**
 * Created by Administrator on 2016/8/13.
 */
public class NearPeopleData {

    /**
     * retcode : 2000
     * msg : 获取成功！
     * data : [{"uid":"88","lat":"39.908840","lng":"116.638794","geohash":"wx4gn1jvbfvc","last_login_time":"1471602334","distance":0,"time":"刚刚","nickname":"菜牙哥","sex":"女","age":"24","sign":"去你大爷的","head_pic":"http://bubblefish.jbserver.cn/Uploads/Picture/2016-08-19/20160819140254225.jpg","timezone":7,"godpic":["http://bubblefish.jbserver.cn/Uploads/Picture/2016-07-20/578f0f06f3844.png","http://bubblefish.jbserver.cn/Uploads/Picture/2016-07-20/578f0eda129c9.png","http://bubblefish.jbserver.cn/Uploads/Picture/2016-07-20/578f0f20b3b81.png","http://bubblefish.jbserver.cn/Uploads/Picture/2016-07-20/578f0e84759a5.png","http://bubblefish.jbserver.cn/Uploads/Picture/2016-07-20/578f0ee873d2f.png","http://bubblefish.jbserver.cn/Uploads/Picture/2016-07-20/578f0e9aa58a3.png"]},{"uid":"95","lat":"39.908848","lng":"116.638817","geohash":"wx4gn1jvckem","last_login_time":"1471595659","distance":0,"time":"1小时前","nickname":"志伟篮子","sex":"男","age":"50","sign":"","head_pic":"http://bubblefish.jbserver.cn/Uploads/Picture/2016-08-19/20160819153238737.jpg","timezone":6682},{"uid":"94","lat":"39.908894","lng":"116.638878","geohash":"wx4gn1jy4v43","last_login_time":"1471595594","distance":0.01,"time":"1小时前","nickname":"你于大妈的公爹哈","sex":"女","age":"12","sign":"ABC","head_pic":"http://bubblefish.jbserver.cn/Uploads/Picture/2016-08-17/20160817172816885.jpg","timezone":6747,"godpic":["http://bubblefish.jbserver.cn/Uploads/Picture/2016-07-20/578f0e9aa58a3.png","http://bubblefish.jbserver.cn/Uploads/Picture/2016-07-20/578f0ee873d2f.png","http://bubblefish.jbserver.cn/Uploads/Picture/2016-07-21/57902fa1bcb94.png"]},{"uid":"82","lat":"0.000000","lng":"0.000000","geohash":"7zzzzzzzzzzz","last_login_time":"1471599479","distance":12244.36,"time":"47分钟前","nickname":"芦笛","sex":"男","age":"18","sign":"好好发广告还将举办","head_pic":"http://bubblefish.jbserver.cn/Uploads/Picture/2016-07-16/20160716095919455.jpg","timezone":2862,"godpic":["http://bubblefish.jbserver.cn/Uploads/Picture/2016-07-21/57902fa1bcb94.png","http://bubblefish.jbserver.cn/Uploads/Picture/2016-07-20/578f0eda129c9.png","http://bubblefish.jbserver.cn/Uploads/Picture/2016-07-20/578f0f06f3844.png","http://bubblefish.jbserver.cn/Uploads/Picture/2016-07-20/578f0e9aa58a3.png"]},{"uid":"96","lat":"0.000000","lng":"0.000000","geohash":"7zzzzzzzzzzz","last_login_time":"1471601828","distance":12244.36,"time":"8分钟前","nickname":"二傻子","sex":"男","age":"43","sign":"","head_pic":"http://bubblefish.jbserver.cn/Uploads/Picture/2016-08-19/2016081916411974.jpg","timezone":513}]
     */

    private int retcode;
    private String msg;
    /**
     * uid : 88
     * lat : 39.908840
     * lng : 116.638794
     * geohash : wx4gn1jvbfvc
     * last_login_time : 1471602334
     * distance : 0
     * time : 刚刚
     * nickname : 菜牙哥
     * sex : 女
     * age : 24
     * sign : 去你大爷的
     * head_pic : http://bubblefish.jbserver.cn/Uploads/Picture/2016-08-19/20160819140254225.jpg
     * timezone : 7
     * godpic : ["http://bubblefish.jbserver.cn/Uploads/Picture/2016-07-20/578f0f06f3844.png","http://bubblefish.jbserver.cn/Uploads/Picture/2016-07-20/578f0eda129c9.png","http://bubblefish.jbserver.cn/Uploads/Picture/2016-07-20/578f0f20b3b81.png","http://bubblefish.jbserver.cn/Uploads/Picture/2016-07-20/578f0e84759a5.png","http://bubblefish.jbserver.cn/Uploads/Picture/2016-07-20/578f0ee873d2f.png","http://bubblefish.jbserver.cn/Uploads/Picture/2016-07-20/578f0e9aa58a3.png"]
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
        private String lat;
        private String lng;
        private String geohash;
        private String last_login_time;
        private double distance;
        private String time;
        private String nickname;
        private String sex;
        private String age;
        private String sign;
        private String head_pic;
        private int timezone;
        private List<String> godpic;

        public String getUid() {
            return uid;
        }

        public void setUid(String uid) {
            this.uid = uid;
        }

        public String getLat() {
            return lat;
        }

        public void setLat(String lat) {
            this.lat = lat;
        }

        public String getLng() {
            return lng;
        }

        public void setLng(String lng) {
            this.lng = lng;
        }

        public String getGeohash() {
            return geohash;
        }

        public void setGeohash(String geohash) {
            this.geohash = geohash;
        }

        public String getLast_login_time() {
            return last_login_time;
        }

        public void setLast_login_time(String last_login_time) {
            this.last_login_time = last_login_time;
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

        public String getAge() {
            return age;
        }

        public void setAge(String age) {
            this.age = age;
        }

        public String getSign() {
            return sign;
        }

        public void setSign(String sign) {
            this.sign = sign;
        }

        public String getHead_pic() {
            return head_pic;
        }

        public void setHead_pic(String head_pic) {
            this.head_pic = head_pic;
        }

        public int getTimezone() {
            return timezone;
        }

        public void setTimezone(int timezone) {
            this.timezone = timezone;
        }

        public List<String> getGodpic() {
            return godpic;
        }

        public void setGodpic(List<String> godpic) {
            this.godpic = godpic;
        }
    }
}
