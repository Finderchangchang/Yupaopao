package com.longxiang.woniuke.bean;

/**
 * Created by Administrator on 2016/8/27.
 */
public class GodskilldetailData {

    /**
     * retcode : 2000
     * msg : 获取成功！
     * data : {"userinfo":{"head_pic":"http://bubblefish.jbserver.cn/Uploads/Picture/2016-08-23/20160823144448181.jpg","nickname":"迪哥","age":"26","sex":"男"},"godinfo":{"pic":"http://bubblefish.jbserver.cn/Uploads/Picture/2016-08-24/2016082410493747.jpg","price":"30.00","skill":"声优聊天","level":"音诱","jnid":"112","explain":"kkk","unit":"小时","skill_pic":"http://bubblefish.jbserver.cn/Uploads/Picture/2016-08-22/57baa87fc70f4.png","times":"0","commenttimes":"0","avglevel":null,"basicprice":""},"other":{"distance":12244.36,"time":"2分钟前"}}
     */

    private int retcode;
    private String msg;
    /**
     * userinfo : {"head_pic":"http://bubblefish.jbserver.cn/Uploads/Picture/2016-08-23/20160823144448181.jpg","nickname":"迪哥","age":"26","sex":"男"}
     * godinfo : {"pic":"http://bubblefish.jbserver.cn/Uploads/Picture/2016-08-24/2016082410493747.jpg","price":"30.00","skill":"声优聊天","level":"音诱","jnid":"112","explain":"kkk","unit":"小时","skill_pic":"http://bubblefish.jbserver.cn/Uploads/Picture/2016-08-22/57baa87fc70f4.png","times":"0","commenttimes":"0","avglevel":null,"basicprice":""}
     * other : {"distance":12244.36,"time":"2分钟前"}
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
        /**
         * head_pic : http://bubblefish.jbserver.cn/Uploads/Picture/2016-08-23/20160823144448181.jpg
         * nickname : 迪哥
         * age : 26
         * sex : 男
         */

        private UserinfoBean userinfo;
        /**
         * pic : http://bubblefish.jbserver.cn/Uploads/Picture/2016-08-24/2016082410493747.jpg
         * price : 30.00
         * skill : 声优聊天
         * level : 音诱
         * jnid : 112
         * explain : kkk
         * unit : 小时
         * skill_pic : http://bubblefish.jbserver.cn/Uploads/Picture/2016-08-22/57baa87fc70f4.png
         * times : 0
         * commenttimes : 0
         * avglevel : null
         * basicprice :
         */

        private GodinfoBean godinfo;
        /**
         * distance : 12244.36
         * time : 2分钟前
         */

        private OtherBean other;

        public UserinfoBean getUserinfo() {
            return userinfo;
        }

        public void setUserinfo(UserinfoBean userinfo) {
            this.userinfo = userinfo;
        }

        public GodinfoBean getGodinfo() {
            return godinfo;
        }

        public void setGodinfo(GodinfoBean godinfo) {
            this.godinfo = godinfo;
        }

        public OtherBean getOther() {
            return other;
        }

        public void setOther(OtherBean other) {
            this.other = other;
        }

        public static class UserinfoBean {
            private String head_pic;
            private String nickname;
            private String age;
            private String sex;
            private String real_name;

            public String getReal_name() {
                return real_name;
            }

            public void setReal_name(String real_name) {
                this.real_name = real_name;
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
        }

        public static class GodinfoBean {
            private String pic;
            private String price;
            private String skill;
            private String level;
            private String jnid;
            private String explain;
            private String unit;
            private String skill_pic;
            private String times;
            private String commenttimes;
            private Object avglevel;
            private String basicprice;
            private String djid;

            public String getDjid() {
                return djid;
            }

            public void setDjid(String djid) {
                this.djid = djid;
            }

            public String getPic() {
                return pic;
            }

            public void setPic(String pic) {
                this.pic = pic;
            }

            public String getPrice() {
                return price;
            }

            public void setPrice(String price) {
                this.price = price;
            }

            public String getSkill() {
                return skill;
            }

            public void setSkill(String skill) {
                this.skill = skill;
            }

            public String getLevel() {
                return level;
            }

            public void setLevel(String level) {
                this.level = level;
            }

            public String getJnid() {
                return jnid;
            }

            public void setJnid(String jnid) {
                this.jnid = jnid;
            }

            public String getExplain() {
                return explain;
            }

            public void setExplain(String explain) {
                this.explain = explain;
            }

            public String getUnit() {
                return unit;
            }

            public void setUnit(String unit) {
                this.unit = unit;
            }

            public String getSkill_pic() {
                return skill_pic;
            }

            public void setSkill_pic(String skill_pic) {
                this.skill_pic = skill_pic;
            }

            public String getTimes() {
                return times;
            }

            public void setTimes(String times) {
                this.times = times;
            }

            public String getCommenttimes() {
                return commenttimes;
            }

            public void setCommenttimes(String commenttimes) {
                this.commenttimes = commenttimes;
            }

            public Object getAvglevel() {
                return avglevel;
            }

            public void setAvglevel(Object avglevel) {
                this.avglevel = avglevel;
            }

            public String getBasicprice() {
                return basicprice;
            }

            public void setBasicprice(String basicprice) {
                this.basicprice = basicprice;
            }
        }

        public static class OtherBean {
            private double distance;
            private String time;

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
}
