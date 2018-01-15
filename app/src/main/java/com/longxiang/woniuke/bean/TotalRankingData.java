package com.longxiang.woniuke.bean;

import java.util.List;

/**
 * Created by Administrator on 2016/8/19.
 */
public class TotalRankingData {

    /**
     * retcode : 2000
     * msg : 获取成功！
     * data : [{"uid":"88","allrank":"1146631","nickname":"菜牙哥","sex":"女","age":"24","starchar":"双鱼座","head_pic":"http://bubblefish.jbserver.cn/Uploads/Picture/2016-08-19/20160819140254225.jpg"},{"uid":"95","allrank":"88800","nickname":"志伟篮子","sex":"男","age":"50","starchar":"双鱼座","head_pic":"http://bubblefish.jbserver.cn/Uploads/Picture/2016-08-19/20160819153238737.jpg"},{"uid":"94","allrank":"11300","nickname":"你于大妈的公爹哈","sex":"女","age":"12","starchar":"狮子座","head_pic":"http://bubblefish.jbserver.cn/Uploads/Picture/2016-08-17/20160817172816885.jpg"}]
     */

    private int retcode;
    private String msg;
    /**
     * uid : 88
     * allrank : 1146631
     * nickname : 菜牙哥
     * sex : 女
     * age : 24
     * starchar : 双鱼座
     * head_pic : http://bubblefish.jbserver.cn/Uploads/Picture/2016-08-19/20160819140254225.jpg
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
        private String allrank;
        private String nickname;
        private String sex;
        private String age;
        private String starchar;
        private String head_pic;

        public String getUid() {
            return uid;
        }

        public void setUid(String uid) {
            this.uid = uid;
        }

        public String getAllrank() {
            return allrank;
        }

        public void setAllrank(String allrank) {
            this.allrank = allrank;
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

        public String getStarchar() {
            return starchar;
        }

        public void setStarchar(String starchar) {
            this.starchar = starchar;
        }

        public String getHead_pic() {
            return head_pic;
        }

        public void setHead_pic(String head_pic) {
            this.head_pic = head_pic;
        }
    }
}
