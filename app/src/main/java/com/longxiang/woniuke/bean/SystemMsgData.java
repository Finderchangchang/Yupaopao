package com.longxiang.woniuke.bean;

import java.util.List;

/**
 * Created by Administrator on 2016/8/22.
 */
public class SystemMsgData {

    /**
     * retcode : 2000
     * msg : 获取成功！
     * data : [{"id":"458","content":"123456","read":"0","add_time":"2016-08-19 19:54:31"},{"id":"460","content":"999888","read":"0","add_time":"2016-08-19 19:56:44"},{"id":"461","content":"恭喜！您的身份已被认证！","read":"0","add_time":"2016-08-19 21:13:41"},{"id":"462","content":"抱歉！您的身份认证失败请发送真实信息！","read":"0","add_time":"2016-08-19 21:15:58"},{"id":"459","content":"123456789","read":"1","add_time":"2016-08-19 19:55:03"}]
     */

    private int retcode;
    private String msg;
    /**
     * id : 458
     * content : 123456
     * read : 0
     * add_time : 2016-08-19 19:54:31
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
        private String id;
        private String content;
        private String read;
        private String add_time;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getRead() {
            return read;
        }

        public void setRead(String read) {
            this.read = read;
        }

        public String getAdd_time() {
            return add_time;
        }

        public void setAdd_time(String add_time) {
            this.add_time = add_time;
        }
    }
}
