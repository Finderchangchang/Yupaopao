package com.longxiang.woniuke.bean;

import java.util.List;

/**
 * Created by Administrator on 2016/8/18.
 */
public class OrderRecorderData {


    /**
     * retcode : 2000
     * msg : 获取成功！
     * data : [{"oid":"303","uid":"98","jnid":"127","amount":"60.00","expense":"12.00","times":"1","realtime":"2016-08-26 06:00:00","level":"2","comment":"fgfdggff","head_pic":"http://bubblefish.jbserver.cn/Uploads/Picture/2016-08-23/20160823144448181.jpg","skill":"LOL","unit":"小时","skill_pic":"http://bubblefish.jbserver.cn/Uploads/Picture/2016-08-22/57bab60684d70.png","info":{"nickname":"迪哥","sex":"男","age":"26"}},{"oid":"302","uid":"98","jnid":"127","amount":"60.00","expense":"12.00","times":"1","realtime":"2016-08-26 05:10:00","level":null,"comment":null,"head_pic":"http://bubblefish.jbserver.cn/Uploads/Picture/2016-08-23/20160823144448181.jpg","skill":"LOL","unit":"小时","skill_pic":"http://bubblefish.jbserver.cn/Uploads/Picture/2016-08-22/57bab60684d70.png","info":{"nickname":"迪哥","sex":"男","age":"26"}}]
     */

    private int retcode;
    private String msg;
    /**
     * oid : 303
     * uid : 98
     * jnid : 127
     * amount : 60.00
     * expense : 12.00
     * times : 1
     * realtime : 2016-08-26 06:00:00
     * level : 2
     * comment : fgfdggff
     * head_pic : http://bubblefish.jbserver.cn/Uploads/Picture/2016-08-23/20160823144448181.jpg
     * skill : LOL
     * unit : 小时
     * skill_pic : http://bubblefish.jbserver.cn/Uploads/Picture/2016-08-22/57bab60684d70.png
     * info : {"nickname":"迪哥","sex":"男","age":"26"}
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
        private String oid;
        private String uid;
        private String jnid;
        private double amount;
        private double expense;
        private String times;
        private String realtime;
        private String level;
        private String comment;
        private String head_pic;
        private String skill;
        private String unit;
        private String skill_pic;
        /**
         * nickname : 迪哥
         * sex : 男
         * age : 26
         */

        private InfoBean info;

        public String getOid() {
            return oid;
        }

        public void setOid(String oid) {
            this.oid = oid;
        }

        public String getUid() {
            return uid;
        }

        public void setUid(String uid) {
            this.uid = uid;
        }

        public String getJnid() {
            return jnid;
        }

        public void setJnid(String jnid) {
            this.jnid = jnid;
        }

        public double getAmount() {
            return amount;
        }

        public void setAmount(double amount) {
            this.amount = amount;
        }

        public double getExpense() {
            return expense;
        }

        public void setExpense(double expense) {
            this.expense = expense;
        }

        public String getTimes() {
            return times;
        }

        public void setTimes(String times) {
            this.times = times;
        }

        public String getRealtime() {
            return realtime;
        }

        public void setRealtime(String realtime) {
            this.realtime = realtime;
        }

        public String getLevel() {
            return level;
        }

        public void setLevel(String level) {
            this.level = level;
        }

        public String getComment() {
            return comment;
        }

        public void setComment(String comment) {
            this.comment = comment;
        }

        public String getHead_pic() {
            return head_pic;
        }

        public void setHead_pic(String head_pic) {
            this.head_pic = head_pic;
        }

        public String getSkill() {
            return skill;
        }

        public void setSkill(String skill) {
            this.skill = skill;
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

        public InfoBean getInfo() {
            return info;
        }

        public void setInfo(InfoBean info) {
            this.info = info;
        }

        public static class InfoBean {
            private String nickname;
            private String sex;
            private String age;

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
        }
    }
}
