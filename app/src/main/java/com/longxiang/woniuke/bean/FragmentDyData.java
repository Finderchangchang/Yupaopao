package com.longxiang.woniuke.bean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/8/15.
 */
public class FragmentDyData {

    /**
     * retcode : 2000
     * msg : 获取成功！
     * data : [{"dmid":"56","uid":"82","content":"他吞吞吐吐","pic":"http://bubblefish.jbserver.cn/Uploads/Picture/2016-08-13/20160813171654136.jpg","landtimes":"498","nickname":"芦笛","head_pic":"http://bubblefish.jbserver.cn/Uploads/Picture/2016-07-16/20160716095919455.jpg","addtime":"1471079821","sex":"男","age":"18","distance":1.35,"commenttimes":"1","time":"16分钟前","godpic":["http://bubblefish.jbserver.cn/Uploads/Picture/2016-07-21/57902fa1bcb94.png","http://bubblefish.jbserver.cn/Uploads/Picture/2016-07-20/578f0eda129c9.png","http://bubblefish.jbserver.cn/Uploads/Picture/2016-07-20/578f0f06f3844.png","http://bubblefish.jbserver.cn/Uploads/Picture/2016-07-20/578f0e9aa58a3.png"],"followstate":1},{"dmid":"54","uid":"82","content":"志伟篮子3","pic":"http://bubblefish.jbserver.cn/Uploads/Picture/2016-07-27/20160727182146965.jpg","landtimes":"630","nickname":"123456","head_pic":"http://bubblefish.jbserver.cn/Uploads/Picture/2016-07-16/20160716095919455.jpg","addtime":"1469614909","sex":"男","age":"18","distance":1.35,"commenttimes":"1","time":"16分钟前","godpic":["http://bubblefish.jbserver.cn/Uploads/Picture/2016-07-21/57902fa1bcb94.png","http://bubblefish.jbserver.cn/Uploads/Picture/2016-07-20/578f0eda129c9.png","http://bubblefish.jbserver.cn/Uploads/Picture/2016-07-20/578f0f06f3844.png","http://bubblefish.jbserver.cn/Uploads/Picture/2016-07-20/578f0e9aa58a3.png"],"followstate":1},{"dmid":"47","uid":"88","content":"志伟篮子2","pic":"http://bubblefish.jbserver.cn/Uploads/Picture/2016-07-27/2016072717072464.jpg","landtimes":"810","nickname":"菜","head_pic":"http://bubblefish.jbserver.cn/Uploads/Picture/2016-07-18/20160718151847882.jpg","addtime":"1469610477","sex":"女","age":"24","distance":0,"commenttimes":"3","time":"14分钟前","godpic":["http://bubblefish.jbserver.cn/Uploads/Picture/2016-07-20/578f0f06f3844.png","http://bubblefish.jbserver.cn/Uploads/Picture/2016-07-20/578f0eda129c9.png","http://bubblefish.jbserver.cn/Uploads/Picture/2016-07-20/578f0f20b3b81.png","http://bubblefish.jbserver.cn/Uploads/Picture/2016-07-20/578f0e84759a5.png","http://bubblefish.jbserver.cn/Uploads/Picture/2016-07-20/578f0ee873d2f.png","http://bubblefish.jbserver.cn/Uploads/Picture/2016-07-20/578f0e9aa58a3.png"],"followstate":3},{"dmid":"45","uid":"88","content":"志伟篮子1","pic":"http://bubblefish.jbserver.cn/Uploads/Picture/2016-07-27/20160727170209370.jpg","landtimes":"481","nickname":"菜","head_pic":"http://bubblefish.jbserver.cn/Uploads/Picture/2016-07-18/20160718151847882.jpg","addtime":"1469610143","sex":"女","age":"24","distance":0,"commenttimes":"5","time":"14分钟前","godpic":["http://bubblefish.jbserver.cn/Uploads/Picture/2016-07-20/578f0f06f3844.png","http://bubblefish.jbserver.cn/Uploads/Picture/2016-07-20/578f0eda129c9.png","http://bubblefish.jbserver.cn/Uploads/Picture/2016-07-20/578f0f20b3b81.png","http://bubblefish.jbserver.cn/Uploads/Picture/2016-07-20/578f0e84759a5.png","http://bubblefish.jbserver.cn/Uploads/Picture/2016-07-20/578f0ee873d2f.png","http://bubblefish.jbserver.cn/Uploads/Picture/2016-07-20/578f0e9aa58a3.png"],"followstate":3}]
     */

    private int retcode;
    private String msg;
    /**
     * dmid : 56
     * uid : 82
     * content : 他吞吞吐吐
     * pic : http://bubblefish.jbserver.cn/Uploads/Picture/2016-08-13/20160813171654136.jpg
     * landtimes : 498
     * nickname : 芦笛
     * head_pic : http://bubblefish.jbserver.cn/Uploads/Picture/2016-07-16/20160716095919455.jpg
     * addtime : 1471079821
     * sex : 男
     * age : 18
     * distance : 1.35
     * commenttimes : 1
     * time : 16分钟前
     * godpic : ["http://bubblefish.jbserver.cn/Uploads/Picture/2016-07-21/57902fa1bcb94.png","http://bubblefish.jbserver.cn/Uploads/Picture/2016-07-20/578f0eda129c9.png","http://bubblefish.jbserver.cn/Uploads/Picture/2016-07-20/578f0f06f3844.png","http://bubblefish.jbserver.cn/Uploads/Picture/2016-07-20/578f0e9aa58a3.png"]
     * followstate : 1
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
        private String dmid;
        private String uid;
        private String content;
        private String pic;
        private String landtimes;
        private String nickname;
        private String head_pic;
        private String addtime;
        private String sex;
        private String age;
        private double distance;
        private String commenttimes;
        private String time;
        private int followstate;
        private String rewardtimes;
        private ArrayList<String> godpic;

        public String getDmid() {
            return dmid;
        }

        public void setDmid(String dmid) {
            this.dmid = dmid;
        }

        public String getUid() {
            return uid;
        }

        public void setUid(String uid) {
            this.uid = uid;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getPic() {
            return pic;
        }

        public void setPic(String pic) {
            this.pic = pic;
        }

        public String getLandtimes() {
            return landtimes;
        }

        public void setLandtimes(String landtimes) {
            this.landtimes = landtimes;
        }

        public String getNickname() {
            return nickname;
        }

        public void setNickname(String nickname) {
            this.nickname = nickname;
        }

        public String getHead_pic() {
            return head_pic;
        }

        public void setHead_pic(String head_pic) {
            this.head_pic = head_pic;
        }

        public String getAddtime() {
            return addtime;
        }

        public void setAddtime(String addtime) {
            this.addtime = addtime;
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

        public double getDistance() {
            return distance;
        }

        public void setDistance(double distance) {
            this.distance = distance;
        }

        public String getCommenttimes() {
            return commenttimes;
        }

        public void setCommenttimes(String commenttimes) {
            this.commenttimes = commenttimes;
        }

        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
        }

        public int getFollowstate() {
            return followstate;
        }

        public void setFollowstate(int followstate) {
            this.followstate = followstate;
        }

        public ArrayList<String> getGodpic() {
            return godpic;
        }

        public void setGodpic(ArrayList<String> godpic) {
            this.godpic = godpic;
        }

        public String getRewardtimes() {
            return rewardtimes;
        }

        public void setRewardtimes(String rewardtimes) {
            this.rewardtimes = rewardtimes;
        }
    }
}
