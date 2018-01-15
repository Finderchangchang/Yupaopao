package com.longxiang.woniuke.bean;

import java.util.List;

/**
 * Created by Administrator on 2016/7/26.
 */
public class DynamicData {
    /**
     * retcode : 2000
     * msg : 获取成功！
     * data : [{"dmid":"4","uid":"88","content":"就想问你屌不屌。。。","pic":"http://bubblefish.jbserver.cn/Uploads/Picture/2016-07-26/20160726151556210.jpg","addtime":"1469517375","landtimes":"0","commenttimes":"0","rewardtimes":"0","distance":0,"time":"刚刚"}]
     */

    private int retcode;
    private String msg;
    /**
     * dmid : 4
     * uid : 88
     * content : 就想问你屌不屌。。。
     * pic : http://bubblefish.jbserver.cn/Uploads/Picture/2016-07-26/20160726151556210.jpg
     * addtime : 1469517375
     * landtimes : 0
     * commenttimes : 0
     * rewardtimes : 0
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
        private String dmid;
        private String uid;
        private String content;
        private String pic;
        private String addtime;
        private String landtimes;
        private String commenttimes;
        private String rewardtimes;
        private double distance;
        private String time;

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

        public String getAddtime() {
            return addtime;
        }

        public void setAddtime(String addtime) {
            this.addtime = addtime;
        }

        public String getLandtimes() {
            return landtimes;
        }

        public void setLandtimes(String landtimes) {
            this.landtimes = landtimes;
        }

        public String getCommenttimes() {
            return commenttimes;
        }

        public void setCommenttimes(String commenttimes) {
            this.commenttimes = commenttimes;
        }

        public String getRewardtimes() {
            return rewardtimes;
        }

        public void setRewardtimes(String rewardtimes) {
            this.rewardtimes = rewardtimes;
        }

        public double getDistance() {
            return distance;
        }

        public void setDistance(int distance) {
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
