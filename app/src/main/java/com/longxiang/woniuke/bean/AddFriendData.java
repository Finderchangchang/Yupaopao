package com.longxiang.woniuke.bean;

import java.util.List;

/**
 * Created by Administrator on 2016/7/25.
 */
public class AddFriendData {

    /**
     * retcode : 2000
     * msg : 成功!
     * data : [{"uid":"82","nickname":"123456","age":"18","head_pic":"http://bubblefish.jbserver.cn/Uploads/Picture/2016-07-16/20160716095919455.jpg","sex":"男","sign":"hhhhhhhhhhkkkkkkkkkl","distance":0,"time":"刚刚"}]
     */

    private int retcode;
    private String msg;
    /**
     * uid : 82
     * nickname : 123456
     * age : 18
     * head_pic : http://bubblefish.jbserver.cn/Uploads/Picture/2016-07-16/20160716095919455.jpg
     * sex : 男
     * sign : hhhhhhhhhhkkkkkkkkkl
     * distance : 0
     * time : 刚刚
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
        private String nickname;
        private String age;
        private String head_pic;
        private String sex;
        private String sign;
        private double distance;
        private String time;

        public String getUid() {
            return uid;
        }

        public void setUid(String uid) {
            this.uid = uid;
        }

        public String getNickname() {
            return nickname;
        }

        public void setNickname(String nickname) {
            this.nickname = nickname;
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
    }
}
