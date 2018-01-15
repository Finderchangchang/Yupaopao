package com.longxiang.woniuke.bean;

import java.util.List;

/**
 * Created by Administrator on 2016/8/2.
 */
public class YhqData {

    /**
     * retcode : 2000
     * msg : 获取成功！
     * data : [{"cid":"7","money":"10","days":"1000","type":"97","price":"100","pic":"http://bubblefish.jbserver.cn/Uploads/Picture/2016-07-21/57902fa1bcb94.png","typename":"DOTA"},{"cid":"8","money":"10","days":"1000","type":"0","price":"100","typename":"全部品类"}]
     */

    private int retcode;
    private String msg;
    /**
     * cid : 7
     * money : 10
     * days : 1000
     * type : 97
     * price : 100
     * pic : http://bubblefish.jbserver.cn/Uploads/Picture/2016-07-21/57902fa1bcb94.png
     * typename : DOTA
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
        private String cid;
        private String money;
        private String days;
        private String type;
        private String price;
        private String pic;
        private String typename;

        public String getCid() {
            return cid;
        }

        public void setCid(String cid) {
            this.cid = cid;
        }

        public String getMoney() {
            return money;
        }

        public void setMoney(String money) {
            this.money = money;
        }

        public String getDays() {
            return days;
        }

        public void setDays(String days) {
            this.days = days;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getPrice() {
            return price;
        }

        public void setPrice(String price) {
            this.price = price;
        }

        public String getPic() {
            return pic;
        }

        public void setPic(String pic) {
            this.pic = pic;
        }

        public String getTypename() {
            return typename;
        }

        public void setTypename(String typename) {
            this.typename = typename;
        }
    }
}
