package com.longxiang.woniuke.bean;

import java.util.List;

/**
 * Created by Administrator on 2016/7/18.
 */
public class IntrestData {

    /**
     * retcode : 2000
     * msg : 获取成功！
     * data : [{"interest":"LOL"},{"interest":"CF"},{"interest":"守望先锋"},{"interest":"DNF"},{"interest":"WOW"},{"interest":"杀人游戏"}]
     */

    private int retcode;
    private String msg;
    /**
     * interest : LOL
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
        private String interest;

        public String getInterest() {
            return interest;
        }

        public void setInterest(String interest) {
            this.interest = interest;
        }
    }
}
