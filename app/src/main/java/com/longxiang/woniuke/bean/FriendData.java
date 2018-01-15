package com.longxiang.woniuke.bean;

/**
 * Created by Administrator on 2016/7/22.
 */
public class FriendData {


    /**
     * retcode : 2000
     * msg : 获取成功！
     * data : {"nickname":"123","age":"24","sex":"女","head_pic":"http://bubblefish.jbserver.cn/Uploads/Picture/2016-07-18/20160718151847882.jpg","birthday":"1992-2-21","starchar":"双鱼座","sign":"去你大爷的","occupation":"生产|工艺|制造","school":"最美不过下雨天，优衣库的试衣间","interest":"WOW/杀人游戏/守望先锋","mobile":"13029000604","distance":0,"moment":"刚刚","followstate":1}
     */

    private int retcode;
    private String msg;
    /**
     * nickname : 123
     * age : 24
     * sex : 女
     * head_pic : http://bubblefish.jbserver.cn/Uploads/Picture/2016-07-18/20160718151847882.jpg
     * birthday : 1992-2-21
     * starchar : 双鱼座
     * sign : 去你大爷的
     * occupation : 生产|工艺|制造
     * school : 最美不过下雨天，优衣库的试衣间
     * interest : WOW/杀人游戏/守望先锋
     * mobile : 13029000604
     * distance : 0
     * moment : 刚刚
     * followstate : 1
     */

    private DataBean data;

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

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        private String nickname;
        private String age;
        private String sex;
        private String head_pic;
        private String birthday;
        private String starchar;
        private String sign;
        private String occupation;
        private String school;
        private String interest;
        private String mobile;
        private double distance;
        private String moment;
        private int followstate;

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

        public String getSex() {
            return sex;
        }

        public void setSex(String sex) {
            this.sex = sex;
        }

        public String getHead_pic() {
            return head_pic;
        }

        public void setHead_pic(String head_pic) {
            this.head_pic = head_pic;
        }

        public String getBirthday() {
            return birthday;
        }

        public void setBirthday(String birthday) {
            this.birthday = birthday;
        }

        public String getStarchar() {
            return starchar;
        }

        public void setStarchar(String starchar) {
            this.starchar = starchar;
        }

        public String getSign() {
            return sign;
        }

        public void setSign(String sign) {
            this.sign = sign;
        }

        public String getOccupation() {
            return occupation;
        }

        public void setOccupation(String occupation) {
            this.occupation = occupation;
        }

        public String getSchool() {
            return school;
        }

        public void setSchool(String school) {
            this.school = school;
        }

        public String getInterest() {
            return interest;
        }

        public void setInterest(String interest) {
            this.interest = interest;
        }

        public String getMobile() {
            return mobile;
        }

        public void setMobile(String mobile) {
            this.mobile = mobile;
        }

        public double getDistance() {
            return distance;
        }

        public void setDistance(double distance) {
            this.distance = distance;
        }

        public String getMoment() {
            return moment;
        }

        public void setMoment(String moment) {
            this.moment = moment;
        }

        public int getFollowstate() {
            return followstate;
        }

        public void setFollowstate(int followstate) {
            this.followstate = followstate;
        }
    }
}
