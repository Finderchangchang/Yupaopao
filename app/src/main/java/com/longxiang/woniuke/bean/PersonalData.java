package com.longxiang.woniuke.bean;

/**
 * Created by mac on 16/10/15.
 */
public class PersonalData {

    /**
     * retcode : 2000
     * msg : 获取成功！
     * data : {"uid":"110","nickname":"王者无需爱.","head_pic":"http://bubblefish.jbserver.cn/Uploads/Picture/2016-10-11/2016101113383238.jpg"}
     */

    private int retcode;
    private String msg;
    /**
     * uid : 110
     * nickname : 王者无需爱.
     * head_pic : http://bubblefish.jbserver.cn/Uploads/Picture/2016-10-11/2016101113383238.jpg
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
        private String uid;
        private String nickname;
        private String head_pic;

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

        public String getHead_pic() {
            return head_pic;
        }

        public void setHead_pic(String head_pic) {
            this.head_pic = head_pic;
        }
    }
}
