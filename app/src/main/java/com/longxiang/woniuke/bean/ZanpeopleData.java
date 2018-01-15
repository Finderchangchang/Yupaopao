package com.longxiang.woniuke.bean;

import java.util.List;

/**
 * Created by Administrator on 2016/7/29.
 */
public class ZanpeopleData {

    /**
     * retcode : 2000
     * msg : 获取成功！
     * data : [{"uid":"88","head_pic":"http://bubblefish.jbserver.cn/Uploads/Picture/2016-07-18/20160718151847882.jpg","nickname":"菜","age":"24","sex":"女"}]
     */

    private int retcode;
    private String msg;
    /**
     * uid : 88
     * head_pic : http://bubblefish.jbserver.cn/Uploads/Picture/2016-07-18/20160718151847882.jpg
     * nickname : 菜
     * age : 24
     * sex : 女
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
        private String head_pic;
        private String nickname;
        private String age;
        private String sex;

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
}
