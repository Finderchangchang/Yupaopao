package com.longxiang.woniuke.bean;

import java.util.List;

/**
 * Created by Administrator on 2016/8/10.
 */
public class SelcetGodData {

    /**
     * retcode : 2000
     * msg : 获取成功
     * data : [{"uid":"94","bgid":"15","starlevel":0,"alltimes":"0","head_pic":"http://bubblefish.jbserver.cn/Uploads/Picture/2016-08-03/20160803094647768.jpg","sex":"女","age":"12","level":"全部","nickname":"12","amount":0.5,"ucmoney":"0","oid":"179"}]
     */

    private int retcode;
    private String msg;
    /**
     * uid : 94
     * bgid : 15
     * starlevel : 0
     * alltimes : 0
     * head_pic : http://bubblefish.jbserver.cn/Uploads/Picture/2016-08-03/20160803094647768.jpg
     * sex : 女
     * age : 12
     * level : 全部
     * nickname : 12
     * amount : 0.5
     * ucmoney : 0
     * oid : 179
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
        private String bgid;
        private int starlevel;
        private String alltimes;
        private String head_pic;
        private String sex;
        private String age;
        private String level;
        private String nickname;
        private double amount;
        private String ucmoney;
        private String oid;

        public String getUid() {
            return uid;
        }

        public void setUid(String uid) {
            this.uid = uid;
        }

        public String getBgid() {
            return bgid;
        }

        public void setBgid(String bgid) {
            this.bgid = bgid;
        }

        public int getStarlevel() {
            return starlevel;
        }

        public void setStarlevel(int starlevel) {
            this.starlevel = starlevel;
        }

        public String getAlltimes() {
            return alltimes;
        }

        public void setAlltimes(String alltimes) {
            this.alltimes = alltimes;
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

        public String getAge() {
            return age;
        }

        public void setAge(String age) {
            this.age = age;
        }

        public String getLevel() {
            return level;
        }

        public void setLevel(String level) {
            this.level = level;
        }

        public String getNickname() {
            return nickname;
        }

        public void setNickname(String nickname) {
            this.nickname = nickname;
        }

        public double getAmount() {
            return amount;
        }

        public void setAmount(double amount) {
            this.amount = amount;
        }

        public String getUcmoney() {
            return ucmoney;
        }

        public void setUcmoney(String ucmoney) {
            this.ucmoney = ucmoney;
        }

        public String getOid() {
            return oid;
        }

        public void setOid(String oid) {
            this.oid = oid;
        }
    }
}
