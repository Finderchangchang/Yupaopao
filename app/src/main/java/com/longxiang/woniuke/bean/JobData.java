package com.longxiang.woniuke.bean;

import java.util.List;

/**
 * Created by Administrator on 2016/7/16.
 */
public class JobData {


    /**
     * retcode : 2000
     * msg : 获取成功！
     * data : [{"occupat":"计算机|互联网|通信"},{"occupat":"生产|工艺|制造"},{"occupat":"特殊"}]
     */

    private int retcode;
    private String msg;
    /**
     * occupat : 计算机|互联网|通信
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
        private String occupat;

        public String getOccupat() {
            return occupat;
        }

        public void setOccupat(String occupat) {
            this.occupat = occupat;
        }
    }
}
