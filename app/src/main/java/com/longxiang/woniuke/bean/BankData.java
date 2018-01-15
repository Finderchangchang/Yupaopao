package com.longxiang.woniuke.bean;

import java.util.List;

/**
 * Created by Administrator on 2016/8/5.
 */
public class BankData {

    /**
     * retcode : 2000
     * msg : 用户银行卡信息信息获取成功！
     * data : [{"bid":"18","bankcard":"6221882400086645257","realname":"爹","bankname":"邮储银行-绿卡银联标准卡-借记卡"}]
     */

    private int retcode;
    private String msg;
    /**
     * bid : 18
     * bankcard : 6221882400086645257
     * realname : 爹
     * bankname : 邮储银行-绿卡银联标准卡-借记卡
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
        private String bid;
        private String bankcard;
        private String realname;
        private String bankname;

        public String getBid() {
            return bid;
        }

        public void setBid(String bid) {
            this.bid = bid;
        }

        public String getBankcard() {
            return bankcard;
        }

        public void setBankcard(String bankcard) {
            this.bankcard = bankcard;
        }

        public String getRealname() {
            return realname;
        }

        public void setRealname(String realname) {
            this.realname = realname;
        }

        public String getBankname() {
            return bankname;
        }

        public void setBankname(String bankname) {
            this.bankname = bankname;
        }
    }
}
