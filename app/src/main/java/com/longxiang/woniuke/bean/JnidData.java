package com.longxiang.woniuke.bean;

import java.util.List;

/**
 * Created by Administrator on 2016/7/27.
 */
public class JnidData {

    /**
     * retcode : 2000
     * msg : 获取成功！
     * data : [{"jnid":"97","state":"2"},{"jnid":"96","state":"1"}]
     */

    private int retcode;
    private String msg;
    /**
     * jnid : 97
     * state : 2
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
        private String jnid;
        private String state;

        public String getJnid() {
            return jnid;
        }

        public void setJnid(String jnid) {
            this.jnid = jnid;
        }

        public String getState() {
            return state;
        }

        public void setState(String state) {
            this.state = state;
        }
    }
}
