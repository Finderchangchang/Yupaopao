package com.longxiang.woniuke.bean;

import java.util.List;

/**
 * Created by Administrator on 2016/8/27.
 */
public class GodcommentData {

    /**
     * retcode : 2000
     * msg : 获取成功！
     * data : [{"uid":"98","level":"2","comment":"fgfdggff","realtime":"2016-08-26 06:00:00","noname":"1","nickname":"匿名","head_pic":""}]
     */

    private int retcode;
    private String msg;
    /**
     * uid : 98
     * level : 2
     * comment : fgfdggff
     * realtime : 2016-08-26 06:00:00
     * noname : 1
     * nickname : 匿名
     * head_pic :
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
        private String level;
        private String comment;
        private String realtime;
        private String noname;
        private String nickname;
        private String head_pic;

        public String getUid() {
            return uid;
        }

        public void setUid(String uid) {
            this.uid = uid;
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

        public String getRealtime() {
            return realtime;
        }

        public void setRealtime(String realtime) {
            this.realtime = realtime;
        }

        public String getNoname() {
            return noname;
        }

        public void setNoname(String noname) {
            this.noname = noname;
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
