package com.longxiang.woniuke.bean;

import java.util.List;

/**
 * Created by Administrator on 2016/8/19.
 */
public class WeekRankData {

    /**
     * retcode : 2000
     * msg : 获取成功！
     * data : [{"uid":"88","weekrank":"0","nickname":"菜牙","sex":"女","age":"24","starchar":"双鱼座","head_pic":"http://bubblefish.jbserver.cn/Uploads/Picture/2016-08-18/20160818111420613.jpg"}]
     */

    private int retcode;
    private String msg;
    /**
     * uid : 88
     * weekrank : 0
     * nickname : 菜牙
     * sex : 女
     * age : 24
     * starchar : 双鱼座
     * head_pic : http://bubblefish.jbserver.cn/Uploads/Picture/2016-08-18/20160818111420613.jpg
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
        private String weekrank;
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

        public String getWeekrank() {
            return weekrank;
        }

        public void setWeekrank(String weekrank) {
            this.weekrank = weekrank;
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
