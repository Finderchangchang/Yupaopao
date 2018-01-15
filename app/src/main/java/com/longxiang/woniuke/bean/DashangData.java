package com.longxiang.woniuke.bean;

import java.util.List;

/**
 * Created by mac on 16/9/24.
 */
public class DashangData {

    /**
     * retcode : 2000
     * msg : 获取成功！
     * data : [{"rwid":"1","dmid":"81","uid":"97","head_pic":"http://bubblefish.jbserver.cn/Uploads/Picture/2016-09-10/20160910111237644.jpg","amount":"50.00","nickname":"屌炸天","sex":"男","age":"22"}]
     */

    private int retcode;
    private String msg;
    /**
     * rwid : 1
     * dmid : 81
     * uid : 97
     * head_pic : http://bubblefish.jbserver.cn/Uploads/Picture/2016-09-10/20160910111237644.jpg
     * amount : 50.00
     * nickname : 屌炸天
     * sex : 男
     * age : 22
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
        private String rwid;
        private String dmid;
        private String uid;
        private String head_pic;
        private String amount;
        private String nickname;
        private String sex;
        private String age;

        public String getRwid() {
            return rwid;
        }

        public void setRwid(String rwid) {
            this.rwid = rwid;
        }

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

        public String getHead_pic() {
            return head_pic;
        }

        public void setHead_pic(String head_pic) {
            this.head_pic = head_pic;
        }

        public String getAmount() {
            return amount;
        }

        public void setAmount(String amount) {
            this.amount = amount;
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
    }
}
