package com.longxiang.woniuke.bean;

import java.util.List;

/**
 * Created by Administrator on 2016/8/26.
 */
public class BlacklistData {

    /**
     * retcode : 2000
     * msg : 获取成功！
     * data : [{"uid":"98","nickname":"迪哥","head_pic":"http://bubblefish.jbserver.cn/Uploads/Picture/2016-08-23/20160823144448181.jpg"}]
     */

    private int retcode;
    private String msg;
    /**
     * uid : 98
     * nickname : 迪哥
     * head_pic : http://bubblefish.jbserver.cn/Uploads/Picture/2016-08-23/20160823144448181.jpg
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
